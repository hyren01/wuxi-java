package com.jn.primiary.beyondsoft.strategy;

import java.sql.*;

public class Main2 {

    public static void main(String[] args) {
        try(Connection conn = Main2.getConnection()){
            String checkSql = "SELECT * FROM serdbequipmentmanage";
            PreparedStatement preState = conn.prepareStatement(checkSql);
            //preState.setInt(0,0);
            ResultSet rs = preState.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            System.out.println(metaData.getColumnCount());
            if(rs.next()) {
                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        Connection conn = null;
        String driveName = "org.postgresql.Driver";
        String url = "jdbc:postgresql://10.1.22.146:5432/cddb";
        String username = "cddb";
        String password = "qwer1234";
        String schema = "cddb";

        Class.forName(driveName);
        conn = DriverManager.getConnection(url,username,password);

        return conn;
    }

}
