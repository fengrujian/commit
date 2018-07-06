$(function() {
	common.showMessage($("#message").val());//消息提示
	$("#mainForm").validate({
		rules : {
			"title" : "required",
			"link" : "required",
			"weight" : {
				required : true,
				digits : true,
				maxlength : 5
			}
		},
		messages : {
			"title" : "请输入标题！"
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
	location.href = $('#basePath').val() + '/ad/gobackser' +'?currentPage='+currentPage +'&title='+title;
}


