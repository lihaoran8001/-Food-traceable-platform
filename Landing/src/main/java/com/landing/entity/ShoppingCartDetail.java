/**
 * 文件名称：ShoppingCartDetail.java
 * 描述：购物车详情类
 * 创建日期：2019.12.29
 * 最后修改日期：2019.12.29
 * 编码人员：刘凯
 */
package com.landing.entity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan("com.landing.dao")
public class ShoppingCartDetail {//购物车详情实体类
	private Integer shoppingCartDetailID;
	private Integer userID;
	private Integer quantity;
	private String IBSN;
	
	//构造函数
	public ShoppingCartDetail(Integer shoppingCartDetailID, Integer userID, Integer quantity, String IBSN) {
		super();
		this.setShoppingCartDetailID(shoppingCartDetailID);
		this.setUserID(userID);
		this.setQuantity(quantity);
		this.setIBSN(IBSN);
	}
	
	//下为自动生成的所有的 getter setter 和构造函数
	public ShoppingCartDetail() {
		super();
	}

	public Integer getShoppingCartDetailID() {
		return shoppingCartDetailID;
	}

	public void setShoppingCartDetailID(Integer shoppingCartDetailID) {
		this.shoppingCartDetailID = shoppingCartDetailID;
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

	public String getIBSN() {
		return IBSN;
	}

	public void setIBSN(String iBSN) {
		IBSN = iBSN;
	}
}
