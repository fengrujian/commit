<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>人人点评系统</title>
  <link rel="stylesheet" type="text/css" href="${basePath}/css/login.css"/>
  <link href="${basePath}/css/jquery.validate.css" rel="stylesheet" type="text/css" />
  <script src="${basePath}/js/common/jquery.min.js" type="text/javascript"></script>
  <script src="${basePath}/js/system/login.js" type="text/javascript" charset=""></script>
  <script src="${basePath}/js/common/common.js" type="text/javascript"></script>
  <script src="${basePath}/js/common/jQuery.md5.js" type="text/javascript"></script>
  <script src="${basePath}/js/common/validation/jquery.validate.min.js" type="text/javascript"></script>
  <script src="${basePath}/js/common/validation/messages_zh.js" type="text/javascript"></script>
</head>

<body>
    <input type="hidden" id="basePath" value="${basePath}"/>
	<div class="event" id="login_box" style="display: block;">
		<div class="login">
			<div class="title">
				<span class="t_txt">欢迎登录，<b>人人点评后台管理系统</b></span>
			</div>
			 <form id="mainForm" method="post" action="${basePath}/login/validate">
				
				<p style="text-align: center;color:red;">${pageCode.msg}</p>
				<input type="text" name="name" id="name" value="${user.name}" placeholder="请输入用户名"/>
				<p style="color:red;">${login_username}</p>
				
				<input type="hidden" name="password" id="password_md5"/>
				<input type="password" name="" id="password" value="${user.password}" placeholder="请输入密码"/>
				<p style="color:red;">${login_password}</p>
				
				<input type="button" id="submit_login" name="" value="登录" class="btn"/>
			    
			</form>
		</div>
	</div>
</body>
</html>