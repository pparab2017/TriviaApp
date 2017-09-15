package com.example.triviaapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by pushparajparab on 9/22/16.
 */




public class QuestionUtil  {

  private static String[] getChoice(JSONArray choiceString) throws JSONException

  {
      String[] toReturn = new String[choiceString.length()];
      for(int i =0 ;i<choiceString.length();i++ )
      {
          toReturn[i] =  choiceString.getString(i);
      }
        return  toReturn;
  }


    static ArrayList<Question> parsePerson(String string) throws JSONException {
        ArrayList<Question> toReturn = new ArrayList<Question>();
        JSONObject root = new JSONObject(string);
        JSONArray array = root.getJSONArray("questions");

        for(int i=0;i< array.length();i++)
        {
            Question toAdd = new Question();
            toAdd.id = array.getJSONObject(i).getInt("id");
            toAdd.questionText = array.getJSONObject(i).getString("text");
            if(array.getJSONObject(i).has("image"))
            toAdd.imgUrl = array.getJSONObject(i).getString("image");
            JSONObject stringChoices = new JSONObject(array.getJSONObject(i).getString("choices"));
            JSONArray choiceArray = stringChoices.getJSONArray("choice");
            toAdd.correctAnswerIndex = stringChoices.getInt("answer");



            toAdd.choices = getChoice(choiceArray);



            toReturn.add(toAdd);
        }

        return  toReturn;

    }
}
