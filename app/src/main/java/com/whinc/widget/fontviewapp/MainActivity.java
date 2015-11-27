package com.whinc.widget.fontviewapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.whinc.widget.fontview.FontTextView;
import com.whinc.widget.fontview.FontUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = MainActivity.class.getSimpleName();
    private FontTextView mFontTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Method 1: replace the font with FontTextView */

        findViewById(R.id.set_path_button).setOnClickListener(this);
        findViewById(R.id.create_button).setOnClickListener(this);
        findViewById(R.id.replace_font_from_asset_btn).setOnClickListener(this);
        findViewById(R.id.replace_font_from_file_btn).setOnClickListener(this);
        findViewById(R.id.add_view_btn).setOnClickListener(this);
        mFontTextView = (FontTextView)findViewById(R.id.font1_textView);

        /* Method 2: replace the font of view and it's children recursively */

        // read font data from asset
        String fontPath = "fonts/my_font.ttf";
        FontUtils.getInstance().replaceFontFromAsset(
                findViewById(R.id.layout2), fontPath);
        FontUtils.getInstance().replaceFontFromAsset(
                findViewById(R.id.layout3), fontPath, Typeface.BOLD);

        // read font data from file
//        String fontPath = Environment.getExternalStorageDirectory() + "/whinc/my_font.ttf";
//        Log.i(TAG, "font path:" + fontPath);
//        FontUtils.getInstance().replaceFontFromFile(
//                findViewById(R.id.layout2), fontPath);
//        FontUtils.getInstance().replaceFontFromFile(
//                findViewById(R.id.layout3), fontPath, Typeface.BOLD);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_path_button:
                mFontTextView.setFontPath("fonts/my_font.ttf");
                break;
            case R.id.create_button:
                v.setEnabled(false);    // can click only once
                ViewGroup parent = (ViewGroup) findViewById(R.id.container_layout);
                createFontTextView(parent, "fonts/my_font.ttf");
//                createTextView(parent, "fonts/my_font.ttf");
                break;
            case R.id.replace_font_from_asset_btn:
                /* Method 3: replace system default font */
                FontUtils.getInstance().replaceSystemDefaultFontFromAsset(this, "fonts/my_font.ttf");
                recreate();
                break;
            case R.id.replace_font_from_file_btn:
                if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                    String fontPath = Environment.getExternalStorageDirectory() + "/whinc/my_font.ttf";
                    FontUtils.getInstance().replaceSystemDefaultFontFromFile(this, fontPath);
                    recreate();
                } else {
                    Toast.makeText(this, "External storage is not accessible!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.add_view_btn:
                v.setEnabled(false);    // can click only once
                Button button = new Button(this, null, R.attr.buttonStyle);
                button.setText("Button");
                RelativeLayout layout = (RelativeLayout) findViewById(R.id.font_text_layout);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                lp.addRule(RelativeLayout.CENTER_IN_PARENT);
                layout.addView(button, lp);
                break;
            default:
                break;
        }
    }

    private void createFontTextView(ViewGroup parent, String path) {
        FontTextView fontTextView = new FontTextView(this);
        fontTextView.setFontPath(path);
        fontTextView.setText("FontTextView");
        fontTextView.setTextColor(Color.RED);
        fontTextView.setGravity(Gravity.CENTER_VERTICAL);
        parent.addView(fontTextView, new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT, 1
        ));
    }

    private void createTextView(ViewGroup parent, String fontPath) {
        TextView textView = new TextView(this);
        textView.setText("TextView");
        parent.addView(textView);
    }
}
