package org.imooc.bean;

public class Businesser extends BaseBean{//商家
    
	private int id;
    private String name;//商家名称
    private int number;//商家的编号
    private int sys_userId;
	private User user; //一个商家对应一个后台用户
    
	public int getSys_userId() {	
		return sys_userId;
	}
	public void setSys_userId(int sys_userId) {
		this.sys_userId = sys_userId;
	}
	
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
