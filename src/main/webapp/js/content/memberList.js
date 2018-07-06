$(function() {
	common.showMessage($("#message").val());
});

function search(currentPage) {
	$("#currentPage").val(currentPage);
	$("#name").val($("#name").val());
	$("#mainForm").submit();
}

function addInit() {//新增初始化页面
	var name = $("#name").val();
	$("#name").val(name)
	$("#mainForm").attr("action",$("#basePath").val() + "/member/memberdInit");
	$("#mainForm").submit();
}

function remove(id) {
	if(confirm("确定要删除这条会员吗？")) {
		$("#id").val(id);
		$("#mainForm").attr("action",$("#basePath").val() + "/member/remove" );
		$("#mainForm").submit();
	}
}

function modifyInit(id) {
	$("#id").val(id);
	$("#mainForm").attr("action",$("#basePath").val() + "/member/modifyInit");
	$("#mainForm").submit();
}

