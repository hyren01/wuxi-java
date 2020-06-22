package com.jn.primiary.beyondsoft.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jn.primiary.beyondsoft.dao.DBTypeMappingRepository;
import com.jn.primiary.beyondsoft.dao.DQCodeRepository;
import com.jn.primiary.beyondsoft.entity.DBInfo;
import com.jn.primiary.beyondsoft.entity.DBTypeMapping;
import com.jn.primiary.beyondsoft.entity.DateBaseType;
import com.jn.primiary.beyondsoft.entity.FieldInfo;
import com.jn.primiary.beyondsoft.entity.CodeInfo;
import com.jn.primiary.beyondsoft.util.ConnUtil;
import com.jn.primiary.metadata.entity.RegisterStatus;

import net.sf.json.JSONObject;

/**
 * 数据质量检测Service
 * */
//@Service
//@Transactional
public class DataQualityService {
	
	@Autowired
	private DQCodeRepository codeDao;
	
	@Autowired
	private DBTypeMappingRepository typeDao;
	
	//获取配置文件参数
	@Value("${webapp.interfaceUrl}") 
	private String interfaceUrl;
	
	//调用接口编码
	private static final String charset = "UTF-8";
	
	/**
	 * 调接口获取所有数据库信息，未启用
	 * */
	public List<DBInfo> getAllDBInfos() throws ClientProtocolException, IOException{
		//调接口
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String result = "";
		HttpGet httpGet = new HttpGet(interfaceUrl);
		CloseableHttpResponse response = httpclient.execute(httpGet);
		if (response != null) {
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				result = EntityUtils.toString(resEntity, charset);
			}
		}
		//关闭连接
		response.close();
		httpclient.close();
		//处理返回结果
		JSONObject obj = JSONObject.fromObject(result);
		String resultCode = obj.getString("resultCode");
		if("RESULT_SUCCESS".equals(resultCode)) {
			//调用成功，并且成功拿到结果
			String dbArray = obj.getString("data");
			ObjectMapper mapper = new ObjectMapper();
			List<DBInfo> list = mapper.readValue(dbArray,mapper.getTypeFactory().constructCollectionType(List.class, DBInfo.class));
			return list;
		}else {
			throw new IllegalStateException("接口响应状态错误");
		}
	}
	
	/**
	 * 根据数据库信息获取所有表信息
	 * */
	public List<String> getAllTables(String driver, String url, String insName, String userName, String passWord){
		if(driver == null || driver.isEmpty()) {
			throw new RuntimeException("数据库驱动不能为空");
		}
		if(url == null || url.isEmpty()) {
			throw new RuntimeException("数据库访问url不能为空");
		}
		if(userName == null || userName.isEmpty()) {
			throw new RuntimeException("数据库访问用户名不能为空");
		}
		if(passWord == null || passWord.isEmpty()) {
			throw new RuntimeException("数据库访问密码不能为空");
		}
		if(insName == null || insName.isEmpty()) {
			throw new RuntimeException("数据库名称不能为空");
		}
		List<String> allTables = new ArrayList<>();
		Connection conn = ConnUtil.getConnection(driver, url, insName, userName, passWord);
		if(conn != null) {
			allTables = ConnUtil.getAllTables(conn);
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return allTables;
	}
	
	/**
	 *根据表名获取表中所有字段信息
	 * */
	public List<FieldInfo> getFieldsByTableName(String driver, String url, String insName, String userName, String passWord, String tableName){
		if(driver == null || driver.isEmpty()) {
			throw new RuntimeException("数据库驱动不能为空");
		}
		if(url == null || url.isEmpty()) {
			throw new RuntimeException("数据库访问url不能为空");
		}
		if(insName == null || insName.isEmpty()) {
			throw new RuntimeException("数据库名不能为空");
		}
		if(userName == null || userName.isEmpty()) {
			throw new RuntimeException("数据库访问用户名不能为空");
		}
		if(passWord == null || passWord.isEmpty()) {
			throw new RuntimeException("数据库访问密码不能为空");
		}
		if(tableName == null || tableName.isEmpty()) {
			throw new RuntimeException("表名不能为空");
		}
		List<FieldInfo> allFidlds = new ArrayList<>();
		Connection conn = ConnUtil.getConnection(driver, url, insName, userName, passWord);
		if(conn != null) {
			allFidlds = ConnUtil.getAllFidldsByCondition(conn, tableName);
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return allFidlds;
	}
	
	/**
	 * 获取码值检测码值项中文名和码值类型
	 * */
	public List<CodeInfo> getTbStdCode(){
		return codeDao.getValueType(RegisterStatus.REGISTERED);
	}
	
	/**
	 * 通过代码值类型获取码值详细信息
	 * */
	public List<CodeInfo> getTbStdCodeInfo(String codeEname){
		return codeDao.getCodeValueByType(RegisterStatus.REGISTERED, codeEname);
	}
	
	/**
	 * 根据数据库类型获取JDBCURL，暂时未启用
	 * */
	public JSONObject getJDBCURL(String type) {
		if(type == null || type.isEmpty()) {
			throw new RuntimeException("数据库类型不能为空");
		}
		JSONObject jdbcObj = new JSONObject();
		if(DateBaseType.MYSQL.getDesc().equalsIgnoreCase(type)) {
			jdbcObj.put("jdbcPrefix", "jdbc:mysql://");
			jdbcObj.put("jdbcIp", ':');
			jdbcObj.put("jdbcPort", '/');
			jdbcObj.put("jdbcBase", "?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull");
		}else if(DateBaseType.GBASE.getDesc().equals(type)) {
			jdbcObj.put("jdbcPrefix", "jdbc:gbase://");
			jdbcObj.put("jdbcIp", ':');
			jdbcObj.put("jdbcPort", '/');
			jdbcObj.put("jdbcBase", "");
		}else if(DateBaseType.ODPS.getDesc().equals(type)) {
			jdbcObj.put("jdbcPrefix", "jdbc:odps:tcp://");
			jdbcObj.put("jdbcIp", ':');
			jdbcObj.put("jdbcPort", '/');
			jdbcObj.put("jdbcBase", "");
		}else if(DateBaseType.HIVE.getDesc().equals(type)) {
			jdbcObj.put("jdbcPrefix", "jdbc:hive:tcp://");
			jdbcObj.put("jdbcIp", ':');
			jdbcObj.put("jdbcPort", '/');
			jdbcObj.put("jdbcBase", "");
		}else {
			throw new RuntimeException("系统目前不支持该数据库");
		}
		jdbcObj.put("jdbcDriver", getJDBCDriver(type));
		return jdbcObj;
	}
	
	/**
	 * 测试连接
	 * */
	public String testConnection(String driver, String url, String insName, String userName, String passWord) {
		if(driver == null || driver.isEmpty()) {
			throw new RuntimeException("数据库驱动不能为空");
		}
		if(url == null || url.isEmpty()) {
			throw new RuntimeException("数据库访问url不能为空");
		}
		if(insName == null || insName.isEmpty()) {
			throw new RuntimeException("数据库名不能为空");
		}
		if(userName == null || userName.isEmpty()) {
			throw new RuntimeException("数据库访问用户名不能为空");
		}
		if(passWord == null || passWord.isEmpty()) {
			throw new RuntimeException("数据库访问密码不能为空");
		}
		Connection conn = ConnUtil.getConnection(driver, url, insName, userName, passWord);
		if(conn != null) {
			try {
				conn.close();
				return "SUCCESS";
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "FAILED";
	}
	
	/**
	 * 根据数据库类型获得数据库驱动，暂时未启用
	 * */
	private String getJDBCDriver(String dbtype) {
		String jdbcDriver = "";
		if(DateBaseType.MYSQL.getDesc().equalsIgnoreCase(dbtype)) {
			jdbcDriver = "com.mysql.jdbc.Driver";
		}else if(DateBaseType.GBASE.getDesc().equals(dbtype)) {
			jdbcDriver = "com.gbase.jdbc.Driver";
		}else if(DateBaseType.ODPS.getDesc().equals(dbtype)) {
			jdbcDriver = "com.aliyun.odps.jdbc.OdpsDriver";
		}else {
			jdbcDriver = "org.apache.hive.jdbc.HiveDriver";
		}
		return jdbcDriver;
	}
	
	/**
	 * 获取数据库连接driver
	 * */
	public List<DBTypeMapping> getDBDriver(){
		return typeDao.findAll();
	}
}
