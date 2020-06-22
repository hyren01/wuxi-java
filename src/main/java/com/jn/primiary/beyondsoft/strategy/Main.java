package com.jn.primiary.beyondsoft.strategy;

import com.jn.primiary.beyondsoft.service.CheckMoudleService;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.Closeable;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
//        String driveName = "org.postgresql.Driver";
//        String url = "jdbc:postgresql://10.1.22.146:5432/cddb";
//        String username = "cddb";
//        String password = "qwer1234";
//        String schema = "cddb";

        String driveName = "org.postgresql.Driver";
        String url = "jdbc:postgresql://10.1.20.148:5432/hrsdxg";
        String username = "hrsdxg";
        String password = "hrsdxg";
        String schema = "hrsdxg";

        DriverManagerDataSource dataSource = DataSoureProvider.getInstance(driveName, url, username, password, schema);




        //String checkSql = "SELECT COUNT(*) as num FROM serdbequipmentmanage WHERE LENGTH(服务ID) > 0";
        //String checkSql = "SELECT * FROM serdbequipmentmanage";
        String checkSql = "SELECT * FROM sys_user";
        try(Connection conn = dataSource.getConnection()) {
            PreparedStatement preState = conn.prepareStatement(checkSql);
            //preState.setInt(0,0);
            ResultSet rs = preState.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            System.out.println(metaData.getColumnCount());
            if(rs.next()) {
                System.out.println(rs.getString("0"));
                System.out.println(rs.getString("1"));
                System.out.println(rs.getString("2"));
                System.out.println(rs.getString("3"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }


    private static class DataSoureProvider implements Closeable {
        private static DriverManagerDataSource dataSource = new DriverManagerDataSource();

        public static DriverManagerDataSource getInstance(String driverName, String url, String username,
                                                          String password, String schema) {
            dataSource.setDriverClassName(driverName);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            dataSource.setSchema(schema);

            return dataSource;
        }

        static void manuClose() {
            try {
                Connection conn = dataSource.getConnection();
                if(!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void close() {
            DataSoureProvider.manuClose();
        }
    }


}
