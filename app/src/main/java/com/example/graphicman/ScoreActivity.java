package com.example.graphicman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    private int score;
    private int highScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        restartHighScore();

        setContentView(R.layout.activity_score);

        score = getIntent().getIntExtra("score", 0);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        highScore = sharedPref.getInt("highScore", 0);

        TextView highScoreView = findViewById(R.id.h);
        highScoreView.setText(highScore + "");

        TextView scoreView = findViewById(R.id.score);
        scoreView.setText(score + "");

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("highScore", Math.max(score, highScore));
        editor.apply();
    }

    public void recommencerPartie(View view){
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }

    public void nouvellePartie(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        this.startActivity(intent);
    }

    public void restartHighScore(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("highScore",0);
        editor.apply();
    }
}