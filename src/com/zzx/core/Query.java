package com.zzx.core;

import com.zzx.bean.ColumnInfo;
import com.zzx.bean.TableInfo;
import com.zzx.po.Goods;
import com.zzx.utils.JDBCUtils;
import com.zzx.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 对外提供查询服务的核心类
 */
public abstract class Query implements Cloneable{

    /**
     * 采用模板方法将JDBC操作风阻航成模板，便于重用
     * @param sql sql语句
     * @param params sql的参数
     * @param clazz 记录封装到的java类
     * @param callBack   callblack的匿名实现类
     * @return
     */
    public Object excuteQueryTemplate(String sql,Object[] params,Class clazz,CallBack callBack){
        Connection conn = DBManager.getConn();
        List list = null;//存放查询结果
        PreparedStatement ps =null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            JDBCUtils.handleParams(ps,params);
            rs = ps.executeQuery();
          return   callBack.doExcute(conn,ps,rs);
            }
         catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DBManager.close(ps,conn);
        }
    }


    /**
     * 直接执行一个DML查询
     * @param sql
     * @param params
     * @return   执行sql后影响的记录行数
     */
    public int excuteDML(String  sql,Object[] params){
        Connection conn = DBManager.getConn();
        int count = 0;
        PreparedStatement ps =null;
        try {
            ps = conn.prepareStatement(sql);

            JDBCUtils.handleParams(ps,params);
            System.out.println("sql:---------->"+sql+"----ps:"+ps);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DBManager.close(ps,conn);
        }
        return count;
    }




    /**将一个对对象存储到数据库中
     * 把对象中不为空的属性往数据库里存储。如果数字为null，则放0
     * 存储一个对象到数据库中
     * @param object
     */
    public void insert (Object object){
        //obj---->表中   insert into tablename(id,,,) values(?,?,?,?)
        Class  c = object.getClass();
        TableInfo tableInfo = TableContext.poClassTableMap.get(c);

        List<Object> params = new ArrayList<>();//存储sql参数对象
        StringBuilder sql = new StringBuilder();
        sql.append("insert into "+tableInfo.getTname()+" (");
        int countNotNullField = 0;//计算属性不为空的值
        Field[] fs = c.getDeclaredFields();
        for (Field f:
                fs) {
            String fieldName = f.getName();
            Object fieldValue = ReflectionUtils.invokeGet(fieldName,object);

            if(fieldValue!=null){
                countNotNullField++;
                sql.append(fieldName+",");
                params.add(fieldValue);
            }
        }
        sql.setCharAt(sql.length()-1, ')');
        sql.append(" values (");

        for(int i = 0 ;i<countNotNullField;i++){
            sql.append("?,");
        }
        sql.setCharAt(sql.length()-1, ')');
        excuteDML(sql.toString(),params.toArray());
    }




    /**
     * 删除clazz类对应的表中的记录（指定id记录）
     * @param clazz 跟表对应的Class对象
     * @param id 主键的值
     * @return  return执行sql后影响的记录行数
     */
    public void delete(Class clazz,Object id) {
        //delete from customer where id=2
        TableInfo tableInfo = TableContext.poClassTableMap.get(clazz);

        ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();
        String sql = "delete from "+tableInfo.getTname()+" where "+onlyPriKey.getName()+"=? ";
        excuteDML(sql,new Object[]{id});

    }



    /**
     * 删除对象在数据库中对应的记录（对象所在类对应到表，对象主键值对应到记录）
     * @param object
     *
     */
    public void delete (Object object) {
        System.out.println(((Goods)object).getId()+"-----第二次打印goods"+object);
        Class c = object.getClass();
        TableInfo tableInfo = TableContext.poClassTableMap.get(c);
        ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();//主键
        System.out.println(onlyPriKey.getName()+"---------"+object);
        //通过反射机制调用对应的get方法或者set方法
        Object priKeyValue = ReflectionUtils.invokeGet(onlyPriKey.getName(),object);
        System.out.println(onlyPriKey.getName()+priKeyValue);
        delete(c,priKeyValue);
    }



    /**
     * 更新记录
     *
     * @param object  更新的对象
     * @param fielNames  更新的属性列表
     * @return       * @return  行sql后影响的记录行数
     */
    public int update (Object object,String[] fielNames) {
        // obj{ "uname","psw"}--> update tabalename set name=? ,pwd=? where id = ?
        Class  c = object.getClass();
        TableInfo tableInfo = TableContext.poClassTableMap.get(c);
        List<Object> params = new ArrayList<>();//存储sql参数对象
        StringBuilder sql = new StringBuilder();
        sql.append("update "+tableInfo.getTname()+" set ");

        for (String fname:fielNames){
            Object fvalue = ReflectionUtils.invokeGet(fname,object);
            params.add(fvalue);
            sql.append(fname+"=?,");
        }

        ColumnInfo priKey = tableInfo.getOnlyPriKey();
        sql.setCharAt(sql.length()-1, ' ');
        sql.append(" where ");
        sql.append(priKey.getName()+"=?");

        params.add(ReflectionUtils.invokeGet(priKey.getName(),object));
        return excuteDML(sql.toString(),params.toArray());
    }



    /**
     * 查询返回多行记录，并将每行记录封装到clazz指定的对象中
     * @param sql  查询语句
     * @param clazz 封装数据的javabean类的Class对象
     * @param params  sql 的参数
     * @return  查询到的结果过
     */
    public List queryRows(final String sql, final Class clazz,final Object[] params) {

        return (List)excuteQueryTemplate(sql, params, clazz, new CallBack() {
            @Override
            public Object doExcute(Connection conn, PreparedStatement ps, ResultSet rs) {
                List list = null;//存放查询结果
                try {
                    ResultSetMetaData metaData = rs.getMetaData();
                    //多行
                    while (rs.next()){
                        if(list== null){
                            list = new ArrayList();
                        }
                        Object rowObject = clazz.newInstance();//调用javabean的无参构造器
                        //多列 select uname,psw,age from user where id>? and age >18
                        for (int i=0;i<metaData.getColumnCount();i++){
                            String colomnName = metaData.getColumnLabel(i+1);//列名
                            Object columnValue = rs.getObject(i+1);//列值
                            //调用反射方法，将值传进去 setNmae(Object param)
                            ReflectionUtils.invokeSet(rowObject,colomnName,columnValue);
                        }
                        list.add(rowObject);
                    }
                    return list;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return list;
            }
        });
    }




    /**
     *
     * 查询返回一行记录，并将记录封装到clazz指定的对象中
     * @param sql  查询语句
     * @param clazz 封装数据的javabean类的Class对象
     * @param params  sql 的参数
     * @return  查询到的结果过
     */
    public Object queryUniqueRows(String sql, Class clazz, Object[] params){
        List list = queryRows(sql,clazz,params);
        return (list!=null&&list.size()>0)?list.get(0):null;
    }



    /**
     * 查询返回一个值
     * @param sql  查询语句
     * @param params  sql 的参数
     * @return  查询到的结果过
     */
    public Object queryValue(String sql,Object[] params) {
        return excuteQueryTemplate(sql, params, null, new CallBack() {
            @Override
            public Object doExcute(Connection conn, PreparedStatement ps, ResultSet rs) {
                Object value = null;

                    try {
                        while (rs.next()) {
                            value = rs.getObject(1);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                }
                return value;
            }
        });

    }



    /**
     * 查询返回一个数字
     * @param sql  查询语句
     * @param params  sql 的参数
     * @return  查询到的结果过
     */
    public Number queryNumner(String sql,Object[] params){
        return  (Number) queryValue(sql,params);
    }

    /**
     * 分页查询
     * @param pageNum  第几页
     * @param size  每页显示多少记录
     * @return
     */
    public abstract Object queryPagenate(int pageNum,int size);

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
