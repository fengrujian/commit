package org.imooc.service;

import java.util.List;

import org.imooc.bean.Page;
import org.imooc.dto.CommentDto;
import org.imooc.dto.CommentForSubmitDto;
import org.imooc.dto.CommentListDto;

public interface CommentService {
    
    /**
     * 保存评论
     * @param commentForSubmitDto 提交的评论DTO对象
     * @return 是否保存成功：true-成功，false-失败
     */
    boolean add(CommentForSubmitDto commentForSubmitDto);
    
    /**
     * 按分页条件，根据商品ID获取商品下的评论列表dto
     * @param businessId 商品ID
     * @param page 分页对象
     * @return 评论列表dto
     */
    CommentListDto getListByBusinessId(Long businessId,Page page);
    
    
	List<CommentDto> search_comment(CommentDto commentDto);
	
	
}