package com.example.graphicman;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    private GameThread thread;
    private boolean running = true;
    private SensorManager sensorManager;
    private LifeBars lifebars;
    private int screenHeight;
    private int screenWidth;
    Drawable image;


    public boolean isRunning() {
        return true;
    }

    public LifeBars getLifebars() {
        return lifebars;
    }

    public GameView(Context context, SensorManager sensorManager) {
        super(context);

        image = context.getDrawable(R.drawable.gragro);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity )context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        lifebars = new LifeBars(context,100,100,100, screenHeight, screenWidth);
        getHolder().addCallback(this);
        thread = new GameThread(context, getHolder(), this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        CanvasWrapper c = new CanvasWrapper(1080, 2154);
        c.setCanvas(canvas);

        if (canvas != null) {
            canvas.drawColor(Color.parseColor("#F5F5F5"));
            //lifebars.draw(canvas);
            //image.setBounds(200, 200, 500, 500);
            c.drawImage(image, 200, 200, 500, 900);
        }
    }
    public void update() {

    }

}
