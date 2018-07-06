package org.imooc.dto;

import org.imooc.bean.Ad;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
//属性为NULL 不序列化 ,就是所有的属性都不可以null，为null就不可以转为json
@JsonInclude(Include.NON_NULL)
public class AdDto extends Ad{
    private String img;//获取图片的路径
    private MultipartFile imgFile;//用于接收的图片文件的对应的属性
    
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public MultipartFile getImgFile() {
		return imgFile;
	}
	public void setImgFile(MultipartFile imgFile) {
		this.imgFile = imgFile;
	}
    
    
}
