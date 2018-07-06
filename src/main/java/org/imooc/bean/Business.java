package org.imooc.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
/*
 * 将这个注解写在类上之后，就会忽略类中不存在的字段，可以满足当前的需要。这个注解还可以指定要忽略的字段。使用方法如下：
@JsonIgnoreProperties({ "internalId", "secretKey" })
指定的字段不会被序列化和反序列化。
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Business extends BaseBean {
   
	private Long id;
    private String img;
    private String imgFileName;
    private String title;
	private String subtitle;
    private Double price;
    private Integer distance;
    private Integer number;
    private String desc;
    private String city;
    private String category;
	private Long starTotalNum;
    private Long commentTotalNum;
    private Long memberid;//会员的主键
    private Long mask;//接受商品的主键
    private Businesser businesser;//商家  
    private int sv_star;//根据平均星级降序
    private int businesserId;
    
    
	public int getBusinesserId() {
		return businesserId;
	}
	public void setBusinesserId(int businesserId) {
		this.businesserId = businesserId;
	}
	public int getSv_star() {
		return sv_star;
	}
	public void setSv_star(int sv_star) {
		this.sv_star = sv_star;
	}
	public Businesser getBusinesser() {
		return businesser;
	}
	public void setBusinesser(Businesser businesser) {
		this.businesser = businesser;
	}

	public Long getMask() {
		return mask;
	}
	public void setMask(Long mask) {
		this.mask = mask;
	}
	private Dic cityDic;
    private Dic categoryDic;

	

	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
    
    public Long getMemberid() {
		return memberid;
	}
	public void setMemberid(Long memberid) {
		this.memberid = memberid;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
    
    public Dic getCityDic() {
        return cityDic;
    }
    public void setCityDic(Dic cityDic) {
        this.cityDic = cityDic;
    }
    public Dic getCategoryDic() {
        return categoryDic;
    }
    public void setCategoryDic(Dic categoryDic) {
        this.categoryDic = categoryDic;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getImgFileName() {
        return imgFileName;
    }
    public void setImgFileName(String imgFileName) {
        this.imgFileName = imgFileName;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
//    public String getSubtitle() {
//        return subtitle;
//    }
//    public void setSubtitle(String subtitle) {
//        this.subtitle = subtitle;
//    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Integer getDistance() {
        return distance;
    }
    public void setDistance(Integer distance) {
        this.distance = distance;
    }
    public Integer getNumber() {
        return number;
    }
    public void setNumber(Integer number) {
        this.number = number;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
	public Long getStarTotalNum() {
		return starTotalNum;
	}
	public void setStarTotalNum(Long starTotalNum) {
		this.starTotalNum = starTotalNum;
	}
	public Long getCommentTotalNum() {
		return commentTotalNum;
	}
	public void setCommentTotalNum(Long commentTotalNum) {
		this.commentTotalNum = commentTotalNum;
	}
}