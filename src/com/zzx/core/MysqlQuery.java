package com.zzx.core;

import com.zzx.bean.ColumnInfo;
import com.zzx.bean.TableInfo;


import com.zzx.po.Goods;
import com.zzx.utils.JDBCUtils;
import com.zzx.utils.ReflectionUtils;
import com.zzx.utils.StringUtils;
import javafx.scene.control.Tab;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 针对mysql数据库的查询操作
 */
public class MysqlQuery extends Query {

    @Override
    public Object queryPagenate(int pageNum, int size) {
        return null;
    }


    /**
     * 下面都是测试类，忽略
     */
 /*删除测试
 public static void main(String[] args) {
     Goods goods = new Goods();
        goods.setId(16);
      System.out.println(goods.getId()+"-----第一次打印goods"+goods);
      new MysqlQuery().delete(goods);
    }*/

/* //添加操作测试
 public static void main(String[] args) {
     Goods goods = new Goods();
     goods.setId(16);
     goods.setGoodsName("zheshi ge shabi");
     goods.setMax(3);
     goods.setContext("hello");
     System.out.println(goods.getId()+"-----第一次打印goods"+goods);
     new MysqlQuery().insert(goods);
 }*/

//修改update操作测试
/*public static void main(String[] args) {
    Goods goods = new Goods();
    goods.setId(16);
    goods.setGoodsName("ziransahbi");
    goods.setPrice(12);
     goods.setContext("1");
    System.out.println(goods.getId()+"-----第一次打印goods"+goods);
    new MysqlQuery().update(goods,new String[]{"goodsName","price"});
}*/


 /*public static void main(String[] args) {
       List<Goods> list =  new MysqlQuery().queryRows("select id,goodsName,context from goods where id>? and id<?"
                ,Goods.class,new Object[]{2,20});
        System.out.println(list);
        for (Goods goods : list){
            System.out.println(goods.getGoodsName());
        }
    }*/

 /*public static void main(String[] args) {
       Number  o =(Number) new MysqlQuery().queryValue("select count(*) from goods where id>?"
        ,new Object[]{2});
        System.out.println(o);
    }*/

}
