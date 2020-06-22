package com.jn.primiary.office;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * 标准文档解析类
 *
 * @author gyd
 * @date 2019年3月27日 上午10:49:36
 */

public class WordTable {
	//获取文档
	public XWPFDocument getDoc(String docName)
	{
	XWPFDocument document=null;
	try
	{
	document = new XWPFDocument(new FileInputStream(docName));
	
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return document;
	}
	/*
	//获取完整表名，并去除空白
	public  List<String> getWholeTableName(String documentContent)
	{
		//匹配模式
		Pattern pattern = Pattern.compile("\n[\\s|\t|\r]*表.*?\n");
		Matcher matcher = pattern.matcher(documentContent);
		List<String> listWholeTableName = new ArrayList<>();
		//匹配内容
		while (matcher.find())
		{
			String matchElement = matcher.group();
			//去掉空白
			String matchElementStrim = matchElement.replaceAll("\\s*|\t|\r|\n", "");
			listWholeTableName.add(matchElementStrim);
		}
		return listWholeTableName;
	}
	*/
	public  List<String> getWholeTableName(String documentContent)
	{
		//匹配模式
		Pattern pattern = Pattern.compile("\\S*属性列表");
		Matcher matcher = pattern.matcher(documentContent);
		List<String> listWholeTableName = new ArrayList<>();
		//匹配内容
		while (matcher.find())
		{
			String matchElement = matcher.group();
			//去掉空白
			String matchElementStrim = matchElement.replaceAll("\\s*|\t|\r|\n", "");
			listWholeTableName.add(matchElementStrim);
		}
		return listWholeTableName;
	}
	//取出表序号和表名
	public HashMap<String,String> getTableHashMap(String wholeTableName)
	{
		HashMap<String,String> tableHashName = new HashMap<String,String>();
		//Pattern patternTableNum = Pattern.compile("表([a-zA-Z].){0,1}[0-9]{1,3}");
		Pattern patternTableNum = Pattern.compile("[0-9]{1,}");
		Matcher matcherTableNum = patternTableNum.matcher(wholeTableName);
		while (matcherTableNum.find())
		{
			String tableNum = matcherTableNum.group();	
			String tableName = wholeTableName.substring(matcherTableNum.end(0), wholeTableName.length());
			tableHashName.put(tableNum, tableName);
		}
		return tableHashName;
	}
	
	//获取所有表
	public List<XWPFTable> getTables(XWPFDocument document)
	{
		List<XWPFTable> tables = document.getTables();
		return tables;
	}
	
	//获取内容
	public void getTableContent(List<XWPFTable> tables)
	{
		for (XWPFTable table:tables)
		{
			//获取行
			List<XWPFTableRow> rows = table.getRows();
			for (XWPFTableRow row: rows)
			{
				//获取所有单元格
				List<XWPFTableCell> tableCells = row.getTableCells();
				for (XWPFTableCell cell: tableCells)
				{
					//获取单元格内容
					String text = cell.getText();
				}
			}
		}
	}

}
