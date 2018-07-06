<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
		<title></title>
		<link rel="stylesheet" href="${basePath}/css/style.css"/>
		<link rel="stylesheet" type="text/css" href="${basePath}/css/all.css"/>
		<link rel="stylesheet" type="text/css" href="${basePath}/css/pop.css"/>
		<link rel="stylesheet" type="text/css" href="${basePath}/css/main.css"/>
		<link rel="stylesheet" type="text/css" href="${basePath}/css/jquery.validate.css"/>
		<script type="text/javascript" src="${basePath}/js/common/validation/jquery.validate.js"></script>
		<script type="text/javascript" src="${basePath}/js/common/validation/messages_zh.js"></script>
		<script type="text/javascript" src="${basePath}/js/common/common.js"></script>
	    <script src="${basePath}/js/common/jquery-1.8.3.js" type="text/javascript"></script>
	    <script src="${basePath}/js/common/jQuery.md5.js" type="text/javascript"></script>
	    <script src="${basePath}/js/common/validation/jquery.validate.min.js" type="text/javascript"></script>
	    <script src="${basePath}/js/system/register.js" type="text/javascript"></script>  
	   <script type="text/javascript">
       $(function(){
    	   common.showMessage('${value}');
       })
    </script>
	
	</head>
	<body style="background: #FFFFFF;">
		<form id="mainForm" action="${basePath}/register/register">
			<input type="hidden" id="message" value="${pageCode.msg}"/>
			<input type="hidden" id="basePath" value="${basePath}"/>
			<div class="right">
				<div class="current">当前位置：<a href="###">用户管理 </a> &gt;超级管理员注册</div>
				<div class="rightCont">
					<table class="tab1" width="100%">
						<tbody>
							<tr>
							<td width="30%">
								<div class="register-group">
					                <h3 class="form-title">账号：</h3>
					                <input type="text" id="name" name="name"  maxlength="37" autocomplete="off" class="iptx" placeholder="请输入正确账号">
					            </div>
							</td>
							<td width="30%">
								<div class="register-group">
					            	<h3 class="form-title">中文名：</h3>
					                <input type="text" id="ch_name" name="ch_name" maxlength="16" autocomplete="off" class="iptx" placeholder="请输入中文名">
					            </div>
							</td>
						</tr>
						<tr>
							<td width="30%">
								<div class="register-group">
					            	<h3 class="form-title">设置密码：</h3>
					            	<input type="hidden" id="password_md5" name="password_md5">
					                <input type="password" id="password" name="password" maxlength="16" autocomplete="off" class="iptx" placeholder="请输入密码">
					            </div>
							</td>
							<td width="30%">
								<div class="register-group">
					            	<h3 class="form-title">确认密码：</h3>
					                <input type="hidden" id="password_md5_1" name="password_md5_1">
					                <input type="password" id="password_re" name="password_re" maxlength="16" autocomplete="off" class="iptx" placeholder="请确认密码">
					            </div>
							</td>
						</tr>
					  </tbody>
					</table>
					 <div style="text-align: center; margin-top: 30px;">
						<input class="tabSub" value="注册" type="button" id="submit_register" onclick="sub();"/>&nbsp;&nbsp;&nbsp;&nbsp;
			         </div>
				</div>
			</div>
		</form>
	</body>
</html>