package com.example.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.bean.AllOrderBean;
import com.example.bean.AllOrderUser;
import com.example.logindemo.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OneCompleteAdapter extends RecyclerView.Adapter {

    private Context context;

    public OneCompleteAdapter(Context context) {
        this.context = context;
    }
    private ArrayList<AllOrderUser> list = new ArrayList<>();
    //
    public void addItem(List<AllOrderUser> orderList) {
        if(orderList!=null)
        {
            list.addAll(orderList);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.pingjia_one, null);
        One one = new One(view);
        return one;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        AllOrderUser allOrderUser = list.get(i);
        List<AllOrderBean> detailList = (List<AllOrderBean>) allOrderUser.getDetailList();
        One one = (One) viewHolder;
        one.ordernum3.setText(allOrderUser.getOrderId());
        Log.e("=========订单Id=========",allOrderUser.getOrderId());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(allOrderUser.getPayMethod());
        one.pingjiaTime.setText(simpleDateFormat.format(date));

        //全部订单
        LinearLayoutManager linearLayoutManager0 = new LinearLayoutManager(context);
        one.oderrecy4.setLayoutManager(linearLayoutManager0);
        TwoCompleteAdapter twoCompleteAdapter = new TwoCompleteAdapter(context);
        //第二个添加数据
        twoCompleteAdapter.addItem(detailList);
        one.oderrecy4.setAdapter(twoCompleteAdapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //创建VH
    class One extends RecyclerView.ViewHolder
    {

        public TextView ordernum3;
        public TextView pingjiaTime;
        public RecyclerView oderrecy4;
        public One(@NonNull View itemView) {
            super(itemView);
            ordernum3 = itemView.findViewById(R.id.ordernum3);
            pingjiaTime = itemView.findViewById(R.id.pingjiaTime);
            oderrecy4 = itemView.findViewById(R.id.oderrecy4);
        }
    }


}
