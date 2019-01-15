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

import java.util.ArrayList;
import java.util.List;

public class TwoCompleteAdapter extends RecyclerView.Adapter {

    private Context context;

    public TwoCompleteAdapter(Context context) {
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
        View view = View.inflate(context, R.layout.complete, null);
        Two two = new Two(view);
        return two;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        AllOrderBean allOrderBean = list.get(i);
        Two two = (Two) viewHolder;

        String[] split = allOrderBean.getCommodityPic().split(",");
        two.wanchengsdv.setImageURI(Uri.parse(split[0]));
        two.wanchengName.setText(allOrderBean.getCommodityName());
        two.wanchengPrice.setText("¥"+allOrderBean.getCommodityPrice());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //创建VH
    class Two extends RecyclerView.ViewHolder
    {

        public SimpleDraweeView wanchengsdv;
        public TextView wanchengName;
        public TextView wanchengPrice;
        public Two(@NonNull View itemView) {
            super(itemView);
            wanchengsdv = itemView.findViewById(R.id.wanchengsdv);
            wanchengName = itemView.findViewById(R.id.wanchengName);
            wanchengPrice = itemView.findViewById(R.id.wanchengPrice);
        }
    }


}
