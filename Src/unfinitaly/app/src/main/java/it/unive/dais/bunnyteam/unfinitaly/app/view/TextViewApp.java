package it.unive.dais.bunnyteam.unfinitaly.app.view;

/**
 *
 * @author BunnyTeam, Università Ca' Foscari
 */

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatTextView;



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
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Montserrat-Regular.ttf");
        setTypeface(tf ,1);

    }

}