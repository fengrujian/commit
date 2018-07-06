package org.imooc.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.imooc.bean.Ad;
import org.imooc.bean.Member;
import org.imooc.bean.User;
import org.imooc.dao.UserDao;
import org.imooc.dto.AdDto;
import org.imooc.dto.MemberDto;
import org.imooc.dto.UserDto;
import org.imooc.service.UserService;
import org.imooc.util.CommonUtil;
import org.imooc.util.FileUtil;
import org.imooc.util.MD5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	public boolean validate(UserDto userDto) {
		if (userDto != null && !CommonUtil.isEmpty(userDto.getName()) && !CommonUtil.isEmpty(userDto.getPassword())) {
			User user = new User();
			BeanUtils.copyProperties(userDto, user);
			List<User> list = userDao.select(user);
			if(list.size() == 1) {
				BeanUtils.copyProperties(list.get(0),userDto);
				return true;
			}
			return false;
		}
		return false;
	}
    /**
     * 获取用户的数据
     */
	@Override
	public List<UserDto> getList() {
		List<UserDto> result = new ArrayList<>();
		List<User> userList = userDao.select(new User());
		if(userList.size()>0){
			for (User user : userList) {
				UserDto userDto = new UserDto();
				result.add(userDto);
				BeanUtils.copyProperties(user, userDto);
				userDto.setpId(0);// 把pId设置为0，0就是根节点的id值，把子节点都挂在根节点的下面
			}
		}
		return result;
	}

	@Override
	public boolean add(UserDto userDto) {
		User user = new User();
		BeanUtils.copyProperties(userDto, user);
		user.setPassword(MD5Util.getMD5(userDto.getPassword()));//对密码加密了一下
		return userDao.insert(user) == 1;
	}

	@Override
	public UserDto getById(Long id) {
		UserDto userDto = new UserDto();
		User user = userDao.selectById(id);
		if(user!=null){
			BeanUtils.copyProperties(user, userDto);
		}
		return userDto;
	}
    /**
     * 根据用户的id修改用户
     */
	@Override
	public boolean modify(UserDto userDto) {
		User user = new User();
		BeanUtils.copyProperties(userDto, user);
		if(!CommonUtil.isEmpty(userDto.getPassword())) {
			user.setPassword(MD5Util.getMD5(userDto.getPassword()));//对密码加密
		}
		return userDao.update(user) == 1;
	}
	
	// 只修改用户名与用户中文名
	@Override
	public boolean modify_and_namechName(UserDto userDto) {
		User user = new User();
		BeanUtils.copyProperties(userDto, user);
		return userDao.update(user) == 1;
	}
	
	@Override
	public boolean remove(Long id) {
		return userDao.delete(id) == 1;
	}
	
	@Override
	public UserDto getByName(UserDto userDto) {
		if (userDto.getName()!=null && userDto.getChName()!=null && userDto.getPassword()!=null) {
			User user = new User();
			BeanUtils.copyProperties(userDto, user);
			List<User> list = userDao.selectByName(user);	
			if(list.size()==1){
				BeanUtils.copyProperties(list.get(0), userDto);
				return userDto;
			}
			return null;
		}else{
			return null;
		}
	}
	
	@Override
	public UserDto getGorudId_ByName(UserDto userDto) {
		if (userDto.getName()!=null) {
			User user = new User();
			BeanUtils.copyProperties(userDto, user);
			List<User> list = userDao.selectByName(user);	
			if(list.size()==1){
				BeanUtils.copyProperties(list.get(0), userDto);
				return userDto;
			}
			return null;
		}else{
			return null;
		}
	}
	
	@Override
	public int register(UserDto userDto) {
		if(userDto!=null){
			int num = userDao.register(userDto);
			if(num==1){
				return num;
			}else{
				return 0;
			}
		}else{
			return 0;
		}
	}
	
	/**
	 *   根据账号  判断是否是原密码
	 */
	@Override
	public boolean isoldpassword(String name, String oldPassword_md5) {
	   	User user=userDao.isoldpassword(name,oldPassword_md5);
	   	if(user!=null){
			return true;
		}
		return false;
	}
	
	/**
	 * 根据账号   修改密码
	 */
	@Override
	public int UpdateUserPassword(String name, String newPassword_md5) {
		int num=userDao.UpdateUserPassword(name,newPassword_md5);
	   	if(num==1){
			return num;
		}
		return 0;
	}
	
	
	@Override
	public UserDto getUserandGroup(UserDto userDto) {
		if (userDto.getName()!=null) {
			User user = new User();
			BeanUtils.copyProperties(userDto, user);
			List<User> list = userDao.selectUserandGroupByName(user);	
			if(list.size()==1){
				BeanUtils.copyProperties(list.get(0), userDto);
				return userDto;
			}
			return null;
		}else{
			return null;
		}
		
	}
	
	@Override
	public UserDto getUserandgroupById(int sys_userId) {
		User user = new User();
		user.setId((long)sys_userId);
		User user1 = userDao.getUserandGroupById(user);
		UserDto userDto = new UserDto();
		if(user1!=null){
			if(user1.getGroup()!=null){
				BeanUtils.copyProperties(user1, userDto);
			}
		}
		return userDto;
	}
	
	
	@Override
	public int insertUser(UserDto userDto) {
		String password_md5= MD5Util.getMD5(userDto.getPassword());
		userDto.setPassword(password_md5);
		User user = new User();
		if(userDto!=null){
			BeanUtils.copyProperties(userDto, user);
		}
		int num = userDao.insertuser(user);
		return num;
	}
	
	@Override
	public UserDto getUserByBusinesserId(UserDto userDto) {	
		
		if (userDto.getBusinesserId()!=null) {
			User user = new User();
			BeanUtils.copyProperties(userDto, user);
			List<User> list = userDao.selectByBusinesserId(user);	
			if(list.size()==1){
				BeanUtils.copyProperties(list.get(0), userDto);
				return userDto;
			}
			return null;
		}else{
			return null;
		}
	}
	
	//用户管理
	@Override
	public List<UserDto> searchByPage(UserDto userDto) {
		List<UserDto> result = new ArrayList<UserDto>();
		User condition = new User();
		BeanUtils.copyProperties(userDto, condition);
		List<User> userList = userDao.searchByPage(condition);//使用mybatis的分页拦截器
		if(userList.size()>0){
			for (User user : userList) {
				UserDto userDto1 = new UserDto();
				BeanUtils.copyProperties(user, userDto1);
				result.add(userDto1);
			}
		}
		return result;
	}
	

	
	
}