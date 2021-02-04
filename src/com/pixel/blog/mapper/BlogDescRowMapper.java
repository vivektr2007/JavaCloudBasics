package com.pixel.blog.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pixel.aws.model.BlogDescripitionModel;

public class BlogDescRowMapper implements RowMapper<BlogDescripitionModel> {

	public BlogDescripitionModel mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BlogDescripitionModel bm = new BlogDescripitionModel();
		bm.setBlogdesc(rs.getString("blogdesc"));
		bm.setBlogid(rs.getInt("blogid"));
		bm.setBlogtitle(rs.getString("blogtitle"));
		bm.setCategory(rs.getString("category"));
		bm.setLinkid(rs.getString("linkid"));
		
		
		return bm;
	}

}
