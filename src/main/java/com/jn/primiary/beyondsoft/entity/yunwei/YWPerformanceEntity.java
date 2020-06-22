package com.jn.primiary.beyondsoft.entity.yunwei;

import com.alibaba.fastjson.JSONObject;

/**
 * 性能上报 实体
 */
public class YWPerformanceEntity {
    //指标名称
    private String metric;
    //标签集合,主要用于标识数据归属，值为键值对。例如useID、ip、classCode等，方便查询时按标签分组查询。
    private JSONObject tags;
    //(非必填) 采样时间，如果不提供则为服务端当前时间。
    private String time;
    //指标值
    private String value;

    public YWPerformanceEntity() {
    }

    public YWPerformanceEntity(String metric, JSONObject tags, String time, String value) {
        this.metric = metric;
        this.tags = tags;
        this.time = time;
        this.value = value;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public JSONObject getTags() {
        return tags;
    }

    public void setTags(JSONObject tags) {
        this.tags = tags;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
