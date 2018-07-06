package org.imooc.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.imooc.bean.Ad;
import org.imooc.bean.Orders;
import org.imooc.bean.record_order_createtime;
import org.imooc.constant.CommentStateConst;
import org.imooc.dao.OrdersDao;
import org.imooc.dto.AdDto;
import org.imooc.dto.OrdersDto;
import org.imooc.service.OrdersService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceImpl implements OrdersService {
	@Resource
	private OrdersDao ordersDao;
	
	@Value("${businessImage.url}")
    private String businessImageUrl;

	@Override
	public boolean add(OrdersDto ordersDto) {
		Orders orders = new Orders();
		BeanUtils.copyProperties(ordersDto, orders);
		//给订单添加评论状态
		orders.setCommentState(CommentStateConst.NOT_COMMENT);
		int num = ordersDao.insert(orders);
		if(num==1){
			return true;
		}
		return false;
	}
	@Override
	public OrdersDto getById(Long id) {
		OrdersDto result = new OrdersDto();
		Orders orders = ordersDao.selectById(id);
		if(orders!=null){
			BeanUtils.copyProperties(orders, result);
		}
		return result;
	}
	//根据会员的主键来查询订单列表
	@Override
	public List<OrdersDto> getListByMemberId(Long memberId) {
		List<OrdersDto> result = new ArrayList<OrdersDto>();
		Orders ordersForSelect = new Orders();
		ordersForSelect.setMemberId(memberId);
		List<Orders>  ordersList = ordersDao.select(ordersForSelect);
		if(ordersList.size()>0){
			for(Orders orders : ordersList) {
				OrdersDto ordersDto = new OrdersDto();
				result.add(ordersDto);
				BeanUtils.copyProperties(orders, ordersDto);
				ordersDto.setImg(businessImageUrl + orders.getBusiness().getImgFileName());
				ordersDto.setTitle(orders.getBusiness().getTitle());
				ordersDto.setCount(orders.getBusiness().getNumber());
			}	
		}
		return result;
	}
    /**
     * 更新订单的下单时间
     */
	@Override
	public boolean update_ordertime(record_order_createtime record_order_createtime) {
		//更新订单的下单时间
		int num=ordersDao.update_ordertime(record_order_createtime);
		if(num==1){
			return true;
		}
		return false;
	}

	@Override
	public List<OrdersDto> search_Orders(OrdersDto ordersDto) {
		List<OrdersDto> result = new ArrayList<OrdersDto>();
		Orders condition = new Orders();
		BeanUtils.copyProperties(ordersDto, condition);
		List<Orders> OrdersList = ordersDao.search_Orders(condition);//使用mybatis的分页拦截器
		if(OrdersList.size()>0){
			for (Orders orders : OrdersList) {
				OrdersDto ordersDto1 = new OrdersDto();
				BeanUtils.copyProperties(orders, ordersDto1);
				result.add(ordersDto1);
			}
			return result;
		}
		return result;
	}
	
	
	@Override
	public List<Orders> ishave(Long id) {
		List<Orders>  ordersList = new ArrayList<Orders>();
		if(id!=null){
			Orders ordersForSelect = new Orders();
			ordersForSelect.setBusinessId(id);
		    ordersList = ordersDao.selectOrdersByBusinessId(ordersForSelect);
			return ordersList;
		}
		return ordersList;
	}
	
	
}