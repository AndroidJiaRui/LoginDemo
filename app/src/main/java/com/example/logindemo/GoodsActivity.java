package com.example.logindemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.example.fragment.Fragment1;
import com.example.fragment.Fragment2;
import com.example.fragment.Fragment3;
import com.example.fragment.Fragment4;
import com.example.fragment.Fragment5;

import static com.example.logindemo.R.id.fragment;

public class GoodsActivity extends AppCompatActivity {

    private FragmentManager manager;
    private RadioGroup btn_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        btn_group = findViewById(R.id.btn_group);
        manager = getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        final Fragment1 fragment1 = new Fragment1();
        final Fragment2 fragment2 = new Fragment2();
        final Fragment3 fragment3 = new Fragment3();
        final Fragment4 fragment4 = new Fragment4();
        final Fragment5 fragment5 = new Fragment5();
        transaction.add(R.id.fragment, fragment1).show(fragment1);
        transaction.add(R.id.fragment, fragment2).hide(fragment2);
        transaction.add(R.id.fragment, fragment3).hide(fragment3);
        transaction.add(R.id.fragment, fragment4).hide(fragment4);
        transaction.add(R.id.fragment, fragment5).hide(fragment5);
        transaction.commit();
        btn_group.check(btn_group.getChildAt(0).getId());
        btn_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                FragmentManager manager1 = getSupportFragmentManager();
                FragmentTransaction transaction1 = manager1.beginTransaction();
                switch (i){
                    case R.id.rb1:
                        transaction1.show(fragment1).hide(fragment2).hide(fragment3).hide(fragment4).hide(fragment5);
                        break;
                    case R.id.rb2:
                        transaction1.show(fragment2).hide(fragment1).hide(fragment3).hide(fragment4).hide(fragment5);
                        break;
                    case R.id.rb3:
                        transaction1.show(fragment3).hide(fragment2).hide(fragment1).hide(fragment4).hide(fragment5);
                        break;
                    case R.id.rb4:
                        transaction1.show(fragment4).hide(fragment2).hide(fragment3).hide(fragment1).hide(fragment5);
                        break;
                    case R.id.rb5:
                        transaction1.show(fragment5).hide(fragment2).hide(fragment3).hide(fragment4).hide(fragment1);
                        break;
                }
                transaction1.commit();
            }
        });
    }

}
