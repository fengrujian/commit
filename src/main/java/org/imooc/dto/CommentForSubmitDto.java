package org.imooc.dto;

import org.imooc.bean.Comment;

public class CommentForSubmitDto extends Comment{

    // 订单表主�?
    private Long id;
    // 提交的评论内�?
    private String comment;
    // 提交的评价星�?
    private Integer star;
    // 登录成功后的token
    private String token;
    // 会员手机�?
    private Long username;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Long getUsername() {
        return username;
    }
    public void setUsername(Long username) {
        this.username = username;
    }
}