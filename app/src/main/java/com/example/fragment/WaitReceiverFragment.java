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


import com.example.adapter.OneReceivedAdapter;
import com.example.bean.AllOrderUser;
import com.example.bean.Result;
import com.example.bean.User;
import com.example.core.BaseCall;
import com.example.exception.ApiException;
import com.example.logindemo.R;
import com.example.logindemo.greendao.DaoMaster;
import com.example.logindemo.greendao.DaoSession;
import com.example.logindemo.greendao.UserDao;
import com.example.presenter.ConfirmReceiptPresenter;
import com.example.presenter.ListPresenter;

import java.util.List;

public class WaitReceiverFragment extends Fragment {

    private long userId;
    private String sessionId;
    private RecyclerView recyclerView;
    private OneReceivedAdapter oneReceivedAdapter;
    private ListPresenter listPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.waitreceiver_fragment, container, false);

        listPresenter = new ListPresenter(new ReceiCall());

        //数据库
        DaoSession daoSession = DaoMaster.newDevSession(getActivity(), UserDao.TABLENAME);
        //获取操作数据库
        UserDao userDao = daoSession.getUserDao();
        List<User> list = userDao.loadAll();
        for (int i = 0; i < list.size(); i++) {
            userId = list.get(i).getUserId();
            sessionId = list.get(i).getSessionId();
        }

        recyclerView = view.findViewById(R.id.receivedRecycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        //设置适配器
        oneReceivedAdapter = new OneReceivedAdapter(getActivity());
        //设置适配器
        recyclerView.setAdapter(oneReceivedAdapter);

        listPresenter.request((int)userId,sessionId,"2","1","5");
        oneReceivedAdapter.setOnItem(new OneReceivedAdapter.OnItem() {
            @Override
            public void cancelOrder(String orderId) {
                new ConfirmReceiptPresenter(new MyCall()).request((int)userId,sessionId,orderId);
            }
        });
        return view;
    }
    class MyCall implements BaseCall<Result> {

        @Override
        public void loadSuccess(Result data) {
            Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void loadError(ApiException throwable) {

        }
    }
    class ReceiCall implements BaseCall<Result>{

        @Override
        public void loadSuccess(Result data) {
            if(data.getStatus().equals("0000"))
            {
                List<AllOrderUser> orderList = (List<AllOrderUser>) data.getOrderList();

                //Toast.makeText(getActivity(), ""+orderList.size(), Toast.LENGTH_SHORT).show();
                //第一个添加数据
                oneReceivedAdapter.addItem(orderList);

                oneReceivedAdapter.notifyDataSetChanged();

            }
        }

        @Override
        public void loadError(ApiException throwable) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        listPresenter.request((int)userId,sessionId,"2","1","5");
    }
}
