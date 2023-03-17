package com.example.graphicman;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

public class Bougies extends Jeu{

    private MediaRecorder recorder = new MediaRecorder();
    private GameView gameView;
    private float valeur;
    private String audioPath;

    public Bougies(GameView gameView, String audioPath){
        this.gameView = gameView;
        this.audioPath = audioPath;
    }

    @Override
    public void draw(Canvas canvas) {
        CanvasWrapper c = new CanvasWrapper(800,1276);
        c.setCanvas(canvas);
        Paint paint = new Paint();
        c.drawText(valeur + "", 200, 500, paint, 100);
    }


    @Override
    public void update() {
        valeur = recorder.getMaxAmplitude();
        if(recorder.getMaxAmplitude() > 5000){
            recorder.stop();
            //gameView.nextJeu();
            gameView.perdu();
        }
    }

    @Override
    public void start() {
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
    }
}