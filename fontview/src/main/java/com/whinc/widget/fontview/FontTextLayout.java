package com.whinc.widget.fontview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2015/11/27.
 */
public class FontTextLayout extends RelativeLayout {

    private String mFontPath = null;

    public FontTextLayout(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public FontTextLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public FontTextLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FontTextLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FontTextLayout, defStyleAttr, defStyleRes);
        mFontPath = typedArray.getString(R.styleable.FontTextLayout_ftl_font_path);
        typedArray.recycle();
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        if (!TextUtils.isEmpty(mFontPath)) {
            FontUtils.getInstance().replaceFontFromAsset(child, mFontPath);
        }
    }
}
