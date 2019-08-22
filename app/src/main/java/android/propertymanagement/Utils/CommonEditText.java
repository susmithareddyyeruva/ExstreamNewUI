package android.propertymanagement.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.EditText;




/*
 * this class is created to maintain the custom and commmom EditText
 * this will extends the EditText
 * we can use this EditText anywhere in project
 * we need to import this class
 * */


@SuppressLint("AppCompatCustomView")
public class CommonEditText extends EditText {
    public CommonEditText(Context context) {
        super(context);
        // applying custom font
        ApplyCustomFont(context);
    }

    public CommonEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        // applying custom font
        ApplyCustomFont(context);
    }

    public CommonEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // applying custom font
        ApplyCustomFont(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CommonEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        // applying custom font
        ApplyCustomFont(context);

    }
    //appliying custom font
    private void ApplyCustomFont(Context context) {

        // change the font style from here by adding the .ttf file to the asserts
        Typeface customFont = Fontcache.gettypeFace(context, "Font-Regular.ttf");
        setTypeface(customFont);
    }
}
