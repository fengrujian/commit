/**退出系统**/
function logout(){
	if(confirm("您确定要退出本系统吗？")){
		window.location.href = $("#basePath").val()+ "/session";
	}
}
/**获得当前日期**/
function  getDate01(){
	var time = new Date();
	var myYear = time.getFullYear();
	var myMonth = time.getMonth()+1;
	var myDay = time.getDate();
	if(myMonth < 10){
		myMonth = "0" + myMonth;
	}
	document.getElementById("yue_fen").innerHTML =  myYear + "." + myMonth;
	document.getElementById("day_day").innerHTML =  myYear + "." + myMonth + "." + myDay;
}

//当前登录用户可以访问的菜单Map,一级菜单
var menuMap = {};

$(function() {
	common.ajax({
			url : $("#basePath").val() + "/session/menus", //从session里面获取
			success : function(data) {
				if(data && data.length > 0) {				
  //data {"list":[{"id":1,"name":"系统管理","url":"/"},{},{},{},{},{}]}
					$.each(data,function(i,value){//parentId为0的是父菜单
						if(!menuMap[value.parentId]) {
							//如果menuMap[value.parentId]不为空，就创建一个数组
							menuMap[value.parentId] = new Array();
						}
						//往数据后面加数据
						menuMap[value.parentId].push(value);
					});
					initMenu();
				}
			}
	 });
});

/**
 * 初始化父级菜单
 */
function initMenu() {
	var menuList = menuMap[0];
	$(".accordion").html("");//清空一级菜单 
	$.each(menuList,function(i,value) {
		//value.id是父id,value.name是父菜单的名字 
	    $(".accordion").append("<li id='"+ value.num +"' class='files' onclick='clickMenu(this,"+value.id+")' ><a href='#"+ value.num +"'>" + value.name + "</a><ul class='sub-menu'></ul> </li>");		
	});
}
/**
 * 方法描述: 单击父菜单，初始化子菜单,查询子菜单
 */
function clickMenu(element,id) {
	initSubMenu(id);
 }
/**
 *  根据父菜单ID初始化子菜单
 */
function initSubMenu(parentId) {
	var menuList = menuMap[parentId];
	$(".sub-menu").html("");//清空所有二级菜单 
	var pa = $("#basePath").val();
	$.each(menuList,function(i,value) {
		var name = $("#name").val();
		var path = pa + value.url;
		var p = pa + value.url+ "?name=" +name;
//		if(value.name=="修改密码" || value.name=="权限管理"){
		  $('.sub-menu').append("<li><a href=' " +p+ " ' target='right'>" + value.name + "</a></li>");
//		}
//		else{
//		  $('.sub-menu').append("<li><a href=' " +path+ " ' target='right'>" + value.name + "</a></li>");
//		}
			
	});
 }
/**
 * 打开密码修改弹出层
 */
function openAddDiv(){
	alert("111");
	$("#mengban").css("visibility","visible");
	$(".wishlistBox").show();
	$(".wishlistBox").find(".persongRightTit").html("&nbsp;&nbsp;修改密码");
	$("#submitVal").show();
}
/**
* 关闭密码修改弹出层
*/
function closeDiv(){
	$("#mengban").css("visibility","hidden");
	$("#oldPassword").val("");
	$("#newPassword").val("");
	$("#newPasswordAgain").val("");
	$(".wishlistBox").hide();
}
/**
 * 密码修改提交事件,使用ajax 提交  ${basePath}/users/updatepassword
 */
$(function(){
	$("#submitVal").click(function(){
		common.ajax({
			type:"post",
			url: $("#basePath").val() + "/users/updatepassword",
			dataType:"json",
			data:{"name":$("#name").val(),"oldPassword":$("#oldPassword").val(),"newPassword":$("#newPassword").val(),"newPasswordAgain":$("#newPasswordAgain").val()},
			success:function(data){
				if(data.code==1706){//修改成功
					closeDiv();//关闭密码修改弹出层
				}else{//修改失败提示
					$("#message").html(data.msg);
					$("#oldPassword").val("");
					$("#newPassword").val("");
					$("#newPasswordAgain").val("");
				}
			}
		});
	});
});
		