package com.jn.primiary.standard.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jn.primiary.beyondsoft.dao.FieldRepository;
import com.jn.primiary.beyondsoft.dao.ObjectRepository;
import com.jn.primiary.beyondsoft.entity.StandardObject;
import com.jn.primiary.metadata.dao.ModelFieldRepository;
import com.jn.primiary.metadata.dao.ModelRepository;
import com.jn.primiary.metadata.entity.Model;
import com.jn.primiary.metadata.entity.ModelField;






@Service
public class WriteInfoService {
	@Autowired
	 private ModelRepository modelRepository;
	
	@Autowired
	 private ObjectRepository objectRepository;
	
	@Autowired
	 private ModelFieldRepository modelFieldRepository;
	
	
	
	@Autowired
	 private FieldRepository fieldRepository;
	/**
	 * @Title: submitUpdateStdRequest  
	 * @Description:提交标准修改申请
	 * @param 
	 * @return 
	 * @throws
	 */
	@Transactional
	public void writeobjectcninfo() {
		// 生成标准请求
		List<Model> listnocnmodel=modelRepository.findModelnocnname();
		if(listnocnmodel.size()>0){
			for(Model model:listnocnmodel){
				String enname=model.getEnName();
				String selname=enname;
				if(enname.contains("hs_metadata_")){
					selname=enname.substring(12);
				}
				if(enname.contains("metadata_")){
					selname=enname.substring(9);
				}
				if(enname.contains("t_")){
					selname=enname.substring(2);
				}
				List<StandardObject> stand=objectRepository.findByShortName(selname);
				if(stand!=null){
					if(stand.size()>0){
						if(stand.size()==1){//说明在复合型中找到一个相符的情况
							modelRepository.updateCnname(stand.get(0).getObjCname(), enname);
						}
						else{
							modelRepository.updateCnname(stand.get(0).getObjCname(), enname);
						}
					}
				}
				
			}
		}
		
	}
	
	
	@Transactional
	public void writefieldcninfo() {
		List<ModelField> listfield=modelFieldRepository.findFieldnocnname();
		if(listfield.size()>0){
			for(ModelField field:listfield){
				List<String> listcnname=fieldRepository.findByFieldCode(field.getCode());
				if(listcnname.size()>0){
					if(listcnname.size()==1){
						modelFieldRepository.updateCnname(listcnname.get(0), field.getCode());
					}
				}
			}
		}
	}
	
}	
	
	
	
	