package com.example.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapter.MyXQAdapter;
import com.example.bean.DetaileGoods;
import com.example.bean.Result;
import com.example.bean.User;
import com.example.core.BaseCall;
import com.example.exception.ApiException;
import com.example.logindemo.R;
import com.example.logindemo.greendao.DaoMaster;
import com.example.logindemo.greendao.DaoSession;
import com.example.logindemo.greendao.UserDao;
import com.example.presenter.DetailsPresenter;
import com.youth.banner.BannerConfig;

import java.util.Arrays;
import java.util.List;


public class XQFragment extends Fragment {

    private ListView listView;
    private MyXQAdapter myXQAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xq, container, false);
        listView = view.findViewById(R.id.xq_list);
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
        myXQAdapter = new MyXQAdapter();
        listView.setAdapter(myXQAdapter);
    }
    class MyCall implements BaseCall<Result<DetaileGoods>> {

        @Override
        public void loadSuccess(Result<DetaileGoods> data) {
            if(data.getStatus().equals("0000")){
                Toast.makeText(getContext(), "11111", Toast.LENGTH_SHORT).show();
                DetaileGoods result = data.getResult();
                myXQAdapter.addList(result);
                myXQAdapter.notifyDataSetChanged();

            }
        }

        @Override
        public void loadError(ApiException throwable) {

        }
    }
}
