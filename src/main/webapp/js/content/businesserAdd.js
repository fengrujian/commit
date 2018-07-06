$(function() {
	common.showMessage($("#message").val());//消息提示
	$("#mainForm").validate({
		rules : {
			"businesser_name" : "required",	
		},
		messages : {
			"businesser_name" : "请输入商家名称！",
			
		}
	});
});


function add() {
	var currentPage = $("#currentPage").val();
	var like_name = $("#like_name").val();
	var user_name = $("#user_name").val();
	var businesser_name = $("#businesser_name").val();
	$("#mainForm").attr("action",$("#basePath").val() + '/businesseser/add'+ '?currentPage='+currentPage +'&like_name='+like_name +'&user_name='+user_name+'&businesser_name='+businesser_name);
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
