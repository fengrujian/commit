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
		<script type="text/javascript" src="${basePath}/js/content/dicAdd.js"></script>
	</head>
	<body style="background: #FFFFFF;">
		<form id="mainForm" method="post" action="${basePath}/dic/add" enctype="multipart/form-data">
			<input type="hidden" id="message" value="${pageCode.msg}"/>
			<input type="hidden" id="basePath" value="${basePath}"/>
			<input name="currentPage" id="currentPage" value="${searchParam.page.currentPage}" class="allInput" type="hidden"/>
			<input name="name_like" id="name_like" value="${searchParam.name}" class="allInput" type="hidden"/>
			<div class="right">
				<div class="current">当前位置：<a href="###">字典管理 </a> &gt;新增字典数据</div>
				<div class="rightCont">
<!-- 					<p class="g_name fix">新增广告</p> -->
					<table class="tab1" width="100%">
						<tbody>
							<tr>
							<td align="right" width="10%">名称<font color="red">*</font>：</td>
							<td width="30%">
								<input id="name" name="name" class="allInput" style="width:100%;" type="text"/>
							</td>
							<td align="right" width="10%">code<font color="red">*</font>：</td>
							<td width="30%">
								<input id="code" name="code" class="allInput" style="width:100%;" type="text"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="10%">权重(值越大排名越靠前)<font color="red">*</font>：</td>
							<td width="30%">
								<input id="weight" name="weight"  class="allInput" style="width:100%;" type="text"/>
							</td>
							<td align="right" width="10%">类别<font color="red">*</font>：</td>
							<td width="30%">
									<select name="type">
											<option value="category">category</option>
									        <option value="city">city</option>
									        <option value="httpMethod">httpMethod</option>
									</select>
							</td>	
						</tr>
					  </tbody>
					</table>
					<div style="text-align: center; margin-top: 30px;">
						<input class="tabSub" value="保     存" type="button" onclick="add();"/>&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
				</div>
			</div>
		</form>
	</body>
</html>