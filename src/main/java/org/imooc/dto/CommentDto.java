package org.imooc.dto;

import org.imooc.bean.Comment;

public class CommentDto extends Comment{
    
    private String username;
    private String comment;
    private Integer star;
    private String title;//标题
    private String create_commenttime;//评论的创建时间
    

    public String getCreate_commenttime() {
		return create_commenttime;
	}

	public void setCreate_commenttime(String create_commenttime) {
		this.create_commenttime = create_commenttime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}
}