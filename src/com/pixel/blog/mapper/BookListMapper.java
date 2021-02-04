package com.pixel.blog.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pixel.aws.model.BookModel;

public class BookListMapper implements RowMapper<BookModel> {

	public BookModel mapRow(ResultSet rs, int arg1) throws SQLException {

		BookModel bm = new BookModel();
		bm.setBookid(rs.getString("bookid"));
		bm.setBookpath(rs.getString("bookpath"));
		bm.setThumbpath(rs.getString("thumbpath"));
		bm.setBookname(rs.getString("bookname"));
		bm.setVersion(rs.getString("version"));
		bm.setCatergory(rs.getString("category"));
		bm.setOrderid(Integer.parseInt(rs.getString("orderid")));
		return bm;
	}

}
