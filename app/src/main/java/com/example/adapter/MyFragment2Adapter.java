package com.example.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app.MyApp;
import com.example.bean.CircleBean;
import com.example.logindemo.R;
import com.example.utils.StringUtils;
import com.example.view.MyRecycleGridView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MyFragment2Adapter extends RecyclerView.Adapter<MyFragment2Adapter.MyViewHolder> {
    private List<CircleBean> list = new ArrayList<>();


    public void addList(List<CircleBean> user){
        if(user!=null){
            list.addAll(user);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_circle, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final CircleBean circleBean = list.get(position);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
//获取当前时间
        Date date = new Date(circleBean.getCreateTime());
        holder.imagview1.setImageURI(circleBean.getHeadPic());
        holder.textView1.setText(circleBean.getNickName());
        holder.textView2.setText(simpleDateFormat.format(date)+"");
        holder.textView3.setText(circleBean.getContent());
        holder.textView4.setText(circleBean.getGreatNum()+"");
        if (StringUtils.isEmpty(circleBean.getImage())){
            holder.gridView.setVisibility(View.GONE);
        }else{
            holder.gridView.setVisibility(View.VISIBLE);
            String[] images = circleBean.getImage().split(",");
            //int imageCount = (int) (Math.random()*9)+1;
            int colNum;//列数
            if (images.length == 1){
                colNum = 1;
            }else if (images.length == 2||images.length == 4){
                colNum = 2;
            }else {
                colNum = 3;
            }
            holder.gridView.setNumColumns(colNum);//设置列数
            holder.circleImageAdapter.clear();//清空
            holder.circleImageAdapter.addAll(Arrays.asList(images));
            holder.circleImageAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeAll() {
        list.clear();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final SimpleDraweeView imagview1;
        private final CheckBox imageView3;
        private final TextView textView1;
        private final TextView textView2;
        private final TextView textView3;
        private final TextView textView4;
        private final MyRecycleGridView gridView;
        private CircleImageAdapter circleImageAdapter;

        public MyViewHolder(View itemView) {
            super(itemView);
            imagview1 = itemView.findViewById(R.id.fragment2_header);
            textView1 = itemView.findViewById(R.id.fragment2_nickname);
            textView2 = itemView.findViewById(R.id.fragment2_time);
            textView3 = itemView.findViewById(R.id.fragment2_content);
            imageView3 = itemView.findViewById(R.id.fragment2_zan);
            textView4 = itemView.findViewById(R.id.fragment2_number);
            gridView = itemView.findViewById(R.id.circle_grid_view);
            circleImageAdapter = new CircleImageAdapter();
            gridView.setAdapter(circleImageAdapter);

        }
    }


}
