/**
 * 文件名称：ShoppingCart.java
 * 描述：购物车类
 * 创建日期：2019.12.28
 * 最后修改日期：2019.12.28
 * 编码人员：刘凯
 */
package com.landing.entity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan("com.landing.dao")
public class ShoppingCart {//购物车实体类
	private Integer shoppingCartID;
	private Integer userID;
	private Integer quantity;
	private float totalPrice;
	
	//构造函数
	public ShoppingCart(Integer shoppingCartID, Integer userID, Integer quantity, float totalPrice) {
		super();
		this.setShoppingCartID(shoppingCartID);
		this.setUserID(userID);
		this.setQuantity(quantity);
		this.setTotalPrice(totalPrice);
	}
	
	//下为自动生成的所有的 getter setter 和构造函数
	public ShoppingCart() {
		super();
	}

	public Integer getShoppingCartID() {
		return shoppingCartID;
	}

	public void setShoppingCartID(Integer shoppingCartID) {
		this.shoppingCartID = shoppingCartID;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
}
