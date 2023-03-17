package com.example.graphicman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

public class PetThedog extends Jeu {
    private GameView gameView;
    private CanvasWrapper canvasWrapper;
    private Chrono chrono;

    private Drawable sadDog;
    private Drawable hapyDog1;
    private Drawable hapyDog2;

    private int pet = 0;
    private int frame = 0;

    private boolean isPeted = false;
    private boolean hasBeenPeted = false;

    public PetThedog(Context context, GameView gameView, int width, int height){
        this.gameView = gameView;
        this.canvasWrapper = new CanvasWrapper(width, height);
        this.chrono = new Chrono();

        this.sadDog= context.getDrawable(R.drawable.saddog);
        this.hapyDog1 = context.getDrawable(R.drawable.hapydog1);
        this.hapyDog2 = context.getDrawable(R.drawable.happidog2);
    }

    public void drag(MotionEvent motionEvent) {
        pet++;
        if(pet>2){
            isPeted = true;
        }
        if (pet>50){
            isPeted = false;
            hasBeenPeted = false;
            pet = 0;
            gameView.nextJeu();
        }
    }

    public void drawSadDog(int x, int y){
        canvasWrapper.drawImage(sadDog,x,y,x+800,y+800);
    }

    public void boucleHappyDog(int x, int y){
        switch (frame) {
            case 0:
                canvasWrapper.drawImage(hapyDog1, x, y, x + 800, y + 800);
                frame++;
                break;
            case 1:
                canvasWrapper.drawImage(hapyDog2, x, y, x + 800, y + 800);
                frame = 0;
                break;
            case 2:
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvasWrapper.setCanvas(canvas);
        Paint p = new Paint();
        p.setColor(Color.RED);
        canvasWrapper.drawText("Caresse le chien !", 150, 100, p, 70);
        p.setColor(Color.BLACK);
        canvasWrapper.drawText(chrono.displayTime(), 300, 200, p, 70);
        int x = 0;
        int y = 0;
        if(isPeted){
            boucleHappyDog(x, y);
        }else{
            drawSadDog(x,y);
        }

    }

    @Override
    public void update() {
        if (chrono.isFinit() && !hasBeenPeted){
            gameView.perdu("il fallait caresser plus le chien");
        }else if(chrono.isFinit() && hasBeenPeted){
           hasBeenPeted = false;
            isPeted = false;
            pet = 0;
           gameView.nextJeu();
        }
    }

    @Override
    public void start() {
        isPeted = false;
        hasBeenPeted = false;
        pet = 0;
        chrono.start(5000);
        frame = 0;
    }
}
