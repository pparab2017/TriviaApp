package com.example.triviaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class TriviaStats extends AppCompatActivity implements View.OnClickListener {
    ArrayList<Question> questionsList ;
    double max,score = 0 ;
    ProgressBar progressBar;
    TextView textScore,textStatus;
    Button btnQuit,btnTryAgain;
    int FINISH = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_stats);
        progressBar = (ProgressBar) findViewById(R.id.progressBarScore);
        textScore = (TextView)findViewById(R.id.textViewScore);
        textStatus = (TextView)findViewById(R.id.textViewStatus);
        btnQuit = (Button)findViewById(R.id.buttonQuit);
        btnTryAgain = (Button)findViewById(R.id.buttonTryAgain);

        btnTryAgain.setOnClickListener(this);

        btnQuit.setOnClickListener(this);

        if (getIntent().getExtras() != null) {
            questionsList = getIntent().getExtras().getParcelableArrayList(Trivia.LIST_STATS);
            max = questionsList.size();
            for(int i=0;i<questionsList.size();i++)
            {
               if(questionsList.get(i).userAnswerIndex == questionsList.get(i).correctAnswerIndex)
               {
                   score ++;
               }
            }
            double percentInFloat = (score/max) * 100;
            int percent = (int) Math.round(percentInFloat);
            progressBar.setProgress(percent);
            textScore.setText(percent +" %");
            if(percent < 100)
            textStatus.setText("Try again and see if you can get all the correct answers!");
            else
                textStatus.setText("Congrats you scored full!!");


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.buttonQuit:

                Intent toSend = new Intent(TriviaStats.this,MainActivity.class);
                startActivity(toSend);

                break;
            case R.id.buttonTryAgain:
                Intent startQuiz = new Intent(TriviaStats.this, Trivia.class);

                startQuiz.putExtra(MainActivity.LIST_START,questionsList);
                startActivity(startQuiz);


                break;
        }
    }
}
