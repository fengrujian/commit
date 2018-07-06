package org.imooc.dto;

import org.imooc.bean.User;

public class UserDto extends User {
    
    private Integer pId;//虚拟一个根节点，把用户子节点都挂在根节点下面

	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	
	
}