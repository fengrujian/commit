package org.imooc.controller.system;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.imooc.constant.PageCodeEnum;
import org.imooc.dto.MemberDto;
import org.imooc.dto.PageCodeDto;
import org.imooc.dto.UserDto;
import org.imooc.service.UserService;
import org.imooc.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *  用户相关
 */
@RestController
@RequestMapping("/users")
public class UsersController {
	@Autowired
	private UserService userService;
	/**
	 * 获取用户列表
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<UserDto> getList() {
		return userService.getList();
	}
	/**
	 * 新增用户
	 */
	@RequestMapping(method = RequestMethod.POST)
	public PageCodeDto add(UserDto userDto) {
		PageCodeDto result;
		if(userService.add(userDto)) {
			result = new PageCodeDto(PageCodeEnum.ADD_SUCCESS);
		} else {
			result = new PageCodeDto(PageCodeEnum.USERNAME_EXISTS);
		}
		return result;
	}
	/**
	 * 根据主键获取用户dto
	 */
	@RequestMapping(value="/{id}",method = RequestMethod.GET)
	public UserDto getById(@PathVariable("id") Long id) {
		return userService.getById(id);
	}
	/**
	 *  修改用户
	 */
	@RequestMapping(value="/{id}",method = RequestMethod.PUT)
	public PageCodeDto modify(UserDto userDto) {
		PageCodeDto result;
		if(userService.modify(userDto)) {
			result = new PageCodeDto(PageCodeEnum.MODIFY_SUCCESS);
		} else {
			result = new PageCodeDto(PageCodeEnum.USERNAME_EXISTS);
		}
		return result;
	}	
	/**
	 * 删除用户
	 */
	@RequestMapping(value="/{id}",method = RequestMethod.DELETE)
	public PageCodeDto remove(@PathVariable("id")Long id) {
		PageCodeDto result;
		if(userService.remove(id)) {
			result = new PageCodeDto(PageCodeEnum.REMOVE_SUCCESS);
		} else {
			result = new PageCodeDto(PageCodeEnum.REMOVE_FAIL);
		}
		return result;
	}
	/*
	 * 修改用户密码
	 */
	@RequestMapping(value="/updatepassword",method=RequestMethod.POST)
	public PageCodeDto updatepassword(@RequestParam("name") String name,@RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword,@RequestParam("newPasswordAgain") String newPasswordAgain){
	   PageCodeDto result;
	   if(oldPassword!=null && oldPassword!=""){
		  if(newPassword!=null && newPassword!=""){
			  if(newPasswordAgain!=null && newPasswordAgain!=""){
				 if(newPassword.equals(newPasswordAgain)){
					 String oldPassword_md5 = MD5Util.getMD5(oldPassword);  
					 String newPassword_md5 = MD5Util.getMD5(newPassword);
					 String newPasswordAgain_md5 = MD5Util.getMD5(newPasswordAgain);
					 if(name!=null && name!=""){
						 boolean bool=userService.isoldpassword(name,oldPassword_md5);
						 if(bool){
							int num = userService.UpdateUserPassword(name,newPassword_md5);
							if(num==1){
								result = new PageCodeDto(PageCodeEnum.update_success);
							}else{
								result = new PageCodeDto(PageCodeEnum.update_fail);
							}
						 }else{
							 result = new PageCodeDto(PageCodeEnum.not_password);
						 } 						 
					 }else{
						 result = new PageCodeDto(PageCodeEnum.USERNAME_null);						 
					 }
				 }else{
					 result = new PageCodeDto(PageCodeEnum.TWO_PASSWORD);
				 }
			  }else{
				  result = new PageCodeDto(PageCodeEnum.NEW_PASSWORDAGIN); 
			  }
		  }else{
			  result = new PageCodeDto(PageCodeEnum.NEW_PASSWORD);
		  }	
       }else{
    	   result = new PageCodeDto(PageCodeEnum.OLD_PASSWORD);
       }
	   return result;
	}
	
	
	
	
	
 }