package com.example.graphicman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.text.method.Touch;
import android.view.MotionEvent;

public class TouchButton extends Jeu {

    private GameView gameView;
    private CanvasWrapper canvasWrapper;
    private Drawable img_button;
    private int count;


    public TouchButton(Context context, GameView gameView, int width, int height) {
        this.gameView = gameView;
        this.canvasWrapper = new CanvasWrapper(width, height);
        img_button = context.getDrawable(R.drawable.red_glossy_button);


    }

    public void buttonTouch(MotionEvent motionEvent) {


    }

    public void draw(Canvas canvas) {
        canvasWrapper.setCanvas(canvas);


        Paint p = new Paint();
        p.setColor(Color.BLACK);
        canvasWrapper.drawRect(0,0, 600, 600, p);
        canvasWrapper.drawImage(img_button, 200 + count, 200 + count, 500 + count, 500 + count);

    }

    @Override
    public void update() {
        count = count + 20;

    }

    @Override
    public void start() {

    }
}
