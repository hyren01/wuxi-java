package com.jn.primiary.beyondsoft.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jn.primiary.beyondsoft.aspect.CheckLoginEndPoint;
import com.jn.primiary.beyondsoft.entity.*;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jn.primiary.beyondsoft.service.DataQualityService;
import com.jn.primiary.metadata.entity.BaseResponse;
import com.jn.primiary.metadata.entity.ResultCode;

import net.sf.json.JSONObject;


/**
 * 数据质量检测Controller
 */
//@Controller
//>@RequestMapping("stdglprj/dataQuality")
public class DataQualityController{
	@Autowired
	private DataQualityService service;
	
	/**
	 * @Title: dataQuality
	 * @Description: 访问接口获取所有数据库信息，未启用 
	 */
	@ResponseBody
	@RequestMapping(value = "/allDBs")
	public BaseResponse<DBInfo> getAllDBs() {
		BaseResponse<DBInfo> response = new BaseResponse<DBInfo>();
		
		List<DBInfo> dbInfos;
		try {
			dbInfos = service.getAllDBInfos();
			response.setResultCode(ResultCode.RESULT_SUCCESS);
			response.setData(dbInfos);
			response.setMessage("获取所有数据库信息成功");
		} catch (ClientProtocolException e) {
			response.setResultCode(ResultCode.RESULT_ERROR);
			response.setMessage("获取所有数据库信息失败 : " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			response.setResultCode(ResultCode.RESULT_ERROR);
			response.setMessage("获取所有数据库信息失败 : " + e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * @Title: dataQuality
	 * @Description: 通过数据库连接信息获取数据库所有表信息 
	 */
	@ResponseBody
	@RequestMapping(value = "/allTablesByDBInfo")
	public BaseResponse<String> getAllTablesByDBInfo(@RequestParam(required=true) String jsonCondition) {
		BaseResponse<String> response = new BaseResponse<String>();
		
		JSONObject conditionObject = JSONObject.fromObject(jsonCondition);
		
		String driver = conditionObject.getString("dbdriver");
		String url = conditionObject.getString("url");
		String insName = conditionObject.getString("insname");
		String userName = conditionObject.getString("user");
		String passWord = conditionObject.getString("password");
		
		List<String> allTables = service.getAllTables(driver, url, insName, userName, passWord);
		
		response.setResultCode(ResultCode.RESULT_SUCCESS);
		response.setData(allTables);
		response.setMessage("根据数据库信息获取所有表信息成功");
		return response;
	}
	
	/**
	 * @Title: dataQuality
	 * @Description: 根据表名获取表中所有字段信息
	 */
	@ResponseBody
	@RequestMapping(value = "/allFieldsByTableName")
	public BaseResponse<FieldInfo> getFieldsByTableName(@RequestParam(required=true) String jsonCondition) {
		BaseResponse<FieldInfo> response = new BaseResponse<FieldInfo>();
		
		JSONObject conditionObject = JSONObject.fromObject(jsonCondition);
		
		String driver = conditionObject.getString("dbdriver");
		String url = conditionObject.getString("url");
		String insName = conditionObject.getString("insname");
		String userName = conditionObject.getString("user");
		String passWord = conditionObject.getString("password");
		String tableName = conditionObject.getString("tableName");
		
		List<FieldInfo> metaFieldsByModelID = service.getFieldsByTableName(driver, url, insName, userName, passWord, tableName);
		response.setResultCode(ResultCode.RESULT_SUCCESS);
		response.setData(metaFieldsByModelID);
		response.setMessage("根据表名获取字段信息成功");
		return response;
	}
	
	/**
	 * @Title: dataQuality
	 * @Description: 若用户界面选择的检测类型是码值检测，则调用该接口从码表中获取码值项中文名和码值项代码
	 */
	@ResponseBody
	@RequestMapping(value = "/tbStdCode")
	public BaseResponse<CodeInfo> getTbStdCode() {
		BaseResponse<CodeInfo> response = new BaseResponse<CodeInfo>();
		
		List<CodeInfo> tbStdCodeInfo = service.getTbStdCode();
		response.setResultCode(ResultCode.RESULT_SUCCESS);
		response.setData(tbStdCodeInfo);
		response.setMessage("获取码值项信息成功");
		return response;
	}
	
	/**
	 * @Title: dataQuality
	 * @Description: 用户选择完码值项之后，根据码值项编码获取所有码值
	 */
	@ResponseBody
	@RequestMapping(value = "/tbStdCodeInfo")
	public BaseResponse<CodeInfo> getTbStdCodeInfo(@RequestParam(required=true) String codeEname) {
		BaseResponse<CodeInfo> response = new BaseResponse<CodeInfo>();
		
		List<CodeInfo> tbStdCodeInfo = service.getTbStdCodeInfo(codeEname);
		response.setResultCode(ResultCode.RESULT_SUCCESS);
		response.setData(tbStdCodeInfo);
		response.setMessage("获取码值详细信息成功");
		return response;
	}
	
	/**
	 * @Title: dataQuality
	 * @Description: 根据数据库类型获取url返回前端,暂时未启用
	 */
	@ResponseBody
	@RequestMapping(value = "/JDBCURL")
	public BaseResponse<JSONObject> getJDBCURL(@RequestParam(required=true) String dbType) {
		BaseResponse<JSONObject> response = new BaseResponse<JSONObject>();
		
		List<JSONObject> returnList = new ArrayList<>();
		JSONObject jdbcURL = service.getJDBCURL(dbType);
		returnList.add(jdbcURL);
		response.setResultCode(ResultCode.RESULT_SUCCESS);
		response.setData(returnList);
		response.setMessage("根据数据库类型获取JDBCURL成功");
		return response;
	}
	
	/**
	 * @Title: dataQuality
	 * @Description: 测试连接
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/testConnection")
	public BaseResponse testConnection(@RequestParam(required=true) String jsonCondition){
		BaseResponse response = new BaseResponse();
		
		JSONObject conditionObject = JSONObject.fromObject(jsonCondition);
		
		String driver = conditionObject.getString("dbdriver");
		String url = conditionObject.getString("url");
		String insName = conditionObject.getString("insname");
		String userName = conditionObject.getString("user");
		String passWord = conditionObject.getString("password");
		
		String result = service.testConnection(driver, url, insName, userName, passWord);
		
		if("SUCCESS".equals(result)) {
			response.setResultCode(ResultCode.RESULT_SUCCESS);
			response.setMessage("测试连接成功");
		}else {
			response.setResultCode(ResultCode.RESULT_ERROR);
			response.setMessage("测试连接失败");
		}
		
		return response;
	}
	
	/**
	 * @Title: dataQuality
	 * @Description: 获取数据库驱动
	 */
	@ResponseBody
	@RequestMapping(value = "/getDBDriver")
	public BaseResponse<DBTypeMapping> getDBDriver() {
		BaseResponse<DBTypeMapping> response = new BaseResponse<DBTypeMapping>();
		List<DBTypeMapping> dbDriver = service.getDBDriver();
		response.setResultCode(ResultCode.RESULT_SUCCESS);
		response.setData(dbDriver);
		response.setMessage("获取数据库驱动成功");
		return response;
	}
}
