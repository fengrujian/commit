package org.imooc.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.imooc.bean.Ad;
import org.imooc.dao.AdDao;
import org.imooc.dto.AdDto;
import org.imooc.service.AdService;
import org.imooc.util.FileUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdServiceImpl implements AdService {
	@Autowired
	private AdDao adDao;
	//保存本地的路径
	@Value("${adImage.savePath}")
	private String adImageSavePath;
	//获取图片的服务器地址
	@Value("${adImage.url}")
	private String adImageUrl;
	
	@Override
	public boolean add(AdDto adDto){
//		String adImageSavePath = "";
//		try {
//			Properties prop = new Properties();
//			InputStream in = getClass().getResourceAsStream("/properties/system.properties");
//			prop.load(in);
//			adImageSavePath = prop.getProperty("adImage.savePath");
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
		Ad ad = new Ad();
		ad.setTitle(adDto.getTitle());
		ad.setLink(adDto.getLink());
		ad.setWeight(adDto.getWeight());
		if(adDto.getImgFile()!=null && adDto.getImgFile().getSize()>0){
			String filename =System.currentTimeMillis()+'_'+adDto.getImgFile().getOriginalFilename();
			File file = new File(adImageSavePath+filename);//本地路径图片文件
			File fileFolder = new File(adImageSavePath);
			try {
				if(fileFolder.exists()){//如果文件路径不存在的话 就创建
					fileFolder.mkdirs();
				}
				adDto.getImgFile().transferTo(file);//把图片文件保存到本地服务器的webapps文件里面
				ad.setImgFileName(filename);
				int num=adDao.insert(ad);//报存广告数据
				if(num!=0){
					return true;
				}else{
					return false;
				}
			} catch (IllegalStateException | IOException e) {
				return false;
			}
		}else{
			return false;//没填写文件 ，就报存失败了
		}
	}
	/**
	 * 查询广告列表
	 */
	@Override
	public List<AdDto> searchByPage(AdDto adDto) {
		List<AdDto> result = new ArrayList<AdDto>();
		Ad condition = new Ad();
		BeanUtils.copyProperties(adDto, condition);
		List<Ad> adList = adDao.searchByPage(condition);//使用mybatis的分页拦截器
		if(adList.size()>0){
			for (Ad ad : adList) {
				AdDto adDto1 = new AdDto();
				BeanUtils.copyProperties(ad, adDto1);
				adDto1.setImg(adImageUrl + ad.getImgFileName());//保存图片的保存路径
				result.add(adDto1);
			}
		}
		return result;
	}
	/**
	 * 修改
	 * @param adDto
	 * @return
	 */
	@Override
	public boolean modify(AdDto adDto) {
		Ad ad = new Ad();
		BeanUtils.copyProperties(adDto, ad);
		String fileName = null;
		if (adDto.getImgFile() != null && adDto.getImgFile().getSize() > 0) {
			try {
				fileName = FileUtil.save(adDto.getImgFile(), adImageSavePath);
				ad.setImgFileName(fileName);
			} catch (IllegalStateException | IOException e) {
				return false;
			}
		}
		int updateCount = adDao.update(ad);
		if (updateCount != 1) {
			return false;
		}
		if (fileName != null) {
			return FileUtil.delete(adImageSavePath + adDto.getImgFileName());
		}
		return true;
	}
	
	@Override
	public AdDto getById(Long id) {
		AdDto result = new AdDto();
		Ad ad = adDao.selectById(id);
		if(ad!=null){
			BeanUtils.copyProperties(ad, result);
			result.setImg(adImageUrl + ad.getImgFileName());
		}
		return result;
	}
	
	/**
	 * 根据id来删除广告L
	 */
	@Override
	public boolean remove(long id) {
		String filename = adDao.getfilenameById(id);	
		int num = adDao.remove(id);//删除成功返回1
		if(filename!=null){
			FileUtil.delete(adImageSavePath + filename);
		}
		if(num==1){
			return true;
		}else{
			return false;
		}
	}
	
	
}