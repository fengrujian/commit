$(function() {
	common.showMessage($("#message").val());//消息提示
	$("#mainForm").validate({
		rules : {
			"code" : "required",
			"name" : "required",
			"weight" : {
				required : true,
				digits : true,
				maxlength : 5
			}
		},
		messages : {
			"name" :	"请输入名称!"
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
//	$("#title_name").val($("#title_name").val())
	var title = $("#title_name").val();
	location.href = $('#basePath').val() + '/dic/gobackser' +'?currentPage='+currentPage +'&title='+title;
}


