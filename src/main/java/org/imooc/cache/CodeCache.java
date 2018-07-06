package org.imooc.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * 验证码缓存，存放用户手机号与所下发的验证码
 */
public class CodeCache {
	private static CodeCache instance;//單例
	
	private Map<Long,String> codeMap;//保存手机号和验证码
	
	private CodeCache() {//必须是私有的  否则外部new CodeCache()  就会有多个实例
		codeMap = new HashMap<>();
	}
	
	public static CodeCache getInstance() {
		if(instance == null) {
			synchronized(CodeCache.class){//利用同步对象锁，其他线程不可以进入
				if (instance == null) {
					instance = new CodeCache();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 保存手机号与验证码
	 * @param phone 手机号
	 * @param code 验证码
	 * @return true：保存成功，false：保存失败，手机号已存在
	 */
	public boolean save(Long phone,String code) {
//		if(codeMap.containsKey(phone)) {  //判断codeMap集合里面是否有这个手机号
//			return false;
//		}
		codeMap.put(phone, code);
		return true;
	}
	/**
	 * 根据手机号获取验证码
	 * @param phone 手机号
	 * @return 验证码
	 */
	public String getCode(Long phone) {
		return codeMap.get(phone);
	}
}