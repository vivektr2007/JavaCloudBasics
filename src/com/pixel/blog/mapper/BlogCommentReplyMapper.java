package com.pixel.blog.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pixel.aws.model.BlogCommentReplyBean;

public class BlogCommentReplyMapper implements RowMapper<BlogCommentReplyBean> {

	public BlogCommentReplyBean mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BlogCommentReplyBean bean = new BlogCommentReplyBean();
		bean.setCommentId(rs.getInt("comment_id"));
		bean.setReplyBy(rs.getString("reply_by"));
		bean.setReplyDate(rs.getString("reply_date"));
		bean.setReplyDesc(rs.getString("reply_desc"));
		bean.setSeq(rs.getInt("seq"));
		
		return bean;
	}

}
