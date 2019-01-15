package com.example.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.app.MyApp;
import com.example.bean.DetaileGoods;
import com.example.logindemo.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.POST;

public class MyDetailAdapter extends BaseAdapter {
    private List<DetaileGoods> list = new ArrayList<>();
    public void addList(DetaileGoods u){
        if(u!=null){
            list.add(u);
        }
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            view = View.inflate(MyApp.getContext(), R.layout.my_detail, null);
            holder = new ViewHolder();
            holder.textView1 = view.findViewById(R.id.detail_price);
            holder.textView2 = view.findViewById(R.id.detail_number);
            holder.textView3 = view.findViewById(R.id.detail_content);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.textView1.setText("￥"+list.get(i).getPrice());
        holder.textView2.setText("已售"+list.get(i).getSaleNum()+"件");
        holder.textView3.setText(list.get(i).getDescribe());
        return view;
    }
    class ViewHolder{
        TextView textView1,textView2,textView3;
    }
}
