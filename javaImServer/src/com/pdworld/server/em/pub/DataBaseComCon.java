package com.pdworld.server.em.pub;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class DataBaseComCon {

    private static String driverName = "";

    private static  String url = "";

    private static  String databaseusername="";

    private static  String databaseuserpwd="";

    private static Connection conn = null;

    private DataBaseComCon() {
    }
    private static void loadproperties(){
        FileInputStream fis;
        try {
//			fis = new FileInputStream("SQLSERVER2000.properties");//连接SQLSERVER2000数据库
            fis = new FileInputStream("C:\\Users\\Lifeix\\Documents\\IMJava\\javaImServer\\MYSQL.properties");//连接MYSQL数据库
            Properties propertie = new Properties();
            propertie.load(fis);
            driverName = propertie.getProperty("driver");
            url = propertie.getProperty("url");
            databaseusername = propertie.getProperty("databaseusername");
            databaseuserpwd = propertie.getProperty("databaseuserpwd");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static boolean testConnection() {
        Connection conn = null;
        try {
            loadproperties();
            Class.forName(driverName);
            String userName = databaseusername;
            String password = databaseuserpwd;
            conn = DriverManager.getConnection(url, userName, password);
            return true;
        } catch(Exception e) {

        } finally{
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e1) {
                }
            }
        }
        return false;
    }

    /**
     * 进行数据库连接
     * @return
     */
    private static Connection databaseConn() {
        Connection conn = null;
        try {
            loadproperties();
            Class.forName(driverName);
            String userName = databaseusername;
            String password = databaseuserpwd;
            conn = DriverManager.getConnection(url, userName, password);
        } catch(Exception e) {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e1) {
                }
            }
            return null;
        }
        return conn;
    }

    /**
     * 取得一个数据库连接,采用单一模式
     * @return
     */
    public static Connection getConnection() {
        if (conn == null)
            conn = databaseConn();
//		System.out.println("数据库连接为："+conn);
        return conn;
    }

    /**
     * 关闭数据源连接
     * @return
     */
    public static boolean Close() {
        try {
            if (conn != null)
                conn.close();
            return true;
        } catch (SQLException e) {
            // e.printStackTrace();
        }
        return false;
    }
}
