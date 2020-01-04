package com.zzx.pool;

import com.zzx.core.DBManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBConnPool {
    private  List<Connection> pool;//连接对象
    /**
     *
     * 最大连接个数
     */
    private static final int POOL_MAX_SIZE=DBManager.getConf().getPoolMaxSize();
    private static  final int POOL_MIN_SIZE=DBManager.getConf().getPoolMinSize();

    static {

    }
    public void  initPool(){
        if(pool==null){
            pool = new ArrayList<Connection>();
        }
        while (pool.size()<DBConnPool.POOL_MIN_SIZE){
            pool.add(DBManager.createConn());
            System.out.println("初始化连接池："+pool.size());
        }
    }

    public DBConnPool(){
        initPool();
    }

    /**
     * 从连接池中取出连接
     * @return
     */
    public synchronized Connection getConnection(){
          int las_index = pool.size()-1;
          Connection conn = pool.get(las_index);
          pool.remove(las_index);
          return conn;
    }

    /**
     * 将连接放回池中
     * @param conn
     */
    public synchronized  void close(Connection conn){
     if(pool.size()>=POOL_MAX_SIZE){
         if(conn!=null){
             try {
                 conn.close();
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }
         }else {
         pool.add(conn);
     }
    }

}
