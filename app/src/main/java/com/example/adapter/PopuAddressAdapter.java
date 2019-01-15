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

public class PopuAddressAdapter extends RecyclerView.Adapter<PopuAddressAdapter.MyViewHolder> {
    private List<AddressBean> list = new ArrayList<>();
    private CheckListener checkListener;

    public void addList(List<AddressBean> u){
        if(u!=null){
            list.addAll(u);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popu_address, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.a_name.setText(list.get(position).getRealName());
        holder.a_phone.setText(list.get(position).getPhone());
        holder.a_address.setText(list.get(position).getAddress());
        holder.a_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkListener.check(list.get(position));
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
        private final TextView a_ok;

        public MyViewHolder(View itemView) {
            super(itemView);
            a_name = itemView.findViewById(R.id.popu_address_name);
            a_phone = itemView.findViewById(R.id.popu_address_phone);
            a_address = itemView.findViewById(R.id.popu_address_address);
            a_ok = itemView.findViewById(R.id.popu_address_ok);
        }
    }
    public interface CheckListener{
        void check(AddressBean address);
    }
    public void setOnCheckListener(CheckListener checkListener){
        this.checkListener=checkListener;
    }

}
