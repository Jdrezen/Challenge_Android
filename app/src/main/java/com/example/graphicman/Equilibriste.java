package com.example.graphicman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Equilibriste extends Jeu{
    private CanvasWrapper canvasWrapper;

    Equilibriste(){

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

    @Override
    public void draw(Canvas canvas) {
        canvasWrapper.setCanvas(canvas);
        int x = 500;
        int y = 500;

        drawEquilibriste(x, y);
        drawPoto(x, y);
    }

    @Override
    public void update() {

    }

    @Override
    public void start() {

    }
}
