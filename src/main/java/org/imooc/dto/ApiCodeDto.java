package org.imooc.dto;

import org.imooc.bean.Member;
import org.imooc.constant.ApiCodeEnum;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ApiCodeDto {

	private Integer errno;//提示的数字

	private String msg;//验证码

	private String token;

	private String code;
    //用来存手机号  返回给客户端
	private Long phone;
	
	public Long getPhone() {
		return phone;
	}
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	
	public ApiCodeDto(Integer errno,Long phone,String token) {
		this.errno = errno;
		this.phone = phone;
		this.token = token;
	}

	public ApiCodeDto(Integer errno, String msg) {
		this.errno = errno;
		this.msg = msg;
	}

	public ApiCodeDto() {
		
	}
	
	public ApiCodeDto(ApiCodeEnum apiCodeEnum) {
		this.errno = apiCodeEnum.getErrno();
		this.msg = apiCodeEnum.getMsg();
	}
	

	public Integer getErrno() {
		return errno;
	}

	public void setErrno(Integer errno) {
		this.errno = errno;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
