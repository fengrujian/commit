package org.imooc.dto;

import org.imooc.bean.Business;
import org.imooc.bean.Collect_Business;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Collect_BusinessDto extends Collect_Business{
    
    private MultipartFile imgFile;
    private String keyword;
    private Integer mumber;
    private Integer star;
  
    
    public MultipartFile getImgFile() {
        return imgFile;
    }
    public void setImgFile(MultipartFile imgFile) {
        this.imgFile = imgFile;
    }
    public String getKeyword() {
        return keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public Integer getMumber() {
        return mumber;
    }
    public void setMumber(Integer mumber) {
        this.mumber = mumber;
    }
	public Integer getStar() {
		return star;
	}
	public void setStar(Integer star) {
		this.star = star;
	}
	
	
}