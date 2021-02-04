package com.pixel.blog.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.pixel.aws.model.BlogCommentBean;
import com.pixel.aws.model.BlogCommentReplyBean;

public class BlogCommentRowMapper implements RowMapper<BlogCommentBean> {

	private JdbcTemplate jdbcTemplate;
	public BlogCommentRowMapper(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public BlogCommentBean mapRow(ResultSet rs, int arg1) throws SQLException {

		BlogCommentBean bean = new BlogCommentBean();
		bean.setCommentBy(rs.getString("comment_by"));
		bean.setCommentDate(rs.getString("comment_date"));
		bean.setCommentDesc(rs.getString("comment_desc"));
		bean.setSeq(rs.getInt("seq"));
		bean.setTopicId(rs.getInt("topic_id"));
		
		List<BlogCommentReplyBean> listReply = jdbcTemplate.query("select * from blog_comment_reply_detail where comment_id="+bean.getSeq(), new BlogCommentReplyMapper());
		bean.setReplyBeanList(listReply);
		
		
		return bean;
	}

	
	
}
