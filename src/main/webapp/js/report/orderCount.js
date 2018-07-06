$(function() {
	var myChart = echarts.init(document.getElementById('report'));
	common.ajax({
		url : $('#basePath').val() + '/orderReport/count' + '?user_name='+$("#user_name").val(),
		success : function(data) {
			var option = {
			    title: {
			        text: '商家的订单数目'
			    },
			    tooltip: {
			        trigger: 'axis'
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    toolbox: {
			        feature: {
			            saveAsImage: {}
			        }
			    },
			    xAxis: {
			        type: 'category',
			        boundaryGap: false
			    },
			    yAxis: {
			        type: 'value'
			    } 
			};
			$.extend(true,option,data);//使用$.extend方法把option和data合二为一
			myChart.setOption(option);//把option设置到myChart加载图形
		},
		type : 'GET'
	});
});

