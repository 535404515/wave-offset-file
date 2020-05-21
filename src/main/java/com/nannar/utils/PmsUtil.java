package com.nannar.utils;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.nannar.dao.PmsBusiWaveFileMapper;
import com.nannar.entity.*;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @typename      : PmsUtil
 * @description   : (用一段文字描述此类的作用)
 * @author        : hangui_zhang
 * @create by     : 2018年12月12日 下午2:42:15
 * @version       : V1.0  
 */
public class PmsUtil {
	private static final String OFFSET_ID_PATTERN = "{fileLogId}-{pointIndex}";
	private static final String OFFSET_ID_MARK1 = "{fileLogId}";
	private static final String OFFSET_ID_MARK2 = "{pointIndex}";
	private static final Logger logger = LoggerFactory.getLogger(PmsUtil.class);
	public static final List<PmsBusiWaveOffsetDataTemp> changeOffsetJsonToTempList(WaveOffsetFileJson waveOffsetFileJson,Long fileLogId){
		Timestamp offsetTime = null;
		//默认开1.5万个长度的数组，避免不断扩容影响性能。
		List<PmsBusiWaveOffsetDataTemp> result = new ArrayList<PmsBusiWaveOffsetDataTemp>(15000);
		Map<Integer,Integer> offsetCountMap = Maps.newHashMap();
		PmsBusiWaveOffsetDataTemp temp = null;
		//zhanghg 20191022 距离单位为（米），4位小数已经足够
		int digitLength = 4;
		BigDecimal dec = null;
		Double offsetValueOri = null;
		Integer distanceInt = null;
		Integer offset1Int = null;
		Integer offset2Int = null;
		Integer offset3Int = null;
		Integer offset4Int = null;
		double ignoreNum = 99999.0;
		Integer filePointIndex = 1;
		String offsetId = "";
		for(int i = 0;i<waveOffsetFileJson.getData().size();i++) {
			WaveOffsetData data = waveOffsetFileJson.getData().get(i);
			//zhanghg 20191022 测试程序时发现原始距离值有18位小数的。数据库字段最多13位导致溢出
			distanceInt = data.getDistanceInt();
			offsetTime = data.getOffsetTime(waveOffsetFileJson.getCreateTime());
			offset1Int = data.getOffset1Int();
			offset2Int = data.getOffset2Int();
			offset3Int = data.getOffset3Int();
			offset4Int = data.getOffset4Int();
			if(null != data.getOffset1Int() && data.getOffset1() < ignoreNum) {
				dec = new BigDecimal(data.getOffset1());
				dec = dec.setScale(digitLength,BigDecimal.ROUND_HALF_UP);
				offsetValueOri = dec.doubleValue();
				offsetId = OFFSET_ID_PATTERN.replace(OFFSET_ID_MARK1,fileLogId.toString()).replace(OFFSET_ID_MARK2,filePointIndex.toString());
				filePointIndex ++;
				temp = PmsBusiWaveOffsetDataTemp.newInstance(offsetId,fileLogId,distanceInt,offsetTime,offset1Int,offsetValueOri);
				result.add(temp);
			}
			if(null != data.getOffset2Int() && data.getOffset2() < ignoreNum) {
				dec = new BigDecimal(data.getOffset2());
				dec = dec.setScale(digitLength,BigDecimal.ROUND_HALF_UP);
				offsetValueOri = dec.doubleValue();
				offsetId = OFFSET_ID_PATTERN.replace(OFFSET_ID_MARK1,fileLogId.toString()).replace(OFFSET_ID_MARK2,filePointIndex.toString());
				filePointIndex ++;
				temp = PmsBusiWaveOffsetDataTemp.newInstance(offsetId,fileLogId,distanceInt,offsetTime,offset2Int,offsetValueOri);
				result.add(temp);
			}
			if(null != data.getOffset3Int() && data.getOffset3() < ignoreNum) {
				dec = new BigDecimal(data.getOffset3());
				dec = dec.setScale(digitLength,BigDecimal.ROUND_HALF_UP);
				offsetValueOri = dec.doubleValue();
				offsetId = OFFSET_ID_PATTERN.replace(OFFSET_ID_MARK1,fileLogId.toString()).replace(OFFSET_ID_MARK2,filePointIndex.toString());
				filePointIndex ++;
				temp = PmsBusiWaveOffsetDataTemp.newInstance(offsetId,fileLogId,distanceInt,offsetTime,offset3Int,offsetValueOri);
				result.add(temp);
			}
			if(null != data.getOffset4Int() && data.getOffset4() < ignoreNum) {
				dec = new BigDecimal(data.getOffset4());
				dec = dec.setScale(digitLength,BigDecimal.ROUND_HALF_UP);
				offsetValueOri = dec.doubleValue();
				offsetId = OFFSET_ID_PATTERN.replace(OFFSET_ID_MARK1,fileLogId.toString()).replace(OFFSET_ID_MARK2,filePointIndex.toString());
				filePointIndex ++;
				temp = PmsBusiWaveOffsetDataTemp.newInstance(offsetId,fileLogId,distanceInt,offsetTime,offset4Int,offsetValueOri);
				result.add(temp);
			}
		}
		return result;
	}
	/**
	 * 将拉出值出现次数的Map转换成List数据库对象
	 * @author    : hangui_zhang
	 * @create by : 2019-10-18 16:00:15
	 * @param offsetCountMap
	 * @param fileLogId
	 * @return
	 */
	public static final List<PmsBusiWaveOffsetData> changeOffsetMapToList(Map<Integer,Integer> offsetCountMap, Long fileLogId){
		List<PmsBusiWaveOffsetData> list = new ArrayList<PmsBusiWaveOffsetData>();
		String offsetId = "";
		Map.Entry<Integer,Integer> next = null;
		if(!offsetCountMap.isEmpty()) {
			Iterator<Map.Entry<Integer,Integer>> iterator = offsetCountMap.entrySet().iterator();
			while(iterator.hasNext()) {
				next = iterator.next();
				offsetId = OFFSET_ID_PATTERN.replace(OFFSET_ID_MARK1,fileLogId.toString()).replace(OFFSET_ID_MARK2,next.getKey().toString());
				PmsBusiWaveOffsetData offsetData = PmsBusiWaveOffsetData.newInstance(offsetId,fileLogId,next.getKey(),next.getValue());
				list.add(offsetData);
			}
		}
		return list;
	}
	/**
	 * 计算波形文件中每个拉出值出现多少次
	 * @author    : hangui_zhang
	 * @create by : 2019-10-18 15:54:50
	 * @param waveFile
	 * @param waveOffsetFileJson
	 * @return
	 */
	public static final Map<Integer,Integer> getOffsetCountMap(PmsBusiWaveFile waveFile,WaveOffsetFileJson waveOffsetFileJson) {
		Timestamp offsetTime = null;
		Timestamp startTime = null;
		Timestamp endTime = null;
		startTime = waveOffsetFileJson.getCreateTime();
		endTime = waveOffsetFileJson.getCreateTime();
		Map<Integer,Integer> offsetCountMap = Maps.newHashMap();
		for(WaveOffsetData data : waveOffsetFileJson.getData()) {
			offsetTime = data.getOffsetTime(waveOffsetFileJson.getCreateTime());
			startTime = DateUtils.min(offsetTime,startTime);
			endTime = DateUtils.max(offsetTime,endTime);
			Integer offsetValue = data.getOffset1Int();
			if(null != offsetValue) {
				Integer offsetCount = offsetCountMap.get(offsetValue);
				if(null == offsetCount) {
					offsetCount = 0;
				}
				offsetCount += 1;
				offsetCountMap.put(offsetValue,offsetCount);
			}

		}
		waveFile.setOffsetStartTime(startTime);
		waveFile.setOffsetEndTime(endTime);
		return offsetCountMap;
	}
	public static boolean isEmpty(Object[] arr) {
		int zero = 0;
		if(null == arr || arr.length == zero) {
			return true;
		}
		return false;
	}
	public static File getWaveJsonFile(PmsBusiWaveFile waveFile,String datFileKey,String defaultDatFileName) {
		String tempDatFileName = "wire_height_stagger_wave.dat";
		String datFileName = null;
		if(!StringUtils.isEmpty(tempDatFileName)) {
			datFileName = tempDatFileName.trim().replace("\t", "");
		}
		if(StringUtils.isEmpty(datFileName)) {
			datFileName = defaultDatFileName;
		}
		File trempFile = null;
		String localDir = waveFile.getLocalDir();
		if(!StringUtils.isEmpty(localDir)) {
			String fileName = localDir + FileTransfer.WINDOWS_FILE_SPLITER1 + datFileName;
			fileName = FileTransfer.formatPath(fileName);
			trempFile = new File(fileName);
		}
		return trempFile;
	}
	/**
	 * 读取动态拉出值波形文件并转换成Java对象。
	 * @author    : hangui_zhang
	 * @create by : 2019-10-18 15:49:21
	 * @param waveFile
	 * @return
	 */
	public static final WaveOffsetFileJson readWaveOffsetJsonFromFile(PmsBusiWaveFile waveFile, PmsBusiWaveFileMapper dao,String dbName) {
		WaveOffsetFileJson result = null;
		String defaultDatFileName = "wire_height_stagger_wave.dat";
		String datFileKey = "file.name.catenary.offset.json";
		File tempFile = PmsUtil.getWaveJsonFile(waveFile,datFileKey,defaultDatFileName);
		logger.info(String.format("尝试读取文件%s",tempFile.getAbsolutePath()));
		if(null == tempFile || !tempFile.exists()) {
			logger.error(String.format("文件不存在%s",tempFile.getAbsolutePath()));
			waveFile.setFileState(PmsBusiWaveFile.FILE_STATE_LOSTED);
			dao.updateByPrimaryKeySelective(waveFile,dbName);
		}else {
			Gson gson = new Gson();
			String json = null;
			try {
				json = FileUtils.readFileToString(tempFile);
				result = new WaveOffsetFileJson();
				Map fromJson = gson.fromJson(json, Map.class);
				if(null != fromJson && !fromJson.isEmpty()) {
					String creatTimeStr = MapUtils.getString(fromJson,"createTime");
					Timestamp createTime = DateUtils.toTimestamp(creatTimeStr,DateUtils.FORMAT_FULLTIME);
					result.setCreateTime(createTime);
					result.setCurrentStation(MapUtils.getInteger(fromJson,"current_station"));
					result.setNextStation(MapUtils.getInteger(fromJson,"next_station"));
					result.setSamples(MapUtils.getInteger(fromJson,"samples"));
					List<Map> dataList = (List<Map>)fromJson.get("data");
					if(null != dataList && !dataList.isEmpty()) {
						for(Map map : dataList) {
							Double distance = MapUtils.getDouble(map,"distance");
							String timeStr = MapUtils.getString(map,"time");
							Object obj = map.get("staggerValue");
							List<Double> offsets = (List)obj;
							WaveOffsetData offsetData = WaveOffsetData.newInstance(distance,offsets,timeStr);
							result.getData().add(offsetData);
						}
					}
				}
				logger.info(String.format("转换Json对象成功！%s",tempFile.getAbsolutePath()));
			} catch(IOException e) {
				result = null;
				logger.error(String.format("文件解析失败！%s",tempFile.getAbsolutePath()));
				waveFile.setFileState(PmsBusiWaveFile.FILE_STATE_FAILED);
			}
		}
		return result;
	}
        
}

