package com.pixel.section.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pixel.section.model.ViewSectionModel;

public class ViewSectionRowMapper implements RowMapper<ViewSectionModel> {

	public ViewSectionModel mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ViewSectionModel ms = new ViewSectionModel();
		ms.setSubmasterdesc(rs.getString("submasterdesc"));
		ms.setContentid(rs.getString("content_id"));
		ms.setLongdesc(rs.getString("long_desc"));
		ms.setContenttype(rs.getString("content_type"));
		ms.setOrderid(rs.getString("order_id"));
		ms.setCreationdate(rs.getString("creation_date"));
		ms.setSubsectionid(rs.getString("subsectionid"));
		ms.setTitle(rs.getString("title"));
		ms.setTags(rs.getString("tags"));
		ms.setPrev(rs.getString("prev"));
		ms.setNext(rs.getString("next"));
		return ms;
	}

}
