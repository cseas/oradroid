/* java program to connect to oracle database 11g
 * Created by Abhijeet Singh
 * cseas.github.io
 */

package com.absingh;

import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.pool.OracleDataSource;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class Conn {
    public static void main(String[] args) throws SQLException {
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

    String jdbcUrl = "jdbc:oracle:thin:<username>/<password>@192.168.43.47:1521:XE";
    String userid = "<username>";
    String password = "<password>";
    Connection conn;

    public void getDBConnection() throws SQLException {
        OracleDataSource ds;
        ds = new OracleDataSource();
        ds.setURL(jdbcUrl);
        conn=ds.getConnection(userid,password);
    }

    Statement stmt;
    ResultSet rset;
    String query;
    String sqlString;

    public ResultSet getAllEmployees() throws SQLException {
            getDBConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            query = "SELECT * FROM Student ORDER BY stud_id";
            System.out.println("\nExecuting query: " + query);
            rset = stmt.executeQuery(query);
            return rset;
    }
}