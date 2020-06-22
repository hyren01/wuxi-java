package com.jn.primiary.beyondsoft.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Id;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DBInfo {
	
	@JsonProperty("id")
	@Id
	private String id;
	
	@JsonProperty("dbCode")
	private String dbCode;
	
	@JsonProperty("dbdriver")
	private String dbDriver;
	
	@JsonProperty("url")
	private String url;
	
	@JsonProperty("insname")
	private String insname;
	
	@JsonProperty("user")
	private String user;
	
	@JsonProperty("password")
	private String password;
	
	@JsonProperty("ip")
	private String ip;
	
	@JsonProperty("port")
	private String port;
	
	@JsonProperty("categoryid")
	private String categoryId;
	
	@JsonProperty("charaterset")
	private String charaterset;
	
	@JsonProperty("location")
	private String location;
	
	@JsonProperty("creator")
	private String creator;
	
	@JsonProperty("createTime")
	private String createTime;
	@JsonProperty("updator")
	private String updator;
	
	@JsonProperty("version")
	private String version;
	
	@JsonProperty("serviceName")
	private String serviceName;
	
	@JsonProperty("maxConnections")
	private String maxConnections;
	
	@JsonProperty("isCluster")
	private String isCluster;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDbCode() {
		return dbCode;
	}

	public void setDbCode(String dbCode) {
		this.dbCode = dbCode;
	}

	public String getDbDriver() {
		return dbDriver;
	}

	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getInsname() {
		return insname;
	}

	public void setInsname(String insname) {
		this.insname = insname;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCharaterset() {
		return charaterset;
	}

	public void setCharaterset(String charaterset) {
		this.charaterset = charaterset;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(String maxConnections) {
		this.maxConnections = maxConnections;
	}

	public String getIsCluster() {
		return isCluster;
	}

	public void setIsCluster(String isCluster) {
		this.isCluster = isCluster;
	}

	@Override
	public String toString() {
		return "DBInfo [id=" + id + ", dbCode=" + dbCode + ", dbDriver=" + dbDriver + ", url=" + url + ", insname="
				+ insname + ", user=" + user + ", password=" + password + ", ip=" + ip + ", port=" + port
				+ ", categoryId=" + categoryId + ", charaterset=" + charaterset + ", location=" + location
				+ ", creator=" + creator + ", createTime=" + createTime + ", updator=" + updator + ", version="
				+ version + ", serviceName=" + serviceName + ", maxConnections=" + maxConnections + ", isCluster="
				+ isCluster + "]";
	}
}
