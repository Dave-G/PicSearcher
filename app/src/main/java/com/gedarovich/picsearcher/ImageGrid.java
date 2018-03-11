package com.gedarovich.picsearcher;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class ImageGrid extends AppCompatActivity {

    private TextView backButton;
    private TextView imageGridTitle;
    private GridView imageGridView;
    private GridViewAdapter gridAdapter;
    private String searchString = "";
    private JSONArray hitsArray;
    private int maxResults = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Setup activity
        super.onCreate(savedInstanceState);
        Tools.removeBars(this);
        setContentView(R.layout.activity_image_grid);
        getReferences();
        setTypefaces();
        bindListeners();
        getExtras();
    }

    // Get references from the layout
    private void getReferences(){
        backButton = findViewById(R.id.backButton);
        imageGridTitle = findViewById(R.id.imageGridTitle);
        imageGridView = findViewById(R.id.imageGridView);
    }

    // Set typefaces for text elements
    private void setTypefaces(){
        Tools.setTypeface(this, backButton);
        Tools.setTypeface(this, imageGridTitle);
    }

    // Bind button and other listeners
    private void bindListeners(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tools.bounceView(ImageGrid.this, view);
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

    // Get extras passed from previous activity and send request if they exist
    private void getExtras(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            searchString = extras.getString(getString(R.string.extrasSearchString));
            sendGetRequest();
        }
    }

    // Send a request to the server based on search query
    private void sendGetRequest(){
        String url = getString(R.string.pixabayBaseURL) + searchString;
        JsonObjectRequest pixabaySearchRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        setupGrid(response);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        }
                );
        RequestSingleton.getInstance(this).addRequest(pixabaySearchRequest);
    }

    // Get image URLs for the grid
    private ArrayList<String> getURLs(JSONObject jsonObject) {
        try {
            if (jsonObject.has("hits")) {
                hitsArray = jsonObject.getJSONArray("hits");
                final ArrayList<String> imageURLs = new ArrayList<>();
                // Show a message if no images are found
                if (hitsArray.length() == 0){
                    MediaPlayer errorSFX = MediaPlayer.create(getApplicationContext(), R.raw.error);
                    errorSFX.start();
                    Toast.makeText(this, getString(R.string.errorNoImages), Toast.LENGTH_SHORT).show();
                }
                else {
                    for (int i = 0; i < maxResults && i < hitsArray.length(); i++) {
                        imageURLs.add(hitsArray.getJSONObject(i).getString("previewURL"));
                    }
                }
                return imageURLs;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    // Setup GridView, adapter, and click listeners
    private void setupGrid(JSONObject jsonObj){
        gridAdapter = new GridViewAdapter(this, R.layout.grid_image, getURLs(jsonObj));
        imageGridView.setAdapter(gridAdapter);
        imageGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                MediaPlayer buttonSFX = MediaPlayer.create(getApplicationContext(), R.raw.button);
                buttonSFX.start();
                Intent intent = new Intent(ImageGrid.this, ImageDetails.class);
                // Pass image details JSON as String
                try {
                    intent.putExtra(getString(R.string.extrasDetailsJSON), hitsArray.getJSONObject(position).toString());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                // Open image details page
                startActivity(intent);
            }
        });
    }
}
