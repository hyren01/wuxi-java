package com.jn.primiary.dataQuality.entity;

import java.util.List;

public class CsvReaderBean {
	
	private String[] headers;
	private List<String[]> rows;
	
	public String[] getHeaders() {
	
		return headers;
	}
	
	public void setHeaders(String[] headers) {
	
		this.headers = headers;
	}
	
	public List<String[]> getRows() {
	
		return rows;
	}
	
	public void setRows(List<String[]> rows) {
	
		this.rows = rows;
	}
	
}

