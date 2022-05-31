package com.example.logdatabase01;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import java.io.IOException;
import java.sql.Connection;

public class Speedmetor extends AppCompatActivity {
    public static int read = 0;
    //public static String msgs="";
    public static String msgs[] = new String[10];
    public String tblname;
    public Connection con;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView text02 = (TextView) findViewById(R.id.text2);

        //配列初期化
        for(int i = 0;i < 10;i++){
            msgs[i] = "";
        }

        //スピード表示
        MyView myView = new MyView(this);
        setContentView(myView);
        myView.setNum(0);


        //接続
        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        UsbSerialDriver usb = UsbSerialProber.acquire(manager);

        if (usb != null) {
            //CreateDb task = new CreateDb(this);
            CreateDb2 task = new CreateDb2(this);
            task.execute();

            //text02.setText(text.getText());
            try {
                usb.open();
                //ボーレート115200
                usb.setBaudRate(115200);
                usb.write("r".getBytes("UTF-8"), 1);
                //text.setText("接続完了");
                //start_read_thread(usb,text02,text03);
                start_read_thread(usb, myView, text02);
            } catch (IOException e) {
                e.printStackTrace();
                //text.setText("接続エラー");
            }
        } else {
            //text.setText("usbを認識していません");
        }
    }

    public void start_read_thread(UsbSerialDriver usb,MyView myView,TextView text02){
        new Thread(new Runnable(){
            public void run(){
                try{
                    while(true){


                        byte buf[] = new byte[256];

                        //num:文字数 buf:受け取る配列(byte型) buf.length:タイムアウト(ms)
                        int num = usb.read(buf, buf.length);
                        String str = new String(buf);
                        char[] strchar = str.toCharArray();

                        //メッセージを分けて読み込む
                        if(num > 0) {
                            //Log.d("debug", str);
                            //Log.d("debug",String.valueOf(strchar[num-1]));
                            //Log.d("debug",String.valueOf(num));

                            msgs[read] = msgs[read] + str.substring(0, num);
                            //Log.d("debug", msgs);

                            if (strchar[num-1] == ';') {
                                Log.d("debug",msgs[read]);

                                read++;
                                usb.write("r".getBytes("UTF-8"),1);
                            }
                        }

                        //メッセージがそろった
                        if(read == 10){
                            read = 0;

                            Dbname db = (Dbname)getApplication();
                            if(!db.getDbname().equals("No Data")){
                                InsertDBbuff2 task2 = new InsertDBbuff2(Speedmetor.this,msgs[0],msgs[1],msgs[2],msgs[3],msgs[4],msgs[5],msgs[6],msgs[7],msgs[8],msgs[9],db.getDbname());
                                task2.execute();

                            }
                            for(int i = 0;i < 10;i++){
                                //Log.d("debug",String.valueOf(i));
                                //文字列を','で区切る
                                //String[] data = msgs[i].split(",");
                                msgs[i] = "";

                            }

                            /*
                            if(status[0] == 'i'){
                                //text02.setText(ssr);
                                //モータ回転数
                                String srpm = String.valueOf(ID2[6]) + String.valueOf(ID2[7]) + String.valueOf(ID2[4]) + String.valueOf(ID2[5]);
                                double rpm = (double)Integer.parseInt(srpm,16);
                                rpm = (rpm * 0.5) -16000;
                                //DCV
                                String sDCV = String.valueOf(ID2[2]) + String.valueOf(ID2[3]) + String.valueOf(ID2[0]) + String.valueOf(ID2[1]);
                                double DCV = (double)Integer.parseInt(sDCV,16);
                                DCV = (DCV * 0.01) - 50;

                                //text03.setText("モータ回転数:" + String.valueOf(rpm) + "rpm" + "\nDCV:" + String.valueOf(DCV));

                                myView.setNum((int) rpm);
                                myView.invalidate();
                                myView.setSpeed((int)rpm%10,(int)((rpm/10)%10),(int)rpm/100);

                                Dbname db = (Dbname)getApplication();
                                if(!db.getDbname().equals("No Data")){
                                    InsertDb task2 = new InsertDb(Speedmetor.this,(int)rpm,(int)DCV,32,124,db.getDbname());
                                    task2.execute();

                                }




                            }


                             */

                            //usb.write("r".getBytes("UTF-8"),1);
                        }


                        Thread.sleep(2); //50ms停止
                    }                }
                catch(IOException e){
                    e.printStackTrace();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
