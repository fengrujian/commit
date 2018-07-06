<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	    <title>人人点评后台管理</title>
	    <link rel="stylesheet" type="text/css" href="${basePath}/css/login.css" />
	    <link rel="stylesheet" type="text/css" href="${basePath}/css/jquery.validate.css" />
	    <script src="${basePath}/js/common/jquery-1.8.3.js" type="text/javascript"></script>
	    <script src="${basePath}/js/common/jQuery.md5.js" type="text/javascript"></script>
	    <script src="${basePath}/js/common/validation/jquery.validate.min.js" type="text/javascript"></script>
	    <script src="${basePath}/js/common/validation/messages_zh.js" type="text/javascript"></script>
	    <script src="${basePath}/js/common/common.js" type="text/javascript"></script>
	    <script src="${basePath}/js/system/login.js" type="text/javascript"></script>
	</head>
	<body>
		<input type="hidden" id="basePath" value="${basePath}"/>
<%-- 		<input type="hidden" id="message" value="${pageCode.msg}"/> --%>
		<div class="main">
<!-- 		    <div class="header hide"></div> -->
		    <div class="content">
		        <div class="title hide"></div>
		        <form id="mainForm" method="post" action="${basePath}/login/validate">
		            <fieldset> 
		                <div class="input">
		                    <p style="text-align: center;color:red;">${pageCode.msg}</p>
		                    <input class="input_all name" name="name" value="${user.name}" id="name" placeholder="用户名" type="text" onFocus="this.className='input_all name_now';" onBlur="this.className='input_all name'"/>
		                    <p style="color:red;">${login_username}</p>
		                </div>
		                <div class="input">
		                	<input type="hidden" name="password" id="password_md5"/>
		                    <input class="input_all password" id="password" value="${user.password}" type="password" placeholder="密码" onFocus="this.className='input_all password_now';" onBlur="this.className='input_all password'"/>
		                    <p style="color:red;">${login_password}</p>
		                </div>
		                <div class="checkbox">
		                    <input type="checkbox" name="remember" id="remember" /><label for="remember"><span>记住密码 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></label>
		                </div>
		                <a href="${basePath}/register/index" id="reg">超级管理员注册？&nbsp;&nbsp;&nbsp;</a>
		                <div class="enter">
		                    <input class="button hide" type="button" id="submit_login" value="登录" />
		                </div>
		            </fieldset>
		        </form>
		    </div>
		</div>
	</body>
</html>