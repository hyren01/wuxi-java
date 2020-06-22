package com.jn.primiary.beyondsoft.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jn.primiary.beyondsoft.dao.CategoryRepository;
import com.jn.primiary.beyondsoft.entity.CategoryInfo;
import com.jn.primiary.beyondsoft.util.CategoryTreeUtil;
import com.jn.primiary.metadata.utils.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    private Logger logger = Logger.getLogger(CategoryService.class);

    //点击当前目录显示出相应的信息
    public List<CategoryInfo> getCategoryInfo(String id) {
        return categoryRepository.queryCategoryInfoById(id);
    }

    //根据当前目录的id去查找子目录
    public List<CategoryInfo> findChildCategory(String id){
        return categoryRepository.findCategoryInfoByParentId(id);
    }

    //查询所有目录的id和name
    public List<CategoryInfo> getAllCategory(){
        return categoryRepository.getCategoryInfo();
    }

    //修改当前目录信息
    public void updataCategory(String name, String parentId, Integer pxh,String id){
        categoryRepository.updataCategoryInfoById(name,parentId,pxh,id);
    }

    //删除目录，如果当前目录下还有子目录或者标准则无法删除
    public String deleteCategory(String id){
        String childId = "true";
        List<CategoryInfo> categoryInfos = categoryRepository.findCategoryInfoByParentId(id);
        if(categoryInfos.isEmpty()){
            categoryRepository.deleteCategoryInfoById(id);
            childId = "false";
        }
        return childId;
    }

    //新增目录
    public void saveCategory(String name, String parentId, Integer pxh) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CategoryInfo categoryInfo = new CategoryInfo();
        categoryInfo.setId(CommonUtil.getUUID());
        categoryInfo.setName(name);
        categoryInfo.setParentId(parentId);
        categoryInfo.setPxh(pxh);
        categoryInfo.setCreateTime(df.format(new Date()).toString());
        categoryInfo.setStatus("1");
        categoryRepository.save(categoryInfo);
    }

    public String getpxh(String id){
      return categoryRepository.getpxh(id);
    }

    /**
     * 根据name查询分类
     * @param category_name
     * @return
     */
    public boolean getcategorybyname(String category_name,String parent_id){
        List<CategoryInfo> categorylist = categoryRepository.getcategorybyname(category_name,parent_id);
        if(categorylist.size()>0){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 根据name,id查询分类
     * @param category_name
     * @return
     */
    public boolean getcategorybynameandid(String category_name,String category_id){
        List<CategoryInfo> categorylist = categoryRepository.getcategorybynameandid(category_name,category_id);
        if(categorylist.size()>0){
            return false;
        }else{
            return true;
        }
    }



//    public List<CategoryInfo> getAllCategoryRecurive(){
//        List<CategoryInfo> resultCategoryInfoList = new ArrayList<>();
//
//        List<CategoryInfo> categoryInfoList = categoryRepository.getCategoryInfoOrderByPxh();
//        CategoryTreeUtil categoryTreeUtil = new CategoryTreeUtil(categoryInfoList);
//
//        resultCategoryInfoList=categoryTreeUtil.builTree();
//        String jsonOutput= JSONArray.toJSONString(resultCategoryInfoList);
//        logger.info(jsonOutput);
//
//
//        return resultCategoryInfoList;
//    }



}
