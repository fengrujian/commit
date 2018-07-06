package org.imooc.controller.system;

import javax.servlet.http.HttpSession;

import org.imooc.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController {
	@Autowired
	private HttpSession session;
	
	/**
	 * 登录成功后，后台管理首页
	 */
	@RequestMapping
	public String init(Model model) {
		UserDto UserDto=(UserDto)session.getAttribute("USER_INFO");
		model.addAttribute("user", UserDto);
		return "/system/index";
	}
	/**
	 * 初始化介绍页面
	 * @return
	 */
	@RequestMapping(value="/introduce")
    public String introduce(){
    	return "/system/introduce";
    }
	
	
	
}