package com.pixel.blog.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pixel.aws.model.ContentLVModel;

public class ContentLVRowMapper implements RowMapper<ContentLVModel> {

	public ContentLVModel mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ContentLVModel lv = new ContentLVModel();
		
		lv.setLikeCount(rs.getInt("like_count"));
		lv.setViewCount(rs.getInt("view_count"));
		lv.setContentId(rs.getInt("content_id"));	
		
		return lv;
	}

}
