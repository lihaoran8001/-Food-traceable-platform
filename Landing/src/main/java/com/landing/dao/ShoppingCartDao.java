
/**
 * 文件名称：shoppingCartDao.java
 * 描述：数据库操作接口
 * 创建日期：2019.12.28
 * 最后修改日期：2019.1.8
 * 编码人员：刘凯、陈文龙
 */
package com.landing.dao;

import org.apache.ibatis.annotations.Param;

import com.landing.entity.ShoppingCart;

public interface ShoppingCartDao {
    
	/**
	 * @methodsName: findByUserID
	 * @description: 通过userID查找购物车信息
	 * @param: userID
	 * @return: ShoppingCart
	 * @throws: 
	 */
	public abstract ShoppingCart findByUserID(@Param("userID")Integer userID); 
	
	/**
	 * @methodsName: updateShoppingCart
	 * @description: 更新购物车信息
	 * @param: userID, quantity, totalPrice
	 * @return: Boolean
	 * @throws: 
	 */
	public abstract boolean updateShoppingCart(@Param("userID")Integer userID, 
			@Param("quantity")Integer quantity, @Param("totalPrice")float totalPrice); 
}
