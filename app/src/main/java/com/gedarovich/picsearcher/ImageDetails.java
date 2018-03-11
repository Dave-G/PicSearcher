package com.gedarovich.picsearcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageDetails extends AppCompatActivity {

    private TextView backButton;
    private TextView imageDetailsTitle;
    private TextView postedBy;
    private TextView favorites;
    private TextView likes;
    private TextView views;
    private TextView webLink;
    private ImageView imageLarge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Setup activity
        super.onCreate(savedInstanceState);
        Tools.removeBars(this);
        setContentView(R.layout.activity_image_details);
        // Get references
        backButton = findViewById(R.id.backButton);
        imageDetailsTitle = findViewById(R.id.imageDetailsTitle);
        postedBy = findViewById(R.id.postedBy);
        favorites = findViewById(R.id.favorites);
        likes = findViewById(R.id.likes);
        views = findViewById(R.id.views);
        webLink = findViewById(R.id.webLink);
        imageLarge = findViewById(R.id.imageLarge);
        // Set typefaces
        Tools.setTypeface(this, backButton);
        Tools.setTypeface(this, imageDetailsTitle);
        Tools.setTypeface(this, postedBy);
        Tools.setTypeface(this, favorites);
        Tools.setTypeface(this, likes);
        Tools.setTypeface(this, views);
        Tools.setTypeface(this, webLink);
        // Bind listeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tools.bounceView(ImageDetails.this, view);
                clickBack();
            }
        });
    }

    // Click the back button and close the page
    private void clickBack(){
        finish();
    }
}
