package com.example.graphicman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;

public class TouchButton extends Jeu {

    private GameView gameView;
    private CanvasWrapper canvasWrapper;
    private Drawable img_button;
    private int xButton;
    private int yButton;
    private int buttonRadius;
    private Chrono chrono;
    private int maxTime;

    public TouchButton(Context context, GameView gameView, int width, int height) {
        this.gameView = gameView;
        this.canvasWrapper = new CanvasWrapper(width, height);
        img_button = context.getDrawable(R.drawable.red_glossy_button);
        this.chrono = new Chrono();
    }

    public void buttonTouch(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int xCenter = xButton + buttonRadius;
        int yCenter = yButton + buttonRadius;
        if (Math.sqrt((x - xCenter) * (x - xCenter) + (y - yCenter) * (y - yCenter)) < buttonRadius* 3 / 4) {
            gameView.nextJeu();
        }
    }

    public void draw(Canvas canvas) {
        canvasWrapper.setCanvas(canvas);
        Paint p = new Paint();
        String display = chrono.displayTime();
        p.setColor(Color.BLUE);
        canvasWrapper.drawText(display, 300, 100, p, 70);
        canvasWrapper.drawImage(img_button, xButton, yButton, xButton + buttonRadius * 2, yButton + buttonRadius * 2);
    }

    @Override
    public void update() {
        if (chrono.isFinit()) {
            gameView.perdu();
        }
    }

    @Override
    public void start() {
        maxTime = 5000;
        double xRand = Math.random();
        double yRand = Math.random();
        xButton = (int) (xRand * (800 - buttonRadius * 2));
        yButton = (int) (yRand * (1276 - buttonRadius * 2));
        buttonRadius = 100;
        chrono.start(maxTime);
    }
}
