package com.jn.primiary.office;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;


@Service
public class OfficeTableName  {
	public static final String MIME_TYPE = "application/poi-3.12";

	private WordTable wordTable = new WordTable();
	
	public class ModelPro{
		private	String code;
		private  String cname;
		private  String ename;
		private String defination;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getCname() {
			return cname;
		}
		public void setCname(String cname) {
			this.cname = cname;
		}
		public String getEname() {
			return ename;
		}
		public void setEname(String ename) {
			this.ename = ename;
		}
		public String getDefination() {
			return defination;
		}
		public void setDefination(String defination) {
			this.defination = defination;
		}
	   
	}

	
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
	
	
	
	
	private List<ModelPro> ParserDocx(String inputfile) throws IOException {
	
		String strtemp="";
		List<ModelPro> tablename=new LinkedList<ModelPro>();
		FileInputStream in=new FileInputStream(inputfile);
		XWPFDocument xwpfdoc=new XWPFDocument(in);
		Iterator<XWPFTable> it=xwpfdoc.getTablesIterator();
		/*
		XWPFWordExtractor extractor = new XWPFWordExtractor(xwpfdoc);
		
		
		//获取文档所有内容
		String documentContent = extractor.getText();
				
		//获取完整表名
		List<String> listWholeTableName = wordTable.getWholeTableName(documentContent);
				
		//获取表序号和表名
		List<Map<String, String>> listTableHashMap = new ArrayList<>();
				
		for (String wholeTableName : listWholeTableName)
		{
			Map<String, String> tableHashMap = wordTable.getTableHashMap(wholeTableName);
			listTableHashMap.add(tableHashMap);
		}
		*/
		
		while(it.hasNext()){
			XWPFTable table=it.next();
			XWPFTableRow temprow=table.getRow(0);
			if(temprow.getTableCells().size()!=11){
				
				tablename.add(null);
				continue;
			}
			if((!temprow.getCell(0).getText().contains("序号"))&&(!temprow.getCell(1).getText().contains("中文名称"))&&(!temprow.getCell(2).getText().contains("英文名称"))){
				tablename.add(null);
				continue;
			}
				
			XWPFTableRow row=table.getRow(1);//取第一行得到信息
		    
			ModelPro model=new ModelPro();
			model.setCname(row.getCell(cname).getText());
			model.setCode(row.getCell(code).getText());
			model.setDefination(row.getCell(defination).getText());
			model.setEname(row.getCell(ename).getText());
			tablename.add(model);
		
			
		}
		in.close();
		// TODO Auto-generated method stub
		return tablename;
	}
	
	
	
	
	
	
	
	
	public  Map<String,List> ParserOfficeDirectory(String inputDir) throws IOException {
		   
			 Map<String,List> tablemap=new HashMap<String,List>();
				
				
		
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
							tablemap.put(stdname,ParserDocx(inputFile));
							
						}		
					}	
		
				
				
				
				}
				return tablemap;
	
	

	}
  
}
