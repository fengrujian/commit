package org.imooc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.imooc.bean.Business;
import org.imooc.bean.Comment;
import org.imooc.bean.Orders;
import org.imooc.bean.Page;
import org.imooc.constant.CommentStateConst;
import org.imooc.dao.CommentDao;
import org.imooc.dao.OrdersDao;
import org.imooc.dto.CommentDto;
import org.imooc.dto.CommentForSubmitDto;
import org.imooc.dto.CommentListDto;
import org.imooc.dto.OrdersDto;
import org.imooc.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Resource
	private CommentDao commentDao;
	@Resource
	private OrdersDao ordersDao;

	@Override
	@Transactional
	public boolean add(CommentForSubmitDto commentForSubmitDto) {
		Comment comment = new Comment();
		BeanUtils.copyProperties(commentForSubmitDto, comment);
		comment.setId(null);
		comment.setOrdersId(commentForSubmitDto.getId());
		comment.setCreateTime(new Date());
		//保存评论
		int numinsert = commentDao.insert(comment);
		Orders orders = new Orders();
		orders.setId(commentForSubmitDto.getId());
		orders.setCommentState(CommentStateConst.HAS_COMMENT);
		//更新订单评论状态
		int numupdate = ordersDao.update(orders);
		if(numinsert==1 && numupdate==1){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public CommentListDto getListByBusinessId(Long businessId, Page page) {
		CommentListDto result = new CommentListDto();
		//组织查询条件
		Comment comment = new Comment();
		Orders orders = new Orders();
		Business business = new Business();
		//评论里包含了订单对象
		comment.setOrders(orders);
		//订单对象里包含了商品对象
		orders.setBusiness(business);
		//设置商品主键
		business.setId(businessId);
		//前端app页码从0开始计算，这里需要+1
		page.setCurrentPage(page.getCurrentPage() + 1);
		//设置分页条件
		comment.setPage(page);
		List<Comment> commentList = commentDao.selectByPage(comment);	
		//组织返回值
		List<CommentDto> data = new ArrayList<>();
		result.setData(data);
		if(commentList.size()>0){
			for(Comment commentTemp : commentList) {
				CommentDto commentDto = new CommentDto();
				data.add(commentDto);
				BeanUtils.copyProperties(commentTemp, commentDto);
				// 隐藏手机号中间4位
				StringBuffer phoneBuffer = new StringBuffer(String.valueOf(commentTemp.getOrders().getMember().getPhone()));
				commentDto.setUsername(phoneBuffer.replace(3, 7, "****").toString());
			}
		}
		result.setHasMore(page.getCurrentPage() < page.getTotalPage());
		return result;
	}
	
    /**
     *  查询评论列表
     */
	@Override
	public List<CommentDto> search_comment(CommentDto commentDto) {
		List<CommentDto> result = new ArrayList<CommentDto>();
		Comment condition = new Comment();
		BeanUtils.copyProperties(commentDto, condition);
		Orders orders = new Orders();
		Business business = new Business();
		if(condition.getOrders()==null){
			condition.setOrders(orders);
		}
		List<Comment> CommentList = commentDao.search_comment(condition);//使用mybatis的分页拦截器
		if(CommentList.size()>0){
			for (Comment comment : CommentList) {
				CommentDto commentDto1 = new CommentDto();
				BeanUtils.copyProperties(comment, commentDto1);
				result.add(commentDto1);
			}
		}
		return result;
	}
	

	
}