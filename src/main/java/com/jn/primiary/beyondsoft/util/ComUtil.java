package com.jn.primiary.beyondsoft.util;

import org.springframework.util.CollectionUtils;

import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.*;

public class ComUtil {

	/**
	 * 转换实体类
	 * @param list  查询出的对象
	 * @param clazz 返回页面的VO
	 * @param <T>
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> castEntity(List<Object[]> list, Class<T> clazz) throws Exception {
		List<T> returnList = new ArrayList<>();
		if (CollectionUtils.isEmpty(list)) {
			return returnList;
		}
		Object[] co = list.get(0);
		Class[] c2 = new Class[co.length];
		//确定构造方法
		for (int i = 0; i < co.length; i++) {
			if (co[i] != null) {
				c2[i] = co[i].getClass();
			} else {
				c2[i] = String.class;
			}
		}
		for (Object[] o : list) {
//			Constructor<T> constructor = clazz.getConstructor(c2);
//			Class<?>[] parameterTypes = constructor.getParameterTypes();
//			System.out.println("o.length"+o.length+",parameterTypes.length:"+parameterTypes.length);
//			for(Class<?> everypara : parameterTypes){
//				System.out.println(everypara);
//			}
			Constructor<T> constructor = clazz.getConstructor(c2);
			returnList.add(constructor.newInstance(o));
		}
		return returnList;
	}

	public static String getSysDate(Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static String getBatchNo(){
	    return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+
                (int)(new Random().nextDouble() * (9 - 1 + 1)) + 1;
    }

	public static void main(String[] args) {

		System.out.println(getBatchNo());
	}
}
