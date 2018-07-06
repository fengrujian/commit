$(function() {
	common.showMessage($("#message").val());
   

	//单击注册
	$("#submitVal").click(function () {
//		 $("#password_md5").val($.md5($("#password").val()));		
//		 $("#password_md5_1").val($.md5($("#password_re").val()));
//		 alert("2222")
		 alert($.md5($("#oldPassword").val()))
		 $("#mainForm2").submit();
    })
	
	
	
});