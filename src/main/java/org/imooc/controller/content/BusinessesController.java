package org.imooc.controller.content;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.imooc.bean.Collect_Business;
import org.imooc.bean.Orders;
import org.imooc.constant.DicTypeConst;
import org.imooc.constant.PageCodeEnum;
import org.imooc.dto.AdDto;
import org.imooc.dto.BusinessDto;
import org.imooc.dto.BusinesserDto;
import org.imooc.dto.GroupDto;
import org.imooc.dto.UserDto;
import org.imooc.service.BusinessService;
import org.imooc.service.DicService;
import org.imooc.service.GroupService;
import org.imooc.service.OrdersService;
import org.imooc.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/businesses")
public class BusinessesController {

	@Resource
	private DicService dicService;

	@Resource
	private BusinessService businessService;
	
	@Resource
	private OrdersService ordersService;
	
	@Resource
	private UserService userService;
	
	
	@Resource
	private GroupService  groupService;
	
	//定义全局变量
	private int currentPage_;//页码
		 
	private String title_;//标题  模糊查询
	/**
	 * 商品列表查询初始化
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String Init(Model model, BusinessDto dto,@RequestParam("name") String name) {
		UserDto userDto = new UserDto();
		userDto.setName(name);
		if(name!=null){
			userDto = userService.getGorudId_ByName(userDto);
		}
		GroupDto groupDto = null;
		if(userDto!=null){
			 groupDto = groupService.getById(userDto.getGroupId());
		}
		List<BusinessDto> businessDtolist =  businessService.search_Business(dto);
		List<BusinessDto> list_new =new ArrayList<>();
		List<BusinessDto> list_new_page_number =new ArrayList<>();
		if(businessDtolist.size()>0){
			for(BusinessDto businessDto1:businessDtolist){
				if(businessDto1!=null){
					if(businessDto1.getBusinesser()!=null){
							if(groupDto!=null){
								    //超级管理员或管理员
								   if(groupDto.getName().equals("超级管理员")|| groupDto.getName().equals("管理员")){
									   list_new.add(businessDto1);//如果登录进来的是超级管理员  就查询所有的商家
								   }
								   //商家业务员
								   if(businessDto1.getBusinesser().getUser()==null
									&& groupDto.getName().equals("商家业务员")){
									        list_new.add(null);
								   }else if(businessDto1.getBusinesser().getUser()!=null
											&& groupDto.getName().equals("商家业务员")){
									   if(businessDto1.getBusinesser().getUser().getGroup()!=null){
										   if(businessDto1.getBusinesser().getUser().getGroup().getName()!=null){
											  if(businessDto1.getBusinesser().getUser().getGroup().getName().equals(groupDto.getName())){
												  if(businessDto1.getBusinesser().getUser().getName().equals(userDto.getName())){
													  list_new.add(businessDto1);
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
		dto.getPage().setTotalNumber(list_new.size());	
		int start = (dto.getPage().getCurrentPage()-1)*(dto.getPage().getPageNumber());
		for(int i= start;i<start+dto.getPage().getPageNumber();i++){
			if(i<list_new.size()){
				BusinessDto businessDto1 = list_new.get(i);
				if(businessDto1!=null){
					list_new_page_number.add(businessDto1);
				}
			}
		}
		model.addAttribute("user", userDto);
		model.addAttribute("list", list_new_page_number);
		model.addAttribute("searchParam", dto);
		return "/content/businessList";
	}
	/**
	 *  商品查询  下一页     上一页
	 */
	
	
	
	@RequestMapping(value="/search")
	public String search(Model model,@RequestParam("currentPage") int currentPage ,
			@RequestParam("title") String title,
			@RequestParam("user_name") String user_name, 
			HttpServletRequest request) throws UnsupportedEncodingException {
		UserDto userDto = new UserDto();
		userDto.setName(user_name);
		if(user_name!=null){
			userDto = userService.getGorudId_ByName(userDto);
		}
		GroupDto groupDto = null;
		if(userDto!=null){
			 groupDto = groupService.getById(userDto.getGroupId());
		}
		BusinessDto dto = new BusinessDto();
		dto.getPage().setCurrentPage(currentPage);
		dto.setTitle(title);
		List<BusinessDto> businessDtolist =  businessService.search_Business(dto);
		List<BusinessDto> list_new =new ArrayList<>();
		List<BusinessDto> list_new_page_number =new ArrayList<>();
		if(businessDtolist.size()>0){
			for(BusinessDto businessDto1:businessDtolist){
				if(businessDto1!=null){
					if(businessDto1.getBusinesser()!=null){
							if(groupDto!=null){						
							   if(groupDto.getName().equals("超级管理员")|| groupDto.getName().equals("管理员")){
								   list_new.add(businessDto1);
							   }							
							   if(businessDto1.getBusinesser().getUser()==null
								&& groupDto.getName().equals("商家业务员")){
								        list_new.add(null);
							   }else if(businessDto1.getBusinesser().getUser()!=null
										&& groupDto.getName().equals("商家业务员")){
								   if(businessDto1.getBusinesser().getUser().getGroup()!=null){
									   if(businessDto1.getBusinesser().getUser().getGroup().getName()!=null){
										  if(businessDto1.getBusinesser().getUser().getGroup().getName().equals(groupDto.getName())){
											  if(businessDto1.getBusinesser().getUser().getName().equals(userDto.getName())){
												  list_new.add(businessDto1);
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
		dto.getPage().setTotalNumber(list_new.size());	
		int start = (dto.getPage().getCurrentPage()-1)*(dto.getPage().getPageNumber());
		for(int i= start;i<start+dto.getPage().getPageNumber();i++){
			if(i<list_new.size()){
				BusinessDto businessDto1 = list_new.get(i);
				if(businessDto1!=null){
					list_new_page_number.add(businessDto1);
				}
			}
		}
		model.addAttribute("user", userDto);
		model.addAttribute("list", list_new_page_number);
		model.addAttribute("searchParam", dto);
		return "/content/businessList";
	}
	/**
	 * 修改或者删除完自动跳到这个接口在按条件查询
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/delupdate", method = RequestMethod.GET)
	public String del_update(Model model,@RequestParam("user_name") String user_name) {  //修改 删除  都进行查询
		UserDto userDto = new UserDto();
		userDto.setName(user_name);
		if(user_name!=null){
			userDto = userService.getGorudId_ByName(userDto);
		}
		GroupDto groupDto = null;
		if(userDto!=null){
			 groupDto = groupService.getById(userDto.getGroupId());
		}
		BusinessDto dto = new BusinessDto();
		if(currentPage_>=1 || (title_!=null || title_!="")){
			dto.getPage().setCurrentPage(currentPage_);
			dto.setTitle(title_);
		}
		List<BusinessDto> businessDtolist =  businessService.search_Business(dto);
		List<BusinessDto> list_new =new ArrayList<>();
		List<BusinessDto> list_new_page_number =new ArrayList<>();
		if(businessDtolist.size()>0){
			for(BusinessDto businessDto1:businessDtolist){
				if(businessDto1!=null){
					if(businessDto1.getBusinesser()!=null){
							if(groupDto!=null){
							    //超级管理员或管理员
							   if(groupDto.getName().equals("超级管理员")|| groupDto.getName().equals("管理员")){
								   list_new.add(businessDto1);//如果登录进来的是超级管理员  就查询所有的商家
							   }
							   //商家业务员
							   if(businessDto1.getBusinesser().getUser()==null
								&& groupDto.getName().equals("商家业务员")){
								        list_new.add(null);
							   }else if(businessDto1.getBusinesser().getUser()!=null
										&& groupDto.getName().equals("商家业务员")){
								   if(businessDto1.getBusinesser().getUser().getGroup()!=null){
									   if(businessDto1.getBusinesser().getUser().getGroup().getName()!=null){
										  if(businessDto1.getBusinesser().getUser().getGroup().getName().equals(groupDto.getName())){
											  if(businessDto1.getBusinesser().getUser().getName().equals(userDto.getName())){
												  list_new.add(businessDto1);
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
		dto.getPage().setTotalNumber(list_new.size());	
		int start = (dto.getPage().getCurrentPage()-1)*(dto.getPage().getPageNumber());
		for(int i= start;i<start+dto.getPage().getPageNumber();i++){
			if(i<list_new.size()){
				BusinessDto businessDto1 = list_new.get(i);
				if(businessDto1!=null){
					list_new_page_number.add(businessDto1);
				}
			}
		}
		model.addAttribute("user", userDto);
		model.addAttribute("list", list_new_page_number);
		model.addAttribute("searchParam", dto);
		return "/content/businessList";
	}
	/**
	 * 删除商品
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String remove(@PathVariable("id") Long id) {
		return "redirect:/businesses";
	}
	/**
	 *  商品新增页初始化
	 */
	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public String addInit(Model model,@RequestParam("currentPage") int currentPage,@RequestParam("title") String title) {
		BusinessDto dto = new BusinessDto();
		dto.getPage().setCurrentPage(Integer.valueOf(currentPage));
		dto.setTitle(title);
		model.addAttribute("cityList", dicService.getListByType(DicTypeConst.CITY));
		model.addAttribute("categoryList", dicService.getListByType(DicTypeConst.CATEGORY));
		model.addAttribute("searchParam", dto);
		return "/content/businessAdd";
	}
	/**
	 * 返回新增 修改返回时查询
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/gobackser", method = RequestMethod.GET)
	public String gobackser(RedirectAttributes attr,Model model,@RequestParam("currentPage") int currentPage,HttpServletRequest request,@RequestParam("title") String title,@RequestParam("user_name") String user_name) throws UnsupportedEncodingException{	
		String str = search(model,currentPage ,title, user_name,request);
		return str;
	}
	/**
	 * 商品修改页初始化
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/modifyInit")
	public String modifyInit(Model model, @RequestParam("id") Long id,@RequestParam("currentPage") int currentPage ,@RequestParam("title") String title,@RequestParam("user_name") String user_name) throws UnsupportedEncodingException {
		UserDto userDto = new UserDto();
		userDto.setName(user_name);
		BusinessDto dto =  new BusinessDto();
		dto.getPage().setCurrentPage(currentPage);//当前页码
		dto.setTitle(title);
		model.addAttribute("user", userDto);
		model.addAttribute("searchParam", dto);
		model.addAttribute("cityList", dicService.getListByType(DicTypeConst.CITY));
		model.addAttribute("categoryList", dicService.getListByType(DicTypeConst.CATEGORY));
		model.addAttribute("modifyObj", businessService.getById(id));
		return "/content/businessModify";
	}
	/**
	 * 商品修改
	 * @throws UnsupportedEncodingException 
	 */	
	//JSPs only permit GET POST or HEAD  该错误  该请求像tomcat容器请求的url  是rstful风格  映射到了/content/businessModify这个jsp接口， 但是jsp接口默认只支持post和get起请求的动作，在给jsp页面加入的  isErrorPage="true"	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String modify(RedirectAttributes attr,@RequestParam("user_name") String user_name, Model model,@PathVariable("id") Long id, BusinessDto dto,@RequestParam("currentPage") int currentPage,@RequestParam("title_like") String title_like,HttpServletRequest request) throws UnsupportedEncodingException {
		    UserDto userDto = new UserDto();
		    userDto.setName(user_name);
		    currentPage_ = currentPage;//页码赋值
		    title_ = title_like;//模糊查询标题
		    BusinessDto dto1 = new BusinessDto();
		    dto1.getPage().setCurrentPage(currentPage);
		    dto1.setTitle(title_like);
		    model.addAttribute("user", userDto);
		    model.addAttribute("modifyObj", dto);
		    model.addAttribute("searchParam", dto1);
		    model.addAttribute("cityList", dicService.getListByType(DicTypeConst.CITY));
			model.addAttribute("categoryList", dicService.getListByType(DicTypeConst.CATEGORY));
		  if(dto.getPrice()>10000){
			    model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.pricevalue);
			    return "/content/businessModify";
		  }
		 if (businessService.modify(dto)) {
			attr.addAttribute(PageCodeEnum.KEY, PageCodeEnum.MODIFY_SUCCESS.getMsg());
			attr.addAttribute("user_name", user_name);
			return "redirect:/businesses/del_update";
		} else {//失败就数据回显
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.MODIFY_FAIL);
		}
		return "/content/businessModify";
	}

	/**
	 * 删除操作  根据id删除
	 * @param model
	 * @param adDto
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/remove")
	public String remove(Model model,RedirectAttributes attr, BusinessDto dto,@RequestParam("currentPage") int currentPage,@RequestParam("title") String title, HttpServletRequest request,@RequestParam("user_name") String user_name) throws UnsupportedEncodingException {
		 UserDto userDto = new UserDto();
		 userDto.setName(user_name);
		//判断该商品下是否有该订单
		currentPage_ = currentPage;
		title_ = title;
		List<Orders> orderslist = ordersService.ishave(dto.getId());
		List<Collect_Business> collect_Businesslist = businessService.ishave(dto.getId());
		if(orderslist.size()>0 || collect_Businesslist.size()>0){
			attr.addAttribute(PageCodeEnum.KEY, PageCodeEnum.REMOVE_no.getMsg());
			attr.addAttribute("user_name", user_name);
			return  "redirect:/businesses/del_update";
		}
		if(businessService.remove(dto.getId())){ //删除成功  就返回true
			model.addAttribute("searchParam", dto);
			attr.addAttribute(PageCodeEnum.KEY, PageCodeEnum.REMOVE_SUCCESS.getMsg());
		}else{//false
			attr.addAttribute(PageCodeEnum.KEY, PageCodeEnum.REMOVE_FAIL.getMsg());
		}
		attr.addAttribute("user_name", user_name);
		return  "redirect:/businesses/del_update";
	}
	
	@RequestMapping(value="/del_update",method=RequestMethod.GET)
	public String del(Model model,@RequestParam("pageCode") String pageCode,@RequestParam("user_name") String user_name) {  //修改 删除  都进行查询
		UserDto userDto = new UserDto();
		userDto.setName(user_name);
		PageCodeEnum.update_msg.setMsg(pageCode);
		model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.update_msg);
		model.addAttribute("user", userDto);
		String str = del_update(model,user_name);
		return str;
	}
	
	
	
	
}