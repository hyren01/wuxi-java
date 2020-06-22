package com.jn.primiary.stdgl;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
//import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jn.primiary.commons.RestFullReturn;
import com.jn.primiary.commons.ResultCode;
import com.jn.primiary.commons.BaseResponse;
import com.jn.primiary.commons.CommonUtil;


/**
 * 封装标准模型相关业务，对外暴露接口
 * 
 * @author xiaowq
 * 
 */
@Controller
@RequestMapping("/stdgl/")
public class StdglSchemaService {
	
	
	@Autowired
	private JdbcTemplate mysqlJdbcTemplate;
	
	@Autowired
	private ModelServiceImpl modelserviceimpl;
	
	private Integer PAGESIZE=10;

	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	private Logger logger = Logger.getLogger(StdglSchemaService.class);


	/**
	 * 测试该服务是否激活
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/testAlive", method = RequestMethod.GET)
	public RestFullReturn testAlive() {
		RestFullReturn rfr = new RestFullReturn();
		try {
			rfr.setRetcode("RESULT_SUCCESS");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this, e);
			rfr.setMessage("服务器异常");
			rfr.setRetcode("RESULT_ERROR");
			rfr.setErrorDetail(CommonUtil.getErrorDetail(e));
		}
		return rfr;
	}
	
	
	/**
	 * 生成真实目录表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/bornRealCategory", method = RequestMethod.GET)
	public RestFullReturn doBornRealCategory() {
		RestFullReturn rfr = new RestFullReturn();
		try {
			bornRealCategory();
			rfr.setRetcode("RESULT_SUCCESS");
		} catch (Exception e) {
			e.printStackTrace();
			rfr.setMessage("服务器异常");
			rfr.setRetcode("RESULT_ERROR");
			rfr.setErrorDetail(CommonUtil.getErrorDetail(e));
		}
		return rfr;
	}	
	
	
	
	/**
	 * 搜索服务
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fieldSearch", method = RequestMethod.POST)
	public RestFullReturn doFieldSearch(@RequestBody SearchPostEntity s) {
		RestFullReturn rfr = new RestFullReturn();
		try {
			rfr.setData(fieldSearch(s));
			rfr.setRetcode("RESULT_SUCCESS");
		} catch (Exception e) {
			e.printStackTrace();
			rfr.setMessage("服务器异常");
			rfr.setRetcode("RESULT_ERROR");
			rfr.setErrorDetail(CommonUtil.getErrorDetail(e));
		}
		return rfr;
	}	
	
	
	/**
	 * 全文搜索服务
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/esSearch", method = RequestMethod.POST)
	public RestFullReturn doesSearch(@RequestBody SearchPostEntity s) {
		RestFullReturn rfr = new RestFullReturn();
		try {
			//rfr.setData(esSearch(s));
			rfr.setRetcode("RESULT_SUCCESS");
		} catch (Exception e) {
			e.printStackTrace();
			rfr.setMessage("服务器异常");
			rfr.setRetcode("RESULT_ERROR");
			rfr.setErrorDetail(CommonUtil.getErrorDetail(e));
		}
		return rfr;
	}	
	
	
	
	

	
	/**
	 * 新增目录信息（word-标准信息）
	 * 
	 * @param s
	 *            
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/doAddCategory", method = RequestMethod.POST)
	public RestFullReturn doAddCategory(@RequestBody CategoryEntity s) {
		RestFullReturn rfr = new RestFullReturn();
		if(s.getName()==null||s.getName().equals("")) {
			rfr.setRetcode("RESULT_ERROR");
			rfr.setMessage("name信息为空");
		}
		if(s.getPid()==null||s.getPid().equals("")) {
			rfr.setRetcode("RESULT_ERROR");
			rfr.setMessage("pid信息为空");
		}
		if(issamenameadd(s)){
			rfr.setMessage("相同目录下存在同名目录！");
			rfr.setRetcode("RESULT_ERROR");
			return rfr;
		}
		try {
			addCategory(s);
			rfr.setRetcode("RESULT_SUCCESS");
			rfr.setMessage("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			rfr.setRetcode("RESULT_ERROR");
			rfr.setMessage("服务器异常");
			rfr.setErrorDetail(CommonUtil.getErrorDetail(e));
		}
		
		return rfr;
	}
	
	
	/**
	 * 得到所有的目录
	 * 
	 * @param s
	 *            
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/doGetAllCategory", method = RequestMethod.GET)
	public BaseResponse<CategoryEntity> doGetAllCategory() {
		BaseResponse<CategoryEntity> rfr = new BaseResponse<CategoryEntity>();
		List<CategoryEntity> returnlist = new ArrayList<CategoryEntity>();
		try {
			returnlist = getAllCategory();
			rfr.setMessage("获取成功！");
			rfr.setData(returnlist);
			rfr.setResultCode(ResultCode.RESULT_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			rfr.setMessage("获取失败！");
			rfr.setResultCode(ResultCode.RESULT_ERROR);
			return rfr;
		}
		
		return rfr;
	}
	

	
	/**
	 * 修改目录
	 * 
	 * @param s
	 *            
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/doUpdateCategory", method = RequestMethod.POST)
	public BaseResponse<CategoryEntity> doUpdateCategory(@RequestBody CategoryEntity s) {
		BaseResponse<CategoryEntity> rfr = new BaseResponse<CategoryEntity>();
		if(issamename(s)){
			rfr.setMessage("相同目录下存在同名目录！");
			rfr.setResultCode(ResultCode.RESULT_ERROR);
			return rfr;
		}
		
		try {
			updateCategory(s);
			rfr.setMessage("修改成功！");
			rfr.setResultCode(ResultCode.RESULT_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			rfr.setMessage("修改失败！");
			rfr.setResultCode(ResultCode.RESULT_ERROR);
			return rfr;
		}
		
		return rfr;
	}	
	
	
	/**
	 * 删除目录
	 * 
	 * @param s
	 *            
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/doDeleteCategory", method = RequestMethod.DELETE)
	public BaseResponse<CategoryEntity> doDeleteCategory(@RequestBody CategoryEntity s) {
		BaseResponse<CategoryEntity> rfr = new BaseResponse<CategoryEntity>();
		try {
			deleteCategory(s);
			rfr.setMessage("删除成功！");
			rfr.setResultCode(ResultCode.RESULT_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			rfr.setMessage("删除失败！");
			rfr.setResultCode(ResultCode.RESULT_ERROR);
			return rfr;
		}
		
		return rfr;
	}	
	
	
	
	
	/**
	 * 得到模型的详细信息(分组信息、字段列表、索引列表、分区信息)
	 * 
	 * @param s
	 *            模型基本信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/doGetStdSchemaObjectDetail", method = RequestMethod.POST)
	public RestFullReturn doGetStdSchemaObjectDetail(@RequestBody StdSchemaObject s) {
		RestFullReturn rfr = new RestFullReturn();
		try {
			rfr.setData(getStdSchemaObjectDetail(s));
			rfr.setRetcode("RESULT_SUCCESS");
			rfr.setMessage("获取成功");
		} catch (Exception e) {
			e.printStackTrace();
			rfr.setRetcode("RESULT_ERROR");
			rfr.setMessage("服务器异常");
			rfr.setErrorDetail(CommonUtil.getErrorDetail(e));
		}
		return rfr;
	}
	


	

	/**
	 * 新增一个标准字段到一个模型中
	 * 
	 * 
	 * @param s
	 *            标准信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/doAddStdfield", method = RequestMethod.POST)
	public BaseResponse<String> doAddStdfield(@RequestBody StdFieldEntity stdschemaobject) {
		BaseResponse<String> rfr = new BaseResponse<String>();
        if(stdschemaobject.getSchemaCodeID().isEmpty()){
			rfr.setMessage("模型ID为空");
			rfr.setResultCode(ResultCode.RESULT_ERROR);
			return rfr;
        }        
        
        if(isexistadd(stdschemaobject)){
        	rfr.setMessage("模型中已存在相同字段编码");
			rfr.setResultCode(ResultCode.RESULT_ERROR);
			return rfr;
        }
        
        try{
        	addStdfield(stdschemaobject);
        }catch(Exception e){
        	e.printStackTrace();
        	rfr.setMessage("新增失败");
			rfr.setResultCode(ResultCode.RESULT_ERROR);
        }
        rfr.setMessage("新增成功");		
		rfr.setResultCode(ResultCode.RESULT_SUCCESS);
		return rfr;
	}
	
	/**
	 * 删除一个标准字段
	 * 
	 * 
	 * @param s
	 *            标准信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/doDeleteStdfieldObject", method = RequestMethod.DELETE)
	public BaseResponse<String> doDeleteStdSchemaObject(@RequestBody StdFieldEntity stdschemaobject) {
		BaseResponse<String> rfr = new BaseResponse<String>();
                
        try{
        	deleteStdSchemaObject(stdschemaobject);
        }catch(Exception e){
        	e.printStackTrace();
        	rfr.setMessage("删除失败");
			rfr.setResultCode(ResultCode.RESULT_ERROR);
        }
        rfr.setMessage("删除成功");		
		rfr.setResultCode(ResultCode.RESULT_SUCCESS);
		return rfr;
	}
	
	
	/**
	 * 修改一个标准字段的信息
	 * 
	 * 
	 * @param s
	 *            标准信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/doUpdateStdfieldObject", method = RequestMethod.POST)
	public BaseResponse<String> doUpdateStdSchemaObject(@RequestBody StdFieldEntity stdschemaobject) {
		BaseResponse<String> rfr = new BaseResponse<String>();
		if(stdschemaobject.getId().isEmpty()){
			rfr.setMessage("字段ID为空");
			rfr.setResultCode(ResultCode.RESULT_ERROR);
			return rfr;
        }
        
        if(isexist(stdschemaobject)){
        	rfr.setMessage("模型中已存在相同字段编码");
			rfr.setResultCode(ResultCode.RESULT_ERROR);
			return rfr;
        }
		
        try{
        	updateStdSchemaObject(stdschemaobject);
        }catch(Exception e){
        	e.printStackTrace();
        	rfr.setMessage("修改失败");
			rfr.setResultCode(ResultCode.RESULT_ERROR);
        }
        rfr.setMessage("修改成功");		
		rfr.setResultCode(ResultCode.RESULT_SUCCESS);
		return rfr;
	}
	
	
	
	
	private void bornRealCategory(){
		List<String> std_source  = new ArrayList<String>();
		String sql_std_source = "select DISTINCT(dataSource) from tb_stdgl_schemamodule";
		try{
			std_source = mysqlJdbcTemplate.queryForList(sql_std_source, String.class);			
			Date nowdate= new Date();
			for(String stdsource :std_source){
				String sql_insert_source = "insert into tb_stdmodel_category values (?,?,?,?,?,?,?)";
				String source_id = CommonUtil.getUUID();
				mysqlJdbcTemplate.update(sql_insert_source,new Object[]{source_id,stdsource,0,"admin",
                        Timestamp.valueOf(formatter.format(nowdate)),"0",0});
				
				String sql_std_model = "select id,schemaName from tb_stdgl_schemamodule where ( dataSource = ? )";		      
		        List<StdMidEntity> schema_list = mysqlJdbcTemplate.query(sql_std_model, 
		        		new Object[] {stdsource}, 
		        		new StdMidEntityRowMapper());
		        
				for(StdMidEntity schema:schema_list){
					String category_schemaModelId =CommonUtil.getUUID();
					String sql_insert_model = "insert into tb_stdmodel_category values (?,?,?,?,?,?,?)";
					mysqlJdbcTemplate.update(sql_insert_model,new Object[]{category_schemaModelId,schema.getName(),source_id,"admin",
	                        Timestamp.valueOf(formatter.format(nowdate)),schema.getId(),1});
					String sql_select_object="select id,fieldname from tb_stdgl_schemafield where (schemacodeid = ? and type = ?)";
					List<StdMidEntity> schema_list2 = mysqlJdbcTemplate.query(sql_select_object, 
							new Object[] {schema.getId(),"对象"},
							new objectCategoryRowMapper());
			        for(StdMidEntity schema2:schema_list2){
			        	String sql_insert_model2 = "insert into tb_stdmodel_category values (?,?,?,?,?,?,?)";
						mysqlJdbcTemplate.update(sql_insert_model2,new Object[]{CommonUtil.getUUID(),schema2.getName(),category_schemaModelId,"admin",
		                        Timestamp.valueOf(formatter.format(nowdate)),schema2.getId(),2});
			        }
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	private List<StdFieldEntity> fieldSearch(SearchPostEntity s){
		List<StdFieldEntity> field_list = new ArrayList<StdFieldEntity>();
		String sql = "select * from tb_stdgl_schemafield where fieldName like '%"+s.getSearchcontent()+"%' or enName like '%"+s.getSearchcontent()+"%' or fieldCode like '%"+s.getSearchcontent()+"%'";
		//String sql = "select * from tb_stdgl_schemafield where locate(rtrim(fieldName),'"+schemaf.getCode()+"')>0 or locate('"+schemaf.getCode()+"',rtrim(fieldCode))>0");
		//System.out.println(sql);
		try{
			field_list = mysqlJdbcTemplate.query(sql,new StdSchemaFieldRowMapper());
		}catch(Exception e){
			e.printStackTrace();
		}
		return field_list;
	}
	
	/*
	private List<FieldEntity> esSearch(SearchPostEntity s){

		Pageable pageable = new PageRequest(0,10);
		//Page<FieldEntity> fieldentity  = modelserviceimpl.search(s.getSearchcontent(), s.getSearchcontent(),  pageable);
		//System.out.println("fieldentity.getClass():"+modelserviceimpl.search(s.getSearchcontent(), s.getSearchcontent(),  pageable).getClass());
		List<FieldEntity> fieldentity=null; // = modelserviceimpl.search(s.getSearchcontent(), s.getSearchcontent(),s.getSearchcontent(),  pageable).getContent();
		
		return fieldentity;

	}
	*/


	
	
	private List<CategoryEntity> getAllCategory(){
		List<CategoryEntity> category_list = new ArrayList<CategoryEntity>();
		List<CategoryEntity> category_list2 = new ArrayList<CategoryEntity>();
        String sql = "select * from tb_stdmodel_category";
//        String sql2="select createtime,id,fieldname,schemacodeid from tb_stdgl_schemafield where type = '对象'";
        try {
        	category_list = mysqlJdbcTemplate.query(sql, new CategoryRowMapper());
//        	category_list2=mysqlJdbcTemplate.query(sql2, new objectCategoryRowMapper());
//        	category_list.addAll(category_list2);
        }catch(Exception e){
        	e.printStackTrace();
        }
        
        return category_list;
	}
	
	
	private void addCategory(CategoryEntity s) {
		String insertsql = "insert into tb_stdmodel_category values (?,?,?,?,?,?,?,?)";
		Date nowdate= new Date();
		if(s.getIsmodel() == 0){
			try {
				mysqlJdbcTemplate.update(insertsql,new Object[]{CommonUtil.getUUID(),s.getName(),s.getPid(),s.getCreator(),
						                                        Timestamp.valueOf(formatter.format(nowdate)),s.getModelID(),s.getIsmodel(),1});
			}catch(Exception e) {
				e.printStackTrace();
			}	
		}
		if(s.getIsmodel() == 1){
			try {
				mysqlJdbcTemplate.update(insertsql,new Object[]{CommonUtil.getUUID(),s.getName(),s.getPid(),s.getCreator(),
						                                        Timestamp.valueOf(formatter.format(nowdate)),CommonUtil.getUUID(),s.getIsmodel(),1});
			}catch(Exception e) {
				e.printStackTrace();
			}	
		}
			
	}
	
	
	//判断是否一个父节点下是否有两个相同名字的子节点
	boolean issamename(CategoryEntity s){
		String sql = "select count(*) from tb_stdmodel_category where parent_id = '"+s.getPid()+"' and name = '"+s.getName()+"' and id !='"+s.getId()+"'";
		int count = 0;
		try{
			count = mysqlJdbcTemplate.queryForObject(sql,Integer.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(count > 0){
			return true;
		}else{
			return false;
		}
	}
	
	//判断是否一个父节点下是否有两个相同名字的子节点
		boolean issamenameadd(CategoryEntity s){
			String sql = "select count(*) from tb_stdmodel_category where parent_id = '"+s.getPid()+"' and name = '"+s.getName()+"'";
			int count = 0;
			try{
				count = mysqlJdbcTemplate.queryForObject(sql,Integer.class);
			}catch(Exception e){
				e.printStackTrace();
			}
			if(count > 0){
				return true;
			}else{
				return false;
			}
		}
	
	
	
	
	private void updateCategory(CategoryEntity s){
		String updatesql = "";
		if(s.getIsmodel() == 1&&(s.getModelID()==null||s.getModelID().isEmpty()||s.getModelID().equals(""))){
			updatesql = "update tb_stdmodel_category set name = '"+s.getName()+"',parent_id = '"+s.getPid()+
                    "',creator = '"+s.getCreator()+"',modelid = '"+CommonUtil.getUUID()+
                    "',pxh = "+s.getPxh()+",ismodel = "+s.getIsmodel()+" where id = '"+s.getId()+"'";
		}else{
			updatesql = "update tb_stdmodel_category set name = '"+s.getName()+"',parent_id = '"+s.getPid()+
                    "',creator = '"+s.getCreator()+"',modelid = '"+s.getModelID()+
                    "',pxh = "+s.getPxh()+",ismodel = "+s.getIsmodel()+" where id = '"+s.getId()+"'";
		}
		
		try {
    		mysqlJdbcTemplate.update(updatesql);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}	
	}
	
	
	
	private void deleteCategory(CategoryEntity s){           
		if(s.getIsmodel()==1){
			//删除叶子节点以及模型和字段
			String deletesql = "delete from tb_stdmodel_category where id = '"+s.getId()+"'";
			String deletemodel = "delete from tb_stdgl_schemamodule where id = '"+s.getModelID()+"'";
			String deletefield = "delete from tb_stdgl_schemafield where schemacodeid = '"+s.getModelID()+"'";
			try{
				mysqlJdbcTemplate.update(deletesql);
				mysqlJdbcTemplate.update(deletemodel);
				mysqlJdbcTemplate.update(deletefield);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			//删除节点并且迭代
			String deletesql = "delete from tb_stdmodel_category where id = '"+s.getId()+"'";
			String cidsql = "select id from tb_stdmodel_category where parent_id = '"+s.getId()+"'";
			List<CategoryEntity> cid = new ArrayList<CategoryEntity>();
			try{
				cid = mysqlJdbcTemplate.query(cidsql, new CategoryRowMapper());
				mysqlJdbcTemplate.update(deletesql);
			}catch(Exception e){
				e.printStackTrace();
			}
			for(CategoryEntity pid:cid){
				deleteCategory(pid);
			}
			
		}

	}
	
	
	private void deleteStdSchemaObject(StdFieldEntity stdschemaobject){
		
			String sql_field = "delete from tb_stdgl_schemafield where id = '"+stdschemaobject.getId()+"'";
		    try{
		    	mysqlJdbcTemplate.update(sql_field);
		    }catch(Exception e){
		    	e.printStackTrace();
		    }
		
	}
	
	
	private void updateStdSchemaObject(StdFieldEntity stdschemaobject){
         	if(stdschemaobject.getFieldCode()!=null&&!stdschemaobject.getFieldCode().isEmpty()){

						String SQL_update_field = "update tb_stdgl_schemafield set fieldName = '"+stdschemaobject.getFieldName()+
				                "',enName = '"+stdschemaobject.getEnName()+"',defination = '"+stdschemaobject.getDefination()+
				                "',type = '"+stdschemaobject.getType()+"',maxsize = '"+stdschemaobject.getMaxsize()+
				                "',required = '"+stdschemaobject.getRequired()+"',comments = '"+stdschemaobject.getComments()+
				                "',maxContainCount = "+stdschemaobject.getMaxContainCount()+",pxh = "+stdschemaobject.getPxh()+
				                ",creator = '"+stdschemaobject.getCreator()+"',fieldcode = '"+stdschemaobject.getFieldCode()+
				                "',field_range = '"+stdschemaobject.getRange()+"' where id = '"+stdschemaobject.getId()+"'";
						try {
	                		mysqlJdbcTemplate.update(SQL_update_field);
	                	}catch(Exception e) {
	                		e.printStackTrace();
	                	}	
				}
	}
	
	
	
	
	private void addStdfield(StdFieldEntity stdschemaobject){
				
         if(stdschemaobject.getFieldCode()!=null&&!stdschemaobject.getFieldCode().isEmpty()){
					String SQL_insert_field = "insert into tb_stdgl_schemafield values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
					Date nowdate_field = new Date();
					try {
                		mysqlJdbcTemplate.update(SQL_insert_field, new Object[] {CommonUtil.getUUID(),stdschemaobject.getSchemaCodeID(),stdschemaobject.getFieldCode(),
                				stdschemaobject.getFieldName(),stdschemaobject.getEnName(),stdschemaobject.getRequired(),
                				stdschemaobject.getMaxsize(),stdschemaobject.getType(),stdschemaobject.getPxh(),
                				stdschemaobject.getRange(),stdschemaobject.getDefination(),stdschemaobject.getMaxContainCount(),
                				stdschemaobject.getComments(),Timestamp.valueOf(formatter.format(nowdate_field)),stdschemaobject.getCreator()});
                	}catch(Exception e) {
                		e.printStackTrace();
                	}
				}
	}
	
	
	private boolean isexist(StdFieldEntity stdschemaobject){
		String sql = "select count(*) from tb_stdgl_schemafield where schemacodeid = '"+stdschemaobject.getSchemaCodeID()+"' and fieldcode = '"+stdschemaobject.getFieldCode()+"' and id !='"+stdschemaobject.getId()+"'";
		int count = 0;
		try{
			count = mysqlJdbcTemplate.queryForObject(sql,Integer.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(count > 0){
			return true;
		}else{
			return false;
		}
	} 
	
	private boolean isexistadd(StdFieldEntity stdschemaobject){
		String sql = "select count(*) from tb_stdgl_schemafield where schemacodeid = '"+stdschemaobject.getSchemaCodeID()+"' and fieldcode = '"+stdschemaobject.getFieldCode()+"'";
		int count = 0;
		try{
			count = mysqlJdbcTemplate.queryForObject(sql,Integer.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(count > 0){
			return true;
		}else{
			return false;
		}
	} 
	

    /**
     * 得到模型的详细信息(分组信息、字段列表、索引列表、分区信息)
     *
     * @param s 模型基本信息
     * @return
     */
    private StdSchemaObject getStdSchemaObjectDetail(StdSchemaObject s) {

        String sql = "select * from tb_stdgl_schemamodule where ( id = ? )";
        
        List<StdSchemaObject> schema_list = mysqlJdbcTemplate.query(sql, 
        		new Object[] {s.getId()}, 
        		new StdSchemaObjectRowMapper());

        // 如果schema不为空那么就根据schemacode查询出该schemacode下面的fields
        if(schema_list.size() == 0) {
        	return null;
        }
        
        s = schema_list.get(0);

        sql = "select * from tb_stdgl_schemafield where (schemacodeid = ? ) ORDER BY pxh";
        List<StdFieldEntity> field_list = mysqlJdbcTemplate.query(sql,
                        new Object[] {s.getId()},
                        new StdSchemaFieldRowMapper());
        s.setFields(field_list.toArray(new StdFieldEntity[0]));

        return s;
    }
    

	
	/**
	 * 批量删除标准字段
	 * 
	 * 
	 * @param s
	 *            标准信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/doplDeleteStdfieldObject", method = RequestMethod.DELETE)
	public BaseResponse<String> doplDeleteStdfieldObject(@RequestBody StdSchemaObject stdschemaobject) {
		BaseResponse<String> rfr = new BaseResponse<String>();
                
        try{
        	deleteStdSchemaObject(stdschemaobject);
        }catch(Exception e){
        	e.printStackTrace();
        	rfr.setMessage("删除失败");
			rfr.setResultCode(ResultCode.RESULT_ERROR);
        }
        rfr.setMessage("删除成功");		
		rfr.setResultCode(ResultCode.RESULT_SUCCESS);
		return rfr;
	}
	
	
	/**
	 * 批量修改标准字段的信息
	 * 
	 * 
	 * @param s
	 *            标准信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/doplUpdateStdfieldObject", method = RequestMethod.POST)
	public BaseResponse<String> doplUpdateStdSchemaObject(@RequestBody StdSchemaObject stdschemaobject) {
		BaseResponse<String> rfr = new BaseResponse<String>();
        		
        try{
        	updateStdSchemaObject(stdschemaobject);
        }catch(Exception e){
        	e.printStackTrace();
        	rfr.setMessage("修改失败");
			rfr.setResultCode(ResultCode.RESULT_ERROR);
        }
        rfr.setMessage("修改成功");		
		rfr.setResultCode(ResultCode.RESULT_SUCCESS);
		return rfr;
	}
	
	
	
	private void deleteStdSchemaObject(StdSchemaObject stdschemaobject){

		if(stdschemaobject.getFields()!=null&&stdschemaobject.getFields().length>0){
			StdFieldEntity[] stdfieldarray = stdschemaobject.getFields();
			for(StdFieldEntity stdfieldentity:stdfieldarray){			
				String sql_field = "delete from tb_stdgl_schemafield where id = '"+stdfieldentity.getId()+"'";
			    try{
			    	mysqlJdbcTemplate.update(sql_field);
			    }catch(Exception e){
			    	e.printStackTrace();
			    }	
			}
			}
	}
	
	
    private void updateStdSchemaObject(StdSchemaObject stdschemaobject){
	
    	//先将该模型所有字段的ID存入一个列表，以备在更新过程中找出被删除的字段
//		String sql_getfieldcode = "select id from tb_stdgl_schemafield where schemaCodeID = '"+stdschemaobject.getId()+"'";
//		List<String> fieldcode_list = new ArrayList<String>();
//		List<String> fieldcode_have = new ArrayList<String>();
//		try{
//			fieldcode_list = mysqlJdbcTemplate.queryForList(sql_getfieldcode, String.class);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		
		if(stdschemaobject.getFields()!=null&&stdschemaobject.getFields().length>0){
			StdFieldEntity[] stdfieldarray = stdschemaobject.getFields();
			for(StdFieldEntity stdfieldentity:stdfieldarray){
				
				if(!isexist(stdfieldentity)){
	                //在更新过程中，存在新增一条字段信息的情况，此情况id为空，需生成id，并且插入到字段信息表中
					if(stdfieldentity.getId()==null||stdfieldentity.getId().isEmpty()){
						String SQL_insert_field = "insert into tb_stdgl_schemafield values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
						Date nowdate_field = new Date();
						try {
	                		mysqlJdbcTemplate.update(SQL_insert_field, new Object[] {CommonUtil.getUUID(),stdfieldentity.getSchemaCodeID(),stdfieldentity.getFieldCode(),
	                				                                                 stdfieldentity.getFieldName(),stdfieldentity.getEnName(),stdfieldentity.getRequired(),
	                				                                                 stdfieldentity.getMaxsize(),stdfieldentity.getType(),stdfieldentity.getPxh(),
	                				                                                 stdfieldentity.getRange(),stdfieldentity.getDefination(),stdfieldentity.getMaxContainCount(),
	                				                                                 stdfieldentity.getComments(),Timestamp.valueOf(formatter.format(nowdate_field)),stdfieldentity.getCreator()});
						}catch(Exception e) {
	                		e.printStackTrace();
	                	}
					}
					else{
						//将更新后的字段id存入一个列表，和所有id列表进行对比
						//fieldcode_have.add(stdfieldentity.getId());
						//更新已存在的字段信息
						String SQL_update_field = "update tb_stdgl_schemafield set fieldName = '"+stdfieldentity.getFieldName()+
					                "',enName = '"+stdfieldentity.getEnName()+"',defination = '"+stdfieldentity.getDefination()+
					                "',type = '"+stdfieldentity.getType()+"',maxsize = '"+stdfieldentity.getMaxsize()+
					                "',required = '"+stdfieldentity.getRequired()+"',comments = '"+stdfieldentity.getComments()+
					                "',maxContainCount = "+stdfieldentity.getMaxContainCount()+",pxh = "+stdfieldentity.getPxh()+
					                ",creator = '"+stdfieldentity.getCreator()+"',fieldCode = '"+stdfieldentity.getFieldCode()+
					                "',field_range = '"+stdschemaobject.getRange()+"' where id = '"+stdfieldentity.getId()+"'";
							try {
		                		mysqlJdbcTemplate.update(SQL_update_field);
		                	}catch(Exception e) {
		                		e.printStackTrace();
		                	}	
					}	
				}			
				}
		}
		//将原本存在的字段信息从所有字段信息列表中去除掉，并将剩下的字段删除
//		fieldcode_list.removeAll(fieldcode_have);
//		for(String fieldcode_delete:fieldcode_list){
//			String sql_delete = "delete from tb_stdgl_schemafield where id = '"+fieldcode_delete+"'";
//			try{
//				mysqlJdbcTemplate.update(sql_delete);
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
		
	}



}