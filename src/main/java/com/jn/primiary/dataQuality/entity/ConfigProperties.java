package com.jn.primiary.dataQuality.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="standard")
public class ConfigProperties {
	
	private int csvnum;
	private String csvpath;
	
	
	public int getCsvnum() {
	
		return csvnum;
	}

	
	public void setCsvnum(int csvnum) {
	
		this.csvnum = csvnum;
	}


	public String getCsvpath() {
	
		return csvpath;
	}

	
	public void setCsvpath(String csvpath) {
	
		this.csvpath = csvpath;
	}
	
	
}

