package com.example.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.bean.AddressBean;
import com.example.logindemo.R;

import java.util.ArrayList;
import java.util.List;

public class MyAddressAdapter extends RecyclerView.Adapter<MyAddressAdapter.MyViewHolder> {
    private List<AddressBean> list = new ArrayList<>();
    public void addList(List<AddressBean> u){
        if(u!=null){
            list.addAll(u);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_address, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.a_name.setText(list.get(position).getRealName());
        holder.a_phone.setText(list.get(position).getPhone());
        holder.a_address.setText(list.get(position).getAddress());
        holder.radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = list.get(position).getId();
                onItemclick.onItem(id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView a_name;
        private final TextView a_phone;
        private final TextView a_address;
        private final Button btn_update;
        private final Button btn_delete;
        private final RadioButton radio;

        public MyViewHolder(View itemView) {
            super(itemView);
            a_name = itemView.findViewById(R.id.address_name);
            a_phone = itemView.findViewById(R.id.address_phone);
            a_address = itemView.findViewById(R.id.address_address);
            btn_update = itemView.findViewById(R.id.address_update);
            btn_delete = itemView.findViewById(R.id.address_delete);
            radio = itemView.findViewById(R.id.address_radio);
        }
    }
    private OnItemclick onItemclick;
    public interface OnItemclick{
        void onItem(int position);
    }
    public void setOnItemclicks(OnItemclick onItemclick){
        this.onItemclick = onItemclick;
    }
}
