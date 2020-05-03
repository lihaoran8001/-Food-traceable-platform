/**
 * 文件名称：UserController.java
 * 描述：响应并处理网页请求
 * 创建日期：2019.8.22
 * 最后修改日期：2019.9.10
 * 编码人员：陈文龙，李浩然，潘世康，魏旭凯
 */
package com.landing.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.landing.entity.User;
import com.landing.service.impl.UserServiceImpl;

//controller类
@Controller
@RequestMapping(value = "/user")
public class UserController {
	private String code = null;

	//userService注入
	@Autowired
	private UserServiceImpl userService;

	/**
	* @methodsName: login
	* @description: 映射user/login.action的post请求，用于登录
	* @param: userName,password,request
	* @return: ModelAndView
	* @throws: 
	*/
	@RequestMapping(value = "/login.action", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam("userName") String userName,
			@RequestParam("password") String password,HttpServletRequest request,HttpSession session) {
		//控制台输出，用于调试
		System.out.println(">>>>>进入ModelAndView函数,userName:"+userName+",password:"+password+",ip:"+request.getRemoteAddr());

		String ip=request.getRemoteAddr();

		//调用service层login函数，返回值为登录状态对应的int值
		int result=userService.login(userName, password,ip);

		if (result==201) {// 用户名与密码匹配
			User currentUser=userService.getUser(userName);
			session.setAttribute("userName", currentUser.getUserName());
			session.setAttribute("phoneNumber",currentUser.getPhoneNumber());
			session.setAttribute("email",currentUser.getEmail());
			session.setAttribute("country",currentUser.getCountry());
			session.setAttribute("province",currentUser.getProvince());
			session.setAttribute("urban",currentUser.geturban());
			session.setAttribute("address",currentUser.getAddress());
			session.setAttribute("product",currentUser.getProduct());
			session.setAttribute("introduction",currentUser.getIntroduction());
			session.setAttribute("infoChanged", false);
			

			return new ModelAndView("redirect:/test.jsp");// 跳转至测试页面
		} else {
			return new ModelAndView("redirect:/start.jsp");// 返回初始页面
		}
	} 


	/**
	* @methodsName: checkUserName
	* @description: 映射user/check.action的post请求，用于查询用户名是否已存在
	* @param: model
	* @return: Map<String,String>
	* @throws: 
	*/
	@RequestMapping(value="/check.action",method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,String> checkUserName(@RequestBody User model){
		//控制台输出，用于调试
		System.out.println(">>>>>进入checkUserName函数,传入的userName:"+model.getUserName());

		//调用service层checkUserName函数，返回值为登录状态对应的int值
		int result=userService.checkUserName(model.getUserName());
		if(result==1){   //返回1用户名可用
			Map<String,String> m1 = new HashMap<String,String>();
			m1.put("result","用户名可用");
			return m1;
		}
		else{//返回其他，用户名已用
			Map<String,String> m1 = new HashMap<String,String>();
			m1.put("result","用户名已使用");
			return m1;
		}
	}

	/**
	* @methodsName: transmit
	* @description: 映射user/transmit.action的post请求，用于接收客户端发来的本地区块链文件
	* @param: user,session
	* @return: void
	* @throws: 
	*/
	@RequestMapping(value="/transmit.action",method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public void transmit(@RequestBody User user,HttpSession session) {
		System.out.println(user.getUserName());
		session.setAttribute("blockChainFile", user.getUserName());//将区块链文件加入session
	}


	/**
	* @methodsName: register
	* @description: 映射user/register.action的post请求，用于注册
	* @param: model
	* @return: ModelAndView
	* @throws: 
	*/
	@RequestMapping(value="/register.action",method=RequestMethod.POST)
	public ModelAndView register(User model){
		System.out.println(">>>>>进入register函数,传入的参数:"+model.getUserName()+","+ model.getPassword()+","
				+model.getCountry()+","+ model.getProvince()+","+ model.geturban()+","+ model.getAddress()+","
				+model.getPhoneNumber()+","+ model.getEmail());


		//调用service层checkUserName函数，返回值为该userName的信息
		int checkResult=userService.checkUserName(model.getUserName());// 检查用户名是否存在
		if(checkResult==1) {
			// 用户名未被占用
			// 执行数据库插入操作
			//调用service层register函数，返回值为该userName的信息
			int result=userService.register(model.getUserName(), model.getPassword(), 
					model.getCountry(), model.getProvince(), model.geturban(), model.getAddress(),
					model.getPhoneNumber(), model.getEmail());

			if(result==400){
				return new ModelAndView("redirect:/start.jsp");// 注册成功，跳转至初始页面
			}
			else{
				return new ModelAndView("redirect:/register.html");// 注册失败，返回注册页面
			}

		}else{// 用户名已被占用
			return new ModelAndView("redirect:/register.html");// 注册失败，返回注册页面
		}


	}

	/**
	* @methodsName: changePassword
	* @description: 映射user/changePassword.action的post请求，用于更改密码
	* @param: userName,oldPassword,newPassword,againNewPassword
	* @return: ModelAndView
	* @throws: 
	*/
	@RequestMapping(value = "/changePassword.action",method=RequestMethod.POST)
	public ModelAndView changePassword(@RequestParam("userName")String userName, 
			@RequestParam("oldPassword")String oldPassword, @RequestParam("newPassword")String newPassword,
			@RequestParam("againNewPassword")String againNewPassword) {
		int result = userService.changePassword(userName, oldPassword, newPassword, againNewPassword);

		if(result == 204) {
			System.out.println("更改成功----------");
			return new ModelAndView("redirect:/main.jsp");
		} else {
			System.out.println("更改失败 原因：" + result);
			return new ModelAndView("redirect:/changePassword.html");
		}
	}

	/**
	* @methodsName: changeInfo
	* @description: 映射user/changeInfo.action的post请求，用于更改信息
	* @param: country,province,urban,address,phoneNumber,email,product,introduction,session
	* @return: ModelAndView
	* @throws: 
	*/
	@RequestMapping(value = "/changeInfo.action",method=RequestMethod.POST)
	public ModelAndView changeInfo(@RequestParam("country")String country, @RequestParam("province")String province, 
			@RequestParam("urban")String urban, @RequestParam("address")String address, 
			@RequestParam("phoneNumber")String phoneNumber, @RequestParam("email")String email, 
			@RequestParam("product")String product, @RequestParam("introduction")String introduction,HttpSession session) {
		
		String name=(String) session.getAttribute("userName");
		int result = userService.changeInfo(name, country, province, urban, address, phoneNumber, email, product, introduction);

		if(result == 202) {
			System.out.println("更改成功");
			session.setAttribute("phoneNumber",phoneNumber);
			session.setAttribute("email",email);
			session.setAttribute("country",country);
			session.setAttribute("province",province);
			session.setAttribute("urban",urban);
			session.setAttribute("address",address);
			session.setAttribute("product",product);
			session.setAttribute("introduction",introduction);
			session.setAttribute("infoChanged", true);//标记更改成功
			return new ModelAndView("redirect:/changeInfo.jsp");
		} else {
			System.out.println("更改失败 原因：" + result);
			session.setAttribute("infoChanged", false);//标记更改失败
			return new ModelAndView("redirect:/changeInfo.jsp");
		}
	}

	/**
	* @methodsName: getCode
	* @description: 映射user/getCode.action的post请求，用于检查邮箱是否合法，可用并发送验证码
	* @param: model
	* @return: Map<String,String>
	* @throws: 
	*/
	@RequestMapping(value = "/getCode.action", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,String> getCode(@RequestBody User model) {
		//控制台输出，用于调试
		System.out.println(">>>>>进入getCode函数,传入的email:"+model.getEmail());

		String reg = "^[0-9a-z]+\\w*" + "@" + "([0-9a-z]+\\.)+[0-9a-z]+$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(model.getEmail());
		if(!matcher.matches()) {
			//测试用
			System.out.println(">>>>>123进入checkEmail函数,传入的email:"+model.getEmail());
			Map<String,String> m1 = new HashMap<String,String>();
			m1.put("result","请输入正确的邮箱");
			return m1;
		}

		//调用service层checkEmail函数，返回值为登录状态对应的int值
		boolean result=userService.checkEmail(model.getEmail());
		if(result){   //返回true用户名可用

			//测试用
			System.out.println(">>>>>1234进入checkEmail函数,传入的email:"+model.getEmail());

			try {
				code = userService.getCode(model.getEmail());
			} catch (IOException | MessagingException e) {
				e.printStackTrace();
			}
			System.out.println(code);

			Map<String,String> m1 = new HashMap<String,String>();
			m1.put("result","验证码已发送");
			return m1;
		}
		else{//返回false邮箱已用

			//测试用
			System.out.println(">>>>>123445进入checkEmail函数,传入的email:"+model.getEmail());

			Map<String,String> m1 = new HashMap<String,String>();
			m1.put("result","邮箱已被注册");
			return m1;
		}
	}

	/**
	* @methodsName: checkCode
	* @description: 映射user/gcheckCode.action的post请求，用于检查验证码是否正确
	* @param: emailCode
	* @return: Map<String,String>
	* @throws: 
	*/
	@RequestMapping(value = "/checkCode.action", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,String> checkCode(@RequestBody Map<String, String> emailCode){
		String eCode = emailCode.get("emailCode");
		System.out.println(code);
		System.out.println(eCode);

		if(eCode.equals(code)) {
			Map<String,String> m1 = new HashMap<String,String>();
			m1.put("result","验证码正确");
			return m1;
		} else {
			Map<String,String> m1 = new HashMap<String,String>();
			m1.put("result","验证码错误");
			return m1;
		}
	}

}