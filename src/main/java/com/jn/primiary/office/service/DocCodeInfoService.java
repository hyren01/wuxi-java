package com.jn.primiary.office.service;

import com.jn.primiary.office.dao.OperStdCodeInfoRepository;
import com.jn.primiary.office.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocCodeInfoService {
	 @Autowired
	 private OperStdCodeInfoRepository codeInfoRepository;


	@Transactional
	public void addCodeInfo( List<OperDocStdCodeInfo> listcodeinfo){
		if(listcodeinfo.size()>0){
			for(OperDocStdCodeInfo codeinfo:listcodeinfo){
				codeInfoRepository.save(codeinfo);
			}
		}
	}






}
