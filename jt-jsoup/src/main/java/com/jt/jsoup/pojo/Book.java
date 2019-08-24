package com.jt.jsoup.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="book")
public class Book{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;	//课程名称
	private String url;		//课程访问地址
	private Integer price;  //价格
	private Integer num;	//学生人数
	private String type;	//课程类型
	private String imageUrl;	//图片地址
	private String imageDir;	//图片本地
	private String courceName;	//课程方向
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getImageDir() {
		return imageDir;
	}
	public void setImageDir(String imageDir) {
		this.imageDir = imageDir;
	}
	public String getCourceName() {
		return courceName;
	}
	public void setCourceName(String courceName) {
		this.courceName = courceName;
	}
}
