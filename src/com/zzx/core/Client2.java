package com.zzx.core;

public class Client2 {
    public static void test(){
        Query query = QueryFactory.creatQuery();
        Number  o= (Number) query.queryValue("select count(*) from goods where id>?"
                ,new Object[]{2});
        System.out.println(o);
    }
    public static void main(String[] args) {
        //测试连接池的效率
        Long a = System.currentTimeMillis();
      for (int i=0;i<1000;i++)
      {
          test();
      }
        Long b = System.currentTimeMillis();
        System.out.println(b-a);//不用池子:5122ms   用池子:1363
    }
}
