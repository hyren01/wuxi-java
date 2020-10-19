package com.jn.primiary.standard.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jn.primiary.metadata.entity.BaseResponse;
import com.jn.primiary.metadata.entity.Model;
import com.jn.primiary.metadata.entity.ModelField;
import com.jn.primiary.metadata.entity.ResultCode;
import com.jn.primiary.standard.entity.FieldCheckResult;
import com.jn.primiary.standard.service.StandardService;

/**
 * 字典的管理控制器
 *
 * @author wld
 * @Date 2019-07-22 
 */
@Controller
@RequestMapping("stdglprj/check")
public class CheckController {
	
	
	@Autowired
	StandardService standardservice;
	
	
	/**
	 * @Title: contrast
	 * @Description: 对比模型与标准 
	 * @return @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/contrast/{modelId}", method = RequestMethod.POST)
	public BaseResponse<FieldCheckResult> contrast(@PathVariable("modelId") String modelId, 
			@RequestBody String stdId) {

		BaseResponse<FieldCheckResult> response = new BaseResponse<FieldCheckResult>();
		
		List<FieldCheckResult> listresult=standardservice.contraststd(modelId,stdId);
		
		response.setResultCode(ResultCode.RESULT_SUCCESS);
		response.setData(listresult);
		response.setMessage("对比标准结果成功");
		
		
		return response;
	}
	
	
	/**
	 * @Title: contrastbyModel
	 * @Description: 对比模型与标准 
	 * @return @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/contrastbyModel/{stdId}", method = RequestMethod.POST)
	public BaseResponse<FieldCheckResult> contrastbyModel(@PathVariable("stdId") String stdId, 
			@RequestBody Model model) {

		BaseResponse<FieldCheckResult> response = new BaseResponse<FieldCheckResult>();
		
		List<FieldCheckResult> listresult=standardservice.contraststdbyModel(model,stdId);
		
		response.setResultCode(ResultCode.RESULT_SUCCESS);
		response.setData(listresult);
		response.setMessage("对比标准结果成功");
		
		
		return response;
	}
	
	/**
	 * @Title: contrastbyField
	 * @Description: 对比模型与标准 
	 * @return @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/contrastbyField/{stdId}", method = RequestMethod.POST)
	public BaseResponse<FieldCheckResult> contrastbyField(@PathVariable("stdId") String stdId,
			@RequestBody List<ModelField> fields) {

		BaseResponse<FieldCheckResult> response = new BaseResponse<FieldCheckResult>();
		
		//List<FieldCheckResult> listresult=standardservice.contrastbyField(fields,stdId);
		
		response.setResultCode(ResultCode.RESULT_SUCCESS);
		//response.setData(listresult);
		response.setMessage("对比标准结果成功");
		
		
		return response;
	}
	
	
	/**
	 * @Title: save
	 * @Description: 存储核标结果数据
	 * @return @throws
	 */
	@ResponseBody
	@Transactional
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public BaseResponse<String> save( @RequestBody List<FieldCheckResult> listresult) {
		BaseResponse<String> response = new BaseResponse<String>();
		
		for(FieldCheckResult result: listresult)
			standardservice.saveCheckResult(result);
		response.setResultCode(ResultCode.RESULT_SUCCESS);
		response.setMessage("存储核标结果数据成功");
		
		return response;
	}
}
