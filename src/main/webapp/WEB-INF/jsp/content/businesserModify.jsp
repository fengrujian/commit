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
		<script type="text/javascript" src="${basePath}/js/content/businesserModify.js"></script>
	</head>
	<body style="background: #FFFFFF;">
		<form id="mainForm" method="post" action="${basePath}/businesseser/modify" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${modifyObj.id}"/>
			<input type="hidden" id="message" value="${pageCode.msg}"/>
			<input type="hidden" id="basePath" value="${basePath}"/>
			<input type="hidden" name="currentPage" id="currentPage" value="${searchParam.page.currentPage}"/>
			<input type="hidden" name="like_name" id="like_name" value="${searchParam.name}"/>
			<input type="hidden" name="user_name" id="user_name" value="${user.name}"/>
			<div class="right">
				<div class="current">当前位置：<a href="###">商家管理</a> &gt;修改商家</div>
				<div class="rightCont">
					<table class="tab1" width="100%">
						<tbody>
							<tr>
							<td align="right" width="10%">商家名称<font color="red">*</font>：</td>
							<td width="30%">
								<input id="name" name="name" value="${modifyObj.name}" class="allInput" style="width:100%;" type="text"/>
							</td>
						</tr>
					  </tbody>
					</table>
					<div style="text-align: center; margin-top: 30px;">
						<input class="tabSub" value="保     存" type="button" onclick="modify();"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input class="tabSub" value="返     回" type="button" onclick="goback();"/>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>