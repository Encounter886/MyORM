package com.zzx.core;

/**
 * 复杂java数据类型和数据库类型的互相转换
 * @Auther 周梓馨 ZHOU ZI XIN
 */
public interface TypeConvertor {

    /**
     * 将数据库数据类型转换成java类型
     * @param columnType 数据库字段数据两类型
     * @return java数据类型
     */
    public  String databaseTypeToJavaType(String columnType);

    /**
     * 将java类型转换成数据库数据类型
     * @param javaDataType java数据类型
     * @return 数据库数据类型
     */
    public  String javaTypeToDatabaseType(String javaDataType);
}
