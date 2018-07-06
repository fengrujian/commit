package org.imooc.controller.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.imooc.dto.UserDto;
import org.imooc.dto.echarts.Option;
import org.imooc.dto.echarts.Serie;
import org.imooc.service.OrderReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/orderReport")
public class OrderReportController {
	@Autowired
	private OrderReportService orderReportService;
	
	@RequestMapping
	public String index(Model model,@RequestParam("name") String name) {
		UserDto userDto = new UserDto();
		userDto.setName(name);
		model.addAttribute("user", userDto);
		return "/report/orderCount";
	}
	
	@ResponseBody
	@RequestMapping(value="/count" , method = RequestMethod.GET)
	public Option count(Model model,@RequestParam("user_name") String user_name) {
		Option option = orderReportService.count(user_name);
		//TODO演示用数据 -- start
//		String[] names = new String[]{"电影","结婚","美食"};
//		option.getLegend().setData(Arrays.asList(names));
//		Random rand = new Random();
//		List<Serie> series = new ArrayList<>();
//		for(String name : option.getLegend().getData()) {
//			Serie serie = new Serie();
//			serie.setName(name);	
//			serie.setType("line");
//			for(int i = 0; i < 24; i++) {
//			  serie.getData().add(Long.valueOf(rand.nextInt(1000)));
//			}
//			series.add(serie);
//		}
//		option.setSeries(series);
		// TODO 演示用数据 -- end
		return option;
	}
	
	
	
}