package org.imooc.dao;

import java.util.List;

import org.imooc.bean.Comment;
import org.imooc.bean.Orders;
import org.imooc.bean.record_order_createtime;

public interface OrdersDao {
	/**
	 * 新增
	 * @param orders 订单表对象
	 * @return 影响行数
	 */
	int insert(Orders orders);
	
	/**
     * 根据主键查询订单表对象
     * @param id 主键值
     * @return 订单表对象
     */
	Orders selectById(Long id);
	
	/**
	 * 修改
	 * @param orders 订单表对象
	 * @return 影响行数
	 */
	int update(Orders orders);
	
	/**
	 * 根据条件查询订单列表
	 * @param orders 查询条件
	 * @return 订单列表
	 */
	List<Orders> select(Orders orders);

	int update_ordertime(record_order_createtime record_order_createtime);

	List<Orders> searchByPage(Orders condition);

	List<Orders> selectOrdersByBusinessId(Orders ordersForSelect);

	List<Orders> search_Orders(Orders condition);

}