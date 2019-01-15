package com.example.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bean.AllOrderBean;
import com.example.logindemo.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class TwoEvaluateAdapter extends RecyclerView.Adapter {

    private Context context;

    public TwoEvaluateAdapter(Context context) {
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
        View view = View.inflate(context, R.layout.pingjia_two, null);
        Two two = new Two(view);
        return two;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        AllOrderBean allOrderBean = list.get(i);
        Two two = (Two) viewHolder;

        String[] split = allOrderBean.getCommodityPic().split(",");
        two.pingjiasdv.setImageURI(Uri.parse(split[0]));
        two.pingjiaName.setText(allOrderBean.getCommodityName());
        two.pingjiaPrice.setText("¥"+allOrderBean.getCommodityPrice());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //创建VH
    class Two extends RecyclerView.ViewHolder
    {

        public SimpleDraweeView pingjiasdv;
        public TextView pingjiaName;
        public TextView pingjiaPrice;
        public Two(@NonNull View itemView) {
            super(itemView);
            pingjiasdv = itemView.findViewById(R.id.pingjiasdv);
            pingjiaName = itemView.findViewById(R.id.pingjiaName);
            pingjiaPrice = itemView.findViewById(R.id.pingjiaPrice);
        }
    }


}
