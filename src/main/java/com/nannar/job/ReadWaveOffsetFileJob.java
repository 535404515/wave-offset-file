package com.nannar.job;

import com.google.common.collect.Maps;
import com.google.gson.JsonSyntaxException;
import com.nannar.dao.PmsBusiWaveFileMapper;
import com.nannar.entity.PmsBasicRailwayStation;
import com.nannar.entity.PmsBusiWaveFile;
import com.nannar.entity.PmsSystemDatabase;
import com.nannar.entity.TimePicker;
import com.nannar.service.DataBaseService;
import com.nannar.service.PmsBasicRailwayStationService;
import com.nannar.service.PmsBusiWaveFileService;
import com.nannar.threads.ThreadInvoker;
import com.nannar.threads.ThreadInvokerEvent;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Caiyuhui
 * @date 2020/5/15
 * @time 8:42
 */
public class ReadWaveOffsetFileJob implements Job {
    private Lock lock = new ReentrantLock();
    private final DataBaseService dataBaseService;
    private final PmsBasicRailwayStationService stationService;
    private final PmsBusiWaveFileService waveFileService;
    @Autowired
    private PmsBusiWaveFileMapper dao;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    public ReadWaveOffsetFileJob(DataBaseService dataBaseService, PmsBasicRailwayStationService stationService, PmsBusiWaveFileService waveFileService) {
        this.dataBaseService = dataBaseService;
        this.stationService = stationService;
        this.waveFileService = waveFileService;
    }

    /**
     * 定时状态属性
     */
    private static boolean IS_RUNNING = false;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        lock.lock();
        final Integer fileTypeId = 4;
        final TimePicker picker = TimePicker.newInstance();
        picker.pick();
        final TimePicker picker2 = TimePicker.newInstance();
        picker2.pick();
        ThreadInvoker invoker = new  ThreadInvoker(new ThreadInvokerEvent() {
            @Override
            public Object doEvent() {
                try{
                    List<PmsSystemDatabase> databaseList = dataBaseService.loadAlldatabases();
                    //每次处理10个文件
                    int pageSize = 50;
                    picker.stepSecond(true);
                    logger.info("进入wave offset file job线程！");
                    String logText = "";
                    List<PmsBusiWaveFile> waveFileList;
                    PmsBusiWaveFile waveFile = null;
                    for (PmsSystemDatabase database : databaseList) {
                        Map<String, String> stationMap = Maps.newHashMap();
                        List<PmsBasicRailwayStation> stationList = stationService.findAll(database.getMonitorStationDatabase());
                        for (PmsBasicRailwayStation station : stationList) {
                            stationMap.put(station.getRailwayStationId().toString(), station.getRailwayStationName());
                        }
                        try {
                            waveFileList = waveFileService.listFileForRead(database.getMonitorStationDatabase(),
                                    fileTypeId,
                                    pageSize);
                            picker2.stepSecond(true);
                            while (null != waveFileList && !waveFileList.isEmpty()) {
                                logger.info(String.format("进入wave offset file job线程,加载波形文件[%s][%s]行！", database.getTrainNoAlias(), waveFileList.size()));
                                for (int i = 0; i < waveFileList.size(); i++) {
                                    try{
                                        waveFile = waveFileList.get(i);
                                        waveFileService.readWaveOffsetFile(database.getMonitorStationDatabase(), waveFile, stationMap);
                                        logText = String.format("执行wave offset file job线程！处理完文件：%d,用时%s秒", waveFile.getFileLogId(), picker.stepSecond(true));
                                        logger.info(logText);
                                    }catch (JsonSyntaxException e){
                                        logger.error("文件ID："+waveFile.getFileLogId()+":-"+"JSON格式不正确"+e);
                                        waveFile.setFileState(PmsBusiWaveFile.FILE_STATE_FAILED);
                                        dao.updateByPrimaryKeySelective(waveFile,database.getMonitorStationDatabase());
                                        logger.error(String.format("JSON格式不正确，更新文件状态：fileLogId=%d,fileState=%d",waveFile.getFileLogId(),waveFile.getFileState()));
                                        lock.unlock();
                                        continue;
                                    }
                                }
                                try{
                                    waveFileList = waveFileService.listFileForRead(database.getMonitorStationDatabase(),fileTypeId,pageSize);
                                }catch (Exception e){
                                    logger.error("listFileForRead发生异常:"+e);
                                    lock.unlock();
                                    continue;
                                }
                            }
                        } catch (Exception e) {
                            if (null != waveFile) {
                                logText = String.format("执行wave offset file job出错！处理文件：%d,用时%s秒", waveFile.getFileLogId(), picker.stepSecond(true));
                                logger.error(logText);
                                logger.error("执行wave offset file job出错！"+e);
                            }
                            ReadWaveOffsetFileJob.IS_RUNNING = false;
                            lock.unlock();
                            break;
                        }
                        logger.info(String.format("wave offset file job线程读完[%s]设备,加载波形文件[%s][%s]行！", database.getExt2(), database.getTrainNoAlias(), waveFileList.size()));
                    }
                    logger.info(String.format("执行完wave offset file job线程！用时%s秒", picker.stepSecond(true)));
                    ReadWaveOffsetFileJob.IS_RUNNING = false;
                }catch (Exception e){
                    ReadWaveOffsetFileJob.IS_RUNNING = false;
                    logger.error("ReadWaveOffsetFileJob.doEvent方法发生异常:"+e);
                    lock.unlock();
                }
                return null;
            }
        });
        invoker.start();
        lock.unlock();
    }
}
