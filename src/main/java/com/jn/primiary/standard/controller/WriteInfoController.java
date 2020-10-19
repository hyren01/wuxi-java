package com.jn.primiary.standard.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jn.primiary.metadata.entity.BaseResponse;
import com.jn.primiary.metadata.entity.ResultCode;
import com.jn.primiary.standard.service.WriteInfoService;

/**
 * 写入中文信息的控制管理器
 *
 * @author wld
 * @Date 2019-07-22 
 */
@Controller
@RequestMapping("stdglprj/writeinfo")
public class WriteInfoController {
	
	
	@Autowired
	WriteInfoService writeInfoService;
	
	
	/**
	 * @Title: 
	 * @Description: 将复合型数据的中文信息写入相应的值
	 * @return @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/writeobjectcninfo", method = RequestMethod.GET)
	public BaseResponse<Object> writeobjectcninfo() {

		BaseResponse<Object> response = new BaseResponse<Object>();
		
		writeInfoService.writeobjectcninfo();
		
		response.setResultCode(ResultCode.RESULT_SUCCESS);
		
		response.setMessage("写入信息成功");
		
		
		return response;
	}
	
	
	/**
	 * @Title: 
	 * @Description: 强行将标准字段数据的中文信息写入相应的模型字段
	 * @return @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/writefieldcninfo", method = RequestMethod.GET)
	public BaseResponse<Object> writefieldcninfo() {

		BaseResponse<Object> response = new BaseResponse<Object>();
		
		writeInfoService.writefieldcninfo();
		
		response.setResultCode(ResultCode.RESULT_SUCCESS);
		
		response.setMessage("写入信息成功");
		
		
		return response;
	}
	
	
}
