/**
 * 文件名称：OrderDetail.java
 * 描述: 订单详情类
 * 创建日期：2019.12.29
 * 最后修改日期：2019.12.29
 * 编码人员：刘凯
 */
package com.landing.entity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan("com.landing.dao")
public class OrderDetail {
	private Integer orderID;
	private Integer quantity;
	private Integer orderDetailID;
	private String ISBN;
	
	public OrderDetail(Integer orderID, Integer quantity, Integer orderDetailID, String ISBN) {
		super();
		this.setOrderID(orderID);
		this.setQuantity(quantity);
		this.setOrderDetailID(orderDetailID);
		this.setISBN(ISBN);
	}
	
	public OrderDetail() {
		super();
	}

	public Integer getOrderID() {
		return orderID;
	}

	public void setOrderID(Integer orderID) {
		this.orderID = orderID;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getOrderDetailID() {
		return orderDetailID;
	}

	public void setOrderDetailID(Integer orderDetailID) {
		this.orderDetailID = orderDetailID;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
}
