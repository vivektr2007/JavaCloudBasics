package com.pixel.blog.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pixel.aws.model.BookModel;
import com.pixel.blog.dao.BookDao;
import com.pixel.blog.service.BookService;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	BookDao bookDao;
	
	public List<BookModel> getBookListbyCatergory(){
		return bookDao.getBookListbyCatergory();
	}
	
	public List<BookModel> getBookList(){
		return bookDao.getBookList();
	}

}
