package com.example.logindemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bean.User;
import com.example.logindemo.greendao.DaoMaster;
import com.example.logindemo.greendao.DaoSession;
import com.example.logindemo.greendao.UserDao;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyActivity extends AppCompatActivity {
    @BindView(R.id.mSDv_Image)
    SimpleDraweeView mSDvImage;
    @BindView(R.id.mTv_Name_Info)
    TextView mTvNameInfo;
    @BindView(R.id.mTv_Pwd_Info)
    TextView mTvPwdInfo;
    @BindView(R.id.my_tui)
    Button myTui;
    @BindView(R.id.my_bianji)
    TextView myBianji;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine__info_);
        ButterKnife.bind(this);
        DaoSession daoSession = DaoMaster.newDevSession(this, UserDao.TABLENAME);
        UserDao userDao = daoSession.getUserDao();
        List<User> list = userDao.loadAll();
        mSDvImage.setImageURI(Uri.parse(list.get(0).getHeadPic()));
        mTvNameInfo.setText(list.get(0).getNickName());
        mTvPwdInfo.setText(list.get(0).getSessionId());
    }

    @OnClick(R.id.my_tui)
    public void onViewClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您确定要退出当前用户吗?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DaoSession daoSession = DaoMaster.newDevSession(MyActivity.this, UserDao.TABLENAME);
                UserDao userDao = daoSession.getUserDao();
                userDao.deleteAll();
                Intent intent = new Intent(MyActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();

    }

    @OnClick(R.id.my_bianji)
    public void onViewClicked(View view) {
        Intent intent = new Intent(MyActivity.this,BJActivity.class);
        startActivity(intent);
    }
}
