package com.jn.primiary.beyondsoft.monitor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jn.primiary.beyondsoft.api.service.APIService;
import com.jn.primiary.beyondsoft.controller.UserController;
import com.jn.primiary.beyondsoft.entity.CategoryInfo;
import com.jn.primiary.beyondsoft.entity.StandardField;
import com.jn.primiary.beyondsoft.entity.yunwei.*;
import com.jn.primiary.beyondsoft.interceptor.AuthenticationInterceptor;
import com.jn.primiary.beyondsoft.util.HttpClientUtil;
import com.jn.primiary.beyondsoft.util.YunWeiUtil;
import com.jn.primiary.commons.StringUtils;
import com.jn.primiary.metadata.entity.BaseResponse;
import com.jn.primiary.metadata.entity.ResultCode;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class StdMonitor {
    private Logger logger = Logger.getLogger(StdMonitor.class);
    //是否开启监控
    @Value("${isMonitor}")
    private String isMonitor;
    //是否开启日志上传
    @Value("${isLogReport}")
    private String isLogReport;
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
    //日志上报
    @Value("${reportLog_url}")
    private String reportLog_url;
    //获取nonce
    @Value("${getNonce_url}")
    private String getNonce_url;

    @Value("${36yw.class_code}")
    private String class_code;

    @Value("${36yw.useId}")
    private String useId;

    @Value("${36yw.belongRelationId}")
    private String belongRelationId;

    @Value("${36yw.belongRelationClassCode}")
    private String belongRelationClassCode;

    @Value("${36yw.useDepartment}")
    private String useDepartment;

    @Value("${36yw.appVersion}")
    private String appVersion;

    @Value("${36yw.appPublishTime}")
    private String appPublishTime;

    @Value("${36yw.operations}")
    private String operations;

    @Value("${36yw.apiServicesIP}")
    private String apiServicesIP;

    @Value("${36yw.classCode}")
    private String classCode;

    @Value("${36yw.name}")
    private String name;

    @Value("${36yw.useIP}")
    private String useIP;

    @Value("${36yw.ip}")
    private String ip;

    @Value("${36yw.apiIP}")
    private String apiIP;

    //服务名称
    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${management.port}")
    private String managementPort;

    @Value("${logreport.systemNumber}")
    public String systemNumber;
    @Value("${logreport.systemAddress}")
    public String systemAddress;

    public String getSystemAddress() {
        return systemAddress;
    }

    public String getSystemNumber() {
        return systemNumber;
    }

    @Autowired
    private HttpClientUtil httpClientUtil;
    @Autowired
    APIService apiService;

    /**
     * 配置上报,24小时上报一次
     */
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
//    @Scheduled(fixedRate = 1000 * 60)
    private void monitorConfiguration() {
       if (isMonitor.equals("false")) {
            logger.info("未开启监控上报，退出");
            return;
        }

        JSONObject belongRelationObj = new JSONObject();
        belongRelationObj.put("classCode", belongRelationClassCode);
        belongRelationObj.put("useID", belongRelationId);

        YWConfigurationReportEntity configurationReportEntity = new YWConfigurationReportEntity.YWConfigurationReportEntityBuilder()
                .setUseId(useId)
                .setName(applicationName)
                .setClassCode(class_code)
                .setBelongRelation(belongRelationObj)
                .build();

        configurationReportEntity.setUseDepartment(useDepartment);
        configurationReportEntity.setAppVersion(appVersion);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = sf.parse(appPublishTime);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
        configurationReportEntity.setAppPublishTime(date.getTime());
        configurationReportEntity.setOperations(operations);
        configurationReportEntity.setApiServicesIP(Arrays.asList(apiServicesIP.split(",")));
        JSONArray array = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("classCode", classCode);
        object.put("name", name);
        object.put("useIP", useIP);
        array.add(object);
        configurationReportEntity.setUseRelation(array);
        configurationReportEntity.setIp(ip);
        configurationReportEntity.setApiIP(Arrays.asList(apiIP.split(",")));
        List<YWConfigurationReportEntity> list = new ArrayList<>();
        list.add(configurationReportEntity);

        String arrayStr = JSONArray.toJSONString(list);
        logger.info("配置上报请求参数：" + arrayStr);
        try {
            String result = httpClientUtil.postWithJson(reportConfiguration_url, arrayStr);
            logger.info("配置上报接口返回信息：" + result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("监控状态请求失败");
        }

    }


    /**
     * 性能上报     每1分钟上报一次
     */
    @Scheduled(fixedRate = 1000 * 60)
    private void monitorPerformance() throws Exception {
       if (isMonitor.equals("false")) {
            logger.info("未开启监控上报，退出");
            return;
        }

        String projectMetricsUrl = "http://127.0.0.1:" + managementPort + "/metrics";
        String projectMetricsStr = httpClientUtil.get(projectMetricsUrl);
        JSONObject projectMetricsObj = JSONObject.parseObject(projectMetricsStr);

        JSONObject tagObj = new JSONObject();
        tagObj.put("classCode", class_code);
        tagObj.put("useID", useId);

        YWPerformanceEntity memPerformanceEntityEntity = new YWPerformanceEntity();
        memPerformanceEntityEntity.setMetric("Memory");
        JSONObject tagObj1 = new JSONObject();
        tagObj1.put("classCode", class_code);
        tagObj1.put("useID", useId);
        memPerformanceEntityEntity.setTags(tagObj1);
        memPerformanceEntityEntity.setValue(projectMetricsObj.getString("mem"));

        YWPerformanceEntity memFreePerformanceEntity = new YWPerformanceEntity();
        memFreePerformanceEntity.setMetric("MemoryFree");
        JSONObject tagObj2 = new JSONObject();
        tagObj2.put("classCode", class_code);
        tagObj2.put("useID", useId);
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
        logger.info("性能上报请求参数：" + arrayStr);
        try {
            String result = httpClientUtil.postWithJson(reportPerformance_url, arrayStr);
            logger.info("性能上报接口返回信息：" + result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("性能上报请求失败");
        }

    }

    /**
     * 状态上报     每5分钟上报一次
     */
    @Scheduled(fixedRate = 1000 * 60 * 5)
    private void monitorStatus() throws Exception {
       if (isMonitor.equals("false")) {
            logger.info("未开启监控上报，退出");
            return;
        }

        YWStatusEntity statusEntity = new YWStatusEntity();
        statusEntity.setState("object.available");
        statusEntity.setClassCode(class_code);
        statusEntity.setUseID(useId);

        //http://127.0.0.1:9001/health
        String projectHealthStatusUrl = "http://127.0.0.1:" + managementPort + "/health";
        String projectHealthStatusStr = httpClientUtil.get(projectHealthStatusUrl);
        JSONObject projectHealthStatusObj = JSONObject.parseObject(projectHealthStatusStr);

        //如果请求127.0.0.1:9001 返回的status是up，就是运行状态正常
        if ("up".equalsIgnoreCase(projectHealthStatusObj.getString("status"))) {
            statusEntity.setValue("on");    //unknown、off、on 分别表示：未知、不可用、可用
        } else {
            statusEntity.setValue("off");
            //状态出问题了，就告警
            monitorAlter();
        }

        String objStr = JSONObject.toJSONString(statusEntity);
        logger.info("状态上报请求参数：" + objStr);
        try {
            String result = httpClientUtil.postWithJson(reportStatus_url, objStr);
            logger.info("状态上报接口返回信息：" + result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("状态上报请求失败");
        }

    }


    /**
     * 告警
     */
//    @Scheduled(fixedRate = 1000 * 60)
    private void monitorAlter() {
       if (isMonitor.equals("false")) {
            logger.info("未开启监控上报，退出");
            return;
        }

        YWAlertEntity alertEntity = new YWAlertEntity();
        alertEntity.setName(applicationName);
        alertEntity.setDescription("服务告警");
        alertEntity.setEntity_name("数据库节点");
        alertEntity.setIdentify_key("classCode,useID");
        JSONArray propArray = new JSONArray();
        JSONObject propObj = new JSONObject();
        propObj.put("val", "DBNode");
        propObj.put("code", "classCode");
        propObj.put("name", "classCode");
        JSONObject propObj1 = new JSONObject();
        propObj1.put("val", useId);
        propObj1.put("code", "useID");
        propObj1.put("name", "useID");
        propArray.add(propObj);
        propArray.add(propObj1);
        alertEntity.setProperties(propArray);

        List<YWAlertEntity> list = new ArrayList<>();
        list.add(alertEntity);

        String arrayStr = JSONArray.toJSONString(list);
        logger.info("告警上报请求参数：" + arrayStr);
        try {
            String result = httpClientUtil.postWithJson(reportAlert_url, arrayStr);
            logger.info("告警上报接口返回信息：" + result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("告警上报请求失败");
        }
    }

    /**
     * 应用系统主动上传日志方法
     */
    //每5分钟上传一次日志
    @Scheduled(fixedRate = 1000 * 60 * 5)
    private void activeReportLog() {
//        logger.info("isLogReport:" + isLogReport);
//        logger.info("isLogReport.equals(\"false\"):" + isLogReport.equals("false"));
        if (isLogReport.equals("false")) {
            logger.info("未开启日志上报，退出");
            return;
        }
        try {
            logger.info("开始主动上报日志");
            List<YwData> YwDatalist = AuthenticationInterceptor.loglist;
            YwLog ywLog = new YwLog();
            ywLog.setData(YwDatalist);
            ywLog.setSystemNumber(systemNumber);
            ywLog.setSystemAddress(systemAddress);
            String lastestLog = JSON.toJSONString(ywLog);
            logger.info("上报日志内容为：" + lastestLog);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(reportLog_url);
            httpPost.addHeader("Content-Type", "application/json");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = df.format(new Date());
            logger.info("date为：" + date);
            String token = getToken(date);
            logger.info("token为：" + token);
            httpPost.addHeader("timestamp", date);
            httpPost.addHeader("token", token);
            StringEntity entity = new StringEntity(lastestLog, StandardCharsets.UTF_8);
            entity.setContentEncoding("UTF-8");
            httpPost.setEntity(entity);
            CloseableHttpResponse execute = httpClient.execute(httpPost);
            String res = EntityUtils.toString(execute.getEntity(), "UTF-8");
            logger.info("上报日志称成功，返回内容为：" + res);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("主动上报日志失败");
        } finally {
            AuthenticationInterceptor.loglist.clear();
        }

    }

    /**
     * 应用系统获取随机码
     */
    private String getToken(String date) throws Exception {

        JSONObject object = new JSONObject();
        object.put("systemNumber", systemNumber);
        object.put("systemAddress", systemAddress);
        String objStr = object.toJSONString();
        logger.info("开始请求nonce：" + objStr);
        String result = httpClientUtil.postWithJson(getNonce_url, objStr);
        logger.info("请求nonce接口返回信息：" + result);
        JSONObject object1 = JSON.parseObject(result);
        if (object1.getInteger("code") != null && object1.getInteger("code") == 200) {
            String nonce = object1.getString("nonce");
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((date + "_" + nonce).getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        }

        return null;
    }


}
