package com.gedarovich.picsearcher;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class Tools  {

    private static Typeface defaultTypeface;
    private static String typefaceName = "swanse.ttf";

    // Set the typeface
    public static void setTypeface(Activity act, TextView tv){
        if (defaultTypeface == null) {
            defaultTypeface = Typeface.createFromAsset(act.getAssets(), typefaceName);
        }
        tv.setTypeface(defaultTypeface);
    }

    // Remove top bars and fullscreen the app
    public static void removeBars(Activity act){
        // Remove title bar
        act.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Remove notification bar
        act.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    // Hide the soft keyboard when touching anywhere outside of a text field
    public static void dynamicSoftKeyboard(Activity act){
        try {
            if (act.getCurrentFocus() != null) {
                InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(act.getCurrentFocus().getWindowToken(), 0);
            }
        }
        catch (Exception e){
            // Null pointers from soft input aren't critical, show them to console only
            e.printStackTrace();
        }
    }

    // Bounce animate a view
    public static void bounceView(Activity act, View v) {
        final Animation bounceAnim = AnimationUtils.loadAnimation(act, R.anim.bounce);
        v.startAnimation(bounceAnim);
    }
}
