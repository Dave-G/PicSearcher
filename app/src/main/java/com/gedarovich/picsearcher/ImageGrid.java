package com.gedarovich.picsearcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ImageGrid extends AppCompatActivity {

    private TextView backButton;
    private TextView imageGridTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Setup activity
        super.onCreate(savedInstanceState);
        Tools.removeBars(this);
        setContentView(R.layout.activity_image_grid);
        // Get references
        backButton = findViewById(R.id.backButton);
        imageGridTitle = findViewById(R.id.imageGridTitle);
        // Set typefaces
        Tools.setTypeface(this, backButton);
        Tools.setTypeface(this, imageGridTitle);
        // Bind listeners
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
        finish();
    }
}
