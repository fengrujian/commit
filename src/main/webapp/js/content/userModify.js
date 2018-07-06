$(function() {
	common.showMessage($("#message").val());//消息提示
	$("#mainForm").validate({
		rules : {
			"name" : "required",
			"chName" : "required",
		},
		messages : {
			"name" : "请输入用户名！",
			"chName" : "请输入用户中文名！"	
		}
	});
});
//保存
function modify() {
	$("#mainForm").submit();
}

//返回
function goback() {
	var currentPage = $("#currentPage").val();
//	$("#name_like").val($("#name_like").val())
	var name = $("#name_like").val();
	location.href = $('#basePath').val() + '/ad/gobackser' +'?currentPage='+currentPage +'&name='+name;
}


