package com.example.logindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.Result;
import com.example.bean.User;
import com.example.core.BaseCall;
import com.example.exception.ApiException;
import com.example.presenter.RegPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class RegiestActivity extends AppCompatActivity {

    @BindView(R.id.regiest_name)
    EditText regiestName;
    @BindView(R.id.safe_code)
    EditText safeCode;
    @BindView(R.id.get_safecode)
    TextView getSafecode;
    @BindView(R.id.regiest_pwd)
    EditText regiestPwd;
    @BindView(R.id.regiest_login)
    TextView regiestLogin;
    @BindView(R.id.btn_regiest)
    Button btnRegiest;
    @BindView(R.id.regiest_phone)
    ImageView regiestPhone;
    @BindView(R.id.regiest_safe)
    ImageView regiestSafe;
    @BindView(R.id.regiest_lock)
    ImageView regiestLock;
    @BindView(R.id.regiest_eyes)
    ImageView regiestEyes;
    private RegPresenter regPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiest);
        ButterKnife.bind(this);
        regPresenter = new RegPresenter(new MyCall());

    }

    @OnClick({R.id.get_safecode, R.id.regiest_login, R.id.btn_regiest, R.id.regiest_eyes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_safecode:
                safeCode.setText("1234");
                break;
            case R.id.regiest_login:
                finish();
                break;
            case R.id.btn_regiest:
                String name = regiestName.getText().toString();
                String pwd = regiestPwd.getText().toString();
                regPresenter.request(name, pwd);
                break;
            case R.id.regiest_eyes:
                if (regiestPwd.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)) {
                    regiestPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                } else {
                    regiestPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }

                break;
        }
    }

    class MyCall implements BaseCall<Result<User>> {


        @Override
        public void loadSuccess(Result<User> data) {
            Toast.makeText(RegiestActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegiestActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        @Override
        public void loadError(ApiException throwable) {
            Toast.makeText(RegiestActivity.this, throwable.getDisplayMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
