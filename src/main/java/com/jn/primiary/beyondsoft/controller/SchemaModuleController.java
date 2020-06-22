package com.jn.primiary.beyondsoft.controller;


import com.alibaba.fastjson.JSONArray;
import com.jn.primiary.beyondsoft.aspect.LoginWhitePathAnnotation;
import com.jn.primiary.beyondsoft.entity.CodeInfo;
import com.jn.primiary.beyondsoft.entity.OperStandard;
import com.jn.primiary.beyondsoft.entity.Standard;
import com.jn.primiary.beyondsoft.service.SchemaModuleService;
import com.jn.primiary.beyondsoft.util.ComUtil;
import com.jn.primiary.beyondsoft.util.UserContextUtil;
import com.jn.primiary.beyondsoft.vo.*;
import com.jn.primiary.metadata.entity.BaseResponse;
import com.jn.primiary.metadata.entity.ResultCode;
import com.jn.primiary.metadata.utils.StringUtils;;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Pattern;
import java.io.InputStream;
import java.util.*;

/**
 * @Des 标准管理控制层
 * @Author chenhong
 * @Date 2019/9/23 10:07
 */
@Validated
@Controller
@RequestMapping("/newstandard")
public class SchemaModuleController{

    @Autowired
    SchemaModuleService standardservice;


    @RequestMapping(path = "/getAllStd",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<Standard> getAllStd(){
        BaseResponse<Standard> response = new BaseResponse<Standard>();
        List<Standard> liststand = null;
        try {
            liststand = standardservice.getAllStd();
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(liststand);
        response.setMessage("获取信息成功");
        return response;
    }


    /**
     * 标准列表
     *
     * @return
     */
    @LoginWhitePathAnnotation
    @RequestMapping(value = "/getStandardList", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<StandardVo> getStandardList(@Pattern(regexp = "^\\w+$",message = "请输入正确的目录id") @RequestParam String categoryId) throws Exception {
        BaseResponse<StandardVo> response = new BaseResponse<StandardVo>();
        List<StandardVo> liststand = standardservice.getAllStandardInfo(categoryId);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(liststand);
        response.setMessage("获取信息成功");
        return response;
    }

    /**
     * 所有审核通过的标准列表
     *
     * @return
     */
    @RequestMapping(value = "/getAllSHTGStandardInfo/{categoryId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<StandardVo> getAllSHTGStandardInfo(@PathVariable String categoryId) throws Exception {
        BaseResponse<StandardVo> response = new BaseResponse<StandardVo>();
        List<StandardVo> liststand = standardservice.getAllSHTGStandardInfoRecursive(categoryId);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(liststand);
        response.setMessage("获取信息成功");
        return response;
    }

    /**
     * 标准列表后端分页
     *
     * @return
     */
    @RequestMapping(value = "/getStandardListByPage", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getStandardListByPage(@RequestBody Map<String,String> paramMap) throws Exception {
        String categoryId = paramMap.get("categoryId");
        String pagenum = paramMap.get("pagenum");
        String size = paramMap.get("size");

        BaseResponse<StandardVo> response = new BaseResponse<StandardVo>();
        Map<String, Object> map = standardservice.getAllStandardInfoByPage(categoryId, pagenum, size);
        map.put("resultcode", ResultCode.RESULT_SUCCESS);
        map.put("message", "获取信息成功");
        return map;
    }

    /**
     * saveStandard
     * 新增/编辑标准
     *
     * @param standard
     * @return
     */
    @RequestMapping(value = "/saveStandard", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<OperStandard> save(@RequestBody OperStandard standard) {
        BaseResponse<OperStandard> response = new BaseResponse<OperStandard>();
        String user = UserContextUtil.getUserName();
        if (StringUtils.isEmpty(standard.getId())) {
            //新增标准
            standardservice.saveField(standard, user);
        } else {
            standardservice.updateField(standard, user);
        }

        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setMessage("获取信息成功");
        return response;
    }

    /**
     * 提交审核
     *
     * @param batchNo
     * @return
     */
    @RequestMapping(value = "/doStandardAudit", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<OperStandard> doStandardAudit(@RequestParam String batchNo) {
        BaseResponse<OperStandard> response = new BaseResponse<OperStandard>();
        standardservice.doAudit(batchNo);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setMessage("获取信息成功");
        return response;
    }

    /**
     * 根据ID查看标准信息
     *
     * @param stdId   标准ID
     * @param version 版本（可为空）
     *                为空-查询当前有效版本
     *                不为空-查询制定版本
     * @return
     * @throws Exception
     */
    @LoginWhitePathAnnotation
    @RequestMapping(value = "/getStandardById", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse getStandardById(@Pattern(regexp = "^\\w+$",message = "请输入正确的标准id") @RequestParam String stdId,
                                        @Pattern(regexp = "^[0-9]*$",message = "请输入正确的版本号") @RequestParam String version,
                                        @RequestParam String batchNo) {
        BaseResponse response = new BaseResponse();
        if (!StringUtils.isEmpty(batchNo)) {
            OperStandard operStandard = standardservice.getStandardByBatchNo(batchNo, stdId);
            List<OperStandard> list = new ArrayList();
            list.add(operStandard);
            response.setData(list);
        } else {
            Standard standard = standardservice.getStandardById(stdId, version);
            List<Standard> list = new ArrayList();
            list.add(standard);
            response.setData(list);
        }
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setMessage("获取信息成功");

        return response;
    }


    /**
     * 根据ID查看历史标准信息
     *
     * @param stdId   标准ID
     * @param version 版本
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getStandardByIdVersion", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse getStandardByIdVersion(@RequestParam String stdId, @RequestParam String version) {
        BaseResponse response = new BaseResponse();

        Standard standard = standardservice.getStandardByIdVersion(stdId, version);
        List<Standard> list = new ArrayList();
        list.add(standard);
        response.setData(list);

        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setMessage("获取信息成功");

        return response;
    }


    /**
     * 批量删除
     *
     * @param deleteVo 标准ID
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteStandardById", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<Standard> deleteStandardById(@RequestBody List<StandardDeleteVo> deleteVo) {

        BaseResponse<Standard> response = new BaseResponse<Standard>();
        standardservice.deleteStandardById(deleteVo);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setMessage("删除成功");
        return response;
    }

    /**
     * 查询历史版本标准列表
     *
     * @param stdId 标准ID
     * @return
     */
    @RequestMapping(value = "/getOldVersionList", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<OldStandardVo> getOldVersionList(@RequestParam String stdId) {
        BaseResponse<OldStandardVo> response = new BaseResponse<OldStandardVo>();
        try {
            if (StringUtils.isEmpty(stdId)) {
                response.setResultCode(ResultCode.RESULT_ERROR);
                response.setMessage("标准ID不能为空");
            }
            List<Object[]> stdlist = standardservice.getOldVersion(stdId);
            List<OldStandardVo> resultlist = ComUtil.castEntity(stdlist, OldStandardVo.class);

            response.setResultCode(ResultCode.RESULT_SUCCESS);
            response.setData(resultlist);
            response.setMessage("获取信息成功");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 激活指定版本的标准信息
     *
     * @param stdId   标准ID
     * @param version 版本
     * @return
     */
    @RequestMapping(value = "/getOldVersionOpen", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<Standard> getOldVersionOpen(@RequestParam String stdId, @RequestParam String version) {

        BaseResponse<Standard> response = new BaseResponse<Standard>();
        standardservice.getOldVersionOpen(stdId, version);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setMessage("获取信息成功");
        return response;
    }

    /**
     * 标准码表审核列表
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAuditList", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<StandardCodeAuditVo> getAuditList() throws Exception {

        BaseResponse<StandardCodeAuditVo> response = new BaseResponse<StandardCodeAuditVo>();
        List<StandardCodeAuditVo> list = standardservice.getAuditList();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(list);
        response.setMessage("获取信息成功");
        return response;
    }

    /**
     * 审核
     *
     * @param auditVo
     * @return
     */
    @RequestMapping(value = "/auditStandard", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<OperStandard> auditStandard(@RequestBody AuditVo auditVo) {
        BaseResponse<OperStandard> response = new BaseResponse<OperStandard>();
        try {
            standardservice.auditStandard(auditVo);
            response.setResultCode(ResultCode.RESULT_SUCCESS);
            response.setMessage("操作成功");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResultCode(ResultCode.RESULT_ERROR);
            response.setMessage("操作失败");
            return response;
        }
    }

    /**
     * 查询有效的标准列表
     *
     * @return
     */
    @RequestMapping(value = "/getStandard", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<Standard> getStandard() throws Exception {
        BaseResponse<Standard> response = new BaseResponse<Standard>();
        List<Standard> std = standardservice.getStandard();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(std);
        response.setMessage("获取信息成功");
        return response;
    }


    /**
     * 标准首页，模糊搜索
     */

    @RequestMapping(value = "/searchStandard", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> searchStandard(@RequestBody Map<String, String> map) throws Exception {
        String pagenum = map.get("pagenum");
        if(!pagenum.matches("^[0-9]*$")){
            throw new Exception("请输入正确的页数");
        }

        String size = map.get("size");
        if(!size.matches("^[0-9]*$")){
            throw new Exception("请输入正确的显示数量");
        }

        BaseResponse<StandardVo> response = new BaseResponse<StandardVo>();
        Map<String, Object> resultmap = new HashMap<>();
        try {
            resultmap = standardservice.searchStandard(map);
            resultmap.put("resultcode", ResultCode.RESULT_SUCCESS);
            resultmap.put("message", "获取信息成功");
            //response.setResultCode(ResultCode.RESULT_SUCCESS);
            //response.setData(liststand);
            //response.setMessage("获取信息成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultmap;

    }

    /**
     * 确定导入数据库
     * dbType:数据库类型
     * stdList：选中的标准
     *
     * @param map
     */
    @RequestMapping(value = "/confirmAddToDb", method = RequestMethod.POST)
    @ResponseBody
    public void confirmAddToDb(@RequestBody Map<String, String> map) {
        String dbType = map.get("dbType");
        List<StandardDeleteVo> list = JSONArray.parseArray(map.get("stdArray"), StandardDeleteVo.class);

        System.out.println(map.toString());

    }

    @RequestMapping(value = "/getExcelTemp", method = RequestMethod.GET)
    @ResponseBody
    public void getApplicationTemplate(HttpServletResponse response) throws Exception {
        InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream("static/temp.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        response.setContentType("application/binary;charset=ISO8859-1");
        String fileName = java.net.URLEncoder.encode("template", "UTF-8");
        response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
        ServletOutputStream out = null;
        out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();

}

    @RequestMapping(value = "/importStandard", method = RequestMethod.POST)
    @ResponseBody
    public void importExcel(MultipartFile file) throws Exception {
        if (!file.getOriginalFilename().matches("^.+\\.(?i)(xls)$")
                && !file.getOriginalFilename().matches("^.+\\.(?i)(xlsx)$")) {
            //log.error("上传文件格式不正确");
        } else {
            standardservice.importExcel(file);
        }

    }

    @RequestMapping(value = "/exportStandardToExcel", method = RequestMethod.GET)
    @ResponseBody
    public void exportToExcel(HttpServletResponse response) throws Exception {
        standardservice.exportToExcel(response);
    }


    /**
     * 根据目录 获取码表信息
     * @param categoryId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCodeTableByCategoryId/{categoryId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<Set<CodeInfo>> getCodeTableByCategoryId(@PathVariable String categoryId) throws Exception {
        BaseResponse<Set<CodeInfo>> response = new BaseResponse<Set<CodeInfo>>();
        Set<CodeInfo> codeInfoList = standardservice.getCodeTableByCategoryId(categoryId);
        //response.setData(codeInfoList);
        response.setOtherData(codeInfoList);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setMessage("查询成功");
        return response;
    }





}
