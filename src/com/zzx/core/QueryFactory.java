package com.zzx.core;
//Query对象生成
public class QueryFactory {

    private static QueryFactory factory = new QueryFactory();
    private static Query prototypeObj;//原型对象
    static {
        try {
            //加载指定的query类
            Class c = Class.forName( DBManager.getConf().getQueryClass());
            prototypeObj = (Query) c.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private QueryFactory(){}

     public static Query creatQuery(){
         try {
             return (Query) prototypeObj.clone();
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
     }

}
