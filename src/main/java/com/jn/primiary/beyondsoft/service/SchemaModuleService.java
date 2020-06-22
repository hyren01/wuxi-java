package com.jn.primiary.beyondsoft.service;

import com.jn.config.JDBCHelper;
import com.jn.primiary.beyondsoft.api.vo.FieldVo;
import com.jn.primiary.beyondsoft.dao.*;
import com.jn.primiary.beyondsoft.entity.*;
import com.jn.primiary.beyondsoft.util.ComUtil;
import com.jn.primiary.beyondsoft.util.PageUtil;
import com.jn.primiary.beyondsoft.util.UserContextUtil;
import com.jn.primiary.beyondsoft.vo.*;
import com.jn.primiary.metadata.utils.CommonUtil;
import com.jn.primiary.metadata.utils.StringUtils;
import com.jn.primiary.office.dao.TbStdglFiletableRepository;
import com.jn.primiary.office.service.TbDictionaryService;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;
//import com.jn.stdtest.entity.ReturnInf;


/**
 * @Des 标准管理业务层
 * @Author chenhong
 * @Date 2019/9/23 10:07
 */
@Service
@Transactional
public class SchemaModuleService {

    @Autowired
    private ModuleRepository schemaModuleRepository;
    @Autowired
    private FieldRepository schemaFieldRepository;
    @Autowired
    private ObjectRepository objectRepository;
    @Autowired
    private OperModuleRepository operModuleRepository;
    @Autowired
    private OperFieldRepository operFieldRepository;
    @Autowired
    private OperObjectRepository operObjectRepository;
    @Autowired
    private OperCodeInfoRepository operCodeInfoRepository;
    @Autowired
    private CodeInfoRepository codeInfoRepository;
    @Autowired
    private FieldCodeRepository fieldCodeRepository;
    @Autowired
    private TbStdglFiletableRepository fileRepository;
    @Autowired
    private CheckRepository fieldCheckRepository;
    @Autowired
    private TbDictionaryService tbDictionaryService;
    @Autowired
    CategoryRepository categoryRepository;


    private Logger logger = Logger.getLogger(SchemaModuleService.class);

    public List<Standard> getAllStd() throws Exception {
        return schemaModuleRepository.getAllStandardInfo();
    }

    /**
     * 查询标准列表
     *
     * @return
     */
    public List<StandardVo> getAllStandardInfo(String categoryId) throws Exception {
        List<StandardVo> operStandardList = new ArrayList<>();
        //如果根目录（categoryId = 0），就把categoryId =“”
        if ("0".equals(categoryId)) {
            categoryId = "";
        }
        List<Object[]> objList = operModuleRepository.getAllStandardInfo(categoryId);
        if (objList.size() > 0) {
            operStandardList = ComUtil.castEntity(objList, StandardVo.class);
        }

        return operStandardList;
    }

    /**
     * 查询审核通过的标准列表
     *
     * @return
     */
    public List<StandardVo> getAllSHTGStandardInfo(String categoryId) throws Exception {
        List<StandardVo> operStandardList = new ArrayList<>();
        if ("0".equals(categoryId)) {
            categoryId = "";
        }
        List<Object[]> objList = operModuleRepository.getAllSHTGStandardInfo(categoryId);
        if (objList.size() > 0) {
            operStandardList = ComUtil.castEntity(objList, StandardVo.class);
        }

        return operStandardList;
    }

    /**
     * 递归     查询审核通过的标准列表
     * @param categoryId
     * @return
     * @throws Exception
     */
    public List<StandardVo> getAllSHTGStandardInfoRecursive(String categoryId) throws Exception {
        List<StandardVo> operStandardList = new ArrayList<>();
        if ("0".equals(categoryId)) {
            categoryId = "";
        }

        //获取当前目录下的所有子目录
        List<String> childCategoryIdList = new ArrayList<>();
        childCategoryIdList.add(categoryId);
        getCategoryChild(categoryId,childCategoryIdList);

        for (int i = 0; i < childCategoryIdList.size(); i++) {
            String tmpCategoryId = childCategoryIdList.get(i);
            logger.info("category_id:"+tmpCategoryId);
            List<Object[]> objList = operModuleRepository.getAllSHTGStandardInfo(tmpCategoryId);
            operStandardList.addAll(ComUtil.castEntity(objList, StandardVo.class));
        }

        List<StandardVo> sortedlist = operStandardList.stream().sorted(Comparator.comparing(StandardVo::getName)).collect(Collectors.toList());

        return sortedlist;
    }

    /**
     * 查询标准列表   分页
     *
     * @return
     */
    /*public Map<String, Object> getAllStandardInfoByPage(String categoryId, String pagenum, String size) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<StandardVo> operStandardList = new ArrayList<>();
        //如果根目录（categoryId = 0），就把categoryId =“”
        if ("0".equals(categoryId)) {
            categoryId = "";
        }

        int startindex = Integer.valueOf(pagenum) * Integer.valueOf(size) - Integer.valueOf(size);
        Integer count = operModuleRepository.getAllStandardInfoCount(categoryId);
        map.put("count", count);

        List<Object[]> objList = operModuleRepository.getAllStandardInfoByPage(categoryId, startindex, Integer.valueOf(size));
        if (objList.size() > 0) {
            operStandardList = ComUtil.castEntity(objList, StandardVo.class);
        }
        map.put("operStandardList", operStandardList);
        return map;
    }*/

    public Map<String, Object> getAllStandardInfoByPage(String categoryId, String pagenum, String size) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<StandardVo> operStandardList = new ArrayList<>();
        //如果根目录（categoryId = 0），就把categoryId =“”
        if (StringUtils.isEmpty(categoryId)) {
            categoryId = "0";
        }

        //获取当前目录下的所有子目录
        List<String> childCategoryIdList = new ArrayList<>();
        childCategoryIdList.add(categoryId);
        getCategoryChild(categoryId,childCategoryIdList);

        //int startindex = Integer.valueOf(pagenum) * Integer.valueOf(size) - Integer.valueOf(size);
        int count = 0;
        for (int i = 0; i < childCategoryIdList.size(); i++) {
            count += operModuleRepository.getAllStandardInfoCount(childCategoryIdList.get(i));

            List<Object[]> objList = operModuleRepository.getAllStandardInfo(childCategoryIdList.get(i));
            if (objList.size() > 0) {
                operStandardList.addAll(ComUtil.castEntity(objList, StandardVo.class));
            }
        }

        List<StandardVo> resultList = PageUtil.doPage(operStandardList,Integer.valueOf(pagenum),Integer.valueOf(size));
        map.put("count", count);
        map.put("operStandardList", resultList);

        return map;
    }

    /**
     * 递归找某个目录下的子目录
     * @param categoryId
     */
    public void getCategoryChild(String categoryId,List<String> childCategoryIdList){
        List<CategoryInfo> categoryInfoList = categoryRepository.findCategoryInfoByParentId(categoryId);
        if(categoryInfoList.size() > 0 ){
            for (int i = 0; i < categoryInfoList.size(); i++) {
                childCategoryIdList.add(categoryInfoList.get(i).getId());
                getCategoryChild(categoryInfoList.get(i).getId(),childCategoryIdList);
            }
        }
    }

    /**
     * 新增
     * 数据进操作表
     *
     * @param operStandard 标准实体
     */
    public void saveField(OperStandard operStandard, String user) {
        /*修改的标准数据进操作表 start*/
        String batchNo = ComUtil.getBatchNo();
        operStandard.setBatchNo(batchNo);
        operStandard.setId(CommonUtil.getUUID());
        operStandard.setDataType(String.valueOf(DataType.ADD.getCode()));
        operStandard.setCreatePerson(user);
        operStandard.setCreateTime(new Date());
        operStandard.setIsAuth(String.valueOf(AuthType.BIANJI.getCode()));
        operStandard.setStatus(String.valueOf(Status.JIHUO.getCode()));
        operModuleRepository.saveOperMoudle(operStandard);
        /*修改的标准数据进操作表 end*/
        //新增复合型对象(如果有)
        if (operStandard.getStandardObject().size() > 0) {
            for (OperStandardObject operObject : operStandard.getStandardObject()) {
                /*新增的标准对象数据进操作表 start*/
                operObject.setBatchNo(batchNo);
                operObject.setObjId(CommonUtil.getUUID());
                operObject.setDataType(String.valueOf(DataType.ADD.getCode()));
                operObject.setMoudleId(operStandard.getId());
                operObject.setStatus(String.valueOf(Status.JIHUO.getCode()));
                operObjectRepository.saveOperObject(operObject);
                /*新增的标准对象数据进操作表 end*/
                //复合对象下的属性
                for (OperStandardField operField : operObject.getStandardField()) {
                    /*新增的字段数据进操作表 start*/
                    operField.setBatchNo(batchNo);
                    operField.setId(CommonUtil.getUUID());
                    operField.setDataType(String.valueOf(DataType.ADD.getCode()));
                    operField.setMoudleId(operStandard.getId());
                    operField.setObjId(operObject.getObjId());
                    operField.setCreatePerson(user);
                    operField.setCreateTime(new Date());
                    operField.setStatus(String.valueOf(Status.JIHUO.getCode()));
                    operFieldRepository.saveOperFeild(operField);
                    /*新增的字段数据进操作表 end*/
                }
            }
        } else {
            //新增标准字段
            for (OperStandardField operField : operStandard.getStandardField()) {
                /*新增的字段数据进操作表 start*/
                operField.setBatchNo(batchNo);
                operField.setId(CommonUtil.getUUID());
                operField.setDataType(String.valueOf(DataType.ADD.getCode()));
                operField.setMoudleId(operStandard.getId());
                operField.setCreatePerson(user);
                operField.setCreateTime(new Date());
                operField.setStatus(String.valueOf(Status.JIHUO.getCode()));
                operFieldRepository.saveOperFeild(operField);
                /*新增的字段数据进操作表 end*/
            }
        }

    }

    /**
     * 修改
     * 修改数据进操作表
     *
     * @param operStandard 实体
     */
    public void updateField(OperStandard operStandard, String user) {
        String batchNo = "";
        int count = operModuleRepository.getCountByBatchNo(operStandard.getBatchNo());
        if (count != 0) {
            operModuleRepository.deleteByBatchNo(operStandard.getBatchNo());
            operFieldRepository.deleteFeildByBatchNo(operStandard.getBatchNo());
            operObjectRepository.deleteObjectByBatchNo(operStandard.getBatchNo());
            batchNo = operStandard.getBatchNo();
        } else {
            operStandard.setDataType(String.valueOf(DataType.UPDATE.getCode()));
            batchNo = ComUtil.getBatchNo();
        }
        /*修改的标准数据进操作表 start*/
        //String batchNo = CommonUtil.getUUID();//操作编号
        operStandard.setBatchNo(batchNo);
        operStandard.setDataType(operStandard.getDataType());
        operStandard.setCreatePerson(user);
        operStandard.setCreateTime(new Date());
        operStandard.setUpdatePerson(user);
        operStandard.setUpdateTime(new Date());
        operStandard.setIsAuth(String.valueOf(AuthType.DSH.getCode()));
        operStandard.setStatus(String.valueOf(Status.JIHUO.getCode()));
        operModuleRepository.saveOperMoudle(operStandard);
        /*修改的标准数据进操作表 end*/
        //对象（如果有）
        if (operStandard.getStandardObject().size() > 0) {
            for (OperStandardObject operObject : operStandard.getStandardObject()) {
                /*修改的标准对象数据进操作表 start*/
                if (StringUtils.isEmpty(operObject.getObjId())) {
                    operObject.setObjId(CommonUtil.getUUID());
                    operObject.setMoudleId(operStandard.getId());
                }
                operObject.setBatchNo(batchNo);
                operObject.setDataType(operStandard.getDataType());
                operObject.setStatus(String.valueOf(Status.JIHUO.getCode()));
                operObjectRepository.saveOperObject(operObject);
                /*修改的标准对象数据进操作表 end*/
                //处理复合对象下的属性
                for (OperStandardField operField : operObject.getStandardField()) {
                    /*修改的标准字段数据进操作表 start*/
                    if (StringUtils.isEmpty(operField.getId())) {
                        operField.setId(CommonUtil.getUUID());
                        operField.setMoudleId(operStandard.getId());
                        operField.setObjId(operObject.getObjId());
                    }
                    operField.setBatchNo(batchNo);
                    operField.setDataType(operStandard.getDataType());
                    operField.setStatus(String.valueOf(Status.JIHUO.getCode()));
                    operFieldRepository.saveOperFeild(operField);
                    /*修改的标准字段数据进操作表 end*/
                }
            }
        } else {
            for (OperStandardField operField : operStandard.getStandardField()) {
                /*修改的标准字段数据进操作表 end*/
                if (StringUtils.isEmpty(operField.getId())) {
                    operField.setId(CommonUtil.getUUID());
                    operField.setMoudleId(operStandard.getId());
                }
                operField.setBatchNo(batchNo);
                operField.setDataType(operStandard.getDataType());
                operField.setStatus(String.valueOf(Status.JIHUO.getCode()));
                operFieldRepository.saveOperFeild(operField);
                /*修改的标准字段数据进操作表 end*/
            }
        }

    }

    public void doAudit(String batchNo) {
        operModuleRepository.doAudit(batchNo);
    }

    /**
     * 根据ID查询标准
     *
     * @param stdId
     * @param version
     * @return
     */
    public Standard getStandardById(String stdId, String version) {
        //获取标准
        Standard moudle = schemaModuleRepository.getStandardById(stdId, version);
        //获取对象
        List<StandardObject> objectList = objectRepository.getObjectById(stdId);
        //List<StandardObjectVo> objectList=new ArrayList<>();
        if (objectList.size() > 0) {
            for (StandardObject object : objectList) {
                List<StandardField> fieldList = schemaFieldRepository.getFieldById(stdId, object.getObjId());
                object.setStandardField(fieldList);
            }
            //objectList = ComUtil.castEntity(object, StandardObjectVo.class);
            moudle.setStandardField(new ArrayList<>());
        } else {
            //获取字段
            List<StandardField> fieldList = schemaFieldRepository.getFieldById(stdId, null);
            moudle.setStandardField(fieldList);
        }
        moudle.setStandardObject(objectList);

        return moudle;
    }


    /**
     * 根据ID版本查询历史标准
     *
     * @param stdId
     * @param version
     * @return
     */
    public Standard getStandardByIdVersion(String stdId, String version) {
        //获取标准
        Standard moudle = schemaModuleRepository.getStandardById(stdId, version);
        //获取对象
        List<StandardObject> objectList = objectRepository.getObjectByIdVersion(stdId, version);
        if (objectList.size() > 0) {
            for (StandardObject object : objectList) {
                List<StandardField> fieldList = schemaFieldRepository.getFieldByIdAndVersion(stdId, object.getObjId(), version);
                object.setStandardField(fieldList);
            }
            moudle.setStandardField(new ArrayList<>());
        } else {
            //获取字段
            List<StandardField> fieldList = schemaFieldRepository.getFieldByIdAndVersion(stdId, null, version);
            moudle.setStandardField(fieldList);
        }
        moudle.setStandardObject(objectList);
        return moudle;
    }

    /**
     * 批量删除
     *
     * @param deleteVo
     */
    public void deleteStandardById(List<StandardDeleteVo> deleteVo) {
        for (StandardDeleteVo standardDeleteVo : deleteVo) {
            if (standardDeleteVo.getIsAuth().equals(String.valueOf(AuthType.BIANJI.getCode()))) {
                //正式表的标准数据进操作表
                operModuleRepository.deleteByIdAndBatchNo(standardDeleteVo.getId(), standardDeleteVo.getBatchNo());
                //正式表的标准对象数据进操作表
                operObjectRepository.deleteByIdAndBatchNo(standardDeleteVo.getId(), standardDeleteVo.getBatchNo());
                //正式表的标准字段数据进操作表
                operFieldRepository.deleteByIdAndBatchNo(standardDeleteVo.getId(), standardDeleteVo.getBatchNo());
            } else if (standardDeleteVo.getIsAuth().equals(String.valueOf(AuthType.SHTG.getCode()))) {
                String batchNo = ComUtil.getBatchNo();
                //正式表的标准数据进操作表
                operModuleRepository.deleteMoudle(String.valueOf(AuthType.DSH.getCode()), batchNo, String.valueOf(DataType.DELETE.getCode()), standardDeleteVo.getId());
                //正式表的标准对象数据进操作表
                operObjectRepository.deleteObject(batchNo, String.valueOf(DataType.DELETE.getCode()), standardDeleteVo.getId());
                //正式表的标准字段数据进操作表
                operFieldRepository.deleteFeild(batchNo, String.valueOf(DataType.DELETE.getCode()), standardDeleteVo.getId());
            }
        }

    }

    /**
     * 查看历史版本
     *
     * @param stdId
     * @return
     */
    public List<Object[]> getOldVersion(String stdId) {
        List<Object[]> oldVersionList = schemaModuleRepository.getOldVersionList(stdId);
        return oldVersionList;
    }

    /**
     * 历史版本激活
     *
     * @param stdId
     * @param version
     */
    public void getOldVersionOpen(String stdId, String version) {
        //查询标准当前有效版本
        Standard moudle = schemaModuleRepository.getStandardById(stdId, null);
        //将当前有效版本置为无效
        schemaModuleRepository.updateStatus(stdId, moudle.getVersion());
        schemaFieldRepository.updateStatus(stdId, moudle.getVersion());
        objectRepository.updateStatus(stdId, moudle.getVersion());
        //将页面上选择的版本置为有效
        schemaModuleRepository.udpateStatusByIdAndVersion(stdId, version);
        schemaFieldRepository.udpateStatusByIdAndVersion(stdId, version);
        objectRepository.udpateStatusByIdAndVersion(stdId, version);
    }

    /**
     * 审核列表
     *
     * @return
     * @throws Exception
     */
    public List<StandardCodeAuditVo> getAuditList() throws Exception {
        //标准列表
        List<Object[]> moudleObject = operModuleRepository.getOperList();
        //码表列表
        List<Object[]> codeInfoObject = operCodeInfoRepository.getOperCodeInfoList();
        /*组装返回页面的VO start*/
        List<StandardCodeAuditVo> moudleList = ComUtil.castEntity(moudleObject, StandardCodeAuditVo.class);
        List<StandardCodeAuditVo> codeInfoList = ComUtil.castEntity(codeInfoObject, StandardCodeAuditVo.class);
        //合并结果集，按创建时间排序
        List<StandardCodeAuditVo> totList = new ArrayList<>();
        totList = moudleList;
        totList.removeAll(codeInfoList);
        totList.addAll(codeInfoList);
        Collections.sort(totList, new Comparator<StandardCodeAuditVo>() {
            @Override
            public int compare(StandardCodeAuditVo v1, StandardCodeAuditVo v2) {
                if (v1.getCreateTime() != null) {
                    return v1.getCreateTime().compareTo(v2.getCreateTime());
                } else {
                    return 0;
                }
            }
        });
        /*组装返回页面的VO end*/
        return totList;
    }

    /**
     * 根据ID查询标准
     *
     * @return
     */
    public OperStandard getStandardByBatchNo(String batchNo, String stdId) {
        //获取标准
        OperStandard moudle = operModuleRepository.getStandardByBatchNo(batchNo, stdId);
        //获取对象
        List<OperStandardObject> objectList = operObjectRepository.getObjectByBatchNo(batchNo, stdId);
        if (objectList.size() > 0) {
            for (OperStandardObject object : objectList) {
                List<OperStandardField> fieldList = operFieldRepository.getFieldByBatchNo(batchNo, object.getObjId(), stdId);
                object.setStandardField(fieldList);
            }
            //objectList = ComUtil.castEntity(object, StandardObjectVo.class);
            moudle.setStandardField(new ArrayList<>());
        } else {
            //获取字段
            List<OperStandardField> fieldList = operFieldRepository.getFieldByBatchNo(batchNo, null, stdId);
            moudle.setStandardField(fieldList);
        }
        moudle.setStandardObject(objectList);

        return moudle;
    }

    /**
     * 审核
     *
     * @param auditVo
     */
    public void auditStandard(AuditVo auditVo) {
        Set<String> fileIdSetList = new HashSet<>();
        if (auditVo.getIsAuth().equals(String.valueOf(AuthType.SHTG.getCode()))) {
            if (auditVo.getMoudleList().size() > 0) {
                //标准审核通过
                moudleAudit(auditVo, fileIdSetList);
            }
            if (auditVo.getCodeInfoList().size() > 0) {
                //码表审核通过
                codeInfoAudit(auditVo, fileIdSetList);
            }
            //审核拒绝
        } else {
            refused(auditVo, fileIdSetList);

        }
        //如果是文件上传的数据
//        List<String> fileIdList=new ArrayList<>(fileIdSetList);
//        if(fileIdList.size()>0){
//            //所有记录都为通过/拒绝时，修改审核状态
//            for (int i=0;i<fileIdList.size();i++){
//                List<OperStandard> operMoudleList = operModuleRepository.getStandardByFileId(fileIdList.get(i),auditVo.getIsAuth());
//                List<OperCodeInfo> operCodeInfoList= operCodeInfoRepository.getOperCodeInfoByFileId(fileIdList.get(i),auditVo.getIsAuth());
//                if(operMoudleList.size()==0&&operCodeInfoList.size()==0){
//                    fileRepository.audit(auditVo.getIsAuth(), fileIdList.get(i));
//                }
//            }
//        }
//
//        //如果是数据库导入标准的数据
//        List<String> db_fileIdList=new ArrayList<>(db_fileIdSetList);
//        if(db_fileIdList.size()>0){
//            //所有记录都为通过/拒绝时，修改审核状态
//            for (int i=0;i<db_fileIdList.size();i++){
//                List<OperStandard> operMoudleList = operModuleRepository.getStandardByDbId(db_fileIdList.get(i),auditVo.getIsAuth());
//                List<OperCodeInfo> operCodeInfoList= operCodeInfoRepository.getOperCodeInfoByDbId(db_fileIdList.get(i),auditVo.getIsAuth());
//                if(operMoudleList.size()==0&&operCodeInfoList.size()==0){
//                    fileRepository.audit_db(auditVo.getIsAuth(), db_fileIdList.get(i));
//                }
//            }
//        }

    }


    private void refused(AuditVo auditVo, Set<String> fileIdSetList) {
        //标准审核拒绝
        for (OperStandard operStandard : auditVo.getMoudleList()) {
            operModuleRepository.audit(auditVo.getIsAuth(), auditVo.getAuthRemark(), operStandard.getBatchNo(), operStandard.getId());
            //如果是文件新增
            if (!StringUtils.isEmpty(operStandard.getFileId())) {
                fileIdSetList.add(operStandard.getFileId());
            }
        }
        //码表审核拒绝
        for (OperCodeInfo operCodeInfo : auditVo.getCodeInfoList()) {
            operCodeInfoRepository.audit(auditVo.getIsAuth(), auditVo.getAuthRemark(), operCodeInfo.getBatchNo(), operCodeInfo.getCodetableEname());
            //如果是文件新增
            if (!StringUtils.isEmpty(operCodeInfo.getFileId())) {
                fileIdSetList.add(operCodeInfo.getFileId());
            }

        }
    }

    private void codeInfoAudit(AuditVo auditVo, Set<String> fileIdList) {
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        Connection conn = jdbcHelper.getConnection();
        String tmp_codeinfotablename = "";
        for (OperCodeInfo operCodeInfo : auditVo.getCodeInfoList()) {
            tmp_codeinfotablename = "";
            String create_sql = "create table " + operCodeInfo.getCodetableEname() + " (id varchar(32),code_ename varchar(512)," +
                    "code_cname varchar(512),code_value varchar(512)) engine = InnoDB default charset = utf8";
            String insert_sql = "insert into " + operCodeInfo.getCodetableEname() + "(id,code_ename,code_cname,code_value) values " +
                    "(?,?,?,?)";
            String drop_sql = "drop table " + operCodeInfo.getCodetableEname();

            operCodeInfoRepository.audit(auditVo.getIsAuth(), auditVo.getAuthRemark(), operCodeInfo.getBatchNo(), operCodeInfo.getCodetableEname());
            //如果是文件新增的数据
            if (!StringUtils.isEmpty(operCodeInfo.getFileId())) {
                fileIdList.add(operCodeInfo.getFileId());
            }

            //新增
            if (operCodeInfo.getDataType().equals(String.valueOf(DataType.ADD.getCode()))) {
                codeInfoRepository.codeInfoOperTofinal(operCodeInfo.getBatchNo(), operCodeInfo.getCodetableEname());
                fieldCodeRepository.fieldCodeInfoOperTofinal(operCodeInfo.getBatchNo(), operCodeInfo.getCodetableEname());


                if (StringUtils.isEmpty(tmp_codeinfotablename)) {
                    tmp_codeinfotablename = operCodeInfo.getCodetableEname();

                    //判断表在库里是否存在
                    boolean isexist = jdbcHelper.table_is_exist(conn, tmp_codeinfotablename);
                    if (isexist == false) {
                        jdbcHelper.createTable(conn, create_sql);
                    } else {
                        jdbcHelper.dropTable(conn, drop_sql);
                        jdbcHelper.createTable(conn, create_sql);
                    }
                }

                List<CodeInfo> codeinfolist = codeInfoRepository.getcodebybatchnoandename(operCodeInfo.getBatchNo(), operCodeInfo.getCodetableEname());
                for (CodeInfo codeInfo : codeinfolist) {
                    jdbcHelper.insertTable(conn, insert_sql, codeInfo);
                }
                //修改
            } else if (operCodeInfo.getDataType().equals(String.valueOf(DataType.UPDATE.getCode()))) {
                codeInfoRepository.deleteCodeInfo(operCodeInfo.getCodetableEname());
                codeInfoRepository.codeInfoOperTofinal(operCodeInfo.getBatchNo(), operCodeInfo.getCodetableEname());
                fieldCodeRepository.deleteFieldCode(operCodeInfo.getCodetableEname());
                fieldCodeRepository.fieldCodeInfoOperTofinal(operCodeInfo.getBatchNo(), operCodeInfo.getCodetableEname());

                boolean isexist = jdbcHelper.table_is_exist(conn, operCodeInfo.getCodetableEname());
                if (isexist == false) {
                    jdbcHelper.createTable(conn, create_sql);
                } else {
                    jdbcHelper.dropTable(conn, drop_sql);
                    jdbcHelper.createTable(conn, create_sql);
                }

                List<CodeInfo> codeinfolist = codeInfoRepository.getcodebybatchnoandename(operCodeInfo.getBatchNo(), operCodeInfo.getCodetableEname());
                for (CodeInfo codeInfo : codeinfolist) {
                    jdbcHelper.insertTable(conn, insert_sql, codeInfo);
                }

                //删除
            } else if (operCodeInfo.getDataType().equals(String.valueOf(DataType.DELETE.getCode()))) {
                fieldCodeRepository.deleteFieldCode(operCodeInfo.getCodetableEname());
                codeInfoRepository.deleteCodeInfo(operCodeInfo.getCodetableEname());

                jdbcHelper.dropTable(conn, drop_sql);
            }
        }
    }

    private void moudleAudit(AuditVo auditVo, Set<String> fileIdList) {
        for (OperStandard operStandard : auditVo.getMoudleList()) {
            //审核通过/拒绝
            operModuleRepository.audit(auditVo.getIsAuth(), auditVo.getAuthRemark(), operStandard.getBatchNo(), operStandard.getId());
            //如果是文件新增的数据
            if (!StringUtils.isEmpty(operStandard.getFileId())) {
                fileIdList.add(operStandard.getFileId());
            }

            //新增
            if (operStandard.getDataType().equals(String.valueOf(DataType.ADD.getCode()))) {
                //操作表进正式表
                schemaModuleRepository.moudleOperTofinal(operStandard.getBatchNo(), 1, operStandard.getId());
                schemaFieldRepository.fieldOperTofinal(operStandard.getBatchNo(), 1, operStandard.getId());
                objectRepository.objectOperTofinal(operStandard.getBatchNo(), 1, operStandard.getId());
                //修改
            } else if (operStandard.getDataType().equals(String.valueOf(DataType.UPDATE.getCode()))) {
                //查询正式表当前版本
                String version = schemaModuleRepository.getVersion(operStandard.getId());
                //当前版本置为无效
                schemaModuleRepository.updateStatus(operStandard.getId(), version);
                schemaFieldRepository.updateStatus(operStandard.getId(), version);
                objectRepository.updateStatus(operStandard.getId(), version);
                //操作表进正式表
                schemaModuleRepository.moudleOperTofinal(operStandard.getBatchNo(), Integer.parseInt(version) + 1, operStandard.getId());
                schemaFieldRepository.fieldOperTofinal(operStandard.getBatchNo(), Integer.parseInt(version) + 1, operStandard.getId());
                objectRepository.objectOperTofinal(operStandard.getBatchNo(), Integer.parseInt(version) + 1, operStandard.getId());

                //修改标准，通过后，对标过该标准的模型表，要重新检测
                String stdId = operStandard.getId();
                List<FieldCheckResult> resultByStdIdList = fieldCheckRepository.findByStdId(stdId);
                if (resultByStdIdList.size() > 0) {

                }

                //删除
            } else if (operStandard.getDataType().equals(String.valueOf(DataType.DELETE.getCode()))) {
                //查询正式表当前版本
                String version = schemaModuleRepository.getVersion(operStandard.getId());
                //当前版本置为无效
                schemaModuleRepository.updateStatus(operStandard.getId(), version);
                schemaFieldRepository.updateStatus(operStandard.getId(), version);
                objectRepository.updateStatus(operStandard.getId(), version);
            }
        }
    }

    /**
     * 查询有效的标准列表
     *
     * @return
     */
    public List<Standard> getStandard() throws Exception {

        return schemaModuleRepository.getAllStandardInfo();
    }

    public Map<String, Object> searchStandard(Map<String, String> map) throws Exception {
        Map<String, Object> resultmap = new HashMap<>();
        String searchword = map.get("key");
        String pagenum = map.get("pagenum");
        String size = map.get("size");
        List<StandardVo> operStandardList = new ArrayList<>();

        int startindex = Integer.valueOf(pagenum) * Integer.valueOf(size) - Integer.valueOf(size);
        Integer count = operModuleRepository.searchStandardCount(searchword);
        resultmap.put("count", count);

        List<Object[]> objList = operModuleRepository.searchStandard(searchword, startindex, Integer.valueOf(size));
        if (objList.size() > 0) {
            operStandardList = ComUtil.castEntity(objList, StandardVo.class);
        }
        resultmap.put("operStandardList", operStandardList);
        return resultmap;

    }

    /**
     * 确定导入数据库
     *
     * @param deleteVo
     */
    public void confirmAddToDb(String dbType, List<StandardDeleteVo> deleteVo) {
        for (StandardDeleteVo standardDeleteVo : deleteVo) {
            //调用无锡接口

            /*if(standardDeleteVo.getIsAuth().equals(String.valueOf(AuthType.BIANJI.getCode()))){

            }else if(standardDeleteVo.getIsAuth().equals(String.valueOf(AuthType.SHTG.getCode()))){
                String batchNo = ComUtil.getBatchNo();

            }*/
        }

    }

    public void importExcel(MultipartFile file) throws Exception {
        Map<String, Object> moudleIdmap = new HashMap<>();
        Map<String, Object> objectIdmap = new HashMap<>();
        String batchNo = ComUtil.getBatchNo();
        Workbook workbook = null;
        InputStream is = file.getInputStream();
        if (file.getOriginalFilename().endsWith("xlsx")) {
            workbook = new XSSFWorkbook(is);
        }
        if (file.getOriginalFilename().endsWith("xls")) {
            workbook = new HSSFWorkbook(is);
        }
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            int numberOfRows = sheet.getPhysicalNumberOfRows();
            switch (i) {
                case 0:
                    for (int j = 0; j < numberOfRows; j++) {
                        if (j == 0) {
                            continue;//标题行
                        }
                        String id = CommonUtil.getUUID();
                        Row row = sheet.getRow(j);
                        moudleIdmap.put(row.getCell(0).getStringCellValue(), id);
                        OperStandard std = new OperStandard();
                        std.setId(id);
                        std.setBatchNo(batchNo);
                        std.setName(row.getCell(0).getStringCellValue());
                        String ename=tbDictionaryService.getEname(row.getCell(0).getStringCellValue(),"");
                        std.setEnName(ename);
                        std.setCode(ename);
//                        std.setEnName(row.getCell(1).getStringCellValue());
//                        std.setCode(row.getCell(2).getStringCellValue());
//                        std.setDescription(row.getCell(3).getStringCellValue());
//                        std.setType(Integer.parseInt(row.getCell(4).getStringCellValue()));
                        std.setDataSource("file");
                        std.setVersion("1");
                        std.setCreatePerson("2001");
                        std.setDataType(String.valueOf(DataType.ADD.getCode()));
                        std.setCreateTime(new Date());
                        std.setIsAuth(String.valueOf(AuthType.DSH.getCode()));
                        std.setStatus(String.valueOf(Status.JIHUO.getCode()));
                        std.setCategoryId("0");
                        operModuleRepository.saveOperMoudle(std);
                    }
                    break;
                case 1:
                    for (int j = 0; j < numberOfRows; j++) {
                        if (j == 0) {
                            continue;//标题行
                        }
                        String id = CommonUtil.getUUID();
                        Row row = sheet.getRow(j);
                        objectIdmap.put(row.getCell(0).getStringCellValue(), id);
                        OperStandardObject object = new OperStandardObject();
                        object.setObjId(id);
                        object.setBatchNo(batchNo);
                        object.setVersion("1");
                        object.setObjCname(row.getCell(0).getStringCellValue());
                        object.setObjEname(row.getCell(1).getStringCellValue());
                        object.setShortName(row.getCell(2).getStringCellValue());
                        object.setObjDefined(row.getCell(3).getStringCellValue());
                        object.setObjDataType(row.getCell(4).getStringCellValue());
                        object.setRemark(row.getCell(5).getStringCellValue());
                        object.setDataType(String.valueOf(DataType.ADD.getCode()));
                        object.setStatus(String.valueOf(Status.JIHUO.getCode()));
                        Iterator it = moudleIdmap.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry entry = (Map.Entry) it.next();
                            if (entry.getKey().equals(row.getCell(6).getStringCellValue())) {
                                object.setMoudleId(entry.getValue().toString());
                                break;
                            }
                        }
                        operObjectRepository.saveOperObject(object);
                    }
                    break;
                case 2:
                    for (int j = 0; j < numberOfRows; j++) {
                        if (j == 0) {
                            continue;//标题行
                        }
                        String id = CommonUtil.getUUID();
                        Row row = sheet.getRow(j);
                        OperStandardField field = new OperStandardField();
                        field.setId(id);
                        field.setBatchNo(batchNo);
                        field.setName(row.getCell(0).getStringCellValue());
                        field.setEnName(row.getCell(1).getStringCellValue());
                        field.setCode(row.getCell(2).getStringCellValue());
                        field.setDefination(row.getCell(3).getStringCellValue());
                        field.setType(row.getCell(4).getStringCellValue());
                        field.setMaxsize(Integer.parseInt(row.getCell(5).getStringCellValue()));
                        field.setRangee(row.getCell(6).getStringCellValue());
                        field.setRequired(row.getCell(7).getStringCellValue());
                        field.setComments(row.getCell(8).getStringCellValue());
                        field.setMaxContains(row.getCell(9).getStringCellValue());
                        field.setPxh(Integer.parseInt(row.getCell(10).getStringCellValue()));
                        field.setDataType(String.valueOf(DataType.ADD.getCode()));
                        field.setCreatePerson(UserContextUtil.getUserName());
                        field.setCreateTime(new Date());
                        field.setStatus(String.valueOf(Status.JIHUO.getCode()));
                        Iterator it = moudleIdmap.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry entry = (Map.Entry) it.next();
                            if (entry.getKey().equals(row.getCell(11).getStringCellValue())) {
                                field.setMoudleId(entry.getValue().toString());
                                break;
                            }
                        }
                        if (row.getCell(12) !=null ) {
                            Iterator it2 = objectIdmap.entrySet().iterator();
                            while (it2.hasNext()) {
                                Map.Entry entry = (Map.Entry) it2.next();
                                if (entry.getKey().equals(row.getCell(12).getStringCellValue())) {
                                    field.setObjId(entry.getValue().toString());
                                    break;
                                }
                            }
                        }
                        operFieldRepository.saveOperFeild(field);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void exportToExcel(HttpServletResponse response) throws Exception {
            List<Standard> stdList = schemaModuleRepository.getAllStandardInfo();
            List<StandardField> fieldList = schemaFieldRepository.getAllField();
            List<StandardObject> objList = objectRepository.getAllObject();
            //实例化HSSFWorkbook
            HSSFWorkbook workbook = new HSSFWorkbook();
            stdSheet(stdList, workbook);
            fieldSheet(fieldList, workbook);
            getObjSheet(objList, workbook);
            //清空response
            response.reset();
            //设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + "test.xls");
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/vnd.ms-excel;charset=gb2312");
            //将excel写入到输出流中
            workbook.write(os);
            os.flush();
            os.close();
    }

    private void getObjSheet(List<StandardObject> objList, HSSFWorkbook workbook) {
        HSSFSheet sheet3 = workbook.createSheet("sheet3");
        HSSFRow row3 = sheet3.createRow(0);
        row3.createCell(0).setCellValue("id");
        row3.createCell(1).setCellValue("对象中文名");
        row3.createCell(2).setCellValue("对象英文名");
        row3.createCell(3).setCellValue("短名");
        row3.createCell(4).setCellValue("定义");
        row3.createCell(5).setCellValue("数据类型");
        row3.createCell(6).setCellValue("备注");
        row3.createCell(7).setCellValue("标准Id");
        for (int i = 0; i < objList.size(); i++) {
            int rowNum = 0;
            row3 = sheet3.createRow(i + 1);
            row3.createCell(rowNum++).setCellValue(objList.get(i).getObjId());
            row3.createCell(rowNum++).setCellValue(objList.get(i).getObjCname());
            row3.createCell(rowNum++).setCellValue(objList.get(i).getObjEname());
            row3.createCell(rowNum++).setCellValue(objList.get(i).getShortName());
            row3.createCell(rowNum++).setCellValue(objList.get(i).getObjDefined());
            row3.createCell(rowNum++).setCellValue(objList.get(i).getObjDataType());
            row3.createCell(rowNum++).setCellValue(objList.get(i).getRemark());
            row3.createCell(rowNum++).setCellValue(objList.get(i).getMoudleId());
            //rowNum++;
        }
    }

    private void fieldSheet(List<StandardField> fieldList, HSSFWorkbook workbook) {
        HSSFSheet sheet2 = workbook.createSheet("sheet2");
        HSSFRow row2 = sheet2.createRow(0);
        row2.createCell(0).setCellValue("id");
        row2.createCell(1).setCellValue("字段中文名");
        row2.createCell(2).setCellValue("字段英文名");
        row2.createCell(3).setCellValue("短名");
        row2.createCell(4).setCellValue("定义");
        row2.createCell(5).setCellValue("元素数据");
        row2.createCell(6).setCellValue("长度");
        row2.createCell(7).setCellValue("值域");
        row2.createCell(8).setCellValue("是否必填");
        row2.createCell(9).setCellValue("备注");
        row2.createCell(10).setCellValue("最大出现次数");
        row2.createCell(11).setCellValue("排序号");
        row2.createCell(12).setCellValue("标准id");
        for (int i = 0; i < fieldList.size(); i++) {
            int rowNum = 0;
            row2 = sheet2.createRow(i + 1);
            row2.createCell(rowNum++).setCellValue(fieldList.get(i).getId());
            row2.createCell(rowNum++).setCellValue(fieldList.get(i).getName());
            row2.createCell(rowNum++).setCellValue(fieldList.get(i).getEnName());
            row2.createCell(rowNum++).setCellValue(fieldList.get(i).getCode());
            row2.createCell(rowNum++).setCellValue(fieldList.get(i).getDefination());
            row2.createCell(rowNum++).setCellValue(fieldList.get(i).getType());
            row2.createCell(rowNum++).setCellValue(fieldList.get(i).getMaxsize());
            row2.createCell(rowNum++).setCellValue(fieldList.get(i).getRangee());
            row2.createCell(rowNum++).setCellValue(fieldList.get(i).getRequired());
            row2.createCell(rowNum++).setCellValue(fieldList.get(i).getComments());
            row2.createCell(rowNum++).setCellValue(fieldList.get(i).getMaxContains());
            row2.createCell(rowNum++).setCellValue(fieldList.get(i).getPxh());
            row2.createCell(rowNum++).setCellValue(fieldList.get(i).getMoudleId());
            //rowNum++;
        }
    }

    private HSSFSheet stdSheet(List<Standard> stdList, HSSFWorkbook workbook) {
        //创建一个Excel表单，参数为sheet的名字
        HSSFSheet sheet = workbook.createSheet("sheet");

        //int rowNum = 1;
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("标准中文名");
        row.createCell(2).setCellValue("标准英文名");
        row.createCell(3).setCellValue("短名");
        row.createCell(4).setCellValue("定义");
        row.createCell(5).setCellValue("数据源");
        row.createCell(6).setCellValue("对象类型");
        row.createCell(7).setCellValue("创建人");
        row.createCell(8).setCellValue("创建时间");
        row.createCell(9).setCellValue("目录id");
        row.createCell(10).setCellValue("检测次数");
        for (int i = 0; i < stdList.size(); i++) {
            int rowNum = 0;
            row = sheet.createRow(i + 1);
            row.createCell(rowNum++).setCellValue(stdList.get(i).getId());
            row.createCell(rowNum++).setCellValue(stdList.get(i).getName());
            row.createCell(rowNum++).setCellValue(stdList.get(i).getEnName());
            row.createCell(rowNum++).setCellValue(stdList.get(i).getCode());
            row.createCell(rowNum++).setCellValue(stdList.get(i).getDescription());
            row.createCell(rowNum++).setCellValue(stdList.get(i).getDataSource());
            if(stdList.get(i).getType() != null){
                row.createCell(rowNum++).setCellValue(stdList.get(i).getType());
            }else{
                row.createCell(rowNum++).setCellValue(-1);
            }

            row.createCell(rowNum++).setCellValue(stdList.get(i).getCreatePerson());
            row.createCell(rowNum++).setCellValue(stdList.get(i).getCreateTime());
            row.createCell(rowNum++).setCellValue(stdList.get(i).getCategoryId());
            row.createCell(rowNum++).setCellValue(stdList.get(i).getCount());
            //rowNum++;
        }
        return sheet;
    }


    /**
     * 根据目录id获取码表信息
     * @param categoryId
     * @return
     * @throws Exception
     */
    /*public List<CodeInfo> getCodeTableByCategoryId(String categoryId) throws Exception {
        List<CodeInfo> resultCodeInfo = new ArrayList<>();
        //如果根目录（categoryId = 0），就把categoryId =“”
        if ("0".equals(categoryId)) {
            categoryId = "";
        }

        //获取目录下的所有标准
        List<Object[]> objList = operModuleRepository.getAllStandardInfo(categoryId);
        if (objList.size() > 0) {
            List<StandardVo> operStandardList = ComUtil.castEntity(objList, StandardVo.class);
            for (int i = 0; i < operStandardList.size(); i++) {
                String stdId = operStandardList.get(i).getId();

                //获取标准下字段 对应的码表的 codeid
                List<StandardField> fieldList = schemaFieldRepository.getFieldById(stdId, null);
                for (int j = 0; j < fieldList.size(); j++) {
                    //根据codeid获取码表的信息
//                    String codeEnname = CodeIdList.get(j);
//                    List<CodeInfo> codeInfo = codeInfoRepository.getCodeInfoByName(codeEnname);
//                    resultCodeInfo.addAll(codeInfo);
                    String fieldId = fieldList.get(j).getId();
                    String codeInfoName = fieldCodeRepository.getCodeTableByFeildId(fieldId);
                    if(!StringUtils.isEmpty(codeInfoName)){
                        List<CodeInfo> codeInfoList = codeInfoRepository.getCodeInfoByName(codeInfoName);
                        resultCodeInfo.addAll(codeInfoList);
                    }

                }

            }


        }

        return resultCodeInfo;
    }*/


    public Set<CodeInfo> getCodeTableByCategoryId(String categoryId) throws Exception {
       // List<CodeInfo> resultCodeInfo = new ArrayList<>();
        Set<CodeInfo> resultCodeInfo = new HashSet<>();
        //如果根目录（categoryId = 0），就把categoryId =“”
        if (StringUtils.isEmpty(categoryId)) {
            categoryId = "0";
        }

        //获取当前目录下的的所有子目录
        List<String> childCategoryIdList = new ArrayList<>();
        childCategoryIdList.add(categoryId);
        getCategoryChild(categoryId,childCategoryIdList);

        for (int j = 0; j < childCategoryIdList.size(); j++) {
            String tmpCategoryId = childCategoryIdList.get(j);
            List<String> fieldIdList = fieldCodeRepository.getFieldCodeByCategoryId(tmpCategoryId);
            for (int i = 0; i < fieldIdList.size(); i++) {
                String fieldId = fieldIdList.get(i);
                String codeInfoName = fieldCodeRepository.getCodeTableByFeildId(fieldId);
                if(!StringUtils.isEmpty(codeInfoName)){
                    List<CodeInfo> codeInfoList = codeInfoRepository.getCodeInfoByName(codeInfoName);
                    resultCodeInfo.addAll(codeInfoList);
                }
            }
        }

        return resultCodeInfo;
    }





}
