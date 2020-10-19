package com.jn.primiary.commons;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class StringUtils {
	private char[] specialCode = {'\r','\n',' ','/','\\','~','!','@','#','$','%','^','&','*','(',')','+'};

	/**
	 * @param <T> 
	* @Title: listConvertToString
	* @Description: 将List转换为以指定符号分隔的字符串 
	* @param list
	* @param separator
	* @return
	* @throws 
	*/
	public static <T> String listConvertToString(List<T> list, String separator) {
		StringBuilder result = new StringBuilder("");
		int i = 0;
		for(T it: list) {
			if(i>0) {
				result.append(separator).append(it);
			} else {
				result.append(it);
			}
			i++;
		}
		return result.toString();
	}
	
	/** 
	* @Title: getStringStream
	* @Description: 字符串转化为输入流
	* @param sInputString
	* @return
	* @throws 
	*/
	public static InputStream getStringStream(String sInputString) {
		if (sInputString != null && !sInputString.trim().equals("")) {
			try {
				ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(
						sInputString.getBytes());
				return tInputStringStream;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	/** 
	* @Title: getStreamString
	* @Description: 输入流转化为字符串 
	* @param tInputStream
	* @return
	* @throws 
	*/
	public static String getStreamString(InputStream tInputStream) {
		if (tInputStream != null) {
			try {
				BufferedReader tBufferedReader = new BufferedReader(
						new InputStreamReader(tInputStream));
				StringBuffer tStringBuffer = new StringBuffer();
				String sTempOneLine = new String("");
				while ((sTempOneLine = tBufferedReader.readLine()) != null) {
					tStringBuffer.append(sTempOneLine);
				}
				return tStringBuffer.toString();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	public static int getOccTimes(String input, String target) {
		
		int start = input.indexOf(target);
		int times = 0;
		while (start >-1 ) {
			times ++;
			input = input.substring(start+1);
			start = input.indexOf(target);
		}
		
		return times;
		
	}
	
	public static String[] getAccesConstraintsArray(String accessConstraints) {
		
		String [] accessConstraintsArray = accessConstraints.split(";");
		if (accessConstraintsArray.length == 4)
			return accessConstraintsArray;
		String [] resultAccessConstraintsArray = new String[4];
		int indexquery = accessConstraints.indexOf(";");
//		System.out.println("indexquery:"+indexquery);
		String query = accessConstraints.substring(0,indexquery);
//		System.out.println("query:" + query);
		String afterquery = accessConstraints.substring(indexquery+1);
//		System.out.println(afterquery);
		
		int indexadd = afterquery.indexOf(";");
		String add = afterquery.substring(0,indexadd);
//		System.out.println("add:" + add );
		String afteradd = afterquery.substring(indexadd+1);
//		System.out.println(afteradd);
		

		int indexupdate = afteradd.indexOf(";");
		String update = afteradd.substring(0,indexupdate);
//		System.out.println("update:" + update );
		String afterupdate = afteradd.substring(indexupdate+1);
//		System.out.println(afterupdate);
		
		String delete = afterupdate;
//		System.out.println("delete:" + delete);
		
		resultAccessConstraintsArray[0] = query;
		resultAccessConstraintsArray[1] = add;
		resultAccessConstraintsArray[2] = update;
		resultAccessConstraintsArray[3] = delete;	
		return resultAccessConstraintsArray;
	}
	
	public static String [] getSplitString(String input, String target) {
		
		int start = 0;
		int end = input.indexOf(target);
		//int times = 0;
		List<String> resultList = new ArrayList<String>(); 
		String tmpString = "";
		if (end > start)
			tmpString = input.substring(start, end);
		else if (end == start)
			tmpString = "null";
		else if (end == -1)
			tmpString = input;
		resultList.add(tmpString);
		
		// 处理空字符串的情况
		if ( end == -1)
			return resultList.toArray(new String[0]);
		
		while ( end > -1  ) {
			//times ++;
			input = input.substring(end+1);
			start = 0;
			end = input.indexOf(target);
			if (end == -1)
				break;
			if (end > start)
				tmpString = input.substring(start, end);
			else 
				tmpString = "null";
			resultList.add(tmpString);
		}
		
		// 最后一个分组
		if (input.length() > 0)
			resultList.add(input);
		else
			resultList.add("null");
		
		
		return resultList.toArray(new String[0]);
		
	}
	
	public static String [] getCleanArray (String [] input) {
		
		List<String> tmpList = new ArrayList<String> ();
		for (int i=0; i<input.length; i++) {
			if ( !input[i].equals("null") ) {
				tmpList.add(input[i]);
			}
		}
		
		if ( tmpList.size() > 0 )
			return tmpList.toArray(new String[0]);
		else 
			return null;
	}
	
	public static boolean isNotBlank(String str) {
		if(null == str || str.trim().isEmpty()) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public static boolean isBlank(String str) {
		if(null == str || str.trim().isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean isEmpty(String str) {
		if(null == str || str.trim().isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static String removeEnd(String str, String endStr) {
		if(str.endsWith(endStr)) {
			str = str.substring(0, str.length()-endStr.length());
		}
		return str;
	}

	/**
	 * 标准文档，字段规范
	 * @param str
	 * @return
	 */
	public static String formateFieldCode(String str){
		String strtemp = str;
		strtemp = org.apache.commons.lang.StringUtils.replace(strtemp, "\r", "");
		strtemp = org.apache.commons.lang.StringUtils.replace(strtemp, "\n", "");
		strtemp = org.apache.commons.lang.StringUtils.replace(strtemp, " ", "");
		strtemp = org.apache.commons.lang.StringUtils.replace(strtemp, "/", "");
		strtemp = org.apache.commons.lang.StringUtils.replace(strtemp, "\\", "");
		strtemp = org.apache.commons.lang.StringUtils.replace(strtemp, "\t", "");
		strtemp = strtemp.replace("\r\n","");
//
		return strtemp;
	}
	
}
