package com.example.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.logindemo.R;
import com.example.logindemo.WDActivity;
import com.example.utils.UIUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class SimImageAdapter extends RecyclerView.Adapter<SimImageAdapter.MyHodler> {
    private List<Object> mList = new ArrayList<>();
    private int sign;//0:普通点击，1:自定义
    public void addAll(List<Object> list) {
        mList.addAll(list);
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    @NonNull
    @Override
    public MyHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), R.layout.circle_image_item, null);
        return new MyHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHodler myHodler, final int position) {
        if (mList.get(position) instanceof String) {
            String imageUrl = (String) mList.get(position);
            if (imageUrl.contains("http:")) {
                myHodler.image.setImageURI(Uri.parse(imageUrl));
            } else {
                Uri uri = Uri.parse("file://" + imageUrl);
                myHodler.image.setImageURI(uri);
            }
        } else {
            int id = (int) mList.get(position);
            Uri uri = Uri.parse("res:///" + id);
            myHodler.image.setImageURI(uri);
        }
        myHodler.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sign == 1) {//自定义点击
                    if (position == 0) {
                        Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        WDActivity.getForegroundActivity().startActivityForResult(intent, WDActivity.PHOTO);
                    } else {
                        UIUtils.showToastSafe("点击了图片");
                    }
                }else{
                    UIUtils.showToastSafe("点击了图片");
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void clear() {
        mList.clear();
    }

    public void add(Object image) {
        if (image != null) {
            mList.add(image);
        }
    }

    class MyHodler extends RecyclerView.ViewHolder {
        SimpleDraweeView image;

        public MyHodler(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.circle_image);
        }
    }
    public List<Object> getList() {
        return mList;
    }
    private onClickListener onClickListener;

    public void setOnClickListener(SimImageAdapter.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface onClickListener {
        void onclick(int sign, int position);
    }
}
