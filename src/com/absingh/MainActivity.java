package com.absingh;

//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

public class MainActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
    public static void main(String[] args) {
        try {
//            super.onCreate(savedInstanceState);
            ConnectOra db = new ConnectOra();
            ResultSet rset = db.getResult();

            ResultSetMetaData rsmd = rset.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            // print column names
            for(int i=1; i<=columnsNumber; i++)
                System.out.print(rsmd.getColumnName(i) + "\t\t\t\t");
            System.out.println();

            while (rset.next()) {
                for (int i = 1; i <= columnsNumber; i++) {

                    if (i > 1) System.out.print(",\t\t\t");
                    String columnValue = rset.getString(i);
//                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
                    System.out.print(columnValue);
                }
                System.out.println("");
            }

            db.closeStmt();
        } catch (Exception e) {
//            System.out.print(e);
            e.printStackTrace();
        }
    }
//    }
//
//    public void btn(View view) {
//        startActivity(new Intent(MainActivity.this, MainActivity.class));//Just to refresh the mainact.
//    }
}
