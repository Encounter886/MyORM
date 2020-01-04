package com.zzx.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public interface CallBack {
    public Object  doExcute(Connection conn, PreparedStatement ps, ResultSet rs);
}
