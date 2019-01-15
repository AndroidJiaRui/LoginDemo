package com.example.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bean.AllOrderBean;
import com.example.bean.AllOrderUser;
import com.example.logindemo.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.http.POST;

public class OneReceivedAdapter extends RecyclerView.Adapter {

    private Context context;

    public OneReceivedAdapter(Context context) {
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
        View view = View.inflate(context, R.layout.shoukuanone, null);
        One one = new One(view);
        return one;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        AllOrderUser allOrderUser = list.get(i);
        final List<AllOrderBean> detailList = (List<AllOrderBean>) allOrderUser.getDetailList();
        One one = (One) viewHolder;
        one.ordernum.setText(allOrderUser.getOrderId());
        Log.e("=========订单Id=========",allOrderUser.getOrderId());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");// HH:mm:ss
        //获取当前时间
        Date date = new Date(allOrderUser.getPayMethod());
        one.orderdata2.setText(simpleDateFormat.format(date));
        one.kuaitigongsi.setText(allOrderUser.getExpressCompName());
        one.kuaididanhao.setText(allOrderUser.getOrderId());

        //全部订单
        LinearLayoutManager linearLayoutManager0 = new LinearLayoutManager(context);
        one.oderrecy2.setLayoutManager(linearLayoutManager0);
        TwoReceivedAdapter twoReceivedAdapter = new TwoReceivedAdapter(context);
        //第二个添加数据
        twoReceivedAdapter.addItem(detailList);
        one.oderrecy2.setAdapter(twoReceivedAdapter);
        one.oderpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItem.cancelOrder(list.get(i).getOrderId()+"");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    //创建VH
    class One extends RecyclerView.ViewHolder
    {

        public TextView ordernum;
        public TextView orderdata2;
        public TextView kuaitigongsi;
        public TextView kuaididanhao;
        public RecyclerView oderrecy2;
        public Button oderpay;
        public One(@NonNull View itemView) {
            super(itemView);
            ordernum = itemView.findViewById(R.id.ordernum2);
            orderdata2 = itemView.findViewById(R.id.orderdata2);
            kuaitigongsi = itemView.findViewById(R.id.kuaitigongsi);
            kuaididanhao = itemView.findViewById(R.id.kuaididanhao);
            oderrecy2 = itemView.findViewById(R.id.oderrecy2);
            oderpay = itemView.findViewById(R.id.oderpay);
        }
    }
    private OnItem onItem;
    public interface OnItem{
        void cancelOrder(String orderId);
    }
    public void setOnItem(OnItem onItem){
        this.onItem=onItem;
    }

}
