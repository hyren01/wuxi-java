package com.jn.primiary.metadata.controller;

import java.util.ArrayList;
import java.util.List;

import com.jn.primiary.beyondsoft.aspect.CheckLoginEndPoint;
import com.jn.primiary.metadata.entity.ModelCategory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jn.primiary.metadata.entity.BaseResponse;
import com.jn.primiary.metadata.entity.Model;
import com.jn.primiary.metadata.entity.ResultCode;
import com.jn.primiary.metadata.service.ModelService;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: ModelController
 * @Description: 模型Rest接口
 * @author gongyf
 * @date 2018年5月7日 上午11:22:51
 */

@Controller
@RequestMapping("/model")
public class ModelController implements CheckLoginEndPoint {
	
	@Autowired
	ModelService modelService;
	@Value("${isJoinWith56}") //是否对接56接口
	private boolean isJoinWith56;
	@Value("${getAllRegisteredModels_url}") //模型列表
	private String getAllRegisteredModels_url;
	@Value("${getModelInfo_url}")   //模型字段
	private String getModelInfo_url;
	@Value("${getAllModelCategory_url}")   //所有模型目录
	private String getAllModelCategory_url;
	@Value("${getTableByCategoryId_url}")   //根据目录id获取模型信息
	private String getTableByCategoryId_url;
	@Value("${getModuleDBbyMoudleId_url}")   //根据模型id，获取 模型落在的那些库
	private String getModuleDBbyMoudleId_url;
	@Value("${getDataSourceById_url}")   //根据数据源id，获取数据源信息
	private String getDataSourceById_url;


	private Logger logger = Logger.getLogger(ModelController.class);
	

	

	/**
	 * @Title: getModelInfo 
	 * @Description: 根据模型Id获取模型信息 
	 * @param modelId 模型ID 
	 * @return 
	 * @throws
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/getModelInfo/{modelId:.+}", method = RequestMethod.GET)
	public BaseResponse<Model> getModelInfo(@PathVariable("modelId") String modelId,HttpServletRequest request) throws Exception {
		logger.info("开始调用获取模型信息接口：/model/getModelInfo");
		BaseResponse<Model> response = new BaseResponse<Model>();
		if (StringUtils.isEmpty(modelId)) {
			response.setResultCode(ResultCode.RESULT_BAD_REQUEST);
			logger.debug("resultCode:" + ResultCode.RESULT_BAD_REQUEST);
			return response;
		}

		Model model = null;
		if(!isJoinWith56){
			//测试用
			model = modelService.getModelInfo(modelId);
		}else{
			//正式联调用
			 model = modelService.getModelInfo(modelId,getModelInfo_url);
		}

		if(null == model) {
			response.setResultCode(ResultCode.RESULT_NOT_EXIST);
			response.setMessage("未找到相应模型信息");
			return response;
		}
		List<Model> models = new ArrayList<Model>();
		models.add(model);
		response.setResultCode(ResultCode.RESULT_SUCCESS);
		response.setData(models);
		response.setMessage("获取模型信息成功");

		return response;
	}

	/**
	 * 本机测试用
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllModelInfoInner", method = RequestMethod.GET)
	public BaseResponse<Model> getAllModelInfoInner(HttpServletRequest request) throws Exception {
		logger.info("开始调用获取模型信息接口：getAllRegisteredModels_url");
		BaseResponse<Model> response = new BaseResponse<Model>();
		List<Model> models = null ;
		if(!isJoinWith56){
			//测试用
			models = modelService.getAllModelInfo();
		}else{
			//正式联调用
			models = modelService.getAllModelInfo(getAllRegisteredModels_url);
		}

		response.setResultCode(ResultCode.RESULT_SUCCESS);
		response.setData(models);
		response.setMessage("获取模型信息成功");
		return response;
	}


	/**
	 * 获取所有模型的目录
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllModelCategory", method = RequestMethod.GET)
	public BaseResponse<ModelCategory> getAllModelCategory(HttpServletRequest request) throws Exception {
		BaseResponse<ModelCategory> response = new BaseResponse<ModelCategory>();
		List<ModelCategory> resultlist = null;
		if(!isJoinWith56){

		}else{
			//正式联调
			resultlist = modelService.getAllModelCategory(getAllModelCategory_url);
		}

		response.setResultCode(ResultCode.RESULT_SUCCESS);
		response.setData(resultlist);
		response.setMessage("获取模型信息成功");
		return response;
	}


	/**
	 * 根据目录id获取模型信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTableByCategoryId/{id}", method = RequestMethod.GET)
	public BaseResponse<Model> getTableByCategoryId(@PathVariable String id,HttpServletRequest request) throws Exception {
		BaseResponse<Model> response = new BaseResponse<Model>();
		List<Model> resultlist = null;
		if(!isJoinWith56){

		}else{
			//正式联调
			String url = getTableByCategoryId_url+id;
			resultlist = modelService.getTableByCategoryId(url);
		}

		response.setResultCode(ResultCode.RESULT_SUCCESS);
		response.setData(resultlist);
		response.setMessage("获取模型信息成功");
		return response;
	}



	

}
