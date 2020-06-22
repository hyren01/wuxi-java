package com.jn.primiary.office;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;

import com.jn.primiary.metadata.utils.CommonUtil;
import com.jn.primiary.office.entity.DocModel;
import com.jn.primiary.office.entity.DocModelField;



@Service
public class OfficeTextParserHuiju  {
	
	
	private class typeandsize{
		private String type;
		private int size;
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public int getSize() {
			return size;
		}
		public void setSize(int size) {
			this.size = size;
		}
		
	}
	
	public static final String MIME_TYPE = "application/poi-3.12";

	

	
    private static int ename=0;
	private static int type=1;
	private static int max_size=2;
	private static int defination=3;
	private static int required=4;
	
	
	
	
	
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
			int isize=temprow.getTableCells().size();
			if(isize!=6){
				continue;
			}
			
			
			if((!temprow.getCell(0).getText().contains("字段名"))&&(!temprow.getCell(1).getText().contains("数据类型"))&&(!temprow.getCell(2).getText().contains("长度")))
				continue;
			DocModel model=new DocModel();
			iCountModel++;
			XWPFTableRow row=table.getRow(1);
			
			
			model.setId(CommonUtil.getUUID());		
			model.setStdsource(stdname);
			strtemp=row.getCell(ename).getText().trim();
			strtemp=DeluselessChar(strtemp);
			
			System.out.printf("解析到表"+strtemp+"\n");
			
			
			model.setCode(strtemp);	
			model.setEnName(row.getCell(ename).getText().trim());	
			//model.setName(row.getCell(ename).getText().trim());
			model.setDescription(row.getCell(defination).getText().trim());
			model.setCreateTime(Calendar.getInstance().getTime());
			model.setRegisterStatus("未注册");
			
			
			
			
			List<DocModelField> listfield=new LinkedList<DocModelField>();
			
			
	
			
			
			for(int irow=2;irow<table.getNumberOfRows();irow++){
				row=table.getRow(irow);
				if(row.getTableCells().size()!=6){
					continue;
				}
				DocModelField field=new DocModelField();
				
				
				
				
				iCountField++;
				field.setId(CommonUtil.getUUID());
				field.setModel(model);
				
				
				strtemp=row.getCell(ename).getText().trim();
				strtemp=DeluselessChar(strtemp);		
				field.setCode(strtemp);
				
				System.out.printf("解析到字段"+strtemp+"\n");
				
				
				
				strtemp=row.getCell(ename).getText().trim();
				strtemp=DeluselessChar(strtemp);
				field.setEnName(strtemp);
				
				/*
				strtemp=row.getCell(name).getText().trim();
				strtemp=strtemp.replace("\r", "");
				strtemp=strtemp.replace("\n", "");
				strtemp=strtemp.replace(" ", "");
				field.setName(strtemp);
				*/
				strtemp=row.getCell(max_size).getText().trim();
				strtemp=DeluselessChar(strtemp);
				typeandsize ts=TypeAndSize(row.getCell(type).getText().trim(),strtemp);
				
				field.setDefination(row.getCell(defination).getText().trim());
				//field.setType(row.getCell(type).getText().trim());
				field.setType(ts.getType());
				//field.setRange(row.getCell(field_range).getText().trim());
				if(row.getCell(required).getText().trim().contains("是")){
					field.setRequired(true);
				}else if(row.getCell(required).getText().trim().contains("否")){
					field.setRequired(false);
				}
				//field.setComments(row.getCell(comments).getText().trim());
				//int ilen=row.getCell(max_size).getText().trim().length();
				
				/*
				strtemp=row.getCell(max_size).getText().trim();
				strtemp=DeluselessChar(strtemp);
				if(!strtemp.isEmpty()){
					field.setMaxsize(Integer.parseInt(strtemp));
				}else{
					field.setMaxsize(0);
				}
				*/
				field.setMaxsize(ts.getSize());
				
				/*
				if(0!=row.getCell(max_size).getText().trim().length()){
					field.setMaxsize(Integer.parseInt(row.getCell(max_size).getText().trim()));
				}
				else{
					if(row.getCell(type).getText().trim().equals("字符串"))
						field.setMaxsize(513);//设一个奇怪的值为了以后找到这些 没有定义长度的字符串					
				}
			    */
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
			listModel.add(model);
			
			
			
		}
		in.close();
		
		System.out.println("一共解析出model"+iCountModel+"条，field"+iCountField+"条");
		
		return listModel;
		
		// TODO Auto-generated method stub
		
	}
	
	
	private String DeluselessChar(String input){
		input=input.replace("\r", "");
		input=input.replace("\n", "");
		input=input.replace(" ", "");
		input=input.replace(" ", "");
		input=input.replace("/", "");
		input=input.replace("\\", "");
		return input;
	}
	
	
 private typeandsize TypeAndSize(String type,String strsize){
	 typeandsize ts=new typeandsize();
	 int isize=-1;
	 strsize=DeluselessChar(strsize);
		if(!strsize.isEmpty()){
			isize=Integer.parseInt(strsize);
		}else{
			isize=0;
		}
	 
	 if(type.equalsIgnoreCase("varchar")){
		 type="字符串";
	 }else if(type.equalsIgnoreCase("float")){
		 type="单精度";
		 isize=16;
	 }else if(type.equalsIgnoreCase("DOUBLE")){
		 type="双精度";
		 isize=16;
	 }else if(type.equalsIgnoreCase("INT")){
		 type="整数";
		 isize=8;
	 }else if(type.equalsIgnoreCase("TINYINT")){
		 type="整数";
		 isize=8;
	 }else if(type.equalsIgnoreCase("DATE")){
		 type="日期";
		 isize=0;
	 }else if(type.equalsIgnoreCase("TIMESTAMP")){
		 type="日期";
		 isize=0;
	 }else if(type.equalsIgnoreCase("BIGINT")){
		 type="整数";
		 isize=8;
	 }else if(type.equalsIgnoreCase("TIMESTAMP(9)")){
		 type="日期";
		 isize=0;
	 }else if(type.equalsIgnoreCase("BOOLERN")){
		 type="整数";
		 isize=0;
	 }else if(type.equalsIgnoreCase("SMALLINT")){
		 type="整数";
		 isize=8;
	 }else if(type.equalsIgnoreCase("TEXT")){
		 type="字符串";
		 isize=256;
	 }
	 
	 ts.setSize(isize);
	 ts.setType(type);
	 return ts;
 }
	
		
		
	
	public  List<DocModel> ParserOfficeDirectory(String inputDir) throws IOException {
		   
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
						
						listmodel.addAll(ParserDocx(inputFile,stdname));
						
					}
				}
		
				return listmodel;
				
				
    }
   	
	
	


   
}
