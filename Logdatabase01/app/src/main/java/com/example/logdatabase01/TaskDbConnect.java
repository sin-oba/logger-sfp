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

public class TaskDbConnect extends AsyncTask<Void, Void, String> {
    Activity activity = null;
    public TaskDbConnect(Activity act) {
        activity = act;
    }

    protected String doInBackground(Void... params){
        String text1="";
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn= DriverManager.getConnection("jdbc:mysql://172.16.129.35:3306/test?characterEncoding=utf8","all","sinno0203");
            //Connection conn= DriverManager.getConnection("jdbc:mysql://172.29.125.210/test?characterEncoding=ascii","player","Sinno0203");

            Statement stmt = conn.createStatement();
            //ResultSet rs= stmt.executeQuery("Select * from test01");
            ResultSet rs;

            //テーブルの有無確認
            Date d = new Date();
            SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd");
            String c1 = d1.format(d);
            String tblname;
            Integer tblno = 1;
            tblname = c1 + "_" + tblno.toString();
            String qry1 = "SHOW TABLES LIKE '" + tblname +"'";
            rs = stmt.executeQuery(qry1);
            while (rs.next()) {
                Log.d("DBtest",tblno.toString());
                tblno++;
                tblname = c1 + "_" + tblno.toString();
                qry1 = "SHOW TABLES LIKE '" + tblname +"'";
                rs = stmt.executeQuery(qry1);
            }

            //テーブル作成yyyy-MM-dd_n
            String qry2 = "CREATE TABLE `test`.`" + tblname + "` ( `id` INT NOT NULL , `data` INT NOT NULL ,`name` VARCHAR(30) NOT NULL ,  `Time` TIME NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB";
            stmt.executeUpdate(qry2);

            Integer id = 0;
            PreparedStatement ps;
            Date time;
            SimpleDateFormat d2;
            String stime;
            Integer data;


            //データ保存
            while (true){
                time = new Date();
                d2 = new SimpleDateFormat("HH:mm:ss");
                stime = d2.format(time);
                id++;
                Random ran = new Random();
                data = ran.nextInt(100);
                String SQL2 = "INSERT INTO `" + tblname +"` values (?,?,?,?)";

                ps = conn.prepareStatement(SQL2);
                ps.setInt(1, id);
                ps.setInt(2, data);
                ps.setString(3, "ANDROID");
                ps.setString(4, stime);

                ps.executeUpdate();
                Thread.sleep(1000);
            }



        } catch (Exception e) {
            text1=e.getMessage();

        }
        return text1;
    }

    protected void onPostExecute(String result){
        //TextView tv = (TextView)activity.findViewById(R.id.text01);
        Log.d("DBtest", result);
        //tv.setText(result);
    }
}
