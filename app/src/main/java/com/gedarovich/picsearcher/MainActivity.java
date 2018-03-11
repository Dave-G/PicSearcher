package com.gedarovich.picsearcher;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private TextView searchTitle;
    private TextView searchButton;
    private TextView credits;
    private EditText searchText;
    private boolean searchActivated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Setup activity
        super.onCreate(savedInstanceState);
        Tools.removeBars(this);
        setContentView(R.layout.activity_main);
        getReferences();
        setTypefaces();
        bindListeners();
    }

    // Get references from the layout
    private void getReferences(){
        searchTitle = findViewById(R.id.searchTitle);
        searchButton = findViewById(R.id.searchButton);
        credits = findViewById(R.id.credits);
        searchText = findViewById(R.id.searchText);
    }

    // Set typefaces for text elements
    private void setTypefaces(){
        Tools.setTypeface(this, searchTitle);
        Tools.setTypeface(this, searchButton);
        Tools.setTypeface(this, credits);
        Tools.setTypeface(this, searchText);
    }

    // Bind button and other listeners
    private void bindListeners(){
        searchText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    clearSearchText();
                }
                return false;
            }
        });
        searchText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // Pressing "enter" in search is the same as clicking the button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    clickSearch();
                    return true;
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

    // Clear the search text field and update color when it is first touched
    private void clearSearchText(){
        if (!searchActivated) {
            searchText.setText("");
            searchText.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));
            searchActivated = true;
        }
    }

    // Click the search button and pass string to the next page
    private void clickSearch(){
        String searchString = searchText.getText().toString();
        // Don't allow searching with the default text or an empty string
        if (searchString.length() > 0 && searchActivated) {
            MediaPlayer buttonSFX = MediaPlayer.create(getApplicationContext(), R.raw.button);
            buttonSFX.start();
            Intent i = new Intent(this, ImageGrid.class);
            i.putExtra(getString(R.string.extrasSearchString), searchString);
            startActivity(i);
        }
        else{
            MediaPlayer errorSFX = MediaPlayer.create(getApplicationContext(), R.raw.error);
            errorSFX.start();
            // Show a message if nothing has been entered
            Toast.makeText(this, getString(R.string.errorNoSearch), Toast.LENGTH_SHORT).show();
        }
    }

}
