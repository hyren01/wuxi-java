package com.jn.primiary.office.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jn.primiary.beyondsoft.dao.*;
import com.jn.primiary.beyondsoft.entity.*;
import com.jn.primiary.beyondsoft.util.ComUtil;
import com.jn.primiary.metadata.utils.CommonUtil;
import com.jn.primiary.metadata.utils.StringUtils;
import com.jn.primiary.office.dao.*;
import com.jn.primiary.office.entity.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import javax.transaction.Transactional;


@Transactional
@Service
public class DocModelService {
	
	 @Autowired
	 private StdModuleRepository modulerepository;
	 
	 @Autowired
	 private DocModelFieldRepository modelfieldrepository;
	 
	 @Autowired
	 private DocFieldCheckRepository fieldcheckrepository;

	 @Autowired
	 private TbStdglFiletableRepository  filetableRepository;

	@Autowired
	private OperStdModleRepository operStdModleRepository;

	@Autowired
	private OperStdCodeInfoRepository operStdCodeInfoRepository;

	@Autowired
	private OperStdModelFieldRepository operStdModelFieldRepository;

	@Autowired
	private OperStdModelObjRepository operStdModelObjRepository;

	@Autowired
	private ModuleRepository moduleRepository;

	@Autowired
	private ObjectRepository objectRepository;

	@Autowired
	private FieldRepository fieldRepository;

	@Autowired
	private OperCodeInfoRepository operCodeInfoRepository;

	@Autowired
	private CodeInfoRepository codeInfoRepository;

	 //文件信息保存入库
	 public TbStdglFiletable persitfile(TbStdglFiletable filetable){
		 TbStdglFiletable table = filetableRepository.save(filetable);
		 return table;
	 }

	 //页面编辑按钮，返回标准和码表信息
	 public List<OperDocStdModel> editfile_std(String file_id){
		 List<OperDocStdModel> std_mess = operStdModleRepository.get_std_mess_status(file_id);
		 return std_mess;
	 }

	//页面编辑按钮，返回码表信息
	public List<OperDocStdCodeInfo> editfile_codeinfo(String file_id){
		List<OperDocStdCodeInfo> codeinfo_mess = operStdCodeInfoRepository.get_codeinfo_mess(file_id);
		return codeinfo_mess;
	}

	//根据标准id查询标准字段
	public OperDocStdModel getfieldbyid(String std_id){
		OperDocStdModel std = operStdModleRepository.get_std_by_idandstatus(std_id);

		List<OperDocStdModelObj> std_obj_mess = operStdModelObjRepository.getobjbyidandstatus(std_id,std.getBatch_no());
		if(std_obj_mess.size()>0){
			//对象
			for (OperDocStdModelObj objmess :std_obj_mess) {
				String obj_id = objmess.getObj_id();
				List<OperDocStdModelField> fieldmess = operStdModelFieldRepository.getfieldbyobjidandstatus(obj_id,objmess.getBatch_no());
				objmess.setFields(fieldmess);
			}
			std.setObjfields(std_obj_mess);
		}else{
			List<OperDocStdModelField> fieldlist = operStdModelFieldRepository.getfieldbyidandstatus(std_id,std.getBatch_no());
			std.setFields(fieldlist);
		}
		return std;
	}

	public Standard getfieldbyid_resulttable(String std_id){
		Standard std = moduleRepository.getstdmessbyidandstatus(std_id);

		List<StandardObject> std_obj_mess = objectRepository.getObjectById(std_id);
		if(std_obj_mess.size()>0){
			//对象
			for (StandardObject objmess :std_obj_mess) {
				String obj_id = objmess.getObjId();
				List<StandardField> fieldmess = fieldRepository.getFieldById(std_id, obj_id);
				objmess.setStandardField(fieldmess);
			}
			std.setStandardObject(std_obj_mess);
		}else{
			List<StandardField> fieldList = fieldRepository.getfieldbyidandstatus(std_id);
			std.setStandardField(fieldList);
		}
		return std;
	}

	//删除文档
	public void delfile(TbStdglFiletable file_entity){
	 	if(file_entity.getStatus()==1){
			file_entity.setStatus(Status.SHIXIAO.getCode());
			filetableRepository.save(file_entity);

			List<OperDocStdModel> stdlist = operStdModleRepository.get_std_mess(file_entity.getFile_id());
			for (OperDocStdModel std:stdlist) {
				std.setStatus(Status.SHIXIAO.getCode());
				std.setOper_flag(DataType.DELETE.getCode());
				operStdModleRepository.save(std);

				List<OperDocStdModelObj> objlist = operStdModelObjRepository.getobjbyid(std.getStd_id());
				if(objlist.size()>0){
					for(OperDocStdModelObj obj : objlist){
						obj.setStatus(Status.SHIXIAO.getCode());
						obj.setOper_flag(DataType.DELETE.getCode());
						operStdModelObjRepository.save(obj);
					}
				}

				List<OperDocStdModelField> fieldlist = operStdModelFieldRepository.getfieldbyid(std.getStd_id());
				if(fieldlist.size()>0){
					for(OperDocStdModelField field : fieldlist){
						field.setStatus(Status.SHIXIAO.getCode());
						field.setOper_flag(DataType.DELETE.getCode());
						operStdModelFieldRepository.save(field);
					}
				}

			}

			List<OperDocStdCodeInfo> codeinfolist = operStdCodeInfoRepository.get_codeinfo_mess(file_entity.getFile_id());
			if(codeinfolist.size()>0){
				for(OperDocStdCodeInfo codeinfo : codeinfolist){
					codeinfo.setStatus(Status.SHIXIAO.getCode());
					operStdCodeInfoRepository.save(codeinfo);
				}
			}
		}

	}

	//根据id获取文件信息
	public TbStdglFiletable getfilemessbyid(String file_id){
		TbStdglFiletable filemess = filetableRepository.getfilemessbyid(file_id);
		return filemess;
	}

	public void savefile(OperFileMess operFileMess,String username){
	 	String file_id = operFileMess.getFile_id();
	 	//上一次操作的文件，变为失效
		TbStdglFiletable oldfilemess = filetableRepository.getfilemessbyid(file_id);
		oldfilemess.setStatus(Status.SHIXIAO.getCode());
		filetableRepository.save(oldfilemess);

		List<OperDocStdModel> std_mess = operFileMess.getStd_mess();
		for(OperDocStdModel std: std_mess){
			List<OperDocStdModelObj> objlist = operStdModelObjRepository.getobjbyidandstatus(std.getStd_id(),std.getBatch_no());
			if(objlist.size()>0){
				for (OperDocStdModelObj obj : objlist) {
					List<OperDocStdModelField> fieldlist = operStdModelFieldRepository.getfieldbyobjidandstatus(obj.getObj_id(),obj.getBatch_no());
					obj.setFields(fieldlist);
				}
				std.setObjfields(objlist);
			}else{
				List<OperDocStdModelField> fieldlist = operStdModelFieldRepository.getfieldbyidandstatus(std.getStd_id(),std.getBatch_no());
				std.setFields(fieldlist);
			}

		}

		for(OperDocStdModel std:std_mess){
			std.setStatus(Status.SHIXIAO.getCode());
			operStdModleRepository.save(std);
			List<OperDocStdModelObj> objList = std.getObjfields();
			if(objList.size()>0){
				for(OperDocStdModelObj obj : objList){
					obj.setStatus(Status.SHIXIAO.getCode());
					operStdModelObjRepository.save(obj);
				}
			}

			List<OperDocStdModelField> fieldlist = std.getFields();
			for(OperDocStdModelField field : fieldlist){
				field.setStatus(Status.SHIXIAO.getCode());
				operStdModelFieldRepository.save(field);
			}
		}


		List<OperDocStdCodeInfo> codeinfo_mess = operFileMess.getCodeinfo_mess();
		for(OperDocStdCodeInfo codeinfo : codeinfo_mess){
			codeinfo.setStatus(Status.SHIXIAO.getCode());
			operStdCodeInfoRepository.save(codeinfo);
		}


		//新增一条新的文件记录
		TbStdglFiletable newfile = new TbStdglFiletable();
		BeanUtils.copyProperties(oldfilemess,newfile);
		newfile.setFile_id(CommonUtil.getUUID());
		newfile.setStatus(Status.JIHUO.getCode());
		filetableRepository.save(newfile);

		String batch_no = ComUtil.getBatchNo();
		for(OperDocStdModel std:std_mess){
			OperDocStdModel newstd = new OperDocStdModel();
			BeanUtils.copyProperties(std,newstd);
			newstd.setStd_id(CommonUtil.getUUID());
			newstd.setBatch_no(batch_no);
			newstd.setFile_id(newfile.getFile_id());
			newstd.setStatus(Status.JIHUO.getCode());
			newstd.setUpdate_time(new Date());
			newstd.setUpdate_person(username);
			operStdModleRepository.save(newstd);

			List<OperDocStdModelObj> objList = std.getObjfields();
			if(objList.size()>0){
				for(OperDocStdModelObj obj : objList){
					OperDocStdModelObj newobj = new OperDocStdModelObj();
					BeanUtils.copyProperties(obj,newobj);
					newobj.setObj_id(CommonUtil.getUUID());
					newobj.setModel_id(newstd.getStd_id());
					newobj.setBatch_no(batch_no);
					newobj.setStatus(Status.JIHUO.getCode());
					operStdModelObjRepository.save(newobj);

					List<OperDocStdModelField> fieldlsit = obj.getFields();
					for(OperDocStdModelField field : fieldlsit){
						OperDocStdModelField newfield = new OperDocStdModelField();
						BeanUtils.copyProperties(field,newfield);
						newfield.setObj_id(newobj.getObj_id());
						newfield.setField_id(CommonUtil.getUUID());
						newfield.setModel_id(newstd.getStd_id());
						newfield.setBatch_no(batch_no);
						newfield.setStatus(Status.JIHUO.getCode());
						operStdModelFieldRepository.save(newfield);
					}
				}
			}else{
				List<OperDocStdModelField> fieldlist = std.getFields();
				for(OperDocStdModelField field : fieldlist){
					OperDocStdModelField newfield = new OperDocStdModelField();
					BeanUtils.copyProperties(field,newfield);
					newfield.setField_id(CommonUtil.getUUID());
					newfield.setModel_id(newstd.getStd_id());
					newfield.setBatch_no(batch_no);
					newfield.setStatus(Status.JIHUO.getCode());
					operStdModelFieldRepository.save(newfield);
				}
			}
		}

		//新增一条码表信息
		for(OperDocStdCodeInfo codeinfo : codeinfo_mess){
			OperDocStdCodeInfo newcodeinfo = new OperDocStdCodeInfo();
			BeanUtils.copyProperties(codeinfo,newcodeinfo);
			newcodeinfo.setBatch_no(batch_no);
			newcodeinfo.setFile_id(newfile.getFile_id());
			newcodeinfo.setCode_id(CommonUtil.getUUID());
			newcodeinfo.setStatus(Status.JIHUO.getCode());
			operStdCodeInfoRepository.save(newcodeinfo);
		}

	}

	public List<TbStdglFiletable> fileindex(){
		List<TbStdglFiletable> filelist = filetableRepository.getfilemessbystatus(Status.JIHUO.getCode());
		int is_retract = BooleanFlag.FOU.getCode();

		for (TbStdglFiletable file : filelist) {
			List<OperDocStdModel> DSH_list = new ArrayList<>();
			List<OperDocStdModel> SHTG_list = new ArrayList<>();
			List<OperDocStdModel> SHJJ_list = new ArrayList<>();

			List<OperDocStdModel> stdlist = operStdModleRepository.get_std_mess_status(file.getFile_id());
			if(stdlist.size()>0){
				//标准和码表是否全部通过
				for (OperDocStdModel std : stdlist) {
					if(std.getAuth_status() == AuthType.DSH.getCode()){
						DSH_list.add(std);
					}else if(std.getAuth_status() == AuthType.SHTG.getCode()){
						SHTG_list.add(std);
					}else if(std.getAuth_status() == AuthType.SHJJ.getCode()){
						SHJJ_list.add(std);
					}
				}

				if(DSH_list.size() == stdlist.size()){
					file.setAuth_status(AuthType.DSH.getCode());
					is_retract = BooleanFlag.SHI.getCode();
					file.setIs_retract(is_retract);
					filetableRepository.save(file);
					continue;
				}

				if(SHTG_list.size() == stdlist.size()){
					file.setAuth_status(AuthType.SHTG.getCode());
					file.setIs_retract(is_retract);
					filetableRepository.save(file);
					continue;
				}

				if(SHJJ_list.size() == stdlist.size()){
					file.setAuth_status(AuthType.SHJJ.getCode());
					file.setStatus(Status.SHIXIAO.getCode());
					file.setIs_retract(is_retract);
					filetableRepository.save(file);
					continue;
				}

				file.setIs_retract(is_retract);
				filetableRepository.save(file);
			}
		}
		return filelist;
	}

	@Transactional(rollbackOn = Exception.class)
	public void fileapply (String category_id,String file_id,String username) throws Exception {
	 	ArrayList<OperDocStdModelObj> result_objlist= new ArrayList<>();
		ArrayList<OperDocStdModelField> result_fieldlist= new ArrayList<>();

	 	//判断

		TbStdglFiletable oldfile = filetableRepository.getfilemessbyid(file_id);
		oldfile.setStatus(Status.SHIXIAO.getCode());
		filetableRepository.save(oldfile);

		TbStdglFiletable newfile = new TbStdglFiletable();
		BeanUtils.copyProperties(oldfile,newfile);
		newfile.setFile_id(CommonUtil.getUUID());
		newfile.setStatus(Status.JIHUO.getCode());
		newfile.setAuth_status(AuthType.DSH.getCode());
		newfile.setUpdate_person(username);
		newfile.setUpdate_time(new Date());
		filetableRepository.save(newfile);

	 	//文档下的标准
	 	List<OperDocStdModel> oldstdlist = operStdModleRepository.get_std_mess(file_id);
		for (OperDocStdModel std : oldstdlist) {
			if(StringUtils.isEmpty(std.getCode())){
				throw new Exception("标准的短名不能为空："+std.getCname());
			}
		}
		String batch_no = ComUtil.getBatchNo();

	 	if(oldstdlist.size()>0) {
			//所有激活的标准信息
			List<Standard> allStandardlist = moduleRepository.getAllStandardInfo();
			for (OperDocStdModel oldstd : oldstdlist) {
				boolean is_repeat_flag = false;
				Standard repeat_activestd = new Standard();
				for (Standard activestd : allStandardlist) {
					if (activestd.getCode().equals(oldstd.getCode())) {
						is_repeat_flag = true;
						repeat_activestd = activestd;
						break;
					}
				}


				String std_id = oldstd.getStd_id();
				String old_batch_no = oldstd.getBatch_no();
				//对象
				List<OperDocStdModelObj> objlist = operStdModelObjRepository.getobjbyidandstatus(std_id, old_batch_no);
					List<OperDocStdModelField> fieldlist = new ArrayList<>();
					if (objlist.size() > 0) {
						for (OperDocStdModelObj obj : objlist) {
							if(StringUtils.isEmpty(obj.getCode())){
								throw new Exception("标准下的对象类型的短名不能为空："+ oldstd.getCname()+";对象名："+obj.getCname());
							}
						}

					for (OperDocStdModelObj obj : objlist) {
						fieldlist = operStdModelFieldRepository.getfieldbyobjidandstatus(obj.getObj_id(), obj.getBatch_no());
						obj.setFields(fieldlist);
					}
				} else {
					//字段
					fieldlist = operStdModelFieldRepository.getfieldbyidandstatus(std_id, old_batch_no);
					for (OperDocStdModelField field : fieldlist) {
						if(StringUtils.isEmpty(field.getCode())){
							throw new Exception("标准下的字段的短名不能为空："+ oldstd.getCname()+";字段名："+field.getCname());
						}
					}
				}


				operStdModleRepository.update_status(Status.SHIXIAO.getCode(),oldstd.getBatch_no(),oldstd.getStd_id());
				if (objlist.size() > 0) {
					for (OperDocStdModelObj obj : objlist) {
						//obj.setStatus(Status.SHIXIAO.getCode());
						operStdModelObjRepository.update_status(Status.SHIXIAO.getCode(),obj.getBatch_no(),obj.getObj_id());
					}
				}

				for (OperDocStdModelField field : fieldlist) {
					//field.setStatus(Status.SHIXIAO.getCode());
					operStdModelFieldRepository.update_status(Status.SHIXIAO.getCode(),field.getBatch_no(),field.getField_id());
				}

				//重新添加记录
				OperDocStdModel newstd = new OperDocStdModel();
				BeanUtils.copyProperties(oldstd, newstd);
				if (is_repeat_flag == true) {
					//如果新增的标准code，和已经激活的其中一个标准的code相同的话，就是一个新版本
					newstd.setStd_id(repeat_activestd.getId());
					newstd.setOper_flag(DataType.UPDATE.getCode());
					String newversion = moduleRepository.getstdbycode(newstd.getCode());
					newstd.setVersion(String.valueOf(Integer.parseInt(newversion) + 1));
				} else {
					newstd.setStd_id(CommonUtil.getUUID());
					newstd.setOper_flag(DataType.ADD.getCode());
					newstd.setVersion("1");
				}

				newstd.setBatch_no(batch_no);
				newstd.setCategory_id(category_id);
				newstd.setStatus(Status.JIHUO.getCode());
				newstd.setAuth_status(AuthType.DSH.getCode());
				newstd.setFile_id(newfile.getFile_id());
				newstd.setUpdate_person(username);
				newstd.setUpdate_time(new Date());
				operStdModleRepository.savestdbysql(newstd);


				if (objlist.size() > 0) {
					for (OperDocStdModelObj obj : objlist) {
						OperDocStdModelObj newobj = new OperDocStdModelObj();
						BeanUtils.copyProperties(obj, newobj);
						newobj.setObj_id(CommonUtil.getUUID());
						newobj.setModel_id(newstd.getStd_id());
						newobj.setBatch_no(batch_no);
						newobj.setStatus(Status.JIHUO.getCode());
						if (is_repeat_flag == true) {
							newobj.setOper_flag(DataType.UPDATE.getCode());
						} else {
							newobj.setOper_flag(DataType.ADD.getCode());
						}
						//operStdModelObjRepository.save(newobj);
						result_objlist.add(newobj);

						List<OperDocStdModelField> fieldList = obj.getFields();
						if (fieldList.size() > 0) {
							for (OperDocStdModelField field : fieldList) {
								OperDocStdModelField newfield = new OperDocStdModelField();
								BeanUtils.copyProperties(field, newfield);
								newfield.setField_id(CommonUtil.getUUID());
								newfield.setModel_id(newstd.getStd_id());
								newfield.setObj_id(newobj.getObj_id());
								newfield.setStatus(Status.JIHUO.getCode());
								newfield.setBatch_no(batch_no);
								if (is_repeat_flag == true) {
									newfield.setOper_flag(DataType.UPDATE.getCode());
								} else {
									newfield.setOper_flag(DataType.ADD.getCode());
								}

								//operStdModelFieldRepository.save(newfield);
								result_fieldlist.add(newfield);
							}
						}
					}
				} else {
					for (OperDocStdModelField field : fieldlist) {
						OperDocStdModelField newfield = new OperDocStdModelField();
						BeanUtils.copyProperties(field, newfield);
						newfield.setField_id(CommonUtil.getUUID());
						newfield.setBatch_no(batch_no);
						newfield.setModel_id(newstd.getStd_id());
						newfield.setStatus(Status.JIHUO.getCode());
						if (is_repeat_flag == true) {
							newfield.setOper_flag(DataType.UPDATE.getCode());
						} else {
							newfield.setOper_flag(DataType.ADD.getCode());
						}
						//operStdModelFieldRepository.save(newfield);
						result_fieldlist.add(newfield);
					}
				}
			}
		}

		operStdModelObjRepository.save(result_objlist);
	 	operStdModelFieldRepository.save(result_fieldlist);



		//码表
		ArrayList<OperDocStdCodeInfo> reuslt_codeinfolist = new ArrayList<>();
		List<OperDocStdCodeInfo> codeinfolist = operStdCodeInfoRepository.get_codeinfo_mess_bystatus(file_id);
		if(codeinfolist.size()>0) {
			for (OperDocStdCodeInfo codeInfo : codeinfolist) {
				if(StringUtils.isEmpty(codeInfo.getCodetable_ename())){
					throw new Exception("码表英文名不能为空");
				}
			}

			List<String> active_codeinfoenamelist = operCodeInfoRepository.getcodetableename();
			//try {
			if (active_codeinfoenamelist.size() > 0) {
				//判断操作的码表的英文名和正式表的码表英文名是否重复
				for (OperDocStdCodeInfo oper_codeinfo : codeinfolist) {
					for (String active_ename : active_codeinfoenamelist) {
						if (oper_codeinfo.getCodetable_ename().equals(active_ename)) {
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							throw new Exception("该码值英文名与正式码表的英文名重复：" + active_ename + ",请修改后再提交");
						}
					}
				}
			}

			if (codeinfolist.size() > 0) {
				for (OperDocStdCodeInfo codeinfo : codeinfolist) {
					//codeinfo.setStatus(Status.SHIXIAO.getCode());
					operStdCodeInfoRepository.update_status(Status.SHIXIAO.getCode(),codeinfo.getBatch_no(),codeinfo.getCode_id());
				}
			}

			for (OperDocStdCodeInfo codeinfo : codeinfolist) {
				OperDocStdCodeInfo newcode = new OperDocStdCodeInfo();
				BeanUtils.copyProperties(codeinfo, newcode);
				newcode.setBatch_no(batch_no);
				newcode.setCode_id(CommonUtil.getUUID());
				newcode.setStatus(Status.JIHUO.getCode());
				newcode.setAuth_status(AuthType.DSH.getCode());
				newcode.setUpdate_user(username);
				newcode.setUpdate_time(new Date());
				newcode.setFile_id(newfile.getFile_id());
				//operStdCodeInfoRepository.save(newcode);
				reuslt_codeinfolist.add(newcode);
			}
		}
		operStdCodeInfoRepository.save(reuslt_codeinfolist);
	}


	public void retractapply(String file_id){
		TbStdglFiletable filetable = filetableRepository.getfilemessbyid(file_id);
		filetable.setAuth_status(AuthType.DSQ.getCode());
		filetableRepository.save(filetable);

		List<OperDocStdModel> std_list = operStdModleRepository.get_std_mess(file_id);
		for (OperDocStdModel std:std_list) {
			operStdModleRepository.update_authtype(AuthType.DSQ.getCode(),std.getBatch_no(),std.getStd_id());
		}

		List<OperDocStdCodeInfo> codelist = operStdCodeInfoRepository.get_codeinfo_mess_bystatus(file_id);
		for (OperDocStdCodeInfo codeInfo : codelist) {
			operStdCodeInfoRepository.update_authtype(AuthType.DSQ.getCode(),codeInfo.getBatch_no(),codeInfo.getCode_id());
		}
	}

	public TbStdglFiletable findfilenamebyid(String file_id){
		return filetableRepository.getfilemessbyid(file_id);
	}


	public void savestd(OperDocStdModel operDocStdModel){
	 	String std_id = operDocStdModel.getStd_id();
	 	String batch_no = ComUtil.getBatchNo();

		List<OperDocStdModelObj> oldobjlist = new ArrayList<>();
		List<OperDocStdModelField> oldfieldlist = new ArrayList<>();
		if(operDocStdModel.getObjfields().size()>0) {
			oldobjlist = operStdModelObjRepository.getobjbyidandstatus(std_id, operDocStdModel.getBatch_no());
			for (OperDocStdModelObj oldobj : oldobjlist) {
				oldfieldlist = operStdModelFieldRepository.getfieldbyobjidandstatus(oldobj.getObj_id(), oldobj.getBatch_no());
			}
		}else{
			oldfieldlist = operStdModelFieldRepository.getfieldbyidandstatus(std_id,operDocStdModel.getBatch_no());
		}

		operStdModleRepository.delstdbyidandbatch(std_id,operDocStdModel.getBatch_no());
		if(oldobjlist.size()>0){
			for (OperDocStdModelObj oldobj : oldobjlist) {
				for (OperDocStdModelField oldfield : oldfieldlist) {
					operStdModelFieldRepository.delfieldbyidandbatchno(oldfield.getField_id(),operDocStdModel.getBatch_no());
				}
				operStdModelObjRepository.delobjbyobjidandbatchno(oldobj.getObj_id(),operDocStdModel.getBatch_no());
			}
		}else{
			for (OperDocStdModelField oldfield : oldfieldlist) {
				operStdModelFieldRepository.delfieldbyidandbatchno(oldfield.getField_id(),operDocStdModel.getBatch_no());
			}
		}

		//新增记录
		operDocStdModel.setStatus(Status.JIHUO.getCode());
		operDocStdModel.setStd_id(std_id);
		operStdModleRepository.savestdbysql(operDocStdModel);

		List<OperDocStdModelObj> newobjlist = operDocStdModel.getObjfields();
		if(oldobjlist.size()>0){
			for (OperDocStdModelObj newobj: newobjlist) {
				newobj.setBatch_no(operDocStdModel.getBatch_no());
				newobj.setObj_id(CommonUtil.getUUID());
				newobj.setStatus(Status.JIHUO.getCode());
				newobj.setOper_flag(DataType.UPDATE.getCode());
				newobj.setModel_id(operDocStdModel.getStd_id());
				operStdModelObjRepository.save(newobj);
				List<OperDocStdModelField> newfieldlist = newobj.getFields();
				for (OperDocStdModelField newfield : newfieldlist) {
					newfield.setBatch_no(operDocStdModel.getBatch_no());
					newfield.setObj_id(newobj.getObj_id());
					newfield.setField_id(CommonUtil.getUUID());
					newfield.setStatus(Status.JIHUO.getCode());
					newfield.setOper_flag(DataType.UPDATE.getCode());
					newfield.setModel_id(operDocStdModel.getStd_id());
					operStdModelFieldRepository.save(newfield);
				}
			}
		}else{
			List<OperDocStdModelField> newfieldlist = operDocStdModel.getFields();
			for (OperDocStdModelField newfield : newfieldlist) {
				newfield.setBatch_no(operDocStdModel.getBatch_no());
				newfield.setField_id(CommonUtil.getUUID());
				newfield.setStatus(Status.JIHUO.getCode());
				newfield.setOper_flag(DataType.UPDATE.getCode());
				newfield.setModel_id(operDocStdModel.getStd_id());
				operStdModelFieldRepository.save(newfield);
			}
		}

	}


	public List<Standard> getstdmess_resulttable(String file_id){
	 	return moduleRepository.getStandByFileId(file_id);
	}

	public List<CodeInfo> getcodemess_resulttable(String file_id){
		return codeInfoRepository.getCodeInfoByFileId(file_id);
	}




	 public void add(DocModel model) {
		 
		
		 modulerepository.save(model);
		 
		 
	 }
	 
	 public void addcheck(DocFieldCheckResult result) {
		 
			
		 fieldcheckrepository.save(result);
		 
		 
	 }
	 
	
	 
	 
	
	 
	 public List<DocModel> findByStdsource(String stdsource) {
		 
			
			return modulerepository.findByStdsource(stdsource);
			 
			 
	}
	 
	 public List<DocModelField> findByModelid(String model_id){
		 return modelfieldrepository.findByModelid(model_id);
	 }
	 
	

}
