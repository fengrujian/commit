package org.imooc.constant;
/**
 * 用于发验证码。的返回值
 * @author 阿奶
 *
 */
public enum ApiCodeEnum {
    SUCCESS(0,"ok"),
    not_SUCCESS(12,"fale"),
    USER_NOT_EXISTS(1,"用户不存在！"),
    REPEAT_REQUEST(2,"验证码有效时间内不需重复请求�?"),
    SEND_FAIL(3,"发�?�验证码失败！请稍后重试�?"),
    CODE_INVALID(4,"验证码已失效！请重新请求验证码！"),
    PASSWORD_ERROR(5,"密码不正确或密码为空！"),
    BUY_FAIL(6,"购买失败�?"),
    NOT_LOGGED(7,"没有登录�?"),
    NO_AUTH(8,"没有权限�?"),
	USER_EXISTS(9,"用户存在！"),
	Password_ERROR(10,"密码跟重复密码不匹配�?"),
	Input_ERROR(11,"输入框不能为空"),
    isLogin(12,"是登陆状态"),
    not_isLogin(13,"不是登陆状态"),
	not_phone(14,"手机号不符合！"),
	null_token(15,"token为空"),
	loginOut_SUCCESS(16,"退出成功"),
	business_null(17,"查询不到商品"),
	business_not_MATH(18,"该商品不匹配"),
	business_collect_SUCCESS(19,"收藏商品成功"),
	business_collect_FAIl(20,"收藏商品失败"),
	phone_business_null(21,"手机号不符合或值不能为空"),
	EXISTS_business(22,"已经收藏过该商品了!"),
	NOT_EXISTS_business(23,"还没有收藏该商品!"),
	delete_collectbusiness_SUCCESS(24,"取消收藏的商品成功"),
	delete_collectbusiness_FAIL(25,"取消收藏的商品失败"),
	BUY_SUCCESS(26,"下单成功！"),
	submitcommit_fail(27,"提交评论失败！");
	
    private Integer errno;
    private String msg;
    
    ApiCodeEnum(Integer errno,String msg) {
	this.errno = errno;
	this.msg = msg;
    }

    public Integer getErrno() {
        return errno;
    }

    public String getMsg() {
        return msg;
    }
}