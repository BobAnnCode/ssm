package com.jt.jsoup.service;

import java.io.IOException;

public interface BookService {
	public void saveBook(String url,String courseName) throws IOException;
}
