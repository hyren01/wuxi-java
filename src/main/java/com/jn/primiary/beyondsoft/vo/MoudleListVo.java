package com.jn.primiary.beyondsoft.vo;

/**
 * @Des 模型列表VO
 * @Author chenhong
 * @Date 2019/10/16 15:46
 */
public class MoudleListVo {

	private String moudleName;
	private String enName;
	private String moudleCode;
	private String moudleDesc;
	private String updateDate;
	private String status;
	private String moudleId;
	private String isCheck;
	private String isCheckModify;

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getIsCheckModify() {
		return isCheckModify;
	}

	public void setIsCheckModify(String isCheckModify) {
		this.isCheckModify = isCheckModify;
	}

	public String getMoudleName() {
		return moudleName;
	}

	public void setMoudleName(String moudleName) {
		this.moudleName = moudleName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getMoudleCode() {
		return moudleCode;
	}

	public void setMoudleCode(String moudleCode) {
		this.moudleCode = moudleCode;
	}

	public String getMoudleDesc() {
		return moudleDesc;
	}

	public void setMoudleDesc(String moudleDesc) {
		this.moudleDesc = moudleDesc;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMoudleId() {
		return moudleId;
	}

	public void setMoudleId(String moudleId) {
		this.moudleId = moudleId;
	}

	public MoudleListVo(String moudleName, String enName, String moudleCode, String moudleDesc, String updateDate, String status, String moudleId, String isCheck, String isCheckModify) {
		this.moudleName = moudleName;
		this.enName = enName;
		this.moudleCode = moudleCode;
		this.moudleDesc = moudleDesc;
		this.updateDate = updateDate;
		this.status = status;
		this.moudleId = moudleId;
		this.isCheck = isCheck;
		this.isCheckModify = isCheckModify;
	}

	public MoudleListVo(){
		super();
	}
}

	
