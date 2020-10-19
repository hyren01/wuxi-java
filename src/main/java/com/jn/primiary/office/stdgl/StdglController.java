package com.jn.primiary.office.stdgl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jn.primiary.metadata.utils.CommonUtil;
import com.jn.primiary.office.entity.DocFieldCheckResult;
import com.jn.primiary.office.entity.DocModel;
import com.jn.primiary.office.entity.DocModelField;
import com.jn.primiary.office.entity.DocStdModelField;
import com.jn.primiary.office.service.DocModelService;
import com.jn.primiary.office.service.StdModelService;





@Controller
@RequestMapping("stdglprj/office/stdgl")
public class StdglController {
	 @Autowired
	 private DocModelService modelService;
	 
	 
	 @Autowired
	 private StdModelService stdmodelService;
	 
	 
	 @RequestMapping(value = "/jxupdate")
	 @ResponseBody
     public void addjxstd() throws IOException {   	
		 String stdname="SJB Y5-2018 JX信号元数据工程应用要求（V2.0）";
		 List<DocModel> listmodel=modelService.findByStdsource(stdname);
		
		 for(DocModel model:listmodel){
			 List<DocStdModelField> stdfields=stdmodelService.findjxall(model.getDescription());
			if(stdfields!=null&&stdfields.size()>0){
				List<DocModelField> modelfields=modelService.findByModelid(model.getId());		
				for(DocModelField field:modelfields){
					String cname=field.getName();
					for(DocStdModelField stdfield:stdfields){
						if(cname.equals(stdfield.getcName())){
							DocFieldCheckResult result=getCheckResult(field,stdfield,stdname);
							field.setFieldcheckresult(result);
							modelService.addcheck(result);
						}
					}
					
				}
				for(DocModelField field:modelfields){
					if(field.getFieldcheckresult()==null){
						DocFieldCheckResult result=getNoCheckResult(field,stdname);
						field.setFieldcheckresult(result);
						modelService.addcheck(result);					
					}
					
				}
				
			}
			
		 }
		 
		 System.out.println("写入数据库完成");
	     return ;
	 }
	 
	 
	 @RequestMapping(value = "/chaoduanupdate")
	 @ResponseBody
     public void addchaoduanbostd() throws IOException {   	
		 String stdname="SJB Y7-2018 超短波信号元数据工程应用要求（V2.0）";
		 List<DocModel> listmodel=modelService.findByStdsource(stdname);
		
		 for(DocModel model:listmodel){
			 List<DocStdModelField> stdfields=stdmodelService.findcomplexall(model.getDescription());
			if(stdfields!=null&&stdfields.size()>0){
				List<DocModelField> modelfields=modelService.findByModelid(model.getId());		
				for(DocModelField field:modelfields){
					String cname=field.getName();
					for(DocStdModelField stdfield:stdfields){
						if(cname.equals(stdfield.getcName())){
							DocFieldCheckResult result=getCheckResult(field,stdfield,stdname);
							DocFieldCheckResult ser=stdmodelService.findByFieldid(result.getModelfield().getId());
							if(ser==null){
								field.setFieldcheckresult(result);
								modelService.addcheck(result);
							}
						}
					}
					
				}
				for(DocModelField field:modelfields){
					if(field.getFieldcheckresult()==null){
						DocFieldCheckResult result=getNoCheckResult(field,stdname);
						DocFieldCheckResult ser=stdmodelService.findByFieldid(result.getModelfield().getId());
						if(ser==null){
							field.setFieldcheckresult(result);
							modelService.addcheck(result);
						}				
					}
					
				}
				
			}
			
		 }
		 
		 System.out.println("写入数据库完成");
	     return ;
	 }
	 
	 
	 @RequestMapping(value = "/dianziupdate")
	 @ResponseBody
     public void dianziupdate() throws IOException {   	
		 String stdname="SJB Y8-2018 电子信号元数据工程应用要求（V2.0）";
		 List<DocModel> listmodel=modelService.findByStdsource(stdname);
		
		 for(DocModel model:listmodel){
			 List<DocStdModelField> stdfields=stdmodelService.findduanall(model.getDescription());
			if(stdfields!=null&&stdfields.size()>0){
				List<DocModelField> modelfields=modelService.findByModelid(model.getId());		
				for(DocModelField field:modelfields){
					String cname=field.getName();
					for(DocStdModelField stdfield:stdfields){
						if(cname.equals(stdfield.getcName())){
							DocFieldCheckResult result=getCheckResult(field,stdfield,stdname);
							field.setFieldcheckresult(result);
							modelService.addcheck(result);
						}
					}
					
				}
				for(DocModelField field:modelfields){
					if(field.getFieldcheckresult()==null){
						DocFieldCheckResult result=getNoCheckResult(field,stdname);
						field.setFieldcheckresult(result);
						modelService.addcheck(result);					
					}
					
				}
				
			}
			
		 }
		 
		 System.out.println("写入数据库完成");
	     return ;
	 }
	 
	 
	 
	 
	 @RequestMapping(value = "/duanupdate")
	 @ResponseBody
     public void addduan() throws IOException {   	
		 String stdname="SJB Y1-2018 短波信号元数据工程应用要求（V2.0）";
		 List<DocModel> listmodel=modelService.findByStdsource(stdname);
		
		 for(DocModel model:listmodel){
			 List<DocStdModelField> stdfields=stdmodelService.findduanall(model.getDescription());
			if(stdfields!=null&&stdfields.size()>0){
				List<DocModelField> modelfields=modelService.findByModelid(model.getId());		
				for(DocModelField field:modelfields){
					String cname=field.getName();
					for(DocStdModelField stdfield:stdfields){
						if(cname.equals(stdfield.getcName())){
							DocFieldCheckResult result=getCheckResult(field,stdfield,stdname);
							
							field.setFieldcheckresult(result);
							modelService.addcheck(result);
						}
					}
					
				}
				for(DocModelField field:modelfields){
					if(field.getFieldcheckresult()==null){
						DocFieldCheckResult result=getNoCheckResult(field,stdname);
						field.setFieldcheckresult(result);
						modelService.addcheck(result);					
					}
					
				}
				
			}
			
		 }
		 
		 System.out.println("写入数据库完成");
	     return ;
	 }
	 
	 
	 @RequestMapping(value = "/hangtianupdate")
	 @ResponseBody
     public void addhuantian() throws IOException {   	
		 String stdname="SJB Y13-2018 航天侦察元数据工程应用要求（V1.0）";
		 List<DocModel> listmodel=modelService.findByStdsource(stdname);
		
		 for(DocModel model:listmodel){
			 List<DocStdModelField> stdfields=stdmodelService.findduanall(model.getDescription());
			if(stdfields!=null&&stdfields.size()>0){
				List<DocModelField> modelfields=modelService.findByModelid(model.getId());		
				for(DocModelField field:modelfields){
					String cname=field.getName();
					for(DocStdModelField stdfield:stdfields){
						if(cname.equals(stdfield.getcName())){
							DocFieldCheckResult result=getCheckResult(field,stdfield,stdname);
							
							field.setFieldcheckresult(result);
							modelService.addcheck(result);
						}
					}
					
				}
				for(DocModelField field:modelfields){
					if(field.getFieldcheckresult()==null){
						DocFieldCheckResult result=getNoCheckResult(field,stdname);
						field.setFieldcheckresult(result);
						modelService.addcheck(result);					
					}
					
				}
				
			}
			
		 }
		 
		 System.out.println("写入数据库完成");
	     return ;
	 }
	 
	 
	 private DocFieldCheckResult getCheckResult(DocModelField field,DocStdModelField stdfield,String stdname)
	 {
		 DocFieldCheckResult result=new DocFieldCheckResult();
		 
		 result.setId(CommonUtil.getUUID());
		 result.setModelfield(field);		 	 
	     result.setCheckresult("符合");	 
		 result.setStdname(stdname);		
		 result.setStdproperty(stdfield.getId());		 
		 result.setDetail("完全符合");	
		 return result;
	 }
	 
	 
	 private DocFieldCheckResult getNoCheckResult(DocModelField field,String stdname)
	 {
		 DocFieldCheckResult result=new DocFieldCheckResult();
		 
		 result.setId(CommonUtil.getUUID());
		 result.setModelfield(field);		 	 
	     result.setCheckresult("不符合");	 		 
	     result.setDetail("标准中无此字段");	
		 return result;
	 }
	 
	
	 
	
}
