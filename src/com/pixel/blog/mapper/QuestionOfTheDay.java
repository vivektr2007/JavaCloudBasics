package com.pixel.blog.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pixel.aws.model.QuestionOfTheDayBean;

public class QuestionOfTheDay implements RowMapper<QuestionOfTheDayBean> {

	public QuestionOfTheDayBean mapRow(ResultSet rs, int arg1) throws SQLException {
		
		QuestionOfTheDayBean bean = new QuestionOfTheDayBean();
		bean.setSeq(rs.getInt("seq"));
		bean.setCreation_date(rs.getString("creation_date"));
		bean.setHeading(rs.getString("heading"));
		bean.setQuestion_details(rs.getString("question_details"));
		
		return bean;
	}

}
