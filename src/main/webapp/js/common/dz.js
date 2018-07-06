var url  = location.href;
var MOOC_URL= url.substring(0,url.length-url.replace(/^http:\/\/[A-Za-z\.:0-9]*/,"").length);
if(MOOC_URL.indexOf("localhost")!=-1){
	MOOC_URL=MOOC_URL+"/zmooc";
}
//加载html文件进去
$(function(){
	 $("#header").load("header.html",function(){//表示加载完header.html后 在执行function()这个函数
	 $("#login_register").load("login.html",function(){//加载登陆页面，在执行登陆按钮
			 $("#js-signin-btn").click(function(){
				  refreshImg();//点击登陆按钮 重新刷新验证码
				  $(".modal-backdrop").fadeIn(100);//显示屏蔽层
				  $("#signin").attr("style","display:inline");//把登录框变为显示状态
			 });
			 $("#login_close").click(function(){//关闭登陆框
				 $(".modal-backdrop").fadeOut(100);//关闭显示层
				 $("#signin").attr("style","display:none");////把登录框变隐藏状态为状态
			 });
		 });
	 });
	 
	 $("#footer").load("footer.html");//加载底部的html
	
 });

//跳转到课程详情页面   播放页面进行播放
function toCoursedetail(courseId){
	 //先判断是否是登陆状态
	 $.ajax({
		  type:"post",
		  url:MOOC_URL+"/user/isLogin.do",
		  dataType:"json",
		  data:{},
		  success:function(data){
			 if(data.result=="1"){//是登录状态
				 alert("是登录状态");
// 				 window.lacation=MOOC_URL+"/html/course_detail.html?cid="+courseId;//在当前窗口打开
				 window.open(MOOC_URL+"/html/course_detail.html?cid="+courseId,"_blank");//在新窗口打开
			 }else{//未登录状态
				 $("#js-signin-btn").click();
			 }
		  }
	  });
}