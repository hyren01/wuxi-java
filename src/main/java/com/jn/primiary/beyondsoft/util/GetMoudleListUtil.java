package com.jn.primiary.beyondsoft.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jn.primiary.beyondsoft.dao.CheckRepository;
import com.jn.primiary.beyondsoft.dao.ModuleRepository;
import com.jn.primiary.beyondsoft.entity.FieldCheckResult;
import com.jn.primiary.beyondsoft.entity.Standard;
import com.jn.primiary.beyondsoft.vo.MoudleListVo;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


public class GetMoudleListUtil implements Runnable {
    private Logger logger = Logger.getLogger(GetMoudleListUtil.class);
    public String threadName;
    public JSONArray array;
    public int start;
    public int end;
    public List<MoudleListVo> resultList;

    private CheckRepository fieldCheckRepository = RepositoryUtil.fieldCheckRepository;
    private ModuleRepository schemaModuleRepository = RepositoryUtil.schemaModuleRepository;

    public GetMoudleListUtil(String threadName, JSONArray array, int start, int end,List<MoudleListVo> resultList) {
        this.threadName = threadName;
        this.array = array;
        this.start = start;
        this.end = end;
        this.resultList = resultList;
    }


    @Override
    public void run() {

        List<String> allMouldIdList = new ArrayList<>();

        Map<String,List<FieldCheckResult>> allCheckResultMap = new HashMap();
        List<MoudleListVo> moudleList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            JSONObject tmpobj = array.getJSONObject(i);
            MoudleListVo tmpmodel = new MoudleListVo();
            tmpmodel.setMoudleName(tmpobj.getString("name"));
            tmpmodel.setEnName(tmpobj.getString("enName"));
            tmpmodel.setMoudleCode(tmpobj.getString("code"));
            tmpmodel.setMoudleDesc(tmpobj.getString("description"));
            tmpmodel.setUpdateDate(tmpobj.getString("updateTime"));
            tmpmodel.setStatus(tmpobj.getString("registerStatus"));
            tmpmodel.setMoudleId(tmpobj.getString("id"));

            moudleList.add(tmpmodel);
            allMouldIdList.add(tmpobj.getString("id"));
            allCheckResultMap.put(tmpobj.getString("id"),new ArrayList<>());
        }

        List<FieldCheckResult> allCheckResultList = fieldCheckRepository.findCheckResultInMoudleIds(allMouldIdList);


        for (int i = 0; i < allCheckResultList.size(); i++) {
            String moudleId = allCheckResultList.get(i).getMoudleId();
            List<FieldCheckResult> tmpListCheckResult = (List<FieldCheckResult>) allCheckResultMap.get(moudleId);
            //if(tmpListCheckResult != null){
                tmpListCheckResult.add(allCheckResultList.get(i));
            //}
        }


        for (MoudleListVo moudleListVo:moudleList) {
            String moudleId = moudleListVo.getMoudleId();
            List<FieldCheckResult> list = allCheckResultMap.get(moudleId);
            if(list.size()>0){
                try{
                    moudleListVo.setIsCheck("已检测");
                    String std_id = list.get(0).getStdname();
                    Standard std = schemaModuleRepository.getstdmessbyidandstatus(std_id);

                    String version = list.get(0).getVersion();
                    System.out.println(version+"-------"+std.getVersion());
                    if(!std.getVersion().equals(version)){
                        moudleListVo.setIsCheckModify("标准变更");
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else{
                moudleListVo.setIsCheck("未检测");
            }
        }


        resultList.addAll(moudleList);

    }



    /*@Override
    public void run() {
        List<MoudleListVo> moudleList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            JSONObject tmpobj = array.getJSONObject(i);
            MoudleListVo tmpmodel = new MoudleListVo();
            tmpmodel.setMoudleName(tmpobj.getString("name"));
            tmpmodel.setEnName(tmpobj.getString("enName"));
            tmpmodel.setMoudleCode(tmpobj.getString("code"));
            tmpmodel.setMoudleDesc(tmpobj.getString("description"));
            tmpmodel.setUpdateDate(tmpobj.getString("updateTime"));
            tmpmodel.setStatus(tmpobj.getString("registerStatus"));
            tmpmodel.setMoudleId(tmpobj.getString("id"));
            moudleList.add(tmpmodel);

        }
        //组装VO，查看模型是否做过检测/是否需要重新检测
        for (MoudleListVo moudleListVo:moudleList) {

            //查看模型是否做过检测
            List<FieldCheckResult> list=fieldCheckRepository.findByMoudleId(moudleListVo.getMoudleId());
            //已检测
            if(list.size()>0){
                try{
                    moudleListVo.setIsCheck("已检测");
                    String std_id = list.get(0).getStdname();
                    Standard std = schemaModuleRepository.getstdmessbyidandstatus(std_id);

                    String version = list.get(0).getVersion();
                    System.out.println(version+"-------"+std.getVersion());
                    if(!std.getVersion().equals(version)){
                        moudleListVo.setIsCheckModify("标准变更");
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else{
                moudleListVo.setIsCheck("未检测");
            }
        }

        resultList.addAll(moudleList);

    }*/

}
