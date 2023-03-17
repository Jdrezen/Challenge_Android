package com.example.graphicman;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    private GameThread thread;
    private boolean running = true;
    private SensorManager sensorManager;
    private LifeBars lifebars;
    private int screenHeight;
    private int screenWidth;
    Drawable image;
    private ArrayList<Jeu> historiqueJeux = new ArrayList<Jeu>();
    private ArrayList<Jeu> jeuxPossibles = new ArrayList<Jeu>();
    private int iJeuxEnCour = 0;
    private TouchButton touchButton;


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
        Log.d("tag", "screen" + screenWidth + ", " + screenHeight);

        // lifebars = new LifeBars(context,100,100,100, screenHeight, screenWidth);
        getHolder().addCallback(this);
        thread = new GameThread(context, getHolder(), this);
        initJeux(context, sensorManager);
    }

    public void initJeux(Context context, SensorManager sensorManager){
//        jeuxPossibles.add(new TouchButton(context, this, screenWidth, screenHeight));
        jeuxPossibles.add(new Equilibriste( sensorManager,this, screenWidth, screenHeight));




        nextJeu();
    }

    public int getRandomInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min) + min;
    }

    // appelé par un jeu quand c'est gagné
    public void nextJeu(){
        if(historiqueJeux.size()==0){
            historiqueJeux.add(jeuxPossibles.get(getRandomInt(0, jeuxPossibles.size())));
        }else if(iJeuxEnCour==historiqueJeux.size()-1){
            historiqueJeux.add(jeuxPossibles.get(getRandomInt(0, jeuxPossibles.size())));
            iJeuxEnCour++;
        }else{
            iJeuxEnCour++;
        }
    }

    // appelé par un jeu quand c'est perdu
    public void perdu(){
        iJeuxEnCour = 0;

        //vers activite fin/restart
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
        Log.d("draw","draw");
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.parseColor("#F5F5F5"));
            historiqueJeux.get(iJeuxEnCour).draw(canvas);
        }
    }
    public void update() {
        historiqueJeux.get(iJeuxEnCour).update();
    }

}
