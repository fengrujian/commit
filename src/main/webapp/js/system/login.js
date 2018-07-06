$(function() {
	common.showMessage($("#message").val());
    
	//单击登录
	$("#submit_login").click(function () {
		if($("#password").val()) {
			$("#password_md5").val($.md5($("#password").val()));
		}
		$("#mainForm").submit();
    })
    
    
});