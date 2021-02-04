package com.pixel.blog.service;

import java.util.List;

import com.pixel.aws.model.BookModel;

public interface BookService {

	public List<BookModel> getBookListbyCatergory();

	public List<BookModel> getBookList();

}
