package com.gedarovich.picsearcher;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList<String> urls = new ArrayList<>();

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<String> urls) {
        super(context, layoutResourceId, urls);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.urls = urls;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        // Create view
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.image = convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        // Set placeholders
        holder.image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.default_image));
        // Load images asynchronously in to each view
        AsyncImageLoader asyncLoader = new AsyncImageLoader(holder.image);
        asyncLoader.execute(urls.get(position));
        return convertView;
    }

    static class ViewHolder {
        ImageView image;
    }
}