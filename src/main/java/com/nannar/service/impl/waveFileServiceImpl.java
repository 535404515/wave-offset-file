package com.nannar.service.impl;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.nannar.dao.PmsBusiWaveFileMapper;
import com.nannar.dao.PmsBusiWaveOffsetDataMapper;
import com.nannar.dao.PmsBusiWaveOffsetDataTempMapper;
import com.nannar.entity.*;
import com.nannar.service.PmsBusiWaveFileService;
import com.nannar.utils.PmsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Caiyuhui
 * @date 2020/5/15
 * @time 9:56
 */
@Service("waveFileServiceImpl")
public class waveFileServiceImpl implements PmsBusiWaveFileService {
    private static String LOG_TEXT_PATTERN = "fileLogId=%d,samples=%d,用时：%s秒";
    private final PmsBusiWaveFileMapper dao;
    private final PmsBusiWaveOffsetDataMapper waveOffsetDao;
    private final PmsBusiWaveOffsetDataTempMapper waveOffsetTempDao;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    public waveFileServiceImpl(PmsBusiWaveFileMapper mapper, PmsBusiWaveOffsetDataMapper waveOffsetDao, PmsBusiWaveOffsetDataTempMapper waveOffsetTempDao) {
        this.dao = mapper;
        this.waveOffsetDao = waveOffsetDao;
        this.waveOffsetTempDao = waveOffsetTempDao;
    }
    @Override
    public List<PmsBusiWaveFile> listFileForTempOffset(String dbName, Integer fileTypeId, Integer offset, Integer pageSize) {
        return dao.listFileForTempOffset(dbName,fileTypeId,offset,pageSize);
    }

    @Override
    public List<PmsBusiWaveFile> listFileForRead(String dbName, Integer fileTypeId, Integer pageSize) {
        return dao.listFileForRead(dbName,fileTypeId,pageSize);
    }

    @Override
    public Integer readWaveOffsetFile(String dbName, PmsBusiWaveFile waveFile, Map<String, String> stationMap) {
        int rowCount = 0;
        WaveOffsetFileJson waveOffsetFileJson = null;
        TimePicker picker = null;
        picker = TimePicker.newInstance();
        picker.pick();
        if(null == waveFile.getFileState()) {
            waveFile.setFileState(PmsBusiWaveFile.FILE_STATE_READY);
        }
        waveOffsetFileJson = PmsUtil.readWaveOffsetJsonFromFile(waveFile,dao,dbName);
        if(null == waveOffsetFileJson) {
            logger.error(String.format("读取文件不成功，更新文件状态：fileLogId=%d,fileState=%d",waveFile.getFileLogId(),waveFile.getFileState()));
            rowCount = dao.updateByPrimaryKeySelective(waveFile,dbName);
            return rowCount;
        }
        waveFile.setOffsetSamples(waveOffsetFileJson.getSamples());
        logger.info(String.format("开始统计拉出值位置出现次数：fileLogId=%d,fileState=%d,data==null?%s",waveFile.getFileLogId(),waveFile.getFileState(),(waveOffsetFileJson.getData()==null)+""));
        Map<Integer,Integer> offsetCountMap = Maps.newHashMap();
        try {
            if(null != waveOffsetFileJson.getData()) {
                logger.info(String.format("原始数据点个数：fileLogId=%d,fileState=%d,size=%d",waveFile.getFileLogId(),waveFile.getFileState(),waveOffsetFileJson.getData().size()));
            }
            offsetCountMap = PmsUtil.getOffsetCountMap(waveFile,waveOffsetFileJson);
            logger.info(String.format("完成统计拉出值位置出现次数，记录数：fileLogId=%d,size=%d",waveFile.getFileLogId(),offsetCountMap.size()));
        } catch(Exception e) {
            logger.error(String.format("统计拉出值出错：fileLogId=%d,fileState=%d",waveFile.getFileLogId(),waveFile.getFileState()));
            e.printStackTrace();
        }
        if(!offsetCountMap.isEmpty()) {
            logger.info(String.format("开始转换Map为List：fileLogId=%d,size=%d",waveFile.getFileLogId(),offsetCountMap.size()));
            List<PmsBusiWaveOffsetData> records = PmsUtil.changeOffsetMapToList(offsetCountMap,waveFile.getFileLogId());
            logger.info(String.format("批量插入拉出值！fileLogId=%d,size:%d",waveFile.getFileLogId(),records.size()));
            try {
                rowCount += waveOffsetDao.insertSelectiveBatch(dbName,records);
                logger.info(String.format("批量插入成功！fileLogId=%d,size:%d",waveFile.getFileLogId(),rowCount));
            } catch(Exception e) {
                logger.error(String.format("批量插入失败！fileLogId=%d,size:%d",waveFile.getFileLogId(),rowCount));
                logger.error("错误状态为:"+e);
            }
            waveFile.setFileState(PmsBusiWaveFile.FILE_STATE_READ_FINISHED);
            String logText = String.format(LOG_TEXT_PATTERN,
                    waveFile.getFileLogId(),
                    waveFile.getOffsetSamples(),
                    picker.allSecond());
            logger.info(logText);
        }
        logger.info(String.format("开始更新文件状态：fileLogId=%d,fileState=%d",waveFile.getFileLogId(),waveFile.getFileState()));
        try {
            rowCount += dao.updateByPrimaryKeySelective(waveFile,dbName);
            logger.info(String.format("成功更新文件状态：fileLogId=%d,fileState=%d",waveFile.getFileLogId(),waveFile.getFileState()));
        } catch(Exception e) {
            logger.error(String.format("更新文件状态失败！fileLogId=%d,fileState=%d",waveFile.getFileLogId(),waveFile.getFileState()));
            logger.error("错误信息:"+e);
        }
        logger.info(String.format("文件入库成功，影响行数：%d",rowCount));
        return rowCount;
    }

    @Override
    public Integer readFileToWaveOffsetDataTemp(String dbName, PmsBusiWaveFile waveFile) {
        int rowCount = 0;
        WaveOffsetFileJson waveOffsetFileJson = null;
        TimePicker picker = null;
        picker = TimePicker.newInstance();
        picker.pick();
        waveOffsetFileJson = PmsUtil.readWaveOffsetJsonFromFile(waveFile,dao,dbName);
        if(null == waveOffsetFileJson) {
            logger.error(String.format("读取文件不成功，更新文件状态：fileLogId=%d,fileState=%d",waveFile.getFileLogId(),waveFile.getFileState()));
            waveFile.setFileState(PmsBusiWaveFile.FILE_STATE_LOSTED);
            dao.updateByPrimaryKeySelective(waveFile,dbName);
            return 0;
        }
        logger.info(String.format("开始提取拉出值点数据：fileLogId=%d,fileState=%d,data==null?%s",waveFile.getFileLogId(),waveFile.getFileState(),(waveOffsetFileJson.getData()==null)+""));
        List<PmsBusiWaveOffsetDataTemp> tempDataList = null;
        if(null != waveOffsetFileJson.getData()) {
            logger.info(String.format("原始数据点个数：fileLogId=%d,fileState=%d,size=%d",waveFile.getFileLogId(),waveFile.getFileState(),waveOffsetFileJson.getData().size()));
        }
        tempDataList = PmsUtil.changeOffsetJsonToTempList(waveOffsetFileJson,waveFile.getFileLogId());
        logger.info(String.format("提取数据后点个数：fileLogId=%d,fileState=%d,size=%d",waveFile.getFileLogId(),waveFile.getFileState(),tempDataList.size()));
        List<PmsBusiWaveOffsetDataTemp> subList = null;
        Gson gson = new Gson();
        if(!tempDataList.isEmpty()) {
            logger.info(String.format("批量插入拉出值临时表！fileLogId=%d,size:%d",waveFile.getFileLogId(),tempDataList.size()));
            try {
                int step = 5000;
                int fromIndex = 0;
                int toIndex = fromIndex + step ;
                //zhanghg 20200228 18:00 解决生产问题。subList下标越界。
                if(toIndex > tempDataList.size()) {
                    toIndex = tempDataList.size();
                }
                subList = tempDataList.subList(fromIndex,toIndex);
                while(null != subList && !subList.isEmpty()) {
                    try{
                        rowCount += waveOffsetTempDao.insertSelectiveBatch(dbName,subList);
                        fromIndex += step;
                        toIndex += step;
                        //zhanghg 20191022 11:18 做边界控制，否则抛下标越界错误。
                        if(toIndex > tempDataList.size()) {
                            toIndex = tempDataList.size();
                        }
                        if(fromIndex < tempDataList.size()) {
                            subList = tempDataList.subList(fromIndex,toIndex);
                        }else {
                            subList = null;
                        }
                    }catch (Exception e){
                        logger.error("waveFileServiceImpl,While循环发生异常"+e);
                        continue;
                    }
                }
                logger.info(String.format("批量插入成功！fileLogId=%d,size:%d",waveFile.getFileLogId(),rowCount));
            } catch(Exception e) {
                logger.error(String.format("批量插入失败！fileLogId=%d,size:%d",waveFile.getFileLogId(),rowCount));
                logger.error("错误数据："+e);
                if(null != subList) {
                    String errData = gson.toJson(subList);
                    logger.error("错误数据："+errData);
                }
            }
            String logText = String.format(LOG_TEXT_PATTERN,
                    waveFile.getFileLogId(),
                    waveFile.getOffsetSamples(),
                    picker.allSecond());
            logger.info(logText);
        }
        logger.info(String.format("文件入库临时表成功，影响行数：%d",rowCount));
        return rowCount;
    }
}
