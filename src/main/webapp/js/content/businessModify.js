$(function() {
	common.showMessage($("#message").val());//消息提示
	$("#mainForm").validate({
		rules : {
			"title" : "required",
			"subtitle" : "required",
			"link" : "required",		
			"desc" : "required",
			"price" : {
				required : true,
				digits : true,	
				maxlength : 5
			},
			"distance" : {
				required : true,
				digits : true,
				maxlength : 5
			}
		},
		messages : {
			"title" : "请输入标题！",
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
//	$("#title_like").val($("#title_like").val())
	var title = $("#title_like").val();
	var user_name = $("#user_name").val();
	location.href = $('#basePath').val() + '/businesses/gobackser' +'?currentPage='+currentPage +'&title='+title+ '&user_name='+user_name;
}

