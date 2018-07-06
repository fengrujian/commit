package org.imooc.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.imooc.bean.Business;
import org.imooc.bean.Businesser;
import org.imooc.bean.Collect_Business;
import org.imooc.bean.Orders;
import org.imooc.dto.BusinessDto;
import org.imooc.dto.BusinesserDto;

import com.sun.org.glassfish.gmbal.ParameterNames;

public interface BusinesserDao {

	List<Businesser> search_Businesser(Businesser condition);

	int insertbusinesser(Businesser businesser);

	boolean delbusinesser(Businesser businesser);

	Businesser getBusinesserById(int id);

	boolean updatebusinesser(Businesser businesser);



	
	
}