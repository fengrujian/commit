$(function() {//页面加载完 自动执行
	common.showMessage($("#message").val());
});

function remove(id) {
	$("#mainForm").attr("action",$("#basePath").val() + "/businesses/" + id);
	$("#mainForm").submit();
}

function search(currentPage) {
	$("#currentPage").val(currentPage);
	var currentPage = $("#currentPage").val();
	$("#mainForm").attr("action",$("#basePath").val() + '/businesses/search');
	$("#mainForm").submit();
}

function modifyInit(id) {
	$("#mainForm").attr("action",$("#basePath").val() + "/businesses/" + id);
	$("#mainForm").submit();
//	location.href = $("#basePath").val() + "/businesses/" + id;
}

function modifyInit(id) {
	$("#id").val(id);
	$("#mainForm").attr("action",$("#basePath").val() + "/businesses/modifyInit");
	$("#mainForm").submit();
}

function addInit() {//新增初始化页面
	var title = $("#title").val();
	$("#title").val(title)
	$("#mainForm").attr("action",$("#basePath").val() + "/businesses/addPage");
	$("#mainForm").submit();
}

function remove(id) {
	if(confirm("确定要删除这个商品吗？")) {
		$("#id").val(id);
		$("#mainForm").attr("action",$("#basePath").val() + "/businesses/remove" );
		$("#mainForm").submit();
	}
}
