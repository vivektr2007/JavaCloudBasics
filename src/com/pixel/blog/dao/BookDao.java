package com.pixel.blog.dao;

import java.util.List;

import com.pixel.aws.model.BookModel;

public interface BookDao {
	
	public String BOOK_LIST_BY_CATERGORY ="select bookid,bookpath,thumbpath,bookname,version,category,orderid from book_info where category=? order by orderid";
	public List<BookModel> getBookListbyCatergory();
	
	public String BOOK_LIST ="select bookid,bookpath,thumbpath,bookname,version,category,orderid from book_info";
	public List<BookModel> getBookList();

}
