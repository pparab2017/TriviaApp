package com.example.triviaapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetAllQuestions.GetRequiredMainActivity, View.OnClickListener {

    ArrayList<Question> questionsList ;
    ImageView imgTrivia;
    Button btnStart,btnExit;
    TextView viewReady;
    final static String  FINISH_ACTIVITY = "FINISH_ACTIVITY";
    final static String LIST_START = "LIST_START";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgTrivia = (ImageView)findViewById(R.id.imageViewTrivia);
        btnStart = (Button)findViewById(R.id.buttonStartTrivia);
        btnExit = (Button)findViewById(R.id.buttonExit);
        viewReady = (TextView)findViewById(R.id.textViewReady);

        btnExit.setOnClickListener(this);
        btnStart.setOnClickListener(this);

        new GetAllQuestions(this).execute("http://dev.theappsdr.com/apis/trivia_json/index.php");
    }

    @Override
    public void setUpQuestions(ArrayList<Question> data) {
        questionsList = data;

        imgTrivia.setImageResource(R.drawable.trivia);
        viewReady.setText("Trivia Ready");
        btnStart.setEnabled(true);
    }



    @Override
    public Context getContext() {
        return MainActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.buttonStartTrivia:
                Intent startQuiz = new Intent(MainActivity.this, Trivia.class);

                startQuiz.putExtra(LIST_START,questionsList);
                startActivity(startQuiz);
                break;
            case R.id.buttonExit:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                break;
        }
    }
}
