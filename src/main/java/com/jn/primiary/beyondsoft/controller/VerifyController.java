package com.jn.primiary.beyondsoft.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jn.primiary.beyondsoft.entity.FunctionType;
import com.jn.primiary.beyondsoft.entity.CodeInfo;
import com.jn.primiary.beyondsoft.service.CodeInfoService;
import com.jn.primiary.metadata.entity.AuditResult;
import com.jn.primiary.metadata.entity.BaseResponse;
import com.jn.primiary.metadata.entity.BatchRequest;
import com.jn.primiary.metadata.entity.RegisterType;
import com.jn.primiary.metadata.entity.ResultCode;
import com.jn.primiary.standard.entity.Standard;
import com.jn.primiary.standard.entity.StandardField;
import com.jn.primiary.standard.entity.StandardRequest;
import com.jn.primiary.standard.service.StandardModifyService;
import com.jn.primiary.standard.service.StandardService;

import net.sf.json.JSONObject;

/**
 * 用于实现标准审核及码表审核
 * @author gzz
 *
 */
@Controller
@RequestMapping("stdglprj/verify")
public class VerifyController{
	
	@Autowired
	StandardService standardservice;
	
	@Autowired
	StandardModifyService standardModifyService;
	
	@Autowired
	CodeInfoService codeInfoService;
	
 	/**
	 * @Title: agree 
	 * @Description: 审核通过请求
	 * @return @throws
	 */
	@ResponseBody
	@Transactional
	@RequestMapping(value = "/agree/{fun_type}/{requestId}", method = RequestMethod.POST)
	public BaseResponse<String> agree(@PathVariable("fun_type") int funType, @PathVariable("requestId") String requestId, @RequestBody String message) {

		BaseResponse<String> response = new BaseResponse<String>();
		
		if(FunctionType.STANDARD.getCode() == funType) {
			
			StandardRequest request = standardModifyService.findById(requestId);
			if (null == request) {
				response.setResultCode(ResultCode.RESULT_ERROR);
				response.setMessage("未找到请求信息,请求ID:" + requestId);
				return response;
			}
			
			return agreeStdRequest(request, message);
			
		}else if (FunctionType.CODEINFO.getCode() == funType) {
			
			CodeInfo codeInfo = new CodeInfo();
			codeInfo.setId(requestId);
			codeInfo.setAuthRemark(message);
			//codeInfo.setIsAuth(RegisterStatus.REGISTERED);
			//codeInfoService.verifyCodeById(codeInfo);
			
			response.setResultCode(ResultCode.RESULT_SUCCESS);
			response.setMessage("码表审批完成");
			return response;
			
		}else {
			
			response.setResultCode(ResultCode.RESULT_ERROR);
			response.setMessage("功能类型错误:" + funType);
			return response;
		}
		
		
	}
	
	
	/**
	 * @Title: batchAgree 
	 * @Description: 批量审核通过
	 * @return @throws
	 */
	@ResponseBody
	@Transactional
	@RequestMapping(value = "/batchAgree", method = RequestMethod.POST)
	public BaseResponse<String> batchAgree(@RequestBody BatchRequest batchRequest) {

		BaseResponse<String> response = new BaseResponse<String>();
		
		List<String> requestIdList = batchRequest.getRequestIdList();
		
		List<String> responseList = new ArrayList<String>();
		int auditSuccessCount = 0;
		for(String requestId : requestIdList) {
			BaseResponse<String> singleResponse = agree(FunctionType.ALL.getCode() , requestId, batchRequest.getMessage());
			if(singleResponse.getResultCode().equals(ResultCode.RESULT_SUCCESS)) {
				auditSuccessCount ++;
				responseList.add("请求ID="+requestId+"已通过审核.");
			}
			else {
				responseList.add(singleResponse.getMessage());
			}
		}
		
		if(auditSuccessCount == requestIdList.size()) {
			response.setResultCode(ResultCode.RESULT_SUCCESS);
			response.setMessage("批量审核完成");
		}
		else if(auditSuccessCount == 0) {
			response.setResultCode(ResultCode.RESULT_ERROR);
			response.setMessage("批量审核失败");			
		}
		else {
			response.setResultCode(ResultCode.RESULT_ERROR);
			response.setMessage("部分审核未通过");
		}
		response.setData(responseList);
		
		return response;
	}
	
	/**
	 * @Title: reject 
	 * @Description: 审核拒绝请求
	 * @return @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/reject/{fun_type}/{requestId}", method = RequestMethod.POST)
	@Transactional
	public BaseResponse<String> reject(@PathVariable("fun_type") int funType, @PathVariable("requestId") String requestId, @RequestBody String message) {

		BaseResponse<String> response = new BaseResponse<String>();
		
		if(FunctionType.STANDARD.getCode() == funType) {
			
			StandardRequest request = standardModifyService.findById(requestId);
			
			if (null == request) {
				response.setResultCode(ResultCode.RESULT_ERROR);
				response.setMessage("未找到请求信息,请求ID:" + requestId);
				return response;
			}
			
			return rejectStdRequest(request,message);
		}else if (FunctionType.CODEINFO.getCode() == funType) {
			
			CodeInfo codeInfo = new CodeInfo();
			codeInfo.setId(requestId);
			codeInfo.setAuthRemark(message);
			//codeInfo.setIsAuth(RegisterStatus.REJECTED);
			//codeInfoService.verifyCodeById(codeInfo);
			
			response.setResultCode(ResultCode.RESULT_SUCCESS);
			response.setMessage("码表审批完成");
			return response;
		}else {
			
			response.setResultCode(ResultCode.RESULT_ERROR);
			response.setMessage("功能类型错误:" + funType);
			return response;
		}
		
		
	}
	
	private BaseResponse<String> rejectStdRequest(StandardRequest request,String message){

		BaseResponse<String> response = new BaseResponse<String>();
		
		String stdId = request.getResourceId();
		if (StringUtils.isEmpty(stdId)) {
			response.setResultCode(ResultCode.RESULT_ERROR);
			response.setMessage("请求ID为:"+request.getId()+"的标准id为空.");
			return response;
		}
		
//		UserInfo auditUser = null;//(UserInfo) SecurityUtils.getSubject().getPrincipal();
//		if(null != auditUser) {
//			request.setAuditPerson(auditUser.getUserName());
//		}
		request.setAuditTime(Calendar.getInstance().getTime());
		request.setAuditResult(AuditResult.REJECT);
		request.setAuditMessage(message);
		// 将标准信息保存到请求表内
		//request.setResourceContent(JSONObject.fromObject(stand).toString());		
		standardModifyService.save(request);
		
		response.setResultCode(ResultCode.RESULT_SUCCESS);
		response.setMessage("已拒绝请求ID:" + request.getId());
		
		return response;
		
	}
	
	private BaseResponse<String> agreeStdRequest(StandardRequest request, String message){
		
		Standard stand = null;
		BaseResponse<String> response = new BaseResponse<String>();
		
		String stdId = request.getResourceId();
		if (StringUtils.isEmpty(stdId)) {
			response.setResultCode(ResultCode.RESULT_ERROR);
			response.setMessage("请求ID为:"+request.getId()+"的标准id为空.");
			return response;
		}
		
		if(request.getType().equals(RegisterType.DELETE)) {//删除请求
			stand = standardservice.getStandardInfoById(stdId);
			standardservice.deleteStd(stand);
		}
		else if(request.getType().equals(RegisterType.MODIFY)) {//修改请求
			stand = getModelFromRequestContent(request);
			standardservice.saveStd(stand);
		}else if(request.getType().equals(RegisterType.ADD)) {
			stand = getModelFromRequestContent(request);
			standardservice.saveStd(stand);
		}
		
//		UserInfo auditUser = null;//(UserInfo) SecurityUtils.getSubject().getPrincipal();
//		if(null != auditUser) {
//			request.setAuditPerson(auditUser.getUserName());
//		}
		request.setAuditTime(Calendar.getInstance().getTime());
		request.setAuditResult(AuditResult.AGREE);
		request.setAuditMessage(message);
		// 将标准信息保存到请求表内
		request.setResourceContent(JSONObject.fromObject(stand).toString());		
		standardModifyService.save(request);
		
		response.setResultCode(ResultCode.RESULT_SUCCESS);
		response.setMessage("已审核通过请求ID:" + request.getId());
		
		return response;
	}
	
	private Standard getModelFromRequestContent(StandardRequest request) {
		
		// 提取信息
		String content = request.getResourceContent();
		JSONObject modifyJsonObject = JSONObject.fromObject(content);
		//Map classMap = new HashMap();
		Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
		classMap.put("fields",StandardField.class);
		Standard modifyStandInfo = (Standard) JSONObject.toBean(modifyJsonObject, Standard.class, classMap);
		
		return modifyStandInfo;
	}
	
}
