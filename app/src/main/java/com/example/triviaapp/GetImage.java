package com.example.triviaapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by pushparajparab on 9/22/16.
 */
public class GetImage extends AsyncTask<String,Void,Bitmap> {


    Image toShow;
    ProgressDialog progressDialog;
    public GetImage(Image image)
    {
        this.toShow = image;
    }
    @Override
    protected Bitmap doInBackground(String... params) {
        InputStream inputStream = null;
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET"); // connection.setRequestMethod("POST")
            inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(inputStream!= null)
            {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;


    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Context context = toShow.getContext();
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading Image..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        toShow.SetImage(bitmap);
        progressDialog.dismiss();
    }

    interface Image{
        public void SetImage(Bitmap bitmap);
        public Context getContext();

    }
}
