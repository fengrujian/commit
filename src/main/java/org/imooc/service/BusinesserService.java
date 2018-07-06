package org.imooc.service;

import java.util.List;

import org.imooc.bean.Businesser;
import org.imooc.dto.BusinessDto;
import org.imooc.dto.BusinessListDto;
import org.imooc.dto.BusinesserDto;
import org.imooc.dto.Collect_BusinessListDto;
import org.imooc.dto.OrdersDto;

public interface BusinesserService {

	List<BusinesserDto> search_Businesser(BusinesserDto businesserDto);

	int insertbusinesser(Businesser businesser);

	Boolean delbusinesser(BusinesserDto businesserDto);

	BusinesserDto getById(int id);

	boolean updatebusinesser(String name, int id);

	
	
	
	
	
	
	
}
