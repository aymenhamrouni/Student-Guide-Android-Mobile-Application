package com.hamdroid.StudentGuide.views.textview;

/**
 * Created by Eymen Hamrouni on 04/12/2017.
 */


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.hamdroid.StudentGuide.R;


public class TypefacedTextView extends TextView {

    public TypefacedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Typeface.createFromAsset doesn't work in the layout editor. Skipping...
        if (isInEditMode()) {
            return;
        }

        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.TypefacedTextView);
        String fontName = styledAttrs.getString(R.styleable.TypefacedTextView_typeface);

        setTypeFace(fontName);
        boolean allCaps = styledAttrs.getBoolean(R.styleable.TypefacedTextView_allCaps,false);
        allCaps(allCaps);

        styledAttrs.recycle();
    }

    public void setTypeFace(String fontName){
        try{
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
            setTypeface(typeface);

        }catch (Exception e){
            //Log.e("FONT", "Police " + fontName + " introuvable grosse !");
        }
    }

    public void allCaps(boolean upperCase){
        if(upperCase)
            this.setText(this.getText().toString().toUpperCase());
    }
}

