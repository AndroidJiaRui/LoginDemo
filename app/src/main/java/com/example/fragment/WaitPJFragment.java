package com.example.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.adapter.OneEvaluateAdapter;
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

public class WaitPJFragment extends Fragment {

    private OneEvaluateAdapter oneEvaluateAdapter;
    private long userId;
    private String sessionId;
    private ListPresenter listPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.waitpj_fragment, container, false);

        listPresenter = new ListPresenter(new EvaluateCall());

        //数据库
        DaoSession daoSession = DaoMaster.newDevSession(getActivity(), UserDao.TABLENAME);
        //获取操作数据库
        UserDao userDao = daoSession.getUserDao();
        List<User> list = userDao.loadAll();
        for (int i = 0; i < list.size(); i++) {
            userId = list.get(i).getUserId();
            sessionId = list.get(i).getSessionId();
        }

        RecyclerView recyclerView = view.findViewById(R.id.evaluateRecycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        //设置适配器
        oneEvaluateAdapter = new OneEvaluateAdapter(getActivity());
        //设置适配器
        recyclerView.setAdapter(oneEvaluateAdapter);

        //待评价
        listPresenter.request((int)userId,sessionId,"3","1","5");

        return view;
    }

    class EvaluateCall implements BaseCall<Result>{

        @Override
        public void loadSuccess(Result data) {
            if(data.getStatus().equals("0000"))
            {
                List<AllOrderUser> orderList = (List<AllOrderUser>) data.getOrderList();
                //Toast.makeText(getActivity(), ""+orderList.size(), Toast.LENGTH_SHORT).show();
                //第一个添加数据
                oneEvaluateAdapter.addItem(orderList);

                oneEvaluateAdapter.notifyDataSetChanged();

            }
        }

        @Override
        public void loadError(ApiException throwable) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        listPresenter.request((int)userId,sessionId,"3","1","5");
    }
}
