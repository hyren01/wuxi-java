package com.jn.primiary.beyondsoft.monitor;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.alibaba.fastjson.JSONObject;
import com.jn.primiary.beyondsoft.util.YunWeiUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class KafkaAppender extends AppenderBase<ILoggingEvent> {
    private Logger logger = Logger.getLogger(KafkaAppender.class);

    private String logPath; //日志路径
    private String logFileName; //日志名称
    private String useId;
    private String reportKafkaLogTopic;
    private String servers;

    private long logCount = 0l;

    private YunWeiUtil yunWeiUtil = new YunWeiUtil();

    private Producer producer;

    @Override
    public void start() {
        super.start();
        if(producer == null){
            Map<String, Object> props = new HashMap();
            props.put("bootstrap.servers", servers);
            props.put("retries", "0");
            props.put("batch.size", "16384");
            props.put("buffer.memory", "33554432");
            props.put("key.serializer", StringSerializer.class);
            props.put("value.serializer", StringSerializer.class);
            producer = new KafkaProducer(props);
        }

    }



    @Override
    protected void append(ILoggingEvent eventObject) {
        String msg = logObj(eventObject).toString();
        ProducerRecord<String,String> record = new ProducerRecord<>(reportKafkaLogTopic,"msg",msg);
//        logger.info("开始发送日志到kafka中");
        producer.send(record);


//        if (kafkaTemplate != null){
//            logger.info("发送到kafka的日志："+logObj(eventObject).toString());
//            kafkaTemplate.send(reportKafkaLogTopic, logObj(eventObject).toString());
//        }else{
//            kafkaTemplate = (KafkaTemplate<String, String>)SpringContextUtil.getBean("KafkaTemplate");
//            kafkaTemplate.send(reportKafkaLogTopic, logObj(eventObject).toString());
//        }
    }

//    public KafkaTemplate<String, String> getKafkaTemplate() {
//        return kafkaTemplate;
//    }
//
//    public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }


    public JSONObject logObj(ILoggingEvent eventObject){
        JSONObject obj = new JSONObject();
        obj.put("adMessage",eventObject.getFormattedMessage());
        obj.put("agSourceID",useId);
        obj.put("agSourceTags","数据标准管理");

        obj.put("agCollectTime",new Date().getTime());
        obj.put("adOffset",logCount++);
        obj.put("agPath",logPath);
        obj.put("agName",logFileName);
        obj.put("agentIP",yunWeiUtil.getLocalIP());
        obj.put("agSourceOS",yunWeiUtil.getOsType());
        return obj;
    }


    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public String getLogFileName() {
        return logFileName;
    }

    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

    public String getUseId() {
        return useId;
    }

    public void setUseId(String useId) {
        this.useId = useId;
    }

    public String getReportKafkaLogTopic() {
        return reportKafkaLogTopic;
    }

    public void setReportKafkaLogTopic(String reportKafkaLogTopic) {
        this.reportKafkaLogTopic = reportKafkaLogTopic;
    }

    public String getServers() {
        return servers;
    }

    public void setServers(String servers) {
        this.servers = servers;
    }

    public long getLogCount() {
        return logCount;
    }

    public void setLogCount(long logCount) {
        this.logCount = logCount;
    }

    public YunWeiUtil getYunWeiUtil() {
        return yunWeiUtil;
    }

    public void setYunWeiUtil(YunWeiUtil yunWeiUtil) {
        this.yunWeiUtil = yunWeiUtil;
    }
}
