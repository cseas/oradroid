/* java program to connect to oracle database 11g
 * Created by Abhijeet Singh
 * cseas.github.io
 */

package com.absingh;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.pool.OracleDataSource;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Properties;

public class ConnJava {
    public static void main(String[] args) throws SQLException, FileNotFoundException, IOException {
        DataHandler dh = new DataHandler();
        ResultSet rset = dh.getAllEmployees();

        ResultSetMetaData rsmd = rset.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (rset.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = rset.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println("");
        }
    }
}

class DataHandler {
    public DataHandler() {}

    Connection conn;

    public void getDBConnection() throws SQLException, FileNotFoundException, IOException {
        OracleDataSource ds;
        ds = new OracleDataSource();

        Properties prop = new Properties();
        String propFileName = "config.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        if (inputStream != null) { prop.load(inputStream); }
        else { throw new FileNotFoundException("Property file '" + propFileName + "' not found in the classpath"); }
        String userid = prop.getProperty("username");
        String password = prop.getProperty("password");
        String jdbcUrl = "jdbc:oracle:thin:" + userid + "/" + password + "@192.168.43.47:1521:XE";

        ds.setURL(jdbcUrl);
        conn=ds.getConnection(userid,password);
    }

    Statement stmt;
    ResultSet rset;
    String query;
    String sqlString;

    public ResultSet getAllEmployees() throws SQLException, FileNotFoundException, IOException {
            getDBConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            query = "SELECT * FROM Student ORDER BY stud_id";
            System.out.println("\nExecuting query: " + query);
            rset = stmt.executeQuery(query);
            return rset;
    }
}