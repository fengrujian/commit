package org.imooc.controller.content;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.imooc.bean.Member;
import org.imooc.bean.Orders;
import org.imooc.constant.PageCodeEnum;
import org.imooc.dto.BusinessDto;
import org.imooc.dto.CommentDto;
import org.imooc.dto.GroupDto;
import org.imooc.dto.OrdersDto;
import org.imooc.dto.UserDto;
import org.imooc.dto.CommentDto;
import org.imooc.service.CommentService;
import org.imooc.service.GroupService;
import org.imooc.service.MemberService;
import org.imooc.service.UserService;
import org.imooc.util.CommonUtil;
import org.imooc.util.TimetoStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/comments")
public class CommentsController {
	
	@Autowired
	private CommentService commentService;
	
	@Resource
	private MemberService memberService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private GroupService  groupService;
	
	
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
		CommentDto commentDto = new CommentDto();
		List<CommentDto> list = commentService.search_comment(commentDto);
		List<CommentDto> list_new =new ArrayList<>();
		List<CommentDto> list_new_page_number =new ArrayList<>();
		
		if(list.size()>0){
			for(CommentDto commentDto1:list){
				if(commentDto1!=null){
				   commentDto1.setCreate_commenttime(TimetoStringUtil.timeto(commentDto1.getCreateTime()));
				if(commentDto1.getOrders()!=null){
				if(commentDto1.getOrders().getBusiness()!=null){
		        	if(commentDto1.getOrders().getBusiness().getBusinesser()!=null){
		        					  
        		             if(groupDto!=null){
        		            	//超级管理员或管理员
 								 if(groupDto.getName().equals("超级管理员")||groupDto.getName().equals("管理员")){
 									 list_new.add(commentDto1);
 								 }
 								 //商家业务员
 								 if(groupDto.getName().equals("商家业务员") 
 										 && commentDto1.getOrders().getBusiness().getBusinesser().getUser()==null){
 								 	 list_new.add(null);
 								 }else if(groupDto.getName().equals("商家业务员")
 										 && commentDto1.getOrders().getBusiness().getBusinesser().getUser()!=null){
 									if(commentDto1.getOrders().getBusiness().getBusinesser().getUser().getGroup()!=null){
										   if(commentDto1.getOrders().getBusiness().getBusinesser().getUser().getGroup().getName()!=null){
											  if(commentDto1.getOrders().getBusiness().getBusinesser().getUser().getGroup().getName().equals(groupDto.getName())){
												if(commentDto1.getOrders().getBusiness().getBusinesser().getUser().getName().equals(userDto.getName())){
													list_new.add(commentDto1);
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
}
		commentDto.getPage().setTotalNumber(list_new.size());	
		int start = (commentDto.getPage().getCurrentPage()-1)*(commentDto.getPage().getPageNumber());
		for(int i= start;i<start+commentDto.getPage().getPageNumber();i++){
			if(i<list_new.size()){
				CommentDto commentDto1=list_new.get(i);
				if(commentDto1!=null){
					list_new_page_number.add(commentDto1);
				}
			}
		}
		model.addAttribute("user",userDto);
		model.addAttribute("list",list_new_page_number);
		model.addAttribute("searchParam", commentDto);	
		return "/content/commentList";
	}
	
	/**
	 * 查询
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/search" ,method=RequestMethod.POST)
	public String search(Model model,@RequestParam("currentPage") int currentPage ,@RequestParam("user_name") String user_name,@RequestParam("phone") String phone, HttpServletRequest request) throws UnsupportedEncodingException {
		//新加的
		UserDto userDto = new UserDto();
		userDto.setName(user_name);
		if(user_name!=null){
			userDto = userService.getGorudId_ByName(userDto);
		}
		GroupDto groupDto = null;
		if(userDto!=null){
			 groupDto = groupService.getById(userDto.getGroupId());
		}
	   //新加的结束
		CommentDto commentDto = new CommentDto();
		commentDto.getPage().setCurrentPage(currentPage);
		if(phone.length()>0){//手机号不为空 
			if(CommonUtil.isNumeric(phone) && phone.length()==11){//手机号是数字 也是11位数字
				Long memberId= memberService.getIdByPhone(Long.valueOf(phone));
			    if(memberId!=null){//有该会员，但是可能查询不出来
			    	Orders orders = new Orders();
			    	Member member = new Member();
			    	commentDto.setOrders(orders);
			    	orders.setMember(member);
			    	commentDto.getOrders().setMemberId(memberId);//用户模糊查询
			    	commentDto.getOrders().getMember().setPhone(Long.parseLong(phone));
			    }else{//没有该会员
			    	commentDto.getPage().setCurrentPage(1);
					commentDto.getPage().setTotalPage(1);
					model.addAttribute("user",userDto);
			    	model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.phone_noEXISTS);
			    	model.addAttribute("searchParam", commentDto);	
			    	return "/content/commentList";
			    }
			}else{//手机号不是数字或者不是11位数
				commentDto.getPage().setCurrentPage(1);
				commentDto.getPage().setTotalPage(1);
				model.addAttribute("user",userDto);
				model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.phone_nomember);	
				model.addAttribute("searchParam", commentDto);	
				return "/content/commentList";
			}
		}
		List<CommentDto> list = commentService.search_comment(commentDto);
		List<CommentDto> list_new =new ArrayList<>();
		List<CommentDto> list_new_page_number =new ArrayList<>();
		if(list.size()>0){
			for(CommentDto commentDto1:list){
				if(commentDto1!=null){
				   commentDto1.setCreate_commenttime(TimetoStringUtil.timeto(commentDto1.getCreateTime()));
				if(commentDto1.getOrders()!=null){
				if(commentDto1.getOrders().getBusiness()!=null){
		        	if(commentDto1.getOrders().getBusiness().getBusinesser()!=null){
		        					  if(groupDto!=null){
		          		            	//超级管理员或管理员
		  								 if(groupDto.getName().equals("超级管理员")||groupDto.getName().equals("管理员")){
		  									 list_new.add(commentDto1);
		  								 }
		  								 //商家业务员
		  								 if(groupDto.getName().equals("商家业务员") 
		  										 && commentDto1.getOrders().getBusiness().getBusinesser().getUser()==null){
		  								 	 list_new.add(null);
		  								 }else if(groupDto.getName().equals("商家业务员")
		  										 && commentDto1.getOrders().getBusiness().getBusinesser().getUser()!=null){
		  									if(commentDto1.getOrders().getBusiness().getBusinesser().getUser().getGroup()!=null){
		 										   if(commentDto1.getOrders().getBusiness().getBusinesser().getUser().getGroup().getName()!=null){
		 											  if(commentDto1.getOrders().getBusiness().getBusinesser().getUser().getGroup().getName().equals(groupDto.getName())){
		 												if(commentDto1.getOrders().getBusiness().getBusinesser().getUser().getName().equals(userDto.getName())){
		 													list_new.add(commentDto1);
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
}
		commentDto.getPage().setTotalNumber(list_new.size());	
		int start = (commentDto.getPage().getCurrentPage()-1)*(commentDto.getPage().getPageNumber());
		for(int i= start;i<start+commentDto.getPage().getPageNumber();i++){
			if(i<list_new.size()){
				CommentDto commentDto1=list_new.get(i);
				if(commentDto1!=null){
					list_new_page_number.add(commentDto1);
				}
			}
		}
		model.addAttribute("user",userDto);
		model.addAttribute("list",list_new_page_number);
		model.addAttribute("searchParam", commentDto);	
		return "/content/commentList";
	}
	
	
}
