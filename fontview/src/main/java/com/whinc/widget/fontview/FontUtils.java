package com.whinc.widget.fontview;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by whinc on 2015/11/23.
 * <p>FontUtils can replace all the font under specified view.</p>
 */
public class FontUtils {
    public static final String TAG = FontUtils.class.getSimpleName();
    private Map<String, SoftReference<Typeface>> mCache = new HashMap<>();
    private static FontUtils sSingleton = null;

    public static Typeface DEFAULT = Typeface.DEFAULT;

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
    public void replaceFont(@NonNull View root, @NonNull String fontPath) {
        replaceFont(root, createTypeface(root.getContext(), fontPath));
    }

    public void replaceFont(@NonNull View root, @NonNull Typeface typeface) {
        if (root == null || typeface == null) {
            return;
        }

        if (root instanceof TextView) { // If view is TextView or it's subclass, replace it's font
            TextView textView = (TextView)root;
            int style = Typeface.NORMAL;
            if (textView.getTypeface() != null) {
                style = textView.getTypeface().getStyle();
            }
            textView.setTypeface(typeface, style);
        } else if (root instanceof ViewGroup) { // If view is ViewGroup, apply this method on it's child views
            ViewGroup viewGroup = (ViewGroup) root;
            for (int i = 0; i < viewGroup.getChildCount(); ++i) {
                replaceFont(viewGroup.getChildAt(i), typeface);
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
            Log.i(TAG, "Create typeface:" + fontPath);
        } else {
            Log.i(TAG, "Hit cache:" + fontPath);
        }
        return typeface;
    }

    /**
     * <p>Replace system default font. <b>Note:</b>you should also add code below to your app theme in styles.xml. </p>
     * {@code <item name="android:typeface">monospace</item>}
     * <p>The best place to call this method is {@link Application#onCreate()}, it will affect
     * whole app font.If you call this method after view is visible, you need to invalid the view to make it effective.</p>
     * @param context {@link Context Context}
     * @param fontPath font file path relative to 'assets' directory.
     */
    public void replaceSystemDefaultFont(@NonNull Context context, @NonNull String fontPath) {
        replaceSystemDefaultFont(createTypeface(context, fontPath));
    }

    /**
     * <p>Replace system default font. <b>Note:</b>you should also add code below to your app theme in styles.xml. </p>
     * {@code <item name="android:typeface">monospace</item>}
     * <p>The best place to call this method is {@link Application#onCreate()}, it will affect
     * whole app font.If you call this method after view is visible, you need to invalid the view to make it effective.</p>
     */
    public void replaceSystemDefaultFont(@NonNull Typeface typeface) {
        replaceTypefaceField("MONOSPACE", typeface);
    }

    /**
     * <p>Replace field in class Typeface with reflection.</p>
     * @param fieldName Field name in Typeface.
     * @param value New field value
     */
    private void replaceTypefaceField(String fieldName, Object value) {
        try {
            Field defaultField = Typeface.class.getDeclaredField(fieldName);
            defaultField.setAccessible(true);
            defaultField.set(null, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
