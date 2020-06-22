package com.jn.primiary.stdgl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
/**
 * 在数据库查询中，如果返回的类型是用户自定义的类型则需要包装
 * RowMapper可以将数据中的每一行封装成用户定义的类，
 * @author gongyf
 * @Date 2018年3月9日9:51:48
 */
public class StdSchemaObjectRowMapper implements RowMapper<StdSchemaObject> {
	
	public StdSchemaObject mapRow(ResultSet rs, int rowNum) throws SQLException {
		StdSchemaObject so = new StdSchemaObject();
        so.setCreator(rs.getString("creator"));
        so.setCreateTime(rs.getString("createtime"));
        so.setDataSource(rs.getString("dataSource"));
        so.setDescription(rs.getString("description"));
        so.setSchemaCode(rs.getString("schemacode"));
        so.setId(rs.getString("id"));
        so.setSchemaName(rs.getString("schemaname"));
        so.setVersion(rs.getString("version"));
        so.setEnName(rs.getString("enname"));
        so.setType(rs.getInt("type"));
        return so;
	}
}
