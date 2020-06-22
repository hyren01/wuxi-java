package com.jn.primiary.beyondsoft.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_stdgl_dbtype_mapping")
@Entity
public class DBTypeMapping {
	
	@Id
	@Column(name = "id", nullable = false, length = 32)
	private String ID;
	
	@Column(name = "db_type", nullable = true, length = 64)
	private String dbType;
	
	@Column(name = "driver_name", nullable = true, length = 128)
	private String driverName;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	@Override
	public String toString() {
		return "DBTypeMapping [ID=" + ID + ", dbType=" + dbType + ", driverName=" + driverName + "]";
	}
}
