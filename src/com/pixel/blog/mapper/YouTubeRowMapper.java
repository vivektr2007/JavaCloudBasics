package com.pixel.blog.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pixel.aws.model.YouTubeModel;

public class YouTubeRowMapper implements RowMapper<YouTubeModel> {

	public YouTubeModel mapRow(ResultSet rs, int arg1) throws SQLException {
		
		YouTubeModel lv = new YouTubeModel();
		lv.setContentId(rs.getInt("content_id"));
		lv.setYouTubeUrl(rs.getString("youtubeurl"));
		lv.setYouTubeCode(rs.getString("youtubecode"));
		
		return lv;
	}

}
