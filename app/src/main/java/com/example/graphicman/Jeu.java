package com.example.graphicman;

import android.graphics.Canvas;

public abstract class Jeu {
    private int time = 5;
    public abstract void draw(Canvas canvas);
    public abstract void update();
    public abstract void start();

}
