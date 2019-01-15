package com.example.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapter.MyFragment2Adapter;
import com.example.bean.CircleBean;
import com.example.bean.Result;
import com.example.core.BaseCall;
import com.example.exception.ApiException;
import com.example.logindemo.R;
import com.example.presenter.CirclePresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

public class Fragment2 extends Fragment implements XRecyclerView.LoadingListener {

    public static boolean addCircle;
    private XRecyclerView list_view;
    private MyFragment2Adapter myFragment2Adapter;
    private static final String TAG = "Fragment2";
    private int page=1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        list_view = view.findViewById(R.id.fragment2_list_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new CirclePresenter(new MyCall()).request(page+"","20");
        list_view.setLoadingListener(this);
        myFragment2Adapter = new MyFragment2Adapter();
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        list_view.setLayoutManager(manager);
        list_view.setAdapter(myFragment2Adapter);
        ;

    }

    @Override
    public void onRefresh() {
        page = 1;
        myFragment2Adapter.removeAll();
        myFragment2Adapter.notifyDataSetChanged();
        new CirclePresenter(new MyCall()).request(page+"","20");


    }

    @Override
    public void onLoadMore() {
        page++;
        new CirclePresenter(new MyCall()).request(page+"","20");

    }

    class MyCall implements BaseCall<Result<List<CircleBean>>>{

        @Override
        public void loadSuccess(Result<List<CircleBean>> data) {
            if(data.getStatus().equals("0000")){
                List<CircleBean> result = data.getResult();
                Log.d(TAG, "loadSuccess: " +result.toString());
                myFragment2Adapter.addList(result);
                myFragment2Adapter.notifyDataSetChanged();
                list_view.refreshComplete();//停止刷新
                list_view.loadMoreComplete();//停止加载
            }
        }

        @Override
        public void loadError(ApiException throwable) {
            Toast.makeText(getContext(), throwable.getDisplayMessage()+"2222", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (addCircle){//publish new message,so you have to refresh
            addCircle = false;
        }
    }
}
