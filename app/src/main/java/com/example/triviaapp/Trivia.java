package com.example.triviaapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Trivia extends AppCompatActivity implements View.OnClickListener, GetImage.Image {
    ArrayList<Question> questionsList ;
    TextView qNumber,qText;
    RadioGroup radioGroupChoice;
    Button btnQuit,btnNext;
    int currIndex;
    ImageView imgRef;
    CountDownTimer timer = null;
    long currTimer = 0;
    long defaultTimer = 60000 * 2;
    final static String  FINISH_ACTIVITY = "FINISH_ACTIVITY";


    boolean isPause = false;

    final static String LIST_STATS = "LIST_STATS";
    final static int  LIST_GAME_CODE = 100;
    private void setTimer()
    {

        timer = new CountDownTimer(defaultTimer, 1000) {

            public void onTick(long millisUntilFinished) {
                TextView time = (TextView)findViewById(R.id.textViewTimer);


                    if (millisUntilFinished / 1000 < 30) {
                        time.setBackgroundResource(R.drawable.redrounded);
                    } else if (millisUntilFinished / 1000 < 60) {
                        time.setBackgroundResource(R.drawable.yellowrounded);
                    } else {
                        time.setBackgroundResource(R.drawable.greenrounded);
                    }

                    time.setText("Time Left: "
                            + millisUntilFinished / 1000 + " Seconds");
                    currTimer = millisUntilFinished ;
                }



            public void onFinish() {
                cancel();

                Log.d("demo",String.valueOf( currTimer));
                Intent quizStats = new Intent(Trivia.this,TriviaStats.class);

                quizStats.putExtra(LIST_STATS,questionsList);
                startActivity(quizStats);

            }
        }.start();


    }



    private void clearAnswers()
    {
        for (Question q:questionsList
             ) {
            q.setUserAnswerIndex(0);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
        qNumber = (TextView)findViewById(R.id.textViewNumber);
        qText = (TextView)findViewById(R.id.textViewQuestion);
        radioGroupChoice = (RadioGroup)findViewById(R.id.radioGrpChoice);
        btnQuit = (Button)findViewById(R.id.buttonQuit);
        btnNext = (Button)findViewById(R.id.buttonNext);
        imgRef = (ImageView)findViewById(R.id.imageViewRef);
        btnNext.setOnClickListener(this);
        btnQuit.setOnClickListener(this);

        //qNumber.setBackgroundResource(R.drawable.greenrounded);

        if (getIntent().getExtras() != null) {
            questionsList = getIntent().getExtras().getParcelableArrayList(MainActivity.LIST_START);

            setTimer();
            currIndex = 0;
            clearAnswers();
            setQuestion(currIndex);



        }
    }


    private void setQuestion(int index)

    {

        radioGroupChoice.removeAllViewsInLayout();
        radioGroupChoice.clearCheck();

        if(index < questionsList.size()) {

            imgRef.setImageBitmap(null);
            imgRef.getLayoutParams().height = 0;
            qNumber.setText("Q" + (questionsList.get(index).id + 1));
            qText.setText(questionsList.get(index).questionText);
            for (int i = 0; i < questionsList.get(index).choices.length; i++) {
                RadioButton toAdd = new RadioButton(Trivia.this);
                toAdd.setId(i + 1);
                toAdd.setText(questionsList.get(index).choices[i]);
                radioGroupChoice.addView(toAdd);
            }
            if(questionsList.get(index).imgUrl!= null)
            {
                new GetImage(Trivia.this).execute(questionsList.get(index).imgUrl);

            }
        }else
        {
            timer.cancel();
            Intent quizStats = new Intent(Trivia.this,TriviaStats.class);

            quizStats.putExtra(LIST_STATS,questionsList);
            startActivity(quizStats);
        }
    }
    @Override
    public void onClick(View v) {
switch (v.getId())
{
    case R.id.buttonNext:

        questionsList.get(currIndex).userAnswerIndex = radioGroupChoice.getCheckedRadioButtonId();
        currIndex++;
        setQuestion(currIndex);

        break;
    case R.id.buttonQuit:
        Intent toSend = new Intent(Trivia.this,MainActivity.class);
        startActivity(toSend);
        timer.cancel();
        break;
}
    }

    @Override
    public void SetImage(Bitmap bitmap) {
        isPause = false;
        imgRef.getLayoutParams().height = 400;
        imgRef.setImageBitmap(bitmap);

    }

    @Override
    public Context getContext() {
        return Trivia.this;
    }
}
