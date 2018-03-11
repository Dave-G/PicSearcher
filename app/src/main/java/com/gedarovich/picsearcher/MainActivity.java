package com.gedarovich.picsearcher;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private TextView searchTitle;
    private EditText searchText;
    private TextView searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Setup activity
        super.onCreate(savedInstanceState);
        Tools.removeBars(this);
        setContentView(R.layout.activity_main);
        // Get references
        searchTitle = findViewById(R.id.searchTitle);
        searchText = findViewById(R.id.searchText);
        searchButton = findViewById(R.id.searchButton);
        // Set typefaces
        Tools.setTypeface(this, searchTitle);
        Tools.setTypeface(this, searchText);
        Tools.setTypeface(this, searchButton);
        // Bind listeners
        searchText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    clearSearchText();
                }
                return false;
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tools.bounceView(MainActivity.this, view);
                clickSearch();
            }
        });
    }

    // Hide soft keyboard when not in a text field
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Tools.dynamicSoftKeyboard(this);
        return super.dispatchTouchEvent(ev);
    }

    // Clear the search text field and update color when it is touched
    private void clearSearchText(){
        searchText.setText("");
        searchText.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));
    }

    // Click the search button and send a request to the server
    private void clickSearch(){
        String url = getString(R.string.pixabayBaseURL) + searchText.getText().toString();
        JsonObjectRequest pixabaySearchRequest = new JsonObjectRequest
            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println(response.toString());
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

}
