package com.jn.primiary.beyondsoft.Connection;

import java.io.Closeable;
import java.sql.Connection;

public abstract class AbstractConnection implements Closeable {
    public String url;
    public String driveName;
    public String username;
    public String password;
    public Connection conn;

    public AbstractConnection(String url, String driveName, String username, String password) {
        this.url = url;
        this.driveName = driveName;
        this.username = username;
        this.password = password;
    }

    public abstract Connection getConnection();

    public abstract void close();


}
