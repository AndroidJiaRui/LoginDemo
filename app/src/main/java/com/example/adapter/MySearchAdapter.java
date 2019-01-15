package com.example.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bean.SearchGoods;
import com.example.logindemo.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class MySearchAdapter extends RecyclerView.Adapter<MySearchAdapter.MyViewHolder> {
    private List<SearchGoods> list = new ArrayList<>();
    public void addList(List<SearchGoods> u){
        if(u!=null){
            list.addAll(u);
        }
    }
    public void clear(){
        list.clear();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_search, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.simpleDraweeView.setImageURI(list.get(position).getMasterPic());
        holder.text_title.setText(list.get(position).getCommodityName()+"");
        holder.text_price.setText("￥"+list.get(position).getPrice());
        holder.text_number.setText("已售"+list.get(position).getSaleNum()+"件");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final SimpleDraweeView simpleDraweeView;
        private final TextView text_title;
        private final TextView text_price;
        private final TextView text_number;

        public MyViewHolder(View itemView) {
            super(itemView);
            simpleDraweeView = itemView.findViewById(R.id.search_image);
            text_title = itemView.findViewById(R.id.search_title);
            text_price = itemView.findViewById(R.id.search_price);
            text_number = itemView.findViewById(R.id.search_number);
        }
    }
}
