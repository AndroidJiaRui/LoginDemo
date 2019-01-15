package com.example.logindemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.Result;
import com.example.bean.User;
import com.example.bean.UserInfo;
import com.example.core.BaseCall;
import com.example.exception.ApiException;
import com.example.logindemo.greendao.DaoMaster;
import com.example.logindemo.greendao.DaoSession;
import com.example.logindemo.greendao.UserDao;
import com.example.logindemo.greendao.UserInfoDao;
import com.example.presenter.MyPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.login_name)
    EditText loginName;
    @BindView(R.id.login_pwd)
    EditText loginPwd;
    @BindView(R.id.login_check)
    CheckBox loginCheck;
    @BindView(R.id.login_regiest)
    TextView loginRegiest;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.login_phone)
    ImageView loginPhone;
    @BindView(R.id.login_lock)
    ImageView loginLock;
    @BindView(R.id.login_eyes)
    ImageView loginEyes;
    private MyPresenter myPresenter;
    private int i = 0;
    private UserDao userDao;
    //    private UserInfoDao userInfoDao;
//    private List<UserInfo> userInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        myPresenter = new MyPresenter(new RegiestCall());
        DaoSession daoSession = DaoMaster.newDevSession(this, UserDao.TABLENAME);
        userDao = daoSession.getUserDao();
//        DaoSession daoSession = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME);
//        userInfoDao = daoSession.getUserInfoDao();
    }

    @OnClick({ R.id.login_regiest, R.id.btn_login, R.id.login_eyes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_regiest:
                Intent intent = new Intent(MainActivity.this, RegiestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                String name = loginName.getText().toString();
                String pwd = loginPwd.getText().toString();
                myPresenter.request(name, pwd);
                break;
            case R.id.login_eyes:
                if (loginPwd.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)) {
                    loginPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                } else {
                    loginPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                break;

        }
    }

    class RegiestCall implements BaseCall<Result<User>> {

        @Override
        public void loadSuccess(Result<User> data) {
            //Toast.makeText(MainActivity.this, "11111", Toast.LENGTH_SHORT).show();
            if(data.getStatus().equals("0000")){
               // Toast.makeText(MainActivity.this, "2222", Toast.LENGTH_SHORT).show();
                boolean checked = loginCheck.isChecked();
                    String name = loginName.getText().toString();
                    String pwd = loginPwd.getText().toString();
                    //UserInfo userInfo = new UserInfo(System.currentTimeMillis(),name,pwd,1);
                    User result = data.getResult();
                    userDao.insertOrReplace(result);

                Intent intent = new Intent(MainActivity.this,GoodsActivity.class);
                startActivity(intent);
                finish();
            }
        }

        @Override
        public void loadError(ApiException throwable) {
            Toast.makeText(MainActivity.this, throwable.getDisplayMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
