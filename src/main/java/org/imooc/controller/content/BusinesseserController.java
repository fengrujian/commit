package org.imooc.controller.content;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Repeatable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.DefaultEditorKit.CopyAction;

import org.imooc.bean.Business;
import org.imooc.bean.Businesser;
import org.imooc.bean.User;
import org.imooc.constant.DicTypeConst;
import org.imooc.constant.PageCodeEnum;
import org.imooc.dto.AdDto;
import org.imooc.dto.BusinessDto;
import org.imooc.dto.BusinesserDto;
import org.imooc.dto.GroupDto;
import org.imooc.dto.OrdersDto;
import org.imooc.dto.UserDto;
import org.imooc.service.BusinessService;
import org.imooc.service.BusinesserService;
import org.imooc.service.DicService;
import org.imooc.service.GroupService;
import org.imooc.service.UserService;
import org.imooc.util.CommonUtil;
import org.imooc.util.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/businesseser")
public class BusinesseserController {
	
	@Resource
	private BusinessService businessService;
	
	@Resource
	private BusinesserService businesserService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private DicService dicService;
	
	@Resource
	private GroupService  groupService;
	
	//定义全局变量
	private int currentPage_;//页码
	 
	private String name_;//商家名称 模糊查询
	
	private String user_name_;
	/**
	 * 初始化浏览商家列表
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String init(Model model,@RequestParam("name") String name) {
		UserDto userDto = new UserDto();
		userDto.setName(name);
		if(name!=null){
			userDto = userService.getGorudId_ByName(userDto);
		}
		GroupDto groupDto = null;
		if(userDto!=null){
			 groupDto = groupService.getById(userDto.getGroupId());
		}
		BusinesserDto businesserDto = new BusinesserDto();
		List<BusinesserDto> list = businesserService.search_Businesser(businesserDto);
		List<BusinesserDto> list_new =new ArrayList<>();
		List<BusinesserDto> list_new_page_number =new ArrayList<>();
		if(list.size()>0){
			for(BusinesserDto businesserDto1:list){
				if(businesserDto1!=null){
					if(groupDto!=null){
						  
						   if(groupDto.getName().equals("超级管理员")|| groupDto.getName().equals("管理员")){
							   list_new.add(businesserDto1);
						   }						
						   if(businesserDto1.getUser()==null
							&& groupDto.getName().equals("商家业务员")){
							        list_new.add(null);
						   }else if(businesserDto1.getUser()!=null
									&& groupDto.getName().equals("商家业务员")){
							   if(businesserDto1.getUser().getGroup()!=null){
								   if(businesserDto1.getUser().getGroup().getName()!=null){
									  if(businesserDto1.getUser().getGroup().getName().equals(groupDto.getName())){
										  if(businesserDto1.getUser().getName().equals(userDto.getName())){
											  list_new.add(businesserDto1);
										  }
									  } 
								   }
							   }
							   
						   }
					}
				}
			}
		}
		businesserDto.getPage().setTotalNumber(list_new.size());	
		int start = (businesserDto.getPage().getCurrentPage()-1)*(businesserDto.getPage().getPageNumber());
		for(int i= start;i<start+businesserDto.getPage().getPageNumber();i++){
			if(i<list_new.size()){
				BusinesserDto businesserDto1 = list_new.get(i);
				if(businesserDto1!=null){
					list_new_page_number.add(businesserDto1);
				}
			}
		}
		model.addAttribute("user",userDto);
		model.addAttribute("list",list_new_page_number);
		model.addAttribute("searchParam", businesserDto);	
		return "/content/businesserList";
	}
	
	/**
	 * 查询
	 * @throws UnsupportedEncodingException 
	 */
	
	
	@RequestMapping(value="/search" ,method=RequestMethod.POST)
	public String search(Model model,@RequestParam("currentPage") int currentPage,
			@RequestParam("user_name")String user_name ,
			@RequestParam("name") String name,HttpServletRequest request)
					throws UnsupportedEncodingException {		
		UserDto userDto = new UserDto();
		userDto.setName(user_name);
		if(user_name!=null){
			userDto = userService.getGorudId_ByName(userDto);
		}
		GroupDto groupDto = null;
		if(userDto!=null){
			 groupDto = groupService.getById(userDto.getGroupId());
		}
		BusinesserDto businesserDto = new BusinesserDto();
		businesserDto.getPage().setCurrentPage(currentPage);
		businesserDto.setName(name);
		List<BusinesserDto> list = businesserService.search_Businesser(businesserDto);
		List<BusinesserDto> list_new =new ArrayList<>();
		List<BusinesserDto> list_new_page_number =new ArrayList<>();
		if(list.size()>0){
			for(BusinesserDto businesserDto1:list){
				if(businesserDto1!=null){
							if(groupDto!=null){
								  
								   if(groupDto.getName().equals("超级管理员")|| groupDto.getName().equals("管理员")){
									   list_new.add(businesserDto1);
								   }								  
								   if(businesserDto1.getUser()==null
									&& groupDto.getName().equals("商家业务员")){
									        list_new.add(null);
								   }else if(businesserDto1.getUser()!=null
											&& groupDto.getName().equals("商家业务员")){
									   if(businesserDto1.getUser().getGroup()!=null){
										   if(businesserDto1.getUser().getGroup().getName()!=null){
											  if(businesserDto1.getUser().getGroup().getName().equals(groupDto.getName())){
												  if(businesserDto1.getUser().getName().equals(userDto.getName())){
													  list_new.add(businesserDto1);
												  }
											  } 
										   }
									   }
									   
								   }
							}
					
				}
			}
		}
		businesserDto.getPage().setTotalNumber(list_new.size());	
		int start = (businesserDto.getPage().getCurrentPage()-1)*(businesserDto.getPage().getPageNumber());
		for(int i= start;i<start+businesserDto.getPage().getPageNumber();i++){
			if(i<list_new.size()){
				BusinesserDto businesserDto1 = list_new.get(i);
				if(businesserDto1!=null){
					list_new_page_number.add(businesserDto1);
				}
			}
		}
		model.addAttribute("user",userDto);
		model.addAttribute("list",list_new_page_number);
		model.addAttribute("searchParam", businesserDto);	
		return "/content/businesserList";
	}
    
	@RequestMapping("/addInit")
	public String addInit(Model model,HttpServletRequest request,@RequestParam("currentPage") int currentPage,@RequestParam("name") String name,@RequestParam("user_name") String user_name) throws UnsupportedEncodingException {
	    BusinesserDto businesserDto = new BusinesserDto();
	    businesserDto.getPage().setCurrentPage(Integer.valueOf(currentPage));
	    businesserDto.setName(name);//商家名
	    
	    UserDto userDto = new UserDto();
	    userDto.setName(user_name);//登录的用户名
	    
//	    UserDto userDto1 = userService.getUserandGroup(userDto);
	    
	    model.addAttribute("user", userDto);
	  
	    
	    model.addAttribute("searchParam", businesserDto);
		return "/content/businesserAdd";
	}
	
	
	
	
	
	/**
	 *   返回新增 修改返回时查询
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/gobackser", method = RequestMethod.GET)
	public String gobackser(Model model,@RequestParam("currentPage") int currentPage,HttpServletRequest request,@RequestParam("like_name") String like_name,@RequestParam("user_name") String user_name){
        //商家名称
		UserDto userDto = new UserDto();
		userDto.setName(user_name);
		if(user_name!=null){
			userDto = userService.getGorudId_ByName(userDto);
		}
		GroupDto groupDto = null;
		if(userDto!=null){
			 groupDto = groupService.getById(userDto.getGroupId());
		}
		BusinesserDto businesserDto = new BusinesserDto();
		businesserDto.getPage().setCurrentPage(currentPage);//当前页
		businesserDto.setName(like_name);//商家名称
		List<BusinesserDto> list = businesserService.search_Businesser(businesserDto);
		List<BusinesserDto> list_new =new ArrayList<>();
		List<BusinesserDto> list_new_page_number =new ArrayList<>();
		if(list.size()>0){
			for(BusinesserDto businesserDto1:list){
				if(businesserDto1!=null){
					
							if(groupDto!=null){
								   //超级管理员或管理员
								   if(groupDto.getName().equals("超级管理员")|| groupDto.getName().equals("管理员")){
									   list_new.add(businesserDto1);//如果登录进来的是超级管理员  就查询所有的商家
								   }
								   //商家业务员
								   if(businesserDto1.getUser()==null
									&& groupDto.getName().equals("商家业务员")){
									        list_new.add(null);
								   }else if(businesserDto1.getUser()!=null
											&& groupDto.getName().equals("商家业务员")){
									   if(businesserDto1.getUser().getGroup()!=null){
										   if(businesserDto1.getUser().getGroup().getName()!=null){
											  if(businesserDto1.getUser().getGroup().getName().equals(groupDto.getName())){
												  if(businesserDto1.getUser().getName().equals(userDto.getName())){
													  list_new.add(businesserDto1);
												  }
											  } 
										   }
									   }
									   
								   }
							}
						
					
				}
			}
		}
		businesserDto.getPage().setTotalNumber(list_new.size());	
		int start = (businesserDto.getPage().getCurrentPage()-1)*(businesserDto.getPage().getPageNumber());
		for(int i= start;i<start+businesserDto.getPage().getPageNumber();i++){
			if(i<list_new.size()){
				BusinesserDto businesserDto1 = list_new.get(i);
				if(businesserDto1!=null){
					list_new_page_number.add(businesserDto1);
				}
			}
		}
		model.addAttribute("user",userDto);
		model.addAttribute("list",list_new_page_number);
		model.addAttribute("searchParam", businesserDto);	
		return "/content/businesserList";
	}
	/**
	 * 添加商家
	 * @param model
	 * @param userDto
	 * @param currentPage
	 * @param request
	 * @param like_name
	 * @param user_name
	 * @param businesser_name
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model,
			@RequestParam("currentPage") int currentPage,
			HttpServletRequest request,
			@RequestParam("like_name") String like_name,
			@RequestParam("user_name") String user_name,
	        @RequestParam("businesser_name") String businesser_name) {
		    BusinesserDto businesserDto = new BusinesserDto();
		    businesserDto.getPage().setCurrentPage(currentPage);
		    businesserDto.setName(like_name);
		    UserDto userDto1 = new UserDto();
		    userDto1.setName(user_name);
		    model.addAttribute("user",userDto1);
		    model.addAttribute("searchParam", businesserDto);
		  if(businesser_name.length()>0){				  
					   //添加商家
					   Businesser businesser = new Businesser();
					   businesser.setName(businesser_name);				
					   businesser.setNumber((int)(Math.random()*200+1));//输出[1,200)间的随机数  
					   int num=businesserService.insertbusinesser(businesser);
					   if(num==1){
						   model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.addbusinesser_SUCCESS);
					   }else{
						   model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_FAIL);
					   }
		  }else{
			  model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.businesser_null);
		  }
		return "/content/businesserAdd";
	}
	
	//根据商家的id
	@RequestMapping("/remove")
	public String remove(Model model,RedirectAttributes attr,@RequestParam("id") int id,@RequestParam("currentPage") int currentPage,@RequestParam("name") String name,@RequestParam("user_name") String user_name,HttpServletRequest request) throws UnsupportedEncodingException {
		 currentPage_ = currentPage;
		 name_ = name;
		 user_name_= user_name;
		if(String.valueOf(id).length()>0){
			 Business  business = new Business();
			 business.setBusinesserId(id);
			 UserDto userDto = new UserDto();
			 userDto.setBusinesserId((long)id);
			 List<Business>  business1 = businessService.ishave(business);
			 User user = userService.getUserByBusinesserId(userDto);
			
				 if(business1.size()>0 || user!=null){//商家底下有商品或商家业务员,不能删除该商家
					 attr.addAttribute(PageCodeEnum.KEY, PageCodeEnum.REMOVE_not.getMsg());
					 return  "redirect:/businesseser/del";
				 }else{//商家下没商品或商家业务员,可以删除
					 BusinesserDto businesserDto = new BusinesserDto();
					 businesserDto.setId(id);
					 Boolean fla= businesserService.delbusinesser(businesserDto);
					 if(fla){
						 attr.addAttribute(PageCodeEnum.KEY, PageCodeEnum.REMOVE_SUCCESS.getMsg());
						 return  "redirect:/businesseser/del";
					 }else{
						 attr.addAttribute(PageCodeEnum.KEY, PageCodeEnum.REMOVE_FAIL.getMsg());					
						 return  "redirect:/businesseser/del";
					 }
				 }
			
				 
		 }else{
			 attr.addAttribute(PageCodeEnum.KEY, PageCodeEnum.businesserid_null.getMsg());
			 return  "redirect:/businesseser/del";
		 }
		
		
		
	}
	
	//删除时自动调用查询方法
	@RequestMapping(value="/del",method=RequestMethod.GET)
	public String del(ModelMap map,Model model,HttpServletRequest request,@RequestParam("pageCode") String pageCode) throws UnsupportedEncodingException {  //修改 删除  初始化 都进行查询
		PageCodeEnum.update_msg.setMsg(pageCode);
		model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.update_msg);
		String str = search(model, currentPage_, user_name_, name_, request);
		return str;
	}
	
	/**
	 * 修改商家页面初始化
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/modifyInit")
	public String modifyInit(Model model, @RequestParam("id") int id,
			@RequestParam("currentPage") int currentPage,
			@RequestParam("user_name") String user_name,
			@RequestParam("name") String name) throws UnsupportedEncodingException {		     
		    BusinesserDto  businesserDto = new BusinesserDto();
		    businesserDto.getPage().setCurrentPage(currentPage);//当前页码
			businesserDto.setName(name);
			UserDto userDto = new UserDto();
			userDto.setName(user_name);
			model.addAttribute("user", userDto);
		    model.addAttribute("searchParam", businesserDto);
		    BusinesserDto  BusinesserDto =  businesserService.getById(id);
		    model.addAttribute("modifyObj", BusinesserDto);
		    return "/content/businesserModify";
	}
	
	//修改保存商家
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public String modify(RedirectAttributes attr,Model model, @RequestParam("id") int id,
			@RequestParam("currentPage") int currentPage,
			@RequestParam("user_name") String user_name,
			@RequestParam("like_name") String like_name,
			@RequestParam("name") String name){
		    currentPage_ = currentPage;
		    name_ = like_name;
		    user_name_= user_name;
			if(name.length()>0 && String.valueOf(id).length()>0){
				boolean b =businesserService.updatebusinesser(name,id);
				if(b){//修改成功
					 attr.addAttribute(PageCodeEnum.KEY, PageCodeEnum.MODIFY_SUCCESS.getMsg());
					 return  "redirect:/businesseser/del";
				}else{//修改失败
					 BusinesserDto businesserDto = new BusinesserDto();
					 businesserDto.getPage().setCurrentPage(currentPage);
					 businesserDto.setName(like_name);
					 UserDto userDto = new UserDto();
					 userDto.setName(user_name);
					 BusinesserDto businesserDto1 = new BusinesserDto();
					 businesserDto1.setName(name);
					 model.addAttribute("user", userDto);
					 model.addAttribute("searchParam", businesserDto);
					 model.addAttribute("modifyObj", businesserDto1);
					 model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.MODIFY_FAIL);
					 return "/content/businesserModify";
				}
			}
		    return null;
	}
	/**
	 * 商品新增页初始化
	 */
	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public String addInit(Model model,@RequestParam("id") int id, @RequestParam("currentPage") int currentPage,@RequestParam("name") String name,@RequestParam("user_name") String user_name) {
		BusinesserDto dto = new BusinesserDto();
		dto.getPage().setCurrentPage(Integer.valueOf(currentPage));
		dto.setName(name);
		dto.setId(id);
		UserDto userDto = new UserDto();
		userDto.setName(user_name);
		model.addAttribute("cityList", dicService.getListByType(DicTypeConst.CITY));
		model.addAttribute("categoryList", dicService.getListByType(DicTypeConst.CATEGORY));
		model.addAttribute("searchParam", dto);
		model.addAttribute("user", userDto);
		return "/content/businessAdd";
	}
	/**
	 *  在商家下新增  商品
	 */
	@RequestMapping(value="/addbusiness" ,method=RequestMethod.POST)
	public String add(BusinessDto dto,Model model,@RequestParam("currentPage") int currentPage,@RequestParam("like_name") String like_name,@RequestParam("user_name") String user_name) {
		BusinesserDto businesserDto = new BusinesserDto();
		businesserDto.getPage().setCurrentPage(currentPage);
		businesserDto.setName(like_name);
		businesserDto.setId(dto.getBusinesserId());
		model.addAttribute("searchParam", businesserDto);
		UserDto userDto = new UserDto();
		userDto.setName(user_name);
		model.addAttribute("user", userDto);
		model.addAttribute("cityList", dicService.getListByType(DicTypeConst.CITY));
		model.addAttribute("categoryList", dicService.getListByType(DicTypeConst.CATEGORY));
		if(dto.getPrice()>10000){
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.pricevalue);
			return "/content/businessAdd";
		}
		if(businessService.add(dto)) {
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_SUCCESS);
		} else {	
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_FAIL);		
		}
		return "/content/businessAdd";
	}
	
	@RequestMapping("/addUserInit")
	public String addUserInit(Model model,HttpServletRequest request,@RequestParam("id") Long businesserId, @RequestParam("currentPage") int currentPage,@RequestParam("name") String name,@RequestParam("user_name") String user_name) throws UnsupportedEncodingException {
	    BusinesserDto businesserDto = new BusinesserDto();
	    businesserDto.getPage().setCurrentPage(Integer.valueOf(currentPage));
	    businesserDto.setName(name);//商家名
	    UserDto userDto = new UserDto();
	    userDto.setName(user_name);//登录的用户名
	    userDto.setBusinesserId(businesserId);
	    model.addAttribute("user", userDto);
        List<GroupDto> groupDtolist = groupService.getAll();
        List<GroupDto> GroupDtolist_new = new ArrayList<>();
        for(GroupDto groupDto:groupDtolist){
        	if(groupDto.getName().equals("商家业务员")){
        		GroupDtolist_new.add(groupDto);
        	}
        }
        if(groupDtolist.size()>0){
        	model.addAttribute("groupList", GroupDtolist_new);
        }
	    model.addAttribute("searchParam", businesserDto);
		return "/content/userAdd";
	}
	
	@RequestMapping("/adduser")
	public String adduser(Model model, UserDto userDto,@RequestParam("currentPage") int currentPage
			,HttpServletRequest request,
			@RequestParam("like_name") String like_name,@RequestParam("user_name") String user_name) {
		    BusinesserDto businesserDto = new BusinesserDto();
		    businesserDto.getPage().setCurrentPage(currentPage);
		    businesserDto.setName(like_name);
		    UserDto userDto1 = new UserDto();
		    userDto1.setName(user_name);
		    List<GroupDto> groupDtolist = groupService.getAll();
	        if(groupDtolist.size()>0){
	        	model.addAttribute("groupList", groupDtolist);
	        }
	        model.addAttribute("user", userDto1);
	        model.addAttribute("searchParam", businesserDto);
	        model.addAttribute("userDto", userDto);
		    if(userDto!=null){
		    	if(userDto.getName()!="" 
		    	&& userDto.getChName()!=""
		    	&& userDto.getPassword()!=""
		    	&& userDto.getBusinesserId().toString().length()>0
		    	&& userDto.getGroupId().toString().length()>0){
		    		if(CommonUtil.strIsChinese(userDto.getChName())){//要为中文名
			    		if(CommonUtil.strIsEnglish(userDto.getName())){//要为英文名
			    			UserDto user= userService.getUserByBusinesserId(userDto);
			    			if(user!=null){
			    				model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.businesser_haveuser);
			    			}else{
			    				int num=userService.insertUser(userDto);
					    		if(num==1){
						    		model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_SUCCESS);
					    		}else{
					    			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_FAIL);
					    		}
			    			}
			    		}else{
			    			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.user_isenlish);
			    		}
		    		}else{
		    			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.user_ischiess);
		    		}
		    	}else{
		    		model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.user_null);
		    	}
		    }
		    return "/content/userAdd";  
	}
	
	
	
}