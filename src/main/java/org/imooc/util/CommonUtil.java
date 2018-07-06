package org.imooc.util;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.imooc.constant.SessionKeyConst;
import org.imooc.dto.ActionDto;

/**
 * 共�?�工具类.
 */
public class CommonUtil {
	/**
	 * 方法描述：判断一个字符串是否为null或空字符串（被trim后为空字符串的也算）�?
	 * 
	 * @param str
	 *            �?要判断的字符�?
	 * @return false：不是空字符串，true：是空字符串
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		}
		return false;
	}
	/**
	 * 生成指定位数的随机整�?
	 * 
	 * @param number
	 *            位数
	 * @return 随机整数
	 */
	public static int random(int number) {  //[1,10)*10的5次方  100000---999999
		return (int) ((Math.random() * 9 + 1) * Math.pow(10, number - 1));
	}
	/**
	 * 获取UUID
	 * 
	 * @return UUID
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	/**
	 * 判断session中存放的动作dto列表中是否包含指定的url
	 * @param session
	 * @param url 
	 * @param method http动作
	 * @return true:包含，false：不包含
	 */
	public static boolean contains(HttpSession session,String url,String method) {
		Object obj = session.getAttribute(SessionKeyConst.ACTION_INFO);
		if(obj != null) {
			@SuppressWarnings("unchecked")
			List<ActionDto> dtoList = (List<ActionDto>)obj;
			for(ActionDto actionDto : dtoList) {
				if(url.equals("/auth") || url.equals("/menus")){
					return true;
				}
				//method有值但不是  post get put delete  比如a
				if(!isEmpty(actionDto.getMethod()) && !actionDto.getMethod().equals(method)) {
					continue;
				}
				//证明匹配 
				if(!url.matches(actionDto.getUrl())) {
					continue;
				}
				return true;
			}
		}
		return false;
	}
	/*
	 * 判断一个字符串是否是数字，如果是数字返回true  否则为false
	 */
	public static boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true;
		}
	  
	public static void main(String[] args) {
//	   System.out.println("/menus/0/0/0".matches("/menus/.{1,}/.{1,}/.{1,}"));
//		System.out.println(isNumeric("13649517190"));
//		
//		  System.out.println(isChinese('好'));
//	      System.out.println(isChinese('A'));  
		
//		System.out.println(strIsChinese("1"));
		
		
		        String str1 = "java判断是否为汉字"  ;
		        String str2 = "全为汉字467597###"  ;
		        String reg = "[\\u4e00-\\u9fa5]+"  ;
		        boolean result1 = str1.matches(reg);//false  
		        boolean result2 = str2.matches(reg);//true  
	}
	
	
	//判断单字符中文还是英文
	 public static boolean isChinese(char c) {
	        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
	        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
	                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
	                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
	                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
	                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
	                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
	            return true;//中文为true
	        }
	        return false;
	    }
	
	 
//	 public boolean isChinese(String str){
//		    boolean b;
//			char[] chrCharArray; //创建一个字符数组chrCharArray
//			chrCharArray = str.toCharArray(); //将字符串变量转换为字符数组
//			for(int i = 0;i<chrCharArray.length;i++){
//				
//				b=isChinese(chrCharArray[i]);
//				if(){
//					
//				}
//			}
//		 
//	 }
	 
	    public static boolean strIsChinese(String str) {  
	    	  String reg = "[\\u4e00-\\u9fa5]+"  ;
	    	  boolean result = str.matches(reg);//true
	    	  return result;
	    }
	 
	 
	    public static boolean strIsEnglish(String str) { 
	    	boolean b=str.matches("^[a-zA-Z]*");
	    	return b;  
	    }
	    
	
}