package com.jn.primiary.stdgl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CategoryRowMapper implements RowMapper<CategoryEntity>{
	public CategoryEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		CategoryEntity so = new CategoryEntity();
        so.setCreator(rs.getString("creator"));
        so.setIsmodel(rs.getInt("ismodel"));
        so.setCreate_time(rs.getString("create_time"));
        so.setId(rs.getString("id"));
        so.setName(rs.getString("name"));
        so.setPid(rs.getString("parent_id"));
        so.setModelID(rs.getString("modelid"));
        return so;
	}
}
