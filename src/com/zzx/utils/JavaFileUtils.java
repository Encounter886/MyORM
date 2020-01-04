package com.zzx.utils;

import com.zzx.bean.ColumnInfo;
import com.zzx.bean.JavaFieldGetSet;
import com.zzx.bean.TableInfo;
import com.zzx.core.DBManager;
import com.zzx.core.MysqlTypeConvertor;
import com.zzx.core.TableContext;
import com.zzx.core.TypeConvertor;
import javafx.scene.control.Tab;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 封装了生成java（源代码）的操作
 */
public class JavaFileUtils {

    /**
     * 根据字段信息生成java属性信息。如 var username ---->private String username
     * @param column  字段信息
     * @param convertor 类型转化器
     * @return   java属性和set/get方法源码
     */
    public static JavaFieldGetSet createFieldGetSetSRC(ColumnInfo column, TypeConvertor convertor){
        JavaFieldGetSet jfgs = new JavaFieldGetSet();
        String javaFieldType = convertor.databaseTypeToJavaType(column.getDataType());

      jfgs.setFieldInfo("\tprivate "+javaFieldType+" "+column.getName()+";\n");

      //get
      StringBuilder getSrc = new StringBuilder();
      getSrc.append("\tpublic "+javaFieldType+" get"+StringUtils.firstCharToUpperCase(column.getName())+
              "(){\n");
      getSrc.append("\t\treturn "+column.getName()+";\n");
      getSrc.append("\t}\n");
      jfgs.setGetInfo(getSrc.toString());

      //set
        StringBuilder setSrc = new StringBuilder();
        setSrc.append("\tpublic void set"+StringUtils.firstCharToUpperCase(column.getName())+
                "(");
        setSrc.append(javaFieldType+" "+column.getName()+"){\n");
        setSrc.append("\t\tthis."+column.getName()+"="+column.getName()+";\n");
        setSrc.append("\t}\n");
        jfgs.setSetInfo(setSrc.toString());
        return   jfgs;
    }

    /**
     *根据表信息生成java类的源代码
     * @param tableInfo
     * @param convertor 数据类型转换器
     * @return java类的源代码
     */
    public static String createJavaSrc(TableInfo tableInfo,TypeConvertor convertor){

        Map<String,ColumnInfo> columns =  tableInfo.getColumns();
        List<JavaFieldGetSet> javaFields = new ArrayList<>();
        for(ColumnInfo c:columns.values()){
            javaFields.add(createFieldGetSetSRC(c,convertor));
        }

        StringBuilder src = new StringBuilder();

        //生成package语句
                 src.append("package "+ DBManager.getConf().getPoPackage()+";\n\n");
        //生成import语句
               src.append("import java.sql.*;\n");
        src.append("import java.util.*;\n\n");

        //生成类声明语句
        src.append("public class "+StringUtils.firstCharToUpperCase(tableInfo.getTname())+" {\n\n");
        //生成属性列表
        for (JavaFieldGetSet f:javaFields) {
            src.append(f.getFieldInfo());
        }
        src.append("\n\n");

       //生成get
        for (JavaFieldGetSet f:javaFields) {
            src.append(f.getGetInfo());
        }
        //生成set

        for (JavaFieldGetSet f:javaFields) {
            src.append(f.getSetInfo());
        }

        //生成类结束
        src.append("}\n");
        return src.toString();
    }

    public static  void createJavaPOFile(TableInfo tableInfo,TypeConvertor convertor){

       String src =  createJavaSrc(tableInfo,convertor);
       String srcPath = DBManager.getConf().getSrcPath()+"/";
       String packagePath = DBManager.getConf().getPoPackage().replaceAll("\\.","/");
        System.out.println(packagePath);
       File f = new File(srcPath+packagePath);
        System.out.println(f.getAbsolutePath() +"***********************");
        if(!f.exists()){//如果指定目录不存在，则创建目录
            f.mkdirs();
        }
        BufferedWriter bw = null;
        try {
            bw= new BufferedWriter(new FileWriter(f.getAbsoluteFile()+"/"+StringUtils.firstCharToUpperCase(tableInfo.getTname()+".java")));
            bw.write(src);
            System.out.println("建立表"+tableInfo.getTname()+"对应的java类"+StringUtils.firstCharToUpperCase(tableInfo.getTname()+".java"));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bw!=null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {
       /* ColumnInfo ci = new ColumnInfo("uername","varchar",0);
        JavaFieldGetSet f =  createFieldGetSetSRC(ci, new MysqlTypeConvertor());
        System.out.println(f);*/

       /*Map<String,TableInfo> map =  TableContext.tables;
        for (TableInfo t:
             map.values()) {
            createJavaPOFile(t,new MysqlTypeConvertor());
        }*/
    }
}

