package com.zzx.core;

import java.math.BigDecimal;

/**
 * Mysql数据类型和java数据类型转换
 */
public class MysqlTypeConvertor  implements TypeConvertor{
    @Override
    public String databaseTypeToJavaType(String columnType) {
       if("varchar".equalsIgnoreCase(columnType)||"char".equalsIgnoreCase(columnType)){
           return "String";
       }else if("int".equalsIgnoreCase(columnType)||
       "tinyint".equalsIgnoreCase(columnType)||
       "smallint".equalsIgnoreCase(columnType)||
       "integer".equalsIgnoreCase(columnType)){
           return "Integer";
       }else if("bigint".equalsIgnoreCase(columnType)){
           return "Long";
       }else if("double".equalsIgnoreCase(columnType)){
           return "Double";
       }else if("float".equalsIgnoreCase(columnType)){
           return "Float";
       }else if("decimal".equalsIgnoreCase(columnType)){
           return "  BigDecimal";
       }
       else if("clob".equalsIgnoreCase(columnType)){
           return "java.sql.CLob";
       } else if("blob".equalsIgnoreCase(columnType)){
           return "java.sql.BLob";
       }
       else if("date".equalsIgnoreCase(columnType)){
           return "java.sql.Date";
       }else if("time".equalsIgnoreCase(columnType)){
           return "java.sql.Time";
       }else if("timestap".equalsIgnoreCase(columnType)){
           return "java.sql.Timestap";
       }
        return null;
    }


    @Override
    public String javaTypeToDatabaseType(String javaDataType) {
        return null;
    }
}
