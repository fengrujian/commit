package org.imooc.service;

import java.util.List;

import org.imooc.bean.Business;
import org.imooc.bean.Collect_Business;
import org.imooc.dto.BusinessDto;
import org.imooc.dto.BusinessListDto;
import org.imooc.dto.Collect_BusinessListDto;

public interface BusinessService {
	
	/**
	 * 新增
	 * @param BusinessDto 商品dto对象
	 * @return 是否新增成功：true-成功;fale-失败
	 */
	boolean add(BusinessDto businessDto);
    
    /**
     * 根据主键获取商品dto
     * @param id 主键
     * @return 商品dto
     */
    BusinessDto getById(Long id);
    
    /**
     * 分页搜索商品列表
     * @param businessDto 查询条件(包含分页对象)
     * @return 商品列表
     */
    List<BusinessDto> search_Business(BusinessDto businessDto);
    
    /**
     * 分页搜索商品列表(接口专用)
     * @param businessDto 查询条件(包含分页对象)
     * @return 商品列表Dto对象
     */
    BusinessListDto searchByPageForApi(BusinessDto businessDto);
    
    /**
     * 收藏商品
     * @param businessDto
     * @return
     */
	boolean collectBusiness(BusinessDto businessDto);
	
    /**
     * 显示收藏的数据
     * @param businessDto
     * @param username
     * @return
     */
	Collect_BusinessListDto CollectByPageForApi(BusinessDto businessDto, Long username);
    
	/**
     * 根据id查询 collectBusiness
     * @param businessDto1
     * @return
     */
	boolean getcollectBusinessById(BusinessDto businessDto1);

	boolean delcollectBusinessById(BusinessDto businessDto);

	boolean modify(BusinessDto dto);

	boolean remove(Long id);

	List<Business> ishave(Business business);

	List<Collect_Business> ishave(Long id);
	
	
}
