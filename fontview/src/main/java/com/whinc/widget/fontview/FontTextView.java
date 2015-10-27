package com.whinc.widget.fontview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/27.
 */
public class FontTextView extends TextView {
    private static final String TAG = FontTextView.class.getSimpleName();
    /** The file name of the font data in the assets directory*/
    private String mPath;
    private Context mContext;
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

    public String getPath() {
        return mPath;
    }

    /**
     * <p>Set font file path</p>
     * @param path The file name of the font data in the assets directory
     */
    public void setPath(String path) {
        if (TextUtils.equals(mPath, path)) {
            return;
        }

        mPath = path;

        // get font style
        int style = Typeface.NORMAL;
        if (getTypeface() != null) {
            style = getTypeface().getStyle();
        }
        // set typeface
        if (TextUtils.isEmpty(path)) {
            setTypeface(Typeface.DEFAULT, style);
        } else {
            setTypeface(getCustomTypeface(mContext, path), style);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        if (attrs == null) {
            return;
        }

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FontTextView);
        mPath = typedArray.getString(R.styleable.FontTextView_path);
        typedArray.recycle();

        if (mPath == null) {
            return;
        }

        Log.i(TAG, "font file name:" + mPath);
        int style = Typeface.NORMAL;
        if (getTypeface() != null) {
            style = getTypeface().getStyle();
        }
        setTypeface(getCustomTypeface(context, mPath), style);
    }

    private Typeface getCustomTypeface(Context c, String fontFileName) {
        SoftReference<Typeface> typefaceRef = sCache.get(fontFileName);
        Typeface typeface = null;
        if (typefaceRef == null || (typeface = typefaceRef.get()) == null) {
            typeface = Typeface.createFromAsset(c.getAssets(), fontFileName);
            typefaceRef = new SoftReference<>(typeface);
            sCache.put(fontFileName, typefaceRef);
        }
        return typeface;
    }
}
