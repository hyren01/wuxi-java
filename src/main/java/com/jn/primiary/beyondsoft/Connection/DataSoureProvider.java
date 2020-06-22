package com.jn.primiary.beyondsoft.Connection;

import com.jn.primiary.beyondsoft.entity.CommonException;

import java.sql.Connection;

public class DataSoureProvider {

    public String url;
    public String driverName;
    public String username;
    public String password;
    public AbstractConnection abstractConnection;

    public DataSoureProvider(String url, String driverName, String username, String password) {
        this.url = url;
        this.driverName = driverName;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() throws CommonException {

        try{

            if(driverName.contains("odps")){
                abstractConnection = new OdpsConnection(url,driverName,username,password);
            }else {
                abstractConnection = new CommonConnection(url,driverName,username,password);
            }
        }catch (Exception e){
            throw new CommonException("数据库连接失败："+url);
        }

        return abstractConnection.getConnection();
    }

    public void close(){
        abstractConnection.close();
    }




}
