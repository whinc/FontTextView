package com.whinc.widget.fontview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/27.
 */
public class FontTextView extends TextView {
    private static final String TAG = FontTextView.class.getSimpleName();
    /** The file name of the font data in the assets directory*/
    private String mFontPath;
    private static Map<String, SoftReference<Typeface>> sCache = new HashMap<>();

    public FontTextView(Context context) {
        super(context);
        init(context, null);
    }
    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public String getFontPath() {
        return mFontPath;
    }

    /**
     * <p>Set font file fontPath</p>
     * @param fontPath The file name of the font data in the assets directory
     */
    public void setFontPath(String fontPath) {
        mFontPath = fontPath;

        // get font style
        int style = Typeface.NORMAL;
        if (getTypeface() != null) {
            style = getTypeface().getStyle();
        }
        // replace typeface
        if (TextUtils.isEmpty(fontPath)) {
            setTypeface(Typeface.DEFAULT, style);
        } else {
            setTypeface(createTypeface(fontPath), style);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        // get font path from XML layout
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FontTextView);
        mFontPath = typedArray.getString(R.styleable.FontTextView_font_path);
        typedArray.recycle();

        if (mFontPath == null) {
            return;
        }

        // replace default typeface
        int style = Typeface.NORMAL;
        if (getTypeface() != null) {
            style = getTypeface().getStyle();
        }
        setTypeface(createTypeface(mFontPath), style);
    }

    /**
     * <p>Create a Typeface instance with specified font file</p>
     * @param fontPath font file path relative to 'assets' directory.
     * @return Return created typeface instance.
     */
    private Typeface createTypeface(String fontPath) {
        SoftReference<Typeface> typefaceRef = sCache.get(fontPath);
        Typeface typeface = null;
        if (typefaceRef == null || (typeface = typefaceRef.get()) == null) {
            typeface = Typeface.createFromAsset(getContext().getAssets(), fontPath);
            typefaceRef = new SoftReference<>(typeface);
            sCache.put(fontPath, typefaceRef);
            Log.i(TAG, "Create typeface:" + fontPath);
        } else {
            Log.i(TAG, "Hit cache:" + fontPath);
        }
        return typeface;
    }
}
