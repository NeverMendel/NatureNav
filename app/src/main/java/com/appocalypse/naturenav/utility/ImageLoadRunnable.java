package com.appocalypse.naturenav.utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageLoadRunnable implements Runnable {

    private final String url;
    private final ImageView imageView;

    public ImageLoadRunnable(String url, ImageView imageView) {
        this.url = url;
        this.imageView = imageView;
    }

    @Override
    public void run() {
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}