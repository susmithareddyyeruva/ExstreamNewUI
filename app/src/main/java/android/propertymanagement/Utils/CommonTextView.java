package android.propertymanagement.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;



/*
 * this class is created to maintain the custom and commmom textview
 * this will extends the textview
 * we can use this button anywhere in project
 * we need to import this class
 * */

public class CommonTextView extends android.support.v7.widget.AppCompatTextView {

    public CommonTextView(Context context) {
        super(context);
        // applying custom font
        ApplyCustomFont(context);
    }

    public CommonTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // applying custom font
        ApplyCustomFont(context);
    }

    public CommonTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // applying custom font
        ApplyCustomFont(context);
    }


    //appliying custom font
    private void ApplyCustomFont(Context context) {
        //change the font style from here by adding the .ttf file to the asserts
        Typeface customFont = Fontcache.gettypeFace(context, "Font-Regular.ttf");
        setTypeface(customFont);
    }


}
