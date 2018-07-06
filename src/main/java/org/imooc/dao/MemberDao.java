package org.imooc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.imooc.bean.Member;

public interface MemberDao {
    /**
     * 根据查询条件查询会员列表
     * @param member 查询条件
     * @return 会员列表
     */
    List<Member> select(Member member);
    //插入一条会员
	boolean insertmember(Member member);
    //验证姓名hi是否存在
	List<Member> selectName(Member member);
	
	String getpass(@Param(value = "username") Long username);
	
	Member getPhoneById (@Param(value = "memberId") Long memberId );
	
	
	List<Member> searchByPage(Member condition);
	
	int remove(long id);
	Member selectById(Long id);
	
	
}