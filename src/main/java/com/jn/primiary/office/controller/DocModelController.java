package com.jn.primiary.office.controller;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import com.alibaba.fastjson.JSONObject;
import com.jn.primiary.beyondsoft.aspect.CheckLoginEndPoint;
import com.jn.primiary.beyondsoft.entity.*;
import com.jn.primiary.beyondsoft.util.UserContextUtil;
import com.jn.primiary.metadata.utils.CommonUtil;
import com.jn.primiary.office.OfficeTextParserSJB;
import com.jn.primiary.office.entity.*;
import com.jn.primiary.office.service.TbDictionaryService;
import org.apache.log4j.Logger;
//import org.apache.shiro.web.session.HttpServletSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.jn.primiary.metadata.entity.BaseResponse;
import com.jn.primiary.metadata.entity.ResultCode;
import com.jn.primiary.office.OfficeTextParserStd;
import com.jn.primiary.office.entity.OperDocStdModel;
import com.jn.primiary.office.service.DocModelService;
import com.jn.primiary.office.service.StdModelService;

@CrossOrigin
@Controller
@RequestMapping("stdglprj/docmodel")
public class DocModelController{
	private Logger logger = Logger.getLogger(DocModelController.class);

	 @Autowired
	 private DocModelService modelService;

	 @Autowired
	 private StdModelService stdmodelService;
	 
	 @Autowired
	 private OfficeTextParserStd officeServiceStd;
	@Autowired
	private OfficeTextParserSJB officeServiceSJB;

	@Autowired
	private TbDictionaryService tbDictionaryService;

	 /**
	  * 文档上传
	  */
	 @RequestMapping(value = "/addstdbyfile",method=RequestMethod.POST)
	 @ResponseBody
     public BaseResponse<String> addstdbyfile(@RequestParam("file") MultipartFile file) throws Exception {
		 BaseResponse<String> response = new BaseResponse<String>();
		 logger.info("文件名是："+file.getOriginalFilename());

	 	 if(file.isEmpty()) throw new CommonException("上传的文件不能为空");
		 if(!file.getOriginalFilename().endsWith("docx")) throw new CommonException("请检查上传的文件格式");


		 String fileid="";
		 //文件名前缀
		 String relative_file_prefix = CommonUtil.getUUID();
		 Date date = new Date();

		 //把上传的文档放到doc文件夹下
		 File directory = new File("");//设定为当前文件夹
		 File directory_path = new File(directory.getCanonicalPath()+File.separator+"doc");
		 if (!directory_path.exists()) {
			 directory_path.mkdirs();
		 }

		 //文件名前面加个时间戳
		 //String filename = relative_file_prefix+"`"+file.getOriginalFilename();
		 String filename = relative_file_prefix+".docx";

		 String file_path=directory_path.getPath() +File.separator+ filename;
		 File uploadfile = new File(file_path);

		 //文档写到userdir/doc
		 FileOutputStream os = new FileOutputStream(uploadfile);
		 BufferedOutputStream out = new BufferedOutputStream(os);
		 out.write(file.getBytes());
		 out.flush();
		 out.close();

		 List<OperDocStdModel> listmodel=new ArrayList<>();
		 List<OperDocStdCodeInfo> listcodeinfo=new ArrayList<>();
		 JSONObject resultobj = new JSONObject();
		 TbStdglFiletable filetable = new TbStdglFiletable();
		 int anaylize_std_num=0;
		 try {
			 //保存文件信息
			 logger.info("保存文件信息");
			 filetable.setFile_id(CommonUtil.getUUID());
			 filetable.setFile_name(file.getOriginalFilename());
			 filetable.setAuth_status(AuthType.DSQ.getCode());
			 filetable.setUpload_person(UserContextUtil.getUserName());
			 filetable.setUpload_time(date);
			 filetable.setStatus(Status.JIHUO.getCode());
			 filetable.setRelative_file_prefix(relative_file_prefix);
			 fileid = filetable.getFile_id();

			 //解析成标准和码表
			 logger.info("开始解析成标准和码表");
			 System.out.println(file.getOriginalFilename());
			 //因文档的内容，做的特殊的判断
			 if(file.getOriginalFilename().contains("SJB S")){
				 logger.info("开始解析SJB S文件");
				 resultobj=officeServiceSJB.ParserOfficeDirectory(file_path,filetable,anaylize_std_num,UserContextUtil.getUserName());
			 }else{
				 resultobj=officeServiceStd.ParserOfficeDirectory(file_path,filetable,anaylize_std_num,UserContextUtil.getUserName());
			 }

			 logger.info("解析后的数据："+resultobj.toString());
			 listmodel = (List<OperDocStdModel>)resultobj.get("std_list");
			 listcodeinfo = (List<OperDocStdCodeInfo>)resultobj.get("code_list");

		 }catch(Exception e){
		 	logger.info(e.getMessage());
		 	File delfile=new File(file_path);
		 	delfile.delete();
		 	throw new CommonException(e.getMessage());
		 	//response.setMessage("解析成功"+anaylize_std_num+"个，请检查上传文件内容");
		 	//return response;
		 }

		 //解析出的标准数量
		 /*if(0==listmodel.size()) {
			 File delfile=new File(file_path);
			 delfile.delete();
			 response.setResultCode(ResultCode.RESULT_ERROR);
			 response.setMessage("上传文件中未能找到有效标准");
			 return response;
		 }*/

		 try {
			 //保存文件，标准，字段，码表
			 logger.info("标准码表信息入库");
			 stdmodelService.add(listmodel,listcodeinfo,filetable);
		 }catch(Exception e){
			 e.printStackTrace();
			 File delfile=new File(file_path);
			 delfile.delete();
			 response.setResultCode(ResultCode.RESULT_ERROR);
			 response.setMessage("保存解析结果失败，检查上传文件中是否有与当前数据库内code相同的标准");
			 return response;
		 }

		 response.setResultCode(ResultCode.RESULT_SUCCESS);
		 response.setMessage("解析入库上传标准成功");
		 logger.info("写入数据库完成");
		 //记录操作日志

	     return response;
	 }


	/**
	 * 文档上传，编辑按钮，查看标准和码表信息
	 * @param file_id
	 * @return
	 */
	@RequestMapping(value = "/editfile/{file_id}",method=RequestMethod.GET)
	@ResponseBody
	public BaseResponse<JSONObject> EditFile(@PathVariable("file_id") String file_id){
		BaseResponse<JSONObject> response = new BaseResponse<JSONObject>();

		TbStdglFiletable filemess = modelService.getfilemessbyid(file_id);
		JSONObject resultobj = new JSONObject();

		if(filemess.getAuth_status() == AuthType.SHTG.getCode()){
			List<Standard> standardList = modelService.getstdmess_resulttable(file_id);
			List<CodeInfo> codeInfoList = modelService.getcodemess_resulttable(file_id);

			resultobj.put("std_mess",standardList);
			resultobj.put("codeinfo_mess",codeInfoList);
		}else{
			//标准
			List<OperDocStdModel> operDocStdModels = modelService.editfile_std(file_id);
			//码表
			List<OperDocStdCodeInfo> operDocStdCodeInfos = modelService.editfile_codeinfo(file_id);

			resultobj.put("std_mess",operDocStdModels);
			resultobj.put("codeinfo_mess",operDocStdCodeInfos);
		}

		List<JSONObject> list = new ArrayList<>();
		list.add(resultobj);

		response.setResultCode(ResultCode.RESULT_SUCCESS);
		response.setData(list);
		response.setMessage("查询成功");
		return response;
	}


	/**
	 * 根据标准id查询标准字段	操作表
	 * @param std_id
	 * @return
	 */
	@RequestMapping(value = "/getfieldbyid",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse<OperDocStdModel> getfieldbyid(@RequestParam("std_id") String std_id){
		BaseResponse<OperDocStdModel> response = new BaseResponse<OperDocStdModel>();
		//TbStdglFiletable filemess = modelService.getfilemessbyid(file_id);
		OperDocStdModel std = modelService.getfieldbyid(std_id);
		List<OperDocStdModel> list = new ArrayList<>();
		list.add(std);

		response.setData(list);
		response.setResultCode(ResultCode.RESULT_SUCCESS);
		response.setMessage("查询成功");
		return response;
	}


	/**
	 * 根据标准id查询标准字段	结果表
	 * @param std_id
	 * @return
	 */
	@RequestMapping(value = "/getfieldbyidresulttable",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Standard> getfieldbyid_resulttable(@RequestParam("std_id") String std_id){
		BaseResponse<Standard> response = new BaseResponse<Standard>();
		//TbStdglFiletable filemess = modelService.getfilemessbyid(file_id);
		Standard std = modelService.getfieldbyid_resulttable(std_id);
		List<Standard> list = new ArrayList<>();
		list.add(std);
		response.setData(list);
		response.setResultCode(ResultCode.RESULT_SUCCESS);
		response.setMessage("查询成功");
		return response;
	}



	/**
	 * 删除文档
	 * @param file_entity
	 * @return
	 */
	@RequestMapping(value = "/delfile",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse<String> delfile(@RequestBody TbStdglFiletable file_entity){
		BaseResponse<String> response = new BaseResponse<String>();
		try {
			modelService.delfile(file_entity);
			response.setResultCode(ResultCode.RESULT_SUCCESS);
			response.setMessage("删除成功");
		}catch (Exception e){
			response.setResultCode(ResultCode.RESULT_ERROR);
			response.setMessage("删除失败");
		}
		return response;
	}

	/**
	 * 文件下载
	 * @param file_id
	 * @return
	 */
	@RequestMapping(value = "/downloadfile/{file_id}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<FileSystemResource>  downloadfile(@PathVariable("file_id") String file_id) throws IOException, CommonException {
		TbStdglFiletable filemess = modelService.findfilenamebyid(file_id);
		String filename = filemess.getFile_name();
		Date upload_time = filemess.getUpload_time();
		//standard_file_example.docx
		//System.out.println(filename);
		String filename_in_doc = filemess.getRelative_file_prefix()+"`"+filename;
		String userDir = System.getProperty("user.dir")+File.separator+"doc"+File.separator;
		String filepath = userDir+filename_in_doc;
		logger.info("下载的文件名是："+filepath);
		File file = new File(filepath);

		//1569564884989`standard_file_example(1).docx
		if (!file.exists()){
			//如果文件不存在，就找文件名是这样的文件名1569564884989.docx
			filepath = userDir+filemess.getRelative_file_prefix()+".docx";
			file = new File(filepath);

			if(!file.exists()){
				//如果还没有，就到文件目录中去找
				logger.info("文件不存在，到文件目录中找");
				//文件不存在，就去文档目录下找，以Relative_file_prefix的值为前缀的，就认为是同一个文件
				File userDirectory = new File(userDir);
				File[] files = userDirectory.listFiles((pathname) -> {
					if (pathname.getName().startsWith(filemess.getRelative_file_prefix())) {
						return true;
					} else {
						return false;
					}
				});

				logger.info("过滤出的文件数量："+files.length);
				logger.info("文件名："+files[0].getName());
				logger.info("文件名路径："+files[0].getPath());
				logger.info("文件名绝对路径："+files[0].getAbsolutePath());
				logger.info("文件length："+files[0].length());

				if(files.length == 0){
					logger.info("该文件不存在："+filepath);
					throw new CommonException("该文件不存在："+filepath);
				}else{
					file = files[0];
				}
			}

		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/octet-stream;charset=utf-8");
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		//headers.add("Content-Disposition", "attachment; filename=" +  URLEncoder.encode(filename, "UTF-8"));
		//headers.add("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		headers.add("Content-Disposition", "attachment;filename=" + filename + ";filename*=utf-8''" + URLEncoder.encode(filename, "UTF-8"));
		//headers.add("Content-Disposition","attachment;filename=kpi.xlsx");
		//headers.add("Content-Disposition","attachment;filename=\"$filename\"");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");




		return ResponseEntity
				.ok()
				.headers(headers)
				.contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(new FileSystemResource(file));
	}


	/**
	 * 保存修改后的文档
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/savefile",method=RequestMethod.POST)
	@ResponseBody
	public void savefile(@RequestBody OperFileMess operFileMess){
		modelService.savefile(operFileMess,UserContextUtil.getUserName());
	}

	/**
	 * 文档上传首页
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/fileindex",method=RequestMethod.GET)
	@ResponseBody
	public BaseResponse<TbStdglFiletable> fileindex(){
		BaseResponse<TbStdglFiletable> response = new BaseResponse<TbStdglFiletable>();
		try {
			List<TbStdglFiletable> fileindex = modelService.fileindex();
			response.setResultCode(ResultCode.RESULT_SUCCESS);
			response.setData(fileindex);
		}catch (Exception e){
			e.printStackTrace();
			response.setResultCode(ResultCode.RESULT_ERROR);
			response.setMessage("请联系管理员");
		}
		return response;
	}


	/**
	 * 文档申请提交
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/fileapply",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse<TbStdglFiletable> fileapply(@RequestParam String category_id,@RequestParam String file_id){
		BaseResponse<TbStdglFiletable> response = new BaseResponse<TbStdglFiletable>();
		try {
			modelService.fileapply(category_id, file_id,UserContextUtil.getUserName());

			response.setResultCode(ResultCode.RESULT_SUCCESS);
			response.setMessage("申请成功");
		}catch (Exception e){
			e.printStackTrace();
			//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			response.setResultCode(ResultCode.RESULT_ERROR);
			if("".equals(e.getMessage())){
				response.setMessage("申请失败");
			}else{
				response.setMessage(e.getMessage());
			}

		}
		return response;
	}

	/**
	 * 文档上传，保存编辑后的标准字段信息
	 * @param operDocStdModel
	 * @return
	 */
	@RequestMapping(value = "/savestd",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse<TbDictionary> savestd(@RequestBody OperDocStdModel operDocStdModel){
		BaseResponse<TbDictionary> response = new BaseResponse<TbDictionary>();
		try {
			//对象类型
			modelService.savestd(operDocStdModel);
			response.setResultCode(ResultCode.RESULT_SUCCESS);
			response.setMessage("保存成功");
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/adddictionary")
	@ResponseBody
	public BaseResponse<TbDictionary> adddictionary(){
		BaseResponse<TbDictionary> response = new BaseResponse<TbDictionary>();
		try {
			tbDictionaryService.adddictionary("D:\\ecdict.csv");
			response.setResultCode(ResultCode.RESULT_SUCCESS);
			response.setMessage("上传成功");
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 撤回申请
	 * @param file_id
	 */
	@RequestMapping(value = "/retractapply",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse<TbStdglFiletable> retractapply(@RequestParam String file_id){
		BaseResponse<TbStdglFiletable> response = new BaseResponse<TbStdglFiletable>();
		try {
			modelService.retractapply(file_id);
			response.setResultCode(ResultCode.RESULT_SUCCESS);
			response.setMessage("撤回成功");
		}catch (Exception e){
			e.printStackTrace();
			response.setResultCode(ResultCode.RESULT_ERROR);
			response.setMessage("撤回失败");
		}
		return response;
	}






	 //57汇聚模型文档到模型
	 /*@RequestMapping(value = "/addhuiju")
	 @ResponseBody
     public void addhuiju() throws IOException {   
		 

		 
		 List<DocModel> listmodel=officeServiceHuiju.ParserOfficeDirectory("D:\\wld\\hui");
		 
		 for(int i=0;i<listmodel.size();i++){
			 modelService.add(listmodel.get(i));
		 }
		 //modelService.add(model);
		 System.out.println("写入数据库完成");
	     return ;
	 }
	 
	 
	 
	 @RequestMapping(value = "/mapdata")
	 @ResponseBody
     public void mapdata() throws IOException {   	
		 
		 officeService.Maptable();
		 
		 
		 //modelService.add(model);
		 System.out.println("统计完成");
	     return ;
	 }
	 
	 

	 @RequestMapping(value = "/count")
	 @ResponseBody
     public void count() throws IOException {   	
		 
		 List<DocModel> listmodel=officeService.ParserOfficeDirectory("C:\\wld\\guifan",1);
		 
		 
		 //modelService.add(model);
		 System.out.println("统计完成");
	     return ;
	 }
	 
	 
	 @RequestMapping(value = "/del")
	 @ResponseBody
     public void del() throws IOException {   	
		 
		 List<DocModel> listmodel=officeService.ParserOfficeDirectory("C:\\wld\\guifan",2);
		 
		 
		 //modelService.add(model);
		 System.out.println("统计完成");
	     return ;
	 }
	 
	 
	 
	 
	 @RequestMapping(value = "/tempcreatetable")
	 @ResponseBody
     public void tempcreatetable() throws IOException {   	
		 
		 DocModel model=officeService.ParserTempDoc("D:\\4.docx");
		 
		 
		 modelService.add(model);
		 System.out.println("写入完成");
	     return ;
	 }*/
	 
	
}
