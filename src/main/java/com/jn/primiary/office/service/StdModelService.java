package com.jn.primiary.office.service;

import java.util.List;

import com.jn.primiary.office.dao.*;
import com.jn.primiary.office.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class StdModelService {
	 @Autowired
	 private OperStdModleRepository modulerepository;
	 @Autowired
	 private OperStdModelFieldRepository modelfieldrepository;
	 @Autowired
	 private OperStdModelObjRepository modelObjRepository;
	 @Autowired
	 private DocModelService modelService;
	 @Autowired
	 private DocCodeInfoService docCodeInfoService;
	 //@Autowired
	 //private DocFieldCheckRepository fieldcheckrepository;

	/*@Transactional
	 public void add(OperDocStdModel model) {
		 modulerepository.save(model);
		 if(model.getObjfields().size()>0){
			 for(OperDocStdModelObj obj:model.getObjfields()){
				 modelObjRepository.save(obj);
			 }
		 }

		 for(OperDocStdModelField field :model.getFields()){
			 modelfieldrepository.save(field);
		 }

	 }*/

	@Transactional
	public void add(List<OperDocStdModel> liststd,List<OperDocStdCodeInfo> listcodeinfo,TbStdglFiletable filetable) {
		modelService.persitfile(filetable);
		modulerepository.save(liststd);

		for (OperDocStdModel std:liststd) {
			modelObjRepository.save(std.getObjfields());
			modelfieldrepository.save(std.getFields());

		}

		for(int i=0;i<listcodeinfo.size();i++){
			docCodeInfoService.addCodeInfo(listcodeinfo);
		}
	}
	 
	 
	 public List<DocStdModelField> findjxall(String description) {
	 	return null;
		 //return modelfieldrepository.findjxall(description);
	 }
	 
	 
	 public List<DocStdModelField> findcomplexall(String description) {
		 return null;
		 //return modelfieldrepository.findcomplexall(description);
	 }
	 
	 
	 
	 public List<DocStdModelField> findduanall(String description) {
		 return null;
		 //return modelfieldrepository.findduanall(description);
	 }
	 
	 
	 
	 public DocFieldCheckResult findByFieldid(String fieldid){
		 return null;
	 	//return fieldcheckrepository.findByFieldid(fieldid);
	 }

}
