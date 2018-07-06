package org.imooc.controller.system;

import org.imooc.constant.RegisterKeyConst;
import org.imooc.dto.GroupDto;
import org.imooc.dto.UserDto;
import org.imooc.service.GroupService;
import org.imooc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private UserService userService;
	@Autowired
	private GroupService groupService;
	
	//初始化注册页面
	@RequestMapping("/index")
	public String index(){
		return "/system/register";
	}
	
	@RequestMapping("/register")
	public String register(Model model, @RequestParam("name") String name,@RequestParam("ch_name") String ch_name, 
			@RequestParam("password_md5") String password_md5,
			@RequestParam("password_md5_1") String password_md5_1){
		if(name!=null && name !=""
				&&ch_name!=null && ch_name!="" 
				&& password_md5!=null && password_md5!="" 
				&& password_md5_1!=null && password_md5_1!=""){
			 if(password_md5.equals(password_md5_1)){
				 UserDto userDto = new UserDto();
				 userDto.setName(name);
				 userDto.setChName(ch_name);
				 userDto.setPassword(password_md5);
				 UserDto user = userService.getByName(userDto);
				 if(user!=null && user.getName()!=null){ //注册了
					 model.addAttribute(RegisterKeyConst.REGISTERVALUE, "用户已存在!");
					 return "/system/register";
				 }else{//没注册		
				    GroupDto groupDto =	groupService.selectByName(RegisterKeyConst.REGISTERNAME);
				    if(groupDto!=null && groupDto.getName()!=null){
				    	userDto.setGroupId(groupDto.getId());
				    	int num=userService.register(userDto);
				    	if(num==1){
				    	  model.addAttribute(RegisterKeyConst.REGISTERVALUE, "注册成功!");
						  return "/system/register";
				    	}else{
				    	  model.addAttribute(RegisterKeyConst.REGISTERVALUE, "注册失败!");
						  return "/system/register";
				    	}
				    }else{
				    	 model.addAttribute(RegisterKeyConst.REGISTERVALUE, "用户角色不存在!");
						 return "/system/register";
				    }
				 }
			 }else{
			   model.addAttribute(RegisterKeyConst.REGISTERVALUE, "设置的密码和确认密码不一致!");
			   return "/system/register";
			 }	     
		}else{
			model.addAttribute(RegisterKeyConst.REGISTERVALUE, "请输入值!");
			return "/system/register";
		}
	}
	
	
	
	
	
}