package com.pixel.blog.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pixel.aws.model.BookModel;
import com.pixel.blog.dao.BookDao;
import com.pixel.blog.mapper.BookListMapper;

@Repository
public class BookDaoImpl implements BookDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<BookModel> getBookListbyCatergory() {
		List<BookModel> list = jdbcTemplate.query(BOOK_LIST_BY_CATERGORY, new BookListMapper());
		return list;
	}

	public List<BookModel> getBookList() {

		List<BookModel> list = jdbcTemplate.query(BOOK_LIST, new BookListMapper());
		return list;
	}

}
