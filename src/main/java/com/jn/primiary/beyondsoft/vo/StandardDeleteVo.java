package com.jn.primiary.beyondsoft.vo;

/**
 * @Des 标准删除VO
 * @Author chenhong
 * @Date 2019/10/23 16:46
 */
public class StandardDeleteVo {

    private String id;
    private String isAuth;
    private String batchNo;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(String isAuth) {
        this.isAuth = isAuth;
    }

    public StandardDeleteVo(String id, String isAuth, String batchNo) {
        this.id = id;
        this.isAuth = isAuth;
        this.batchNo = batchNo;
    }

    public StandardDeleteVo() {
        super();
    }
}

	
