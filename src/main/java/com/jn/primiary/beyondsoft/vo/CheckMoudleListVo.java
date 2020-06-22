package com.jn.primiary.beyondsoft.vo;

/**
 * @Des 模型检测列表VO
 * @Author chenhong
 * @Date 2019/10/16 15:46
 */
public class CheckMoudleListVo {

	private String fieldName="";	//模型字段中文名
	private String enName="";	//模型字段英文名
	private String fieldCode="";	//模型字段code
	private String checkDate="";	//检测日期
	private String checkResult="";	//检测结果
	private String fieleId="";		//模型字段id
	private String moudleId="";	//模型id

	private String std_id="";	//标准id
	private String std_cname="";	//标准中文名
	private String obj_id="";	//	objid
	private String obj_cname="";	//	objcname
	private String relate_field_id="";	//关联的标准的字段id
	private String relate_field_name="";	//关联的标准的字段的中文名

	public String getStd_id() {
		return std_id;
	}

	public void setStd_id(String std_id) {
		this.std_id = std_id;
	}

	public String getStd_cname() {
		return std_cname;
	}

	public void setStd_cname(String std_cname) {
		this.std_cname = std_cname;
	}

	public String getObj_id() {
		return obj_id;
	}

	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
	}

	public String getObj_cname() {
		return obj_cname;
	}

	public void setObj_cname(String obj_cname) {
		this.obj_cname = obj_cname;
	}

	public String getRelate_field_id() {
		return relate_field_id;
	}

	public void setRelate_field_id(String relate_field_id) {
		this.relate_field_id = relate_field_id;
	}

	public String getRelate_field_name() {
		return relate_field_name;
	}

	public void setRelate_field_name(String relate_field_name) {
		this.relate_field_name = relate_field_name;
	}

	public String getFieleId() {
		return fieleId;
	}

	public void setFieleId(String fieleId) {
		this.fieleId = fieleId;
	}

	public String getMoudleId() {
		return moudleId;
	}

	public void setMoudleId(String moudleId) {
		this.moudleId = moudleId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public CheckMoudleListVo(String fieldName, String enName, String fieldCode, String checkDate, String checkResult, String fieleId, String moudleId) {
		this.fieldName = fieldName;
		this.enName = enName;
		this.fieldCode = fieldCode;
		this.checkDate = checkDate;
		this.checkResult = checkResult;
		this.fieleId = fieleId;
		this.moudleId = moudleId;
	}

	public CheckMoudleListVo(){
		super();
	}
}

	
