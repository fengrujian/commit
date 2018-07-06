package org.imooc.bean;

import java.util.List;

public class User extends BaseBean{
	
	private Long id;
	private String name;
	private String password;
	private String chName;
	private Long groupId;
	private Long businesserId;
	private Group group;

    //用户对应多个商家  一个商家业务员只对应多个商家，意思一个商家业务员可以添加属于自己身份的下多个的商家
//	private List<Businesser> businesser;
	
	private Businesser businesser;
	
	public Businesser getBusinesser() {
		return businesser;
	}
	
	public void setBusinesser(Businesser businesser) {
		this.businesser = businesser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getChName() {
		return chName;
	}

	public void setChName(String chName) {
		this.chName = chName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
//	public List<Businesser> getBusinesser() {
//		return businesser;
//	}
//
//	public void setBusinesser(List<Businesser> businesser) {
//		this.businesser = businesser;
//	}
	 public Group getGroup() {
			return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Long getBusinesserId() {
		return businesserId;
	}

	public void setBusinesserId(Long businesserId) {
		this.businesserId = businesserId;
	}
	
	
}