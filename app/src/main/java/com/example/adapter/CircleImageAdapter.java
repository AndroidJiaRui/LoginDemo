package com.example.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.example.app.MyApp;
import com.example.logindemo.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.POST;

public class CircleImageAdapter extends BaseAdapter {
    private List<String> mList = new ArrayList<>();

    public void addAll(List<String> list){
        mList.addAll(list);
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            view = View.inflate(MyApp.getContext(), R.layout.circie_image_item, null);
            holder = new ViewHolder();
            holder.simpleDraweeView = view.findViewById(R.id.circle_item_image);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.simpleDraweeView.setImageURI(mList.get(i));
        return view;
    }

    public void clear() {
        mList.clear();
    }

    class ViewHolder{
        SimpleDraweeView simpleDraweeView;
    }
}
