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

public class TwoReceivedAdapter extends RecyclerView.Adapter {

    private Context context;

    public TwoReceivedAdapter(Context context) {
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
        View view = View.inflate(context, R.layout.shoukuantwo, null);
        Two two = new Two(view);
        return two;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        AllOrderBean allOrderBean = list.get(i);
        Two two = (Two) viewHolder;

        String[] split = allOrderBean.getCommodityPic().split(",");
        two.showkuansdv.setImageURI(Uri.parse(split[0]));
        two.showkuanName.setText(allOrderBean.getCommodityName());
        two.showkuanPrice.setText("¥"+allOrderBean.getCommodityPrice());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //创建VH
    class Two extends RecyclerView.ViewHolder
    {

        public SimpleDraweeView showkuansdv;
        public TextView showkuanName;
        public TextView showkuanPrice;
        public Two(@NonNull View itemView) {
            super(itemView);
            showkuansdv = itemView.findViewById(R.id.showkuansdv);
            showkuanName = itemView.findViewById(R.id.showkuanName);
            showkuanPrice = itemView.findViewById(R.id.showkuanPrice);
        }
    }


}
