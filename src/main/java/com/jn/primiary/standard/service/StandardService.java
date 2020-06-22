package com.jn.primiary.standard.service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jn.primiary.metadata.entity.Model;
import com.jn.primiary.metadata.entity.ModelField;
import com.jn.primiary.metadata.service.ModelService;
import com.jn.primiary.metadata.utils.CommonUtil;
import com.jn.primiary.standard.dao.FieldCheckRepository;
import com.jn.primiary.standard.dao.StandardCategoryRepository;
import com.jn.primiary.standard.dao.StandardFieldRepository;
import com.jn.primiary.standard.dao.StandardRepository;
import com.jn.primiary.standard.entity.CheckModel;
import com.jn.primiary.standard.entity.CheckModelField;
import com.jn.primiary.standard.entity.Dictionary;
import com.jn.primiary.standard.entity.FieldCheckResult;
import com.jn.primiary.standard.entity.ModelBaseInfo;
import com.jn.primiary.standard.entity.Standard;
import com.jn.primiary.standard.entity.StandardCategory;
import com.jn.primiary.standard.entity.StandardDataSource;
import com.jn.primiary.standard.entity.StandardField;
//import com.jn.stdtest.entity.ReturnInf;



@Service
public class StandardService {
	
	
	 @Autowired
	 private StandardCategoryRepository standardCategoryRepository;
	 
	 @Autowired
	 private StandardRepository standardRepository;
	 
	 @Autowired
	 private StandardFieldRepository standardFieldRepository;
	 
	 
	 @Autowired
	 private FieldCheckRepository fieldCheckRepository;
	 
	 
	 @Autowired
	ModelService modelService;
	 
	 
	 private Logger logger = Logger.getLogger(StandardService.class);
	
	//获取全部标准目录信息
	public List<StandardCategory> getAllStandardCategorys() {
		
		List<StandardCategory> listcategory = standardCategoryRepository.findAll();
		
		return listcategory;
	}
	
	//获取指定标准目录的子目录信息
	public List<StandardCategory> getSubStandardCategorys(String parentId) {
		
		List<StandardCategory> listcategory = standardCategoryRepository.findByParentId(parentId);
		
		return listcategory;
	}
	
	
	//获取指定目录下的标准信息
	public List<Standard> getStandardsByCategoryId(String categoryId) {
		
		
		List<Standard> liststand =standardRepository.findByCategoryId(categoryId);
		
		return liststand;
	}
	
	
	
	// 获取全部标准信息
	public List<Standard> getAllStandardInfo() {
		
		return standardRepository.findAll();

		
		
	}
	
	
	// 通过标准ID获取指定标准信息
		public Standard getStandardInfoById(String standardId) {
			
			Standard stand = standardRepository.findById(standardId);
			if(stand!=null) {
				List<StandardField> fields=standardFieldRepository.findBySchemacodeId(standardId);
				stand.setFields(fields);
			}
			
			return stand;
		}
	
	
		
	//通过标准code获取指定标准信息 
		public Standard getStandardInfoByCode(String code) {
			
			Standard stand = standardRepository.findByCode(code);
			
			
			return stand;
		}
		
	
	public Standard getComplexStandardInfoById(String standardId) {
		Standard stand=new Standard();
		StandardField mainfield=standardFieldRepository.findById(standardId);
		stand.setId(mainfield.getId());
		stand.setCode(mainfield.getCode());
		stand.setName(mainfield.getName());
		stand.setDescription(mainfield.getDefination());
		stand.setType(0);
		
		List<StandardField> fields=standardFieldRepository.findBySchemacodeId(standardId);
		stand.setFields(fields);
		
		return stand;
		
	}
	
	public List<Dictionary> getDictionariesByCategoryId(String categoryId) {
		
		List<Dictionary> listdict = new ArrayList<Dictionary> ();
		
		return listdict;
	}
	
	
	public List<Dictionary> getDictionaryInfo(String dictionaryId) {
		
		List<Dictionary> listdict = new ArrayList<Dictionary> ();
		
		return listdict;
	}
	
	
	//
	public List<ModelBaseInfo> getStdInfBySchemaId(Model model){
		List<ModelBaseInfo> listmodel = new ArrayList<ModelBaseInfo> ();
		
		int haspasstest = 1; // 判断整个模型是否通过标准检测
		List<ModelBaseInfo> result = new ArrayList<ModelBaseInfo>();     //字段检测结果返回列表

		//得到模型的字段详细信息
		model = modelService.getModelInfo(model.getId());

		List<ModelField> modelFields = model.getFields();
		
		
		for (ModelField schemaf : modelFields) {		
			ModelBaseInfo returnf = checkFieldsByStandards(schemaf);  //单个字段的返回结果
			listmodel.add(returnf);
		}		
		return listmodel;

		
	}
	
	
	private ModelBaseInfo checkFieldsByStandards(ModelField field) {
		ModelBaseInfo info = new ModelBaseInfo(); 
		info.setFieldId(field.getId());
		info.setFieldCode(field.getCode());
		info.setStdFieldCode(field.getCode());
		info.setFieldName(field.getEnName());
		info.setType(field.getType());
		info.setStdFieldType(field.getType());
		
		String checkresult = "不符合";
		FieldCheckResult result=fieldCheckRepository.findByFieldId(field.getId());
		
		if(result!=null)
		{
			if(result.getCheckResult().replaceAll(" ", "").equals("符合")){
				info.setStatus(1);
			}else if(result.getCheckResult().replaceAll(" ", "").equals("相似")){
				info.setStatus(2);
			}else{
				info.setStatus(0);
			}
			
			if(info.getStatus()!=0) {//考虑有不符合的情况会报错
				info.setStdfieldId(result.getStdProperty());
				
				
				String stdfieldid=result.getStdProperty();
				
				StandardField stdfield=standardFieldRepository.findById(stdfieldid);
				
				
				Standard stand=standardRepository.findById(stdfield.getSchemacodeId());
				if(stand!=null) {//非复合型标准
					info.setStdschemaCode(stand.getCode());
					info.setStdschemaID(stand.getId());
				}else {//复合型标准
					StandardField complexfield=standardFieldRepository.findById(stdfield.getSchemacodeId());
					if(complexfield!=null) {
						info.setStdschemaCode(complexfield.getCode());
						info.setStdschemaID(complexfield.getId());
					}
				}
			}
		}else {
			info.setStatus(0);
		}
		return info;
	}
	
	
	//获取全部datasource名以及其类型
	public List<StandardDataSource> getAllStdName(){
		List<StandardDataSource> listdatasource=new ArrayList<StandardDataSource>();
		List<String> liststr=standardRepository.findAllDataSource();
		for(String str:liststr){
			StandardDataSource datasource=new StandardDataSource();
			datasource.setDatasource(str);
			datasource.setType(isSingular(str));
			listdatasource.add(datasource);
		}
		return listdatasource;
	}
	
	  private int isSingular(String stdname){
			int iscompound=-1;
			if(stdname.contains("SJB Y1-2018")){
				 iscompound=0;
			}else if(stdname.contains("SJB Y2")){
				iscompound=1;
			}else if(stdname.contains("SJB Y5-2018")){
				iscompound=1;
			}else if(stdname.contains("SJB Y7-2018")){
				iscompound=1;
			}else if(stdname.contains("SJB Y8-2018")){
				iscompound=0;
			}else if(stdname.contains("SJB Y12-2018")){
				iscompound=1;
			}else if(stdname.contains("SJB Y13-2018")){
				iscompound=0;
			}else if(stdname.contains("SJB Y14-2018")){
				iscompound=0;
			}
			
			return iscompound;
			
			
		}
	  
	  
	  public List<Standard> getModelByStdSource(String datasource){
		  return standardRepository.findByDataSource(datasource);
	  }
	  
	  
	  public List<StandardField>  getFiledsByModelid(String modelid){
		  return standardFieldRepository.findBySchemacodeId(modelid);
	  }
	  
	  public List<Standard> getModelByComplexModelid(String modelid){
		  List<Standard> liststand=new ArrayList<Standard>();
		  List<StandardField> listfield=standardFieldRepository.findBySchemacodeId(modelid);
		  for(StandardField field:listfield){
			  Standard stand=new Standard();
			  stand.setCode(field.getCode());
			  stand.setDescription(field.getDefination());
			  stand.setEnName(field.getEnName());
			  stand.setName(field.getName());
			  stand.setType(0);
			  
			  liststand.add(stand);
		  }
		  return liststand;
	  }
	  
	  public void saveStdFiled(StandardField field){
		  standardFieldRepository.save(field);
		  return;
	  }
	  
	  public void delStdFiled(String fieldId){
		  //首先需要判断是不是复合型的field，方法是查看field的type信息
		  boolean isComplex=isFiledComplex(fieldId);
		  if(isComplex==true){//如果是复合型的需要先删除属于它的field，再删除自己			 
			  standardFieldRepository.deleteBySchemacodeId(fieldId);
		  }
		  
		  standardFieldRepository.deleteById(fieldId);
	  }
	  
	  private boolean isFiledComplex(String fieldId){
		  boolean isComplex=false;
		  List<StandardField> field=standardFieldRepository.findBySchemacodeId(fieldId);
		  if(field.size()>0)
			  isComplex=true;
			  
		  
		  return isComplex;
	  }
	  
	  
	  public List<Standard> getModelByModelInfo(String modelinfo){
		  return standardRepository.findByModelInfo(modelinfo);
	  }
	  
	  
	  public List<StandardField> getFieldByFieldInfo(String fieldinfo,String category){
		  if(category.isEmpty()){
			  return standardFieldRepository.findByFieldinfo(fieldinfo);
		  }else{
			  return standardFieldRepository.findByFieldAndCategory(fieldinfo,category);
		  }
		  
		 
	  }
	  
	  
	  public void deleteStd(Standard stand){
		  try {
			//删除字段信息
			//先判断是不是复合型的 ，如果是则需要先从叶子节点开始删起
			  deleteAllFieldByStand(stand);
			  
			  
			  
			//删除标准信息
			standardRepository.deleteById(stand.getId());
		  } catch (Exception e) {
				logger.error(e.getMessage());
				return;
			}
	  }
	  
	  
	  private void deleteAllFieldByStand(Standard stand){
		  int issingular=isSingular(stand.getDataSource());
		  if(1==issingular){//复合型
			 List<StandardField>  fields=standardFieldRepository.findBySchemacodeId(stand.getId());
			 if(null!=fields){
				 for(StandardField field:fields){
					 standardFieldRepository.deleteBySchemacodeId(field.getId());
				 }
			 }
		  }
		  standardFieldRepository.deleteBySchemacodeId(stand.getId());
	  }
	  
	  
	  public void saveStd(Standard stand){
		  try {
			  if(StringUtils.isEmpty(stand.getId())) {
				  stand.setId(CommonUtil.getUUID());
			  } 
			  standardRepository.save(stand);
			  //先删除旧的数据
			  deleteAllFieldByStand(stand);
			  
			  List<StandardField> fields=stand.getFields();
			  for(StandardField field:fields){
				  String fieldId = field.getId();
					if (StringUtils.isEmpty(fieldId)) {
						field.setId(CommonUtil.getUUID());
					}
					field.setSchemacodeId(stand.getId());
					standardFieldRepository.save(field);
			  }
		  }catch (Exception e) {
				logger.error(e.getMessage());
				return;
			}
	  }
	  
	  
	  /**
		 * @Title: isStdExistById 
		 * @Description:根据标准ID判断该标准是否已存在
		 * @param 
		 * @return 
		 * @throws
		 */
		public boolean isStdExistById(String id) {
			
			return (standardRepository.countById(id)>0) ? true : false;
			
		}
		
		/**
		 * @Title: isStdExistByCode 
		 * @Description:根据标准编码判断该标准是否已存在
		 * @param code 标准编码 
		 * @return 
		 * @throws
		 */
		public boolean isStdExistByCode(String code) {
			
			return (standardRepository.countByCode(code)>0) ? true : false;
			
		}
		
		
		
		/**
		 * @Title: contraststd 
		 * @Description:根据标准编码与模型编码比较两者的情况
		 * @param modelId 模型id  stdId 标准id
		 * @return 
		 * @throws
		 * 暂时未考虑组合标准的情况，这种情况怎么弄回头再想
		 */
		public List<FieldCheckResult> contraststd(String modelId,String stdId){
			List<FieldCheckResult> listresult=new LinkedList<FieldCheckResult>();
		    Model model = modelService.getModelInfo(modelId);
		    Standard stand=getStandardInfoById(stdId);
		    List<ModelField> listmodelfield=model.getFields();
		    List<StandardField> liststdfield=stand.getFields();
		    for(ModelField field : listmodelfield) {
		    	FieldCheckResult result=null;
		    	boolean isaccord=false;
		    	for(StandardField stdfield: liststdfield) {
		    		result=CompareField(field,stdfield,stand.getDataSource());
		    		if(result.getCheckResult().equalsIgnoreCase("符合")||result.getCheckResult().equalsIgnoreCase("相似")) {
		    			listresult.add(result);
		    			isaccord=true;
		    		}
		    	}
		    	if(result.getCheckResult().equalsIgnoreCase("不符合")&&false==isaccord)
		    		listresult.add(result);
		    	
		    }
			return listresult;
		}
		
		
		/**
		 * @Title: contraststd 
		 * @Description:根据标准编码与模型编码比较两者的情况
		 * @param modelId 模型id  stdId 标准id
		 * @return 
		 * @throws
		 * 暂时未考虑组合标准的情况，这种情况怎么弄回头再想
		 */
		public List<FieldCheckResult> contraststdbyModel(Model model,String stdId){
			List<FieldCheckResult> listresult=new LinkedList<FieldCheckResult>();
		    //Model model = modelService.getModelInfo(modelId);
		    Standard stand=getStandardInfoById(stdId);
		    List<ModelField> listmodelfield=model.getFields();
		    List<StandardField> liststdfield=stand.getFields();
		    for(ModelField field : listmodelfield) {
		    	FieldCheckResult result=null;
		    	boolean isaccord=false;
		    	for(StandardField stdfield: liststdfield) {
		    		result=CompareField(field,stdfield,stand.getDataSource());
		    		if(result.getCheckResult().equalsIgnoreCase("符合")||result.getCheckResult().equalsIgnoreCase("相似")) {
		    			listresult.add(result);
		    			isaccord=true;
		    		}
		    	}
		    	if(result.getCheckResult().equalsIgnoreCase("不符合")&&false==isaccord)
		    		listresult.add(result);
		    	
		    }
			return listresult;
		}
		
		
		
		/**
		 * @Title: contraststd 
		 * @Description:根据标准编码与模型编码比较两者的情况
		 * @param modelId 模型id  stdId 标准id
		 * @return 
		 * @throws
		 * 暂时未考虑组合标准的情况，这种情况怎么弄回头再想
		 */
		/*public List<FieldCheckResult> contrastbyField(List<ModelField> fields,String stdId){
			List<FieldCheckResult> listresult=new LinkedList<FieldCheckResult>();
		    //Model model = modelService.getModelInfo(modelId);
		    Standard stand=getStandardInfoById(stdId);
		    List<ModelField> listmodelfield=fields;//model.getFields();
		    List<StandardField> liststdfield=stand.getFields();
		    for(ModelField field : listmodelfield) {
		    	FieldCheckResult result=null;
		    	boolean isaccord=false;
		    	for(StandardField stdfield: liststdfield) {
		    		result=CompareField(field,stdfield,stand.getDataSource());
		    		if(result.getCheckResult().equalsIgnoreCase("符合")||result.getCheckResult().equalsIgnoreCase("相似")) {
		    			listresult.add(result);
		    			isaccord=true;
		    		}
		    	}
		    	if(result.getCheckResult().equalsIgnoreCase("不符合")&&false==isaccord)
		    		listresult.add(result);
		    	
		    }
			return listresult;
		}*/
		
		
		private FieldCheckResult CompareField(ModelField modelfield,StandardField stdfield,String standname) {
			FieldCheckResult result =new FieldCheckResult();
			boolean isenname=false;
			boolean iscname=false;
			boolean istype=false;
			boolean issize=false;
			if(modelfield.getEnName().equalsIgnoreCase(stdfield.getEnName())) {
				isenname=true;
			}
			if(modelfield.getName().equalsIgnoreCase(stdfield.getName())) {
				iscname=true;
			}
			if(modelfield.getType().equalsIgnoreCase(stdfield.getType())) {
				istype=true;
			}
			if(modelfield.getMaxsize()==stdfield.getMaxsize()) {
				issize=true;
			}
			if(true==isenname&&true==iscname&&true==istype&&true==issize) {
				result.setCheckResult("符合");
				result.setDetail("完全符合");
				result.setFieldId(modelfield.getId());
				result.setStdProperty(stdfield.getId());
				result.setStdname(standname);
			}else if(true==isenname&&true==iscname&&true==istype&&false==issize){
				result.setCheckResult("相似");
				result.setDetail("长度不吻合");
				result.setFieldId(modelfield.getId());
				result.setStdProperty(stdfield.getId());
				
			}else if(true==isenname&&false==iscname&&true==istype&&true==issize) {
				result.setCheckResult("相似");
				result.setDetail("中文名称不吻合");
				result.setFieldId(modelfield.getId());
				result.setStdProperty(stdfield.getId());
			}else if(false==isenname&&true==iscname&&true==istype&&true==issize) {
				result.setCheckResult("相似");
				result.setDetail("英文名称不吻合");
				result.setFieldId(modelfield.getId());
				result.setStdProperty(stdfield.getId());
			}else {
				result.setCheckResult("不符合");
				result.setDetail("完全不符合");
				result.setFieldId(modelfield.getId());
			}
			String str=modelfield.toString();
			
			return result;
		}
		
		
		public void saveCheckResult(FieldCheckResult result) {
			if(result.getFieldId()!=null&&!result.getFieldId().isEmpty()) {
				FieldCheckResult oldresult=fieldCheckRepository.findByFieldId(result.getFieldId());
				//先查询是否有核标结果，如果有需要先删掉
				if(null != oldresult) {
					fieldCheckRepository.deleteByFieldId(result.getFieldId());
				}
				result.setId(CommonUtil.getUUID());
				fieldCheckRepository.save(result);
			}
		}

		
		
		public List<CheckModelField> getCheckFieldbyModelid(String modelId){
			List<CheckModelField> listcheckfield=new LinkedList<CheckModelField>();
			List<ModelField> listfield=modelService.getModelInfo(modelId).getFields();
			for(ModelField field:listfield) {
		    	
		    	CheckModelField  checkfield=new CheckModelField();
		    	try {
					copybean(field,checkfield);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		    	FieldCheckResult result=fieldCheckRepository.findByFieldId(field.getId());
		    	if(null==result) {
		    		
		    	}else {
		    		
		    		checkfield.setCheckResult(result.getCheckResult());
		    		checkfield.setDetail(result.getDetail());
		    		checkfield.setStdProperty(result.getStdProperty());
		    		checkfield.setStdname(result.getStdname());
		    	}
		    	
		    	listcheckfield.add(checkfield);
		    	
		    }
			
			
			return listcheckfield;
		}
		
		
		
		
		public List<CheckModel> getCheckModelAll(){
			List<CheckModel> listcheckmodel=new LinkedList<CheckModel>();
			List<Model> listmodel=modelService.getAllTableModel();
			for(Model model:listmodel) {
				CheckModel checkmodel=new CheckModel();
				checkmodel.setChecked(false);
				//List<CheckModelField> listcheckfield=new LinkedList<CheckModelField>();
				
				//checkmodel.setFields(listcheckfield);//对象拷贝时，将field字段也拷贝进去了，需要置零
				try {
					copybean(model,checkmodel);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				checkmodel.setFields(null);
				List<ModelField> listfield=  model.getFields();
				for(ModelField field:listfield) {
					FieldCheckResult result=fieldCheckRepository.findByFieldId(field.getId());
			    	if(null==result) {
			    		//checkmodel.setChecked(false);//未查到检测结果，设置模型信息中检测标志为false
			    	}else {
			    		checkmodel.setChecked(true);
			    	}
			}
				/*
				List<ModelField> listfield=  model.getFields();
				if(null==listfield||listfield.size()==0) {
					checkmodel.setChecked(false);//未查到检测结果，设置模型信息中检测标志为false
					continue;
				}
				    for(ModelField field:listfield) {
				    	
				    	CheckModelField  checkfield=new CheckModelField();
				    	try {
							copybean(field,checkfield);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    	
				    	FieldCheckResult result=fieldCheckRepository.findByFieldId(field.getId());
				    	if(null==result) {
				    		checkmodel.setChecked(false);//未查到检测结果，设置模型信息中检测标志为false
				    	}else {
				    		checkmodel.setChecked(true);
				    		checkfield.setCheckResult(result.getCheckResult());
				    		checkfield.setDetail(result.getDetail());
				    	}
				    	
				    	listcheckfield.add(checkfield);
				    	
				    }
				
				    
				checkmodel.setFields(listcheckfield);    
				*/
				listcheckmodel.add(checkmodel);
			}
			
			return listcheckmodel;
		}
	 
		
		private static void copybean(Object source, Object dest)throws Exception {  
	        //获取属性  
	        BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(), java.lang.Object.class);  
	        PropertyDescriptor[] sourceProperty = sourceBean.getPropertyDescriptors();  
	          
	        BeanInfo destBean = Introspector.getBeanInfo(dest.getClass(), java.lang.Object.class);  
	        PropertyDescriptor[] destProperty = destBean.getPropertyDescriptors();  
	          
	        try{  
	            for(int i=0;i<sourceProperty.length;i++){  
	                  
	                for(int j=0;j<destProperty.length;j++){  
	                      
	                    if(sourceProperty[i].getName().equals(destProperty[j].getName())){  
	                        //调用source的getter方法和dest的setter方法  
	                        destProperty[j].getWriteMethod().invoke(dest, sourceProperty[i].getReadMethod().invoke(source));  
	                        break;                    
	                    }  
	                }  
	            }  
	        }catch(Exception e){  
	            throw new Exception("属性复制失败:"+e.getMessage());  
	        }  
	    } 
}
