package org.imooc.constant;

public enum PageCodeEnum {
    ADD_SUCCESS(1000,"新增成功！"),
    ADD_FAIL(1001,"新增失败！"),
    MODIFY_SUCCESS(1100,"修改成功！"),
    MODIFY_FAIL(1101,"修改失败！"),
    REMOVE_SUCCESS(1200,"删除成功！"),
    REMOVE_FAIL(1201,"删除失败！"),
    LOGIN_FAIL(1301,"登录失败！用户名密码或错误！"),
    SESSION_TIMEOUT(1302,"session超时，请重新登录！"),
    NO_AUTH(1303,"没有权限访问请求资源，请切换账户后重试！"),
    USERNAME_EXISTS(1401,"用户名已存在！"),
    GROUPNAME_EXISTS(1402,"用户组名已存在！"),
    SUB_MENU_EXISTS(1403,"菜单下还存在子菜单！"),
    ASSIGN_SUCCESS(1500,"分配成功！"),
    ASSIGN_FAIL(1501,"分配失败！"),
    ORDER_SUCCESS(1600,"排序成功！"),
    ORDER_FAIL(1601,"排序失败！"),
    OLD_PASSWORD(1700,"原始密码不能为空!"),
	NEW_PASSWORD(1701,"新密码不能为空!"),
	NEW_PASSWORDAGIN(1702,"确认新密码不能为空!"),
	TWO_PASSWORD(1703,"两次输入的密码不一致！"),
	USERNAME_null(1704,"账号为空，请重新登录！"),
	not_password(1705,"输入的原密码错误!"),
	update_success(1706,"修改密码成功!"),
	update_fail(1707,"修改密码失败!"),
	phone_noEXISTS(1708,"手机号不存在!请重新输入"),
	phone_nomember(1709,"手机号不是数字或不是一个11位手机号！请重新输入"),
	User_null(1710,"请重新登录！用户名为空！"),
	UserDto_null(1711,"请重新登录！查找不到该登录的用户"),
	groudId_isnull(1712,"该用户的用户组的id为空!"),
	businesser_null(1713,"请添加商家！"),
	user_null(1714,"用户信息为空！"),
	addbusinesser_SUCCESS(1715,"添加商家成功！"),
	REMOVE_not(1715,"该商家下有发商品或商家业务员不能删除!"),
	businesserid_null(1716,"该商家的id为空不能删除!"),
	update_msg(1717,""),
	pricevalue(1718,"你输入的价格不能大于10000"),
	REMOVE_no(1719,"该商品下有订单或有收藏的商品不能删除!"),
	user_ischiess(1720,"用户中文名要为中文！"),
	user_isenlish(1721,"登录账号要为英文！"),
	businesser_haveuser(1722,"该商家已经添加了用户！"),
	noEXISTS_user(1723,"不存在该用户！"),
	REMOVE_not_member(1724,"该会员下有订单不能直接删除！"),
	data_null(1725,"请输入数据！"),
	CATEGORY_NUM(1726,"类别不能超过10"),
	CITY_NUM(1727,"城市不能超过12"),
	name_and_code(1728,"名称与code必须要相同!");
	
    private Integer code;
    private String msg;
    
    public static final String KEY = "pageCode";
    
    PageCodeEnum(Integer code,String msg) {
	this.code = code;
	this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }

	public void setCode(Integer code) {
		this.code = code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
    
}
