<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		<script type="text/javascript" src="${basePath}/js/content/userAdd.js"></script>
	</head>
	<body style="background: #FFFFFF;">
		<form id="mainForm" method="post" action="${basePath}/businesseser/add" enctype="multipart/form-data">
			<input type="hidden" id="message" value="${pageCode.msg}"/>
			<input type="hidden" id="basePath" value="${basePath}"/>
			<input name="currentPage" id="currentPage" value="${searchParam.page.currentPage}" class="allInput" type="hidden"/>
			<input name="like_name" id="like_name" value="${searchParam.name}" class="allInput" type="hidden"/>
			<input name="user_name" id="user_name" value="${user.name}" class="allInput" type="hidden"/>
			<div class="right">
				<div class="current">当前位置：<a href="###">商家管理 </a> &gt;新增用户</div>
				<div class="rightCont">
<!-- 					<p class="g_title fix">新增广告</p> -->
					<table class="tab1" width="100%">
						<tbody>
							<tr>
							<td align="right" width="10%">登录账号<font color="red">*</font>：</td>
							<td width="30%">
							    <input name="businesserId" id="businesserId" value="${user.businesserId}${userDto.businesserId}" class="allInput" type="hidden"/>													
							    <input name="name" id="name" value="" class="allInput" type="text"/>							
							</td>
							<td align="right" width="10%">登录密码<font color="red">*</font>：</td>
							<td width="30%">
							    <input name="password" id="password" value="" class="allInput" type="password"/>							
							</td>
						</tr>
						<tr>
							<td align="right" width="10%">用户中文名<font color="red">*</font>：</td>
							<td width="30%">
								<input name="chName" id="chName" value="" class="allInput" type="text"/>
							</td>
						    <td align="right" width="10%">角色<font color="red">*</font>：</td>
							<td width="30%">
								<select name="groupId">
										<c:forEach items="${groupList}" var="item">
											<option value="${item.id}">${item.name}</option>
										</c:forEach>
								</select>
							</td>
						</tr>
					  </tbody>
					</table>
					<div style="text-align: center; margin-top: 30px;">
						<input class="tabSub" value="保     存" type="button" onclick="add();"/>&nbsp;&nbsp;&nbsp;&nbsp;
<!-- 						<input class="tabSub" value="返     回" type="button" onclick="goback();"/> -->
					</div>
				</div>
			</div>
		</form>
	</body>
</html>
