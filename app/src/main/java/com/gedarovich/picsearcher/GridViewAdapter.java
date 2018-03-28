package com.gedarovich.picsearcher;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter<String> {
    private Context context;
    private int layoutResourceId;
    private ArrayList<String> urls = new ArrayList<>();

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<String> urls) {
        super(context, layoutResourceId, urls);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.urls = urls;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
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
            holder.aSyncLoader.cancel(true);
        }
        // Set placeholders
        holder.image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.default_image));
        // Load images asynchronously in to each view
        holder.aSyncLoader = new AsyncImageLoader(holder.image);
        holder.aSyncLoader.execute(urls.get(position));
        return convertView;
    }

    static class ViewHolder {
        ImageView image;
        AsyncImageLoader aSyncLoader;
    }
}