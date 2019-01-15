package com.example.logindemo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bean.User;
import com.example.bean.UserInfo;
import com.example.logindemo.greendao.DaoMaster;
import com.example.logindemo.greendao.DaoSession;
import com.example.logindemo.greendao.UserDao;
import com.example.logindemo.greendao.UserInfoDao;

import java.util.List;

public class ShowActivity extends AppCompatActivity {

    private TextView textView;
    private int time = 3;
//    private UserInfoDao userInfoDao;
//    private List<UserInfo> userInfos;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                if(time>0){
                    time--;
                    handler.sendEmptyMessageDelayed(0,1000);
                }else{
                    if(list.size()!=0){
                        Intent intent = new Intent(ShowActivity.this,GoodsActivity.class);
                        startActivity(intent);
                        handler.removeCallbacksAndMessages(null);
                        finish();
                    }else{
                        Intent intent = new Intent(ShowActivity.this,MainActivity.class);
                        startActivity(intent);
                        handler.removeCallbacksAndMessages(null);
                        finish();
                    }
                }

            }
        }
    };
    private UserDao userDao;
    private List<User> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        textView = findViewById(R.id.show_text);
        //DaoSession daoSession = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME);
//        userInfoDao = daoSession.getUserInfoDao();
//        userInfos = userInfoDao.loadAll();
        DaoSession daoSession = DaoMaster.newDevSession(this, UserDao.TABLENAME);
        userDao = daoSession.getUserDao();
        list = userDao.loadAll();

        handler.sendEmptyMessageDelayed(0,1000);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.size()!=0){
                    Intent intent = new Intent(ShowActivity.this,GoodsActivity.class);
                    startActivity(intent);
                    handler.removeCallbacksAndMessages(null);
                    finish();
                }else{
                    Intent intent = new Intent(ShowActivity.this,MainActivity.class);
                    startActivity(intent);
                    handler.removeCallbacksAndMessages(null);
                    finish();
                }
            }
        });

    }
}
