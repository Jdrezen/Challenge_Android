package com.example.graphicman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class GameBoy extends Jeu implements SensorEventListener {
    private CanvasWrapper canvasWrapper;
    private GameView gameView;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private int height;
    private int width;
    private int lightValue;
    private boolean hide = false;
    private boolean mama = false;
    private int frame = 0;
    private Drawable boiAwake;
    private Drawable boiSleep;
    private Drawable mama1;
    private Drawable mama2;
    private Chrono chrono;


    GameBoy(Context context, SensorManager sensorManager, GameView gameView, int width, int height) {
        this.sensorManager = sensorManager;
        this.sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);        this.gameView = gameView;
        this.lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        this.canvasWrapper = new CanvasWrapper(width, height);
        this.height = height;
        this.width = width;
        this.boiAwake = context.getDrawable(R.drawable.boiawake);
        this.boiSleep = context.getDrawable(R.drawable.boisleep);
        this.mama1 = context.getDrawable(R.drawable.openingdoor);
        this.mama2 = context.getDrawable(R.drawable.maman);
        this.chrono = new Chrono();
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        float lightLevel = event.values[0];
        lightValue = lightLevel > 500 ? 100 : (int) lightLevel / 5;
        if(lightValue<50) {
            hide = true;
        }else {
            hide = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    public void drawBoiSleep(int x, int y){
        y+=680;
        canvasWrapper.drawImage(boiSleep,x,y,x+800,y+800);
    }

    public void drawBoiPlay(int x, int y){
        y+=680;
        canvasWrapper.drawImage(boiAwake,x,y,x+800,y+800);
    }


    public void drawMama(int x, int y){
        switch (frame) {
            case 0:
                canvasWrapper.drawImage(mama1,x,y,x+800,y+800);
                frame++;
                break;
            case 1:
                canvasWrapper.drawImage(mama1,x,y,x+800,y+800);
                frame++;
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                canvasWrapper.drawImage(mama2,x,y,x+800,y+800);
                frame++;
                break;
        }
    }


    @Override
    public void draw(Canvas canvas) {
        canvasWrapper.setCanvas(canvas);
        int x = 0;
        int y = 0;
        canvas.drawColor(Color.BLACK);
        Paint p = new Paint();
        p.setColor(Color.RED);
        canvasWrapper.drawText("Cache la GameBoy !", 100, 100, p, 70);
        p.setColor(Color.WHITE);
        canvasWrapper.drawText(chrono.displayTime(), 300, 200, p, 70);
        if(hide){
            drawBoiSleep(x,y);
        }else{
            drawBoiPlay(x,y);
        }
        if(mama){
            drawMama(x,y+300);
        }
    }

    @Override
    public void update() {
        if(chrono.getMilliTime() <= 1000){
            mama = true;
        }else
        if(chrono.getMilliTime() <= 4000){
            mama = false;
        }else if(chrono.getMilliTime() <= 5000){
            mama = true;
        }
        if(mama&& frame == 4 && !hide){
            gameView.perdu("Il fallait cacher la luminosité de l'appareil");
        }
        if (chrono.isFinit()){
            mama = false;
            gameView.nextJeu();
        }
    }

    @Override
    public void start() {
        chrono.start(10000);
        frame = 0;
    }
}
