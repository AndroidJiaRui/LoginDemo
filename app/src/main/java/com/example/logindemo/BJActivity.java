package com.example.logindemo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.bean.User;
import com.example.logindemo.greendao.DaoMaster;
import com.example.logindemo.greendao.DaoSession;
import com.example.logindemo.greendao.UserDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BJActivity extends AppCompatActivity {

    @BindView(R.id.bj_name)
    EditText bjName;
    @BindView(R.id.bj_pwd)
    EditText bjPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bj);
        ButterKnife.bind(this);
        DaoSession daoSession = DaoMaster.newDevSession(this, UserDao.TABLENAME);
        UserDao userDao = daoSession.getUserDao();
        List<User> list = userDao.loadAll();
        String headPic = list.get(0).getHeadPic();
        bjName.setText(list.get(0).getNickName());
        bjPwd.setText(list.get(0).getUserId()+"");
    }
}
