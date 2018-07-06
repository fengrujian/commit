$(function() {
	common.showMessage($("#message").val());
    // 验证信息
	$("#mainForm").validate({
		rules:{
			"name" : {
				required : true
			},
			"ch_name" : {
				required : true
			},
			"password" : {
				required : true
			},
			"password_re" : {
				required : true
			}
		},
	messages : {	
		"name" : "请输入账号！",
		"ch_name" : "请输入中文名！",
		"password" : "请输入密码！"	,
		"password_re" : "请输入重复密码！"	
	}
	
	
	});
	
	
	//单击注册
	$("#submit_register").click(function () {
		 $("#password_md5").val($.md5($("#password").val()));		
		 $("#password_md5_1").val($.md5($("#password_re").val()));	
		 $("#mainForm").submit();
    });
	
	
});