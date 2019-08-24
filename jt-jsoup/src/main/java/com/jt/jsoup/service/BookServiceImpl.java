package com.jt.jsoup.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.jsoup.mapper.BookMapper;
import com.jt.jsoup.pojo.Book;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookMapper bookMapper;
	
	/**
	 * 1.通过url实现数据的爬取
	 * 2.将爬取的数据添加Book对象中
	 * 3.利用通用Mapper实现数据的入库
	 * @throws IOException 
	 */
	@Override
	public void saveBook(String url, String courseName) throws IOException {
			Elements elements = Jsoup.connect(url).get()
			.select(".md-bd").select(".clearfix").select(".md-2018040201-lty");
			
			Elements elementsLi = elements.select("li");
			System.out.println("获取课程的个数:"+elementsLi.size());
			
			//1.url地址  2.课程名称  3.课程类型  4学习人数
			for (Element elementLi : elementsLi) {
				//获取图片的地址
				String imageUrl = elementLi.select(".hd-pic img").attr("data-original");
				
				//实现图片的下载
				String imageDir = findImage(imageUrl);
			
				//先获取div
				Element element = 
				elementLi.select(".bd-word").select(".bgcolor-fff a").get(0);
				String bookUrl = element.attr("href");
				String bookName = element.text();
				String type = elementLi.select("span").get(0).text();
				
				//4,388人学习 需要转化为 4388
				/**
				 * 思路:
				 * 	1.先截取数字
				 *  2.将,号替换为空串
				 */
				String num = elementLi.select("span").get(1).text();
				String strNum = num.substring(0,num.indexOf("人"));
				strNum = strNum.replace(",","");
				
				Book book = new Book();
				book.setName(bookName);
				book.setUrl(bookUrl);
				
				//如果没有价格则为0,  如果有价格则截取   ￥ 9.9（会员免费）
				book.setPrice(null);
				book.setNum(Integer.parseInt(strNum));
				book.setType(type);
				book.setImageUrl(imageUrl);
				book.setImageDir(imageDir);
				book.setCourceName(courseName);
				
				//利用通用Mapper实现入库操作
				bookMapper.insert(book);
			}
	
	}
	
	//下载图片  之后返回图片的路径
	public String findImage(String imageUrl){
		String filePath = null;
		try {
			Response response = Jsoup.connect(imageUrl).ignoreContentType(true).execute();
			byte[] bytes = response.bodyAsBytes();
			File file = new File("E:/tmoocimage");
			if(!file.exists()){
				file.mkdirs(); //如果文件不存在则创建文件夹
			}
			String fileName = 
					imageUrl.substring(imageUrl.lastIndexOf("/")+1);
			filePath = "E:/tmoocimage/"+fileName;
			//关于流的说明 现在学习的都是BIO 问题:会出现阻塞     改进 非阻塞IO NIO
			OutputStream outputStream = 
					new FileOutputStream(new File(filePath));
			outputStream.write(bytes);
			//自己编辑缓存流进行数据输出....
			outputStream.close();//关闭流
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		//返回图片真实地址
		return filePath;
	}

}
