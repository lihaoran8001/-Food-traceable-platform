/**
 * 文件名称：orderDetailDao.java
 * 描述：数据库操作接口
 * 创建日期：2019.12.29
 * 最后修改日期：2019.12.29
 * 编码人员：刘凯
 */
package com.landing.dao;

import java.sql.Date;

import org.apache.ibatis.annotations.Param;

import com.landing.entity.OrderDetail;

public interface OrderDetailDao {
	/**
	 * @methodsName: insertDetail
	 * @description: 向数据库插入一行订单详情
	 * @param: orderID, quantity, ISBN
	 * @return: Boolean
	 * @throws: 
	 */
	public abstract boolean insertDetail(@Param("orderID")Integer orderID, 
			@Param("quantity")Integer quantity, @Param("ISBN")String ISBN);
}
