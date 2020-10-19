package com.jn.primiary.beyondsoft.controller;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Main mm = new Main();
        mm.odpsTest();


    }

    public void odpsTest() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        try {
            //			String conn_url = getConn_url(type, database_ip, database_port, database_name);

            String url = "jdbc:odps:http://service.cn-wuxi-zcgy-d01.odps.public.env3.wx/api?project=cross_region_1";
            String driveName = "com.aliyun.odps.jdbc.OdpsDriver";
            String username = "6TllitB0ipDyoFTq";
            String password = "B3WjpbLpQrGUbiDl1jDHUnTqXqywpU";
            String dbname = "odps_test";
            String accessId = username;
            String accessKey = password;

//            String project_name= "swodps";
//            String accessId= "WyYyszNnwFByzbbt";
//            String accessKey= "GVkMOosO20hQUeBF5ucuiYW1kmju50";
//            String url= "jdbc:odps:"+"http://service.cn-wuxi-zcgy-d01.odps.public.env3.wx/api"+"?project=cross_region_1";
//            String log_view_host= "http://logview.cn-wuxi-zcgy-d01.odps.public.env3.wx";
//            String driveName = "com.aliyun.odps.jdbc.OdpsDriver";



            Class.forName(driveName);
            conn = DriverManager.getConnection(url,accessId,accessKey);

            String sql = "SELECT * FROM c1";
            String insertSql = "insert into c1 values('37377373',null,'gdffgh','','sdfasdf')";
            Statement statement = conn.createStatement();
//            ResultSet resultSet = statement.executeQuery(sql);
//            while(resultSet.next()){
//                System.out.println(String.valueOf(resultSet.getString(1))+"----"+String.valueOf(resultSet.getString(2)));
//            }
            boolean flag = statement.execute(insertSql);
            System.out.println(flag);



//            String tableName = "c1";
//            DatabaseMetaData metaData = conn.getMetaData();
//            ResultSet resultSet = metaData.getTables(null, null, tableName, null);
//            while(resultSet.next()){
//                System.out.println(resultSet.getString(1));
//                System.out.println(resultSet.getString(3));
//                System.out.println(resultSet.getString(4));
//            }



        }
        catch(Exception e) {
            e.printStackTrace();
        }finally {
            conn.close();
        }

    }

    public static void gbaseTest() throws Exception {
        //String url = "jdbc:gbase://172.16.45.104:5258/wxpridata";
        String url = "jdbc:gbase://172.16.25.204:5258/wxpridata";
        String driveName = "com.gbase.jdbc.Driver";
        String username = "gbase";
        String password = "gbase20110531";

        Class.forName(driveName);
        Connection conn = DriverManager.getConnection(url,username,password);

        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet resultSet = metaData.getTables("wxpridata", "gbase", "%1%", new String[]{"TABLE"});

        while(resultSet.next()){
            System.out.println(resultSet.getString(1));
            System.out.println(resultSet.getString(3));
            System.out.println(resultSet.getString(4));
        }
    }



}
