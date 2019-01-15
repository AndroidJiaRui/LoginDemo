package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.app.MyApp;
import com.example.bean.AllOrderBean;
import com.example.bean.AllOrderUser;
import com.example.logindemo.R;
import com.example.logindemo.ZFActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OnePaymentAdapter extends RecyclerView.Adapter {

    private Context context;
    private double sum;

    public OnePaymentAdapter(Context context) {
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
        View view = View.inflate(context, R.layout.wallpayone_item, null);
        One one = new One(view);
        return one;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final AllOrderUser allOrderUser = list.get(i);
        List<AllOrderBean> detailList = (List<AllOrderBean>) allOrderUser.getDetailList();
        One one = (One) viewHolder;
        one.ordernum.setText(allOrderUser.getOrderId());
        Log.e("=========订单Id=========",allOrderUser.getOrderId());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");// HH:mm:ss
        //获取当前时间
        Date date = new Date(allOrderUser.getPayMethod());
        one.orderdata.setText(simpleDateFormat.format(date));

        //全部订单
        LinearLayoutManager linearLayoutManager0 = new LinearLayoutManager(context);
        one.oderrecy2.setLayoutManager(linearLayoutManager0);
        TwoPaymentAdapter twoPaymentAdapter = new TwoPaymentAdapter(context);
        //第二个添加数据
        twoPaymentAdapter.addItem(detailList);
        one.oderrecy2.setAdapter(twoPaymentAdapter);
        one.oderpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyApp.getContext(),ZFActivity.class);
                intent.putExtra("orderId",list.get(i).getOrderId());
                intent.putExtra("sum",sum);
                context.startActivity(intent);
            }
        });
        getSum(one,detailList);
        one.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItem.cancelOrder(allOrderUser.getOrderId());
            }
        });
    }
    private void getSum(@NonNull One vh,List<AllOrderBean> lists){
        int num=0;
        sum = 0;
        /*for (int i = 0; i < list.size(); i++) {
            num=0;
            sum =0;
            for (int j = 0; j < list.get(i).getDetailList().size(); j++) {
                num+=list.get(i).getDetailList().get(j).getCommodityCount();
                sum +=list.get(i).getDetailList().get(j).getCommodityCount()*list.get(i).getDetailList().get(j).getCommodityPrice();
            }
        }*/
        for (int i = 0; i < lists.size(); i++) {
            num+=lists.get(i).getCommodityCount();
            sum +=lists.get(i).getCommodityCount()*lists.get(i).getCommodityPrice();
        }

        vh.odercount.setText(num+"");
        vh.odersum.setText(sum +"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //创建VH
    class One extends RecyclerView.ViewHolder
    {

        public TextView ordernum;
        public TextView orderdata;
        public RecyclerView oderrecy2;
        private final Button oderpay;
        private final TextView odercount;
        private final TextView odersum;
        private final Button btn;

        public One(@NonNull View itemView) {
            super(itemView);
            ordernum = itemView.findViewById(R.id.ordernum);
            orderdata = itemView.findViewById(R.id.orderdata);
            odercount = itemView.findViewById(R.id.odercount);
            odersum = itemView.findViewById(R.id.odersum);
            oderrecy2 = itemView.findViewById(R.id.oderrecy2);
            oderpay = itemView.findViewById(R.id.oderpay);
            btn = itemView.findViewById(R.id.odercancel);
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
