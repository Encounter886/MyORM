package com.zzx.core;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

/**
 *测试类
 */
public class Client {
    public static void main(String[] args) {
        Query query = QueryFactory.creatQuery();
        Number  o= (Number) query.queryValue("select count(*) from goods where id>?"
                ,new Object[]{2});
        System.out.println(o);


        /*Connection con = DBManager.getConn();*/
      /*  try {
            DatabaseMetaData dbmd = con.getMetaData();
           List<String> tables = (List<String>) dbmd.getTables(con.getCatalog(),"%","%",new String[]{"TABLE"});
                      System.out.println("---------------获取" + con.getCatalog() + "库的所有表名----------------");

            System.out.println(tables);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error");
        }*/
    }
}
