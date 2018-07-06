$(function() {
	common.showMessage($("#message").val());
});

function search(currentPage) {
	$("#currentPage").val(currentPage);
	$("#title").val($("#title").val());
	$("#mainForm").submit();
}

function addInit() {//新增初始化页面
	var title = $("#title").val();
	$("#title").val(title)
	$("#mainForm").attr("action",$("#basePath").val() + "/ad/addInit");
	$("#mainForm").submit();
}

function remove(id) {
	if(confirm("确定要删除这条广告吗？")) {
		$("#id").val(id);
		$("#mainForm").attr("action",$("#basePath").val() + "/ad/remove" );
		$("#mainForm").submit();
	}
}

function modifyInit(id) {
	$("#id").val(id);
	$("#mainForm").attr("action",$("#basePath").val() + "/ad/modifyInit");
	$("#mainForm").submit();
}
