$(function() {
	common.showMessage($("#message").val());//消息提示
	$("#mainForm").validate({
		rules : {
			"title" : "required",
			"subtitle" : "required",
			"link" : "required",
			"imgFile" : "required",			
			"desc" : "required"	,
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
			"imgFile" : "请上传图片！"
		}
	});
});

function addbusiness() {
	$("#mainForm").attr("action",$("#basePath").val() + '/businesseser/addbusiness');
	$("#mainForm").submit();
}

function goback() {
	var currentPage = $("#currentPage").val();
	var like_name = $("#like_name").val();
	var user_name = $("#user_name").val();
	location.href = $('#basePath').val() + '/businesseser/gobackser' +'?currentPage='+currentPage +'&like_name='+like_name+'&user_name='+user_name ;

}
