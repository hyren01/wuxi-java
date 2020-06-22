package com.jn.primiary.office;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;

import com.jn.primiary.metadata.utils.CommonUtil;
import com.jn.primiary.office.entity.DocFieldCheckResult;
import com.jn.primiary.office.entity.DocModel;
import com.jn.primiary.office.entity.DocModelField;



@Service
public class OfficeTextParser  {
	public static final String MIME_TYPE = "application/poi-3.12";

	

	
    private static int name=1;
	private static int enname=2;
	private static int code=3;
	private static int defination=4;
	private static int type=5;
	private static int field_range=6;
	private static int required=7;
	private static int max_contains=8;
	private static int comments=9;
	private static int max_size=10;
	
	
	 private static int seqid=0;
	 private static int cname=1;
	 private static int ename=2;
	 private static int shortname=3;
	 private static int checkdetail=10;
	 private static int checkstdname=11;	
	 private static int stdproperty=12;	
	 private static int checkresult=13;	
	
	
	private List<DocModel> ParserDoc(String inputfile,String stdname) throws IOException{
		String stdgl="";
		String strtemp="";
		int iCountModel=0;
		int iCountField=0;
		
		List<DocModel> listModel=new LinkedList<DocModel>();
		
		FileInputStream in=new FileInputStream(inputfile);
		HWPFDocument hwpfdoc=new HWPFDocument(in);
		
		DocModel JXSigRecSimmodel=new DocModel();
		
		Range range=hwpfdoc.getRange();
		
		TableIterator it=new TableIterator(range);
		
		while(it.hasNext()){
			Table table=it.next();			
			TableRow row=table.getRow(0);
			TableCell cell=null;
			if(row.numCells()!=11){
				continue;
			}
			if((!row.getCell(0).text().trim().equals("序号"))&&(!row.getCell(1).text().trim().equals("中文名称"))&&(!row.getCell(2).text().trim().equals("英文名称")))
				continue;
			
			DocModel model=new DocModel();
			//读表的第二行获取model信息
			row=table.getRow(1);
			iCountModel++;
			//System.out.println("正在处理表"+row.getCell(code).text().trim());
			
			
		    	
			model.setId(CommonUtil.getUUID());	
			
			model.setStdsource(stdname);
			
			
			strtemp=row.getCell(code).text().trim();
			strtemp=strtemp.replace("\r", "");
			strtemp=strtemp.replace("\n", "");
			strtemp=strtemp.replace(" ", "");
			strtemp=strtemp.replace("/", "");
			strtemp=strtemp.replace("\\", "");
			model.setCode(strtemp);	
			model.setEnName(row.getCell(enname).text().trim());	
			model.setName(row.getCell(name).text().trim());
			model.setDescription(row.getCell(defination).text().trim());
			model.setCreateTime(Calendar.getInstance().getTime());
			model.setRegisterStatus("未注册");
		    List<DocModelField> listfield=new LinkedList<DocModelField>();
			
			for(int irow=2;irow<table.numRows();irow++){				
				row=table.getRow(irow);
				
				if(row.numCells()!=11){
					continue;
				}
				
				//System.out.println("正在处理行"+row.getCell(code).text().trim());
				DocModelField field=new DocModelField();
				
				if(irow==2)//每次都在最前面加一行GID数据 
				{
					DocModelField tempfield=new DocModelField();
					tempfield.setId(CommonUtil.getUUID());
					tempfield.setModel(model);
					tempfield.setCode("GID");
					tempfield.setEnName("GID");
					tempfield.setName("全局ID");
					tempfield.setType("字符串");
					tempfield.setRequired(true);
					tempfield.setMaxsize(64);
					tempfield.setFieldcheckresult(null);
					listfield.add(tempfield);
				}
				
				iCountField++;
				field.setId(CommonUtil.getUUID());
				field.setModel(model);
				
				
				strtemp=row.getCell(code).text().trim();
				strtemp=strtemp.replace("\r", "");
				strtemp=strtemp.replace("\n", "");
				strtemp=strtemp.replace(" ", "");
				strtemp=strtemp.replace("/", "");
				strtemp=strtemp.replace("\\", "");
				field.setCode(strtemp);
				
				strtemp=row.getCell(enname).text().trim();
				strtemp=strtemp.replace("\r", "");
				strtemp=strtemp.replace("\n", "");
				strtemp=strtemp.replace(" ", "");
				field.setEnName(strtemp);
				
				strtemp=row.getCell(name).text().trim();
				strtemp=strtemp.replace("\r", "");
				strtemp=strtemp.replace("\n", "");
				strtemp=strtemp.replace(" ", "");
				field.setName(strtemp);
				/*
				field.setCode(row.getCell(code).text().trim());	
				field.setEnName(row.getCell(enname).text().trim());	
				field.setName(row.getCell(name).text().trim());
				*/
				field.setDefination(row.getCell(defination).text().trim());
				field.setType(row.getCell(type).text().trim());
				field.setRange(row.getCell(field_range).text().trim());
				
				if(row.getCell(required).text().trim().equalsIgnoreCase("M")){
					field.setRequired(true);
				}else if(row.getCell(required).text().trim().equalsIgnoreCase("O")){
					field.setRequired(false);
				}
				field.setComments(row.getCell(comments).text().trim());
				if(0!=row.getCell(max_size).text().trim().length()){
					field.setMaxsize(Integer.parseInt(row.getCell(max_size).text().trim()));
				}
				else{
					if(row.getCell(type).text().trim().equals("字符串"))
						field.setMaxsize(513);//设一个奇怪的值为了以后找到这些 没有定义长度的字符串
						
					
				}
				
				
				field.setFieldcheckresult(null);
				
				listfield.add(field);
				
			   
			}
			
			model.setFields(listfield);
			
			int icount=1;
			
			//处理field字段code重复的情况
			for(int i=0;i<listfield.size();i++){
				strtemp=listfield.get(i).getCode().trim();
				for(int j=0;j<listfield.size();j++){
					if(j==i)
						continue;
					if(listfield.get(j).getCode().trim().equalsIgnoreCase(strtemp)){
						System.out.println(model.getEnName()+"  "+strtemp);
						//listfield.get(j).setCode(listfield.get(j).getCode().trim()+icount);//重复的改名
						listfield.remove(j);//移除重复的字段
						icount++;
					}
				}
			}
			
			
			
		
			//针对jx标准单独的处理，将共性表的字段加入其他的表中，且不将共性表保存
			if(model.getStdsource().contains("Y5-2018")){
				if(model.getCode().equalsIgnoreCase("JXSigRecSim")){
					JXSigRecSimmodel=model;
					
				}else{
					model.getFields().addAll(JXSigRecSimmodel.getFields());
					listModel.add(model);
				}
			}else{
				listModel.add(model);
			}
			
		}
		in.close();
		System.out.println("一共解析出model"+iCountModel+"条，field"+iCountField+"条");
		return listModel;
		/*
		System.out.println(stdgl);
		
		FileOutputStream out=new FileOutputStream("1.txt");
		out.write(stdgl.getBytes("UTF-8"));
		out.close();
		*/
		
	}
	
	
	public DocModel ParserTempDoc(String inputfile) throws IOException {
		
		DocModel model=new DocModel();
		List<DocModelField> listfield=new LinkedList<DocModelField>();
		
		FileInputStream in=new FileInputStream(inputfile);
		XWPFDocument xwpfdoc=new XWPFDocument(in);
		Iterator<XWPFTable> it=xwpfdoc.getTablesIterator();
		while(it.hasNext()){
			XWPFTable table=it.next();
			
			model.setId(CommonUtil.getUUID());		
			model.setStdsource("");			
			model.setCode("RecVoi");	
			model.setEnName("RecVoice");	
			model.setName("话音报文信息表");
			model.setDescription("");
			model.setCreateTime(Calendar.getInstance().getTime());
			model.setRegisterStatus("未注册");
			
			
			for(int irow=1;irow<table.getNumberOfRows();irow++){
				DocModelField field=new DocModelField();
				field.setId(CommonUtil.getUUID());
				XWPFTableRow row=table.getRow(irow);
				
				
				field.setModel(model);
				field.setCode(row.getCell(5).getText().trim().toLowerCase());
				field.setEnName(row.getCell(2).getText().trim());
				field.setName(row.getCell(1).getText().trim());
				field.setType(row.getCell(3).getText().trim());
				field.setRequired(true);
				String strsize=row.getCell(4).getText().trim();
				int iszie=Integer.parseInt(strsize);
				field.setMaxsize(iszie);
				field.setFieldcheckresult(null);
				listfield.add(field);
			}
		}
		model.setFields(listfield);
		return model;
	}
	
	
	private List<DocModel> ParserDocx(String inputfile,String stdname) throws IOException {
		int iCountModel=0;
		int iCountField=0;
		String strtemp="";
		List<DocModel> listModel=new LinkedList<DocModel>();
		DocModel JXSigRecSimmodel=new DocModel();
		FileInputStream in=new FileInputStream(inputfile);
		XWPFDocument xwpfdoc=new XWPFDocument(in);
		Iterator<XWPFTable> it=xwpfdoc.getTablesIterator();
		while(it.hasNext()){
			XWPFTable table=it.next();
			XWPFTableRow temprow=table.getRow(0);
			if(temprow.getTableCells().size()!=10){
				continue;
			}
			if((!temprow.getCell(0).getText().contains("序号"))&&(!temprow.getCell(1).getText().contains("中文名称"))&&(!temprow.getCell(2).getText().contains("英文名称")))
				continue;
			DocModel model=new DocModel();
			iCountModel++;
			XWPFTableRow row=table.getRow(1);
			
			
			model.setId(CommonUtil.getUUID());		
			model.setStdsource(stdname);
			strtemp=row.getCell(code).getText().trim();
			strtemp=strtemp.replace("\r", "");
			strtemp=strtemp.replace("\n", "");
			strtemp=strtemp.replace(" ", "");
			strtemp=strtemp.replace("/", "");
			strtemp=strtemp.replace("\\", "");
			
			
			model.setCode(strtemp);	
			model.setEnName(row.getCell(enname).getText().trim());	
			model.setName(row.getCell(name).getText().trim());
			model.setDescription(row.getCell(defination).getText().trim());
			model.setCreateTime(Calendar.getInstance().getTime());
			model.setRegisterStatus("未注册");
			
			List<DocModelField> listfield=new LinkedList<DocModelField>();
			
			
	
			
			//针对jx标准单独的处理，将共性表的字段加入其他的表中，且不将共性表保存
			if(model.getStdsource().contains("Y5-2018")){
				if(model.getCode().equalsIgnoreCase("JXSigRecSim")){
					
				}else{
					for(int i=0;i<JXSigRecSimmodel.getFields().size();i++){
						DocModelField tmpfield=new DocModelField();
						DocModelField modelfield=JXSigRecSimmodel.getFields().get(i);
						tmpfield.setId(CommonUtil.getUUID());
						tmpfield.setModel(model);
						tmpfield.setCode(modelfield.getCode());
						tmpfield.setEnName(modelfield.getEnName());
						tmpfield.setName(modelfield.getName());
						tmpfield.setType(modelfield.getType());
						tmpfield.setRequired(modelfield.isRequired());
						tmpfield.setMaxsize(modelfield.getMaxsize());
						tmpfield.setFieldcheckresult(modelfield.getFieldcheckresult());
						
						listfield.add(tmpfield);
					}	
				}
			}
			for(int irow=2;irow<table.getNumberOfRows();irow++){
				row=table.getRow(irow);
				if(row.getTableCells().size()!=10){
					continue;
				}
				DocModelField field=new DocModelField();
				if(irow==2)//每次都在最前面加一行GID数据 
				{
					DocModelField tempfield=new DocModelField();
					tempfield.setId(CommonUtil.getUUID());
					tempfield.setModel(model);
					tempfield.setCode("GID");
					tempfield.setEnName("GID");
					tempfield.setName("全局ID");
					tempfield.setType("字符串");
					tempfield.setRequired(true);
					tempfield.setMaxsize(64);
					tempfield.setFieldcheckresult(null);
					listfield.add(tempfield);
				}
				
				
				
				iCountField++;
				field.setId(CommonUtil.getUUID());
				field.setModel(model);
				
				
				strtemp=row.getCell(code).getText().trim();
				strtemp=strtemp.replace("\r", "");
				strtemp=strtemp.replace("\n", "");
				strtemp=strtemp.replace(" ", "");
				strtemp=strtemp.replace("/", "");
				strtemp=strtemp.replace("\\", "");
				
				field.setCode(strtemp);
				
				strtemp=row.getCell(enname).getText().trim();
				strtemp=strtemp.replace("\r", "");
				strtemp=strtemp.replace("\n", "");
				strtemp=strtemp.replace(" ", "");
				field.setEnName(strtemp);
				
				strtemp=row.getCell(name).getText().trim();
				strtemp=strtemp.replace("\r", "");
				strtemp=strtemp.replace("\n", "");
				strtemp=strtemp.replace(" ", "");
				field.setName(strtemp);
				
				/*
				field.setCode(row.getCell(code).getText().trim());	
				field.setEnName(row.getCell(enname).getText().trim());	
				field.setName(row.getCell(name).getText().trim());
				*/
				field.setDefination(row.getCell(defination).getText().trim());
				field.setType(row.getCell(type).getText().trim());
				field.setRange(row.getCell(field_range).getText().trim());
				if(row.getCell(required).getText().trim().equalsIgnoreCase("M")){
					field.setRequired(true);
				}else if(row.getCell(required).getText().trim().equalsIgnoreCase("O")){
					field.setRequired(false);
				}
				field.setComments(row.getCell(comments).getText().trim());
				//int ilen=row.getCell(max_size).getText().trim().length();
				
				/*
				if(0!=row.getCell(max_size).getText().trim().length()){
					field.setMaxsize(Integer.parseInt(row.getCell(max_size).getText().trim()));
				}
				else{
					if(row.getCell(type).getText().trim().equals("字符串"))
						field.setMaxsize(513);//设一个奇怪的值为了以后找到这些 没有定义长度的字符串					
				}
			    */
				field.setMaxsize(getSizeNum(row.getCell(field_range).getText().trim()));
				field.setFieldcheckresult(null);
				
				listfield.add(field);
				
			   
			}
			
			model.setFields(listfield);
			
			for(int i=0;i<listfield.size();i++){
				int icount=1;				
				strtemp=listfield.get(i).getCode().trim();
				for(int j=0;j<listfield.size();j++){
					if(j==i)
						continue;
					if(listfield.get(j).getCode().trim().equalsIgnoreCase(strtemp)){
						System.out.println(model.getEnName()+"  "+strtemp);
						//listfield.get(j).setCode(listfield.get(j).getCode().trim()+icount);//重复的改名
						listfield.remove(j);//移除重复的字段
						icount++;
					}
				}
			}
			
			
			//针对jx标准单独的处理，将共性表的字段加入其他的表中，且不将共性表保存
			if(model.getStdsource().contains("Y5-2018")){
				if(model.getCode().equalsIgnoreCase("JXSigRecSim")){
					JXSigRecSimmodel=model;
					for(int i=0;i<JXSigRecSimmodel.getFields().size();i++){
						JXSigRecSimmodel.getFields().get(i).setModel(null);
					}
					
				}else{
					
					listModel.add(model);
				}
			}else{
				listModel.add(model);
			}
		}
		in.close();
		
		System.out.println("一共解析出model"+iCountModel+"条，field"+iCountField+"条");
		
		return listModel;
		
		// TODO Auto-generated method stub
		
	}
	
	private int getSizeNum(String strsize){
		int  size=0;
		String strfieldsize=null;
		Pattern patternSize1 = Pattern.compile("[0-9]{1,}字");
		Matcher matcherSize1 = patternSize1.matcher(strsize);
		Pattern patternSize2 = Pattern.compile("最长[0-9]{1,}");
		Matcher matcherSize2 = patternSize1.matcher(strsize);
		if (matcherSize1.find())
		{
			strfieldsize= matcherSize1.group();
			strfieldsize=strfieldsize.replace("字", "");
			size=Integer.parseInt(strfieldsize);
		}else{
			
			System.out.println("未能找到长度说明字符串！"+strsize);
			size=-1;
			
		}
		
		return size;
	}
	
	
	
	private List<DocModel> ParserXlsx(String inputfile,String stdname) throws IOException{
		int iCountModel=0;
		int iCountField=0;
		List<DocModel> listModel=new LinkedList<DocModel>();
		FileInputStream in=new FileInputStream(inputfile);
		XSSFWorkbook xwb=new XSSFWorkbook(in);
		XSSFSheet sheet=xwb.getSheetAt(0);
		List<DocModelField> listfield=new LinkedList<DocModelField>();
		DocModel model=null;
		boolean firstrow=true;
		
		for(int irow=1;irow<sheet.getLastRowNum();irow++){
			XSSFRow row=sheet.getRow(irow);
			
			
			String str=row.getCell(seqid).toString().trim();//有三种情况如果为1则需要新开一个 model，如果是其他不为1且不为2.1这样的数则跳过，如果为 2，1类似的值则需要插入一个field
			
			
			int lastIndexOfDot = str.lastIndexOf('.');
			
			
			int Length = str.length();
			String filesuffix = str.substring(lastIndexOfDot+1, Length);
			
			if(str.equals("1.0")||str.equals("1")){
					
				if(firstrow!=true){
					model.setFields(listfield);
					listModel.add(model);						
				}
				//listfield.clear();
					
				model=new DocModel();
				firstrow=false;
				model.setId(CommonUtil.getUUID());		
				model.setStdsource(stdname);
				model.setCode(row.getCell(enname).toString().trim());	
				model.setEnName(row.getCell(shortname).toString().trim());	
				model.setName(row.getCell(cname).toString().trim());
				model.setDescription(row.getCell(defination).toString().trim());
				model.setCreateTime(Calendar.getInstance().getTime());
				model.setRegisterStatus("未注册");
			}else {
				if(filesuffix.equals("0")){
					
				}else{
			
					DocModelField field=new DocModelField();
					field.setId(CommonUtil.getUUID());
					field.setModel(model);
					
					String strtemp;
					strtemp=row.getCell(ename).toString().trim();
					strtemp=strtemp.replace("\r", "");
					field.setCode(strtemp);
					
					strtemp=row.getCell(shortname).toString().trim();
					strtemp=strtemp.replace("\r", "");
					field.setEnName(strtemp);
					
					strtemp=row.getCell(cname).toString().trim();
					strtemp=strtemp.replace("\r", "");
					field.setName(strtemp);
					
				
					field.setDefination(row.getCell(defination).toString().trim());
					field.setType(row.getCell(type).toString().trim());
					if(row.getCell(field_range) != null){
						field.setRange(row.getCell(field_range).toString().trim());
					}
					if(row.getCell(required).toString().trim().equalsIgnoreCase("M")){
						field.setRequired(true);
					}else if(row.getCell(required).toString().trim().equalsIgnoreCase("O")){
						field.setRequired(false);
					}
					if(row.getCell(comments) != null){
						field.setComments(row.getCell(comments).toString().trim());
					}
					//int ilen=row.getCell(max_size).getText().trim().length();
					
					
					listfield.add(field);
			
				}
			}
			
			
		}
		
		return listModel;
	} 
	
	
	
	private List<DocModel> ParsernewXlsx(String inputfile,String stdname) throws IOException{	
		int iCountModel=0;
	int iCountField=0;
	List<DocModel> listModel=new LinkedList<DocModel>();
	FileInputStream in=new FileInputStream(inputfile);
	XSSFWorkbook xwb=new XSSFWorkbook(in);
	XSSFSheet sheet=xwb.getSheetAt(0);
	List<DocModelField> listfield=new LinkedList<DocModelField>();
	DocModel model=null;
	boolean firstrow=true;
	int   ihasinstermodel=0;
	int   imodel=0;
	String strtemp=""; 
	
	for(int isheet=0;isheet<xwb.getNumberOfSheets();isheet++) {
	       sheet=xwb.getSheetAt(isheet);
	       System.out.printf("目前处理到"+sheet.getSheetName()+"\n");
			for(int irow=1;irow<sheet.getLastRowNum();irow++){
				XSSFRow row=sheet.getRow(irow);
				System.out.printf("目前处理到第"+irow+"行数据\n");
				
				String str=row.getCell(seqid).toString().trim();//有三种情况如果为1则需要新开一个 model，如果是其他不为1且不为2.1这样的数则跳过，如果为 2，1类似的值则需要插入一个field
				
				
				int lastIndexOfDot = str.lastIndexOf('.');
				
				
				int Length = str.length();
				String filesuffix = str.substring(lastIndexOfDot+1, Length);
				
				if(str.equals("1.0")||str.equals("1")){
						
					if(firstrow!=true){
						model.setFields(listfield);
						
						for(int i=0;i<listfield.size();i++){
							int icount=1;				
							strtemp=listfield.get(i).getCode().trim();
							for(int j=0;j<listfield.size();j++){
								if(j==i)
									continue;
								if(listfield.get(j).getCode().trim().equalsIgnoreCase(strtemp)){
									System.out.println(model.getEnName()+"  "+strtemp);
									//listfield.get(j).setCode(listfield.get(j).getCode().trim()+icount);//重复的改名
									listfield.remove(j);//移除重复的字段
									icount++;
								}
							}
						}
						
						listModel.add(model);	
						ihasinstermodel++;
					}
					//listfield.clear();
						
					model=new DocModel();
					firstrow=false;
					model.setId(CommonUtil.getUUID());		
					model.setStdsource(stdname);
					
					strtemp=row.getCell(shortname).toString().trim();
					strtemp=strtemp.replace("\r", "");
					strtemp=strtemp.replace("\n", "");
					strtemp=strtemp.replace(" ", "");
					strtemp=strtemp.replace("/", "");
					strtemp=strtemp.replace("\\", "");
					model.setCode(strtemp);	
					model.setEnName(row.getCell(enname).toString().trim());	
					if(row.getCell(cname) != null){
						model.setName(row.getCell(cname).toString().trim());
					}
					if(row.getCell(defination) != null){
					    model.setDescription(row.getCell(defination).toString().trim());
					}
					model.setCreateTime(Calendar.getInstance().getTime());
					model.setRegisterStatus("未注册");
					imodel++;
				}else {
					if(filesuffix.equals("0")){
						
					}else{
				
						DocModelField field=new DocModelField();
						DocFieldCheckResult result=new DocFieldCheckResult();
						field.setId(CommonUtil.getUUID());
						field.setModel(model);
						
						
						strtemp=row.getCell(shortname).toString().trim();
						strtemp=strtemp.replace("\r", "");
						strtemp=strtemp.replace("\n", "");
						strtemp=strtemp.replace(" ", "");
						strtemp=strtemp.replace("/", "");
						strtemp=strtemp.replace("\\", "");
						field.setCode(strtemp);
						
						strtemp=row.getCell(ename).toString().trim();
						strtemp=strtemp.replace("\r", "");
						field.setEnName(strtemp);
						
						System.out.printf("目前处理到"+strtemp+"行数据\n");
						
						if(row.getCell(cname) != null){
							strtemp=row.getCell(cname).toString().trim();
							strtemp=strtemp.replace("\r", "");
							field.setName(strtemp);
						}
					
						if(row.getCell(defination) != null){
							field.setDefination(row.getCell(defination).toString().trim());
						}
						field.setType(row.getCell(type).toString().trim());
						
						
						if(row.getCell(field_range) != null){
							field.setRange(row.getCell(field_range).toString().trim());
							String strfield=row.getCell(field_range).toString().trim();
							int ifield=0;
							
							if(strfield.contains(".")){								
								ifield=(int)Float.parseFloat(strfield);
							}else{
								ifield=Integer.parseInt(strfield);
							}
							
							field.setMaxsize(ifield);
						}
						if(row.getCell(required).toString().trim().equalsIgnoreCase("M")){
							field.setRequired(true);
						}else if(row.getCell(required).toString().trim().equalsIgnoreCase("O")){
							field.setRequired(false);
						}
						if(row.getCell(comments) != null){
							field.setComments(row.getCell(comments).toString().trim());
						}
						//int ilen=row.getCell(max_size).getText().trim().length();
						
						
						//填入核标情况信息
						result.setId(CommonUtil.getUUID());
						result.setModelfield(field);		 
						 if(row.getCell(checkdetail) != null){
							 result.setDetail(row.getCell(checkdetail).toString().trim());	
						 }
						 if(row.getCell(checkstdname) != null){
							 result.setStdname(row.getCell(checkstdname).toString().trim());	
						 }
						 if(row.getCell(stdproperty) != null){
							 result.setStdproperty(row.getCell(stdproperty).toString().trim());	
						 }
						 if(row.getCell(checkresult) != null){
							 result.setCheckresult(row.getCell(checkresult).toString().trim());	
						 }
						 
						 
						field.setFieldcheckresult(result);
						
						listfield.add(field);
				
					}
				}
				
				
			}
			
			int imodelcha=imodel-ihasinstermodel;
			if(imodelcha==1){
				model.setFields(listfield);
				for(int i=0;i<listfield.size();i++){
					int icount=1;				
					strtemp=listfield.get(i).getCode().trim();
					for(int j=0;j<listfield.size();j++){
						if(j==i)
							continue;
						if(listfield.get(j).getCode().trim().equalsIgnoreCase(strtemp)){
							System.out.println(model.getEnName()+"  "+strtemp);
							//listfield.get(j).setCode(listfield.get(j).getCode().trim()+icount);//重复的改名
							listfield.remove(j);//移除重复的字段
							icount++;
						}
					}
				}
				listModel.add(model);	
			}

	}
	
	return listModel;
} 
	
	
	
	
	private void CountnewXlsx(String inputfile) throws IOException{	
		
	FileInputStream in=new FileInputStream(inputfile);
	FileInputStream incheck=new FileInputStream("C:\\wld\\check.xlsx");
	FileOutputStream out=new FileOutputStream("C:\\wld\\checkr.xlsx");
	XSSFWorkbook xwb=new XSSFWorkbook(in);
	XSSFSheet sheet=xwb.getSheetAt(0);
	
	
	XSSFWorkbook xwbcheck=new XSSFWorkbook(incheck);
	XSSFSheet sheetcheck=xwbcheck.getSheetAt(0);
	
	boolean firstrow=true;
	int   ihasinstermodel=0;
	int   imodel=0;
	int   icount=1;
	
	
	for(int isheet=0;isheet<xwb.getNumberOfSheets();isheet++) {
	       sheet=xwb.getSheetAt(isheet);
	       //System.out.printf("目前处理到"+sheet.getSheetName()+"\n");
	       int iequal=0;
			int isame=0;
			int inotequal=0;
			int iother=0;
			for(int irow=1;irow<sheet.getLastRowNum();irow++){
				XSSFRow row=sheet.getRow(irow);
				//System.out.printf("目前处理到第"+irow+"行数据\n");
				if(row.getCell(checkresult) != null){
					String str=row.getCell(checkresult).toString().trim();
					if(str.equals("符合")){
						iequal++;
					}else if(str.equals("相似")){
						isame++;
					}else if(str.equals("不符合")){
						inotequal++;
					}else{
						iother++;
					}
				}
						
				
			}
			XSSFRow rowcheck=sheetcheck.createRow(icount);
			rowcheck.createCell(0).setCellValue(sheet.getSheetName());
			rowcheck.createCell(1).setCellValue(iequal);
			rowcheck.createCell(2).setCellValue(isame);
			rowcheck.createCell(3).setCellValue(inotequal);
			icount++;
			
			System.out.printf(sheet.getSheetName()+"表符合共计   "+iequal+"相似共计   "+isame+"不符合共计   "+inotequal+"\n");
		}
	xwbcheck.write(out);		
	out.flush();
	out.close();
			
			
		
	
	
}
	
	private void DelnewXlsx(String inputfile) throws IOException{	
		
		FileInputStream in=new FileInputStream("C:\\wld\\check.xlsx");
		FileInputStream incheck=new FileInputStream("C:\\wld\\check.xlsx");
		FileOutputStream out=new FileOutputStream("C:\\wld\\checkr.xlsx");
		XSSFWorkbook xwb=new XSSFWorkbook(in);
		XSSFSheet sheet=xwb.getSheetAt(0);
		
		
		XSSFWorkbook xwbcheck=new XSSFWorkbook(incheck);
		XSSFSheet sheetcheck=xwbcheck.getSheetAt(0);
		
		boolean firstrow=true;
		int   ihasinstermodel=0;
		int   imodel=0;
		int   icount=1;
		
		
		for(int isheet=0;isheet<xwb.getNumberOfSheets();isheet++) {
		       sheet=xwb.getSheetAt(isheet);
		       //System.out.printf("目前处理到"+sheet.getSheetName()+"\n");
		       int iequal=0;
				int isame=0;
				int inotequal=0;
				int iother=0;
				for(int irow=0;irow<=sheet.getLastRowNum();irow++){
					XSSFRow row=sheet.getRow(irow);
					//System.out.printf("目前处理到第"+irow+"行数据\n");
				
			        for(int icell=0;icell<=16;icell++){
			        if(icell==3)
			        	continue;
			        if(icell==5)
			        	continue;
			        if(icell==14)
			        	continue;
			        if(icell==15)
			        	continue;
			    	   if(row.getCell(icell)!=null){
			    		   row.getCell(icell).setCellValue("");
			    	   } 
			       }
			       
				}
					
				icount++;
				
			
			}
		xwb.write(out);		
		out.flush();
		out.close();
				
				
			
		
		
	}
	
	
	public void Maptable() throws IOException{	
		
		FileInputStream in=new FileInputStream("C:\\wld\\maptable.xlsx");

		FileOutputStream out=new FileOutputStream("C:\\wld\\maptabler.xlsx");
		XSSFWorkbook xwb=new XSSFWorkbook(in);
		XSSFSheet sheet=xwb.getSheetAt(0);
		
		
		XSSFWorkbook outxwb=new XSSFWorkbook();
		XSSFSheet outsheet=outxwb.createSheet();

		
	
		
		
		for(int isheet=0;isheet<xwb.getNumberOfSheets();isheet++) {
		       sheet=xwb.getSheetAt(isheet);
		     
		   
				
					XSSFRow row=sheet.getRow(1);
				
					XSSFRow outrow=outsheet.createRow(isheet);
			        
			    	if(row.getCell(3)!=null){
			    		outrow.createCell(0).setCellValue(row.getCell(3).toString().trim());
			    	 } 
			       
			       
			    	outrow.createCell(1).setCellValue(sheet.getSheetName());
			    	String strfile="C:\\wld\\shuju\\"+sheet.getSheetName()+".csv";
			    	
			    	File file=new File(strfile);
			    	int ilinenum=0;
			    	if(file.exists()){
			    		BufferedReader lnr=new BufferedReader(new FileReader(strfile));
			    		while(lnr.readLine()!=null){
			    			ilinenum++;
			    		}
			    		outrow.createCell(2).setCellValue(ilinenum-1);
			    	}
			
			}
		outxwb.write(out);		
		out.flush();
		out.close();
				
				
			
		
		
	}
	
	public  List<DocModel> ParserOfficeDirectory(String inputDir,int iscount) throws IOException {
		   
				List<DocModel> listmodel=new LinkedList<DocModel>();
				File dataDir=new File(inputDir);
				int total=0;
				if(dataDir.isDirectory() ){
					File[] fList=dataDir.listFiles();
					total =fList.length;
					for(int i=0;i<total;i++){
						System.out.println(fList[i].getName()+":");
						String inputFile = fList[i].getCanonicalPath();
						
						int lastIndexOfDot = inputFile.lastIndexOf('.');
						int lastIndexOf=inputFile.lastIndexOf('\\');
						
						int fileNameLength = inputFile.length();
						String filesuffix = inputFile.substring(lastIndexOfDot+1, fileNameLength);
						filesuffix=filesuffix.toLowerCase();
						String stdname=inputFile.substring(lastIndexOf+1, lastIndexOfDot);
						if(0==iscount){
							if (filesuffix.contains("docx") )
							{
								//listmodel.add(ParserDocx(inputFile));
								listmodel.addAll(ParserDocx(inputFile,stdname));
							}else if(filesuffix.contains("doc")){
								listmodel.addAll(ParserDoc(inputFile,stdname));
								//listmodel.add(ParserDoc(inputFile));
							}else if(filesuffix.contains("xlsx")){
								listmodel.addAll(ParsernewXlsx(inputFile,stdname));
						
							}else if(filesuffix.contains("xls")){
								
							}
						}else if(1==iscount){
							if (filesuffix.contains("docx") )
							{
								
							}else if(filesuffix.contains("doc")){
								
							}else if(filesuffix.contains("xlsx")){
								CountnewXlsx(inputFile);
						
							}
						}
						else if(2==iscount){
							if (filesuffix.contains("docx") )
							{
								
							}else if(filesuffix.contains("doc")){
								
							}else if(filesuffix.contains("xlsx")){
								DelnewXlsx(inputFile);
						
							}
						}
					}
				}
		
				return listmodel;
				
				
    }
   	
	
	


    /*
	public static void main(String[] args) throws IOException  {
		String sourceDir="C:\\Users\\admin\\57����׼�淶";
		ParserOfficeDirectory(sourceDir);
		System.out.println("Parsing office files has finished!");
	}
	*/
}
