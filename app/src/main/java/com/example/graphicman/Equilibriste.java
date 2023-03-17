package com.example.graphicman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class Equilibriste extends Jeu implements SensorEventListener {
    private CanvasWrapper canvasWrapper;
    private GameView gameView;
    private SensorManager sensorManager;
    private int height;
    private int width;
    private int dirrection = 0;
    private boolean perdu = false;
    private int fallFrame = 0;
    private String fallDirr = "right";
    private Chrono chrono;


    Equilibriste(SensorManager sensorManager, GameView gameView, int width, int height) {
        this.sensorManager = sensorManager;
        this.sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_NORMAL);
        this.gameView = gameView;
        this.height = height;
        this.width = width;
        this.canvasWrapper = new CanvasWrapper(width, height);
        this.chrono = new Chrono();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int x = (int)(sensorEvent.values[0] *(-2));
        int y = (int)(sensorEvent.values[0] *(-2));
        Log.d("dirrr","  "+x+"   "+y);
        if(x>1 || y>1){
            perdu = true;
            fallDirr = "right";
        }
        if(x<-1 || y<-1){
            perdu = true;
            fallDirr = "left";
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    public void drawEquilibriste(int x,int y){
        Paint blackPaint = new Paint();
        blackPaint.setColor(Color.rgb(0, 0, 0));
        canvasWrapper.drawCircle(x,y,50,blackPaint);
    }

    public void drawPoto(int x,int y){
        Paint blackPaint = new Paint();
        blackPaint.setColor(Color.rgb(0, 0, 0));
        canvasWrapper.drawRect(x,y,x+50,y+500, blackPaint);
    }

    public void drawFall(int x,int y){
        if(fallDirr == "right"){
            switch (fallFrame) {
                case 0:
                    drawEquilibriste(x+50, y+15);
                    fallFrame++;
                    break;
                case 1:
                    drawEquilibriste(x+70, y+100);
                    fallFrame++;
                    break;
                case 2:
                    drawEquilibriste(x+75, y+500);
                    fallFrame++;
                    break;
            }

        }else{
            switch (fallFrame) {
                case 0:
                    drawEquilibriste(x-50, y+15);
                    fallFrame++;
                    break;
                case 1:
                    drawEquilibriste(x-70, y+100);
                    fallFrame++;
                    break;
                case 2:
                    drawEquilibriste(x-75, y+500);
                    fallFrame++;
                    break;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvasWrapper.setCanvas(canvas);
        int x =  width/2;
        int y = 500;

        Paint p = new Paint();
        canvasWrapper.drawText("" + chrono.getTime(), 300, 100, p, 70);

        drawPoto(x, y);

        if(!perdu){
            drawEquilibriste(x, y);
        }else{
            drawFall(x,y);
        }
    }

    @Override
    public void update() {
        if(fallFrame>=3){
            chrono.stop();
            fallFrame = 0;
            gameView.perdu();
        }
        if(chrono.getTime()>=5){
            gameView.nextJeu();
        }
    }

    @Override
    public void start() {
        chrono.start();
        fallFrame= 0;
        perdu = false;
    }
}
