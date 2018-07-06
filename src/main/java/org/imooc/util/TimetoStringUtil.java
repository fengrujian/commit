package org.imooc.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimetoStringUtil {
    /**
     * Date类型转String
     * @return
     */
	public static String  timeto(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=sdf.format(date);
		return time;
	}
	
	
}
