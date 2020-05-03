/**
 * 文件名称：userDao.java
 * 描述：数据库操作接口
 * 创建日期：2019.12.28
 * 最后修改日期：2019.12.28
 * 编码人员：刘凯
 */
package com.landing.dao;

import org.apache.ibatis.annotations.Param;

import com.landing.entity.User;

public interface UserDao {
    
	/**
	 * @methodsName: insertOneUser
	 * @description: 向数据库插入一个用户
	 * @param: userName,password,phoneNumber,address,access
	 * @return: boolean
	 * @throws: 
	 */
	public abstract boolean insertOneUser( @Param("userName")String userName,  
			@Param("password")String password, @Param("phoneNumber")String phoneNumber, 
			@Param("address")String address, @Param("access")String access);
	
	/**
	 * @methodsName: findByUserID
	 * @description: 通过userID查找用户
	 * @param: userID
	 * @return: User
	 * @throws: 
	 */
	public abstract User findByUserID(String userID); 
	
	/**
	 * @methodsName: findByUserName
	 * @description: 通过userName查找用户
	 * @param: userName
	 * @return: User
	 * @throws: 
	 */
	public abstract User[] findByUserName(String userName); 
	
	/**
	 * @methodsName: deleteUser
	 * @description: 通过useName删除用户
	 * @param: userName
	 * @return: boolean
	 * @throws: 
	 */
	public abstract boolean deleteUser(String userName);
}
