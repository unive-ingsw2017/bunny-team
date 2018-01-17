package it.unive.dais.bunnyteam.unfinitaly.app.view;

/**
 * Created by giacomo on 17/01/18.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatTextView;


/**
 * Created by giaco on 8/23/2017.
 */

public class TextViewApp extends AppCompatTextView {
    public TextViewApp(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewApp(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public TextViewApp(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/BALOO_REG.ttf");
        setTypeface(tf ,1);

    }

}