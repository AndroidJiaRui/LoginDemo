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
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<GoodsList.RxxpBean.CommodityListBean> list = new ArrayList<>();
    public void rxaddList(List<GoodsList.RxxpBean.CommodityListBean> user){
        if(user!=null){
            list.addAll(user);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rxxp_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final GoodsList.RxxpBean.CommodityListBean commodityListBean = list.get(position);
        holder.rx_title.setText(commodityListBean.getCommodityName());
        holder.rx_price.setText("￥"+commodityListBean.getPrice());
        holder.rx_image.setImageURI(commodityListBean.getMasterPic());
        //Glide.with(MyApp.getContext()).load(commodityListBean.getMasterPic()).into(holder.re_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rxxp.onItemClick(commodityListBean.getCommodityId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView rx_title;
        private final TextView rx_price;
        private final SimpleDraweeView rx_image;

        public MyViewHolder(View itemView) {
            super(itemView);
            rx_image = itemView.findViewById(R.id.rx_image);
            rx_title = itemView.findViewById(R.id.rx_title);
            rx_price = itemView.findViewById(R.id.rx_price);
        }
    }
    private Rxxp rxxp;
    public interface Rxxp{
        void onItemClick(int position);
    }
    public void setRxxpp(Rxxp rxxpp){
        this.rxxp = rxxpp;
    }
}
