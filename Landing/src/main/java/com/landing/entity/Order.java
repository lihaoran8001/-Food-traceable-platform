/**
 * 文件名称：Order.java
 * 描述: 订单类
 * 创建日期：2019.12.29
 * 最后修改日期：2019.12.29
 * 编码人员：刘凯
 */
package com.landing.entity;

import java.sql.Date;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan("com.landing.dao")
public class Order {
	private Integer orderID;
	private Integer userID;
	private String orderState;
	private Integer quantity;
	private Date orderTime;
	private float totalPrice;
	private String address;
	private String phoneNumber;
	
	public Order(Integer orderID, Integer userID, String orderState, 
			Integer quantity, Date orderTime, float totalPrice, String address, String phoneNumber) {
		super();
		this.setOrderID(orderID);
		this.setUserID(userID);
		this.setOrderState(orderState);
		this.setQuantity(quantity);
		this.setOrderTime(orderTime);
		this.setTotalPrice(totalPrice);
		this.setAddress(address);
		this.setPhoneNumber(phoneNumber);
	}
	
	//下为自动生成的所有的 getter setter 和构造函数
	public Order() {
		super();
	}
	
	public Integer getOrderID() {
		return orderID;
	}

	public void setOrderID(Integer orderID) {
		this.orderID = orderID;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
