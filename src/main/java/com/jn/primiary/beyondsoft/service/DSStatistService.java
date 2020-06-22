package com.jn.primiary.beyondsoft.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jn.primiary.beyondsoft.dao.DSStatistRepository;

/**
 * 数据标准统计Service
 * */
@Service
@Transactional
public class DSStatistService {
	
	@Autowired
	private DSStatistRepository dao;
	
	//获取现有模型数
	public int getExistModelsNum() {
		return dao.getExistModelsNum();
	}
	
	//获取已检测模型数 
	public int getDetectedModelsNum() {
		return dao.getDetectedModelsNum();
	}
	
	//获取现有标准模型数
	public int getStandardModelsNum() {
		return dao.getStandardModelsNum();
	}
	
	//获取现有标准字段数
	public int getStandardFieldsNum() {
		return dao.getStandardFieldsNum();
	}

	//获取码表数量
	public int getCodeInfoNum() {
		return dao.getCodeInfoNum();
	}


	//获取文件数量
	public int getFileNum() {
		return dao.getFileNum();
	}

    public int getUserNum() {
		return dao.getUserNum();
    }
}
