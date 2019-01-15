package com.example.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bean.CircleBean;
import com.example.bean.FootBean;
import com.example.logindemo.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyFootAdapter extends RecyclerView.Adapter<MyFootAdapter.MyViewHolder> {
    private List<FootBean> list = new ArrayList<>();
    public void addList(List<FootBean> u){
        if(u!=null){
            list.addAll(u);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_foot, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.simpleDraweeView.setImageURI(list.get(position).getMasterPic());
        holder.text_title.setText(list.get(position).getCommodityName()+"");
        holder.text_price.setText("￥"+list.get(position).getPrice());
        holder.text_number.setText("已浏览"+list.get(position).getBrowseNum()+"次");
        long time = list.get(position).getBrowseTime();
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = formatter.format(currentTime);
        holder.text_time.setText(dateString);

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
        private final TextView text_time;

        public MyViewHolder(View itemView) {
            super(itemView);
            simpleDraweeView = itemView.findViewById(R.id.foot_image);
            text_title = itemView.findViewById(R.id.foot_title);
            text_price = itemView.findViewById(R.id.foot_price);
            text_number = itemView.findViewById(R.id.foot_number);
            text_time = itemView.findViewById(R.id.foot_time);

        }
    }
}
