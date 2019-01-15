package com.example.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.adapter.OneCompleteAdapter;
import com.example.bean.AllOrderUser;
import com.example.bean.Result;
import com.example.bean.User;
import com.example.core.BaseCall;
import com.example.exception.ApiException;
import com.example.logindemo.R;
import com.example.logindemo.greendao.DaoMaster;
import com.example.logindemo.greendao.DaoSession;
import com.example.logindemo.greendao.UserDao;
import com.example.presenter.ListPresenter;

import java.util.List;

public class AlreadyFragment extends Fragment {

    private long userId;
    private String sessionId;
    private OneCompleteAdapter oneCompleteAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.already_fragment, container, false);

        ListPresenter listPresenter = new ListPresenter(new ComCall());
        //数据库
        //数据库
        DaoSession daoSession = DaoMaster.newDevSession(getActivity(), UserDao.TABLENAME);
        //获取操作数据库
        UserDao userDao = daoSession.getUserDao();
        List<User> list = userDao.loadAll();
        for (int i = 0; i < list.size(); i++) {
            userId = list.get(i).getUserId();
            sessionId = list.get(i).getSessionId();
        }

        RecyclerView recyclerView = view.findViewById(R.id.ComRecycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        //设置适配器
        oneCompleteAdapter = new OneCompleteAdapter(getActivity());
        //设置适配器
        recyclerView.setAdapter(oneCompleteAdapter);

        //待评价
        listPresenter.request((int)userId,sessionId,"9","1","5");


        return view;
    }
    class ComCall implements BaseCall<Result> {

        @Override
        public void loadSuccess(Result data) {
            if(data.getStatus().equals("0000"))
            {
                List<AllOrderUser> orderList = (List<AllOrderUser>) data.getOrderList();
                //第一个添加数据
                oneCompleteAdapter.addItem(orderList);

                oneCompleteAdapter.notifyDataSetChanged();

            }
        }

        @Override
        public void loadError(ApiException throwable) {

        }
    }

}
