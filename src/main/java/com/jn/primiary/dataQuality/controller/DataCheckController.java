package com.jn.primiary.dataQuality.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jn.primiary.dataQuality.entity.CsvReaderBean;
import com.jn.primiary.dataQuality.entity.TbMetaQuality;
import com.jn.primiary.dataQuality.service.DataCheckService;
import com.jn.primiary.metadata.entity.BaseResponse;
import com.jn.primiary.metadata.entity.ResultCode;
import com.jn.primiary.metadata.entity.UserInfo;

@RestController
@RequestMapping("stdglprj/dataCheck")
public class DataCheckController {

	@Autowired
	DataCheckService dataCheckService;
	
	@ResponseBody
	@RequestMapping(value = "/getQualityInfo", method = RequestMethod.GET)
	public BaseResponse<TbMetaQuality> getQualityInfo() {
		
		List<TbMetaQuality> qualityInfos = dataCheckService.getQualityCheckInfo();
		
		BaseResponse<TbMetaQuality> response = new BaseResponse<TbMetaQuality>();
		response.setData(qualityInfos);
		response.setResultCode(ResultCode.RESULT_SUCCESS);
		response.setMessage("获取数据质量检测信息成功");
		
		return response;
	}
	
	@ResponseBody
	@RequestMapping(value = "/delQualityInfo/{qualityId:.+}", method = RequestMethod.GET)
	public BaseResponse<CsvReaderBean> delQualityInfo(@PathVariable String qualityId) {
		
		dataCheckService.delQualityInfo(qualityId);
		
		BaseResponse<CsvReaderBean> response = new BaseResponse<CsvReaderBean>();
		response.setResultCode(ResultCode.RESULT_SUCCESS);
		response.setMessage("数据质量删除检测历史成功");
		
		return response;
	}
	
	@ResponseBody
	@RequestMapping(value = "/runQualityCheck", method = RequestMethod.POST)
	public BaseResponse<TbMetaQuality> runQualityCheck(@RequestBody TbMetaQuality qualityInfo) {
		
		UserInfo userInfo = null;	//TODO 缺用户信息
		
		qualityInfo = dataCheckService.saveQualityCheckInfo(qualityInfo, userInfo);
		dataCheckService.runQualityCheck(qualityInfo);
		
		BaseResponse<TbMetaQuality> response = new BaseResponse<TbMetaQuality>();
		response.setData(Arrays.asList(qualityInfo));
		response.setResultCode(ResultCode.RESULT_SUCCESS);
		response.setMessage("数据质量检测任务执行完成");
		
		return response;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getDetailData/{qualityId:.+}", method = RequestMethod.GET)
	public BaseResponse<CsvReaderBean> getDetailData(@PathVariable String qualityId) {
		
		CsvReaderBean csvReader = dataCheckService.getDetailData(qualityId);
		
		BaseResponse<CsvReaderBean> response = new BaseResponse<CsvReaderBean>();
		response.setResultCode(ResultCode.RESULT_SUCCESS);
		response.setData(Arrays.asList(csvReader));
		response.setMessage("数据质量获取检测异常明细数据成功");
		
		return response;
	}
}

