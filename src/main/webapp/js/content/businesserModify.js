$(function() {
	common.showMessage($("#message").val());//消息提示
	$("#mainForm").validate({
		rules : {
			"name" : "required",
			
		},
		messages : {
			"name" : "请输入商家名称！"
		}
	});
});
//保存
function modify() {
	$("#mainForm").submit();
}

//返回
//function goback() {
//	var currentPage = $("#currentPage").val();
//	var title = $("#title_like").val();
//	location.href = $('#basePath').val() + '/ad/gobackser' +'?currentPage='+currentPage +'&title='+title;
//}

function goback() {
	var currentPage = $("#currentPage").val();
	var like_name = $("#like_name").val();
	var user_name = $("#user_name").val();
	location.href = $('#basePath').val() + '/businesseser/gobackser' +'?currentPage='+currentPage +'&like_name='+like_name +'&user_name='+user_name;
}
