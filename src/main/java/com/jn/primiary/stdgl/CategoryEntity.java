package com.jn.primiary.stdgl;

public class CategoryEntity {

	private String id;
	private String name;
	private String pid;
	private String creator;
	private String create_time;
	private int ismodel;            
	private String status;
	private int pxh;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getIsmodel() {
		return ismodel;
	}
	public void setIsmodel(int ismodel) {
		this.ismodel = ismodel;
	}
	public String getModelID() {
		return status;
	}
	public void setModelID(String modelID) {
		this.status = modelID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public int getPxh() {
		return pxh;
	}
	public void setPxh(int pxh) {
		this.pxh = pxh;
	}

}
