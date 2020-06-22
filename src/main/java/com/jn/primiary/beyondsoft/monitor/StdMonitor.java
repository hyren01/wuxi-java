package com.jn.primiary.beyondsoft.monitor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jn.primiary.beyondsoft.controller.UserController;
import com.jn.primiary.beyondsoft.entity.yunwei.YWAlertEntity;
import com.jn.primiary.beyondsoft.entity.yunwei.YWConfigurationReportEntity;
import com.jn.primiary.beyondsoft.entity.yunwei.YWPerformanceEntity;
import com.jn.primiary.beyondsoft.entity.yunwei.YWStatusEntity;
import com.jn.primiary.beyondsoft.util.HttpClientUtil;
import com.jn.primiary.beyondsoft.util.YunWeiUtil;
import com.jn.primiary.commons.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class StdMonitor {
    private Logger logger = Logger.getLogger(StdMonitor.class);
    //是否开启监控
    @Value("${isMonitor}")
    private String isMonitor;
    //配置上报
    @Value("${reportConfiguration_url}")
    private String reportConfiguration_url;
    //状态上报
    @Value("${reportStatus_url}")
    private String reportStatus_url;
    //性能上报
    @Value("${reportPerformance_url}")
    private String reportPerformance_url;
    //告警信息
    @Value("${reportAlert_url}")
    private String reportAlert_url;

    @Value("${36yw.class_code}")
    private String class_code;

    @Value("${36yw.useId}")
    private String useId;

    @Value("${36yw.belongRelationId}")
    private String belongRelationId;

    @Value("${36yw.belongRelationClassCode}")
    private String belongRelationClassCode;

    //服务名称
    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${management.port}")
    private String managementPort;

    @Autowired
    private HttpClientUtil httpClientUtil;

    /**
     * 配置上报,24小时上报一次
     */
    @Scheduled(fixedRate=1000*60*60*24)
    private void monitorConfiguration() {
        if(isMonitor.equals("false")) return;

        JSONObject belongRelationObj = new JSONObject();
        belongRelationObj.put("classCode",belongRelationClassCode);
        belongRelationObj.put("useID",belongRelationId);

        YWConfigurationReportEntity configurationReportEntity = new YWConfigurationReportEntity.YWConfigurationReportEntityBuilder()
                .setUseId(useId)
                .setName(applicationName)
                .setClassCode(class_code)
                .setBelongRelation(belongRelationObj)
                .build();

        List<YWConfigurationReportEntity> list = new ArrayList<>();
        list.add(configurationReportEntity);

        String arrayStr = JSONArray.toJSONString(list);
        logger.info("配置上报请求参数："+arrayStr);
        try {
            String result = httpClientUtil.postWithJson(reportConfiguration_url,arrayStr);
            logger.info("配置上报接口返回信息："+result);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("监控状态请求失败");
        }

    }


    /**
     * 性能上报     每1分钟上报一次
     */
    @Scheduled(fixedRate=1000*60)
    private void monitorPerformance() throws Exception {
        if(isMonitor.equals("false")) return;

        String projectMetricsUrl = "http://127.0.0.1:"+managementPort+"/metrics";
        String projectMetricsStr = httpClientUtil.get(projectMetricsUrl);
        JSONObject projectMetricsObj = JSONObject.parseObject(projectMetricsStr);

        JSONObject tagObj = new JSONObject();
        tagObj.put("classCode",class_code);
        tagObj.put("useID",useId);

        YWPerformanceEntity memPerformanceEntityEntity = new YWPerformanceEntity();
        memPerformanceEntityEntity.setMetric("Memory");
        JSONObject tagObj1 = new JSONObject();
        tagObj1.put("classCode",class_code);
        tagObj1.put("useID",useId);
        memPerformanceEntityEntity.setTags(tagObj1);
        memPerformanceEntityEntity.setValue(projectMetricsObj.getString("mem"));

        YWPerformanceEntity memFreePerformanceEntity = new YWPerformanceEntity();
        memFreePerformanceEntity.setMetric("MemoryFree");
        JSONObject tagObj2 = new JSONObject();
        tagObj2.put("classCode",class_code);
        tagObj2.put("useID",useId);
        memFreePerformanceEntity.setTags(tagObj2);
        memFreePerformanceEntity.setValue(projectMetricsObj.getString("mem.free"));

        /*YWPerformanceEntity instanceUptimeEntity = new YWPerformanceEntity();
        instanceUptimeEntity.setMetric("instanceUptime");
        instanceUptimeEntity.setTags(tagObj);
        instanceUptimeEntity.setValue(projectMetricsObj.getString("instance.uptime"));*/

        JSONArray array = new JSONArray();
        array.add(memPerformanceEntityEntity);
        array.add(memFreePerformanceEntity);
        //array.add(instanceUptimeEntity);
        String arrayStr = JSONArray.toJSONString(array);
        logger.info("性能上报请求参数："+arrayStr);
        try {
            String result = httpClientUtil.postWithJson(reportPerformance_url,arrayStr);
            logger.info("性能上报接口返回信息："+result);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("性能上报请求失败");
        }

    }

    /**
     * 状态上报     每5分钟上报一次
     */
    @Scheduled(fixedRate=1000*60*5)
    private void monitorStatus() throws Exception {
        if(isMonitor.equals("false")) return;

        YWStatusEntity statusEntity = new YWStatusEntity();
        statusEntity.setState("object.available");
        statusEntity.setClassCode(class_code);
        statusEntity.setUseID(useId);

        //http://127.0.0.1:9001/health
        String projectHealthStatusUrl = "http://127.0.0.1:"+managementPort+"/health";
        String projectHealthStatusStr = httpClientUtil.get(projectHealthStatusUrl);
        JSONObject projectHealthStatusObj = JSONObject.parseObject(projectHealthStatusStr);

        //如果请求127.0.0.1:9001 返回的status是up，就是运行状态正常
        if("up".equalsIgnoreCase(projectHealthStatusObj.getString("status"))){
            statusEntity.setValue("on");    //unknown、off、on 分别表示：未知、不可用、可用
        }else{
            statusEntity.setValue("off");
            //状态出问题了，就告警
            monitorAlter();
        }

        String objStr = JSONObject.toJSONString(statusEntity);
        logger.info("状态上报请求参数："+objStr);
        try {
            String result = httpClientUtil.postWithJson(reportStatus_url,objStr);
            logger.info("状态上报接口返回信息："+result);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("状态上报请求失败");
        }

    }


    /**
     * 告警
     */
    private void monitorAlter() {
        if(isMonitor.equals("false")) return;

        YWAlertEntity alertEntity = new YWAlertEntity();
        alertEntity.setName(applicationName);
        alertEntity.setDescription("服务告警");
        alertEntity.setEntity_name("数据库节点");
        alertEntity.setIdentify_key("classCode,useID");
        JSONArray propArray = new JSONArray();
            JSONObject propObj = new JSONObject();
                propObj.put("val","DBNode");
                propObj.put("code","classCode");
                propObj.put("name","classCode");
            JSONObject propObj1 = new JSONObject();
                propObj1.put("val",useId);
                propObj1.put("code","useID");
                propObj1.put("name","useID");
        propArray.add(propObj);
        propArray.add(propObj1);
        alertEntity.setProperties(propArray);

        List<YWAlertEntity> list = new ArrayList<>();
        list.add(alertEntity);

        String arrayStr = JSONArray.toJSONString(list);
        logger.info("告警上报请求参数："+arrayStr);
        try {
            String result = httpClientUtil.postWithJson(reportAlert_url,arrayStr);
            logger.info("告警上报接口返回信息："+result);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("告警上报请求失败");
        }
    }




}
