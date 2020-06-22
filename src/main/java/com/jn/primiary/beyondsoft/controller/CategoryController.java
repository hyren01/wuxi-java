package com.jn.primiary.beyondsoft.controller;

import com.jn.primiary.beyondsoft.entity.CategoryInfo;
import com.jn.primiary.beyondsoft.service.CategoryService;
import com.jn.primiary.metadata.entity.BaseResponse;
import com.jn.primiary.metadata.entity.ResultCode;
import com.jn.primiary.metadata.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 目录控制类
 */
@Validated
@Controller
@RequestMapping("/category")
public class CategoryController{

    @Autowired
    CategoryService categoryService;


    //获取当前目录的信息
    @ResponseBody
    @RequestMapping(value = "/getCategoryInfo", method = RequestMethod.POST)
    public BaseResponse<CategoryInfo>getCategoryInfo(@RequestParam(required=false) String id){//@Pattern(regexp = "^\\w+$",message = "请输入正确的id")
        if(StringUtils.isEmpty(id)){
            id = "0";
        }
        List<CategoryInfo> categoryInfoList = categoryService.getCategoryInfo(id);

        BaseResponse<CategoryInfo> response = new BaseResponse<CategoryInfo>();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(categoryInfoList);
        return response;
    }

    //获取目录结构的所有一级子目录信息
    @ResponseBody
    @RequestMapping(value = "/getChildCategoryInfo/{id}", method = RequestMethod.GET)
    public BaseResponse<CategoryInfo> getChildCategoryInfo(@PathVariable String id) {
        List<CategoryInfo> childCategoryList = categoryService.findChildCategory(id);

        BaseResponse<CategoryInfo> response = new BaseResponse<CategoryInfo>();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(childCategoryList);
        return response;
    }

    //获取所有的目录名和id
    @ResponseBody
    @RequestMapping(value = "/getAllCategory", method = RequestMethod.POST)
    public BaseResponse<CategoryInfo> getAllCategory() {

        List<CategoryInfo> categoryNameAndIdList = categoryService.getAllCategory();

        BaseResponse<CategoryInfo> response = new BaseResponse<CategoryInfo>();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(categoryNameAndIdList);
        return response;
    }

    //修改当前目录信息
    @ResponseBody
    @RequestMapping(value = "/updataCategoryInfo",method = RequestMethod.POST)
    public BaseResponse<CategoryInfo> updataCategoryInfo(@RequestBody CategoryInfo categoryInfo){
        String name = categoryInfo.getName();
        String parentId = categoryInfo.getParentId();
        Integer pxh = categoryInfo.getPxh();
        String id = categoryInfo.getId();
        categoryService.updataCategory(name,parentId,pxh,id);
        BaseResponse<CategoryInfo> response = new BaseResponse<CategoryInfo>();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setMessage("修改目录信息成功");
        return response;
    }

    //删除目录
    @ResponseBody
    @RequestMapping(value = "/deleteCategory/{id}",method = RequestMethod.GET)
    public BaseResponse<CategoryInfo> deleteCategory(@PathVariable String id){
        String childId = categoryService.deleteCategory(id);
        BaseResponse<CategoryInfo> response = new BaseResponse<CategoryInfo>();
        if(childId.equals("false")){
            response.setResultCode(ResultCode.RESULT_SUCCESS);
            response.setMessage("目录删除成功");
        } else{
            response.setResultCode(ResultCode.RESULT_ERROR);
            response.setMessage("该目录下还有子目录或标准");
        }
        return response;
    }
    //新增，编辑目录
    @ResponseBody
    @RequestMapping(value = "/addCategory",method = RequestMethod.POST)
    public BaseResponse<CategoryInfo> addCategory(@RequestBody Map<String, String> params){
        BaseResponse<CategoryInfo> response = new BaseResponse<>();
        if(StringUtils.isEmpty(params.get("id"))){
            //新增
            String category_name = params.get("name");
            boolean flag = categoryService.getcategorybyname(category_name,params.get("parent_id"));
            if(flag == true){
                //新增
                categoryService.saveCategory(params.get("name"),params.get("parent_id"),Integer.parseInt(params.get("pxh")));
                response.setResultCode(ResultCode.RESULT_SUCCESS);
                response.setMessage("新加目录成功");
            }else{
                response.setResultCode(ResultCode.RESULT_ERROR);
                response.setMessage("目录名称重复!");
            }
        }else{
            //编辑
            String category_name = params.get("name");
            String category_id = params.get("id");
            boolean flag = categoryService.getcategorybynameandid(category_name, category_id);
            if(flag == true){
                //编辑保存
                categoryService.updataCategory(params.get("name"),params.get("parent_id"),Integer.parseInt(params.get("pxh")),params.get("id"));
                response.setResultCode(ResultCode.RESULT_SUCCESS);
                response.setMessage("编辑成功");
            }else{
                response.setResultCode(ResultCode.RESULT_ERROR);
                response.setMessage("目录名称重复!");
            }
        }



        return response;
    }


    /**
     * 获取排序号
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getpxh/{id}",method = RequestMethod.GET)
    public BaseResponse<String> getpxh(@PathVariable() String id){
        BaseResponse<String> response = new BaseResponse<String>();
        String pxh = categoryService.getpxh(id);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setMessage(pxh);
        return response;
    }


    /**
     * 所有目录 递归
     * @return
     */
//    @ResponseBody
//    @RequestMapping(value = "/getAllCategoryRecurive", method = RequestMethod.POST)
//    public BaseResponse<CategoryInfo> getAllCategoryRecurive() {
//
//        List<CategoryInfo> categoryNameAndIdList = categoryService.getAllCategoryRecurive();
//
//        BaseResponse<CategoryInfo> response = new BaseResponse<CategoryInfo>();
//        response.setResultCode(ResultCode.RESULT_SUCCESS);
//        response.setData(categoryNameAndIdList);
//        return response;
//    }


}
