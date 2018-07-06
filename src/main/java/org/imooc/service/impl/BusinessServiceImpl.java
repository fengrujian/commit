package org.imooc.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.imooc.bean.Ad;
import org.imooc.bean.Business;
import org.imooc.bean.Collect_Business;
import org.imooc.bean.Orders;
import org.imooc.bean.Page;
import org.imooc.constant.CategoryConst;
import org.imooc.dao.BusinessDao;
import org.imooc.dto.AdDto;
import org.imooc.dto.BusinessDto;
import org.imooc.dto.BusinessListDto;
import org.imooc.dto.Collect_BusinessDto;
import org.imooc.dto.Collect_BusinessListDto;
import org.imooc.service.BusinessService;
import org.imooc.service.MemberService;
import org.imooc.util.CommonUtil;
import org.imooc.util.FileUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BusinessServiceImpl implements BusinessService {

	@Resource
	private BusinessDao businessDao;

	@Resource
	private MemberService memberService;
	
	@Value("${businessImage.savePath}")
	private String savePath;


	@Value("${businessImage.url}")
	private String url;

	@Override
	public BusinessDto getById(Long id) {
		BusinessDto result = new BusinessDto();
		Business business1 =new Business();
		business1.setId(id);
		List<Business> businesslist = businessDao.selectById(business1);
		if(businesslist.size()>0){
		for(Business business:businesslist){
			BeanUtils.copyProperties(business, result);
			result.setImg(url + business.getImgFileName());
			result.setImgFileName(business.getImgFileName());
			result.setStar(this.getStar(business));
			return result;
			}
		}
		return null;
	}
	

	@Override
	public List<BusinessDto> search_Business(BusinessDto businessDto) {
		List<BusinessDto> result = new ArrayList<>();
		Business businessForSelect = new Business();
		BeanUtils.copyProperties(businessDto, businessForSelect);
		List<Business> list = businessDao.search_Business(businessForSelect);
		if(list.size()>0){
			for (Business business : list) {
				BusinessDto businessDtoTemp = new BusinessDto();
				result.add(businessDtoTemp);
				BeanUtils.copyProperties(business, businessDtoTemp);
				businessDtoTemp.setImg(url + business.getImgFileName());
				businessDtoTemp.setStar(this.getStar(business));
			}
		}
		return result;
	}
   
	@Override
	public BusinessListDto searchByPageForApi(BusinessDto businessDto) {
		BusinessListDto result = new BusinessListDto();
		// 组织查询条件
		Business businessForSelect = new Business();
		BeanUtils.copyProperties(businessDto, businessForSelect);
		//当关键字不为空时，把关键字的值分别设置到标题、副标题、描述中
		//TODO 改进做法：全文检索
		if (!CommonUtil.isEmpty(businessDto.getKeyword())) {
			businessForSelect.setTitle(businessDto.getKeyword());
			businessForSelect.setSubtitle(businessDto.getKeyword());
			businessForSelect.setDesc(businessDto.getKeyword());
		}
		// 当类别为全部(all)时，需要将类别清空，不作为过滤条件
		if (businessDto.getCategory() != null && CategoryConst.ALL.equals(businessDto.getCategory())) {
			businessForSelect.setCategory(null);
		}
		//前端app页码从0开始计算，这里需要+1
		int currentPage = businessForSelect.getPage().getCurrentPage();
		businessForSelect.getPage().setCurrentPage(currentPage + 1);
		List<Business> list = businessDao.selectLike(businessForSelect);
		//经过查询后根据page对象设置hasMore
		Page page = businessForSelect.getPage();
		result.setHasMore(page.getCurrentPage() < page.getTotalPage());
		// 对查询出的结果进行格式化
		if(list.size()>0){
			for (Business business : list) {
				BusinessDto businessDtoTemp = new BusinessDto();
				BeanUtils.copyProperties(business, businessDtoTemp);
				businessDtoTemp.setImg(url + business.getImgFileName());
				//为兼容前端mumber这个属性
				businessDtoTemp.setMumber(business.getNumber());
				businessDtoTemp.setStar(this.getStar(business));//平均星级
				business.setSv_star(this.getStar(business));
				businessDao.updatesv_star(business); //更改总星级
			}
		}
		return result;
	}
	/**
	 *  查询收藏的商品
	 */
	@Override
	public Collect_BusinessListDto CollectByPageForApi(BusinessDto businessDto, Long username) {
		Collect_BusinessListDto result = new Collect_BusinessListDto();
		//组织查询条件
		Long member_id = memberService.getIdByPhone(username);
		Business businessForSelect = new Business();
		BeanUtils.copyProperties(businessDto, businessForSelect);	
		//前端app页码从0开始计算，这里需要+1
		int currentPage = businessForSelect.getPage().getCurrentPage();
		businessForSelect.getPage().setCurrentPage(currentPage + 1);
		businessForSelect.setMemberid(member_id);
		List<Collect_Business> list_collect = businessDao.collectLikeByPage(businessForSelect);
		List<Business> list = businessDao.getAllBusiness();
		if(list_collect.size()>0){
			if(list.size()>0){
				Page page = businessForSelect.getPage();
				result.setHasMore(page.getCurrentPage() < page.getTotalPage());
				//经过查询后根据page对象设置hasMore
				for(Collect_Business business_clloect : list_collect){
					//对查询出的结果进行格式化
					for (Business business:list) {
						if(business.getId().equals(business_clloect.getMask())){
								int num=businessDao.update_collect(business.getNumber(),business_clloect.getMask(),member_id);
								if(num==1){
									Collect_BusinessDto businessDtoTemp = new Collect_BusinessDto();
									BeanUtils.copyProperties(business_clloect, businessDtoTemp);
									businessDtoTemp.setImg(url + business_clloect.getImgFileName());
									//为兼容前端mumber这个属性
									businessDtoTemp.setMumber(business.getNumber());
									businessDtoTemp.setStar(this.getStar(business_clloect));
									result.getData().add(businessDtoTemp);
								}	
						  }	
					}
				}
			}
		}
		return result;
	}
    //商品添加
	@Override
	public boolean add(BusinessDto businessDto) {
		Business business = new Business();
		BeanUtils.copyProperties(businessDto, business);
		if (businessDto.getImgFile() != null && businessDto.getImgFile().getSize() > 0) {
			try {
				//保存到了服务器本地upload文件
				String fileName = FileUtil.save(businessDto.getImgFile(), savePath);
				business.setImgFileName(fileName);
				// 默认已售数量为0
				business.setNumber(0);
				// 默认评论总次数为0
				business.setCommentTotalNum(0L);
				// 默认评论星星总数为0
				business.setStarTotalNum(0L);
				
				int num = businessDao.insert(business);
				if(num==1){
					return true;
				}
				return false;
			} catch (IllegalStateException | IOException e) {
				return false;
			}
		} else {
			return false;
		}
	}
	
	//商品的收藏
	@Override
	public boolean collectBusiness(BusinessDto businessDto) {
		Business business = new Business();
		BeanUtils.copyProperties(businessDto, business);	
		int num = businessDao.collectbusiness(business);
		if(num==1){
			return true;
		}else{
			return false;
		}	
	 }
	/**
	 * 根据id判断是否查到收藏的商品
	 * 
	 */
	@Override
	public boolean getcollectBusinessById(BusinessDto businessDto1) {
		Business business = businessDao.select_collectBusinessById(businessDto1);
		if(business!=null){
			return true;
		}else{
			return false;
		}
	}
	/**
	 *  删除已经收藏的商品
	 */
	@Override
	public boolean delcollectBusinessById(BusinessDto businessDto) {
		int num = businessDao.delcollectBusinessById(businessDto);
		if(num==1){
			return true;
		}else{
			return false;
		}
	}
	
	
	private int getStar(Collect_Business business) {
		if(business.getStarTotalNum() != null && business.getCommentTotalNum() != null && business.getCommentTotalNum() != 0) {
			return (int)(business.getStarTotalNum() / business.getCommentTotalNum());
		} else {
			return 0;
		}
	}
	
	//星级的总平均数    总星级/总评论数量
	private int getStar(Business business) {
		if(business.getStarTotalNum() != null && business.getCommentTotalNum() != null && business.getCommentTotalNum() != 0) {
			return (int)(business.getStarTotalNum() / business.getCommentTotalNum());
		} else {
			return 0;
		}
	}
	
    //修改商品
	@Override
	public boolean modify(BusinessDto dto) {
		Business business = new Business();
		BeanUtils.copyProperties(dto, business);
		String fileName = null;
		if (dto.getImgFile() != null && dto.getImgFile().getSize() > 0) {
			try {
				//把修改好的图片保存到了服务器里，并且返回图片的名称
				fileName = FileUtil.save(dto.getImgFile(), savePath);
				business.setImgFileName(fileName);
			} catch (IllegalStateException | IOException e) {
				return false;
			}
		}
		int updateCount = businessDao.update(business);
		if (updateCount != 1) {
			return false;
		}
		if (fileName != null) {
			return FileUtil.delete(savePath + dto.getImgFileName());
		}
		return true;
	}

	@Override
	public boolean remove(Long id) {
		//根据商品的id查询出来的商品文件名
		String filename = businessDao.getfilenameById(id);	
		int num = businessDao.remove(id);//删除成功返回1
		if(filename!=null){
			FileUtil.delete(savePath + filename);
		}
		if(num==1){
			return true;
		}else{
			return false;
		}
		
	}
	
	@Override
	public List<Business> ishave(Business business) {
		 List<Business> business1 = new ArrayList<Business>();
		if(business!=null){
			if(String.valueOf(business.getBusinesserId()).length()>0){
				  business1 = businessDao.ishave(business);
				  return business1;
			}
		}
		return business1;
	}

	@Override
	public List<Collect_Business> ishave(Long id) {
		List<Collect_Business>  collect_BusinessList = new ArrayList<Collect_Business>();
		if(id!=null){
			collect_BusinessList = businessDao.selectCollect_BusinessByMask(id);
			return collect_BusinessList;
		}
		return collect_BusinessList;
	}
	
	
}