/**
 * 文件名称：shoppingCartDetailDao.java
 * 描述：数据库操作接口
 * 创建日期：2019.12.29
 * 最后修改日期：2019.12.29
 * 编码人员：刘凯
 */
package com.landing.dao;

import org.apache.ibatis.annotations.Param;

import com.landing.entity.ShoppingCartDetail;

public interface ShoppingCartDetailDao {
    
	/**
	 * @methodsName: insertDetail
	 * @description: 向shoppingcartdetail数据库中插入一行新的数据
	 * @param: userID, ISBN, quantity
	 * @return: Boolean
	 * @throws: 
	 */
	public abstract boolean insertDetail(@Param("userID")Integer userID, 
			@Param("ISBN")String ISBN, @Param("quantity")Integer quantity); 
	
	/**
	 * @methodsName: deleteDetail
	 * @description: 删除购物车详情信息
	 * @param: shoppingCartDetailID
	 * @return: Boolean
	 * @throws: 
	 */
	public abstract boolean deleteDetail(@Param("shoppingCartDetailID")Integer shoppingCartDetailID); 
}
