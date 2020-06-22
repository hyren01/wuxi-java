package com.jn.primiary.metadata.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class CommonUtil {
	
	private static final String DATE_CHAR8_FORMAT = "yyyyMMdd";
	private static final String TIME_CHAR6_FORMAT = "HHmmss";
	
	/**
	 * 生成32位编码
	 * 
	 * @return string
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}

	/**
	 * 获取当前时间String
	 * 
	 * @return
	 * 
	 * @变更记录 2015-02-02上午09:31:48 武林林 创建
	 */
	public static String getStringDateByFormat(String format) {
		String dayStr = null;
		SimpleDateFormat df = new SimpleDateFormat(format);
		Date d = new Date();
		dayStr = df.format(d);
		return dayStr;
	}
	
	/**
	 * 
	 * getLocalDateByChar8: 获取8位字符的本地日期. <br/>
	 *
	 * @author 13616
	 * @return	8位字符的本地日期
	 * @since JDK 1.7
	 */
	public static String getLocalDateByChar8() {
		
		return CommonUtil.getStringDateByFormat(DATE_CHAR8_FORMAT);
	}
	
	/**
	 * 
	 * getLocalTimeByChar6: 获取6位字符的本地时间. <br/>
	 *
	 * @author 13616
	 * @return	6位字符的本地时间
	 * @since JDK 1.7
	 */
	public static String getLocalTimeByChar6() {
		
		return CommonUtil.getStringDateByFormat(TIME_CHAR6_FORMAT);
	}

	/**
	 * 分割字符串，把结果相加
	 * 
	 * @return
	 * 
	 * @变更记录 2015-02-02上午09:31:48 武林林 创建
	 */
	public static int setTjCount(String tjCount) {
		int sum = 0;
		if (isNotNullAndEmpty(tjCount)) {
			String[] strs = tjCount.split(",");
			if (null != strs && strs.length > 0) {
				for (String str : strs) {
					sum += Integer.parseInt(str);
				}
			}
		}
		return sum;
	}

	public static boolean isNotNullAndEmpty(String str) {
		if (null != str && !"".equals(str)) {
			return true;
		}
		return false;
	}
	/**
	 * date时间加减
	 * 
	 * @return
	 * @throws ParseException
	 * 
	 * @变更记录 2015-02-02上午09:31:48 武林林 创建
	 */
	public static String getDateAdd(String date, int day, String format)
			throws ParseException {
		String dayStr = null;
		SimpleDateFormat df = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance();

		Date d = df.parse(date);
		c.setTime(d);
		c.add(Calendar.DATE, day);
		Date temp_date = c.getTime();
		dayStr = df.format(temp_date);// 结果
		return dayStr;
	}
	/**
	 * 四舍五入 保留places小数
	 * 
	 * @param places
	 *            保留小数位数
	 * @return
	 * 
	 * @变更记录 2015-02-10上午09:31:48 武林林 创建
	 */
	public static double getDoubleKeepDecimal(int places, double number) {
		double returnNum = 0;
		returnNum = new BigDecimal(number).setScale(places,
				BigDecimal.ROUND_HALF_UP).doubleValue();
		return returnNum;
	}
	/**
	 * 选择单位 GB MB KB 四舍五入 保留2位小数
	 * 
	 * @return
	 * 
	 * @变更记录 2015-02-10上午09:31:48 武林林 创建
	 */
	public static String selectUnit(String number) {
		double douNum = Double.parseDouble(number);
		// KB
		Double kb = douNum / 1024;
		if (kb > 1024) {
			// MB
			Double mb = kb / 1024;
			if (mb > 1024) {
				// GB
				return getDoubleKeepDecimal(2, mb / 1024) + "GB";
			} else {
				return getDoubleKeepDecimal(2, mb) + "MB";
			}
		} else {
			return getDoubleKeepDecimal(2, kb) + "KB";
		}
	}
	
	/**
	 * 根据异常获取详细信息
	 * @param e
	 * @return
	 */
	public static String getErrorDetail(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		e.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}
	 
	//磁盘空间计算中 单位由字节换算成G
	public static String getLongFormString(String s) {
		if(StringUtils.isNotBlank(s))
		{
			DecimalFormat df  =  new DecimalFormat("0.00");
			BigDecimal d1 = new BigDecimal(s);
			BigDecimal d2 = new BigDecimal(1073741824);
			return df.format(d1.divide(d2));
		}
		return "0";
	}
	
	public static String getLongFormString2(long s) {
		DecimalFormat df  =  new DecimalFormat("0.00");
		BigDecimal d1 = new BigDecimal(s);
		BigDecimal d2 = new BigDecimal(1073741824);
		return df.format(d1.divide(d2));
	}
	
	public static String getNotNullString(Object obj) {
		return obj==null?"":obj.toString();
	}

	public static String changeString(String str){
		String returnStr = "";
		if(str.matches(".*[a-z].*")){
			returnStr += "\"" + str + "\"";
		} else {
			returnStr += str;
		}
		return returnStr;
	}
	
	public static String GetSqlInParamStr(String[] strList){
		boolean firstParam = true;
    	String inParamString = "(";
    	for(String param : strList) {
    		if(firstParam) {
    			inParamString += "'" + param + "'";
    			firstParam = false;
    		}
    		else {
    			inParamString += ",'" + param + "'";
    		}
    	}
    	inParamString += ")";
    	
    	return inParamString;
	}
	
	public static void main(String[] args) {
		for(int i=0; i<10; i++) {
			System.out.println(CommonUtil.getUUID());
		}
	}
	
}
