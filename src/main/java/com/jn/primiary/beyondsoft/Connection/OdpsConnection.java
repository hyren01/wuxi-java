package com.jn.primiary.beyondsoft.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OdpsConnection extends AbstractConnection{

    private String accessId;
    private String accessKey;

    public OdpsConnection(String url, String driveName, String username, String password) {
        super(url, driveName, username, password);
        this.accessId = username;
        this.accessKey = password;

        try {
            Class.forName(driveName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        try {
            conn = DriverManager.getConnection(url,accessId,accessKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    @Override
    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
