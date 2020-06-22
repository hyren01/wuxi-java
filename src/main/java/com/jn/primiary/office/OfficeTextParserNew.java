package com.jn.primiary.office;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;

import com.jn.primiary.metadata.utils.CommonUtil;
import com.jn.primiary.office.entity.DocModel;
import com.jn.primiary.office.entity.DocModelField;



@Service
public class OfficeTextParserNew  {
	public static final String MIME_TYPE = "application/poi-3.12";

	

	private static int seqid=0;
    private static int cname=1;
	private static int enname=2;
	private static int code=3;
	private static int defination=4;
	private static int type=5;
	private static int field_range=6;
	private static int required=7;
	private static int max_contains=8;
	private static int comments=9;

	private class TypeMaxSize{
		private String type;
		private int  size;
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
	
	
	
	
	
	private List<DocModel> ParserStdDocx(String inputfile,String stdname) throws IOException {
		int iCountModel=0;
		int iCountField=0;
		String strtemp;
		List<DocModel> listModel=new LinkedList<DocModel>();
		FileInputStream in=new FileInputStream(inputfile);
		XWPFDocument xwpfdoc=new XWPFDocument(in);
		Iterator<XWPFTable> it=xwpfdoc.getTablesIterator();
		
		List<DocModelField> jxgeneralfield=new LinkedList<DocModelField>();//记录JX共性元数据信息
		
		
		DocModelField field;
		while(it.hasNext()){
			XWPFTable table=it.next();
			XWPFTableRow temprow=table.getRow(0);
			boolean firstrow=false;
			
			//判断是否是需要处理的标准表 
			if(temprow.getTableCells().size()!=10){
				continue;
			}
			if((!temprow.getCell(0).getText().contains("序号"))&&(!temprow.getCell(1).getText().contains("中文名称"))&&(!temprow.getCell(2).getText().contains("英文名称"))){
				continue;		
			}
		
			temprow=table.getRow(1);//取第一行，判断一下是不是特殊的表
			if((temprow.getCell(cname).getText().contains("全局线路号"))&&(temprow.getCell(enname).getText().contains("GlobalLineNumber"))&&(temprow.getCell(code).getText().contains("GloLineNum"))){//jx标准的第一个表需要单独处理
				firstrow=true;
			
			}
			
			
			//遍历表的每一行处理数据 
			DocModel newmodel=null;
			List<DocModelField> listfield=null;
			for(int irow=1;irow<table.getNumberOfRows();irow++){
				XWPFTableRow row=table.getRow(irow);
				if(row.getTableCells().size()!=10){
					continue;
				}
				System.out.println("正在处理"+row.getCell(code).getText().trim());
				
				if(firstrow){//需要单独处理第一行
					if(!row.getCell(type).getText().trim().contains("复合型")){
						field=getNewField(row);
						jxgeneralfield.add(field); 
					}
					
				}else{
					strtemp=row.getCell(type).getText().trim();
					if(isNewModel(strtemp)){//从序号判断是不是一个新的模型
						 //新的模型
						if(newmodel!=null){
							addGIDField(listfield,newmodel);
							if(stdname.contains("JX")){
								addGeneralFields(listfield,jxgeneralfield,newmodel);
							}
								
							delRepeatField(listfield);
							newmodel.setFields(listfield);
							listModel.add(newmodel);
						}
						newmodel=getNewModel(row,stdname);
						iCountModel++;
						listfield=new LinkedList<DocModelField>();
					}else{
						//新的字段
						field=getNewField(row);
						if(newmodel!=null){
							field.setModel(newmodel);	
							iCountField++;
							listfield.add(field);
						}
					}
				}
			}
			
		
			
			if(!firstrow){
				addGIDField(listfield,newmodel);
				if(stdname.contains("JX")){
					addGeneralFields(listfield,jxgeneralfield,newmodel);
				}
				delRepeatField(listfield);
				newmodel.setFields(listfield);
				listModel.add(newmodel);
			}
	
			
		}
		in.close();
		
		//解析完成做其他的处理工作，加入GID字段以及将公共部分字段加入进去
		
		System.out.println("一共解析出model"+iCountModel+"条，field"+iCountField+"条");
		
		return listModel;
		
		// TODO Auto-generated method stub
		
	}
	
	
	private DocModelField getNewField(XWPFTableRow row){
		
		String strtemp;
		DocModelField field=new DocModelField();
		
		field.setId(CommonUtil.getUUID());
				
		strtemp=row.getCell(code).getText().trim();
		strtemp=delSpecialChar(strtemp);
		field.setCode(strtemp);
		
		strtemp=row.getCell(enname).getText().trim();
		strtemp=strtemp.replace("\r", "");
		field.setEnName(strtemp);
		
		strtemp=row.getCell(cname).getText().trim();
		strtemp=strtemp.replace("\r", "");
		field.setName(strtemp);
		
		
		field.setDefination(row.getCell(defination).getText().trim());
		
		TypeMaxSize typesize=getTypeSize(row);
		field.setType(typesize.getType());
		field.setMaxsize(typesize.getSize());
		
		
		
		field.setRange(row.getCell(field_range).getText().trim());
		if(row.getCell(required).getText().trim().equalsIgnoreCase("M")){
			field.setRequired(true);
		}else if(row.getCell(required).getText().trim().equalsIgnoreCase("O")){
			field.setRequired(false);
		}
		field.setComments(row.getCell(comments).getText().trim());
        
		
		field.setFieldcheckresult(null);
		
		
		/*
		 * 
		field.setType(row.getCell(type).getText().trim());
		if(0!=row.getCell(max_size).getText().trim().length()){
			field.setMaxsize(Integer.parseInt(row.getCell(max_size).getText().trim()));
		}
		else{
			if(row.getCell(type).getText().trim().equals("字符串"))
				field.setMaxsize(513);//设一个奇怪的值为了以后找到这些 没有定义长度的字符串					
		}
		*/
		
		return field;
	}
	
	
	private DocModel getNewModel(XWPFTableRow row,String stdname){
		String strtemp="";
		DocModel model=new DocModel();
		model.setId(CommonUtil.getUUID());		
		model.setStdsource(stdname);
		
		strtemp=row.getCell(code).getText().trim();
		strtemp=delSpecialChar(strtemp);
		if(stdname.contains("JX")){
			if(!strtemp.contains("JX")){
				strtemp="JX"+strtemp;
			}
			
		}
		model.setCode(strtemp);	
		
		model.setEnName(row.getCell(enname).getText().trim());	
		model.setName(row.getCell(cname).getText().trim());
		model.setDescription(row.getCell(defination).getText().trim());
		model.setCreateTime(Calendar.getInstance().getTime());
		model.setRegisterStatus("未注册");
		
		return model;
	}
	
	private TypeMaxSize getTypeSize(XWPFTableRow row){
		TypeMaxSize typesize=new TypeMaxSize();
		int size=-1;
		String strtype=row.getCell(type).getText().trim();
		strtype=strtype.replace("\r", "");
		String strrange=row.getCell(field_range).getText().trim();
		strrange=strrange.replace("\r", "");
		typesize.setSize(0);
		if(strtype.equals("整型")||strtype.equals("整数")){
			typesize.setType("整数");		
			typesize.setSize(4);			
		}else if(strtype.equals("长整型")){
			typesize.setType("整数");		
			typesize.setSize(8);		
		}else if(strtype.equals("浮点型")){
			typesize.setType("单精度");		
			typesize.setSize(4);		
		}else if(strtype.equals("浮点型双精度")||strtype.equals("双精度浮点型")){
			typesize.setType("双精度");		
			typesize.setSize(4);		
		}else if(strtype.equals("日期时间型")){
			typesize.setType("日期");
			typesize.setSize(0);
			if(strrange.contains("高精度时间格式")){//高精度时间改为字符串类型，长度统一设置为64
				typesize.setType("字符串");
				typesize.setSize(64);
			}
		}else if(strtype.equals("布尔型")){
			typesize.setType("整数");
			typesize.setSize(4);
		}else if(strtype.equals("字节型")||strtype.equals("字符型")){
			typesize.setType("整数");
			size=getSizeNum(strrange);
			if(-1==size){
				typesize.setSize(4);
			}else{
				typesize.setSize(size);
			}
		}else if(strtype.equals("字符串型")||strtype.equals("字符串")){
			typesize.setType("字符串");
			size=getSizeNum(strrange);
			if(-1==size){
				typesize.setSize(716);
			}else{
				typesize.setSize(size);
			}
		}else{
			System.out.println("不知道什么类型  "+strrange);
		}
		return typesize;
				
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
	
	
	private boolean isNewModel(String strseqnum){
		boolean isnew=false;
		if(strseqnum.contains("复合型")){
			isnew=true;
		}else{
			isnew=false;
		}
		return isnew;
	}
	
	private String delSpecialChar(String input){//去掉code中的特殊字符防止落表失败	
		input=input.replace("\r", "");
		input=input.replace(" ", "");
		input=input.replace(".", "");
		input=input.replace("\\", "");
		input=input.replace("/", "");
		input=input.replace("-", "");
		return input;
	}
	
	private void delRepeatField(List<DocModelField> listfield){//删除重复字段
		DocModelField field=null;
		String strcode;
		if(null!=listfield){
			for(int i=0;i<listfield.size();i++){
				field=listfield.get(i);
				strcode=field.getCode();
				for(int j=i+1;j<listfield.size();j++){
					if(strcode.equalsIgnoreCase(listfield.get(j).getCode())){
						System.out.println("发现重复字段已经删除！"+listfield.get(j).getCode());
						listfield.remove(j);
					}
				}
			}
		}
	}
	
	
	private void addGIDField(List<DocModelField> listfield,DocModel model){//将GID字段统一加到每个listfield中
		DocModelField field=new DocModelField();
		
		field.setId(CommonUtil.getUUID());	
		field.setCode("GID");		
		field.setEnName("globalID");		
		field.setName("全局标识");		
		field.setType("字符串");
		field.setMaxsize(64);	
		field.setRequired(true);
		field.setModel(model);
		field.setFieldcheckresult(null);
		listfield.add(field);
		
	}
	
	private void addGeneralFields(List<DocModelField> listfield,List<DocModelField> generalfield,DocModel model){//将通用字段加到字段列表中去
		for(int i=0;i<generalfield.size();i++){
			DocModelField tempfield=generalfield.get(i);
			DocModelField field=new DocModelField();
					
			field.setId(CommonUtil.getUUID());		
			field.setCode(tempfield.getCode());			
			field.setEnName(tempfield.getEnName());		
			field.setName(tempfield.getName());		
			field.setDefination(tempfield.getDefination());	
			field.setType(tempfield.getType());
			field.setMaxsize(tempfield.getMaxsize());		
			field.setRange(tempfield.getRange());		
			field.setRequired(tempfield.isRequired());		
			field.setComments(tempfield.getComments());
			field.setFieldcheckresult(tempfield.getFieldcheckresult());
			
			field.setModel(model);
			
			listfield.add(field);
		}
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
						if (filesuffix.contains("docx") )
						{
							listmodel.addAll(ParserStdDocx(inputFile,stdname));
						}
					}
				}
		
				return listmodel;
				
				
    }
   	
	
	


   
}
