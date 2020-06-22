package com.jn.primiary.beyondsoft.entity;

/**
 * 字段实体
 * */
public class FieldInfo {
	//列类型，格式为类型(长度，精度)
	private String columnType;
	//列名
	private String columnName;
	//列类型(仅名称)
	private String typeName;
	//列长度
	private Integer precision;
	//可否为空
	private Integer isNull;
	//列类型(java.sql.Types类型)
	private Integer dataType;
	//精度
	private Integer scale;
	private  String isRequired;


	public FieldInfo(String columnType, String columnName, String typeName, Integer precision, Integer isNull,
					 Integer dataType, Integer scale) {
		super();
		this.columnType = columnType;
		this.columnName = columnName;
		this.typeName = typeName;
		this.precision = precision;
		this.isNull = isNull;
		this.dataType = dataType;
		this.scale = scale;
		this.isRequired = isRequired;
	}

	public FieldInfo(String columnType, String columnName, String typeName, Integer precision, Integer isNull,
					 Integer dataType, Integer scale,String isRequired) {
		super();
		this.columnType = columnType;
		this.columnName = columnName;
		this.typeName = typeName;
		this.precision = precision;
		this.isNull = isNull;
		this.dataType = dataType;
		this.scale = scale;
		this.isRequired = isRequired;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getPrecision() {
		return precision;
	}

	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	public Integer getIsNull() {
		return isNull;
	}

	public void setIsNull(Integer isNull) {
		this.isNull = isNull;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	public String getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}

	@Override
	public String toString() {
		return "FieldInfo [columnType=" + columnType + ", columnName=" + columnName + ", typeName=" + typeName
				+ ", precision=" + precision + ", isNull=" + isNull + ", dataType=" + dataType + ", scale=" + scale
				+ "]";
	}
}
