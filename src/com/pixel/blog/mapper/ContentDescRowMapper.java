package com.pixel.blog.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.pixel.aws.model.AddFormModel;
import com.pixel.utils.CommonUtils;

public class ContentDescRowMapper implements RowMapper<AddFormModel> {

	public AddFormModel mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AddFormModel cBean = new AddFormModel();
		cBean.setContentId(rs.getInt("content_id"));
		cBean.setTitleId(rs.getString("header"));
		cBean.setMenuId(rs.getInt("menu_id"));
		cBean.setNextId(rs.getInt("next"));
		cBean.setPrevId(rs.getInt("prev"));
		cBean.setTags(rs.getString("tags"));
		cBean.setShortDescId(rs.getString("short_desc"));	
		Date date = rs.getDate("creation_date");
		cBean.setCreationDate(CommonUtils.getDisplayDate(date));
		
		return cBean;
	}

}
