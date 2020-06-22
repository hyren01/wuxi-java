package com.jn.primiary.metadata.entity;

import java.util.List;

public class BatchRequest {
	
	List<String> requestIdList;
	
	String message;

	public List<String> getRequestIdList() {
		return requestIdList;
	}

	public void setRequestIdList(List<String> requestIdList) {
		this.requestIdList = requestIdList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
