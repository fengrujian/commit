package org.imooc.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.imooc.bean.Ad;
import org.imooc.bean.Business;
import org.imooc.bean.Businesser;
import org.imooc.bean.Collect_Business;
import org.imooc.bean.Orders;
import org.imooc.bean.Page;
import org.imooc.constant.CategoryConst;
import org.imooc.dao.BusinessDao;
import org.imooc.dao.BusinesserDao;
import org.imooc.dto.AdDto;
import org.imooc.dto.BusinessDto;
import org.imooc.dto.BusinessListDto;
import org.imooc.dto.BusinesserDto;
import org.imooc.dto.Collect_BusinessDto;
import org.imooc.dto.Collect_BusinessListDto;
import org.imooc.dto.OrdersDto;
import org.imooc.service.BusinessService;
import org.imooc.service.BusinesserService;
import org.imooc.service.MemberService;
import org.imooc.util.CommonUtil;
import org.imooc.util.FileUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BusinesserServiceImpl implements BusinesserService {
   
	 @Resource
	 private BusinesserDao businesserDao;
	
	
	@Override
	public List<BusinesserDto> search_Businesser(BusinesserDto businesserDto) {
		List<BusinesserDto> result = new ArrayList<BusinesserDto>();
		Businesser condition = new Businesser();
		BeanUtils.copyProperties(businesserDto, condition);
		List<Businesser> BusinesserList = businesserDao.search_Businesser(condition);//使用mybatis的分页拦截器
		if(BusinesserList.size()>0){
			for (Businesser businesser : BusinesserList) {
				BusinesserDto BusinesserDto1 = new BusinesserDto();
				BeanUtils.copyProperties(businesser, BusinesserDto1);
				result.add(BusinesserDto1);
			}
			return result;
		}
		return result;
	}
	

	@Override
	public int insertbusinesser(Businesser businesser) {
		if(businesser!=null){
			if(businesser.getName()!=null
			&& businesser.getNumber()>0){
				int num = businesserDao.insertbusinesser(businesser);
				if(num==1){
					return num;
				}else{
					return 0;
				}
			}
		}
		return 0;
	}


	@Override
	public Boolean delbusinesser(BusinesserDto businesserDto) {
		Businesser businesser = new Businesser();
		BeanUtils.copyProperties(businesserDto, businesser);
		boolean b= businesserDao.delbusinesser(businesser);
		return b;
	}

	@Override
	public BusinesserDto getById(int id) {
		    BusinesserDto businesserDto = new BusinesserDto();
		    if(String.valueOf(id).length()>0){
		    	Businesser businesser = businesserDao.getBusinesserById(id);
		    	BeanUtils.copyProperties(businesser, businesserDto);
		    	return businesserDto;
		    }
		    return null;
	 }

    //修改商家
	@Override
	public boolean updatebusinesser(String name, int id) {
		if(name.length()>0 && String.valueOf(id).length()>0){
			Businesser businesser = new Businesser();
			businesser.setName(name);
			businesser.setId(id);
			boolean b = businesserDao.updatebusinesser(businesser);
			return b;
		}
		return false;
	}
	
	
	
}