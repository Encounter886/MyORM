package com.zzx.core;

import com.zzx.bean.ColumnInfo;
import com.zzx.bean.TableInfo;
import com.zzx.utils.JavaFileUtils;
import com.zzx.utils.StringUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 负责管理数据库，并且可以根据表结构生成类结构
 */
public class TableContext {
    //表名字为key,表信息为value
    public static Map<String, TableInfo> tables = new HashMap<>();
    //将po的class对象和表信息对象关联起来，便于重用
    public static Map<Class,TableInfo> poClassTableMap = new HashMap<>();

    private TableContext(){}

    static {
        try {
            Connection con = DBManager.getConn();
            DatabaseMetaData dbmd = con.getMetaData();

            ResultSet tableRest = dbmd.getTables(con.getCatalog(),"%","%",new String[]{"TABLE"});

            while (tableRest.next()){
                String tableName = (String) tableRest.getObject("TABLE_NAME");
                TableInfo ti = new TableInfo(tableName,new ArrayList<ColumnInfo>(),new HashMap<String ,ColumnInfo>());
                tables.put(tableName,ti);

                //查询表中所有字段
                ResultSet set = dbmd.getColumns(con.getCatalog(),"%",tableName,"%");
                while (set.next()){
                    ColumnInfo ci = new ColumnInfo(set.getString("COLUMN_NAME"),
                            set.getString("TYPE_NAME"),0);
                    ti.getColumns().put(set.getString("COLUMN_NAME"),ci);
                }
                ResultSet set2 = dbmd.getPrimaryKeys(con.getCatalog(),"%",tableName);//查询t_user中的主键
                while (set2.next()){
                    ColumnInfo ci2 = (ColumnInfo) ti.getColumns().get(set2.getObject("COLUMN_NAME"));
                    ci2.setKeyType(1);//设置为主键类型
                    ti.getPriKeys().add(ci2);
                }
                 if(ti.getPriKeys().size()>0){
                     //取唯一主键，如果为联合主键，则为空！
                     ti.setOnlyPriKey(ti.getPriKeys().get(0));
                 }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //更新类结构
        updateJavaPOFile();
         //加载po包下得所有的类
        loadPOTables();
    }

    /**
     * 根据表结构，更新配置po包下的java类
     * 实现了从表结构转换到类结构
     */
    public static void updateJavaPOFile(){
    Map<String,TableInfo> map =  TableContext.tables;
    for (TableInfo t:
            map.values()) {
        JavaFileUtils.createJavaPOFile(t,new MysqlTypeConvertor());
    }
}

    /**
     * 加载po包下的类
     */
    public static void loadPOTables(){

        for (TableInfo tableInfo:tables.values()){
            Class c = null;
            try {
                 c = Class.forName((DBManager.getConf().getPoPackage()+"."
                         + StringUtils.firstCharToUpperCase(tableInfo.getTname())));
            poClassTableMap.put(c, tableInfo);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

        }


}
