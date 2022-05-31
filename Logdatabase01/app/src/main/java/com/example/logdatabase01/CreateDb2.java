package com.example.logdatabase01;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
public class CreateDb2 extends AsyncTask<Void, Void, String>{
    Activity activity = null;
    public CreateDb2(Activity act) {
        activity = act;
    }


    protected String doInBackground(Void... params){
        String text1="";
        String tblname=null;
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn= DriverManager.getConnection("jdbc:mysql://172.16.129.53/test?characterEncoding=ascii","all","sinno0203");

            Statement stmt = conn.createStatement();
            ResultSet rs;

            //テーブルの有無確認
            Date d = new Date();
            SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd");
            String c1 = d1.format(d);

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
            String qry2 = "CREATE TABLE `test`.`" + tblname + "` ( `id` SERIAL NOT NULL , `ID1` VARCHAR(40) NOT NULL ,`ID2` VARCHAR(40) NOT NULL ,`ID3` VARCHAR(40) NOT NULL ,`GLV` VARCHAR(40) NOT NULL ,`accel` VARCHAR(40) NOT NULL , `Time` TIME NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB";
            stmt.executeUpdate(qry2);

            conn.close();

        } catch (Exception e) {
            text1=e.getMessage();

        }
        return tblname;
    }

    protected void onPostExecute(String result){
        Log.d("DBtest", result);
        //TextView text= (TextView)activity.findViewById(R.id.text2);
        //text.setText(result);
        Dbname db = (Dbname)activity.getApplication();
        db.setDbname(result);
    }


}
