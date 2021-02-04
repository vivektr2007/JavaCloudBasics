package com.pixel.blog.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.pixel.section.model.InterviewQuestionsBean;
import com.pixel.utils.CommonUtils;

public class InterviewQuestionMapper implements RowMapper<InterviewQuestionsBean> {

	public InterviewQuestionsBean mapRow(ResultSet rs, int arg1) throws SQLException {
		
		InterviewQuestionsBean cBean = new InterviewQuestionsBean();
		cBean.setCompanyName(rs.getString("company_name"));
		cBean.setInterviewQuestion(rs.getString("interview_question"));
		cBean.setPk(rs.getInt("pk"));
		cBean.setShortAnswer(rs.getString("answer"));
		
		Date date = rs.getDate("created_date");
		cBean.setCreatedDate(CommonUtils.getDisplayDate(date));
		
		return cBean;
	}

}
