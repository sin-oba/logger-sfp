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
public class InsertDBbuff2 extends AsyncTask<Void, Void, String>{
    Activity activity = null;
    Integer id1;
    String d[] = new String[10];
    String tblname ;

    public InsertDBbuff2(Activity act, String data1, String data2, String data3, String data4, String data5, String data6, String data7, String data8, String data9, String data10, String tbl) {
        activity = act;
        d[0] = data1;
        d[1] = data2;
        d[2] = data3;
        d[3] = data4;
        d[4] = data5;
        d[5] = data6;
        d[6] = data7;
        d[7] = data8;
        d[8] = data9;
        d[9] = data10;

        tblname = tbl;
    }



    protected String doInBackground(Void... params){
        String text1="";
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn= DriverManager.getConnection("jdbc:mysql://172.16.129.53/test?characterEncoding=ascii","all","sinno0203");

            Statement stmt = conn.createStatement();
            ResultSet rs;


            PreparedStatement ps;
            Date time;
            SimpleDateFormat dt2;
            String stime;

            String SQL2 = "INSERT INTO `" + tblname+"` (id, ID1, ID2, ID3, GLV, accel, Time)";

            for(int i = 0;i < 10;i++){
                String[] data = d[i].split(",");
                char[] ID1 = data[0].toCharArray();
                char[] ID2 = data[1].toCharArray();
                char[] ID8 = data[2].toCharArray();
                char[] GLV = data[3].toCharArray();
                char[] accel = data[4].toCharArray();
                //データ保存

                time = new Date();
                dt2 = new SimpleDateFormat("HH:mm:ss");
                stime = dt2.format(time);


                if(i == 0){
                    SQL2 = SQL2 + " values (NULL,'" + String.valueOf(ID1) + "','" + String.valueOf(ID2) + "','"
                            + String.valueOf(ID8) + "','" + String.valueOf(GLV) + "','" + String.valueOf(accel)
                            + "','" + stime + "')";
                }else{
                    SQL2 = SQL2 + " ,(NULL,'" + String.valueOf(ID1) + "','" + String.valueOf(ID2) + "','"
                            + String.valueOf(ID8) + "','" + String.valueOf(GLV) + "','" + String.valueOf(accel)
                            + "','" + stime + "')";
                }

            }

            Log.d("debug",SQL2);
            ps = conn.prepareStatement(SQL2);
            ps.executeUpdate();



        } catch (Exception e) {
            text1=e.getMessage();

        }
        return text1;
    }

    protected void onPostExecute(String result){

    }


}
