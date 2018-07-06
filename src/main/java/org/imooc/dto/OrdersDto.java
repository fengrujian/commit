package org.imooc.dto;

import java.util.Date;

import org.imooc.bean.Orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class OrdersDto extends Orders{

    private String img;
    private String title;//订单对应的商品的标题,页面显示需要用到
    private Integer count;
    private Long phone;//订单对应的会员手机号,页面显示需要用到
    private String iscomment;//该订单是否已经评论了 ，在页面显示需要用到
    private String create_orderstime;
    private String businesser_name;//对应的商家的名称
    private String user_name;//对应用户
    private String user_chname;//对应中文名
    private String group_name;//对应的角色
    
    
    public String getUser_chname() {
		return user_chname;
	}

	public void setUser_chname(String user_chname) {
		this.user_chname = user_chname;
	}
	
    
    
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getBusinesser_name() {
		return businesser_name;
	}

	public void setBusinesser_name(String businesser_name) {
		this.businesser_name = businesser_name;
	}

	public String getCreate_orderstime() {
		return create_orderstime;
	}
	
	public void setCreate_orderstime(String create_orderstime) {
		this.create_orderstime = create_orderstime;
	}
	
	public String getIscomment() {
		return iscomment;
	}
	
	public void setIscomment(String iscomment) {
		this.iscomment = iscomment;
	}
	
	public Long getPhone() {
		return phone;
	}
	
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	
	public String getImg() {
		return img;
	}
	
	public void setImg(String img) {
		this.img = img;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
    

}