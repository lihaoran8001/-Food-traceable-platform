/**
 * 文件名称：UserServiceImpl.java
 * 描述：用户操作类
 * 创建日期：2019.8.22
 * 最后修改日期：2019.9.10
 * 编码人员：陈文龙，李浩然，潘世康，魏旭凯
 */
package com.landing.service.impl;

import java.io.IOException;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.stereotype.Service;

import com.landing.dao.UserDao;
import com.landing.entity.CodeUtil;
import com.landing.entity.MailUtil;
import com.landing.entity.User;
import com.landing.service.UserService;


@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;

	/**
	 * @methodsName: login
	 * @description: 处理用户登录操作
	 * @param: userName,password,ip
	 * @return: int
	 * @throws: 
	 */
	@Override
	public int login(String userName, String password,String ip) {
		System.out.println(">>>>>进入login函数");
		User result = userDao.findByUserName(userName);

		if (result == null) {
			return 200;// 用户不存在
		} else {
			if (password.equals(result.getPassword())) {
				result.setState("online");
				result.setIp(ip);
				userDao.loginUpdate(result.getId(), ip);
				return 201;// 登录成功
			} else {
				return 202;// 密码错误
			}
		}
	}

	/**
	 * @methodsName: register
	 * @description: 处理用户注册操作
	 * @param: userName,password,country,province,urban,address,phoneNumber,email
	 * @return: int
	 * @throws: 
	 */
	public int register( String userName, String password, String country, String province, String urban,
			String address, String phoneNumber, String email) {
		User result = userDao.findByUserName(userName);// 首先判断是否存在使用该用户名的用户

		if (result == null) {
			boolean flag = userDao.insertOneUser( userName, password, country, province, 
					urban, address, phoneNumber,email);

			if (flag) {
				return 400;// 注册成功
			} else {
				return 401;// 数据库插入失败
			}
		} else {
			return 402;// 用户名已存在
		}
	}

	/**
	 * @methodsName: checkUserName
	 * @description: 检测输入用户名是否已存在
	 * @param: userName
	 * @return: int
	 * @throws: 
	 */
	public int checkUserName(String userName){
		User result = userDao.findByUserName(userName);

		if (result == null) {
			return 1; // 用户名未使用
		} else {
			return 0; //用户名已使用
		}
	}


	/**
	 * @methodsName: changePassword
	 * @description: 更改用户密码
	 * @param: userName,oldPassword,newPassword,againNewPassword
	 * @return: int
	 * @throws: 
	 */
	@Override
	public int changePassword(String userName, String oldPassword, String newPassword, String againNewPassword) {
		User result = userDao.findByUserName(userName);

		if(result == null) {
			return 200; //用户不存在
		}

		if(!oldPassword.equals(result.getPassword())) {
			return 201; //原密码有误
		}

		if(!newPassword.equals(againNewPassword)) {
			return 202; //输入两遍新密码不同
		}

		boolean flag = userDao.changePassword(userName, newPassword);
		if(!flag) {
			return 203; //更改失败
		} else {
			return 204; //更改成功
		}
	}

	/**
	 * @methodsName: changeInfo
	 * @description: 修改用户个人资料
	 * @param: userName,country,province,urban,address,phoneNumber,email,product,introduction
	 * @return: int
	 * @throws: 
	 */
	@Override
	public int changeInfo(String userName, String country, 
			String province, String urban,String address, String phoneNumber, String email, String product, String introduction) {
		User result = userDao.findByUserName(userName);

		if(result == null) {
			return 200; //用户不存在
		}

		boolean flag = userDao.changeInfo(userName, country, province, urban, address, phoneNumber, email, product,  introduction);
		if(!flag) {
			return 201; //更改失败
		} else {
			return 202; //更改成功
		}
	}

	/**
	 * @methodsName: getCode
	 * @description: 获取验证码
	 * @param: email
	 * @return: String
	 * @throws: 
	 */
	@Override
	public String getCode(String email) throws IOException, AddressException, MessagingException{
		CodeUtil rcode = new CodeUtil();
		String code = rcode.generateUniqueCode();
		MailUtil mailUtil = new MailUtil();
		mailUtil.sendmail(email, code);
		return code;
	}

	/**
	 * @methodsName: checkEmail
	 * @description: 检查邮箱是否已注册
	 * @param: email
	 * @return: boolean
	 * @throws: 
	 */
	@Override
	public boolean checkEmail(String email) {
		User result = userDao.findByMail(email);

		if(result == null) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * @methodsName: getUser
	 * @description: 根据用户名获取用户对象
	 * @param: userName
	 * @return: User
	 * @throws: 
	 */
	@Override
	public User getUser(String userName) {
		return  userDao.findByUserName(userName);
	}

}