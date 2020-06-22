package com.jn.primiary.stdgl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * 在数据库查询中，如果返回的类型是用户自定义的类型则需要包装 RowMapper可以将数据中的每一行封装成用户定义的类，
 * 
 * @author gongyf
 * @Date2016年11月27日下午9:51:48
 */
public class StdSchemaFieldRowMapper implements RowMapper<StdFieldEntity> {
	
	
	public StdFieldEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		StdFieldEntity sf = new StdFieldEntity();
		sf.setId(rs.getString("id"));
		sf.setFieldCode(rs.getString("fieldcode"));
		sf.setFieldName(rs.getString("fieldname"));
		sf.setMaxsize(rs.getString("maxsize"));
		sf.setRequired(rs.getString("required"));
		sf.setSchemaCodeID(rs.getString("schemacodeid"));
		sf.setType(rs.getString("type"));
		sf.setPxh(rs.getInt("pxh"));
		sf.setEnName(rs.getString("enname"));
//		sf.setRange(rs.getString("range"));
		sf.setDefination(rs.getString("defination"));
		sf.setComments(rs.getString("comments"));
		sf.setMaxContainCount(rs.getInt("maxcontaincount"));
		sf.setCreator(rs.getString("creator"));
		sf.setCreateTime(rs.getString("createtime"));

		
		return sf;
	}
	

	
}
