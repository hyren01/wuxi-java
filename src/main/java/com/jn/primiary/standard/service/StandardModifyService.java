package com.jn.primiary.standard.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jn.primiary.metadata.entity.RegisterStatus;
import com.jn.primiary.metadata.entity.UserInfo;
import com.jn.primiary.metadata.utils.CommonUtil;
import com.jn.primiary.standard.dao.StandardRequestRepository;
import com.jn.primiary.standard.entity.Standard;
import com.jn.primiary.standard.entity.StandardRequest;

import net.sf.json.JSONObject;


@Service
public class StandardModifyService {
	@Autowired
	 private StandardRequestRepository standardRequestRepository;
	
	public List<StandardRequest> findAll(){
		List<StandardRequest> requestlist=standardRequestRepository.findAll();
		return requestlist;
	}
	
	public List<StandardRequest> findStandardAndCodeInfo(){
		
		List<StandardRequest> requestlist = standardRequestRepository.findStandardAndCodeInfo();
		return requestlist;
	}
	
	public StandardRequest findById(String id){
		return standardRequestRepository.findById(id);
	}
	
	
	public void save(StandardRequest request){
		standardRequestRepository.save(request);
		return;
	}
	
	/**
	 * @Title: submitUpdateStdRequest  
	 * @Description:提交标准修改申请
	 * @param 
	 * @return 
	 * @throws
	 */
	@Transactional
	public void submitStdRequest(Standard stdInfo, UserInfo registerUser,String type) {
		// 生成标准请求
		StandardRequest request = new StandardRequest();
		
		request.setId(CommonUtil.getUUID());
		request.setType(type);
		request.setResourceId(stdInfo.getId());
		request.setCode(stdInfo.getCode());
		request.setResourceContent(JSONObject.fromObject(stdInfo).toString());
		
		request.setRequestTime(Calendar.getInstance().getTime());
		
		if(null != registerUser) {
			request.setRequestPerson(registerUser.getUserName());
		}else{
			request.setRequestPerson("admin");
		}
		request.setAuditResult(RegisterStatus.REGISTERING);
		standardRequestRepository.save(request);
		
		return;
	}
	
}	
	
	
	
	