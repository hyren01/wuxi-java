package com.jn.primiary.dataQuality.service;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jn.primiary.beyondsoft.service.CodeInfoService;
import com.jn.primiary.dataQuality.dao.DataCheckRepository;
import com.jn.primiary.dataQuality.entity.ConfigProperties;
import com.jn.primiary.dataQuality.entity.CsvReaderBean;
import com.jn.primiary.dataQuality.entity.DatabaseInfo;
import com.jn.primiary.dataQuality.entity.RunStatus;
import com.jn.primiary.dataQuality.entity.TbMetaQuality;
import com.jn.primiary.metadata.entity.UserInfo;
import com.jn.primiary.metadata.utils.CommonUtil;
import com.jn.primiary.metadata.utils.StringUtils;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;

@Service
public class DataCheckService {
	
	private static final String LINESEPARATOR = "\n";
	private static final String CSV_SUFFIX = ".csv";
	
	private static final String CSV_ENCODING = "UTF-8";
	
	@Autowired
	private ConfigProperties config;
	
	@Autowired
	private DataCheckRepository dataQualityRository;
	
	public List<TbMetaQuality> getQualityCheckInfo() {
		
		return dataQualityRository.findAll();
	}


	public TbMetaQuality saveQualityCheckInfo(TbMetaQuality qualityInfo, UserInfo userInfo) {
		
		if(StringUtils.isEmpty(qualityInfo.getExceptionSql())) {
			throw new IllegalArgumentException("非法参数，数据质量检测时数据异常总数SQL不能为空");
		}
		if(StringUtils.isEmpty(qualityInfo.getDetailSql())) {
			throw new IllegalArgumentException("非法参数，数据质量检测时数据明细数据SQL不能为空");
		}
		
		String qualityId = CommonUtil.getUUID();
		qualityInfo.setId(qualityId);
		qualityInfo.setCheckStatus(RunStatus.RUNNING.getCode());
		qualityInfo.setCreateUser(CodeInfoService.MANAGER_ACCOUNT);
		qualityInfo.setCreateDate(CommonUtil.getLocalDateByChar8());
		qualityInfo.setCreateTime(CommonUtil.getLocalTimeByChar6());
		
		dataQualityRository.save(qualityInfo);
		
		return qualityInfo;
	}
	
	public void delQualityInfo(String qualityId) {
		if(StringUtils.isEmpty(qualityId)) {
			throw new IllegalArgumentException("非法参数，删除检测历史时，主键为空" + qualityId);
		}
		
		TbMetaQuality quality = dataQualityRository.findQualityInfoById(qualityId);
		dataQualityRository.delete(quality);

		File file = new File(quality.getDetailPath());
		if(file.exists()) {
			file.delete();
		}
	}
	
	
	public void runQualityCheck(TbMetaQuality qualityInfo) {
		
		String[] header = qualityInfo.getHeader();
		DatabaseInfo db = qualityInfo.getDb();
		//TODO 缺连接池
		try(Connection conn = DriverManager.getConnection(db.getUrl(), db.getUserName(), db.getPassword());
						Statement st = conn.createStatement();) {
			Class.forName(db.getDriverName());
			
//			ResultSet totalNum = st.executeQuery(qualityInfo.getTotalSql());
//			List<BigDecimal> index2 = (List<BigDecimal>)totalNum.getArray("index2");
//			qualityInfo.setTotalNum(index2.get(0));
			
			ResultSet exceptionNum = st.executeQuery(qualityInfo.getExceptionSql());
			while(exceptionNum.next()) {
				qualityInfo.setExceptionNum(exceptionNum.getBigDecimal("index1"));
			}
			
			ResultSet resultDetail = st.executeQuery(qualityInfo.getDetailSql());
			String csvPath = config.getCsvpath() + File.separatorChar + qualityInfo.getId() + CSV_SUFFIX;
			qualityInfo.setDetailPath(csvPath);
			
			resultSet2CsvFile(csvPath, true, header, resultDetail);
		}
		catch(ClassNotFoundException | SQLException e) {
			
			qualityInfo.setCheckStatus(RunStatus.FAILD.getCode());
			updateQualityCheckStatusAndResult(qualityInfo);
			
			throw new IllegalStateException("运行质量检测时发生异常：" + e.getMessage());
		}
		
		qualityInfo.setCheckStatus(RunStatus.SUCCESS.getCode());
		updateQualityCheckStatusAndResult(qualityInfo);
	}
	
	
	public CsvReaderBean getDetailData(String qualityId) {
		
		TbMetaQuality qualityInfo = dataQualityRository.findQualityInfoById(qualityId);
		if(null == qualityInfo) {
			throw new IllegalArgumentException("非法参数，获取明细数据时，无法根据主键获取数据" + qualityId);
		}
		if(StringUtils.isEmpty(qualityInfo.getDetailPath())) {
			throw new IllegalArgumentException("非法参数，获取明细数据时，明细文件路径为空" + qualityId);
		}

		// The settings object provides many configuration options
		CsvParserSettings parserSettings = new CsvParserSettings();
		parserSettings.getFormat().setLineSeparator(LINESEPARATOR);

		//You can configure the parser to automatically detect what line separator sequence is in the input
		parserSettings.setLineSeparatorDetectionEnabled(true);

		// Let's consider the first parsed row as the headers of each column in the file.
		parserSettings.setHeaderExtractionEnabled(true);

		// creates a parser instance with the given settings
		CsvParser parser = new CsvParser(parserSettings);

		parser.beginParsing(new File(qualityInfo.getDetailPath()), CSV_ENCODING);

		String[] headers = parser.getRecordMetadata().headers();
		List<String[]> rows = new ArrayList<>(config.getCsvnum());
		int count = 0;
		String[] row;
		while ((row = parser.parseNext()) != null) {
			rows.add(row);
			count++;
			if(count >= config.getCsvnum()) {
				break;
			}
		}
		
		CsvReaderBean csvReader = new CsvReaderBean();
		csvReader.setHeaders(headers);
		csvReader.setRows(rows);
		
		return csvReader;
	}
	
	
	private void updateQualityCheckStatusAndResult(TbMetaQuality qualityInfo) {
		
		dataQualityRository.updateQualityCheckStatusAndResult(qualityInfo.getId(), qualityInfo.getCheckStatus(), 
						qualityInfo.getExceptionNum(), qualityInfo.getDetailPath());
	}
	
	
	private void resultSet2CsvFile(String csvPath, boolean isHeader, String[] header, ResultSet resultDetail) throws SQLException {
		
		File file = new File(csvPath);
		CsvWriterSettings settings = new CsvWriterSettings();
		settings.getFormat().setLineSeparator(LINESEPARATOR);
		CsvWriter writer = new CsvWriter(file, CSV_ENCODING, settings);
		
		if(isHeader) {
			writer.writeHeaders(header);
		}
		
		int headerNum = header.length;
		List<Object> row = new ArrayList<>(headerNum);
		while(resultDetail.next()) {
			
			for(int i = 0; i < headerNum; i++) {
				row.add(resultDetail.getObject(header[i]));
			}
			
			writer.writeRow(row);
			row.clear();
		}
		
		writer.close();
	}
	
}

