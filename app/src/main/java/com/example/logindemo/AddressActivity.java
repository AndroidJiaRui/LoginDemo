package com.example.logindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.MyAddressAdapter;
import com.example.bean.AddressBean;
import com.example.bean.Result;
import com.example.bean.User;
import com.example.core.BaseCall;
import com.example.exception.ApiException;
import com.example.logindemo.greendao.DaoMaster;
import com.example.logindemo.greendao.DaoSession;
import com.example.logindemo.greendao.UserDao;
import com.example.presenter.AddressPreseter;
import com.example.presenter.MorenPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressActivity extends AppCompatActivity {

    @BindView(R.id.address_add)
    Button addressAdd;
    @BindView(R.id.address_recy)
    RecyclerView addressRecy;
    @BindView(R.id.address_finish)
    TextView addressFinish;
    private MyAddressAdapter myAddressAdapter;
    private int a;
    private long userId;
    private String sessionId;
    private static final String TAG = "AddressActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        DaoSession daoSession = DaoMaster.newDevSession(this, UserDao.TABLENAME);
        UserDao userDao = daoSession.getUserDao();
        List<User> list = userDao.loadAll();
        userId = list.get(0).getUserId();
        sessionId = list.get(0).getSessionId();
        new AddressPreseter(new MyCall()).request((int) userId, sessionId);
        myAddressAdapter = new MyAddressAdapter();
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        addressRecy.setLayoutManager(manager);
        addressRecy.setAdapter(myAddressAdapter);
        myAddressAdapter.setOnItemclicks(new MyAddressAdapter.OnItemclick() {
            @Override
            public void onItem(int position) {
                a = position;
                Log.d(TAG, "onItem: "+a+"");
            }
        });

    }

    @OnClick({R.id.address_finish, R.id.address_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.address_finish:
                new MorenPresenter(new My()).request((int) userId, sessionId,a);
                finish();
                break;
            case R.id.address_add:
                Intent intent = new Intent(this, NewAddressActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }


    class MyCall implements BaseCall<Result<List<AddressBean>>> {

        @Override
        public void loadSuccess(Result<List<AddressBean>> data) {
            List<AddressBean> result = data.getResult();
            myAddressAdapter.addList(result);
            myAddressAdapter.notifyDataSetChanged();
        }

        @Override
        public void loadError(ApiException throwable) {

        }
    }
    class My implements BaseCall<Result> {

        @Override
        public void loadSuccess(Result data) {
            if(data.getStatus().equals("0000")){
                Toast.makeText(AddressActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void loadError(ApiException throwable) {

        }
    }
}
