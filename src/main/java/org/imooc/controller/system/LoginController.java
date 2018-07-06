package org.imooc.controller.system;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.imooc.constant.LoginkeyConst;
import org.imooc.constant.PageCodeEnum;
import org.imooc.constant.SessionKeyConst;
import org.imooc.dto.ActionDto;
import org.imooc.dto.GroupDto;
import org.imooc.dto.MenuDto;
import org.imooc.dto.UserDto;
import org.imooc.service.GroupService;
import org.imooc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 登录相关
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private UserService userService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private HttpSession session;
	/**
	 * 初始化登录页面，也就是首次登陆
	 */
	@RequestMapping
	public String index() {
		return "/system/login";
	}
	
	@RequestMapping("/out")
	public String out() {
		return "/system/error";
	}
	
	/**
	 * session超时，就执行这个  让他重定向登陆界面
	 */
	@RequestMapping("/sessionTimeout")
	public String sessionTimeout(Model model) {
		model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.SESSION_TIMEOUT);
		return "/system/error";
	}
	/**
	 * 没有权限访问
	 */
	@RequestMapping("/noAuth")
	public String noAuth(Model model) {
		model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.NO_AUTH);
		session.invalidate(); //把session销毁
		return "/system/error";
	}
	/**
	 * 验证用户名/密码是否正确 验证通过跳转至后台管理首页，验证失败，返回至登录页。
	 */
	@RequestMapping("/validate")
	public String validate(Model model,@ModelAttribute("user") UserDto userDto, RedirectAttributes attr) {
		if(userDto.getName()!=null && userDto.getName()!=""){
			if(userDto.getPassword()!=null && userDto.getPassword()!=""){
				if (userService.validate(userDto)) {
					session.setAttribute(SessionKeyConst.USER_INFO, userDto);
					GroupDto groupDto = groupService.getByIdWithMenuAction(userDto.getGroupId());
					session.setAttribute(SessionKeyConst.MENU_INFO,groupDto.getMenuDtoList());
					session.setAttribute(SessionKeyConst.ACTION_INFO, groupDto.getActionDtoList());
					return "/system/index";
				}
				model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.LOGIN_FAIL);
				return "/system/login";
			}else{
				model.addAttribute(LoginkeyConst.loginpasswordkeyConst, "密码不能为空!");
				return "/system/login";
			}
		}else{
			model.addAttribute(LoginkeyConst.loginusernamekeyConst, "用户名不能为空!");
			return "/system/login";
		}
	}
	
	
	
}