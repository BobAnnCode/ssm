package com.jt.jsoup.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jt.jsoup.service.BookService;

public class TestJSONP {
	
	//爬取tmooc中的数据
	/*
	 * 1.定义目标网站
	 * 2.定位目标元素
	 */
	@Test
	public void test01() throws IOException{
		String url = "http://www.tmooc.cn/";
		Document document = Jsoup.connect(url).ignoreContentType(true).get();
		// ID选择器  #Id  
		// 标签选择器    
		//类型选择器  .类型
		//层级选择器 div ul li   子元素选择器
		
		//语法:当类型中使用空格分割时,需要使用多个select选择器.
		Elements elements = document.select(".text-center").select(".data-show");
		System.out.println("获取数据个数:"+elements.size());
		for (Element element : elements) {
			String h3 = element.select("h3").get(0).text();
			String p = element.select("p").get(0).text();
			System.out.println(h3+p);
		}	
	}
	
	/**获取免费的课程
	 * 1.获取行级元素 ul
	 * 2.获取块级元素 li
	 */
	@Test
	public void findFree() throws IOException{
		//String url = "http://www.tmooc.cn/free/";
		//String url = "http://www.tmooc.cn/live/";
		String url = "http://www.tmooc.cn/vip/";
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
	}
	
	
	/**
	 * 获取图片
	 * 1.首先获取图片的url
	 * 2.发起请求获取字节码文件
	 * 3.通过输出流,将文件写入磁盘
	 * 4.保存本地磁盘路径
	 * @throws IOException 
	 */
	@Test
	public void findIamge() throws IOException{
		String url = "http://cdn.tmooc.cn/tmooc-web/courseImg/2018/6/22//1AFF49735B814408BDC4D4D839E51235.jpg";
		Response response = Jsoup.connect(url).ignoreContentType(true).execute();
		//System.out.println("图片信息获取成功!!!");
		
		byte[] bytes = response.bodyAsBytes();
		//System.out.println("图片的字节长度:"+bytes.length);
		File file = new File("E:/tmoocimage");
		if(!file.exists()){
			file.mkdirs(); //如果文件不存在则创建文件夹
		}
		
		//1AFF49735B814408BDC4D4D839E51235.jpg";
		String fileName = 
				url.substring(url.lastIndexOf("/")+1);
		String filePath = "E:/tmoocimage/"+fileName;
		
		//关于流的说明 现在学习的都是BIO 问题:会出现阻塞     改进 非阻塞IO NIO
		OutputStream outputStream = 
				new FileOutputStream(new File(filePath));
		outputStream.write(bytes);
		//自己编辑缓存流进行数据输出....
		System.out.println("文件输出完成!!!");
		outputStream.close();//关闭流
	}
	
	
	//实现课程数据入库
	@Test
	public void saveBook() throws IOException{
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("/spring/applicationContext*.xml");
		
		BookService bookService = context.getBean(BookService.class);
		
		String url = "http://www.tmooc.cn/free/";
		String courseName = "免费课程";
		bookService.saveBook(url, courseName);
		System.out.println("恭喜您,爬虫数据入库成功!!!!");
	}
	
	
	//爬取js数据
	@Test
	public void findJSON() throws IOException{
		String url = "http://uc.tmooc.cn/course/findCourseCategory";
		
		String json = Jsoup.connect(url).ignoreContentType(true).execute().body();
		
		//利用ObjectMapper实现数据处理
		System.out.println(json);
	}
	
	//有些数据获取时使用Post提交/get提交 需要传递参数
	public void testArgs(){
		
		String url = "http://url";
		
		Connection connection = Jsoup.connect(url);
		
		//添加参数
		connection.data("key","value");
		//connection.post()/get();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
