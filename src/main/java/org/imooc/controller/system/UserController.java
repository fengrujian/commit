package org.imooc.controller.system;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.imooc.constant.PageCodeEnum;
import org.imooc.dto.MemberDto;
import org.imooc.dto.OrdersDto;
import org.imooc.dto.PageCodeDto;
import org.imooc.dto.UserDto;
import org.imooc.service.UserService;
import org.imooc.util.CommonUtil;
import org.imooc.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *  用户相关
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	private int currentPage_;//页码
	 
	private String name_;//名称 模糊查询
	
	//用户管理
	@RequestMapping(value="/init",method = RequestMethod.GET)
	public String init(Model model) { //修改 删除  初始化 都进行查询
		UserDto userDto = new UserDto();
		model.addAttribute("list", userService.searchByPage(userDto));
		model.addAttribute("searchParam", userDto);
		return "/content/userList";
	}
	/**
	 * 查询
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/search" ,method=RequestMethod.POST)
	public String search(Model model,@RequestParam("currentPage") int currentPage,@RequestParam("name") String name, HttpServletRequest request) throws UnsupportedEncodingException {
		UserDto userDto = new UserDto();
		userDto.getPage().setCurrentPage(currentPage);
		userDto.setName(name);
		model.addAttribute("list", userService.searchByPage(userDto));
		model.addAttribute("searchParam", userDto);
		return "/content/userList";
	}
	
	/**
	 * 
	 * 修改页面初始化
	 * @throws UnsupportedEncodingException
	 *  
	 */
	@RequestMapping("/modifyInit")
	public String modifyInit(Model model, @RequestParam("id") Long id,
			@RequestParam("currentPage") int currentPage,@RequestParam("name") String name, HttpServletRequest request) throws UnsupportedEncodingException {		     
		    UserDto userDto = new UserDto();
		    userDto.getPage().setCurrentPage(currentPage);//当前页码
		    userDto.setName(name);
		    model.addAttribute("searchParam", userDto);
		    model.addAttribute("modifyObj", userService.getById(id));
		    return "/content/userModify";
	}
	/**
	 * 修改
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/modify")
	public String modify(Model model,RedirectAttributes attr, UserDto userDto,@RequestParam("currentPage") int currentPage,
			HttpServletRequest request,@RequestParam("name_like") String name_like) throws UnsupportedEncodingException {
		    model.addAttribute("modifyObj", userDto);
		    currentPage_ = currentPage;//页码赋值
		    name_ = name_like; //模糊查询 标题
		    if(CommonUtil.strIsChinese(userDto.getChName())){//要为中文名
	    		if(CommonUtil.strIsEnglish(userDto.getName())){//要为英文名
	    			if (userService.modify_and_namechName(userDto)) {
	    				attr.addAttribute(PageCodeEnum.KEY, PageCodeEnum.MODIFY_SUCCESS.getMsg());
	    				return "redirect:/user/del_update";
	    			}else{
	    				model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.MODIFY_FAIL);
	    			}
	    		}else{
	    			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.user_isenlish);
	    		}
    		}else{
    			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.user_ischiess);
    		}
		  return "/content/userModify";
	}
	
	@RequestMapping(value="/del_update", method = RequestMethod.GET)
	public String del_update(Model model,@RequestParam("pageCode") String pageCode) {  //修改 删除  初始化 都进行查询
		PageCodeEnum.update_msg.setMsg(pageCode);
		UserDto userDto = new UserDto();
		if(currentPage_>=1 || (name_!=null || name_!="")){
			userDto.getPage().setCurrentPage(currentPage_);
			userDto.setName(name_);
		}	
		model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.update_msg);
		model.addAttribute("list", userService.searchByPage(userDto));
		model.addAttribute("searchParam", userDto);
		return "/content/userList";
	}
	
	
	/**
	 * 删除操作  根据id删除
	 * @param model
	 * @param adDto
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/remove")
	public String remove(Model model,@RequestParam("currentPage") int currentPage,@RequestParam("name") String name, RedirectAttributes attr, MemberDto memberDto,HttpServletRequest request) throws UnsupportedEncodingException {
		 currentPage_ = currentPage;
		 name_ = name;
		 if(userService.remove(memberDto.getId())){ //删除成功  就返回true
			 attr.addAttribute(PageCodeEnum.KEY, PageCodeEnum.REMOVE_SUCCESS.getMsg());
		}else{//false
			attr.addAttribute(PageCodeEnum.KEY, PageCodeEnum.REMOVE_FAIL.getMsg());
		}
		return  "redirect:/user/del_update";
	}
	
	
	
 }