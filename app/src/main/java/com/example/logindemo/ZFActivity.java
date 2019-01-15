package com.example.logindemo;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.bean.Result;
import com.example.bean.User;
import com.example.core.BaseCall;
import com.example.exception.ApiException;
import com.example.logindemo.GoodsActivity;
import com.example.logindemo.R;
import com.example.logindemo.greendao.DaoMaster;
import com.example.logindemo.greendao.DaoSession;
import com.example.logindemo.greendao.UserDao;
import com.example.presenter.PayPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZFActivity extends AppCompatActivity {

    @BindView(R.id.pay_my)
    RadioButton payMy;
    @BindView(R.id.pay_wx)
    RadioButton payWx;
    @BindView(R.id.pay_wzb)
    RadioButton payWzb;
    @BindView(R.id.pay_but_ok)
    Button payButOk;
    @BindView(R.id.pay_image1)
    ImageView payImage1;
    @BindView(R.id.pay_image2)
    ImageView payImage2;
    @BindView(R.id.pay_image3)
    ImageView payImage3;
    private int way = 1;
    private PayPresenter payPresenter;
    private User userBean;
    private String orderId;
    private PopupWindow window_ok;
    private PopupWindow window_back;
    private View parent;
    private View inflate;
    private View inflate_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zf);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        double sum = intent.getDoubleExtra("sum", 0);
        DaoSession daoSession = DaoMaster.newDevSession(this, UserDao.TABLENAME);
        UserDao userBeanDao = daoSession.getUserDao();
        List<User> userBeans = userBeanDao.loadAll();
        userBean = userBeans.get(0);
        payButOk.setText("余额支付" + sum + "元");
        payPresenter = new PayPresenter(new PayWay());
        parent = View.inflate(this, R.layout.activity_zf, null);
        inflate = View.inflate(this, R.layout.popu_pay_layout, null);
        window_ok = new PopupWindow(inflate,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        window_ok.setFocusable(true);
        window_ok.setTouchable(true);
        window_ok.setOutsideTouchable(true);
        window_ok.setBackgroundDrawable(new BitmapDrawable());
        setWindow_ok();
        inflate_back = View.inflate(this, R.layout.popu_pay_back_layout, null);
        window_back = new PopupWindow(inflate_back,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        window_back.setFocusable(true);
        window_back.setTouchable(true);
        window_back.setOutsideTouchable(true);
        window_back.setBackgroundDrawable(new BitmapDrawable());
        getWindow_back();
        payMy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    payWx.setChecked(false);
                    payWzb.setChecked(false);
                    way = 1;
                }
            }
        });
        payWx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    payMy.setChecked(false);
                    payWzb.setChecked(false);
                    way = 2;
                }
            }
        });
        payWzb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    payWx.setChecked(false);
                    payMy.setChecked(false);
                    way = 3;
                }
            }
        });
    }

    private void getWindow_back() {
        Button popu_pay_back_finish = inflate_back.findViewById(R.id.popu_pay_back_finish);
        popu_pay_back_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window_back.dismiss();
            }
        });
    }

    private void setWindow_ok() {
        Button popu_pay_ok_back= inflate.findViewById(R.id.popu_pay_ok_back);
        Button popu_pay_ok_finish= inflate.findViewById(R.id.popu_pay_ok_finish);
        popu_pay_ok_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window_ok.dismiss();
                Intent intent = new Intent(ZFActivity.this,GoodsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        popu_pay_ok_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window_ok.dismiss();
                finish();
            }
        });
    }

    @OnClick(R.id.pay_but_ok)
    public void onViewClicked() {
        //Log.i("abc", "onViewClicked: "+way);
        switch (way){
            case 1:
                payPresenter.request(userBean.getUserId(),userBean.getSessionId(),orderId,way);
                break;
            case 2:
                window_back.showAtLocation(parent,Gravity.NO_GRAVITY,0,0);
                break;
            case 3:
                window_back.showAtLocation(parent,Gravity.NO_GRAVITY,0,0);
                break;
        }
    }
    class PayWay implements BaseCall<Result> {


        @Override
        public void loadSuccess(Result data) {
            if (data.getStatus().equals("0000")){
                window_ok.showAtLocation(parent,Gravity.NO_GRAVITY,0,0);
            }
            Toast.makeText(ZFActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void loadError(ApiException throwable) {

        }
    }
}
