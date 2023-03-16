package com.example.graphicman;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.util.Log;

import androidx.core.content.res.ResourcesCompat;

import java.util.Random;

public class LifeBars {
    private int food;
    private int energy;
    private int health;
    private int height;
    private int width;
    private float ratio = 10;
    private CanvasWrapper canvasWrapper;
    private Handler timeHandler;


    private int barLength = 0;
    private int barWidth = 0;

    public LifeBars(Context context, int food, int energy, int health, int height, int width) {
        this.food = food;
        this.energy = energy;
        this.health = health;
        this.height = height;
        this.width = width;
        this.barLength = 920;
        this.barWidth = 45;
        this.ratio = ((float)(barLength-5)) /100;
        this.canvasWrapper = new CanvasWrapper(width,height);
        this.timeHandler = new Handler();
        this.timeHandler.postDelayed(reduceLifeBars,1000);
    }

    private Runnable reduceLifeBars = new Runnable() {
        public void run() {
            subEnegy(1);
            subHealth(1);
            subFood(1);
            timeHandler.postDelayed(this, 1000);
        }
    };

    public String getDeathCause(){
        if(food == 0){
            return "FOOD";
        }
        else if(energy == 0){
            return "ENERGY";
        }
        else{
            return "HEALTH";
        }
    }

    public void addFood(int value){
        if((this.food + value) >= 100){
            this.food = 100;
            subHealth(5);
        }else{
            this.food +=value;
        }
    }
    public void subFood(int value){
        if((this.food - value) <= 0){
            this.food = 0;
        }else{
            this.food -=value;
        }
    }
    public void addEnegy(int value){
        if((this.energy + value) >100){
            this.energy = 100;
            subHealth(1);
        }else{
            this.energy +=value;
        }
    }
    public void subEnegy(int value){
        if((this.energy - value) <= 0){
            this.energy = 0;
        }else{
            this.energy -=value;
        }
    }
    public void addHealth(int value){
        if((this.health + value) >100){
            this.health = 100;
        }else{
            this.health +=value;
        }
    }
    public void subHealth(int value){
        if((this.health - value) <= 0){
            this.health = 0;
        }else{
            this.health -=value;
        }
    }

    public boolean isDead(){
        return (this.health == 0 || this.energy == 0 || this.food == 0);
    }

    public void drawPoulew(CanvasWrapper canvasWrapper){
        int x = 20;
        int y = 325;

        Paint brownPaint = new Paint();
        brownPaint.setColor(Color.rgb(153,76,0));
        Paint wightPaint = new Paint();
        wightPaint.setColor(Color.rgb(255,255,255));
        //os
        canvasWrapper.drawRect(x+18, y, x+32, y+80,  wightPaint);
        y+=15;
        //meet
        canvasWrapper.drawRect(x, y, x+50, y+50, brownPaint);
    }
    public void drawEnergy(CanvasWrapper canvasWrapper){
        int x = 20;
        int y = 250;

        Paint bluePaint = new Paint();
        bluePaint.setColor(Color.rgb(0,128,255));
        canvasWrapper.drawRect(x+15, y-5, x+30, y, bluePaint);

        canvasWrapper.drawRect(x, y, x+50, y+15, bluePaint);
        y+=20;
        canvasWrapper.drawRect(x, y, x+50, y+15, bluePaint);
        y+=20;
        canvasWrapper.drawRect(x, y, x+50, y+15, bluePaint);
    }

    public void drawCross(CanvasWrapper canvasWrapper) {
        int x = 20;
        int y = 170;

        Paint redPaint = new Paint();
        redPaint.setColor(Color.rgb(204, 0, 0));

        canvasWrapper.drawRect(x+15, y, x + 35, y+50, redPaint);
        y+=16;
        canvasWrapper.drawRect(x, y, x + 50, y + 20, redPaint);
    }


    public void drawBars(CanvasWrapper canvasWrapper){
        int x = 100;
        int y = 200;

        Paint backPaint = new Paint();
        backPaint.setColor(Color.rgb(128,128,128));

        Paint redPaint = new Paint();
        redPaint.setColor(Color.rgb(250, 0, 0));

        Paint greenPaint = new Paint();
        greenPaint.setColor(Color.rgb(102, 204, 0));

        Paint bluePaint = new Paint();
        bluePaint.setColor(Color.rgb(0, 102, 204));

        canvasWrapper.drawRect(x, y, x+barLength, y+barWidth, backPaint);
        canvasWrapper.drawRect(x+5, y+5, x+(int)(health*ratio), y+barWidth-5, redPaint);
        y = y+70;
        canvasWrapper.drawRect(x, y, x+barLength, y+barWidth, backPaint);
        canvasWrapper.drawRect(x+5, y+5,  x+(int)(energy*ratio), y+barWidth-5, bluePaint);
        y = y+70;
        canvasWrapper.drawRect(x, y, x+barLength, y+barWidth, backPaint);
        canvasWrapper.drawRect(x+5, y+5,  x+(int)(food*ratio), y+barWidth-5, greenPaint);
    }

    public void draw(Canvas canvas) {
        Paint backGroundPaint = new Paint();
        backGroundPaint.setColor(Color.rgb(192,192,192));

        canvasWrapper.setCanvas(canvas);
        canvasWrapper.drawRect(0, 0, 1080, 430, backGroundPaint);

        drawPoulew(canvasWrapper);
        drawEnergy(canvasWrapper);
        drawBars(canvasWrapper);
        drawCross(canvasWrapper);
    }



}
