package com.example.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app.MyApp;
import com.example.bean.DetaileGoods;
import com.example.logindemo.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.POST;

public class MyXQAdapter extends BaseAdapter {
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
            view = View.inflate(MyApp.getContext(), R.layout.my_xq, null);
            holder = new ViewHolder();
            holder.textView1 = view.findViewById(R.id.xq_title);
            holder.textView2 = view.findViewById(R.id.xq_content);
            holder.imageView1 = view.findViewById(R.id.xq_image_one);
            holder.imageView2 = view.findViewById(R.id.xq_image);
            holder.imageView3 = view.findViewById(R.id.xq_image_one2);
            holder.imageView4 = view.findViewById(R.id.xq_image_one3);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        DetaileGoods detaileGoods = list.get(i);
        holder.textView1.setText(detaileGoods.getCommodityName());
        holder.textView2.setText(detaileGoods.getDescribe());
        String picture = detaileGoods.getPicture();
        String[] split = picture.split(",");
        Glide.with(MyApp.getContext()).load(split[0]).into(holder.imageView1);
        Glide.with(MyApp.getContext()).load(split[1]).into(holder.imageView2);
        Glide.with(MyApp.getContext()).load(split[2]).into(holder.imageView3);
        Glide.with(MyApp.getContext()).load(split[1]).into(holder.imageView4);
        return view;
    }
    class ViewHolder{
        TextView textView1,textView2;
        ImageView imageView1,imageView2,imageView3,imageView4;
    }
}
