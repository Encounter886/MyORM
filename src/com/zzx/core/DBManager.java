package com.zzx.core;

import com.zzx.bean.Configuration;
import com.zzx.pool.DBConnPool;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * 根据配置信息，维持连接对象的管理（增加连接池功能）
 */
public class DBManager {
    private  static Configuration conf;
    //连接池对象
    private static DBConnPool pool;
    static {//静态代码块
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        conf = new Configuration();
        conf.setDriver(properties.getProperty("driver"));
        conf.setPoPackage(properties.getProperty("poPackage"));
        conf.setPassword(properties.getProperty("password"));
        conf.setSrcPath(properties.getProperty("srcPath"));
        conf.setUrl(properties.getProperty("url"));
        conf.setUsingDB(properties.getProperty("usingDB"));
        conf.setUser(properties.getProperty("user"));
        conf.setQueryClass(properties.getProperty("queryClass"));
        conf.setPoolMinSize(Integer.parseInt(properties.getProperty("poolMinSize")));
        conf.setPoolMaxSize(Integer.parseInt(properties.getProperty("poolMaxSize")));
        //加载TableContext信息
        System.out.println(TableContext.class);
    }

    /**
     * 获得新的连接对象
     * @return
     */
    public static Connection getConn(){
        if(pool == null){
            pool = new DBConnPool();
        }
       return pool.getConnection();
    }

    public static Connection createConn(){
        try {
            System.out.println("开始获取"+conf.getDriver()+"*");
            Class.forName(conf.getDriver());
            System.out.println(conf.getDriver()+"------"+ conf.getUrl()+"---"+conf.getUser()+"---"+conf.getPassword());
            return DriverManager.getConnection(conf.getUrl(),conf.getUser(),conf.getPassword());//目前直接建立连接，后期增加连接池处理，提高效率
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("建立连接失败");
            return null;
        }
    }

    public static void close(ResultSet rs,Statement ps,Connection conn){
            try {
                if(rs!=null){
                rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (ps!=null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn!=null){
                    /*conn.close();*/
                    pool.close(conn);
            }
    }

    public static void close(Statement ps,Connection conn){
        if (ps!=null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn!=null){
           pool.close(conn);
        }

    }
    public static void close(Connection conn){
        if (conn!=null){
            pool.close(conn);
        }
    }

    /**
     * 返回cconf对象
     * @return
     */
    public static Configuration getConf(){
        return conf;
    }
}
