package com.jn.primiary.beyondsoft.vo;

/**
 * @Des 用于封装数据库连接信息
 */
public class DbConnInfoVo {

    private String driveName;
    private String url;
    private String username;
    private String password;
    private String schema;

    public String getDriveName() {
        return driveName;
    }

    public void setDriveName(String driveName) {
        this.driveName = driveName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public DbConnInfoVo(String driveName, String url, String username, String password, String schema) {
        this.driveName = driveName;
        this.url = url;
        this.username = username;
        this.password = password;
        this.schema = schema;
    }

    public DbConnInfoVo() {
        super();
    }
}

	
