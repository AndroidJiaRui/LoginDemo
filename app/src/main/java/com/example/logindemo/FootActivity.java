package com.example.logindemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.adapter.MyFootAdapter;
import com.example.bean.FootBean;
import com.example.bean.Result;
import com.example.bean.User;
import com.example.core.BaseCall;
import com.example.exception.ApiException;
import com.example.logindemo.greendao.DaoMaster;
import com.example.logindemo.greendao.DaoSession;
import com.example.logindemo.greendao.UserDao;
import com.example.presenter.FootPresenter;
import com.example.view.SpaceItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FootActivity extends AppCompatActivity {

    @BindView(R.id.foot_recy)
    RecyclerView footRecy;
    private MyFootAdapter myFootAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot);
        ButterKnife.bind(this);
        DaoSession daoSession = DaoMaster.newDevSession(this, UserDao.TABLENAME);
        UserDao userDao = daoSession.getUserDao();
        List<User> list = userDao.loadAll();
        long userId = list.get(0).getUserId();
        String sessionId = list.get(0).getSessionId();
        new FootPresenter(new MyCall()).request((int)userId,sessionId,1,10);
        myFootAdapter = new MyFootAdapter();
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        footRecy.setLayoutManager(manager);
        footRecy.setAdapter(myFootAdapter);
        footRecy.addItemDecoration(new SpaceItemDecoration(2));

    }
    class MyCall implements BaseCall<Result<List<FootBean>>>{

        @Override
        public void loadSuccess(Result<List<FootBean>> data) {
            List<FootBean> result = data.getResult();
            myFootAdapter.addList(result);
            myFootAdapter.notifyDataSetChanged();
        }

        @Override
        public void loadError(ApiException throwable) {

        }
    }
}
