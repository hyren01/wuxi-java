package com.jn.primiary.beyondsoft.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import com.jn.primiary.beyondsoft.entity.FieldInfo;

public class ConnUtil {

	/**
	 *  根据数据库连接信息获取数据库连接
	 *  需要根据前台发送过来的内容进行针对性修改
	 * */
	public static Connection getConnection(String driver, String url, String insName, String userName, String passWord) {
		Connection conn = null;
		try {
			Class.forName(driver);
			//对odps做特殊处理
			if(url.contains("jdbc:odps")) {
				Properties config=new Properties();
				config.put("access_id", userName);
				config.put("access_key", passWord);
				config.put("project_name", insName);
				config.put("charset", "utf-8");
				conn = DriverManager.getConnection(url,config);
			}else {
				conn = DriverManager.getConnection(url, userName, passWord);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 根据数据库连接获取所有表名
	 * */
	public static List<String> getAllTables(Connection conn){
		List<String> tableNames = new ArrayList<String>();
		try {
			DatabaseMetaData dbmd = conn.getMetaData();
			String schemaPattern = "%";
			ResultSet tableSet = dbmd.getTables(null, schemaPattern, "%", new String[] { "TABLE" });
			while(tableSet.next()) {
				String tableName = tableSet.getString("TABLE_NAME");
				tableNames.add(tableName);
			}
			tableSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableNames;
	}
	
	/**
	 * 根据数据库连接和表名获取表中字段信息
	 * */
	public static List<FieldInfo> getAllFidldsByCondition(Connection conn, String tableName){
		List<FieldInfo> fieldList = new ArrayList<>();
		try {
			DatabaseMetaData dbmd = conn.getMetaData();
			String schemaPattern = "%";
			ResultSet columnSet = dbmd.getColumns(null, schemaPattern, tableName, "%");
			while(columnSet.next()) {
				String colName = columnSet.getString("COLUMN_NAME");		//列名
				String typeName = columnSet.getString("TYPE_NAME");			//类型名称
				int precision = columnSet.getInt("COLUMN_SIZE");			//长度
				int isNull = columnSet.getInt("NULLABLE");					//是否为空
				String isRequired = isNull == 1?"o":"O";
				int dataType = columnSet.getInt("DATA_TYPE");				//类型(java.sql.Types)
				int scale = columnSet.getInt("DECIMAL_DIGITS");				//小数的位数
				String columnType = getColType(dataType, typeName, precision, scale);
				FieldInfo fieldInfo = new FieldInfo(columnType, colName, typeName, precision, isNull, dataType, scale,isRequired);
				fieldList.add(fieldInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fieldList;
	}
	
	/**
	 *   获取列数据类型，格式为：类型(长度,精度)
	 * @param dataType {@link Integer} sql.Types的数据类型
	 * @param typeName {@link String} 数据类型
	 * @param precision {@link String} 数据长度
	 * @param scale {@link String} 数据精度
	 * @return
	 */
	private static String getColType(int dataType, String typeName, int precision, int scale) {
		//TODO 各数据库不同遇到在修改
		typeName = StringUtils.replace(typeName, "UNSIGNED", "");
		//考虑到有些类型在数据库中获取到就会带有(),同时还能获取到数据的长度，修改方式为如果本事类型带有长度，去掉长度，使用数据库读取的长度
		if( precision != 0 ) {
			int ic = typeName.indexOf("(");
			if( ic != -1 ) {
				typeName = typeName.substring(0, ic);
			}
		}
		String column_type = "";
		if( Types.INTEGER == dataType || Types.TINYINT == dataType || Types.SMALLINT == dataType || Types.BIGINT == dataType ) {
			column_type = typeName;
		}
		else if( Types.NUMERIC == dataType || Types.FLOAT == dataType || Types.DOUBLE == dataType || Types.DECIMAL == dataType ) {
			//1、当一个数的整数部分的长度 > p-s 时，Oracle就会报错
			//2、当一个数的小数部分的长度 > s 时，Oracle就会舍入。
			//3、当s(scale)为负数时，Oracle就对小数点左边的s个数字进行舍入。
			//4、当s > p 时, p表示小数点后第s位向左最多可以有多少位数字，如果大于p则Oracle报错，小数点后s位向右的数字被舍入
			if( precision > precision - Math.abs(scale) || scale > precision|| precision == 0) {
				precision = 38;
				scale = 12;
			}
			column_type = typeName + "(" + precision + "," + scale + ")";
		}
		else {
			if("char".equalsIgnoreCase(typeName) && precision >255) {
				typeName = "varchar";
			}
			column_type = typeName + "(" + precision + ")";
		}
		return column_type;
	}

}
