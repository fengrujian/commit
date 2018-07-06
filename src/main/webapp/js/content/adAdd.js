$(function() {
	common.showMessage($("#message").val());//消息提示
	$("#mainForm").validate({
		rules : {
			"title" : "required",
			"link" : "required",
			"imgFile" : "required",
			"weight" : {
				required : true,
				digits : true,
				maxlength : 5
			}
		},
		messages : {
			"title" : "请输入标题！",
			"imgFile" :	"请上传图片!"
		}
	});
});

function add() {
	var currentPage = $("#currentPage").val();
	var title_like = $("#title_like").val();
	$("#mainForm").attr("action",$("#basePath").val() + '/ad/add'+ '?currentPage='+currentPage +'&title_like='+title_like);
	$("#mainForm").submit();
}

function check() {
	// TODO 需要添加表单验证
	return true;
}

function goback() {
	var currentPage = $("#currentPage").val();
//	$("#title_like").val($("#title_like").val())
	var title = $("#title_like").val();
	
	location.href = $('#basePath').val() + '/ad/gobackser' +'?currentPage='+currentPage +'&title='+title;
}
