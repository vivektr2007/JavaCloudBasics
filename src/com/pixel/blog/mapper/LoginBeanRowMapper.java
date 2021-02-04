package com.pixel.blog.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pixel.aws.model.LoginBean;

public class LoginBeanRowMapper implements RowMapper<LoginBean> {

	public LoginBean mapRow(ResultSet rs, int arg1) throws SQLException {
		
		LoginBean loginBean = new LoginBean();
		loginBean.setPassword(rs.getString("password"));
		loginBean.setUserId(rs.getString("email_id"));
		loginBean.setUserName(rs.getString("first_name") + " " + rs.getString("last_name"));
		
		return loginBean;
	}

	
}
