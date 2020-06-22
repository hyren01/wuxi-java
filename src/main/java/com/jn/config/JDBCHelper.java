package com.jn.config;

import com.jn.primiary.beyondsoft.entity.CodeInfo;
import com.jn.primiary.beyondsoft.entity.JdbcEntity;
import org.apache.log4j.Logger;
import java.sql.*;
import java.util.LinkedList;


public class JDBCHelper {

    //定义MySQL的数据库驱动程序
    private static String driverClassName = "";
    //定义MySQL数据库的连接地址
    private static String url = "";
    //MySQL数据库的连接用户名
    private static String username = "";
    //MySQL数据库的连接密码
    private static String password = "";

    private Logger logger = Logger.getLogger(JDBCHelper.class);
    private int datasourceSize = 10;

    static{
        try {
            JdbcEntity jdbcEntity = SpringContextUtil.getBean(JdbcEntity.class);
            driverClassName = jdbcEntity.getDriverClassName();
            url = jdbcEntity.getUrl();
            username = jdbcEntity.getUsername();
            password = jdbcEntity.getPassword();

            String driver = driverClassName;
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JDBCHelper instance = null;
    public static JDBCHelper getInstance() {
        if(instance == null) {
            synchronized(JDBCHelper.class) {
                if(instance == null) {
                    instance = new JDBCHelper();
                }
            }
        }
        return instance;
    }

    private LinkedList<Connection> datasource = new LinkedList<Connection>();
    private JDBCHelper() {
        try {
            for(int i = 0; i < datasourceSize; i++) {
                Connection conn = DriverManager.getConnection(url, username, password);
                datasource.push(conn);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized Connection getConnection() {
        while(datasource.size() == 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return datasource.poll();
    }


    /**
     * 创建表
     * @param conn
     * @param tableSql
     */
    public void createTable(Connection conn,String tableSql){
        try {
            Statement smt = conn.createStatement();
            if (conn != null) {
                logger.info("数据库连接成功！");
                int i = smt.executeUpdate(tableSql);//DDL语句返回值为0;
                if (i == 0) {
                    logger.info(tableSql + "表已经创建成功！");
                }
            }
        } catch (SQLException e1) {
            logger.info("创建失败");
            e1.printStackTrace();
        }
    }


    /**
     * 删除表
     * @param conn
     * @param tableSql
     */
    public void dropTable(Connection conn,String tableSql){
        try {
            Statement smt = conn.createStatement();
            if (conn != null) {
                logger.info("数据库连接成功！");
                int i = smt.executeUpdate(tableSql);//DDL语句返回值为0;
                if (i == 0) {
                    logger.info(tableSql + "表删除成功！");
                }
            }
        } catch (SQLException e1) {
            logger.info("删除失败");
            e1.printStackTrace();
        }
    }

    public void insertTable(Connection conn, String tableSql, CodeInfo codeInfo){
        try {
            PreparedStatement pstmt;
            pstmt = (PreparedStatement) conn.prepareStatement(tableSql);
            pstmt.setString(1, codeInfo.getId());
            pstmt.setString(2, codeInfo.getCodeEname());
            pstmt.setString(3, codeInfo.getCodeCname());
            pstmt.setString(4, codeInfo.getCodeValue());
            //Statement pstmt = conn.createStatement();
            int i = pstmt.executeUpdate();
            if(i == 1){
                logger.info(tableSql + "表插入成功！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean table_is_exist(Connection conn,String tablename){
        try {
            ResultSet rs = conn.getMetaData().getTables(null, null, tablename, null);
            if (rs.next()) {
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }


}
