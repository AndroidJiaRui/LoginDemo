package com.example.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bean.AllOrderBean;
import com.example.logindemo.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TwoPaymentAdapter extends RecyclerView.Adapter {

    private Context context;

    public TwoPaymentAdapter(Context context) {
        this.context = context;
    }
    private ArrayList<AllOrderBean> list = new ArrayList<>();

    public void addItem(List<AllOrderBean> detailList) {
        if(detailList!=null)
        {
            list.addAll(detailList);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.wallpaytwo_item, null);
        Two two = new Two(view);
        return two;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        AllOrderBean allOrderBean = list.get(i);
        Two two = (Two) viewHolder;

        String[] split = allOrderBean.getCommodityPic().split(",");
        two.Twopaymentsdv.setImageURI(Uri.parse(split[0]));
        two.TwopaymentName.setText(allOrderBean.getCommodityName());
        two.TwopaymentPrice.setText("¥"+allOrderBean.getCommodityPrice());


    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    //创建VH
    class Two extends RecyclerView.ViewHolder
    {

        public SimpleDraweeView Twopaymentsdv;
        public TextView TwopaymentName;
        public TextView TwopaymentPrice;
        public Two(@NonNull View itemView) {
            super(itemView);
            Twopaymentsdv = itemView.findViewById(R.id.Twopaymentsdv);
            TwopaymentName = itemView.findViewById(R.id.TwopaymentName);
            TwopaymentPrice = itemView.findViewById(R.id.TwopaymentPrice);
        }
    }
}
