package com.jn.primiary.office;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import com.hankcs.hanlp.dependency.nnparser.util.std;
import com.jn.primiary.beyondsoft.entity.AuthType;
import com.jn.primiary.beyondsoft.entity.CommonException;
import com.jn.primiary.beyondsoft.entity.DataType;
import com.jn.primiary.beyondsoft.entity.Status;
import com.jn.primiary.beyondsoft.util.ComUtil;
import com.jn.primiary.beyondsoft.util.JwtUtil;
import com.jn.primiary.commons.StringUtils;
import com.jn.primiary.office.controller.DocModelController;
import com.jn.primiary.office.entity.*;
import com.jn.primiary.office.service.TbDictionaryService;
import io.jsonwebtoken.Claims;
import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jn.primiary.metadata.utils.CommonUtil;


@Service
public class OfficeTextParserStd  {
	private Logger logger = Logger.getLogger(OfficeTextParserStd.class);

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

	private static int code_seqid=0;
	private static int code_bianma=1;
	private static int code_contain=2;
	private static int code_remark=3;

	@Autowired
	private TbDictionaryService tbDictionaryService;

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
	

	private JSONObject ParserStdDocx(String inputfile,String stdname,TbStdglFiletable filetable,int anaylize_std_num,String username) throws IOException, CommonException {
		String fileid = filetable.getFile_id();
		logger.info("文件id："+fileid);
		String batch_no = ComUtil.getBatchNo();
		int modelcount=0;
		int fieldcount=0;
		int objcount=0;
		int codeinfonum=0;
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
				if(firstrow.getTableCells().size() == 4 || firstrow.getTableCells().size() == 10
						|| firstrow.getTableCells().size() == 3 || firstrow.getTableCells().size() == 5){
					//表格如果是4列或者10列，4列是码表，10列是标准
					paragraph_table_map.put(getTableTitle(bodyElements,i),table);
				}
			}
		}

		//如果文档里一个表格都没有，提示不是指定内容的文档
		if(paragraph_table_map.size() == 0) throw new CommonException("请上传指定内容的文档");

		logger.info("开始解析");
		logger.info("table个数："+paragraph_table_map.size());
		OperDocStdModelField field = new OperDocStdModelField();
		Set<String> strings = paragraph_table_map.keySet();
		int codeinfo_index=1;
		for (String paragraph : paragraph_table_map.keySet()) {
			try {
				XWPFTable table=paragraph_table_map.get(paragraph);
				//表头
				XWPFTableRow temprow=table.getRow(0);
				//是否是复合型的标准
				boolean table_iscompound=false;
				//4列的是码表
				if(temprow.getTableCells().size()==4 || temprow.getTableCells().size()==3 || temprow.getTableCells().size()==5){
					//码表解析并存库
					int table_column_size = temprow.getTableCells().size();
					parse_codeinfo(table,paragraph,fileid,batch_no,listCodeInfo,codeinfonum,username,filetable.getFile_name(),codeinfo_index);
					codeinfo_index ++;
					continue;
				}else if(temprow.getTableCells().size()==10){
					//十列的是标准yard measureindetifier
					modelcount++;
					//解析标准
					if((!temprow.getCell(0).getText().contains("序号"))&&(!temprow.getCell(1).getText().contains("中文名称"))&&(!temprow.getCell(2).getText().contains("英文名称"))){
						logger.info("表格的表头应该是：序号、中文名称、英文名称、短名、定义、数据类型、值域、约束条件、最大出现次数、备注");
						continue;
					}

					//特殊字符处理
					String duanluo = StringUtils.formateFieldCode(paragraph);
					logger.info("标准表上的段落名："+duanluo);

					//遍历表的每一行处理数据
					List<OperDocStdModelField> listfield = null;
					List<OperDocStdModelObj> listobj = null;

					//截取： 表1 火车元数据 为 火车
					String std_cname = getTableCname(duanluo);
					logger.info("标准中文名："+std_cname);
					String std_ename = getEname(std_cname,duanluo);
					String std_code = tbDictionaryService.getCode(std_cname);
					if(StringUtils.isEmpty(std_code)){
						std_code = std_ename;
					}
					//本来短名是截取英文名的前三位
//					if(StringUtils.isEmpty(std_code)){
//						if(!StringUtils.isEmpty(std_ename)){
//							if(std_ename.length()<3){
//								std_code = std_ename;
//							}else{
//								std_code = std_ename.substring(0,3);
//							}
//						}
//					}

					OperDocStdModel newmodel=new OperDocStdModel();;
					newmodel.setStd_id(CommonUtil.getUUID());
					newmodel.setBatch_no(batch_no);
					newmodel.setDatasource("file");
					newmodel.setCode(std_code);
					newmodel.setEname(std_ename);
					newmodel.setCname(std_cname); //中文名
					newmodel.setAuth_status(AuthType.DSQ.getCode());
					newmodel.setCreate_time(new Date());
					newmodel.setStatus(Status.JIHUO.getCode());
					newmodel.setCreator(filetable.getUpload_person());
					newmodel.setFile_id(fileid);
					newmodel.setOper_flag(DataType.ADD.getCode());

					//因为复合型的表，代码解析出来，表头和第一行的中间，还有一行空白的行，所以下面的代码匹配不到‘复合型’
					//所以加这个判断，把空白的给去了，属于特殊情况
					int rownum = 1;
					System.out.println(table.getNumberOfRows());
					XWPFTableRow firstrow = table.getRow(rownum);
					while(StringUtils.isEmpty(firstrow.getCell(0).getText()) && StringUtils.isEmpty(firstrow.getCell(1).getText())
						&&StringUtils.isEmpty(firstrow.getCell(2).getText()) && StringUtils.isEmpty(firstrow.getCell(3).getText())){
						++rownum;
						firstrow = table.getRow(rownum);
					}

					//判断表是不是复合型
					int first_row_count = firstrow.getTableCells().size();
					if(first_row_count == 10){
						String cellcolor = firstrow.getCell(type).getColor();
						//根据复合型单元格的背景颜色判断，特殊情况
						if(!StringUtils.isEmpty(cellcolor) && !"auto".equals(cellcolor) && !"FFFFFF".equals(cellcolor)){
							table_iscompound = true;
						}else{
							String fieldtype = StringUtils.formateFieldCode(firstrow.getCell(type).getText());
							if(!StringUtils.isEmpty(fieldtype)){
								if("复合型".equals(fieldtype)){
									table_iscompound = true;
								}else{
									table_iscompound = false;
								}
							}
						}

						if(table_iscompound == true){
							listobj = new LinkedList<OperDocStdModelObj>();
							listfield=new LinkedList<OperDocStdModelField>();
							newmodel.setFields(listfield);
							newmodel.setObjfields(listobj);
						}else{
							listfield=new LinkedList<OperDocStdModelField>();
							newmodel.setFields(listfield);
						}

					}else{
						//战略武器
						String str = firstrow.getCell(0).getText();
						if(str.contains("战略武器侦察")){
							table_iscompound = true;
							listobj = new LinkedList<OperDocStdModelObj>();
							listfield=new LinkedList<OperDocStdModelField>();
							newmodel.setFields(listfield);
							newmodel.setObjfields(listobj);
						}
					}
					listModel.add(newmodel);

					OperDocStdModelObj modelobj = null;
					//表头
					XWPFTableRow titlerow = table.getRow(0);
					XWPFTableCell firstcell = titlerow.getCell(0);
					XWPFTableCell secondcell = titlerow.getCell(1);
					XWPFTableCell thirdcell = titlerow.getCell(2);
					XWPFTableCell forthcell = titlerow.getCell(3);
					for(int irow=1;irow<table.getNumberOfRows();irow++){
						XWPFTableRow row = table.getRow(irow);
						if(!inputfile.contains("战略武器侦察")){
							if(row.getTableCells().size() != titlerow.getTableCells().size()){
								continue;
							}
						}

						if(StringUtils.isEmpty(row.getCell(seqid).getText()) && StringUtils.isEmpty(row.getCell(cname).getText())
								&& StringUtils.isEmpty(row.getCell(enname).getText()) && StringUtils.isEmpty(row.getCell(code).getText())){
							continue;
						}

						if(row.getCell(seqid).getText().equals(firstcell.getText())&&row.getCell(cname).getText().equals(secondcell.getText())
								&&row.getCell(enname).getText().equals(thirdcell.getText())&&row.getCell(code).getText().equals(forthcell.getText())){
							logger.info("当前行和表头相同："+row.getCell(seqid).getText()+"--"+
									row.getCell(cname).getText()+"--"+
									row.getCell(enname).getText());
							continue;
						}

						if(table_iscompound == true){
							//表是复合型
							XWPFTableRow current_row = table.getRow(irow);
							if(current_row.getTableCells().size() == 10){
								String current_type = StringUtils.formateFieldCode(current_row.getCell(type).getText());
								if(StringUtils.isEmpty(current_type)){
									String cellcolor = current_row.getCell(0).getColor();
									if(!StringUtils.isEmpty(cellcolor) && !"auto".equals(cellcolor) && !"FFFFFF".equals(cellcolor)){
										current_type = "复合型";
									}
								}

								if("复合型".equals(current_type)){
									objcount++;
									modelobj = new OperDocStdModelObj();
									modelobj.setBatch_no(newmodel.getBatch_no());
									modelobj.setObj_id(CommonUtil.getUUID());
									modelobj.setCname(row.getCell(cname).getText().trim());

									if(StringUtils.isEmpty(row.getCell(enname).getText().trim())){
										String ename = getEname(modelobj.getCname(),"");
										modelobj.setEname(ename);
									}else{
										modelobj.setEname(row.getCell(enname).getText().trim());
									}

									if(!StringUtils.isEmpty(row.getCell(code).getText().trim())){
										modelobj.setCode(row.getCell(code).getText().trim());
									}else{
										modelobj.setCode(modelobj.getEname());
									}

									modelobj.setDefined(row.getCell(defination).getText().trim());
									modelobj.setData_type(current_type);
									modelobj.setModel_id(newmodel.getStd_id());
									modelobj.setOper_flag(DataType.ADD.getCode());
									modelobj.setStatus(Status.JIHUO.getCode());
									modelobj.setRemark(row.getCell(comments).getText().trim());
									listobj.add(modelobj);
								}else{
									fieldcount++;
									OperDocStdModelField modelfield = getStdNewModelField(current_row,username);
									modelfield.setField_id(CommonUtil.getUUID());
									modelfield.setObj_id(modelobj.getObj_id());
									modelfield.setModel_id(newmodel.getStd_id());
									modelfield.setBatch_no(newmodel.getBatch_no());

									modelfield.setOper_flag(DataType.ADD.getCode());
									listfield.add(modelfield);
								}
							}else{
								//战略武器
								if(current_row.getTableCells().size() == 1){
									String current_type = "复合型";
									objcount++;
									modelobj = new OperDocStdModelObj();
									modelobj.setBatch_no(newmodel.getBatch_no());
									modelobj.setObj_id(CommonUtil.getUUID());
									modelobj.setCname(current_row.getCell(0).getText());

									String ename = getEname(current_row.getCell(0).getText(),"");
									modelobj.setEname(ename);

									modelobj.setCode(ename);
									modelobj.setDefined("");
									modelobj.setData_type(current_type);
									modelobj.setModel_id(newmodel.getStd_id());
									modelobj.setOper_flag(DataType.ADD.getCode());
									modelobj.setStatus(Status.JIHUO.getCode());
									modelobj.setRemark("");
									listobj.add(modelobj);
								}
							}

						}else{
							fieldcount++;
							//表不是复合型
							OperDocStdModelField modelfield = getStdNewModelField(row,username);
							modelfield.setField_id(CommonUtil.getUUID());
							modelfield.setModel_id(newmodel.getStd_id());
							modelfield.setBatch_no(newmodel.getBatch_no());
							listfield.add(modelfield);
						}
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

		resultobj.put("std_list",listModel);
		resultobj.put("code_list",listCodeInfo);
        logger.info("一共解析出标准:"+listModel.size()+"个，field:"+fieldcount+"条,"+"obj:"+objcount+"条,"+"码表："+listCodeInfo.size()+"个");
		return resultobj;
	}

	public String getEname(String cname,String oldname){
		//之后根据中文名去库里查英文名
		String ename = tbDictionaryService.getEname(cname,oldname);
		return ename;
	}

	/**
	 * 解析码表
	 * @param table
	 * @param paragraph
	 * @param fileid
	 * @param batch_no
	 * @param listCodeInfo
	 * @param codeinfonum
	 */
	public void parse_codeinfo(XWPFTable table,String paragraph,String fileid,String batch_no,List<OperDocStdCodeInfo> listCodeInfo,
							   int codeinfonum,String username,String filename,int codeinfo_index){
		//截取每个表格上对应的段落
		String duanluo = StringUtils.formateFieldCode(paragraph);
		String cname = getTableCname(duanluo);
		logger.info("码表中文名："+cname);


		//以下是原先去表里查英文名，现在码表英文名改成随机的字符
		//String ename = getEname(cname,duanluo);
		//如果码表英文名翻译出来是空的话，就随机取10个字母，作为英文名
		/*if(StringUtils.isEmpty(ename)){
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 10; i++) {
				char c = (char)(Math.random() * 26 + 'a');
				sb.append(c);
			}
			ename = sb.toString();
		}*/

		//改成前缀+随机10字符	比如：SJBS3**********
		String prefix = "";
		if(filename.contains("-")){
			//正式联调用
			prefix = filename.split("-")[0];
			prefix = tbDictionaryService.getPrefixByfilename(prefix);
		}else{
			//测试用
			prefix = "my";
		}

		String ename = prefix+"_dict"+codeinfo_index;

		/*StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			char c = (char)(Math.random() * 26 + 'a');
			sb.append(c);
		}
		String ename = prefix+"_"+sb.toString();*/

		//码表解析
		List<OperDocStdCodeInfo> operDocStdCodeInfoslist = parseCodeTable(table,fileid,batch_no,cname,ename,username);
		listCodeInfo.addAll(operDocStdCodeInfoslist);
		codeinfonum++;
	}


	/**
	 * 码表存库
	 */
	public List<OperDocStdCodeInfo> parseCodeTable(XWPFTable table,String fileid,String batch_no,String cname,String code_ename,String username){
		List<OperDocStdCodeInfo> list = new ArrayList<>();
		//表头
		XWPFTableRow titlerow = table.getRow(0);
		String bianma = "";
		String content = "";
		String remark = "";
		for (int i = 0; i < titlerow.getTableCells().size(); i++) {
			XWPFTableCell cell = titlerow.getCell(i);
			String tmpstr = tbDictionaryService.getvalue(StringUtils.formateFieldCode(cell.getText()));
			if(("key").equals(tmpstr)){
				bianma = String.valueOf(i);
			}else if(("value").equals(tmpstr)){
				content = String.valueOf(i);
			}else if(("remark").equals(tmpstr)){
				remark = String.valueOf(i);
			}else{
				continue;
			}
		}

		if(!StringUtils.isEmpty(bianma) && !StringUtils.isEmpty(content)){
			code_bianma = Integer.parseInt(bianma);
			code_contain = Integer.parseInt(content);
		}else{
			return list;
		}

		for(int irow=1;irow<table.getNumberOfRows();irow++){
			XWPFTableRow row = table.getRow(irow);
			//表格一行的格数，和表头不一样就跳过
			if(row.getTableCells().size() != titlerow.getTableCells().size()){
				continue;
			}
			OperDocStdCodeInfo codeinfo = new OperDocStdCodeInfo();
			codeinfo.setBatch_no(batch_no);
			codeinfo.setCode_id(CommonUtil.getUUID());
			codeinfo.setCodetable_cname(cname);
			codeinfo.setCodetable_ename(code_ename);
			codeinfo.setCreate_time(new Date());
			codeinfo.setCode_cname(row.getCell(code_contain).getText().trim());
			codeinfo.setCode_ename("");
			codeinfo.setCode_value(row.getCell(code_bianma).getText().trim());
			if(("remark").equals(remark)){
				codeinfo.setRemark(row.getCell(code_remark).getText().trim());
			}else{
				codeinfo.setRemark("");
			}
			codeinfo.setAuth_status(AuthType.DSQ.getCode());
			codeinfo.setCreator(username);
			codeinfo.setFile_id(fileid);
			codeinfo.setOper_flag(DataType.ADD.getCode());//1：新增
			codeinfo.setStatus(Status.JIHUO.getCode());
			list.add(codeinfo);
		}
		return list;
	}

	/**
	 * 解析标准表格的每一行
	 * @param row
	 * @return
	 */
	private OperDocStdModelField getStdNewModelField(XWPFTableRow row,String username){
		String strtemp;
		OperDocStdModelField field=new OperDocStdModelField();
		//中文名
		strtemp=row.getCell(cname).getText().trim();
		//logger.info("中文名："+strtemp);
		field.setCname(strtemp);

		if(StringUtils.isEmpty(row.getCell(enname).getText().trim())){
			strtemp = getEname(row.getCell(cname).getText().trim(),"");
			field.setEname(strtemp);
		}else{
			//英文名
			strtemp=row.getCell(enname).getText().trim();
			field.setEname(strtemp);
		}

		//短名
        strtemp = row.getCell(code).getText().trim();
		if(StringUtils.isEmpty(strtemp)){
            strtemp = tbDictionaryService.getCode(row.getCell(cname).getText().trim());
            if(StringUtils.isEmpty(strtemp)){
                strtemp = field.getEname();
            }
        }
        field.setCode(strtemp);
		//logger.info("短名："+strtemp);

		//定义
		strtemp=row.getCell(defination).getText().trim();
		field.setDefinded(StringUtils.formateFieldCode(strtemp));
		//类型
		strtemp=row.getCell(type).getText().trim();
		String tmp = tbDictionaryService.getType(strtemp);
		if(!StringUtils.isEmpty(tmp)){
			field.setDatatype(StringUtils.formateFieldCode(tmp));
		}else{
			field.setDatatype(strtemp);
		}

		//值域
		strtemp=row.getCell(field_range).getText().trim();
		field.setField_range(StringUtils.formateFieldCode(strtemp));
		//约束条件
		strtemp=row.getCell(required).getText().trim();
		field.setRequired(StringUtils.formateFieldCode(strtemp));
		//最大出现次数
		strtemp=row.getCell(max_contains).getText().trim();
		if(!StringUtils.isEmpty(strtemp)){
			field.setMaxContains(StringUtils.formateFieldCode(strtemp));
		}
		//备注
		strtemp=row.getCell(comments).getText().trim();
		//logger.info("备注："+strtemp);
		if(!StringUtils.isEmpty(strtemp)){
			field.setComments(StringUtils.formateFieldCode(strtemp));
		}
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
	
//	private OperDocStdModel getSingularStdModel(ModelPro tableinfo,String stdname){
//		OperDocStdModel model=new OperDocStdModel();
//		model.setId(CommonUtil.getUUID());
//		model.setDatasource(stdname);
//		model.setCode(tableinfo.getCode());
//		model.setEnName(tableinfo.getEname());
//		model.setDescription(tableinfo.getDefination());
//		model.setcName(tableinfo.getCname());
//		model.setCreateTime(Calendar.getInstance().getTime());
//		return model;
//	}
	
	
	
	/*private OperDocStdModel getPluralStdModel(XWPFTableRow row,String stdname,boolean isy14){
		String strtemp="";
		OperDocStdModel model=new OperDocStdModel();
		model.setId(CommonUtil.getUUID());		
		model.setDatasource(stdname);
		model.setCreateTime(Calendar.getInstance().getTime());
		
		if(!isy14){
			strtemp=row.getCell(code).getText().trim();
			model.setCode(strtemp);	
			
			model.setEnName(row.getCell(enname).getText().trim());	
			model.setcName(row.getCell(cname).getText().trim());
			model.setDescription(row.getCell(defination).getText().trim());
		}else{
			strtemp=row.getCell(0).getText().trim();
			model.setCode(strtemp);	
			model.setcName(strtemp);
		}
		
		
		return model;
	}*/
	
	
	
	
	/*private String getSizeNum(String strsize){
		String  size=null;
		String strfieldsize=null;
		Pattern patternSize1 = Pattern.compile("[0-9]{1,}字");
		Matcher matcherSize1 = patternSize1.matcher(strsize);
		Pattern patternSize2 = Pattern.compile("最长[0-9]{1,}");
		Matcher matcherSize2 = patternSize1.matcher(strsize);
		if (matcherSize1.find())
		{
			strfieldsize= matcherSize1.group();
			strfieldsize=strfieldsize.replace("字", "");
			size=strfieldsize;
		}else{
			
			System.out.println("未能找到长度说明字符串！"+strsize);
			size="";
			
		}
		
		return size;
	}*/
	
	
/*	private boolean isNewModel(XWPFTableRow row){
		String strseqnum="";
		boolean isnew=false;
		int irowlen=row.getTableCells().size();
		if(irowlen==10){
			strseqnum=row.getCell(type).getText().trim();
		}

		if(strseqnum.contains("复合型")){
			isnew=true;
		}else{
			isnew=false;
		}
		return isnew;
	}*/

	/**
	 * 递归向上找，表格上的额第一行就是表名
	 * @param bodyElements
	 * @param i
	 * @return
	 */
	public String getTableTitle(List<IBodyElement> bodyElements,int i) throws CommonException {
		String result = "";
		try{
			XWPFParagraph paragraph = (XWPFParagraph)bodyElements.get(i-1);
			if(StringUtils.isEmpty(paragraph.getText())){
				result = getTableTitle(bodyElements,i-1);
			}else{
				result = paragraph.getText();
			}
		}catch (Exception e){
			throw new CommonException("表格上的第一行应该是表名的信息如：表1火车元数据描述字典表");
		}

		return result;
	}

	private boolean isSingular(String stdname){
		boolean iscompound=false;
		if(stdname.contains("SJB Y1-2018")){
			 iscompound=false;
		}else if(stdname.contains("SJB Y2")){
			iscompound=true;
		}else if(stdname.contains("SJB Y5-2018")){
			iscompound=true;
		}else if(stdname.contains("SJB Y7-2018")){
			iscompound=true;
		}else if(stdname.contains("SJB Y8-2018")){
			iscompound=false;
		}else if(stdname.contains("SJB Y12-2018")){
			iscompound=true;
		}else if(stdname.contains("SJB Y13-2018")){
			iscompound=false;
		}else if(stdname.contains("SJB Y14-2018")){
			iscompound=false;
		}
		
		return iscompound;
	}
	

	public String getTableCname(String str){
		str = str.replaceAll(" ","");
		str = str.replaceFirst("\\.","");
		str = str.replaceFirst("-","");
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
				    if(aChar == '.'){
                        tmpstr = tmpstr.substring(1);
				        continue;
                    }
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

//			String tmpstr = str;
//			String result = str;
//			if(str.startsWith("表")){
//				tmpstr = str.substring(1, str.length());
//				result = str.substring(1, str.length());
//			}
//			for (int i = 0; i < tmpstr.length(); i++) {
//				String tmp = String.valueOf(tmpstr.charAt(i));
//				Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
//				Matcher m = p.matcher(tmp);
//				if (m.find()) {
//					break;
//				}else{
//					result =result.substring(1,result.length());
//				}
//			}
		}

		if(result.endsWith("元数据字典描述表") || result.endsWith("元数据描述字典表")){
			result = result.substring(0,result.length()-8);
		} else if(result.endsWith("字典描述表") || result.endsWith("描述字典表")){
			result = result.substring(0,result.length()-5);
		}else if(result.endsWith("编码表")){
			result = result.substring(0,result.length()-3);
		}
		return result;
	}

	
	/*private void addGeneralFields(List<DocModelField> listfield,List<DocModelField> generalfield,DocModel model){//将通用字段加到字段列表中去
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
	}*/
	
	public  JSONObject ParserOfficeDirectory(String filename,TbStdglFiletable filetable,int anaylize_std_num,String username) throws IOException, CommonException {
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
			resultobj = ParserStdDocx(filepath,stdname,filetable,anaylize_std_num,username);
		}
		return resultobj;
    }

	/**
	 * 码表与标准的关联关系
	 * @param listmodel
	 * @param listcodeinfo
	 */
	public void stdfield_codeinfo_relation(List<OperDocStdModel> listmodel,List<OperDocStdCodeInfo> listcodeinfo){
		//遍历每个标准
		for(int i =0;i<listmodel.size();i++){
			List<OperDocStdModelField> fields = listmodel.get(i).getFields();
			//fields = (List<OperDocStdModelField>) JSONArray.toCollection(JSONArray.fromObject(fields), OperDocStdModelField.class);
			//OperDocStdModel operDocStdModel = listmodel.get(i);
			//List<OperDocStdModelField> fields = operDocStdModel.getFields();
			//遍历标准的每个字段
			for (int j = 0; j < fields.size(); j++) {
				String field_range = fields.get(j).getField_range();
				String field_id = fields.get(j).getField_id();
				//遍历码表，获取字段值域和码表的fulu_id比较
				for (int k = 0; k < listcodeinfo.size(); k++) {
					if(!field_range.equals(listcodeinfo.get(k).getFulu_id())){
						continue;
					}else{
						//listcodeinfo.get(k).setField_id(field_id);
					}
				}
			}
		}


	}
















	/**
	 * 获取段落内容
	 *
	 * @param paragraph
	 */
	private void getParagraphText(XWPFParagraph paragraph) {
		// 获取段落中所有内容
		List<XWPFRun> runs = paragraph.getRuns();
		if (runs.size() == 0) {
			System.out.println("按了回车（新段落）");
			return;
		}
		StringBuffer runText = new StringBuffer();
		for (XWPFRun run : runs) {
			runText.append(run.text());
		}
		if (runText.length() > 0) {
			runText.append("，对齐方式：").append(paragraph.getAlignment().name());
			System.out.println(runText);
		}
	}

	/**
	 * 获取表格内容
	 *
	 * @param table
	 */
	private void getTabelText(XWPFTable table) {
		List<XWPFTableRow> rows = table.getRows();

		for (XWPFTableRow row : rows) {
			List<XWPFTableCell> cells = row.getTableCells();
			for (XWPFTableCell cell : cells) {
				// 简单获取内容（简单方式是不能获取字体对齐方式的）
				// System.out.println(cell.getText());
				// 一个单元格可以理解为一个word文档，单元格里也可以加段落与表格
				List<XWPFParagraph> paragraphs = cell.getParagraphs();
				for (XWPFParagraph paragraph : paragraphs) {
					getParagraphText(paragraph);
				}
			}
		}
	}


	public static void main(String[] args) {

		/*for (int i = 0; i < 10; i++) {
			char c = (char)(Math.random() * 26 + 'a');
			System.out.println(c);
		}*/
		/*String cname = "表1　TACAN/DME辐射源元数据";
		OfficeTextParserStd std = new OfficeTextParserStd();
		String ename = std.getTableCname(cname);
		System.out.println(ename);*/

		Map<String,XWPFTable> paragraph_table_map = new HashMap<>();
		try {
			XWPFDocument xwpfdoc=new XWPFDocument(new FileInputStream("D:\\aa_hyren_20191217\\data\\1.docx"));
			List<IBodyElement> bodyElements = xwpfdoc.getBodyElements();
			for (int i =0;i<bodyElements.size();i++) {
				IBodyElement bodyElement = bodyElements.get(i);
				if(bodyElement instanceof XWPFTable){
					XWPFTable table = (XWPFTable)bodyElements.get(i);
					XWPFTableRow firstrow=table.getRow(0);
					if(firstrow.getTableCells().size() == 4 ){

					}else if(firstrow.getTableCells().size() == 10){
						//10列是标准
						for (int j = 0; j < table.getNumberOfRows(); j++) {
							for (int k = 0; k < table.getRow(j).getTableCells().size(); k++) {
								System.out.println(table.getRow(j).getCell(k).getText() +"------"+table.getRow(j).getCell(k).getColor());
							}
						}

					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}
