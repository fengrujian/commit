package org.imooc.dao;

import java.util.List;
import java.util.Map;

import org.imooc.bean.User;
import org.imooc.dto.UserDto;

public interface ReportDao {

	/**
	 * 按商品类别和时间段    统计订单数
	 * @param user 
	 * @return 订单数统计结果集，key值说明：
	 *                businesserName 商品类别的中文名
	 *                hour 小时数，如：01，表示凌晨1点至2点这个时间段
	 *                count 订单数量
	 */
	List<Map<String,String>> countOrder(User user);
}