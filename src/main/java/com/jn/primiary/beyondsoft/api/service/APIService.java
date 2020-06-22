package com.jn.primiary.beyondsoft.api.service;

import com.jn.primiary.beyondsoft.api.vo.FieldVo;
import com.jn.primiary.beyondsoft.api.vo.StandardVo;
import com.jn.primiary.beyondsoft.dao.CategoryRepository;
import com.jn.primiary.beyondsoft.dao.FieldRepository;
import com.jn.primiary.beyondsoft.dao.ModuleRepository;
import com.jn.primiary.beyondsoft.entity.CategoryInfo;
import com.jn.primiary.beyondsoft.util.ComUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
//import com.jn.stdtest.entity.ReturnInf;


/**
 * @Des 标准管理业务层
 * @Author chenhong
 * @Date 2019/9/23 10:07
 */
@Service
@Transactional
public class APIService {

    @Autowired
    private ModuleRepository schemaModuleRepository;
    @Autowired
    private FieldRepository schemaFieldRepository;

    @Autowired
    CategoryRepository categoryRepository;

    private Logger logger = Logger.getLogger(APIService.class);

    /**
     * api-根据目录id查询标准列表
     *
     * @return
     */
    public List<StandardVo> getStandardByCategoryId(String categoryId) throws Exception{
        List<StandardVo> standard=new ArrayList<>();
        List<Object[]> objList=schemaModuleRepository.getStandardByCategoryId(categoryId,null);
        if(objList.size()>0){
            standard=ComUtil.castEntity(objList,StandardVo.class);
        }
        return standard;
    }

    public List<StandardVo> getStandardInfo(String standardId) throws Exception{
        List<StandardVo> standardList=new ArrayList<>();
        List<Object[]> objList=schemaModuleRepository.getStandardByCategoryId(null,standardId);
        if(objList.size()>0){
            standardList=ComUtil.castEntity(objList,StandardVo.class);
        }
        for (StandardVo standardVo:standardList) {
            List<FieldVo> fieldList=new ArrayList<>();
            List<Object[]> objListf=schemaFieldRepository.getFieldList(standardVo.getId());
            if(objListf.size()>0){
                fieldList=ComUtil.castEntity(objListf,FieldVo.class);
                standardVo.setFields(fieldList);
            }
        }
        return standardList;
    }


    //查询所有目录的id和name
    public List<CategoryInfo> getAllCategory(){
        return categoryRepository.getCategoryInfo();
    }


    //根据当前目录的id去查找子目录
    public List<CategoryInfo> findChildCategory(String id){
        return categoryRepository.findCategoryInfoByParentId(id);
    }

    public List<CategoryInfo> getAllSubStandardCategorys(String parentId){
        List<CategoryInfo> resultchildCategory = getchild(parentId);
        return resultchildCategory;
    }



    public List<CategoryInfo> getchild(String parentId){
        List<CategoryInfo> resultchildCategory = new ArrayList<>();
        List<CategoryInfo> childCategory = categoryRepository.findCategoryInfoByParentId(parentId);
        if(childCategory.size()>0){
            for (CategoryInfo category : childCategory) {
                List<CategoryInfo> childCategory1 = getchild(category.getId());
                if(childCategory1.size()>0){
                    resultchildCategory.addAll(childCategory1);
                }
                resultchildCategory.add(category);
            }
        }
        return resultchildCategory;
    }
}
