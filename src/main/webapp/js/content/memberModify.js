$(function() {
	common.showMessage($("#message").val());//消息提示
	$("#mainForm").validate({
		rules : {
			"name" : "required",
			"link" : "required",
			"weight" : {
				required : true,
				digits : true,
				maxlength : 5
			}
		},
		messages : {
			"name" : "请输入标题！"
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


