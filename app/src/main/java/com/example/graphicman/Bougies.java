package com.example.graphicman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.MediaRecorder;
import android.os.Handler;

import java.io.IOException;

public class Bougies extends Jeu{

    private MediaRecorder recorder = new MediaRecorder();
    private GameView gameView;
    private float valeur;
    private String audioPath;
    private Drawable img_candle;
    private Drawable img_candle2;
    private Drawable img_candle_off;
    private boolean on = true;
    private Handler h = new Handler();
    private int frame = 0;
    private Chrono chrono = new Chrono();
    private boolean anti = false;

    public Bougies(GameView gameView, String audioPath, boolean anti){
        this.gameView = gameView;
        this.audioPath = audioPath;
        this.anti = anti;
        this.img_candle = gameView.getContext().getDrawable(R.drawable.candle_fire);
        this.img_candle2 = gameView.getContext().getDrawable(R.drawable.candle_fire2);
        this.img_candle_off = gameView.getContext().getDrawable(R.drawable.candle_off);
    }

    @Override
    public void draw(Canvas canvas) {
        CanvasWrapper c = new CanvasWrapper(800,1276);
        c.setCanvas(canvas);
        Paint p = new Paint();
        p.setColor(Color.RED);

        if(anti){
            c.drawText("Ne soufflez pas !", 250, 100, p, 70);
        }
        else{
            c.drawText("Soufflez !", 250, 100, p, 70);
        }

        p.setColor(Color.BLACK);
        c.drawText(chrono.displayTime(), 300, 200, p, 70);

        if(on){
            if(frame == 0){
                c.drawImage(img_candle,0,100, 800,1800);
                frame++;
            }
            else{
                c.drawImage(img_candle2,0,100, 800,1800);
                frame--;
            }

        }
        else {
            c.drawImage(img_candle_off,0,100, 800,1800);
        }
    }


    @Override
    public void update() {
        valeur = recorder.getMaxAmplitude();
        if (valeur > 15000 && on){
            on = false;
            h.postDelayed(run, 1000);
        }
        if (chrono.isFinit() && on){
            recorder.stop();
            if (anti) {
                gameView.nextJeu();
            } else {
                gameView.perdu("Il ne fallait pas souffler sur l'écran");
            }
        }
    }

    @Override
    public void start() {
        on = true;
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(audioPath);
        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        recorder.start();
        chrono.start(5000);
    }

    private Runnable run = new Runnable() {
        @Override
        public void run() {
            if (anti) {
                gameView.perdu("Il fallait souffler sur l'écran");
            } else {
                gameView.nextJeu();
            }
        }
    };
}
