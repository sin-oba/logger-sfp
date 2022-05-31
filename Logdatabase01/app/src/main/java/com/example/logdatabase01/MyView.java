package com.example.logdatabase01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public  class  MyView extends View{
    //書き換える値
    public float max = 90.0f;
    public int redzone = 70;
    public int yellowzone = 40;

    //固定
    int[] id = new int[10];
    Paint paint = new Paint();
    Paint paint2 = new Paint();
    Paint paint3 = new Paint();
    Paint paint4 = new Paint();
    public int n = 0;
    public String dbname = "No Data";
    public int k1 = 0,k2 = 0,k3 = 0;
    public  MyView(Context context){
        super(context);
        id[0] = R.drawable.d0a;
        id[1] = R.drawable.d1a;
        id[2] = R.drawable.d2a;
        id[3] = R.drawable.d3a;
        id[4] = R.drawable.d4a;
        id[5] = R.drawable.d5a;
        id[6] = R.drawable.d6a;
        id[7] = R.drawable.d7a;
        id[8] = R.drawable.d8a;
        id[9] = R.drawable.d9a;
    }

    protected  void onDraw(Canvas canvas) {
        //案1
        paint.setColor(Color.BLACK);
        if(n <= yellowzone){
            canvas.drawColor(Color.rgb(0,255,0));
        }else if(n <= redzone){
            canvas.drawColor(Color.rgb(255,255,0));
        }else {
            canvas.drawColor(Color.rgb(255,0,0));
        }
        //案2
        /*
        canvas.drawColor(Color.WHITE);
        if(n <= yellowzone){
            //paint.setColor(Color.rgb(0, 255, 0));
        }else if(n <= redzone){
            //paint.setColor(Color.rgb(255, 255, 0));
        }else {
            //paint.setColor(Color.rgb(255, 0, 0));
        }
        */

        //背景の円
        paint2.setColor(Color.BLACK);
        paint2.setAntiAlias(true);
        canvas.drawCircle(550, 850, 350.0f, paint2);
        canvas.drawCircle(550, 850, 350.0f, paint2);

        //デジタル表示
        Bitmap bmp1,bmp2,bmp3;
        bmp1 = BitmapFactory.decodeResource(getResources(),id[k1]);
        bmp2 = BitmapFactory.decodeResource(getResources(),id[k2]);
        bmp3 = BitmapFactory.decodeResource(getResources(),id[k3]);
        int w = bmp1.getWidth();
        int h = bmp1.getHeight();
        Rect src = new Rect(0, 0, w, h);
        //中心 x550から150 y850から75
        Rect dst3 = new Rect(400, 620, 700, 770);
        Rect dst2 = new Rect(400, 775, 700, 925);
        Rect dst1 = new Rect(400, 930, 700, 1080);
        canvas.drawBitmap(bmp1, src, dst1, null);
        canvas.drawBitmap(bmp2, src, dst2, null);
        canvas.drawBitmap(bmp3, src, dst3, null);

        //メータ(針)
        RectF ova19 = new RectF(120.0f,420.0f,980.0f,1280.0f);
        paint4.setAntiAlias(true);
        paint4.setColor(Color.GRAY);
        paint4.setStrokeWidth(250);
        paint4.setStyle(Paint.Style.STROKE);
        canvas.drawArc(ova19,220.0f+n*(280.0f/max)-2.5f,5,false,paint4);

        //メータ(ベース)
        RectF ova18 = new RectF(80.0f, 380.0f, 1020.0f, 1320.0f);
        paint3.setAntiAlias(true);
        paint3.setColor(Color.GRAY);
        paint3.setStrokeWidth(60);
        paint3.setStyle(Paint.Style.STROKE);
        canvas.drawArc(ova18,220,280,false,paint3);

        //メータ(メイン)
        RectF oval7 = new RectF(100.0f, 400.0f, 1000.0f, 1300.0f);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(60);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(oval7, 220, n*(280.0f/max), false, paint);
    }

    public void setNum(int num){
        n = num;
    }
    public void setSpeed(int keta1, int keta2, int keta3){
        k1 = keta1;
        k2 = keta2;
        k3 = keta3;
    }
    public void setDbname(String name){
        dbname = name;
    }

    public String getDbname(){
        return dbname;
    }
}
