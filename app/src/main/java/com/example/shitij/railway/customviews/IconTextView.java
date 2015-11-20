package com.example.shitij.railway.customviews;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.example.shitij.railway.RailwayApplication;

/**
 * Created by satyamkrishna on 03/12/14.
 */
public class IconTextView extends AppCompatTextView {

    public IconTextView(Context context) {
        super(context);
        setCustomFont(context);
    }

    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context);
    }

    public IconTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFont(context);
    }

    private void setCustomFont(Context context) {
        if (context != null && !isInEditMode()) {
            setTypeface(RailwayApplication.getTypefaceIconFont());
        }
    }

}