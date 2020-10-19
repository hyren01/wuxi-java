package com.jn.primiary.beyondsoft.service;

import javax.transaction.Transactional;

import com.jn.primiary.beyondsoft.entity.CategoryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jn.primiary.beyondsoft.dao.DSStatistRepository;

import java.util.List;

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

	//根据ID获取现有标准模型数
	public int getStandardModelsNumByCategoryID(String categoryid) {
		return dao.getStandardModelsNumByCategoryId(categoryid);
	}

	//把当前目录的id作为其他目录的父id去查询
	public List<String> findCategoryInfoByParentId(String categoryid) {
		return dao.findCategoryInfoByParentId(categoryid);
	}
	//获取现有标准字段数
	public int getStandardFieldsNum() {
		return dao.getStandardFieldsNum();
	}
	//根据ID获取现有标准字段数
	public int getStandardFieldsNumByCategoryId(String CategoryId) {
		return dao.getStandardFieldsNumByCategoryId(CategoryId);
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
