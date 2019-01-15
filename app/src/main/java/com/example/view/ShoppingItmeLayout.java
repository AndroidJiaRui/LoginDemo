package com.example.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.example.logindemo.R;

public class ShoppingItmeLayout extends LinearLayout {
    public ShoppingItmeLayout(Context context) {
        super(context);
        initView();
    }

    public ShoppingItmeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ShoppingItmeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    private void initView(){
        View view = View.inflate(getContext(), R.layout.shopping_itme_layout, this);
    }
}
