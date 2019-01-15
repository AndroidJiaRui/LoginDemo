package com.example.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bean.ShoppingBean;
import com.example.logindemo.R;
import com.example.view.AddSubLayout;

import java.util.ArrayList;
import java.util.List;

public class ShoppingTrolleyAdapter extends RecyclerView.Adapter<ShoppingTrolleyAdapter.ViewHolder> {
    private Context context;
    List<ShoppingBean> list;
    List<ShoppingBean> lists = new ArrayList<>();
    //接口
    private TotalPriceListener totalPriceListener;
    private Close close;

    public void setClose(Close close) {
        this.close = close;
    }

    public void setTotalPriceListener(TotalPriceListener totalPriceListener) {
        this.totalPriceListener = totalPriceListener;
    }

    public ShoppingTrolleyAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shopcart_child, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final ShoppingBean shoppingBean = list.get(i);
        //设置内容
        viewHolder.texta.setText(shoppingBean.getCommodityName());
        //设置单价
        viewHolder.pricea.setText("￥" + shoppingBean.getPrice());
        viewHolder.checkBoxa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                shoppingBean.setSelected(isChecked ? 1 : 0);
                sum();//选中之后调用计算价格方法
            }
        });
        //如果商品为选中状态（1）则为true
        if (shoppingBean.getSelected() == 1) {
            viewHolder.checkBoxa.setChecked(true);
        } else {
            viewHolder.checkBoxa.setChecked(false);
        }
        //设置图片
        String pic = shoppingBean.getPic();
        Glide.with(context).load(pic).into(viewHolder.imageViewa);

        //设置商品数量
        viewHolder.addSub.setCount(shoppingBean.getCount());
        viewHolder.addSub.setAddSubListener(new AddSubLayout.AddSubListener() {
            @Override
            public void addSub(int count) {
                shoppingBean.setCount(count);
                //计算价格
                sum();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(List<ShoppingBean> result) {
        if (result != null) {
            list.addAll(result);
        }
    }

    public void remove() {
        list.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBoxa;
        public ImageView imageViewa;
        public TextView texta;
        public TextView pricea;
        AddSubLayout addSub;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxa = itemView.findViewById(R.id.checkbox3);
            imageViewa = itemView.findViewById(R.id.imageViewa);
            texta = itemView.findViewById(R.id.texta);
            pricea = itemView.findViewById(R.id.pricea);
            addSub = itemView.findViewById(R.id.add_sub_layout);
        }
    }

    //全部选中或者全部取消
    public void checkAll(boolean isCheck) {
        for (int i = 0; i < list.size(); i++) {
            ShoppingBean shoppingBean = list.get(i);
            shoppingBean.setCheck(isCheck);
            //1代表为全部选中
            shoppingBean.setSelected(isCheck ? 1 : 0);

        }
        //刷新适配器
        notifyDataSetChanged();
        //选中之后计算总价方法
        sum();
    }

    public List<ShoppingBean> close(){
        lists.clear();
        for (int i = 0; i < list.size(); i++) {
            ShoppingBean shoppingBean = list.get(i);
            if (shoppingBean.getSelected()==1){
                lists.add(shoppingBean);
            }
        }
        return lists;
    }


    //计算总价格
    private void sum() {
        double totalPrice = 0;
        //循环商品
        for (int i = 0; i < list.size(); i++) {
            ShoppingBean shoppingBean = list.get(i);
            //如果是选中状态才能获取价格（1,是选中状态,0是未选中状态）
            if (shoppingBean.getSelected() == 1) {
                //价钱乘以数量得到总价格
                //叠加
                totalPrice = totalPrice + shoppingBean.getPrice() * shoppingBean.getCount();
            }

        }
        //给总价格接口设置值
        if (totalPriceListener != null) {
            totalPriceListener.totalPrice(totalPrice);
        }
    }

    //内部类接口
    public interface TotalPriceListener {
        void totalPrice(double totalPrice);
    }
    public interface Close{
        void close(ShoppingBean shoppingBean);
    }
}
