package com.pixel.section.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pixel.section.model.MasterSectionModel;

public class MasterSectionRowMapper implements RowMapper<MasterSectionModel> {

	public MasterSectionModel mapRow(ResultSet rs, int arg1) throws SQLException {
		
		MasterSectionModel ms = new MasterSectionModel();
		ms.setMasterid(rs.getString("masterid"));
		ms.setMasterdesc(rs.getString("masterdesc"));
		ms.setOrderid(rs.getString("orderid"));
		ms.setImagelink(rs.getString("imagelink"));
		ms.setCreationdate(rs.getString("creationdate"));
		return ms;
	}

}
