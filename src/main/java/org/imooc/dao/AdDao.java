package org.imooc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.imooc.bean.Ad;
import org.imooc.dto.AdDto;

public interface AdDao {
	int insert(Ad ad);
	
	public List<Ad> searchByPage(Ad condition);

	int update(Ad ad);

	Ad selectById(Long id);

	int remove(long id);//删除广告

	String getfilenameById(long id);
	
	
}

