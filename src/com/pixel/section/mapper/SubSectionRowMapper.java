package com.pixel.section.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pixel.section.model.SubSectionModel;

public class SubSectionRowMapper implements RowMapper<SubSectionModel> {

	public SubSectionModel mapRow(ResultSet rs, int arg1) throws SQLException {
		
		SubSectionModel ms = new SubSectionModel();
		ms.setMasterid(rs.getString("masterid"));
		ms.setSubmasterid(rs.getString("submasterid"));
		ms.setSubmasterdesc(rs.getString("submasterdesc"));
		ms.setOrderid(rs.getString("orderid"));
		ms.setImagelink(rs.getString("imagelink"));
		ms.setCreationdate(rs.getString("creationdate"));
		
		return ms;
	}

}
