package com.example.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app.MyApp;
import com.example.bean.GoodsList;
import com.example.logindemo.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {
    private List<GoodsList.MlssBean.CommodityListBeanXX> list = new ArrayList<>();
    public void rxaddList(List<GoodsList.MlssBean.CommodityListBeanXX> user){
        if(user!=null){
            list.addAll(user);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mlss_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final GoodsList.MlssBean.CommodityListBeanXX commodityListBeanXX = list.get(position);
        holder.rx_title.setText(commodityListBeanXX.getCommodityName());
        holder.rx_price.setText("￥"+commodityListBeanXX.getPrice());
        Glide.with(MyApp.getContext()).load(commodityListBeanXX.getMasterPic()).into(holder.re_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlss.onItemClick(commodityListBeanXX.getCommodityId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final ImageView re_image;
        private final TextView rx_title;
        private final TextView rx_price;

        public MyViewHolder(View itemView) {
            super(itemView);
            re_image = itemView.findViewById(R.id.ml_image);
            rx_title = itemView.findViewById(R.id.ml_title);
            rx_price = itemView.findViewById(R.id.ml_price);
        }
    }
    private Mlss mlss;
    public interface Mlss{
        void onItemClick(int position);
    }
    public void setRxxpp(Mlss mlss){
        this.mlss = mlss;
    }
}
