<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
		<title></title>
		<link rel="stylesheet" type="text/css" href="${basePath}/css/all.css"/>
		<link rel="stylesheet" type="text/css" href="${basePath}/css/pop.css"/>
		<link rel="stylesheet" type="text/css" href="${basePath}/css/main.css"/>
		<link rel="stylesheet" type="text/css" href="${basePath}/css/jquery.validate.css"/>
		<script type="text/javascript" src="${basePath}/js/common/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="${basePath}/js/common/validation/jquery.validate.js"></script>
		<script type="text/javascript" src="${basePath}/js/common/validation/messages_zh.js"></script>
		<script type="text/javascript" src="${basePath}/js/common/common.js"></script>
		<script type="text/javascript" src="${basePath}/js/pass/PassModify.js"></script>
	</head>
	<body style="background: #FFFFFF;">
		<form id="mainForm" method="post" action="" enctype="">
			<input type="hidden" id="message" value="${pageCode.msg}"/>
			<input type="hidden" id="basePath" value="${basePath}"/>
			<input name="name" id="name" style="width: 240px;" type="hidden"  value="${user.name}"/>	                  
			<div class="right">
				<div class="current">当前位置：<a href="###">密码管理</a> &gt;修改密码</div>
				<div class="rightCont">
<!-- 					<p class="g_title fix">修改广告</p> -->
					<table class="tab1" width="100%">
						<tbody>	
							<tr>
							<p id="message_err" style="text-align: center; color:red;"></p>
							<td align="right" width="10%">原始密码<font color="red">*</font>：</td>
							<td width="30%">
								<input id="oldPassword" name="oldPassword"  class="allInput" style="width:100%;" type="text"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="10%">新密码<font color="red">*</font>：</td>
							<td width="30%">
								<input id="newPassword" name="newPassword"  class="allInput" style="width:100%;" type="password"/>
							</td>
							<td align="right" width="10%">确认新密码<font color="red">*</font>：</td>
							<td width="30%">
								<input id="newPasswordAgain" name="newPasswordAgain" class="allInput" style="width:100%;" type="password"/>
							</td>
						</tr>
					  </tbody>
					</table>
					<div style="text-align: center; margin-top: 30px;">
						<input class="tabSub" value="提交" type="button" onclick="sub();"/>&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
				</div>
			</div>
		</form>
	</body>
</html>