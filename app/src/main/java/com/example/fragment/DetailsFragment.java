package com.example.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapter.MyDetailAdapter;
import com.example.bean.DetaileGoods;
import com.example.bean.Result;
import com.example.bean.ShoppingBean;
import com.example.bean.User;
import com.example.core.BaseCall;
import com.example.exception.ApiException;
import com.example.logindemo.R;
import com.example.logindemo.greendao.DaoMaster;
import com.example.logindemo.greendao.DaoSession;
import com.example.logindemo.greendao.ShoppingBeanDao;
import com.example.logindemo.greendao.UserDao;
import com.example.presenter.DetailsPresenter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailsFragment extends Fragment {
    private static final String TAG = "DetailsFragment";
    private ListView listView;
    private Banner banner;
    private MyDetailAdapter myDetailAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment, container, false);
        listView = view.findViewById(R.id.detail_list);
        banner = view.findViewById(R.id.detail_banner);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        String id = intent.getStringExtra("id");
        DaoSession daoSession = DaoMaster.newDevSession(getContext(), UserDao.TABLENAME);
        UserDao userDao = daoSession.getUserDao();
        List<User> list = userDao.loadAll();
        long userId = list.get(0).getUserId();
        String sessionId = list.get(0).getSessionId();
        new DetailsPresenter(new MyCall()).request((int)userId,sessionId,id);
        myDetailAdapter = new MyDetailAdapter();
        listView.setAdapter(myDetailAdapter);
    }
    class MyCall implements BaseCall<Result<DetaileGoods>>{

        @Override
        public void loadSuccess(Result<DetaileGoods> data) {
            if(data.getStatus().equals("0000")){
                DetaileGoods result = data.getResult();
                myDetailAdapter.addList(result);
                String picture = result.getPicture();
                String[] split = picture.split(",");
                banner.setImageLoader(new MyBanner());
                banner.setImages(Arrays.asList(split));
                banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
                banner.isAutoPlay(false);
                banner.start();
                myDetailAdapter.notifyDataSetChanged();
                ShoppingBean shoppingBean = new ShoppingBean(1,result.getCommodityId(),result.getCategoryName(),1,split[0],result.getPrice(),true,1);
                DaoSession daoSession = DaoMaster.newDevSession(getContext(), ShoppingBeanDao.TABLENAME);
                ShoppingBeanDao shoppingBeanDao = daoSession.getShoppingBeanDao();
                shoppingBeanDao.deleteAll();
                shoppingBeanDao.insertOrReplace(shoppingBean);
            }
        }

        @Override
        public void loadError(ApiException throwable) {

        }
    }
    class MyBanner extends ImageLoader{

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Uri uri = Uri.parse((String) path);
            imageView.setImageURI(uri);
        }

        @Override
        public ImageView createImageView(Context context) {
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
            return simpleDraweeView;
        }
    }
}
