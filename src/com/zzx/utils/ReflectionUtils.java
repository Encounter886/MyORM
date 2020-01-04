package com.zzx.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 封装了反射的常用操作
 */
public class ReflectionUtils {

    /**
     *  //根据obj对象对应属性fieldName获取get方法
     * @param
     * @param fieldName
     * @param obj
     * @return
     */
    public static Object invokeGet(String fieldName,Object obj){
        try {
            Class c = obj.getClass();
            System.out.println(StringUtils.firstCharToUpperCase(fieldName));
            Method m = c.getDeclaredMethod(("get"+ StringUtils.firstCharToUpperCase(fieldName)),null);
            Object priKeyValue = m.invoke(obj,null);

            return priKeyValue;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("prikeyvalue为空--------");
            return null;
        }
    }

    public  static void invokeSet(Object object,String colomnName,Object columnValue){

        Method m = null;
        try {
            m = object.getClass().getDeclaredMethod("set"+ StringUtils.firstCharToUpperCase(colomnName)
                    ,columnValue.getClass());
            m.invoke(object,columnValue);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
