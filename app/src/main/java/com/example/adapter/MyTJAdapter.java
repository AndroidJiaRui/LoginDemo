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
import com.example.bean.ShoppingBean;
import com.example.logindemo.R;
import com.example.view.AddSubLayout;

import java.util.ArrayList;
import java.util.List;

public class MyTJAdapter extends RecyclerView.Adapter<MyTJAdapter.MyViewHolder> {
    private List<ShoppingBean> list = new ArrayList<>();
    public void addList(List<ShoppingBean> u){
        if(u!=null){
            list.addAll(u);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_tijiao, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ShoppingBean shoppingBean = list.get(position);
        //设置内容
        holder.text_name.setText(shoppingBean.getCommodityName());
        //设置单价
        holder.text_price.setText("￥" + shoppingBean.getPrice());
        Glide.with(MyApp.getContext()).load(shoppingBean.getPic()).into(holder.imageView);
        holder.addSubLayout.setCount(shoppingBean.getCount());
        holder.addSubLayout.setAddSubListener(new AddSubLayout.AddSubListener() {
            @Override
            public void addSub(int count) {
                shoppingBean.setCount(count);
                countListener.getCount(count);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final ImageView imageView;
        private final TextView text_name;
        private final TextView text_price;
        private final AddSubLayout addSubLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.tijiao_image);
            text_name = itemView.findViewById(R.id.tijiao_name);
            text_price = itemView.findViewById(R.id.tijiao_price);
            addSubLayout = itemView.findViewById(R.id.tijiao_add);
        }
    }
    private CountListener countListener;
    public interface CountListener{
        void getCount(int count);
    }
    public void setCountListener(CountListener countListener){
        this.countListener = countListener;
    }
}
