package org.imooc.dto;

import org.imooc.bean.Ad;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
//����ΪNULL �����л� ,�������е����Զ�������null��Ϊnull�Ͳ�����תΪjson
@JsonInclude(Include.NON_NULL)
public class AdDto extends Ad{
    private String img;//��ȡͼƬ��·��
    private MultipartFile imgFile;//���ڽ��յ�ͼƬ�ļ��Ķ�Ӧ������
    
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
