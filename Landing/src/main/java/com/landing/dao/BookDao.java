/**
 * 文件名称：bookDao.java
 * 描述：数据库操作接口
 * 创建日期：2019.12.28
 * 最后修改日期：2019.1.8
 * 编码人员：刘凯、陈文龙
 */
package com.landing.dao;

import java.sql.Date;

import org.apache.ibatis.annotations.Param;

import com.landing.entity.Book;

public interface BookDao {

	/**
	 * @methodsName: findByIBSN
	 * @description: 通过IBSN查找Book
	 * @param: IBSN
	 * @return: Book
	 * @throws: 
	 */
	public abstract Book[] findByISBN(@Param("IBSN")String IBSN); 	
	
	/**
	 * @methodsName: findByBookName
	 * @description: 通过书名查找Book
	 * @param: bookName
	 * @return: Book[]
	 * @throws: 
	 */
	public abstract Book[] findByBookName(@Param("bookName")String bookName);
	
	/**
	 * @methodsName: findByAuthor
	 * @description: 通过作者查找Book
	 * @param: author
	 * @return: Book[]
	 * @throws: 
	 */
	public abstract Book[] findByAuthor(@Param("author")String author);
	
	/**
	 * @methodsName: findByPublisher
	 * @description: 通过出版社查找Book
	 * @param: publisher
	 * @return: Book[]
	 * @throws: 
	 */
	public abstract Book[] findByPublisher(@Param("publisher")String publisher);
	
	/**
	 * @methodsName: findByCategory
	 * @description: 通过分类查找Book
	 * @param: Category
	 * @return: Book[]
	 * @throws: 
	 */
	public abstract Book[] findByCategory(@Param("category")String category);
	
	/**
	 * @methodsName: insertBook
	 * @description: 插入一本书的信息
	 * @param: bookName, author, price, publisher, publishTime, inventory, category, bookInfo, imagePath, ISBN
	 * @return: Boolean
	 * @throws: 
	 */
	public abstract boolean insertBook(@Param("bookName")String bookName, @Param("author")String author, 
			@Param("price")float price, @Param("publisher")String publisher, @Param("publishTime")Date publishTime, 
			@Param("inventory")Integer inventory, @Param("category")String category, @Param("bookInfo")String bookInfo, 
			@Param("imagePath")String imagePath, @Param("ISBN")String ISBN);
	
	/**
	 * @methodsName: updateBookInfo
	 * @description: 更新书籍信息
	 * @param: bookID, bookName, author, price, publisher, publishTime, inventory, category, bookInfo, imagePath, ISBN
	 * @return: Boolean
	 * @throws: 
	 */
	public abstract boolean updateBookInfo(@Param("bookID")Integer bookID, @Param("bookName")String bookName, 
			@Param("author")String author, 
			@Param("price")float price, @Param("publisher")String publisher, @Param("publishTime")Date publishTime, 
			@Param("inventory")Integer inventory, @Param("category")String category, @Param("bookInfo")String bookInfo, 
			@Param("imagePath")String imagePath, @Param("ISBN")String ISBN);
	
	/**
	 * @methodsName: deleteBookInfo
	 * @description: 删除一本书的信息
	 * @param: bookID
	 * @return: Boolean
	 * @throws: 
	 */
	public abstract boolean deleteBookInfo(Integer bookID);
}
