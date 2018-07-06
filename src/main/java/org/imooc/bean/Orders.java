package org.imooc.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Orders extends BaseBean{
	private Long id;//订单的主键
	private Long memberId;//会员的主键
	private Long businessId;//商品的的主键
	private Integer num;//消费的人数
	private Integer commentState;//评论状态
	private Double price;//商品的价格
	private Business business;//商品的实体
	private Member member;//会员的实体
	private Date create_time;//创建订单的时间
	
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Long getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	public Integer getNum() {
	    return num;
	}
	public void setNum(Integer num) {
	    this.num = num;
	}
	public Integer getCommentState() {
		return commentState;
	}
	public void setCommentState(Integer commentState) {
		this.commentState = commentState;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Business getBusiness() {
		return business;
	}
	public void setBusiness(Business business) {
		this.business = business;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
}