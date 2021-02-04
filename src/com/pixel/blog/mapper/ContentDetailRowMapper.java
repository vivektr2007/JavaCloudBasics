package com.pixel.blog.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pixel.aws.model.AddFormDetailModel;

public class ContentDetailRowMapper implements RowMapper<AddFormDetailModel> {

	public AddFormDetailModel mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AddFormDetailModel detail = new AddFormDetailModel();
		
		detail.setAddMoreType(rs.getString("content_type"));
		if(rs.getString("content_type").equalsIgnoreCase("Text") ){
			detail.setLongDescId(rs.getString("long_desc"));
		}else if(rs.getString("content_type").equalsIgnoreCase("Image")){
			String str = rs.getString("long_desc");
			detail.setLongDescId(str);
		}
		detail.setLongDescId(rs.getString("long_desc"));
		detail.setOrderId(rs.getString("order_id"));
		
		return detail;
	}

}
