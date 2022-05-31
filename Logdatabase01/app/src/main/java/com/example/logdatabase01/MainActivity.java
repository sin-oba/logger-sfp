package com.example.logdatabase01;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import java.sql.Connection;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public static int read = 0;
    public static char[] mojibuf = new char[256];
    public static int bufindex = 0;
    public String tblname;
    public Connection con;

    public static int id = 0;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        TextView text= (TextView)findViewById(R.id.text1);

        TextView text03 = (TextView)findViewById(R.id.text3);
        Button bt = (Button)findViewById(R.id.button);
        Button send = (Button)findViewById(R.id.send);
        text.setText("接続中");

        TextView text02 = (TextView)findViewById(R.id.text2);

        //スピナーカスタマイズ

        Spinner spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.custom_spinner,
                getResources().getStringArray(R.array.list)
        );
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);



        //ボタン　→ Speedmetorヘ移動

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // クリック時の処理
                Intent intent = new Intent(getApplication(), Speedmetor.class);
                startActivity(intent);
            }
        });

        //ボタン スピナーの文字を送信
        /*
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // クリック時の処理
                String sd = spinner.getSelectedItem().toString();
                try {
                    if(usb != null) {

                        usb.write(sd.getBytes("UTF-8"),1);
                    }
                    text02.setText("send「"+ sd +"」");
                    Log.v("write","send");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        */

    }


}