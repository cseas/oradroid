package com.absingh;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import android.util.Log;

public class ConnectOra {
    private Connection conn;
    private Statement stmt;
    public ConnectOra() throws ClassNotFoundException {
        try {
            System.out.println("in try");
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@192.168.43.47:1521/XE";
            this.conn = DriverManager.getConnection(url,"<username>","<password>");
            this.conn.setAutoCommit(false);
            this.stmt = this.conn.createStatement();
        } catch(SQLException e) {
//            Log.d("tag", e.getMessage());
            System.out.println(e);
        }
    }

    public ResultSet getResult() throws SQLException {
        ResultSet rset = stmt.executeQuery("select * from student");
        return rset;
    }

    public void closeStmt() {
        try {
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}