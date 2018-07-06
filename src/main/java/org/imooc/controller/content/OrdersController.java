package org.imooc.controller.content;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.imooc.bean.Member;
import org.imooc.bean.User;
import org.imooc.constant.PageCodeEnum;
import org.imooc.dto.AdDto;
import org.imooc.dto.BusinessDto;
import org.imooc.dto.BusinesserDto;
import org.imooc.dto.GroupDto;
import org.imooc.dto.OrdersDto;
import org.imooc.dto.UserDto;
import org.imooc.service.AdService;
import org.imooc.service.BusinessService;
import org.imooc.service.BusinesserService;
import org.imooc.service.GroupService;
import org.imooc.service.MemberService;
import org.imooc.service.OrdersService;
import org.imooc.service.UserService;
import org.imooc.util.CommonUtil;
import org.imooc.util.TimetoStringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/orders")
public class OrdersController {
	@Autowired
	private OrdersService ordersService;
	
	@Resource
	private BusinessService businessService;
	
	@Resource
	private BusinesserService businesserService;
	
	@Resource
	private MemberService memberService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private GroupService  groupService;
	
	//定义全局变量
	private int currentPage_;//页码
	 
	private String title_;//标题  模糊查询
	/**
	 * 初始化浏览订单列表
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String init(Model model,@RequestParam("name") String name) {
		//新加的
		UserDto userDto = new UserDto();
		userDto.setName(name);
		if(name!=null){
			userDto = userService.getGorudId_ByName(userDto);
		}
		GroupDto groupDto = null;
		if(userDto!=null){
			 groupDto = groupService.getById(userDto.getGroupId());
		}
		//新加的结束
		OrdersDto ordersDto = new OrdersDto();
		List<OrdersDto> list = ordersService.search_Orders(ordersDto);
		List<OrdersDto> list_new =new ArrayList<>();
		List<OrdersDto> list_new_page_number =new ArrayList<>();
		if(list.size()>0){
			for(OrdersDto ordersDto1:list){
				if(ordersDto1!=null){
					 if(ordersDto1.getCreate_time()!=null){
					        ordersDto1.setCreate_orderstime(TimetoStringUtil.timeto(ordersDto1.getCreate_time()));
					        if(ordersDto1.getCommentState()==0){
								ordersDto1.setIscomment("未评论");
							}else if(ordersDto1.getCommentState()==1){
								ordersDto1.setIscomment("评论中");
							}else if(ordersDto1.getCommentState()==2){
								ordersDto1.setIscomment("已评论");
							}else if(ordersDto1.getCommentState()==null){
								ordersDto1.setIscomment("未处理");
							}
					  }    
				        if(ordersDto1.getBusiness()!=null){
				        	if(ordersDto1.getBusiness().getBusinesser()!=null){
				        		             if(groupDto!=null){
				        		            	 ////超级管理员或管理员
				 								 if(groupDto.getName().equals("超级管理员")|| groupDto.getName().equals("管理员")){
				 									 list_new.add(ordersDto1);
				 								 }
				 								 
				        					     //商家业务员
				        					     if(groupDto.getName().equals("商家业务员") 
				 										 && ordersDto1.getBusiness().getBusinesser().getUser()==null){
				 								 	 list_new.add(null);
				 								 }else if(groupDto.getName().equals("商家业务员")
				 										 && ordersDto1.getBusiness().getBusinesser().getUser()!=null){
				 									if(ordersDto1.getBusiness().getBusinesser().getUser().getGroup()!=null){
				 										   if(ordersDto1.getBusiness().getBusinesser().getUser().getGroup().getName()!=null){
				 											  if(ordersDto1.getBusiness().getBusinesser().getUser().getGroup().getName().equals(groupDto.getName())){
				 												  if(ordersDto1.getBusiness().getBusinesser().getUser().getName().equals(userDto.getName())){
				 													 list_new.add(ordersDto1);
				 												  }
				 												  
				 											  } 
				 										   }
				 									   }
				 								 }
				 							}   
				        					 
				                }
				        }
				    }
				 }
			 }
		
		ordersDto.getPage().setTotalNumber(list_new.size());	
		int start = (ordersDto.getPage().getCurrentPage()-1)*(ordersDto.getPage().getPageNumber());
		for(int i= start;i<start+ordersDto.getPage().getPageNumber();i++){
			if(i<list_new.size()){
				OrdersDto OrdersDto=list_new.get(i);
				if(OrdersDto!=null){
					list_new_page_number.add(OrdersDto);
				}
			}
		}
		model.addAttribute("user",userDto);
		model.addAttribute("list",list_new_page_number);
		model.addAttribute("searchParam", ordersDto);
		return "/content/orderList";
	}
	
	/**
	 * 查询
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/search" ,method=RequestMethod.POST)
	public String search(Model model,@RequestParam("currentPage") int currentPage, @RequestParam("phone") String phone,@RequestParam("user_name") String user_name,HttpServletRequest request) throws UnsupportedEncodingException {
		OrdersDto ordersDto = new OrdersDto();
		ordersDto.getPage().setCurrentPage(currentPage);
		UserDto userDto = new UserDto();
		userDto.setName(user_name);
		if(user_name!=null){
			userDto = userService.getGorudId_ByName(userDto);
		}
		GroupDto groupDto = null;
		if(userDto!=null){
			 groupDto = groupService.getById(userDto.getGroupId());
		}
		if(phone.length()>0){//手机号不为空 
			if(CommonUtil.isNumeric(phone) && phone.length()==11){//手机号是数字 也是11位数字
				Long memberId= memberService.getIdByPhone(Long.valueOf(phone));
			    if(memberId!=null){//有该会员，但是可能查询不出来
			    	ordersDto.setMemberId(memberId);
			    	ordersDto.setPhone(Long.parseLong(phone));
			    }else{//没有该会员
			    	ordersDto.getPage().setCurrentPage(1);
			    	ordersDto.getPage().setTotalPage(1);
			    	model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.phone_noEXISTS);
			    	model.addAttribute("searchParam", ordersDto);	
			    	model.addAttribute("user",userDto);
					return "/content/orderList";
			    }
			}else{//手机号不是数字或者不是11位数
				ordersDto.getPage().setCurrentPage(1);
		    	ordersDto.getPage().setTotalPage(1);
				model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.phone_nomember);	
				model.addAttribute("user",userDto);
				model.addAttribute("searchParam", ordersDto);	
				return "/content/orderList";
			}
		}
		List<OrdersDto> list = ordersService.search_Orders(ordersDto);
		List<OrdersDto> list_new =new ArrayList<>();
		List<OrdersDto> list_new_page_number =new ArrayList<>();
		if(list.size()>0){
			for(OrdersDto ordersDto1:list){
				if(ordersDto1!=null){
					 if(ordersDto1.getCreate_time()!=null){
					        ordersDto1.setCreate_orderstime(TimetoStringUtil.timeto(ordersDto1.getCreate_time()));
					        if(ordersDto1.getCommentState()==0){
								ordersDto1.setIscomment("未评论");
							}else if(ordersDto1.getCommentState()==1){
								ordersDto1.setIscomment("评论中");
							}else if(ordersDto1.getCommentState()==2){
								ordersDto1.setIscomment("已评论");
							}else if(ordersDto1.getCommentState()==null){
								ordersDto1.setIscomment("未处理");
							}
					     }    
				        if(ordersDto1.getBusiness()!=null){
				        	if(ordersDto1.getBusiness().getBusinesser()!=null){
				        					  if(groupDto!=null){
					        		            	 ////超级管理员或管理员
					 								 if(groupDto.getName().equals("超级管理员")|| groupDto.getName().equals("管理员")){
					 									 list_new.add(ordersDto1);
					 								 }
					 								 
					        					     //商家业务员
					        					     if(groupDto.getName().equals("商家业务员") 
					 										 && ordersDto1.getBusiness().getBusinesser().getUser()==null){
					 								 	 list_new.add(null);
					 								 }else if(groupDto.getName().equals("商家业务员")
					 										 && ordersDto1.getBusiness().getBusinesser().getUser()!=null){
					 									if(ordersDto1.getBusiness().getBusinesser().getUser().getGroup()!=null){
					 										   if(ordersDto1.getBusiness().getBusinesser().getUser().getGroup().getName()!=null){
					 											  if(ordersDto1.getBusiness().getBusinesser().getUser().getGroup().getName().equals(groupDto.getName())){
					 												  if(ordersDto1.getBusiness().getBusinesser().getUser().getName().equals(userDto.getName())){
					 													 list_new.add(ordersDto1);
					 												  }
					 												  
					 											  } 
					 										   }
					 									   }
					 								 }
					 							}   
				        				
				        }
				    }
				 }
			 }
		}
		ordersDto.getPage().setTotalNumber(list_new.size());	
		int start = (ordersDto.getPage().getCurrentPage()-1)*(ordersDto.getPage().getPageNumber());
		for(int i= start;i<start+ordersDto.getPage().getPageNumber();i++){
			if(i<list_new.size()){
				OrdersDto OrdersDto=list_new.get(i);
				if(OrdersDto!=null){
					list_new_page_number.add(OrdersDto);
				}
			}
		}
		
		model.addAttribute("user",userDto);
		model.addAttribute("list",list_new_page_number);
		model.addAttribute("searchParam", ordersDto);	
		return "/content/orderList";
	}
	
	
	
}