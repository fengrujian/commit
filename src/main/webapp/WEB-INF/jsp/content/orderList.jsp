<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
		<title>人人点评系统</title>
		<link href="${basePath}/css/basic_layout.css" rel="stylesheet" type="text/css"/>
	    <link href="${basePath}/css/common_style.css" rel="stylesheet" type="text/css"/>
	    <link href="${basePath}/css/jquery.fancybox-1.3.4.css"  rel="stylesheet" type="text/css" media="screen"/>
		<link rel="stylesheet" type="text/css" href="${basePath}/css/all.css"/>
		<link rel="stylesheet" type="text/css" href="${basePath}/css/pop.css"/>
		<link rel="stylesheet" type="text/css" href="${basePath}/css/main.css"/>
		<script type="text/javascript" src="${basePath}/js/common/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="${basePath}/js/common/common.js"></script>
		<script type="text/javascript" src="${basePath}/js/content/orderList.js"></script>
	</head>
	<body style="background: #FFFFFF;">
		<form action="${basePath}/orders/search" id="mainForm" method="post">
			<input type="hidden" id="id" name="id"/>
			<input type="hidden" id="message" value="${pageCode.msg}"/>
			<input type="hidden" id="basePath" value="${basePath}"/>
			<input name="name" value="111111" type="hidden"/>
			<!-- 当前页 默认为第一页 -->
            <input name="currentPage" id="currentPage" value="${searchParam.page.currentPage}" class="allInput" type="hidden"/>
			<input name="user_name" id="user_name" value="${user.name}" class="allInput" type="hidden"/>
			<div class="right">
			<div class="ui_content">
				<div class="ui_text_indent">
			    <div id="box_border">
				<div id="box_top">搜索&nbsp;&nbsp;&nbsp;&nbsp;订单列表</div>
				<div id="box_bottom">
				   <table class="tab1">
					<tbody>
						<tr>
							<td align="right" width="80">手机号：</td>
							<td>								
								<input name="phone" id="phone" value="${searchParam.phone}" class="allInput" type="text"/>
							</td>  
                            <td style="text-align: right;" width="150">
                            	<input class="tabSub" value="查询" onclick="search('1');" type="button"/>&nbsp;&nbsp;&nbsp;&nbsp;
<%--                             	<t:auth url="/ad/addInit"> --%>
<!--                             		<input class="tabSub" value="添加" onclick="addInit()" type="button"/> -->
<%--                             	</t:auth> --%>
                            </td>
		       			</tr>
					  </tbody>
				   </table>
			     </div>
			   </div>
			  </div>
	        </div>
					
		   <div class="ui_content">
		   <div class="ui_tb">
			<table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
				<tr>
					<th width="30"><input type="checkbox" id="all" onclick="selectOrClearAllCheckbox(this);" />
					</th>
					<th>序号</th>
					<th>评论状态</th>
					<th>价格</th>
					<th>创建时间</th>
				    <th>商品标题</th>
				    <th>会员手机号</th>
				    <th>所属商家</th>
				    <th>用户名</th>
				    <th>用户中文名</th>
				    <th>所属角色</th>
				</tr>
				<!-- 循环列表查询所有的 -->
				<c:if test="${!empty list}">
              	  <c:forEach items="${list}" var="item" varStatus="s">
					<tr>
						<td><input type="checkbox" name="IDCheck" value="14458579642011" class="acb"/></td>
						<td>${s.index + 1}</td>
						<td>${item.iscomment}</td>
						<td>${item.price}</td>
						<td>${item.create_orderstime}</td>
						<td>${item.business.title}</td>
						<td>${item.member.phone}</td>
						<td>${item.business.businesser.name}</td>
						<td>${item.business.businesser.user.name}</td>
						<td>${item.business.businesser.user.chName}</td>
						<td>${item.business.businesser.user.group.name}</td>
					</tr>
               	 </c:forEach>   
		       </c:if>
			 </table>
		   </div>
	      </div>
		 <!-- 分页 -->
	    <t:page jsMethodName="search" page="${searchParam.page}"></t:page>
	 </div>
	</div>
   </form>
</body>
</html>