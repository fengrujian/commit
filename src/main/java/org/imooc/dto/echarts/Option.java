package org.imooc.dto.echarts;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Option {
	private Legend legend; //类别  那个折线
	private XAxis xAxis;  //x坐标
	private List<Serie> series; //放集合
//	series: [
//	         {
//	             name:'邮件营销',
//	             type:'line',
//	             stack: '总量',
//	             data:[120, 132, 101, 134, 90, 230, 210]
//	         },
//	         {
//	             name:'联盟广告',
//	             type:'line',
//	             stack: '总量',
//	             data:[220, 182, 191, 234, 290, 330, 310]
//	         },
//       ]
	public Option() {
		this.legend = new Legend();
		this.xAxis = new XAxis();
		this.series = new ArrayList<>();
	}

	public Legend getLegend() {
		return legend;
	}

	public void setLegend(Legend legend) {
		this.legend = legend;
	}

	public XAxis getxAxis() {
		return xAxis;
	}

	public void setxAxis(XAxis xAxis) {
		this.xAxis = xAxis;
	}

	public List<Serie> getSeries() {
		return series;
	}

	public void setSeries(List<Serie> series) {
		this.series = series;
	}
}