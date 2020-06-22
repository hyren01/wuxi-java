package com.jn.primiary.beyondsoft.vo;

import com.jn.primiary.beyondsoft.entity.OperCodeInfo;
import com.jn.primiary.beyondsoft.entity.OperFieldCode;

import java.util.List;
/**
 * @Des 码表新增VO
 * @Author chenhong
 * @Date 2019/9/28 16:46
 */
public class FieldCodeVo {

	private List<OperCodeInfo> codeInfoList;
	private List<OperFieldCode> fieldCodeList;

	public List<OperCodeInfo> getCodeInfoList() {
		return codeInfoList;
	}

	public void setCodeInfoList(List<OperCodeInfo> codeInfoList) {
		this.codeInfoList = codeInfoList;
	}

	public List<OperFieldCode> getFieldCodeList() {
		return fieldCodeList;
	}

	public void setFieldCodeList(List<OperFieldCode> fieldCodeList) {
		this.fieldCodeList = fieldCodeList;
	}

	public FieldCodeVo(List<OperCodeInfo> codeInfoList, List<OperFieldCode> fieldCodeList) {
		this.codeInfoList = codeInfoList;
		this.fieldCodeList = fieldCodeList;
	}

	public FieldCodeVo(){
		super();
	}
}

	
