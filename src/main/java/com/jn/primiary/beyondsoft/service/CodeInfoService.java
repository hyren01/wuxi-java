package com.jn.primiary.beyondsoft.service;

import com.jn.primiary.beyondsoft.dao.*;
import com.jn.primiary.beyondsoft.entity.*;
import com.jn.primiary.beyondsoft.util.ComUtil;
import com.jn.primiary.beyondsoft.vo.CodeInfoDetailVo;
import com.jn.primiary.beyondsoft.vo.CodeInfoListVo;
import com.jn.primiary.metadata.entity.ResultCode;
import com.jn.primiary.metadata.utils.CommonUtil;
import com.jn.primiary.metadata.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * @Des 码表管理业务层
 * @Author chenhong
 * @Date 2019/9/26 10:07
 */
@Service
@Transactional
public class CodeInfoService {

    private static final Logger LOGGER = Logger.getLogger(CodeInfoService.class);

    private static final String DATE_CHAR8_FORMAT = "yyyyMMdd";
    private static final String TIME_CHAR6_FORMAT = "HHmmss";

    public static final String MANAGER_ACCOUNT = "admin";

    @Autowired
    CodeInfoRepository codeInfoRepository;
    @Autowired
    OperCodeInfoRepository operCodeInfoRepository;
    @Autowired
    FieldCodeRepository fieldCodeRepository;
    @Autowired
    OperFieldCodeRepository operFieldCodeRepository;
    @Autowired
    FieldRepository fieldRepository;
    @Autowired
    SchemaModuleService moudleService;
    @Autowired
    private ModuleRepository schemaModuleRepository;

    /**
     * 码表列表
     *
     * @return
     */
    public List<CodeInfoListVo> getAllCodeInfo() {
        List<CodeInfoListVo> codeInfoList = new ArrayList<>();
        try {
            //查询码表数据
            List<Object[]> codeInfoObj = operCodeInfoRepository.getAllCodeInfo();
            //组装返回页面的VO
            codeInfoList = ComUtil.castEntity(codeInfoObj, CodeInfoListVo.class);
        } catch (Exception e) {
            LOGGER.info("查询失败", e);
        }
        return codeInfoList;
    }

    /**
     * 码表列表 分页
     *
     * @return
     */
    public Map<String, Object> getAllCodeInfoByPage(Map<String, String> map) throws Exception{
        Map<String, Object> resultmap = new HashMap<>();
        List<CodeInfoListVo> codeInfoList = new ArrayList<>();

        Integer pagenum = Integer.parseInt(map.get("pagenum"));
        Integer size = Integer.parseInt(map.get("size"));
        Integer startindex = pagenum * size - size;
        String key = map.get("key");
        if (StringUtils.isEmpty(key)) {
            Integer count = operCodeInfoRepository.getAllCodeInfoCount();
            resultmap.put("count", count);
            //查询码表数据
            List<Object[]> codeInfoObj = operCodeInfoRepository.getAllCodeInfoByPage(startindex, size);
            //组装返回页面的VO
            codeInfoList = ComUtil.castEntity(codeInfoObj, CodeInfoListVo.class);
            resultmap.put("data", codeInfoList);

            return resultmap;
        } else {
            resultmap = searchCodeInfo(map);
            resultmap.put("resultcode", ResultCode.RESULT_SUCCESS);
            resultmap.put("message","获取信息成功");
            return resultmap;
        }
    }

    /**
     * 查询码表是否存在
     *
     * @param codeInfoList
     * @return
     */
    public List<CodeInfo> codeInfoIsExists(List<OperCodeInfo> codeInfoList) {
        List<CodeInfo> codelist = codeInfoRepository.getCodeInfoByName(codeInfoList.get(0).getCodetableEname());
        String[] allcodeinfoid = new String[codelist.size()];
        for (int i = 0; i < codelist.size(); i++) {
            allcodeinfoid[i] = codelist.get(i).getId();
        }

        Set<String> setList = new HashSet<>();
        List<CodeInfo> codeInfo = new ArrayList<>();
        for (OperCodeInfo operCodeInfo : codeInfoList) {
            setList.add(operCodeInfo.getId());
        }
        List<String> ids = new ArrayList<>(setList);
        String[] a = new String[ids.size()];
        ids.toArray(a);
        codeInfo = codeInfoRepository.getCodeInfoByNameIsExists(codeInfoList.get(0).getCodetableEname(), a);

        return codelist;
    }

    /**
     * 新增/编辑
     *
     * @param codeInfoList
     * @param fieldCodeList
     */
    public String saveCodeInfo(String batchNo, List<OperCodeInfo> codeInfoList, List<OperFieldCode> fieldCodeList, String user) {
        String result = "";
        if (batchNo != "") {
            int count = operCodeInfoRepository.getCountByBatchNo(batchNo);
            if (count != 0) {
                operCodeInfoRepository.deleteByBatchNo(batchNo);
                operFieldCodeRepository.deleteByBatchNo(batchNo);
            }
        } else {
//			String codetableEname = codeInfoList.get(0).getCodetableEname();
//			List<String> existlist = operCodeInfoRepository.isExist(codetableEname);
//			if(existlist.size()>0){
//				result = "码表已存在";
//				return result;
//			}
            batchNo = ComUtil.getBatchNo();
        }
        for (OperCodeInfo operCodeInfo : codeInfoList) {
            //新增
            if (StringUtils.isEmpty(operCodeInfo.getId())) {
                /*新增码表数据进操作表 start*/
                String id = CommonUtil.getUUID();
                operCodeInfo.setCreateUser(user);
                operCodeInfo.setCreateTime(new Date());
                operCodeInfo.setBatchNo(batchNo);
                operCodeInfo.setDataType(StringUtils.isEmpty(operCodeInfo.getDataType()) ? String.valueOf(DataType.UPDATE.getCode()) : operCodeInfo.getDataType());
                operCodeInfo.setId(id);
                operCodeInfo.setStatus(String.valueOf(Status.JIHUO.getCode()));
                operCodeInfo.setIsAuth(String.valueOf(AuthType.BIANJI.getCode()));
                operCodeInfoRepository.saveOperCodeInfo(operCodeInfo);
                /*新增码表数据进操作表 end*/
                for (OperFieldCode operFieldCode : fieldCodeList) {
                    /*新增码表字段关联关系数据进操作表 start*/
                    operFieldCode.setBatchNo(batchNo);
                    operFieldCode.setCodeId(id);
                    operFieldCode.setCodeName(operCodeInfo.getCodetableEname());
                    operFieldCode.setDataType(StringUtils.isEmpty(operCodeInfo.getDataType()) ? String.valueOf(DataType.UPDATE.getCode()) : operCodeInfo.getDataType());
                    //根据字段id，找到所属的目录
                    String fieldId = operFieldCode.getFieldId();
                    String categoryId = schemaModuleRepository.getCategoryIdByFieldId(fieldId);
                    operFieldCode.setCategory_id(categoryId);
                    operFieldCodeRepository.saveOperFieldCode(operFieldCode);
                    /*新增码表字段关联关系数据进操作表 end*/
                }
                //编辑
            } else {
                /*编辑码表数据进操作表 start*/
                operCodeInfo.setCreateUser(user);
                operCodeInfo.setCreateTime(new Date());
                operCodeInfo.setUpdateUser(user);
                operCodeInfo.setUpdateTime(new Date());
                operCodeInfo.setBatchNo(batchNo);
                operCodeInfo.setDataType(StringUtils.isEmpty(operCodeInfo.getDataType()) ? String.valueOf(DataType.UPDATE.getCode()) : operCodeInfo.getDataType());
                operCodeInfo.setStatus(String.valueOf(Status.JIHUO.getCode()));
                operCodeInfo.setIsAuth(String.valueOf(AuthType.DSH.getCode()));
                operCodeInfoRepository.saveOperCodeInfo(operCodeInfo);
                /*编辑码表数据进操作表 end*/
                for (OperFieldCode operFieldCode : fieldCodeList) {
                    /*编辑码表字段关联关系数据进操作表 start*/
                    operFieldCode.setBatchNo(batchNo);
                    operFieldCode.setCodeId(operCodeInfo.getId());
                    operFieldCode.setCodeName(operCodeInfo.getCodetableEname());
                    operFieldCode.setDataType(String.valueOf(DataType.UPDATE.getCode()));
                    String fieldId = operFieldCode.getFieldId();
                    String categoryId = schemaModuleRepository.getCategoryIdByFieldId(fieldId);
                    operFieldCode.setCategory_id(categoryId);
                    operFieldCodeRepository.saveOperFieldCode(operFieldCode);
                    /*编辑码表字段关联关系数据进操作表 end*/
                }
            }

        }
        return result;
    }

    public void doAudit(String batchNo) {
        operCodeInfoRepository.doAudit(batchNo);
    }

    /**
     * 根据码表英文名查看码表数据
     *
     * @param codeName
     * @param batchNo
     * @param isAuth
     * @return
     */
    public CodeInfoDetailVo getCodeInfoByName(String codeName, String batchNo, String isAuth) {
        CodeInfoDetailVo detailVo = new CodeInfoDetailVo();
        List<Standard> stdList = new ArrayList<>();
        //待审核查操作表数据
        if (isAuth.equals(String.valueOf(AuthType.DSH.getCode())) ||
                isAuth.equals(String.valueOf(AuthType.BIANJI.getCode()))) {
            //查询码表信息
            List<OperCodeInfo> operCodeInfoList = operCodeInfoRepository.getCodeInfoByName(codeName, batchNo);
            detailVo.setCodeInfoList(operCodeInfoList);

//			//查询码表关联的所有字段ID
            String[] fieldId = operFieldCodeRepository.getFieldCodeByCodeName(batchNo, codeName);
            detailVo.setFieldId(fieldId);
//			if(fieldId.length!=0){
//				//查询字段所关联的标准ID
//				String[] moudleId=fieldRepository.getMoudleIdByFieleId(fieldId);
//				//根据标准ID查询标准信息
//				for(int i=0;i<moudleId.length;i++){
//					Standard std=moudleService.getStandardById(moudleId[i],null);
//					stdList.add(std);
//				}
//				detailVo.setMoudleList(stdList);
//			}
            return detailVo;
            //查正式表数据
        } else {
            //查询码表信息
            List<CodeInfo> codeInfoList = codeInfoRepository.getCodeInfoByName(codeName);
            detailVo.setCodeInfoList(codeInfoList);
//			//查询码表关联的所有字段ID
            String[] fieldId = fieldCodeRepository.getFieldCodeByCodeName(codeName);
            detailVo.setFieldId(fieldId);
//			if(fieldId.length!=0){
//				//查询字段所关联的标准ID
//				String[] moudleId=fieldRepository.getMoudleIdByFieleId(fieldId);
//				//根据标准ID查询标准信息
//				for(int i=0;i<moudleId.length;i++){
//					Standard std=moudleService.getStandardById(moudleId[i],null);
//					stdList.add(std);
//				}
//				detailVo.setMoudleList(stdList);
//			}
            return detailVo;
        }
    }

    /**
     * 删除码表
     *
     * @param codeName
     */
    public void deleteCodeInfo(String codeName) {
        String batchNo = ComUtil.getBatchNo();
        operCodeInfoRepository.deleteCodeInfo(String.valueOf(AuthType.DSH.getCode()), batchNo, String.valueOf(DataType.DELETE.getCode()), codeName);
        operFieldCodeRepository.deleteOperFieldCode(batchNo, codeName);
    }

    /**
     * 删除未提交的码表数据
     *
     * @param codeName
     */
    public void deleteOperCodeInfo(String codeName, String batchNo) {
        operCodeInfoRepository.deleteByIdAndBatchNo(batchNo, codeName);
        operFieldCodeRepository.deleteByIdAndBatchNo(batchNo, codeName);
    }


    public Map<String, Object> searchCodeInfo(Map<String, String> map) {
        Map<String, Object> resultmap = new HashMap<>();
        List<CodeInfoListVo> codeInfoList = new ArrayList<>();
        try {
            String searchword = map.get("key");
            String pagenum = map.get("pagenum");
            String size = map.get("size");

            int startindex = Integer.valueOf(pagenum) * Integer.valueOf(size) - Integer.valueOf(size);

            Integer count = operCodeInfoRepository.searchCodeInfoCount(searchword);
            resultmap.put("count", count);
            List<Object[]> codeInfoObj = operCodeInfoRepository.searchCodeInfo(searchword, startindex, Integer.parseInt(size));
            //组装返回页面的VO
            codeInfoList = ComUtil.castEntity(codeInfoObj, CodeInfoListVo.class);
            resultmap.put("data", codeInfoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultmap;
    }
}

