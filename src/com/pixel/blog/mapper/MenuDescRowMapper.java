package com.pixel.blog.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pixel.aws.model.MenuBean;

public class MenuDescRowMapper implements RowMapper<MenuBean> {

	public MenuBean mapRow(ResultSet rs, int arg1) throws SQLException {
		
		MenuBean mBean = new MenuBean();
		mBean.setIs_visible(rs.getString("is_visible"));
		mBean.setMenuDesc(rs.getString("menu_desc"));
		mBean.setMenuId(rs.getInt("menu_id"));
		mBean.setParent(rs.getString("parent"));
		mBean.setTarget(rs.getString("target"));
		
		return mBean;
	}

}
