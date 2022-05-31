package com.example.logdatabase01;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class InsertDb extends AsyncTask<Void, Void, String> {
    Activity activity = null;
    Integer id1,d1,d2,d3,d4;
    String tblname ;
    public InsertDb(Activity act,int data1,int data2,int data3,int data4,String tbl) {
        activity = act;
        d1 = data1;
        d2 = data2;
        d3 = data3;
        d4 = data4;

        tblname = tbl;
    }

    protected String doInBackground(Void... params){
        String text1="";
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            //Connection conn= DriverManager.getConnection("jdbc:mysql://172.23.156.178:3306/test?characterEncoding=utf8","all","sinno0203");
            Connection conn= DriverManager.getConnection("jdbc:mysql://172.16.129.41/test?characterEncoding=ascii","all","sinno0203");

            Statement stmt = conn.createStatement();
            ResultSet rs;




            PreparedStatement ps;
            Date time;
            SimpleDateFormat dt2;
            String stime;


            //データ保存

            time = new Date();
            dt2 = new SimpleDateFormat("HH:mm:ss");
            stime = dt2.format(time);

            String SQL2 = "INSERT INTO `" + tblname +"` values (NULL,?,?,?,?,?,?)";

            ps = conn.prepareStatement(SQL2);
            //ps.setInt(1, id1);
            ps.setInt(1, d1);
            ps.setInt(2, d2);
            ps.setInt(3, d3);
            ps.setInt(4, d4);

            ps.setString(5, "ANDROID");
            ps.setString(6, stime);

            ps.executeUpdate();




        } catch (Exception e) {
            text1=e.getMessage();

        }
        return text1;
    }

    protected void onPostExecute(String result){

    }
}
