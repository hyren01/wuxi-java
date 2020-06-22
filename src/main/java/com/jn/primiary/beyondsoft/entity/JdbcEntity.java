package com.jn.primiary.beyondsoft.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class JdbcEntity {
    //定义MySQL的数据库驱动程序
    private String driverClassName = "";
    //定义MySQL数据库的连接地址
    private String url = "";
    //MySQL数据库的连接用户名
    private String username = "";
    //MySQL数据库的连接密码
    private String password = "";

    @Value("${codeinfojdbc.driverClassName}")
    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    @Value("${codeinfojdbc.url}")
    public void setUrl(String url) {
        this.url = url;
    }

    @Value("${codeinfojdbc.username}")
    public void setUsername(String username) {
        this.username = username;
    }

    @Value("${codeinfojdbc.password}")
    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "JdbcEntity{" +
                "driverClassName='" + driverClassName + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
