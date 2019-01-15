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

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.MyViewHolder> {
    private List<GoodsList.PzshBean.CommodityListBeanX> list = new ArrayList<>();
    public void rxaddList(List<GoodsList.PzshBean.CommodityListBeanX> user){
        if(user!=null){
            list.addAll(user);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pzsh_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final GoodsList.PzshBean.CommodityListBeanX commodityListBeanX = list.get(position);
        holder.rx_title.setText(commodityListBeanX.getCommodityName());
        holder.rx_price.setText("￥"+commodityListBeanX.getPrice());
        Glide.with(MyApp.getContext()).load(commodityListBeanX.getMasterPic()).into(holder.re_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pzsh.onItemClick(commodityListBeanX.getCommodityId());
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
            re_image = itemView.findViewById(R.id.pz_image);
            rx_title = itemView.findViewById(R.id.pz_title);
            rx_price = itemView.findViewById(R.id.pz_price);
        }
    }
    private Pzsh pzsh;
    public interface Pzsh{
        void onItemClick(int position);
    }
    public void setRxxpp(Pzsh pzsh){
        this.pzsh = pzsh;
    }
}
