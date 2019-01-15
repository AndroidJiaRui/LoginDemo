package com.example.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.adapter.MyFragment4Adapter;
import com.example.logindemo.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment4 extends Fragment {

    private RadioGroup radio_group;
    private ViewPager view_pager;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment4, container, false);
        radio_group = view.findViewById(R.id.fragment4_radiogroup);
        view_pager = view.findViewById(R.id.fragment4_viewpager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<Fragment> list = new ArrayList<>();
        list.add(new AllFragment());
        list.add(new WaitMoneyFragment());
        list.add(new WaitReceiverFragment());
        list.add(new WaitPJFragment());
        list.add(new AlreadyFragment());
        MyFragment4Adapter myFragment4Adapter = new MyFragment4Adapter(getActivity().getSupportFragmentManager(), list);
        view_pager.setAdapter(myFragment4Adapter);
        view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                radio_group.check(radio_group.getChildAt(position).getId());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioButtona:
                        view_pager.setCurrentItem(0,false);
                        break;
                    case R.id.radioButtonb:
                        view_pager.setCurrentItem(1,false);
                        break;
                    case R.id.radioButtonc:
                        view_pager.setCurrentItem(2,false);
                        break;
                    case R.id.radioButtond:
                        view_pager.setCurrentItem(3,false);
                        break;
                        case R.id.radioButtone:
                            view_pager.setCurrentItem(4,false);
                        break;

                }
            }
        });
    }
}
