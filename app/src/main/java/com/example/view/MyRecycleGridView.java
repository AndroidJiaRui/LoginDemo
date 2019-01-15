package com.example.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class MyRecycleGridView extends GridView {
    public MyRecycleGridView(Context context) {
        super(context);
    }

    public MyRecycleGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecycleGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, i);
    }
}
