/**
 * 文件名称：UserService.java
 * 描述：用户操作接口
 * 创建日期：2019.8.22
 * 最后修改日期：2019.9.10
 * 编码人员：陈文龙，李浩然，潘世康，魏旭凯
 */
package com.landing.service;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.landing.entity.User;

//service层接口类
public interface UserService {
	
	/**
	 * @methodsName: getUser
	 * @description: 根据用户名获取用户对象
	 * @param: userName
	 * @return: User
	 * @throws: 
	 */
	public User getUser(String userName);
	
	/**
	 * @methodsName: login
	 * @description: 处理用户登录操作
	 * @param: userName,password,ip
	 * @return: int
	 * @throws: 
	 */
	public int login(String userName, String password,String ip);//参数为string，可以为userName, email, phoneNumber

	
	/**
	 * @methodsName: register
	 * @description: 处理用户注册操作
	 * @param: userName,password,country,province,urban,address,phoneNumber,email
	 * @return: int
	 * @throws: 
	 */
	public int register( String userName, String password, String country, 
			String province, String urban,String address, String phoneNumber, String email);
 
	/**
	 * @methodsName: checkUserName
	 * @description: 检测输入用户名是否已存在
	 * @param: userName
	 * @return: int
	 * @throws: 
	 */
	public int checkUserName(String userName);
	
	
	/**
	 * @methodsName: changePassword
	 * @description: 更改用户密码
	 * @param: userName,oldPassword,newPassword,againNewPassword
	 * @return: int
	 * @throws: 
	 */
	public int changePassword(String userName, String oldPassword, String newPassword, String againNewPassword);
	
	/**
	 * @methodsName: changeInfo
	 * @description: 修改用户个人资料
	 * @param: userName,country,province,urban,address,phoneNumber,email,product,introduction
	 * @return: int
	 * @throws: 
	 */
	public int changeInfo(String userName, String country, 
			String province, String urban,String address, String phoneNumber, String email, String product, String introduction);
	
	/**
	 * @methodsName: getCode
	 * @description: 获取验证码
	 * @param: email
	 * @return: String
	 * @throws: 
	 */
	public String getCode(String email)  throws IOException, AddressException, MessagingException;
	
	/**
	 * @methodsName: checkEmail
	 * @description: 检查邮箱是否已注册
	 * @param: email
	 * @return: boolean
	 * @throws: 
	 */
	public boolean checkEmail(String email);
}

