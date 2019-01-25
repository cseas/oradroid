package com.absingh;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
//import android.util.Log;

public class ConnectOra {
    private Connection conn;
    private Statement stmt;
    public ConnectOra() throws ClassNotFoundException, FileNotFoundException, IOException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@192.168.43.47:1521/XE";

            Properties prop = new Properties();
            String propFileName = "config.properties";
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            if (inputStream != null) { prop.load(inputStream); }
            else { throw new FileNotFoundException("Property file '" + propFileName + "' not found in the classpath"); }
            String userid = prop.getProperty("username");
            String password = prop.getProperty("password");

            this.conn = DriverManager.getConnection(url, userid, password);
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