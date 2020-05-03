/**
 * 文件名称：orderDao.java
 * 描述：数据库操作接口
 * 创建日期：2019.12.29
 * 最后修改日期：2019.1.8
 * 编码人员：刘凯、陈文龙
 */
package com.landing.dao;

import java.sql.Date;

import org.apache.ibatis.annotations.Param;

import com.landing.entity.Order;

public interface OrderDao {
	
	/**
	 * @methodsName: insertOrder
	 * @description: 向数据库插入一行新的订单信息
	 * @param: userID, orderState, quantity, orderTime, totalPrice, address, phoneNumber
	 * @return: Boolean
	 * @throws: 
	 */
	public abstract boolean insertOrder(@Param("userID")Integer userID, @Param("orderState")String orderState, 
			@Param("quantity")Integer quantity, @Param("orderTime")Date orderTime, @Param("totalPrice")float totalPrice, 
			@Param("address")String address, @Param("phoneNumber")String phoneNumber);
	
	/**
	 * @methodsName: deleteOrder
	 * @description: 删除订单信息
	 * @param: orderID
	 * @return: Boolean
	 * @throws: 
	 */
	public abstract boolean deleteOrder(Integer orderID);
	
	/**
	 * @methodsName: updateOrder
	 * @description: 更新订单状态
	 * @param: orderID, orderState
	 * @return: Boolean
	 * @throws: 
	 */
	public abstract boolean updateOrder(Integer orderID, 
			@Param("bookID")String orderState);
	
	/**
	 * @methodsName: findByUserID
	 * @description: 通过userID查找Order
	 * @param: userID
	 * @return: Order[]
	 * @throws: 
	 */
	public abstract Order[] findByUserID(Integer userID);
}
