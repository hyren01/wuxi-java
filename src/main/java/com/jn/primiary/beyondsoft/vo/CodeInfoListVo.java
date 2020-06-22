package com.jn.primiary.beyondsoft.vo;

/**
 * @Des 码表列表VO
 * @Author chenhong
 * @Date 2019/9/28 16:46
 */
public class CodeInfoListVo {

	private String codetableEname;
	private String codetableCname;
	private String createTime;
	private String createUser;
	private String isAuth;
	private String batchNo;
	private String dataType;

	public String getCodetableEname() {
		return codetableEname;
	}

	public void setCodetableEname(String codetableEname) {
		this.codetableEname = codetableEname;
	}

	public String getCodetableCname() {
		return codetableCname;
	}

	public void setCodetableCname(String codetableCname) {
		this.codetableCname = codetableCname;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(String isAuth) {
		this.isAuth = isAuth;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public CodeInfoListVo(String codetableEname, String codetableCname, String createTime, String createUser, String isAuth, String batchNo, String dataType) {
		this.codetableEname = codetableEname;
		this.codetableCname = codetableCname;
		this.createTime = createTime;
		this.createUser = createUser;
		this.isAuth = isAuth;
		this.batchNo = batchNo;
		this.dataType = dataType;
	}

	public CodeInfoListVo(){
		super();
	}
}

	
