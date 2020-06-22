package com.jn.primiary.stdgl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class objectCategoryRowMapper implements RowMapper<StdMidEntity>{
	public StdMidEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		StdMidEntity so = new StdMidEntity();
        so.setName(rs.getString("fieldname"));
        so.setId(rs.getString("id"));

        return so;
	}
}
