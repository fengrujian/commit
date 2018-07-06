package org.imooc.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.imooc.constant.PageCodeEnum;
import org.imooc.constant.SessionKeyConst;
import org.imooc.dto.GroupDto;
import org.imooc.dto.PageCodeDto;
import org.imooc.dto.UserDto;
import org.imooc.service.GroupService;
import org.imooc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户组相关
 */
@RestController
@RequestMapping("/groups")
public class GroupsController {

	@Autowired
	private GroupService groupService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 获取用户组列表
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<GroupDto> getList() {
		return groupService.getList();
	}
	/**
	 * 新增用户组
	 */
	@RequestMapping(method = RequestMethod.POST)
	public PageCodeDto add(GroupDto groupDto) {
		PageCodeDto result;
		if(groupService.add(groupDto)) {
			result = new PageCodeDto(PageCodeEnum.ADD_SUCCESS);
		} else {
			result = new PageCodeDto(PageCodeEnum.GROUPNAME_EXISTS);
		}
		return result;
	}
	/**
	 * 根据主键获取用户组dto
	 */
	@RequestMapping(value="/{id}",method = RequestMethod.GET)
	public GroupDto getGroupById(@PathVariable("id") Long id) {
		return groupService.getById(id);
	}
	/**
	 * 修改用户组
	 */
	@RequestMapping(value="/{id}",method = RequestMethod.PUT)
	public PageCodeDto modify(GroupDto groupDto) {
		PageCodeDto result;
		if(groupService.modify(groupDto)) {
			result = new PageCodeDto(PageCodeEnum.MODIFY_SUCCESS);
		} else {
			result = new PageCodeDto(PageCodeEnum.GROUPNAME_EXISTS);
		}
		return result;
	}
	/**
	 * 删除用户组
	 */
	@RequestMapping(value="/{id}",method = RequestMethod.DELETE)
	public PageCodeDto modify(@PathVariable("id")Long id) {
		PageCodeDto result;
		if(groupService.remove(id)) {
			result = new PageCodeDto(PageCodeEnum.REMOVE_SUCCESS);
		} else {
			result = new PageCodeDto(PageCodeEnum.REMOVE_FAIL);
		}
		return result;
	}
	/**
	 * 获取用户组对应可以访问的菜单和动作
	 */
	@RequestMapping(value="/{id}/menus",method = RequestMethod.GET)
	public GroupDto getMenuList(@PathVariable("id")Long id) {
		return groupService.getByIdWithMenuAction(id);
	}
	/**
	 * 为用户组分配可以访问的菜单
	 */
	@RequestMapping(value="/{id}/menus",method = RequestMethod.POST)
	public PageCodeDto assignMenu(GroupDto groupDto,HttpServletRequest request) {
		PageCodeDto result;
		String name=request.getParameter("name");
		UserDto userDto = new UserDto();
		if(name.length()>0){
			userDto.setName(name);
			userDto=userService.getGorudId_ByName(userDto);
			if(userDto!=null){
				if(groupService.assignMenu(groupDto)) {//删除了两个中间表的数据   在一次插入多个数据
					  //再次查询还是超级管理员的对应的菜单和动作
					    if(userDto.getGroupId().toString().length()>0){
					    	GroupDto groupDto1 = groupService.getByIdWithMenuAction(userDto.getGroupId());
					    	if(groupDto1!=null){
								session.setAttribute(SessionKeyConst.MENU_INFO,groupDto1.getMenuDtoList());
								session.setAttribute(SessionKeyConst.ACTION_INFO, groupDto1.getActionDtoList());
					    	}
					    	result = new PageCodeDto(PageCodeEnum.ASSIGN_SUCCESS);
					    }else{
					    	result = new PageCodeDto(PageCodeEnum.groudId_isnull);
					    }
				   }else{
						result = new PageCodeDto(PageCodeEnum.ASSIGN_FAIL);
					}			
			}else{
				result = new PageCodeDto(PageCodeEnum.UserDto_null);
			}
		}else{
		  result = new PageCodeDto(PageCodeEnum.User_null);
	  }
	  return result;
	}
	
	
}