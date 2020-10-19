package com.jn.primiary.office;

import com.alibaba.fastjson.JSONObject;
import com.jn.primiary.beyondsoft.entity.AuthType;
import com.jn.primiary.beyondsoft.entity.DataType;
import com.jn.primiary.beyondsoft.entity.Status;
import com.jn.primiary.beyondsoft.util.ComUtil;
import com.jn.primiary.commons.StringUtils;
import com.jn.primiary.metadata.utils.CommonUtil;
import com.jn.primiary.office.entity.OperDocStdCodeInfo;
import com.jn.primiary.office.entity.OperDocStdModel;
import com.jn.primiary.office.entity.OperDocStdModelField;
import com.jn.primiary.office.entity.TbStdglFiletable;
import com.jn.primiary.office.service.TbDictionaryService;
import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析文件名为SJB开头的文件
 */
@Service
public class OfficeTextParserSJB {
	public static final String MIME_TYPE = "application/poi-3.12";

	private Logger logger = Logger.getLogger(OfficeTextParserStd.class);
    private static int ename=0;
	private static int type=1;
	private static int max_size=2;
	private static int defination=3;
	private static int cname=3;
	private static int required=4;

	@Autowired
	private TbDictionaryService tbDictionaryService;

	private JSONObject ParserDocx(String inputfile,String stdname,TbStdglFiletable filetable,int anaylize_std_num,String username) throws IOException {
		String fileid = filetable.getFile_id();
		logger.info("文件id："+fileid);
		String batch_no = ComUtil.getBatchNo();
		int fieldcount=0;
		JSONObject resultobj = new JSONObject();
		List<OperDocStdModel> listModel=new LinkedList<OperDocStdModel>();
		List<OperDocStdCodeInfo> listCodeInfo=new LinkedList<OperDocStdCodeInfo>();

		//把表格里所有的表格，和表格title对应起来
		Map<String,XWPFTable> paragraph_table_map = new HashMap<>();
		XWPFDocument xwpfdoc=new XWPFDocument(new FileInputStream(inputfile));
		List<IBodyElement> bodyElements = xwpfdoc.getBodyElements();
		for (int i =0;i<bodyElements.size();i++) {
			IBodyElement bodyElement = bodyElements.get(i);
			if(bodyElement instanceof XWPFTable){
				XWPFTable table = (XWPFTable)bodyElements.get(i);
				XWPFTableRow firstrow=table.getRow(0);
				if(firstrow.getTableCells().size() == 6 || firstrow.getTableCells().size() == 7){
					//表格如果是4列或者10列，4列是码表，10列是标准
					int flagnum = 0;
					String table_cname = getTableTitle(bodyElements,i);
					String table_ename = getTableEname(bodyElements,i,flagnum);
					if(StringUtils.isEmpty(table_ename)){
						StringBuilder sb = new StringBuilder();
						for (int j = 0; j < 10; j++) {
							char c = (char)(Math.random() * 26 + 'a');
							sb.append(c);
						}
						table_ename = sb.toString();
					}
					String key = table_cname+"`"+table_ename;	//表格中文名`表格英文名 作为key
					paragraph_table_map.put(key,table);
				}
			}
		}

		//根据文件名判断表头的顺序
		tableTitleOrder(filetable.getFile_name());

		logger.info("开始解析");
		logger.info("table个数："+paragraph_table_map.size());
		for (String key : paragraph_table_map.keySet()) {
			try {
				String paragraph = key.split("`")[0];
				String tableename = key.split("`")[1];
				XWPFTable table=paragraph_table_map.get(key);
				//表头
				XWPFTableRow tmptitlerow=table.getRow(0);
				//6,7列的标准
				if(tmptitlerow.getTableCells().size()==6 || tmptitlerow.getTableCells().size()==7){
					String duanluo = StringUtils.formateFieldCode(paragraph);
					logger.info("标准表上的段落名："+duanluo);

					//遍历表的每一行处理数据
					List<OperDocStdModelField> listfield = null;

					String std_cname = getTableCname(duanluo);
					logger.info("标准中文名："+std_cname);
					String std_ename = tableename;
					logger.info("标准英文名："+std_cname);
					String std_code = tableename;
					/*String std_code = tbDictionaryService.getCode(std_cname);
					if(StringUtils.isEmpty(std_code)){
						if(!StringUtils.isEmpty(std_ename)){
							std_code = std_ename;
						}
					}*/

					OperDocStdModel newmodel=new OperDocStdModel();
					newmodel.setStd_id(CommonUtil.getUUID());
					newmodel.setBatch_no(batch_no);
					newmodel.setDatasource("file");
					newmodel.setCode(std_code);
					newmodel.setEname(StringUtils.formateFieldCode(std_ename));
					newmodel.setCname(StringUtils.formateFieldCode(std_cname)); //中文名
					newmodel.setAuth_status(AuthType.DSQ.getCode());
					newmodel.setCreate_time(new Date());
					newmodel.setStatus(Status.JIHUO.getCode());
					newmodel.setCreator(filetable.getUpload_person());
					newmodel.setFile_id(fileid);
					newmodel.setOper_flag(DataType.ADD.getCode());
					listModel.add(newmodel);

					listfield=new LinkedList<OperDocStdModelField>();
					newmodel.setFields(listfield);

					//表头
					XWPFTableRow titlerow = table.getRow(0);
					for(int irow=1;irow<table.getNumberOfRows();irow++){
						XWPFTableRow row = table.getRow(irow);
						if(row.getTableCells().size() != titlerow.getTableCells().size()){
							continue;
						}

						if(StringUtils.isEmpty(row.getCell(0).getText()) && StringUtils.isEmpty(row.getCell(1).getText())
								&& StringUtils.isEmpty(row.getCell(2).getText()) && StringUtils.isEmpty(row.getCell(3).getText())){
							continue;
						}

						OperDocStdModelField modelfield = getStdNewModelField(row,username);
						modelfield.setField_id(CommonUtil.getUUID());
						modelfield.setModel_id(newmodel.getStd_id());
						modelfield.setBatch_no(newmodel.getBatch_no());
						listfield.add(modelfield);
						fieldcount++;
					}
					//记录解析成功的标准数量
					anaylize_std_num++;
				}else{
					//如果列数不是4和10的话，直接跳过
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("错误信息："+e.getMessage());
			}
		}
		for (OperDocStdModel model : listModel) {
			List<OperDocStdModelField> fields = model.getFields();
			for(OperDocStdModelField everyfield : fields){
				everyfield.setCname(StringUtils.formateFieldCode(everyfield.getCname()));
				everyfield.setEname(StringUtils.formateFieldCode(everyfield.getEname()));
				everyfield.setCode(StringUtils.formateFieldCode(everyfield.getCode()));
			}
		}
		resultobj.put("std_list",listModel);
		resultobj.put("code_list",listCodeInfo);
		logger.info("一共解析出标准:"+listModel.size()+"个，field:"+fieldcount+"条");
		return resultobj;
	}

	/**
	 * 获取表格中文名
	 * @param bodyElements
	 * @param i
	 * @return
	 */
	public String getTableTitle(List<IBodyElement> bodyElements,int i){
		String result = "";
		XWPFParagraph paragraph = (XWPFParagraph)bodyElements.get(i-1);
		if(StringUtils.isEmpty(paragraph.getText())){
			result = getTableTitle(bodyElements,i-1);
		}else{
			result = paragraph.getText();
		}
		return result;
	}

	/**
	 * 获取表格英文名
	 * @param bodyElements
	 * @param i
	 * @return
	 */
	public String getTableEname(List<IBodyElement> bodyElements,int i,int flagnum){
		String result = "";
		if(bodyElements.get(i-1) instanceof XWPFTable){
			return result = "";
		}
		XWPFParagraph paragraph = (XWPFParagraph)bodyElements.get(i-1);
		if(StringUtils.isEmpty(paragraph.getText())){
			result = getTableEname(bodyElements,i-1,flagnum);
		}else{
			result = paragraph.getText();
			if(result.startsWith("表名：")){
				result = result.substring(3).replace("。","");
			}else if(result.startsWith("表名")){
				result = result.substring(2).replace("。","");
			}else{
				flagnum++;
				if(flagnum>4){
					return result = "";
				}
				//System.out.println("flagnum:"+flagnum);
				result = getTableEname(bodyElements,i-1,flagnum);
			}
		}
		return result;
	}

	/**
	 * 根据文件名判断表头的顺序
	 * @param filename
	 */
	public void tableTitleOrder(String filename){
		if(filename.contains("SJB S3") || filename.contains("SJB S5") || filename.contains("SJB S9")
				|| filename.contains("SJB S10") || filename.contains("SJB S8")){
			//0 enname 1 type 2 maxsize 3 fieldname 4 required
			ename=0;
			type=1;
			max_size=2;
			defination=3;
			cname = 3;
			required=4;
		}else if(filename.contains("SJB S6")){
			// 0 enname 1 fieldname 2 type 3 maxsize 4 required
			ename=0;
			type=2;
			max_size=3;
			defination=1;
			cname = 1;
			required=4;
		}else if(filename.contains("SJB S7")){
			//1 enname 2 type 3 maxsize 4 fieldname 6 required
			ename=1;
			type=2;
			max_size=3;
			defination=4;
			cname = 4;
			required=6;
		}
	}

	public String getTableCname(String str){
		str = str.replaceAll(" ","");
		str = str.replaceFirst("\\.","");
		String result = "";

		if(str.contains(" ")){
			//比如：表1 火车元数据
			String pre = str.split(" ")[0];
			if(pre.startsWith("表")){
				result = str.split(" ")[1];
			}else{
				result = str;
			}
		}else{
			if(str.startsWith("表")){
				String tmpstr = str.substring(2);
				char[] chars = 	tmpstr.toCharArray();
				for (char aChar : chars) {
					Pattern p = Pattern.compile("[0-9]");
					Matcher m = p.matcher(String.valueOf(aChar));
					if(m.find()){
						tmpstr = tmpstr.substring(1);
					}else{
						break;
					}
				}
				result = tmpstr;
			}else{
				result = str;
			}
		}

		/*if(result.endsWith("元数据字典描述表") || result.endsWith("元数据描述字典表")){
			result = result.substring(0,result.length()-8);
		}else if(result.endsWith("字典描述表") || result.endsWith("描述字典表")){
			result = result.substring(0,result.length()-5);
		}else if(result.endsWith("编码表")){
			result = result.substring(0,result.length()-3);
		}*/
		return result;
	}

	/**
	 * 解析标准表格的每一行
	 * @param row
	 * @return
	 *
	 *
	 * private static int ename=0;
	 * 	private static int type=1;
	 * 	private static int max_size=2;
	 * 	private static int defination=3;
	 * 	private static int required=4;
	 *
	 *
	 *
	 */
	private OperDocStdModelField getStdNewModelField(XWPFTableRow row,String username){
		String strtemp;
		OperDocStdModelField field=new OperDocStdModelField();
		//中文名
		strtemp=row.getCell(cname).getText().trim();;
		field.setCname(StringUtils.formateFieldCode(strtemp));

		//英文名
		strtemp = row.getCell(ename).getText().trim();
		field.setEname(StringUtils.formateFieldCode(strtemp));

		//短名
		strtemp = field.getEname();
		field.setCode(StringUtils.formateFieldCode(strtemp));

		//定义
		strtemp=row.getCell(defination).getText().trim();
		field.setDefinded(StringUtils.formateFieldCode(strtemp));
		//类型
		strtemp=row.getCell(type).getText().trim();
		strtemp = tbDictionaryService.getType(strtemp);
		if(!StringUtils.isEmpty(strtemp)){
			field.setDatatype(StringUtils.formateFieldCode(strtemp));
		}

		//值域
		strtemp="";
		field.setField_range(strtemp);

		//约束条件
		strtemp="";
		field.setRequired(strtemp);

		//最大出现次数
		strtemp="";
		field.setMaxContains(strtemp);

		//备注
		strtemp=row.getCell(defination).getText().trim();
		field.setComments(StringUtils.formateFieldCode(strtemp));

		//创建时间
		field.setCreatetime(new Date());
		//创建者
		field.setCreator(username);
		//操作标识	1:新增
		field.setOper_flag(DataType.ADD.getCode());
		//状态
		field.setStatus(Status.JIHUO.getCode());
		return field;
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

	private typeandsize TypeAndSize(String type, String strsize){
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

	public JSONObject ParserOfficeDirectory(String filename, TbStdglFiletable filetable, int anaylize_std_num, String username) throws IOException {
		//String fileid = filetable.getFile_id();
		JSONObject resultobj = new JSONObject();

		File file=new File(filename);
		String filepath = file.getCanonicalPath();
		int lastIndexOfDot = filepath.lastIndexOf('.');
		int lastIndexOf=filepath.lastIndexOf('\\');

		int fileNameLength = filepath.length();
		String filesuffix = filepath.substring(lastIndexOfDot+1, fileNameLength);
		filesuffix=filesuffix.toLowerCase();
		String stdname=filepath.substring(lastIndexOf+1, lastIndexOfDot);
		if (filesuffix.contains("docx") )
		{
			resultobj = ParserDocx(filepath,stdname,filetable,anaylize_std_num,username);
		}
		return resultobj;
	}


	public static void main(String[] args) {
		OfficeTextParserSJB std = new OfficeTextParserSJB();
		Map<String,XWPFTable> paragraph_table_map = new HashMap<>();
		try {
			//XWPFDocument xwpfdoc=new XWPFDocument(new FileInputStream("D:\\aa_hyren_20191217\\data\\SJB S8-2019.docx"));
			XWPFDocument xwpfdoc=new XWPFDocument(new FileInputStream("D:\\aa_hyren_20191217\\data\\汇聚\\SJB S8-2019 微波侦察数据存储要求 (20190718).docx"));
			List<IBodyElement> bodyElements = xwpfdoc.getBodyElements();
			System.out.println(bodyElements.size());
			for (int i =0;i<bodyElements.size();i++) {
				IBodyElement bodyElement = bodyElements.get(i);
				if(bodyElement instanceof XWPFTable){
					XWPFTable table = (XWPFTable)bodyElements.get(i);
					XWPFTableRow firstrow=table.getRow(0);
					if(firstrow.getTableCells().size() == 6){

						int flagnum = 0;
						String table_cname = std.getTableTitle(bodyElements,i);
						String table_ename = std.getTableEname(bodyElements,i,flagnum);
						if(StringUtils.isEmpty(table_ename)){
							StringBuilder sb = new StringBuilder();
							for (int j = 0; j < 10; j++) {
								char c = (char)(Math.random() * 26 + 'a');
								sb.append(c);
							}
							table_ename = sb.toString();
						}
						System.out.println(StringUtils.formateFieldCode(table_cname) + "----------------" +StringUtils.formateFieldCode(table_ename));
						String key = table_cname+"`"+table_ename;	//表格中文名`表格英文名 作为key
						paragraph_table_map.put(key,table);
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
