package com.example.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adapter.MyPLAdapter;
import com.example.bean.PJGoods;
import com.example.bean.Result;
import com.example.core.BaseCall;
import com.example.exception.ApiException;
import com.example.logindemo.R;
import com.example.presenter.PJPresenter;

import java.util.List;

public class PLFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyPLAdapter myPLAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pl, container, false);
        recyclerView = view.findViewById(R.id.pl_recy);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        String id = intent.getStringExtra("id");
        new PJPresenter(new MyCall()).request(id,"1","5");
        myPLAdapter = new MyPLAdapter();
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(myPLAdapter);
    }
    class MyCall implements BaseCall<Result<List<PJGoods>>> {

        @Override
        public void loadSuccess(Result<List<PJGoods>> data) {
            List<PJGoods> result = data.getResult();
            myPLAdapter.addLsit(result);
            myPLAdapter.notifyDataSetChanged();
        }

        @Override
        public void loadError(ApiException throwable) {

        }
    }
}
