package com.jn.primiary.standard.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 标准的管理控制器
 *
 * @author wld
 * @Date 2019-05-07 
 */
@Controller
@RequestMapping("stdglprj/standard1")
public class StandardController {
	
//	@Autowired
//	StandardService standardservice;
//
//	@Autowired
//	StandardModifyService standardModifyService;
//
//	private Logger logger = Logger.getLogger(StandardController.class);
//
//
//	//获取全部标准信息
//	@RequestMapping(value = "/getAllStd", method = RequestMethod.GET)
//    @ResponseBody
//    public BaseResponse<Standard> getAllStd() {
//    	BaseResponse<Standard> response = new BaseResponse<Standard>();
//    	List<Standard> liststand = standardservice.getAllStandardInfo();
//    	response.setResultCode(ResultCode.RESULT_SUCCESS);
//		response.setData(liststand);
//		response.setMessage("获取信息成功");
//		return response;
//
//    }
//
//
//	//获取全部标准目录
//	@RequestMapping(value = "/getAllStandardCategorys", method = RequestMethod.GET)
//	@ResponseBody
//	public BaseResponse<StandardCategory> getAllStandardCategorys() {
//		BaseResponse<StandardCategory> response = new BaseResponse<StandardCategory>();
//
//		List<StandardCategory> listcategory = standardservice.getAllStandardCategorys();
//		response.setResultCode(ResultCode.RESULT_SUCCESS);
//		response.setData(listcategory);
//
//
//		return response;
//	}
//
//
//	//根据父目录id获取子目录
//	@RequestMapping(value = "/getSubStandardCategorys/{parentId}", method = RequestMethod.GET)
//	@ResponseBody
//	public BaseResponse<StandardCategory> getSubStandardCategorys(@PathVariable("parentId") String parentId) {
//		BaseResponse<StandardCategory> response = new BaseResponse<StandardCategory>();
//
//		List<StandardCategory> listcategory =standardservice.getSubStandardCategorys(parentId);
//		response.setResultCode(ResultCode.RESULT_SUCCESS);
//		response.setData(listcategory);
//		response.setMessage("获取子目录成功");
//
//		return response;
//	}
//
//
//
//
//	//根据目录id获取标准模型信息
//    @RequestMapping(value = "/getStandardsByCategoryId/{categoryId}", method = RequestMethod.GET)
//    @ResponseBody
//    public BaseResponse<Standard> getStandardsByCategoryId(@PathVariable("categoryId") String categoryId) {
//	    BaseResponse<Standard> response = new BaseResponse<Standard>();
//
//	    List<Standard> liststand = standardservice.getStandardsByCategoryId(categoryId);
//		response.setResultCode(ResultCode.RESULT_SUCCESS);
//		response.setData(liststand);
//		response.setMessage("获取信息成功");
//
//		return response;
//    }
//
//    //根据标准id获取标准信息
//    @RequestMapping(value = "/getStandardInfo/{standardId}", method = RequestMethod.GET)
//    @ResponseBody
//    public BaseResponse<Standard> getStandardInfo(@PathVariable("standardId") String standardId) {
//	    BaseResponse<Standard> response = new BaseResponse<Standard>();
//
//	    List<Standard> liststand =new ArrayList<Standard>();
//
//	    Standard stand= standardservice.getStandardInfoById(standardId);
//	    liststand.add(stand);
//		response.setResultCode(ResultCode.RESULT_SUCCESS);
//		response.setData(liststand);
//		response.setMessage("获取信息成功");
//
//		return response;
//    }
//
//
//	    //根据stdmodel的id获取filed list
//	    @RequestMapping(value = "/getFiledsByModelid/{modelid}", method = RequestMethod.GET)
//	    @ResponseBody
//	    public BaseResponse<StandardField> getFiledsByModelid(@PathVariable("modelid") String modelid) {
//	    	BaseResponse<StandardField> response = new BaseResponse<StandardField>();
//	    	List<StandardField> listfield=standardservice.getFiledsByModelid(modelid);
//	    	response.setResultCode(ResultCode.RESULT_SUCCESS);
//			response.setData(listfield);
//			response.setMessage("获取信息成功");
//			return response;
//
//	    }
//
//
//
//	    //复合型标准根据stdmodel的id获取field model list
//	    @RequestMapping(value = "/getModelByComplexModelid/{modelid}", method = RequestMethod.GET)
//	    @ResponseBody
//	    public BaseResponse<Standard> getModelByComplexModelid(@PathVariable("modelid") String modelid) {
//	    	BaseResponse<Standard> response = new BaseResponse<Standard>();
//		    List<Standard> liststand =standardservice.getModelByComplexModelid(modelid);
//			response.setResultCode(ResultCode.RESULT_SUCCESS);
//			response.setData(liststand);
//			response.setMessage("获取信息成功");
//
//			return response;
//	    }
//
//
//	    //根据model信息查找标准模型信息
//	    @ResponseBody
//		@RequestMapping(value = "/searchModelByModelInfo/{modelinfo}", method = RequestMethod.GET)
//		public BaseResponse<Standard> searchModelByModelInfo(@PathVariable("modelinfo") String modelinfo) {
//	    	BaseResponse<Standard> response = new BaseResponse<Standard>();
//		    List<Standard> liststand =standardservice.getModelByModelInfo(modelinfo);
//			response.setResultCode(ResultCode.RESULT_SUCCESS);
//			response.setData(liststand);
//			response.setMessage("搜索成功");
//
//			return response;
//	    }
//
//	    //根据field信息查找标准信息
//	    @ResponseBody
//		@RequestMapping(value = "/searchFieldByFieldInfo", method = RequestMethod.POST)
//		public BaseResponse<StandardField> searchFieldByFieldInfo(@RequestBody Object searchCondition) {
//	    	BaseResponse<StandardField> response = new BaseResponse<StandardField>();
//	    	JSONObject jsonObject = JSONObject.fromObject(searchCondition);
//
//	    	String fieldinfo=jsonObject.getString("fieldinfo");
//	    	String category=jsonObject.getString("category");
//
//
//
//	    	List<StandardField> listfield =standardservice.getFieldByFieldInfo(fieldinfo,category);
//			response.setResultCode(ResultCode.RESULT_SUCCESS);
//			response.setData(listfield);
//			response.setMessage("搜索成功");
//
//			return response;
//	    }
//
//
//	    //添加stdfield
//	    @ResponseBody
//		@RequestMapping(value = "/addStdField", method = RequestMethod.POST)
//		public BaseResponse<StandardField> addStdField(
//				@RequestBody StandardField field) {
//	    	BaseResponse<StandardField> response = new BaseResponse<StandardField>();
//	    	standardservice.saveStdFiled(field);
//	    	response.setResultCode(ResultCode.RESULT_SUCCESS);
//
//			response.setMessage("添加标准字段成功");
//			return response;
//	    }
//
//	    //根据fieldid删除一条标准field
//	    @ResponseBody
//		@RequestMapping(value = "/deleteStdField/{fieldId:.+}", method = RequestMethod.DELETE)
//		public BaseResponse<StandardField> deleteStdField(@PathVariable String fieldId){
//
//			BaseResponse<StandardField> response = new BaseResponse<StandardField>();
//			standardservice.delStdFiled(fieldId);
//			response.setResultCode(ResultCode.RESULT_SUCCESS);
//			response.setMessage("删除标准字段成功");
//
//			return response;
//
//		}
//
//
//	    //获取全部标准编辑信息列表
//	    @ResponseBody
//		@RequestMapping(value = "/getModifyInfo", method = RequestMethod.GET)
//		@Transactional
//		public BaseResponse<StandardRequest> getModifyInfo() {
//
//			BaseResponse<StandardRequest> response = new BaseResponse<StandardRequest>();
//
//			List<StandardRequest> requests = standardModifyService.findStandardAndCodeInfo();
//			if (requests.size() == 0) {
//				response.setResultCode(ResultCode.RESULT_ERROR);
//				response.setMessage("未找到请求信息");
//				return response;
//			}
//
//			response.setResultCode(ResultCode.RESULT_SUCCESS);
//			response.setMessage("获取用户请求信息成功");
//			response.setData(requests);
//			return response;
//
//		}
//
//	    //提交新建标准的请求
//	    @ResponseBody
//		@RequestMapping(value = "/create", method = RequestMethod.POST)
//		public BaseResponse<Standard> createStdModel(
//				@RequestBody Standard stdModel){
//	    	logger.info(String.format("开始调用标准新建请求接口：/standard/create"));
//			BaseResponse<Standard> response = new BaseResponse<Standard>();
//
//			if(StringUtils.isEmpty(stdModel.getId())) {
//				stdModel.setId(CommonUtil.getUUID());
//			}
//			else {
//				if(standardservice.isStdExistById(stdModel.getId())) {
//					response.setResultCode(ResultCode.RESULT_ERROR);
//					response.setMessage("标准ID冲突");
//					return response;
//				}
//			}
//
//			if(StringUtils.isEmpty(stdModel.getCode())) {
//				response.setResultCode(ResultCode.RESULT_ERROR);
//				response.setMessage("请输入标准编码");
//				return response;
//			}
//			else {
//				if(standardservice.isStdExistByCode(stdModel.getCode())) {
//					response.setResultCode(ResultCode.RESULT_ERROR);
//					response.setMessage("标准编码冲突");
//					return response;
//				}
//			}
//
//
//			stdModel.setCreateTime(Calendar.getInstance().getTime());
//
//			UserInfo userInfo =null; //(UserInfo) SecurityUtils.getSubject().getPrincipal();
//
//			standardModifyService.submitStdRequest(stdModel, userInfo,RegisterType.ADD);
//
//
//			response.setResultCode(ResultCode.RESULT_SUCCESS);
//			response.setMessage("新建标准申请已提交");
//			return response;
//
//	    }
//
//	    //提交更新标准的请求
//	    @ResponseBody
//		@RequestMapping(value = "/update", method = RequestMethod.POST)
//		public BaseResponse<Standard> updateStdModel(
//				@RequestBody Standard updateStdModelInfo) {
//
//			logger.info(String.format("开始调用标准更新请求接口：/standard/update"));
//			BaseResponse<Standard> response = new BaseResponse<Standard>();
//
//			// 检查标准是否存在
//			Standard stand = standardservice.getStandardInfoById(updateStdModelInfo.getId());
//			if(null == stand) {
//				response.setResultCode(ResultCode.RESULT_NOT_EXIST);
//				response.setMessage("未找到对应标准");
//				return response;
//			}
//
//			UserInfo userInfo = null;//(UserInfo) SecurityUtils.getSubject().getPrincipal();
//
//			// 提交修改模型申请
//			standardModifyService.submitStdRequest(updateStdModelInfo, userInfo,RegisterType.MODIFY);
//
//			response.setResultCode(ResultCode.RESULT_SUCCESS);
//			response.setMessage("修改标准申请已提交");
//
//			return response;
//	    }
//
//
//
//	    //提交删除标准的请求
//	    @ResponseBody
//	    @Transactional
//		@RequestMapping(value = "/delete/{stdId:.+}", method = RequestMethod.GET)
//		public BaseResponse<Standard> deleteStandard(
//				@PathVariable String stdId) {
//	    	logger.info(String.format("开始调用标准删除请求接口：/standard/delete"));
//	    	logger.info(String.format("请求删除的标准id为"+stdId));
//			BaseResponse<Standard> response = new BaseResponse<Standard>();
//
//			// 检查标准是否存在
//			Standard stand = standardservice.getStandardInfoById(stdId);
//			if(null == stand) {
//				response.setResultCode(ResultCode.RESULT_NOT_EXIST);
//				response.setMessage("未找到对应标准");
//				return response;
//			}
//
//			UserInfo userInfo =null; //(UserInfo) SecurityUtils.getSubject().getPrincipal();
//
//			// 提交修改模型申请
//			standardModifyService.submitStdRequest(stand, userInfo,RegisterType.DELETE);
//
//
//			response.setResultCode(ResultCode.RESULT_SUCCESS);
//			response.setMessage("删除标准申请已提交");
//			return response;
//	    }
//
//
//	    /**
//		 * @Title: affirmDeleteByCodeList
//		 * @Description: 批量删除标准接口
//		 * @param
//		 * @return
//		 * @throws
//		 */
//		@ResponseBody
//		@Transactional
//		@RequestMapping(value = "/affirmDeleteByCodeList", method = RequestMethod.POST)
//		public BaseResponse<String> affirmDeleteByCodeList(
//				@RequestBody List<String> modelCodeList) {
//
//			logger.info(String.format("开始调用批量标准删除接口：/stand/affirmDeleteByCodeList"));
//			BaseResponse<String> response = new BaseResponse<String>();
//
//			for(String modelCode : modelCodeList) {
//
//				// 获取模型
//				Standard stand = standardservice.getStandardInfoByCode(modelCode);
//				if(null == stand) {
//					logger.error("未找到对应标准，标准编码:" + modelCode);
//					continue;
//				}
//
//				UserInfo userInfo =null; //(UserInfo) SecurityUtils.getSubject().getPrincipal();
//
//				// 提交修改模型申请
//				standardModifyService.submitStdRequest(stand, userInfo,RegisterType.DELETE);
//
//			}
//
//			response.setResultCode(ResultCode.RESULT_SUCCESS);
//			response.setMessage("批量删除标准接口调用完成");
//
//			return response;
//		}
//
//		/**
//		 * @Title: getCheckModelAll
//		 * @Description: 在原有获取全部模型基础上增加是否核标、核标情况、是否核标等信息
//		 * @return @throws
//		 */
//		@ResponseBody
//		@RequestMapping(value = "/getCheckModelAll", method = RequestMethod.GET)
//		public BaseResponse<CheckModel> getCheckModelAll(){
//			BaseResponse<CheckModel> response = new BaseResponse<CheckModel>();
//
//			List<CheckModel> listmodel=standardservice.getCheckModelAll();
//
//			response.setResultCode(ResultCode.RESULT_SUCCESS);
//			response.setData(listmodel);
//			response.setMessage("获取模型结果成功");
//
//
//			return response;
//		}
//
//
//
//		/**
//		 * @Title: getCheckFiledbyModelid
//		 * @Description: 在原有获取全部模型基础上增加是否核标、核标情况、是否核标等信息
//		 * @return @throws
//		 */
//		@ResponseBody
//		@RequestMapping(value = "/getCheckFieldbyModelid/{modelId}", method = RequestMethod.GET)
//		public BaseResponse<CheckModelField> getCheckFieldbyModelid(@PathVariable("modelId") String modelId){
//			BaseResponse<CheckModelField> response = new BaseResponse<CheckModelField>();
//
//
//			List<CheckModelField> listmodelfield=standardservice.getCheckFieldbyModelid(modelId);
//
//			response.setResultCode(ResultCode.RESULT_SUCCESS);
//			response.setData(listmodelfield);
//			response.setMessage("获取模型字段结果成功");
//
//
//			return response;
//		}
		
}
