$(function() {//页面加载完 自动执行
	common.showMessage($("#message").val());
});

function search(currentPage) {
	$("#currentPage").val(currentPage);
	$("#mainForm").submit();
}

function addInit() {//新增商家初始化页面
	$("#mainForm").attr("action",$("#basePath").val() + "/businesseser/addInit");
	$("#mainForm").submit();
}

function remove(id) {
	if(confirm("确定要删除这个商家嘛？")) {
		$("#id").val(id);
		$("#mainForm").attr("action",$("#basePath").val() + "/businesseser/remove" );
		$("#mainForm").submit();
	}
}

function modifyInit(id) {
	$("#id").val(id);
	$("#mainForm").attr("action",$("#basePath").val() + "/businesseser/modifyInit");
	$("#mainForm").submit();
}

//下商家下面   添加商品初始化页面
function addbusiness(id){
	$("#id").val(id);
	$("#mainForm").attr("action",$("#basePath").val() + "/businesseser/addPage");
	$("#mainForm").attr("method","get");
	$("#mainForm").submit();
}

//商家下添加用户
function adduser(id) {
	$("#id").val(id);
	$("#mainForm").attr("action",$("#basePath").val() + "/businesseser/addUserInit");
	$("#mainForm").submit();
}

