package com.jn.primiary.beyondsoft.vo;

import java.util.List;
/**
 * @Des 码表详情VO
 * @Author chenhong
 * @Date 2019/10/15 16:46
 */
public class CodeInfoDetailVo {


	private List codeInfoList;
	//private List<Standard> moudleList;
	private String[] fieldId;

	public String[] getFieldId() {
		return fieldId;
	}

	public void setFieldId(String[] fieldId) {
		this.fieldId = fieldId;
	}

	public List getCodeInfoList() {
		return codeInfoList;
	}

	public void setCodeInfoList(List codeInfoList) {
		this.codeInfoList = codeInfoList;
	}

	public CodeInfoDetailVo(List codeInfoList, String[] fieldId) {
		this.codeInfoList = codeInfoList;
		this.fieldId = fieldId;
	}

	public CodeInfoDetailVo(){
		super();
	}
}

	
