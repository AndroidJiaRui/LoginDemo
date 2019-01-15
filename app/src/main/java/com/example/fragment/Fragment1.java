package com.example.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.MyAdapter;
import com.example.adapter.MyAdapter2;
import com.example.adapter.MyAdapter3;
import com.example.bean.GoodsBanner;
import com.example.bean.GoodsList;
import com.example.bean.Result;
import com.example.core.BaseCall;
import com.example.exception.ApiException;
import com.example.logindemo.R;
import com.example.logindemo.SearchActivity;
import com.example.logindemo.XQActivity;
import com.example.presenter.BannerPresenter;
import com.example.presenter.GoodListPresenter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment{

    private Banner banner;
    private RecyclerView recy_view;
    private RecyclerView recy_view2;
    private RecyclerView rexy_view3;
    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        banner = view.findViewById(R.id.banner);
        recy_view = view.findViewById(R.id.rxcp);
        recy_view2 = view.findViewById(R.id.mlss);
        rexy_view3 = view.findViewById(R.id.pzsh);
        textView = view.findViewById(R.id.fragment1_edit);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new BannerPresenter(new MyCall()).request();
        new GoodListPresenter(new MyList()).request();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),SearchActivity.class);
                startActivity(intent);
            }
        });
    }
    class MyCall implements BaseCall<Result<List<GoodsBanner>>> {

        @Override
        public void loadSuccess(Result<List<GoodsBanner>> data) {
            if(data.getStatus().equals("0000")){
                List<String> list = new ArrayList<>();
                List<GoodsBanner> result = data.getResult();
                for (int i = 0; i <result.size()  ; i++) {
                    list.add(result.get(i).getImageUrl());
                }
                banner.setImages(list);
                banner.setImageLoader(new MyBanner());
                banner.start();
            }
        }

        @Override
        public void loadError(ApiException throwable) {
            Toast.makeText(getContext(), throwable.getDisplayMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    class MyList implements BaseCall<Result<GoodsList>>{


        @Override
        public void loadSuccess(Result<GoodsList> data) {
            if(data.getStatus().equals("0000")){
                List<GoodsList.RxxpBean> rxxp = data.getResult().getRxxp();
                List<GoodsList.RxxpBean.CommodityListBean> commodityList = rxxp.get(0).getCommodityList();
                List<GoodsList.MlssBean> mlss = data.getResult().getMlss();
                List<GoodsList.MlssBean.CommodityListBeanXX> commodityList1 = mlss.get(0).getCommodityList();
                List<GoodsList.PzshBean> pzsh = data.getResult().getPzsh();
                List<GoodsList.PzshBean.CommodityListBeanX> commodityList2 = pzsh.get(0).getCommodityList();

                MyAdapter adapter = new MyAdapter();
                MyAdapter2 adapter2 = new MyAdapter2();
                MyAdapter3 adapter3 = new MyAdapter3();

                adapter.rxaddList(commodityList);
                adapter2.rxaddList(commodityList1);
                adapter3.rxaddList(commodityList2);

                StaggeredGridLayoutManager rx = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                StaggeredGridLayoutManager ml = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                StaggeredGridLayoutManager pz = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

                recy_view.setLayoutManager(rx);
                recy_view.setAdapter(adapter);
                recy_view2.setLayoutManager(ml);
                recy_view2.setAdapter(adapter2);
                rexy_view3.setLayoutManager(pz);
                rexy_view3.setAdapter(adapter3);
                adapter.setRxxpp(new MyAdapter.Rxxp() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(getActivity(),XQActivity.class);
                        intent.putExtra("id",position+"");
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.addToBackStack(null);
                        startActivity(intent);
                    }
                });
                adapter2.setRxxpp(new MyAdapter2.Mlss() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(getActivity(),XQActivity.class);
                        intent.putExtra("id",position+"");
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.addToBackStack(null);
                        startActivity(intent);
                    }
                });
                adapter3.setRxxpp(new MyAdapter3.Pzsh() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(getActivity(),XQActivity.class);
                        intent.putExtra("id",position+"");
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.addToBackStack(null);
                        startActivity(intent);
                    }
                });
            }
        }

        @Override
        public void loadError(ApiException throwable) {
            Toast.makeText(getContext(), throwable.getDisplayMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    class MyBanner extends ImageLoader{

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Uri parse = Uri.parse((String) path);
            imageView.setImageURI(parse);
        }

        @Override
        public ImageView createImageView(Context context) {
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) View.inflate(getContext(),R.layout.banner_layout,null);
            return simpleDraweeView;

        }
    }

}
