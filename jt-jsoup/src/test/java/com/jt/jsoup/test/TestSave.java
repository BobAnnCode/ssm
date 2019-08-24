package com.jt.jsoup.test;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jt.jsoup.mapper.BookMapper;
import com.jt.jsoup.service.BookService;

public class TestSave {
	
	
	/*@Test
	public void findFree() throws IOException{
		String url = "http://www.tmooc.cn/free";
		Elements elements = Jsoup.connect(url).get()
		.select(".md-bd").select(".clearfix").select(".md-2018040201-lty");
		System.out.println("获取免费课程数量:"+elements.size());
		Elements elementsLi = elements.select("li");
		System.out.println("获取课程的个数:"+elementsLi.size());
		
		//1.url地址  2.课程名称  3.课程类型  4学习人数
		for (Element elementLi : elementsLi) {
			//获取图片的地址
			String imageUrl = elementLi.select(".hd-pic img").attr("data-original");
			//System.out.println(imageEl);
			//先获取div
			Element element = 
			elementLi.select(".bd-word").select(".bgcolor-fff a").get(0);
			String freeUrl = element.attr("href");
			String freeName = element.text();
			System.out.println("获取数据:"+freeName+"url:"+freeUrl);
			String type = elementLi.select("span").get(0).text();
			String num = elementLi.select("span").get(1).text();
			System.out.println("课程的类型:"+type);
			System.out.println("学习的人数:"+num);
			System.out.println("获取图片url:"+imageUrl);
		}
	}*/
	
	@Test
	public void save() throws IOException{
		ApplicationContext applicationContext = 
				new ClassPathXmlApplicationContext("/spring/applicationContext*.xml");
		
		BookService bookService = applicationContext.getBean(BookService.class);
		
		String url = "http://www.tmooc.cn/free";
		bookService.saveBook(url,"免费课程");
		
		String url1 = "http://www.tmooc.cn/live";
		bookService.saveBook(url1,"直播课程");
		
		String vipUrl = "http://www.tmooc.cn/vip";
		bookService.saveBook(vipUrl,"会员课程");
		
		String advancedUrl = "http://www.tmooc.cn/advanced";
		bookService.saveBook(advancedUrl,"培优课程");
		System.out.println("爬虫操作成功");
	}
	
	
	
	
	
	
}
