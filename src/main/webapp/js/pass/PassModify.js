$(function() {
	common.showMessage($("#message").val());
});

/**
 * 密码修改提交事件,使用ajax 提交  ${basePath}/users/updatepassword
 */

	 function sub(){
		 $.ajax({
				type:"post",
				url: $("#basePath").val() + "/users/updatepassword",
				dataType:"json",
				data:{"name":$("#name").val(),"oldPassword":$("#oldPassword").val(),"newPassword":$("#newPassword").val(),"newPasswordAgain":$("#newPasswordAgain").val()},
				success:function(data){
					if(data.code==1706){//修改成功
						alert(data.msg);
					}else{//修改失败提示
						$("#message_err").html(data.msg);
					}
				}
			});
	 }
		
		