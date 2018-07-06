// 当前登录用户可以访问的菜单Map,一级菜单
var menuMap = {};

$(function() {
	common.ajax({
			url : $("#basePath").val() + "/session/menus",
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
//定时器每秒调用一次fnDate()
$(function(){
	window.onload=function(){setInterval(function(){fnDate();},1000);}
})
function fnDate(){                  
	var date=new Date();
	var year=date.getFullYear();//当前年份
	var month=date.getMonth();//当前月份
	var data=date.getDate();//天             
	var hours=date.getHours();//小时
	var minute=date.getMinutes();//分
	var second=date.getSeconds();//秒
	var time=year+"-"+(month+1)+"-"+data+" "+hours+":"+minute+":"+second;
	$("#time").html(time);
}
/**
 * 初始化菜单
 */
function initMenu() {
	var menuList = menuMap[0];
	$("#menuDiv").html("");
	$.each(menuList,function(i,value) {
		//value.id是父id,value.name是父菜单的名字 
		$("#menuDiv").append("<li  onclick='clickMenu(this," + value.id + ")'><a><span>" + value.name + "</span></a></li>");
	});
}
/**
 *  根据父菜单ID初始化子菜单
 */
function initSubMenu(parentId) {
	var menuList = menuMap[parentId];
	$("#subMenuDiv").html("");
	$.each(menuList,function(i,value) {
		$("#subMenuDiv").append("<h3 onclick=\"clickSubMenu(this,'" + value.url + "')\"><a>" + value.name + "</a></h3>");
	});
}
/**
 * 方法描述: 单击父菜单（页面上部菜单），初始化子菜单（即页面左部菜单）
 */
function clickMenu(element,id) {
	//将同级节点的[选中样式]的清空
	$("#menuDiv").children().attr("class","");
	//将当前单击的节点置为[选中样式]
	$(element).attr("class","on");
	//根据父id加载子菜单内容
	initSubMenu(id);
 }
/**
 * 方法描述:单击子菜单（页面左部菜单），初始化主页面
 */
function clickSubMenu(element,path) {
	// 将其他有[选中样式]的节点的样式清空
//	$("#subMenuDiv").find(".on").attr("class","");
	// 将当前单击的节点置为[选中样式]
//	$(element).children().attr("class","on");
	// 按指定地址加载主页面(iframe)
	$("#mainPage").attr("src",$("#basePath").val()+ path);
}
/**
 * 打开密码修改弹出层
 */
function openAddDiv(){
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






