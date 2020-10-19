package com.jn.primiary.metadata.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jn.primiary.beyondsoft.util.HttpClientUtil;
import com.jn.primiary.beyondsoft.util.UserContextUtil;
import com.jn.primiary.commons.StringUtils;
import com.jn.primiary.metadata.entity.ModelCategory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jn.primiary.metadata.entity.Model;
import com.jn.primiary.metadata.entity.ModelField;
import com.jn.primiary.metadata.dao.ModelFieldRepository;
import com.jn.primiary.metadata.dao.ModelRepository;


/**
 * @ClassName: ModelService
 * @Description: 模型的服务类
 * @author gongyf
 * @date 2018年5月8日 下午15:58:19
 */
@Service
public class ModelService {
	@Autowired
	private ModelRepository modelRepository;
	
	@Autowired
	private ModelFieldRepository modelFieldRepository;


	private Logger logger = Logger.getLogger(ModelService.class);
	
	
	/**
	 * @Title: getAllModelInfo 
	 * @Description:根据模型ID取得该模型详细信息（正式联调的方法）
	 * @param
	 * @return 
	 * @throws
	 */
	public List<Model> getAllModelInfo(String getAllRegisteredModels_url) throws Exception {
		Map<String,String> headerMap = new HashMap<>();
		headerMap.put("scistor-token",UserContextUtil.getToken());
		logger.info("模型列表url："+getAllRegisteredModels_url);
		HttpClientUtil httputil = new HttpClientUtil();
		String result = httputil.get(getAllRegisteredModels_url,headerMap);
		JSONObject obj = JSON.parseObject(result);
		JSONArray array = (JSONArray) obj.get("data");
		logger.info("模型列表array："+array.toString());
		List<Model> moudleList = array.parseArray(array.toString(), Model.class);
		return moudleList;
	}

	/**
	 * @Title: getModelInfo 
	 * @Description:根据模型ID取得该模型详细信息（正式联调的方法）
	 * @return 
	 * @throws
	 */
	public Model getModelInfo(String modelId,String getModelInfo_url) throws Exception {
		Map<String,String> headerMap = new HashMap<>();
		headerMap.put("scistor-token",UserContextUtil.getToken());
		String url =  getModelInfo_url+modelId;
		logger.info("模型字段url："+url);
		HttpClientUtil httputil = new HttpClientUtil();
		String result = httputil.get(url, headerMap);
		logger.info("get返回的结果："+result);
		JSONObject obj = JSON.parseObject(result);

		JSONArray dataarray = new JSONArray();
		Model model = new Model();
		if(obj.get("data") != null && obj.getString("data") != "null"){
			dataarray = obj.getJSONArray("data");
			logger.info("模型字段array："+dataarray.toString());
			model = JSON.parseObject(dataarray.get(0).toString(), Model.class);
		}

		return model;
	}
	
	
	public List<Model> getAllTableModel(){
		List<Model> listmodel=modelRepository.findTableModel();
		for(Model model:listmodel) {
			List<ModelField> modelFields = getModelFields(model.getId());
			model.setFields(modelFields);
		}
		
		return listmodel;
	}
	
	
	// 获取指定模型所有字段信息
		private List<ModelField> getModelFields(String modelId) {
			List<ModelField> modelFields = modelFieldRepository.findByModelId(modelId);
			
			adjustFieldsPrection(modelFields);
			
			return modelFields;
		}
		
		public void adjustFieldsPrection(List<ModelField> modelFields) {
			for (ModelField field : modelFields) {
				if (field.getType().equals("单精度") || field.getType().equals("双精度")) {
					// 设置缺省最大长度
					if(field.getMaxsize() <= 0) {
						field.setMaxsize(11);
					}
					
					// 如果精度大于最大长度，强制将精度调整为最大长度-1
					if (field.getPrecition() >= field.getMaxsize()) {
						field.setPrecition(field.getMaxsize() - 1);
						if(field.getPrecition() > 10) {
							field.setPrecition(10);
						}
					}
					
					// 设置小数点位数
					if (field.getPrecition() > 0 && field.getScale() > field.getPrecition()) {
						field.setScale(field.getPrecition() - 1);
					} else if (field.getPrecition() == 0) {
						field.setScale(0);
					}
				}
			}
			return;
		}

	/**
	 * 本机测试用
	 * @param modelId
	 * @return
	 */
	public Model getModelInfo(String modelId) {
			//测试用
			// 获取模型信息
			Model model = modelRepository.findbymodelid(modelId);
			if(null == model) {
				return null;
			}
			// 获取该模型的字段信息
			List<ModelField> modelFields = getModelFields(modelId);
			model.setFields(modelFields);

			return model;
		}

	/**
	 * 本机测试用
	 * @return
	 */
	public List<Model> getAllModelInfo() {
		 //获取模型信息
		List<Model> models = modelRepository.findAllOrderByCreateTimeDesc();
		 //获取该模型的字段信息
		if(models.size() > 0) {
			for(Model model : models) {
				List<ModelField> modelFields = modelFieldRepository.findByModelId(model.getId());
				model.getFields().addAll(modelFields);
			}
		}
		return models;
	}


	public List<ModelCategory> getAllModelCategory(String getAllModelCategory_url) throws Exception {
		Map<String,String> headerMap = new HashMap<>();
		headerMap.put("scistor-token",UserContextUtil.getToken());
		HttpClientUtil httputil = new HttpClientUtil();
		List<ModelCategory> moudleList = new ArrayList<>();
		if(StringUtils.isEmpty(getAllModelCategory_url)) return moudleList;

		logger.info(getAllModelCategory_url+"--------------"+UserContextUtil.getToken());
		String result = httputil.get(getAllModelCategory_url,headerMap);
		logger.info("模型目录列表arrayaaaaaa："+result);
		JSONObject obj = JSON.parseObject(result);

		JSONArray array = (JSONArray) obj.get("data");
		logger.info("模型目录列表array："+array.toString());
		moudleList = array.parseArray(array.toString(), ModelCategory.class);

		return moudleList;
	}

	public List<Model> getTableByCategoryId(String getTableByCategoryId_url) throws Exception {
		Map<String,String> headerMap = new HashMap<>();
		headerMap.put("scistor-token",UserContextUtil.getToken());
		HttpClientUtil httputil = new HttpClientUtil();
		String result = httputil.get(getTableByCategoryId_url,headerMap);
		JSONObject obj = JSON.parseObject(result);
		logger.info("obj:"+obj);
		JSONArray array = (JSONArray) obj.get("data");
		logger.info("模型列表array："+array.toString());
		List<Model> moudleList = array.parseArray(array.toString(), Model.class);
		return moudleList;
	}


}