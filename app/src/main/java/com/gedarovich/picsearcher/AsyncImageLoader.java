package com.gedarovich.picsearcher;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class AsyncImageLoader extends AsyncTask<String, Void, Bitmap> {
    ImageView img;

    public AsyncImageLoader(ImageView img) {
        this.img = img;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        }
        catch (Throwable e) {
            Log.e("Error", e.getMessage());
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        img.setImageBitmap(result);
    }
}
