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

function add() {
	$("#mainForm").attr("action",$("#basePath").val() + '/dic/add');
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
	
	location.href = $('#basePath').val() + '/dic/gobackser' +'?currentPage='+currentPage +'&name='+name;
}
