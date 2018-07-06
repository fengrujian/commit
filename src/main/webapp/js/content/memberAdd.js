$(function() {
	common.showMessage($("#message").val());//消息提示
	$("#mainForm").validate({
		rules : {
			"name" : "required",
			"link" : "required",
			"imgFile" : "required",
			"weight" : {
				required : true,
				digits : true,
				maxlength : 5
			}
		},
		messages : {
			"name" : "请输入标题！",
			"imgFile" :	"请上传图片!"
		}
	});
});

function add() {
	var currentPage = $("#currentPage").val();
	var name_like = $("#name_like").val();
	$("#mainForm").attr("action",$("#basePath").val() + '/ad/add'+ '?currentPage='+currentPage +'&name_like='+name_like);
	$("#mainForm").submit();
}

function check() {
	// TODO 需要添加表单验证
	return true;
}

function goback() {
	var currentPage = $("#currentPage").val();
//	$("#name_like").val($("#name_like").val())
	var name = $("#name_like").val();
	
	location.href = $('#basePath').val() + '/ad/gobackser' +'?currentPage='+currentPage +'&name='+name;
}
