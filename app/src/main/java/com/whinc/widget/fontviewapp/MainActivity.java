package com.whinc.widget.fontviewapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whinc.widget.fontview.FontTextView;
import com.whinc.widget.fontview.FontUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FontTextView mFontTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.set_null_path_button).setOnClickListener(this);
        findViewById(R.id.set_path_button).setOnClickListener(this);
        findViewById(R.id.create_button).setOnClickListener(this);
        findViewById(R.id.replace_font_btn).setOnClickListener(this);
        findViewById(R.id.restore_font_btn).setOnClickListener(this);
        mFontTextView = (FontTextView)findViewById(R.id.font1_textView);

        View root = findViewById(R.id.layout2);
        FontUtils.getInstance().replaceFont(root, "fonts/my_font.ttf");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_null_path_button:
                mFontTextView.setFontPath(null);
                break;
            case R.id.set_path_button:
                mFontTextView.setFontPath("fonts/my_font.ttf");
                break;
            case R.id.create_button:
                ViewGroup parent = (ViewGroup) findViewById(R.id.container_layout);
                createFontTextView(parent, "fonts/my_font.ttf");
//                createTextView(parent, "fonts/my_font.ttf");
                break;
            case R.id.replace_font_btn:
                FontUtils.getInstance().replaceSystemDefaultFont(this, "fonts/my_font.ttf");
                recreate();
                break;
            case R.id.restore_font_btn:
                FontUtils.getInstance().replaceSystemDefaultFont(FontUtils.DEFAULT);
                recreate();
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
