/**
 * 文件名称：User.java
 * 描述：用户类
 * 创建日期：2019.12.28
 * 最后修改日期：2019.12.28
 * 编码人员：刘凯
 */
package com.landing.entity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan("com.landing.dao")
public class User {//用户实体类
	private Integer userID;  //userID
	private String userName;// 用户名
	private String password;// 密码
	private String phoneNumber; // 联系方式
	private String address;// 详细地址
	private String access;// 用户权限
	
	//构造函数
	public User(Integer userID, String userName, String password, 
			String address, String phoneNumber, String access) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.access = access;
	}
	
	//下为自动生成的所有的 getter setter 和构造函数
	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public User() {
		super();
	}
}
