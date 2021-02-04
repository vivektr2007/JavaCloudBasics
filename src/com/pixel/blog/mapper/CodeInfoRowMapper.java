package com.pixel.blog.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pixel.aws.model.CodeModel;

public class CodeInfoRowMapper implements RowMapper<CodeModel> {

	public CodeModel mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CodeModel lv = new CodeModel();
		lv.setCodeId(rs.getInt("code_id"));
		lv.setCodeInfo(rs.getString("code_info"));
		lv.setCodeValue(rs.getString("code_value"));
		lv.setCodeDesc(rs.getString("code_desc"));
		lv.setDelYn(rs.getString("del_yn"));
		
		return lv;
	}

}
