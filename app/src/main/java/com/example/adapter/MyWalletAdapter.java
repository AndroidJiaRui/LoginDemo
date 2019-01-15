package com.example.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bean.WalletBean;
import com.example.logindemo.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyWalletAdapter extends RecyclerView.Adapter<MyWalletAdapter.MyViewHoder> {
    private List<WalletBean> list = new ArrayList();
    public void addList(List<WalletBean> a){
        if(a!=null){
            list.addAll(a);
        }
    }
    @NonNull
    @Override
    public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_wallet, parent, false);
        return new MyViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
        holder.text_price.setText(list.get(position).getAmount()+"");
        long time = list.get(position).getCreateTime();
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = formatter.format(currentTime);
        holder.text_time.setText(dateString);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHoder extends RecyclerView.ViewHolder{

        private final TextView text_price;
        private final TextView text_time;

        public MyViewHoder(View itemView) {
            super(itemView);
            text_price = itemView.findViewById(R.id.mywalletprice);
            text_time = itemView.findViewById(R.id.mywallettime);
        }
    }
}
