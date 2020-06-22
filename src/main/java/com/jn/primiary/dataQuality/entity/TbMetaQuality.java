package com.jn.primiary.dataQuality.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "tb_meta_quality")
@Entity
public class TbMetaQuality {
	
	@Id
	@Column(name = "id", nullable = false, length = 32)
	private String id;
	
	@Column(name = "database_name", nullable = true, length = 128)
	private String databaseName;
	
	@Column(name = "table_cname", nullable = true, length = 128)
	private String tableCname;
	
	@Column(name = "table_ename", nullable = true, length = 128)
	private String tableEname;
	
	@Column(name = "check_cname", nullable = true, length = 128)
	private String checkCname;
	
	@Column(name = "check_ename", nullable = true, length = 128)
	private String checkEname;
	
	@Column(name = "check_status", nullable = false, length = 1)
	private String checkStatus;
	
	@Column(name = "check_type", nullable = false, length = 1)
	private String checkType;
	
	@Column(name = "minvalue", nullable = true, precision=16, scale=6)
	private BigDecimal minvalue;
	
	@Column(name = "[maxvalue]", nullable = true, precision=16, scale=6)
	private BigDecimal maxvalue;
	
	@Column(name = "total_num", nullable = true, precision=16, scale=0)
	private BigDecimal totalNum;
	
	@Column(name = "exception_num", nullable = true, precision=16, scale=0)
	private BigDecimal exceptionNum;
	
	@Column(name = "total_sql", nullable = true, columnDefinition = "text")
	private String totalSql;
	
	@Column(name = "exception_sql", nullable = true, columnDefinition = "text")
	private String exceptionSql;
	
	@Column(name = "detail_sql", nullable = true, columnDefinition = "text")
	private String detailSql;
	
	@Column(name = "detail_path", nullable = true, length = 1024)
	private String detailPath;
	
	@Column(name = "create_user", nullable = true, length = 32)
	private String createUser;
	
	@Column(name = "create_date", nullable = true, length = 8)
	private String createDate;
	
	@Column(name = "create_time", nullable = true, length = 6)
	private String createTime;
	
	@Transient
	private String[] header;

	@Transient
	private DatabaseInfo db;
	
	public String getId() {
	
		return id;
	}

	
	public void setId(String id) {
	
		this.id = id;
	}
	
	
	public String getDatabaseName() {
	
		return databaseName;
	}

	
	public void setDatabaseName(String databaseName) {
	
		this.databaseName = databaseName;
	}


	public String getTableCname() {
	
		return tableCname;
	}

	
	public void setTableCname(String tableCname) {
	
		this.tableCname = tableCname;
	}

	
	public String getTableEname() {
	
		return tableEname;
	}

	
	public void setTableEname(String tableEname) {
	
		this.tableEname = tableEname;
	}

	
	public String getCheckCname() {
	
		return checkCname;
	}

	
	public void setCheckCname(String checkCname) {
	
		this.checkCname = checkCname;
	}

	
	public String getCheckEname() {
	
		return checkEname;
	}

	
	public void setCheckEname(String checkEname) {
	
		this.checkEname = checkEname;
	}

	
	public String getCheckStatus() {
	
		return checkStatus;
	}

	
	public void setCheckStatus(String checkStatus) {
	
		this.checkStatus = checkStatus;
	}

	
	public String getCheckType() {
	
		return checkType;
	}

	
	public void setCheckType(String checkType) {
	
		this.checkType = checkType;
	}

	
	public BigDecimal getMinvalue() {
	
		return minvalue;
	}

	
	public void setMinvalue(BigDecimal minvalue) {
	
		this.minvalue = minvalue;
	}

	
	public BigDecimal getMaxvalue() {
	
		return maxvalue;
	}

	
	public void setMaxvalue(BigDecimal maxvalue) {
	
		this.maxvalue = maxvalue;
	}

	
	public BigDecimal getTotalNum() {
	
		return totalNum;
	}

	
	public void setTotalNum(BigDecimal totalNum) {
	
		this.totalNum = totalNum;
	}

	
	public BigDecimal getExceptionNum() {
	
		return exceptionNum;
	}

	
	public void setExceptionNum(BigDecimal exceptionNum) {
	
		this.exceptionNum = exceptionNum;
	}

	
	public String getTotalSql() {
	
		return totalSql;
	}

	
	public void setTotalSql(String totalSql) {
	
		this.totalSql = totalSql;
	}

	
	public String getExceptionSql() {
	
		return exceptionSql;
	}

	
	public void setExceptionSql(String exceptionSql) {
	
		this.exceptionSql = exceptionSql;
	}

	
	public String getDetailSql() {
	
		return detailSql;
	}

	
	public void setDetailSql(String detailSql) {
	
		this.detailSql = detailSql;
	}

	
	public String getDetailPath() {
	
		return detailPath;
	}

	
	public void setDetailPath(String detailPath) {
	
		this.detailPath = detailPath;
	}

	
	public String getCreateUser() {
	
		return createUser;
	}

	
	public void setCreateUser(String createUser) {
	
		this.createUser = createUser;
	}

	
	public String getCreateDate() {
	
		return createDate;
	}

	
	public void setCreateDate(String createDate) {
	
		this.createDate = createDate;
	}

	
	public String getCreateTime() {
	
		return createTime;
	}

	
	public void setCreateTime(String createTime) {
	
		this.createTime = createTime;
	}
	
	public String[] getHeader() {
	
		return header;
	}
	
	public void setHeader(String[] header) {
	
		this.header = header;
	}

	public DatabaseInfo getDb() {
	
		return db;
	}

	public void setDb(DatabaseInfo db) {
	
		this.db = db;
	}
	
}

