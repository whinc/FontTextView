package com.whinc.widget.fontviewapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.whinc.widget.fontview.FontTextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FontTextView mFontTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.set_null_path_button).setOnClickListener(this);
        findViewById(R.id.set_path_button).setOnClickListener(this);
        findViewById(R.id.create_button).setOnClickListener(this);
        mFontTextView = (FontTextView)findViewById(R.id.font1_textView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_null_path_button:
                mFontTextView.setPath(null);
                break;
            case R.id.set_path_button:
                mFontTextView.setPath("fonts/SnellRoundhand.ttf");
                break;
            case R.id.create_button:
                ViewGroup parent = (ViewGroup) findViewById(R.id.container_layout);
                createFontTextView(parent, "fonts/SnellRoundhand.ttf");
                break;
            default:
                break;
        }
    }

    private void createFontTextView(ViewGroup parent, String path) {
        FontTextView fontTextView = new FontTextView(this);
        fontTextView.setPath(path);
        fontTextView.setText("Hello world!");
        fontTextView.setTextColor(Color.RED);
        fontTextView.setGravity(Gravity.CENTER_VERTICAL);
        parent.addView(fontTextView, 0, new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT, 1
        ));
    }
}
