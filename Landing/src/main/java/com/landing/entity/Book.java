/**
 * 文件名称：Book.java
 * 描述：书本类
 * 创建日期：2019.12.28
 * 最后修改日期：2019.12.28
 * 编码人员：刘凯
 */
package com.landing.entity;

import java.sql.Date;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan("com.landing.dao")
public class Book {//书籍实体类
	private Integer bookID;  //userID
	private String bookName;// 书名
	private String author;// 作者
	private float price; // 书价格
	private String publisher;// 出版社
	private Date publishTime;// 初版时间
	private Integer inventory;// 书库存量
	private String category;// 书籍分类
	private String bookInfo;// 书籍信息
	private String imagePath;// 书籍封面图片储存路径
	private String IBSN;// 书籍IBSN号
	
	//构造函数
	public Book(Integer bookID, String bookName, String author, float price, 
			String publisher, Date publishTime, Integer inventory, String category, 
			String bookInfo, String imagePath, String IBSN) {
		super();
		this.setBookID(bookID);
		this.setBookName(bookName);
		this.setAuthor(author);
		this.setPrice(price);
		this.setPublisher(publisher);
		this.setPublishTime(publishTime);
		this.setInventory(inventory);
		this.setCategory(category);
		this.setBookInfo(bookInfo);
		this.setImagePath(imagePath);
		this.setIBSN(IBSN);
	}
	
	//下为自动生成的所有的 getter setter 和构造函数
	public Book() {
		super();
	}

	public Integer getBookID() {
		return bookID;
	}

	public void setBookID(Integer bookID) {
		this.bookID = bookID;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBookInfo() {
		return bookInfo;
	}

	public void setBookInfo(String bookInfo) {
		this.bookInfo = bookInfo;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getIBSN() {
		return IBSN;
	}

	public void setIBSN(String iBSN) {
		IBSN = iBSN;
	}
}
