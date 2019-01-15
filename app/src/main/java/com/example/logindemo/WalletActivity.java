package com.example.logindemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;

import com.example.adapter.MyWalletAdapter;
import com.example.bean.Result;
import com.example.bean.User;
import com.example.bean.UserWallet;
import com.example.bean.WalletBean;
import com.example.core.BaseCall;
import com.example.exception.ApiException;
import com.example.logindemo.greendao.DaoMaster;
import com.example.logindemo.greendao.DaoSession;
import com.example.logindemo.greendao.UserDao;
import com.example.presenter.WalletPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletActivity extends AppCompatActivity {

    @BindView(R.id.money_text)
    TextView moneyText;
    @BindView(R.id.mallet_recycle)
    RecyclerView malletRecycle;
    private MyWalletAdapter myWalletAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        ButterKnife.bind(this);
        DaoSession daoSession = DaoMaster.newDevSession(this, UserDao.TABLENAME);
        UserDao userDao = daoSession.getUserDao();
        List<User> list = userDao.loadAll();
        long userId = list.get(0).getUserId();
        String sessionId = list.get(0).getSessionId();
        new WalletPresenter(new MyCall()).request((int)userId,sessionId,1,10);
        myWalletAdapter = new MyWalletAdapter();
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        malletRecycle.setLayoutManager(manager);
        malletRecycle.setAdapter(myWalletAdapter);

    }
    class MyCall implements BaseCall<Result<UserWallet>>{

        @Override
        public void loadSuccess(Result<UserWallet> data) {
            UserWallet result = data.getResult();
            double balance = result.getBalance();
            moneyText.setText(balance+"");
            List<WalletBean> detailList = result.getDetailList();
            myWalletAdapter.addList(detailList);
            myWalletAdapter.notifyDataSetChanged();
        }

        @Override
        public void loadError(ApiException throwable) {

        }
    }
}
