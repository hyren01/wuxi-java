package com.jn.primiary.standard.entity;

/**
 * 标准的数据源信息
 *
 * @author wld
 * @date 2019-05-30 16:00
 */
public class StandardDataSource {
	
	


    private String datasource;//数据源信息
     
    private int type;//数据源复合类型标识 0表示没有复合型 1表示复合型

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
   

	


  
    
   
}
