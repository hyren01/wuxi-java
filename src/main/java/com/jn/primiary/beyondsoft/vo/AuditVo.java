package com.jn.primiary.beyondsoft.vo;

import com.jn.primiary.beyondsoft.entity.OperCodeInfo;
import com.jn.primiary.beyondsoft.entity.OperStandard;

import java.util.List;
/**
 * @Des 审核操作VO
 * @Author chenhong
 * @Date 2019/9/28 16:46
 */
public class AuditVo {

	private String isAuth;
	private String authRemark;
	private List<OperStandard> moudleList;
	private List<OperCodeInfo> codeInfoList;

	public String getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(String isAuth) {
		this.isAuth = isAuth;
	}

	public String getAuthRemark() {
		return authRemark;
	}

	public void setAuthRemark(String authRemark) {
		this.authRemark = authRemark;
	}

	public List<OperStandard> getMoudleList() {
		return moudleList;
	}

	public void setMoudleList(List<OperStandard> moudleList) {
		this.moudleList = moudleList;
	}

	public List<OperCodeInfo> getCodeInfoList() {
		return codeInfoList;
	}

	public void setCodeInfoList(List<OperCodeInfo> codeInfoList) {
		this.codeInfoList = codeInfoList;
	}

	public AuditVo(String isAuth, String authRemark,List<OperStandard> moudleList, List<OperCodeInfo> codeInfoList) {
		this.isAuth = isAuth;
		this.authRemark = authRemark;
		this.moudleList = moudleList;
		this.codeInfoList = codeInfoList;
	}

	public AuditVo(){
		super();
	}
}

	
