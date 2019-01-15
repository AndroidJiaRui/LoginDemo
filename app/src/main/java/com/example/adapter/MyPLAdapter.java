package com.example.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.MyApp;
import com.example.bean.CircleBean;
import com.example.bean.PJGoods;
import com.example.logindemo.R;
import com.example.utils.StringUtils;
import com.example.view.MyRecycleGridView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MyPLAdapter extends RecyclerView.Adapter<MyPLAdapter.MyViewHolder> {
    private List<PJGoods> list = new ArrayList<>();
    public void addLsit(List<PJGoods> u){
        if(u!=null){
            list.addAll(u);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_pl, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PJGoods pjGoods = list.get(position);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
//获取当前时间
        Date date = new Date(pjGoods.getCreateTime());
        holder.simpleDraweeView.setImageURI(pjGoods.getHeadPic());
        holder.text_nick.setText(pjGoods.getNickName());
        holder.text_time.setText(simpleDateFormat.format(date)+"");
        holder.text_content.setText(pjGoods.getContent());
        if (StringUtils.isEmpty(pjGoods.getImage())){
            holder.myRecycleGridView.setVisibility(View.GONE);
        }else{
            holder.myRecycleGridView.setVisibility(View.VISIBLE);
            String[] images = pjGoods.getImage().split(",");
            int colNum;//列数
            if (images.length == 1){
                colNum = 1;
            }else if (images.length == 2||images.length == 4){
                colNum = 2;
            }else {
                colNum = 3;
            }
            holder.myRecycleGridView.setNumColumns(colNum);//设置列数
            holder.circleImageAdapter.clear();//清空
            holder.circleImageAdapter.addAll(Arrays.asList(images));
            holder.circleImageAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final SimpleDraweeView simpleDraweeView;
        private final TextView text_nick;
        private final TextView text_content;
        private final TextView text_time;
        private final MyRecycleGridView myRecycleGridView;
        private final CircleImageAdapter circleImageAdapter;

        public MyViewHolder(View itemView) {
            super(itemView);
            simpleDraweeView = itemView.findViewById(R.id.pl_header);
            text_nick = itemView.findViewById(R.id.pl_nickname);
            text_content = itemView.findViewById(R.id.pl_content);
            text_time = itemView.findViewById(R.id.pl_time);
            myRecycleGridView = itemView.findViewById(R.id.pl_grid_view);
            circleImageAdapter = new CircleImageAdapter();
            int space = MyApp.getContext().getResources().getDimensionPixelSize(R.dimen.dp_10);;//图片间距
            myRecycleGridView.setHorizontalSpacing(space);
            myRecycleGridView.setVerticalSpacing(space);
            myRecycleGridView.setAdapter(circleImageAdapter);

        }
    }
}
