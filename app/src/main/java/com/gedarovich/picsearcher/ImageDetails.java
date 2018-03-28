package com.gedarovich.picsearcher;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

public class ImageDetails extends AppCompatActivity {

    private TextView backButton;
    private TextView imageDetailsTitle;
    private TextView postedBy;
    private TextView favorites;
    private TextView likes;
    private TextView comments;
    private TextView views;
    private TextView webLink;
    private ImageView imageLarge;
    private JSONObject detailsJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Setup activity
        super.onCreate(savedInstanceState);
        Tools.removeBars(this);
        setContentView(R.layout.activity_image_details);
        getReferences();
        setTypefaces();
        bindListeners();
        getExtras();
    }

    // Get references from the layout
    private void getReferences(){
        backButton = findViewById(R.id.backButton);
        imageDetailsTitle = findViewById(R.id.imageDetailsTitle);
        postedBy = findViewById(R.id.postedBy);
        favorites = findViewById(R.id.favorites);
        likes = findViewById(R.id.likes);
        comments = findViewById(R.id.comments);
        views = findViewById(R.id.views);
        webLink = findViewById(R.id.webLink);
        imageLarge = findViewById(R.id.imageLarge);
    }

    // Set typefaces for text elements
    private void setTypefaces(){
        Tools.setTypeface(this, backButton);
        Tools.setTypeface(this, imageDetailsTitle);
        Tools.setTypeface(this, postedBy);
        Tools.setTypeface(this, favorites);
        Tools.setTypeface(this, likes);
        Tools.setTypeface(this, comments);
        Tools.setTypeface(this, views);
        Tools.setTypeface(this, webLink);
    }

    // Bind button and other listeners
    private void bindListeners(){
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
        MediaPlayer clickSFX = MediaPlayer.create(getApplicationContext(), R.raw.click);
        clickSFX.start();
        finish();
    }

    // Get extras passed from previous activity and load data in to the page
    private void getExtras(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                detailsJSON = new JSONObject(extras.getString(getString(R.string.extrasDetailsJSON)));
                loadPage();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // Load image and text on the page
    private void loadPage(){
        try {
            if (detailsJSON.length() > 0) {
                // Load full image
                new AsyncImageLoader(imageLarge).execute(detailsJSON.getString("webformatURL"));
                // Set details text
                String postedByText = postedBy.getText().toString();
                postedByText += " " + detailsJSON.getString("user");
                postedBy.setText(postedByText);
                String favoritesText = favorites.getText().toString();
                favoritesText += " " + detailsJSON.getString("favorites");
                favorites.setText(favoritesText);
                String likesText = likes.getText().toString();
                likesText += " " + detailsJSON.getString("likes");
                likes.setText(likesText);
                String commentsText = comments.getText().toString();
                commentsText += " " + detailsJSON.getString("comments");
                comments.setText(commentsText);
                String viewsText = views.getText().toString();
                viewsText += " " + detailsJSON.getString("views");
                views.setText(viewsText);
                String webLinkText = detailsJSON.getString("pageURL");
                webLink.setText(webLinkText);
            }
        }
        catch (Exception e){
            Log.e("Error", e.getMessage());
        }
    }
}
