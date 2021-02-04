package com.pixel.blog.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TagRowMapper implements RowMapper<String> {
	public String mapRow(ResultSet rs, int arg1) throws SQLException {
		return rs.getString("tags");
	}
}
