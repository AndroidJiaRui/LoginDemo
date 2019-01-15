package com.example.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bean.ShoppingBean;
import com.example.logindemo.R;
import com.example.view.ShoppingItmeLayout;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class MyFragment3Adapter extends RecyclerView.Adapter<MyFragment3Adapter.MyViewHolder> {
    private List<ShoppingBean> list = new ArrayList<>();
    public void addList(List<ShoppingBean> u){
        if(u!=null){
            list.clear();
            list.addAll(u);
        }
    }
    public void clearList(){
        list.clear();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_fragment3, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.checkBox.setChecked(list.get(position).isCheck());
        //Button add= holder.shoppingItmeLayout.findViewById(R.id.shopping_itme_add);
        //Button reduce= holder.shoppingItmeLayout.findViewById(R.id.shopping_itme_reduce);
        TextView count= holder.shoppingItmeLayout.findViewById(R.id.shopping_itme_count);
        SimpleDraweeView image= holder.shoppingItmeLayout.findViewById(R.id.shopping_itme_image);
        TextView title= holder.shoppingItmeLayout.findViewById(R.id.shopping_itme_title);
        TextView price= holder.shoppingItmeLayout.findViewById(R.id.shopping_itme_price);
        count.setText(String.valueOf(list.get(position).getCount()));
        title.setText(list.get(position).getCommodityName());
        price.setText(String.valueOf(list.get(position).getPrice()));
        image.setImageURI(list.get(position).getPic());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickListener.click(list.get(position).getCommodityId());

            }
        });
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.get(position).setCheck(holder.checkBox.isChecked());

                checkClickListener.checkClick();

            }
        });
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                list.get(position).setCount(list.get(position).getCount()+1);
//                if (holder.checkBox.isChecked()){
//                    checkClickListener.checkClick();
//                }
//                notifyDataSetChanged();
//            }
//        });
//        reduce.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if ( 1 == list.get(position).getCount()) return;
//                list.get(position).setCount(list.get(position).getCount()-1);
//                if (holder.checkBox.isChecked()){
//                    checkClickListener.checkClick();
//
//                }
//                notifyDataSetChanged();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final CheckBox checkBox;
        private final ShoppingItmeLayout shoppingItmeLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox= itemView.findViewById(R.id.shopping_itme_check);
            shoppingItmeLayout= itemView.findViewById(R.id.shopping_layout);
        }
    }
    private ClickListener clickListener;
    public interface ClickListener{
        void click(int id);
    }
    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }
    public interface CheckClickListener{
        void checkClick();
    }
    private CheckClickListener checkClickListener;
    public void setCheckClickListener(CheckClickListener checkClickListener){
        this.checkClickListener = checkClickListener;
    }
}
