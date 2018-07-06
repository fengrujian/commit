$(function() {
	common.showMessage($("#message").val());//消息提示
	$("#mainForm").validate({
		rules : {
			"name" : "required",
			"chName" : "required",
			"password" : "required"
		},
		messages : {
			"name" : "请输入登录账号！",
			"chName" : "请输入用户中文名！",
			"password" : "请输入密码！"
		}
	});
});

//保存用户
function add() {
//	var currentPage = $("#currentPage").val();
//	var like_name = $("#like_name").val();
//	var user_name = $("#user_name").val();
//	
//	var name = $("#name").val();
//	var chName = $("#chName").val();
	
	$("#mainForm").attr("action",$("#basePath").val() + '/businesseser/adduser');
	$("#mainForm").submit();
}

function check() {
	return true;
}

function goback() {
	var currentPage = $("#currentPage").val();
	var like_name = $("#like_name").val();
	var user_name = $("#user_name").val();
	location.href = $('#basePath').val() + '/businesseser/gobackser' +'?currentPage='+currentPage +'&like_name='+like_name +'&user_name='+user_name;
}

function addbusiness() {
	var currentPage = $("#currentPage").val();
	var like_name = $("#like_name").val();
	var user_name = $("#user_name").val();
	$("#mainForm").attr("action",$("#basePath").val() + '/businesseser/add'+ '?currentPage='+currentPage +'&title_like='+title_like);
	$("#mainForm").submit();
}
