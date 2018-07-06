package org.imooc.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.imooc.bean.Ad;
import org.imooc.bean.Businesser;
import org.imooc.bean.Dic;
import org.imooc.dao.DicDao;
import org.imooc.dto.AdDto;
import org.imooc.dto.DicDto;
import org.imooc.dto.DicDto;
import org.imooc.service.DicService;
import org.imooc.util.FileUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class DicServiceImpl implements DicService {
    
    @Resource
    private DicDao dicDao;
    
    @Override
    public List<Dic> getListByType(String type) {
    	List<Dic> list1 = new ArrayList();
    	List<Dic> list2 = new ArrayList();
    	Dic dic = new Dic();
		dic.setType(type);
		list1 =dicDao.select(dic); 
		if(list1.size()>0){
			return list1;
		}
		return list2;
		
    }

    
	@Override
	public  List<DicDto>  searchByPage(DicDto dicDto) {
		List<DicDto> result = new ArrayList<DicDto>();
		Dic condition = new Dic();
		BeanUtils.copyProperties(dicDto, condition);
		List<Dic> dicList = dicDao.searchByPage(condition);//使用mybatis的分页拦截器
		if(dicList.size()>0){
			for (Dic dic : dicList) {
				DicDto dicDto1 = new DicDto();
				BeanUtils.copyProperties(dic, dicDto1);	
				result.add(dicDto1);
			}
		}
		return result;
	
	}
	
	@Override
	public boolean add(DicDto dicDto) {
		if(dicDto!=null){
			if(dicDto.getName()!=null
			&& dicDto.getCode()!=null){
				Dic dic = new Dic();
				BeanUtils.copyProperties(dicDto, dic);	
				int num = dicDao.insertdic(dic);
				if(num==1){
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}


	@Override
	public DicDto getById(Long id) {
		DicDto result = new DicDto();
		Dic dic = dicDao.selectById(id);
		if(dic!=null){
			BeanUtils.copyProperties(dic, result);
		}
		return result;
	}


	@Override
	public boolean modify(DicDto dicDto) {

		Dic dic = new Dic();
		BeanUtils.copyProperties(dicDto, dic);
		int updateCount = dicDao.update(dic);
		if (updateCount == 1) {
			return true;
		}
		return false;
	}


	
	@Override
	public boolean remove(int id) {
		int num = dicDao.remove(id);//删除成功返回1
		if(num==1){
			return true;
		}else{
			return false;
		}
	}
	
    
}