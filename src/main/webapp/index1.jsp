<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>     
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>人人点评后台管理</title>
    <link rel="stylesheet" href="${basePath}/css/style_index.css" type="text/css" media="screen" />
    <link href="${basePath}/css/main_css.css" rel="stylesheet" type="text/css" />
	<link href="${basePath}/css/zTreeStyle.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${basePath}/js/common/jquery.min.js"></script>
	<script type="text/javascript" src="${basePath}/js/common/jquery.ztree.core-3.2.js"></script>
	<script type="text/javascript" src="${basePath}/js/common/commonAll.js"></script>
    <script type="text/javascript" src="${basePath}/js/system/index1.js"></script>
</head>
<!-- 页面加载完成时 自动加载这个onload="getDate01()" 函数 -->
<body onload="getDate01()">
   
    <!-- 头部 -->
    <div id="top">
		<div id="top_links">
			<div id="top_op">
				<ul>
					<li>
						<img alt="当前用户" src="${basePath}/images/user.jpg">：
						<span>admin</span>
					</li>
					<li>
						<img alt="事务月份" src="images/month.jpg">：
						<span id="yue_fen"></span>
					</li>
					<li>
						<img alt="今天是" src="images/date.jpg">：
						<span id="day_day"></span>
					</li>
				</ul> 
			</div>
			<div id="top_close">
				<a href="javascript:void(0);" onclick="logout();" target="_parent">
					<img alt="退出系统" title="退出系统" src="images/close.jpg" style="position: relative; top: 10px; left: 25px;">
				</a>
			</div>
		</div>
	</div>
    <!-- 头部结束 --> 
     <input type="text" id="basePath" value="${basePath}"/>
    
    <!-- 左边导航栏 -->
	<div id="side">
	     <!-- 最左边 -->
		<div id="left_menu">
		    <!-- 左边图标 -->
		 	<ul id="TabPage2" style="height:200px; margin-top:50px;">
				<li id="left_tab1" class="selected" onClick="javascript:switchTab('TabPage2','left_tab1');" title="业务模块">
					<img alt="业务模块" title="业务模块" src="images/1_hover.jpg" width="33" height="31">
				</li>
				<li id="left_tab2" onClick="javascript:switchTab('TabPage2','left_tab2');" title="系统管理">
					<img alt="系统管理" title="系统管理" src="images/2.jpg" width="33" height="31">
				</li>		
				<li id="left_tab3" onClick="javascript:switchTab('TabPage2','left_tab3');" title="其他">
					<img alt="其他" title="其他" src="images/3.jpg" width="33" height="31">
				</li>
			</ul>
			<!-- 显示/隐藏 -->
			<div id="nav_show" style="position:absolute; bottom:0px; padding:10px;">
				<a href="javascript:;" id="show_hide_btn">
					<img alt="显示/隐藏" title="显示/隐藏" src="images/nav_hide.png" width="35" height="35">
				</a>
			</div>
		 </div>
		 <!-- 左边菜单 -->
		 <div id="left_menu_cnt">
		 	<div id="nav_module">
		 		<img src="images/module_1.png" width="210" height="58"/>
		 	</div>	 	
		 	<div id="nav_resource">
		 		<ul id="dleft_tab1" class="ztree">
		 		  </ul>
		 	    <!-- 代码开始下拉  菜单-->             
					<div id="wrapper-250">
					  <ul class="accordion">
					  
					    <li id="one" class="files"><a href="">学籍管理</a>
<!-- 					      <ul class="sub-menu"> -->
<%-- 					        <li><a href="${pageContext.request.contextPath}/student/queryStudent.action" target="right">学生信息查询</a></li> --%>
<%-- 					        <li><a href="${pageContext.request.contextPath}/student/queryStudent.action" target="right">学生成绩查询</a></li> --%>
<!-- 					      </ul> -->
					    </li>
					    
<!-- 					    <li id="one" class="files"><a href="">学籍管理1</a> -->
<!-- 					      <ul class="sub-menu"> -->
<%-- 					        <li><a href="${pageContext.request.contextPath}/student/queryStudent.action" target="right">学生信息查询</a></li> --%>
<%-- 					        <li><a href="${pageContext.request.contextPath}/student/queryStudent.action" target="right">学生成绩查询</a></li> --%>
<!-- 					      </ul> -->
<!-- 					    </li> -->
					    
<!-- 					    <li id="two" class="mail"> <a href="#two">信息管理</a> -->
<!-- 					      <ul class="sub-menu"> -->
<%-- 					        <li><a href="${pageContext.request.contextPath}/studentmessage/queryStudentmessage.action" target="right">个人信息查询</a></li> --%>
<!-- 					        <li><a href="indexadmin.jsp" target="right">宿舍信息</a></li> -->
<!-- 					      </ul> -->
<!-- 					    </li> -->
   
<!-- 					    <li id="three" class="cloud"> <a href="#three">Cloud</a> -->
<!-- 					      <ul class="sub-menu"> -->
<!-- 					        <li><a href="#">Connect</a></li> -->
<!-- 					        <li><a href="#">Profiles</a></li> -->
<!-- 					        <li><a href="#">Options</a></li> -->
<!-- 					        <li><a href="#">Connect</a></li> -->
<!-- 					        <li><a href="#">Profiles</a></li> -->
<!-- 					        <li><a href="#">Options</a></li> -->
<!-- 					      </ul> -->
<!-- 					    </li> -->
   
<!-- 					    <li id="four" class="sign"> <a href="#four">退出管理</a> -->
<!-- 					       <ul class="sub-menu"> -->
<!-- 					         <li><a href="#">退出系统</a></li> -->
<!-- 					       </ul> -->
<!-- 					    </li> -->
           </ul>
       </div>
  <!-- 代码结束下拉 菜单-->
  
  </div>
 </div> 
</div>

    <!-- 右边页面 -->
    <div id="main">
      	<iframe name="right" id="rightMain" src="introduce.jsp" frameborder="no" scrolling="auto" width="100%" height="100%" allowtransparency="true"/>
    </div>
    
</body>
</html>