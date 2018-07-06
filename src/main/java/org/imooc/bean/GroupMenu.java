package org.imooc.bean;

public class GroupMenu {
	
	private Long groupId;//装中间表的用户的组的主健
	private Long menuId;//装中间表的菜单的主健

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
}