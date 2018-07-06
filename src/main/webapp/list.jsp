<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>人人点评系统</title>
	<link href="${basePath}/css/basic_layout.css" rel="stylesheet" type="text/css"/>
	<link href="${basePath}/css/common_style.css" rel="stylesheet" type="text/css"/>
	<link href="${basePath}/css/jquery.fancybox-1.3.4.css"  rel="stylesheet" type="text/css" media="screen"/>
	<link rel="stylesheet" type="text/css" href="${basePath}/css/all.css"/>
	<link rel="stylesheet" type="text/css" href="${basePath}/css/pop.css"/>
	<link rel="stylesheet" type="text/css" href="${basePath}/css/main.css"/>
	<script type="text/javascript" src="${basePath}/js/common/commonAll.js"/>
	<script type="text/javascript" src="${basePath}/js/common/jquery.fancybox-1.3.4.js"/>
	<script type="text/javascript" src="${basePath}/js/common/jquery.fancybox-1.3.4.pack.js"/>
	<script type="text/javascript" src="${basePath}/js/common/jquery.min.js"/>
	<script type="text/javascript" src="${basePath}/js/common/artDialog.js?skin=default"/>
	<script type="text/javascript" src="${basePath}/js/common/common.js"/>
	<script type="text/javascript" src="${basePath}/js/content/adList.js"/>
<style type="text/css">

.a_pre_next_shou {
    display:block;
	width: 80px;
	height: 30px;
	line-height: 30px;
	text-align: center;
	border-style: none;
	cursor: pointer;
	font-family: "Microsoft YaHei", "微软雅黑", "sans-serif";
	background: url('../images/btn.jpg') 0px -1px no-repeat;
}
.a_pre_next_shou:hover {
    display:block;
	width: 80px;
	height: 30px;
	line-height: 30px;
	text-align: center;
	border-style: none;
	cursor: pointer;
	font-family: "Microsoft YaHei", "微软雅黑", "sans-serif";
	background: url('../images/btn_hover.jpg') 0px 0px no-repeat;
	color: #fff;
}
.ui_tb_h30 {
	height: 50px;
	overflow: auto;
}

</style>

<script type="text/javascript">
	$(document).ready(function(){
	/** 新增   **/
// 	    $("#addBtn").fancybox({
// 	    	'href'  : '${pageContext.request.contextPath}/student/insertStudent.action',
// 	    	'width' : 733,
// 	        'height' : 530,
// 	        'type' : 'iframe',
// 	        'hideOnOverlayClick' : false,
// 	        'showCloseButton' : false,
// 	        'onClosed' : function() { 
// 	        	window.location.href = 'student_list1.jsp';
// 	        }
// 	    });
	    /** 导入  **/
	    $("#importBtn").fancybox({
	    	'href'  : '/xngzf/archives/importFangyuan.action',
	    	'width' : 633,
	        'height' : 260,
	        'type' : 'iframe',
	        'hideOnOverlayClick' : false,
	        'showCloseButton' : false,
	        'onClosed' : function() { 
	        	window.location.href = 'house_list.html';
	        }
	    });
		
	    /**编辑   **/
	    $("a.edit").fancybox({
	    	'width' : 733,
	        'height' : 530,
	        'type' : 'iframe',
	        'hideOnOverlayClick' : false,
	        'showCloseButton' : false,
	        'onClosed' : function() { 
	        	window.location.href = 'house_list.html';
	        }
	    });
	});
	/** 用户角色   **/
	var userRole = '';
	/** 模糊查询来电用户  **/
// 	function search(){
// 		$("#submitForm").attr("action", "${pageContext.request.contextPath}/student/queryStu.action").submit();
// 	}
	/** 新增   **/
// 	function add(){
// 		$("#submitForm").attr("action", "/xngzf/archives/luruFangyuan.action").submit();	
// 	}
	/** 新增学生   **/
// 	function addstu(){
// 		if(confirm("您确定要新增学生信息吗？")){
// 			// 提交form
// 			$("#submitForm").attr("action", "${pageContext.request.contextPath}/student/insertStu.action").submit();	
// 		}
// 	}
	/** Excel导出  **/
	function exportExcel(){
		if( confirm('您确定要导出吗？') ){
			var fyXqCode = $("#fyXq").val();
// 			var fyXqName = $('#fyXq option:selected').text();
//	 		alert(fyXqCode);
			if(fyXqCode=="" || fyXqCode==null){
				$("#fyXqName").val("");
			}else{
//	 			alert(fyXqCode);
				$("#fyXqName").val(fyXqName);
			}
			$("#submitForm").attr("action", "/xngzf/archives/exportExcelFangyuan.action").submit();	
		}
	}
	/** 删除   单个删除**/
	function del(id){
		// 非空判断
		if(id == '') return;
		if(confirm("您确定要删除吗？")){
			$("#submitForm").attr("action", "${pageContext.request.contextPath}/student/delStudent.action?id=" + id).submit();			
		}
	}
	/** 单个删除   按条件**/
	function deltj(id){
		// 非空判断
		if(id == '') return;
		if(confirm("您确定要删除吗？")){
			$("#submitForm").attr("action", "/xngzf/archives/delFangyuan.action?id=" + id).submit();			
		}
	}
	/** 批量删除 **/
	function batchDel(){
		if($("input[name='IDCheck']:checked").size()<=0){
			art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'至少选择一条', ok:true,});
			return;
		}
		// 1）取出用户选中的checkbox放入字符串传给后台,form提交
		var allIDCheck = "";
		$("input[name='IDCheck']:checked").each(function(index, domEle){
			bjText = $(domEle).parent("td").parent("tr").last().children("td").last().prev().text();
// 			alert(bjText);
			// 用户选择的checkbox, 过滤掉“已审核”的，记住哦
			if($.trim(bjText)=="已审核"){
// 				$(domEle).removeAttr("checked");
				$(domEle).parent("td").parent("tr").css({color:"red"});
				$("#resultInfo").html("已审核的是不允许您删除的，请联系管理员删除！！！");
// 				return;
			}else{
				allIDCheck += $(domEle).val() + ",";
			}
		});
		// 截掉最后一个","
		if(allIDCheck.length>0) {
			allIDCheck = allIDCheck.substring(0, allIDCheck.length-1);
			// 赋给隐藏域
			$("#allIDCheck").val(allIDCheck);
			if(confirm("您确定要批量删除这些记录吗？")){
				// 提交form
				$("#submitForm").attr("action", "/xngzf/archives/batchDelFangyuan.action").submit();
			}
		}
	}

	/** 普通跳转 **/
	function jumpNormalPage(page){
		$("#submitForm").attr("action", "${pageContext.request.contextPath}/student/queryStudent.action?page=" + page).submit();
	}

	/** 输入页跳转 **/
	function jumpInputPage(totalPageCount){
		// 如果“跳转页数”不为空
		var pageNow = parseInt($("#jumpNumTxt").val());
		 if($("#jumpNumTxt").val() !=''){
			// 如果跳转页数在不合理范围内，则置为1
			if(pageNow<1 | pageNow>totalPageCount){
			   alert("请输入合适的页数，自动为你跳到首页！");
			   $("#submitForm").attr("action", "${pageContext.request.contextPath}/student/queryStudent.action?pageNow="+ 1).submit();
			}else
				$("#submitForm").attr("action", "${pageContext.request.contextPath}/student/queryStudent.action?pageNow="+ pageNow).submit();
		}
	}
	/** 输入页跳转 **/
	function jumpInputPage1(totalPageCount1,industry,level,source){
		// 如果“跳转页数”不为空
		var pageNow1 = parseInt($("#jumpNumTxt1").val());
		 if($("#jumpNumTxt1").val() !=''){
			// 如果跳转页数在不合理范围内，则置为1
			if(pageNow1<1 | pageNow1>totalPageCount1){
			   alert("请输入合适的页数，自动为你跳到首页！");
			   $("#submitForm").attr("action", "${pageContext.request.contextPath}/student/queryStu.action?industry="+industry + "&level="+level + "&source="+source + "&pageNow1="+1).submit();
			}else{
			   alert("确定跳转吗？");
			   $("#submitForm").attr("action", "${pageContext.request.contextPath}/student/queryStu.action?industry="+industry + "&level="+level + "&source="+source + "&pageNow1="+pageNow1).submit();
		 }	
	}
}		 
</script>

	<style>
		.alt td{ background:black !important;}
	</style>
</head>
<body>
    <!-- 列表 -->
	<form action="${basePath}/ad/search" id="mainForm" method="post">
		<input type="hidden" name="allIDCheck" value="" id="allIDCheck"/>
		<input type="hidden" name="fangyuanEntity.fyXqName" value="" id="fyXqName"/>
		<input type="hidden" id="id" name="id"/>
		<input type="hidden" id="message" value="${pageCode.msg}"/>
		<input type="text" id="basePath" value="${basePath}"/>
		<input name="name" value="111111" type="hidden"/>
		<!-- 当前页 默认为第一页 -->
        <input name="currentPage" id="currentPage" value="${searchParam.page.currentPage}" class="allInput" type="text"/>
		<div id="container">
			<div class="ui_content">
				<div class="ui_text_indent">
						<div id="box_border">
							<div id="box_top">搜索</div>
							<div id="box_bottom">
							      <table class="tab1">
									<tbody>
										<tr>
											<td align="right" width="80">标题：</td>
											<td>								
												<input name="title" id="title" value="${searchParam.title}" class="allInput" type="text"/>
											</td>  
				                            <td style="text-align: right;" width="150">
				                            	<input class="tabSub" value="查询" onclick="search('1');" type="button"/>&nbsp;&nbsp;&nbsp;&nbsp;
				                            	<t:auth url="/ad/addInit">
				                            		<input class="tabSub" value="添加" onclick="addInit()" type="button"/>
				                            	</t:auth>
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
						    <th>标题</th>
						    <th>链接地址</th>
						    <th>操作</th>
						</tr>
						    <!-- 循环列表查询所有的 -->
						<c:if test="${!empty list}">
                	       <c:forEach items="${list}" var="item" varStatus="s">
							<tr>
								<td><input type="checkbox" name="IDCheck" value="14458579642011" class="acb"/></td>
								<td>${s.index + 1}</td>
								<td>${item.title}</td>
								<td>${item.link}</td>
								<td>
									<a href="javascript:void(0);" class="edit" onclick="modifyInit('${item.id}')">编辑</a> 
									<a href="javascript:void(0);" onclick="remove('${item.id}')">删除</a>
								</td>
							</tr>
	                	 </c:forEach>   
				      </c:if>
					</table>
				</div>
				<!-- 分页 -->
				<t:page jsMethodName="search" page="${searchParam.page}"></t:page>
			</div>
		</div>
	</form>
</body>
</html>