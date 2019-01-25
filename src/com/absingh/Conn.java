package com.absingh;

import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.pool.OracleDataSource;

public class Conn {
    public static void main(String[] args) {
        System.out.println("Doing good so far");
    }
}

public class DataHandler {
    public DataHandler() {}

    String jdbcUrl = null;
    String userid = null;
    String password = null;
    Connection conn;

    public void getDBConnection() throws SQLException{
        OracleDataSource ds;
        ds = new OracleDataSource();
        ds.setURL(jdbcUrl);
        conn=ds.getConnection(userid,password);
    }
}