package com.example.triviaapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pushparajparab on 9/22/16.
 */
public class Question implements Parcelable {



    public  Question(){}

    int id;
    int correctAnswerIndex;
    int userAnswerIndex;
    String questionText,imgUrl, choices[];

    public Question(int id, int correctAnswerIndex, int userAnswerIndex, String questionText, String imgUrl, String[] choices) {
        this.id = id;
        this.correctAnswerIndex = correctAnswerIndex;
        this.userAnswerIndex = userAnswerIndex;
        this.questionText = questionText;
        this.imgUrl = imgUrl;
        this.choices = choices;
    }



    public static final Parcelable.Creator<Question> CREATOR
            = new Parcelable.Creator<Question>() {
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    private Question(Parcel in )
    {
        this.id = in.readInt();
        this.questionText = in.readString();
        this.imgUrl = in.readString();
        this.choices = in.createStringArray();
        this.correctAnswerIndex = in.readInt();
        this.userAnswerIndex = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(questionText);
        dest.writeString(imgUrl);
        dest.writeStringArray(choices);
        dest.writeInt(correctAnswerIndex);
        dest.writeInt(userAnswerIndex);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public int getUserAnswerIndex() {
        return userAnswerIndex;
    }

    public void setUserAnswerIndex(int userAnswerIndex) {
        this.userAnswerIndex = userAnswerIndex;
    }

    public java.lang.String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(java.lang.String questionText) {
        this.questionText = questionText;
    }

    public java.lang.String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(java.lang.String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
