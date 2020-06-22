package com.jn.primiary.beyondsoft.util;

import com.jn.primiary.beyondsoft.entity.CategoryInfo;

import java.util.ArrayList;
import java.util.List;

public class CategoryTreeUtil{
//    private List<CategoryInfo> CategoryInfoList = new ArrayList<CategoryInfo>();
//    public CategoryTreeUtil(List<CategoryInfo> CategoryInfoList) {
//        this.CategoryInfoList=CategoryInfoList;
//    }
//    //建立树形结构
//    public List<CategoryInfo> builTree(){
//        List<CategoryInfo> treeCategoryInfos =new  ArrayList<CategoryInfo>();
//        for(CategoryInfo CategoryInfoNode : getRootNode()) {
//            CategoryInfoNode=buildChilTree(CategoryInfoNode);
//            treeCategoryInfos.add(CategoryInfoNode);
//        }
//        return treeCategoryInfos;
//    }
//
//    //递归，建立子树形结构
//    private CategoryInfo buildChilTree(CategoryInfo pNode){
//        List<CategoryInfo> chilCategoryInfos =new ArrayList<CategoryInfo>();
//        for(CategoryInfo CategoryInfoNode : CategoryInfoList) {
//            if(CategoryInfoNode.getParentId().equals(pNode.getId())) {
//                chilCategoryInfos.add(buildChilTree(CategoryInfoNode));
//            }
//        }
//        pNode.setChildren(chilCategoryInfos);
//        return pNode;
//    }
//
//    //获取根节点
//    private List<CategoryInfo> getRootNode() {
//        List<CategoryInfo> rootCategoryInfoLists =new  ArrayList<CategoryInfo>();
//        for(CategoryInfo CategoryInfoNode : CategoryInfoList) {
//            if(CategoryInfoNode.getParentId().equals("-1")) {
//                rootCategoryInfoLists.add(CategoryInfoNode);
//            }
//        }
//        return rootCategoryInfoLists;
//    }
}
