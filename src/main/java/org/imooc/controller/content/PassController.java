package org.imooc.controller.content;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.imooc.constant.PageCodeEnum;
import org.imooc.dto.AdDto;
import org.imooc.dto.UserDto;
import org.imooc.service.AdService;
import org.imooc.service.impl.AdServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/initpass")
public class PassController {
	
	/*
	 * 初始化修改页面的密码
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String initpass(Model model,@RequestParam("name") String name){
		UserDto userDto = new UserDto();
		userDto.setName(name);
		model.addAttribute("user",userDto);
		return "/pass/PassModify";
	}
	
	
	
}