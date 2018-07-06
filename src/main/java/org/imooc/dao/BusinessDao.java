package org.imooc.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.imooc.bean.Business;
import org.imooc.bean.Collect_Business;
import org.imooc.dto.BusinessDto;

import com.sun.org.glassfish.gmbal.ParameterNames;

public interface BusinessDao {
	/**
	 * 新增
	 * @param business 商品表对�?
	 * @return 影响行数
	 */
	int insert(Business business);
    /**
     * 根据主键查询商品
     * @param business1 主键
     * @return 商品对象
     */
	List<Business> selectById(Business business1);
    
    /**
     * 根据查询条件分页查询商品列表
     * @param business 查询条件
     * @return 商品列表
     */
    List<Business> search_Business(Business business);
    
    /**
     *  根据查询条件分页查询商品列表 : 
     *  标题、副标题、描述三个过滤条件为模糊查询
     *  并且这三个过滤条件之间为或�?�的关系，用 OR 连接
     *  这三个过滤条件与其他过滤条件依然是并且关系，�? AND 连接
     * @param business 查询条件
     * @return 商品列表
     */
    List<Business> selectLike(Business business);
    
    /**
     * 更新商品的�?�统计评论星星�?�数】�?��?�统计评论�?�次数�?�，商品的�?�星级�?�用这两个字段数据计算得�?
     * @param map
     * @return
     */
    int updateStar();
    
    void updateNumber();
    
    /**
     * 收藏商品 ， 添加商品
     * @param business
     * @return
     */
	int collectbusiness(Business business);
	
	/**
	 *  根据会员的memberid查询该会员下的所有收藏的商品
	 * @param memberid
	 * @return
	 */
	List<Business> collectLikeByPage(@Param(value = "memberid") Long memberid);
	
	
	/**
	 * 根据id和miember_id查询是否有收藏过的商品
	 * @param businessDto1
	 * @return
	 */
	Business select_collectBusinessById(BusinessDto businessDto1);
	/**
	 * 根据
	 * @param businessDto
	 * @return
	 */
	int delcollectBusinessById(BusinessDto businessDto);
	
	
//void updateNumber(Map<String, Date> map);
	
	
	List<Business> getAllBusiness();
	
	
	int update_collect(@Param(value = "number") Integer number,@Param(value = "mask") Long mask , @Param(value = "memberid") Long memberid);
	
	
	List<Collect_Business> collectLikeByPage(Business businessForSelect);
	
	
	int update(Business business);
	
	int remove(Long id);
	
	//更改总星级
	void updatesv_star(Business business);
	
	List<Business> ishave(Business business);
	
	String getfilenameById(Long id);
	
	List<Collect_Business> selectCollect_BusinessByMask(Long id);

	
}