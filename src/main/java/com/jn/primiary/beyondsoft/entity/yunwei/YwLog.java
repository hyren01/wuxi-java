package com.jn.primiary.beyondsoft.entity.yunwei;

import java.util.List;

public class YwLog {
    //样例数据 以文档为准
    private String systemNumber;
    private String systemAddress;
    private List<YwData> data;

    public String getSystemNumber() {
        return systemNumber;
    }

    public void setSystemNumber(String systemNumber) {
        this.systemNumber = systemNumber;
    }

    public String getSystemAddress() {
        return systemAddress;
    }

    public void setSystemAddress(String systemAddress) {
        this.systemAddress = systemAddress;
    }

    public List<YwData> getData() {
        return data;
    }

    public void setData(List<YwData> data) {
        this.data = data;
    }
}
