package org.imooc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.imooc.bean.User;
import org.imooc.dao.ReportDao;
import org.imooc.dto.GroupDto;
import org.imooc.dto.UserDto;
import org.imooc.dto.echarts.Option;
import org.imooc.dto.echarts.Serie;
import org.imooc.service.DicService;
import org.imooc.service.GroupService;
import org.imooc.service.OrderReportService;
import org.imooc.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderReportServiceImpl implements OrderReportService {
	@Autowired
	private ReportDao reportDao;
	
	@Resource
	private UserService userService;
	
	@Resource
	private GroupService  groupService;
	
	
	@Override
	public Option count(String user_name) {
		Option option = new Option();
		//多个map{businesser_name = 火锅店,hour = 1，count=2}		
		UserDto userDto = new UserDto();
		userDto.setName(user_name);
		if(user_name!=null){
			userDto = userService.getGorudId_ByName(userDto);
		}
		GroupDto groupDto = null;
		if(userDto!=null){
			 groupDto = groupService.getById(userDto.getGroupId());
		}
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if(groupDto!=null){
			if(groupDto.getName().equals("超级管理员")){
				User user = new User();
				list = reportDao.countOrder(user);
			}else if(groupDto.getName().equals("管理员")){
				User user = new User();
				BeanUtils.copyProperties(userDto, user);
				list = reportDao.countOrder(user);
			}else{
				User user = new User();
				BeanUtils.copyProperties(userDto, user);
				list = reportDao.countOrder(user);
			}
		}
		//商家名称  为了去重,使用了TreeSet 为了有顺序  
		Set<String> businesserNameSet = new TreeSet<>();
		//商家名称+时间为KEY，数量为VALUE<businesser_name+hour,count>
		Map<String,Long> countMap = new HashMap<String,Long>();
		if(list.size()>0){
			for(Map<String, String> map : list) {
				businesserNameSet.add(map.get("businesser_name")); //添加为了去重
				countMap.put(map.get("businesser_name") + map.get("hour"), Long.valueOf(map.get("count")));
			}
		}
		//1.设置参数中线条的分类  ， 把Set<String>集合转List<String>集合
		option.getLegend().setData(new ArrayList<>(businesserNameSet));
		//设置参数的X轴坐标
		List<String> hours = new ArrayList<String>();
		for(int i = 0; i <= 23; i++) {
			hours.add(String.format("%02d", i));
		}
		//2.设置了x坐标
		option.getxAxis().setData(hours);		
		//3.设置线条的名称和数值
		for(String businesser_name : option.getLegend().getData()) {
			Serie serie = new Serie();
			serie.setName(businesser_name);
			serie.setType("line");
			for(String hour : hours) {// key=商家+hours  value= 每小时下的数量
				serie.getData().add(countMap.get(businesser_name + hour) == null ? 0 : countMap.get(businesser_name + hour));
			}
			option.getSeries().add(serie);
		}
		return option;
	}
	
	
	
	
}