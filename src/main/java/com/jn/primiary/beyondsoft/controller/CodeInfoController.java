package com.jn.primiary.beyondsoft.controller;

import com.jn.primiary.beyondsoft.entity.CodeInfo;
import com.jn.primiary.beyondsoft.entity.OperCodeInfo;
import com.jn.primiary.beyondsoft.entity.OperStandard;
import com.jn.primiary.beyondsoft.service.CodeInfoService;
import com.jn.primiary.beyondsoft.vo.CodeInfoDetailVo;
import com.jn.primiary.beyondsoft.vo.CodeInfoListVo;
import com.jn.primiary.beyondsoft.vo.FieldCodeVo;
import com.jn.primiary.metadata.entity.BaseResponse;
import com.jn.primiary.metadata.entity.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Des 码表管理控制层
 * @Author chenhong
 * @Date 2019/9/26 10:07
 */
@Validated
@Controller
@RequestMapping("/codeinfo")
public class CodeInfoController {


    @Autowired
    CodeInfoService codeInfoService;

    /**
     * 码表列表
     *
     * @return
     */
    @RequestMapping(value = "/getCodeInfoList", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<CodeInfoListVo> getCodeInfoList() {
        BaseResponse<CodeInfoListVo> response = new BaseResponse<CodeInfoListVo>();
        List<CodeInfoListVo> codeList = codeInfoService.getAllCodeInfo();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(codeList);
        response.setMessage("获取信息成功");
        return response;
    }


    /**
     * 码表列表 分页
     *
     * @return
     */
    @RequestMapping(value = "/getCodeInfoListByPage", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getCodeInfoListByPage(@RequestBody Map<String,String> map) {
        Map<String, Object> resultmap = codeInfoService.getAllCodeInfoByPage(map);
        resultmap.put("resultcode",ResultCode.RESULT_SUCCESS);
        resultmap.put("message","获取信息成功");
        //response.setResultCode(ResultCode.RESULT_SUCCESS);
        //response.setData(codeList);
        //response.setMessage("获取信息成功");
        return resultmap;
    }

    /**
     * 新增/编辑
     *
     * @param fieldCodeVo
     * @return
     */
    @RequestMapping(value = "/saveCodeInfo", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<OperCodeInfo> saveCodeInfo(@RequestParam String batchNo,
                                                   @RequestBody FieldCodeVo fieldCodeVo,@RequestParam String isadd) {
        BaseResponse<OperCodeInfo> response = new BaseResponse<OperCodeInfo>();
        if("0".equals(isadd)){
            //新增，判断码表英文名是否重复
            List<CodeInfo> codeInfo = codeInfoService.codeInfoIsExists(fieldCodeVo.getCodeInfoList());
            if (codeInfo.size() != 0) {
                response.setResultCode(ResultCode.RESULT_ERROR);
                response.setMessage("码表已存在");
                return response;
            }
        }

        //String user=this.user.getUsername();
        codeInfoService.saveCodeInfo(batchNo,fieldCodeVo.getCodeInfoList(), fieldCodeVo.getFieldCodeList(),"2001");
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setMessage("获取信息成功");
        return response;


//        BaseResponse<OperCodeInfo> response = new BaseResponse<OperCodeInfo>();
//        String user=this.user.getUsername();
//        String result = codeInfoService.saveCodeInfo(batchNo, fieldCodeVo.getCodeInfoList(), fieldCodeVo.getFieldCodeList(), user);
//        if(!StringUtils.isEmpty(result)){
//            response.setMessage(result);
//            response.setResultCode(ResultCode.RESULT_ERROR);
//        }else{
//            response.setResultCode(ResultCode.RESULT_SUCCESS);
//            response.setMessage("获取信息成功");
//        }
//       return response;

    }

    /**
     * 提交审核
     *
     * @param batchNo
     * @return
     */
    @RequestMapping(value = "/doCodeInfoAudit", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<OperStandard> doStandardAudit(@RequestParam String batchNo) {
        BaseResponse<OperStandard> response = new BaseResponse<OperStandard>();
        codeInfoService.doAudit(batchNo);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setMessage("获取信息成功");
        return response;
    }

    /**
     * 根据码表英文名查看码表
     *
     * @param codeName
     * @param isAuth
     * @param batchNo
     * @return
     */
    @RequestMapping(value = "/getCodeInfoByName", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse getCodeInfoByName(@Pattern(regexp = "^\\w+$",message = "请输入正确的codeName") @RequestParam String codeName,
                                          @Pattern(regexp = "^[0-5]*$",message = "请输入正确的审核状态") @RequestParam String isAuth,
                                          @RequestParam String batchNo) {
        BaseResponse response = new BaseResponse();
        List<CodeInfoDetailVo> detailList = new ArrayList<>();
        CodeInfoDetailVo codeInfoDetailVo = codeInfoService.getCodeInfoByName(codeName, batchNo, isAuth);
        detailList.add(codeInfoDetailVo);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(detailList);
        response.setMessage("获取信息成功");
        return response;
    }

    /**
     * 删除码表
     *
     * @param codeName
     * @return
     */
    @RequestMapping(value = "/deleteCodeInfo", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<OperCodeInfo> deleteCodeInfo(@RequestParam String codeName) {
        BaseResponse<OperCodeInfo> response = new BaseResponse<OperCodeInfo>();
        codeInfoService.deleteCodeInfo(codeName);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setMessage("获取信息成功");
        return response;
    }

    /**
     * 删除未提交的码表数据
     *
     * @param codeName
     * @return
     */
    @RequestMapping(value = "/deleteOperCodeInfo", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<OperCodeInfo> deleteOperCodeInfo(@RequestParam String codeName,@RequestParam String batchNo) {
        BaseResponse<OperCodeInfo> response = new BaseResponse<OperCodeInfo>();
        codeInfoService.deleteOperCodeInfo(codeName,batchNo);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setMessage("获取信息成功");
        return response;
    }


    /**
     * 码表首页，模糊搜索
     */
    @RequestMapping(value = "/searchCodeInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> searchCodeInfo(@RequestBody Map<String,String> map){
        Map<String, Object> resultmap = codeInfoService.searchCodeInfo(map);
        resultmap.put("resultcode",ResultCode.RESULT_SUCCESS);
        resultmap.put("message","获取信息成功");
        return resultmap;
    }


}
