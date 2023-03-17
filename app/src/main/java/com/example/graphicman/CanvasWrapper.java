package com.example.graphicman;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class CanvasWrapper {

    private Canvas canvas;
    private float widthRatio;
    private float heightRatio;
    public CanvasWrapper(int width, int height){
        this.widthRatio = width/800f;
        this.heightRatio = height/1276f; // 1190f
//        this.widthRatio = width/1080f;
//        this.heightRatio = height/2154f;
    }

    public void setCanvas(Canvas canvas){
        this.canvas = canvas;
    }



    public void drawRect(float left, float top, float right, float bottom, Paint paint){
        canvas.drawRect(left*widthRatio,top*heightRatio,right*widthRatio,bottom*heightRatio,paint);
    }

    public void drawLine(float starX, float starY, float stopX, float stopY, Paint paint){
        paint.setStrokeWidth(50*widthRatio);
        canvas.drawLine(starX*widthRatio,starY*heightRatio,stopX*widthRatio,stopY*heightRatio,paint);
    }

    public void drawText(String text, float x, float y, Paint paint, int size){
        paint.setTextSize(size*widthRatio);
        canvas.drawText(text, x*widthRatio, y*heightRatio, paint);
    }

    public Rect getRect(int left, int top, int right, int bottom) {
        return new Rect((int) (left * widthRatio), (int) (top * heightRatio), (int) (right * widthRatio), (int) (bottom * heightRatio));
    }

    public void drawCircle(float x, float y, float raduis, Paint paint) {
        canvas.drawCircle(x * widthRatio, y * heightRatio , raduis * widthRatio, paint);
    }

    public void drawImage(Drawable image, float left, float top, float right, float bottom){
        image.setBounds((int) (left * widthRatio), (int) (top * heightRatio), (int) (right * widthRatio), (int) (bottom * heightRatio));
        image.draw(canvas);
    }
}
