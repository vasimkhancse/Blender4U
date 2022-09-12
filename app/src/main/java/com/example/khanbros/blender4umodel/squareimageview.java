package com.example.khanbros.blender4umodel;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;


public class squareimageview extends AppCompatImageView {


    public squareimageview(Context context) {
        super(context);
    }

    public squareimageview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public squareimageview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
