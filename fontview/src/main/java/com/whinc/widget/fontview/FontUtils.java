package com.whinc.widget.fontview;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by whinc on 2015/11/23.
 * <p>FontUtils can replace all the font under specified view.</p>
 */
public class FontUtils {
    public static final String TAG = FontUtils.class.getSimpleName();
    private static FontUtils sSingleton = null;
    private Map<String, SoftReference<Typeface>> mCache = new HashMap<>();

    // disable instantiate
    private FontUtils() {}

    public static FontUtils getInstance() {
        // double check
        if (sSingleton == null) {
            synchronized(FontUtils.class) {
                if (sSingleton == null) {
                    sSingleton = new FontUtils();
                }
            }
        }
        return sSingleton;
    }

    /**
     * <p>Replace the font of specified view and it's children</p>
     * @param root The root view.
     * @param fontPath font file path relative to 'assets' directory.
     */
    public void replaceFont(@NonNull View root, String fontPath) {
        if (root == null || TextUtils.isEmpty(fontPath)) {
            return;
        }

        if (root instanceof TextView) { // If view is TextView or it's subclass, replace it's font
            TextView textView = (TextView)root;
            int style = Typeface.NORMAL;
            if (textView.getTypeface() != null) {
                style = textView.getTypeface().getStyle();
            }
            textView.setTypeface(createTypeface(root.getContext(), fontPath), style);
        } else if (root instanceof ViewGroup) { // If view is ViewGroup, apply this method on it's child views
            ViewGroup viewGroup = (ViewGroup) root;
            for (int i = 0; i < viewGroup.getChildCount(); ++i) {
                replaceFont(viewGroup.getChildAt(i), fontPath);
            }
        } // else return
    }

    /**
     * <p>Create a Typeface instance with specified font file</p>
     * @param fontPath font file path relative to 'assets' directory.
     * @return Return created typeface instance.
     */
    private Typeface createTypeface(Context context, String fontPath) {
        SoftReference<Typeface> typefaceRef = mCache.get(fontPath);
        Typeface typeface = null;
        if (typefaceRef == null || (typeface = typefaceRef.get()) == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), fontPath);
            typefaceRef = new SoftReference<>(typeface);
            mCache.put(fontPath, typefaceRef);
            Log.i(TAG, "Hit cache:" + fontPath);
        }
        return typeface;
    }
}
