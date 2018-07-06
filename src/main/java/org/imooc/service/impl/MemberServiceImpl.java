package org.imooc.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.imooc.bean.Ad;
import org.imooc.bean.Member;
import org.imooc.cache.CodeCache;
import org.imooc.cache.TokenCache;
import org.imooc.dao.MemberDao;
import org.imooc.dto.AdDto;
import org.imooc.dto.MemberDto;
import org.imooc.service.MemberService;
import org.imooc.util.FileUtil;
import org.imooc.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

	@Resource
	private MemberDao memberDao;

	private final static Logger logger = LoggerFactory
			.getLogger(MemberService.class);

	/**
	 * 验证手机号是否注册过，如果数据库的表有，就是注册过 .注册过就为true
	 */
	@Override
	public boolean exists(Long phone) {
		Member member = new Member();
		member.setPhone(phone);
		List<Member> list = memberDao.select(member);
		if(list != null && list.size() == 1){
			return true;
		}
	   return false;
	}
	/**
	 * 验证姓名是否存在
	 */
	@Override
	public boolean exists2(String name) {
		Member member = new Member();
		member.setName(name);
		List<Member> list = memberDao.selectName(member);
		if(list != null && list.size() == 1){
			return true;
		}
	   return false;
	}
	
	@Override
	public boolean saveCode(Long phone, String code) {
		// TODO 在真实环境中，改成借助第三方实现
		CodeCache codeCache = CodeCache.getInstance();//获得一个单例
		return codeCache.save(phone, MD5Util.getMD5(code));
	}

	@Override
	public boolean sendCode(Long phone, String content) {
		logger.info(phone + "|" + content);
		return true;
	}

	@Override
	public String getCode(Long phone) {
		//TODO 在真实环境中，改成借助第三方实现
		CodeCache codeCache = CodeCache.getInstance();
		return codeCache.getCode(phone);
	}

	@Override
	public void saveToken(String token, Long phone) {
		TokenCache tokenCache = TokenCache.getInstance();
		tokenCache.save(token, phone);
	}
	@Override
	public Long getPhone(String token) {
		TokenCache tokenCache = TokenCache.getInstance();
		return tokenCache.getPhone(token);
	}
	//移除用户信息
	@Override
	public void remove(String token) {
		TokenCache tokenCache = TokenCache.getInstance();
		tokenCache.remove(token);
	}
	@Override
	public Long getIdByPhone(Long phone) {
		Member member = new Member();
		member.setPhone(phone);
		List<Member> list = memberDao.select(member);
		if (list != null && list.size() == 1) {
			return list.get(0).getId();
		}
		return null;
	}
    /**
     * 会员注册
     */
	@Override
	public int insertmember(String name,Long username, String password1) {
		Member member = new Member();
		member.setName(name);
		member.setPhone(username);
		member.setPassword(password1);
		boolean b=memberDao.insertmember(member);
        if(b){
        	return 1;
        }
        return 0;
	}
	/**
	 * 根据手机号获得密码
	 */
	@Override
	public String getpassword(Long username) {
		String pass = memberDao.getpass(username);
		if(pass!=null && pass !=""){
			return pass;
		}
		return null;
	}
	
	//根据会员的id查询会员手机号
	@Override
	public Member getPhoneById(Long memberId) {
		Member member = memberDao.getPhoneById(memberId);
		if(member!=null){
			return member;
		}
		return null;
	}
	
	
	@Override
	public List<MemberDto> searchByPage(MemberDto memberDto) {
		List<MemberDto> result = new ArrayList<MemberDto>();
		Member condition = new Member();
		BeanUtils.copyProperties(memberDto, condition);
		List<Member> memberList = memberDao.searchByPage(condition);//使用mybatis的分页拦截器
		if(memberList.size()>0){
			for (Member member : memberList) {
				MemberDto memberDto1 = new MemberDto();
				BeanUtils.copyProperties(member, memberDto1);
				result.add(memberDto1);
			}
		}
		return result;
	}
	//后台维护
	@Override
	public boolean remove(long id) {
		int num = memberDao.remove(id);//删除成功返回1
		if(num==1){
			return true;
		}else{
			return false;
		}
	}
	
//	@Override
//	public MemberDto getById(Long id) {
//		MemberDto result = new MemberDto();
//		Member member = memberDao.selectById(id);
//		if(member!=null){
//			BeanUtils.copyProperties(member, result);
//		}
//		return result;
//	}
//	
	
	
}