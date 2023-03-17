package com.example.graphicman;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread thread;
    private boolean running = true;
    private SensorManager sensorManager;
    private int screenHeight;
    private int screenWidth;
    Drawable image;
    private ArrayList<Jeu> historiqueJeux = new ArrayList<Jeu>();
    private ArrayList<Jeu> jeuxPossibles = new ArrayList<Jeu>();
    private int iJeuxEnCour = 0;
    private TouchButton touchButton;
    private TouchButton donttouchButton;
    private String AUDIOPATH;
    private Context context;
    private int score;
    private boolean isRunning = false;


    public boolean isRunning() {
        return isRunning;
    }

    public GameView(Context context, SensorManager sensorManager) {
        super(context);

        AUDIOPATH = context.getCacheDir().getAbsolutePath() + "/audio.3gp";

        this.context = context;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        getHolder().addCallback(this);
        thread = new GameThread(context, getHolder(), this);
        initJeux(context, sensorManager);
        isRunning = true;
    }

    public void initJeux(Context context, SensorManager sensorManager) {
        touchButton = new TouchButton(context, this, screenWidth, screenHeight, true);
        donttouchButton = new TouchButton(context, this, screenWidth, screenHeight, false);
        jeuxPossibles.add(touchButton);
        jeuxPossibles.add(donttouchButton);

        Equilibriste equilibriste = new Equilibriste(context, sensorManager, this, screenWidth, screenHeight, true);
        Equilibriste antiEquilibre = new Equilibriste(context, sensorManager, this, screenWidth, screenHeight, false);
        jeuxPossibles.add(equilibriste);
        jeuxPossibles.add(antiEquilibre);
        jeuxPossibles.add(new Bougies(this, AUDIOPATH));
        jeuxPossibles.add(new GameBoy(context, sensorManager,this, screenWidth, screenHeight));

        nextJeu();
    }

    public int getRandomInt(int min, int max, int v) {
        Random rand = new Random();
        int res = rand.nextInt(max - min) + min;
        while (res == v) {
            res = rand.nextInt(max - min) + min;
        }

        return res;
    }

    // appelé par un jeu quand c'est gagné
    public void nextJeu() {
        score++;
        if (historiqueJeux.size() == 0) {
            historiqueJeux.add(jeuxPossibles.get(getRandomInt(0, jeuxPossibles.size(), -1)));
        } else if (iJeuxEnCour == historiqueJeux.size() - 1) {
            historiqueJeux.add(jeuxPossibles.get(getRandomInt(0, jeuxPossibles.size(), jeuxPossibles.indexOf(historiqueJeux.get(iJeuxEnCour)))));
            iJeuxEnCour++;
        } else {
            iJeuxEnCour++;
        }
        historiqueJeux.get(iJeuxEnCour).start();
    }

    // appelé par un jeu quand c'est perdu
    public void perdu(String message) {
        isRunning = false;
        Intent intent = new Intent(context, ScoreActivity.class);
        intent.putExtra("mort", message);
        intent.putExtra("score", score);
        context.startActivity(intent);
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
        if (canvas != null) {
            canvas.drawColor(Color.parseColor("#F5F5F5"));
            historiqueJeux.get(iJeuxEnCour).draw(canvas);
        }
    }

    public void update() {
        historiqueJeux.get(iJeuxEnCour).update();
    }

    public void onTouch(MotionEvent motionEvent) {
        touchButton.buttonTouch(motionEvent);
        donttouchButton.buttonTouch(motionEvent);
    }

    public void onDrag(MotionEvent motionEvent) {
    }
}
