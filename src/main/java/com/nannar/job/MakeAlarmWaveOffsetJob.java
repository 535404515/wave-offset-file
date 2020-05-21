package com.nannar.job;
import java.util.Map;
import	java.util.concurrent.locks.ReentrantLock;

import com.nannar.entity.PmsBusiWaveFile;
import com.nannar.entity.PmsSystemDatabase;
import com.nannar.entity.TimePicker;
import com.nannar.service.DataBaseService;
import com.nannar.service.PmsBusiAlarmWaveOffsetService;
import com.nannar.service.PmsBusiWaveFileService;
import com.nannar.threads.ThreadInvoker;
import com.nannar.threads.ThreadInvokerEvent;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * @author Caiyuhui
 * @date 2020/5/15
 * @time 14:04
 */
@Component
public class MakeAlarmWaveOffsetJob implements Job {
    private Lock lock = new ReentrantLock();
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final DataBaseService dataBaseService;
    private final PmsBusiWaveFileService waveFileService;
    private final PmsBusiAlarmWaveOffsetService alarmWaveOffsetService;
    private static volatile boolean IS_RUNNING = false;


    @Autowired
    public MakeAlarmWaveOffsetJob(DataBaseService dataBaseService, PmsBusiWaveFileService waveFileService, PmsBusiAlarmWaveOffsetService alarmWaveOffsetService) {
        this.dataBaseService = dataBaseService;
        this.waveFileService = waveFileService;
        this.alarmWaveOffsetService = alarmWaveOffsetService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        lock.lock();
        if(MakeAlarmWaveOffsetJob.IS_RUNNING) {
            return;
        }
        MakeAlarmWaveOffsetJob.IS_RUNNING = true;
        //读取动态拉出值（碳滑板接触位置）文件进行入库。
        final Integer fileTypeId = 4;
        final TimePicker picker = TimePicker.newInstance();
        picker.pick();
        final TimePicker picker2 = TimePicker.newInstance();
        picker2.pick();
        logger.info("启动makeAlarmWaveOffsetDataJob");
//        ThreadInvoker invoker = new ThreadInvoker(new ThreadInvokerEvent() {
//            @Override
//            public Object doEvent() {
                try{
                    List<PmsSystemDatabase> databaseList = dataBaseService.loadAlldatabases();
                    int pageSize = 50;
                    picker.stepSecond(true);
                    logger.info("进入makeAlarmWaveOffsetDataJob线程！");
                    String logText = "";
                    List<PmsBusiWaveFile> waveFileList;
                    PmsBusiWaveFile waveFile = null;
                    //以告警时间为准，前后多少毫秒扫描拉出值记录，如果能找到则匹配最相近的一笔记录。
                    for(PmsSystemDatabase database : databaseList) {
                        try {
                            int dbOffset = 0;
                            //第1步：查询波形文件：已提取过拉出值重合次数的
                            waveFileList = waveFileService.listFileForTempOffset(database.getMonitorStationDatabase(),fileTypeId,dbOffset,pageSize);
                            logger.info(String.format("执行makeAlarmWaveOffsetDataJob线程！查找已提取过拉出值重合次数的波形文件:%d个",waveFileList.size()));
                            picker2.stepSecond(true);
                            //第2步：遍历波形文件
                            while(null != waveFileList && !waveFileList.isEmpty()) {
                                logger.info(String.format("进入makeAlarmWaveOffsetDataJob线程,加载波形文件到临时表[%s][%s]行！",database.getTrainNoAlias(),waveFileList.size()));
                                for(int i = 0;i < waveFileList.size();i++) {
                                    waveFile = waveFileList.get(i);
                                    //第3步：合并告警记录和动态拉出值记录。
                                    doMapAlarmAndOffsetValue(database,waveFile,waveFile.getFileLogId());
                                }
                                //翻页查询波形文件
                                dbOffset += pageSize;
                                try{
                                    waveFileList = waveFileService.listFileForTempOffset(database.getMonitorStationDatabase(), fileTypeId, dbOffset, pageSize);
                                }catch (Exception e){
                                    logger.error("listFileForTempOffset："+e);
                                    lock.unlock();
                                    continue;
                                }
                            }
                        } catch(Exception e) {
                            if(null != waveFile) {
                                logText = String.format("执行makeAlarmWaveOffsetDataJob出错！处理文件：%d,用时%s秒",waveFile.getFileLogId(), picker.stepSecond(true));
                                logger.error(logText);
                                logger.error("执行wave offset file job出错！"+e);
                            }
                            MakeAlarmWaveOffsetJob.IS_RUNNING = false;
                            lock.unlock();
                            break;
                        }
                        logger.info(String.format("makeAlarmWaveOffsetDataJob线程读完[%s]设备,加载波形文件[%s][%s]行！",database.getExt2(),database.getTrainNoAlias(),waveFileList.size()));
                    }
                    logger.info(String.format("执行完makeAlarmWaveOffsetDataJob线程！用时%s秒",picker.stepSecond(true)));
                    MakeAlarmWaveOffsetJob.IS_RUNNING = false;
                }catch (Exception e){
                    MakeAlarmWaveOffsetJob.IS_RUNNING = false;
                    logger.error("MakeAlarmWaveOffsetJob.doEvent方法发生异常:"+e);
                    lock.unlock();
                }
//                return null;
//            }
//        });
//        invoker.start();
        lock.unlock();
    }
    private void doMapAlarmAndOffsetValue(PmsSystemDatabase database, PmsBusiWaveFile waveFile,Long fileLogId) {
        try{
            TimePicker picker = TimePicker.newInstance();
            picker.pick();
            String logText = null;
            int rowCount = 0;
            //读取动态拉出值插入数据库中。
            rowCount = waveFileService.readFileToWaveOffsetDataTemp(database.getMonitorStationDatabase(),waveFile);
            logText = String.format("执行makeAlarmWaveOffsetDataJob线程！处理完文件：%d,插入%d行,用时%s秒,%d",waveFile.getFileLogId(),rowCount, picker.stepSecond(true),fileLogId);
            logger.info(logText);
            List<Map> maps = alarmWaveOffsetService.selectTemp(database.getMonitorStationDatabase(),fileLogId);
            if(null != maps || maps.size() != 0){
                //合并告警记录和动态拉出值到中间表。
                rowCount = alarmWaveOffsetService.makeDataFromAlarmLog(database.getMonitorStationDatabase(),fileLogId);
                logText = String.format("执行makeAlarmWaveOffsetDataJob线程！合并告警记录和动态拉出值到中间表%d行,用时%s秒,%d",rowCount, picker.stepSecond(true),fileLogId);
                logger.info(logText);
            }else{
                String format = String.format("%d记录为空-跳过,用时%s秒",fileLogId,picker.stepSecond(true));
                logger.info(format);
            }
        }catch (Exception e){
            logger.error("MakeAlarmWaveOffsetJob.doMapAlarmAndOffsetValue方法发生异常"+e);
        }
    }
}
