package com.zzx.bean;

import java.util.List;
import java.util.Map;

/**
 * 存储表结构信息
 */
public class TableInfo {
    private  String tname;//表名字
    private Map<String,ColumnInfo> columns;//所有字段信息

    /**
     * 唯一主键，目前只能处理表中有且仅有唯一主键的情况
     * ，
     */
    private  ColumnInfo onlyPriKey;

    private List<ColumnInfo> priKeys;//联合主键。为了以后扩张
    public TableInfo() {
    }

    public TableInfo(String tname,Map<String, ColumnInfo> columns,ColumnInfo onlyPriKey  ) {
        this.tname = tname;
        this.columns = columns;
        this.onlyPriKey = onlyPriKey;
    }

    public TableInfo(String tname,  List<ColumnInfo> priKeys,Map<String, ColumnInfo> columns) {
        this.tname = tname;
        this.columns = columns;
        this.priKeys = priKeys;
    }

    public List<ColumnInfo> getPriKeys() {
        return priKeys;
    }

    public void setPriKeys(List<ColumnInfo> priKeys) {
        this.priKeys = priKeys;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public Map<String, ColumnInfo> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, ColumnInfo> columns) {
        this.columns = columns;
    }

    public ColumnInfo getOnlyPriKey() {
        return onlyPriKey;
    }

    public void setOnlyPriKey(ColumnInfo onlyPriKey) {
        this.onlyPriKey = onlyPriKey;
    }
}
