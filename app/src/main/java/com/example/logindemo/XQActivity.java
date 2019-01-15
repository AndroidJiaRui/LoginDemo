package com.example.logindemo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.Result;
import com.example.bean.ShoppingBean;
import com.example.bean.User;
import com.example.bean.XQBean;
import com.example.core.BaseCall;
import com.example.exception.ApiException;
import com.example.fragment.DetailsFragment;
import com.example.fragment.PLFragment;
import com.example.fragment.XQFragment;
import com.example.logindemo.greendao.DaoMaster;
import com.example.logindemo.greendao.DaoSession;
import com.example.logindemo.greendao.ShoppingBeanDao;
import com.example.logindemo.greendao.UserDao;
import com.example.presenter.AddToCar;
import com.example.presenter.ShoppingPresenter;
import com.example.view.IdeaScrollView;
import com.example.view.IdeaViewPager;
import com.example.view.StatusBarCompat;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XQActivity extends AppCompatActivity {
    @BindView(R.id.x_fragment1)
    FrameLayout xFragment1;
    @BindView(R.id.x_fragment2)
    FrameLayout xFragment2;
    @BindView(R.id.x_fragment3)
    FrameLayout xFragment3;
    @BindView(R.id.xq_back)
    RadioButton xqBack;
    @BindView(R.id.xq_add)
    ImageView xqAdd;
    @BindView(R.id.xq_buy)
    ImageView xqBuy;
    private IdeaViewPager viewPager;
    private IdeaScrollView ideaScrollView;
    private TextView text;
    private LinearLayout header;
    private RadioGroup radioGroup;
    private LinearLayout headerParent;
    private ImageView icon;
    private View layer;
    private float currentPercentage = 0;
    private RadioGroup.OnCheckedChangeListener radioGroupListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                radioButton.setTextColor(radioButton.isChecked() ? getRadioCheckedAlphaColor(currentPercentage) : getRadioAlphaColor(currentPercentage));
                if (radioButton.isChecked() && isNeedScrollTo) {
                    ideaScrollView.setPosition(i);
                }
            }
        }
    };
    private boolean isNeedScrollTo = true;
    private List<XQBean> list2 = new ArrayList<>();
    ;
    private long userId;
    private String sessionId;
    private ShoppingBean shoppingBean;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xq);
        ButterKnife.bind(this);
        StatusBarCompat.translucentStatusBar(this);
        //header = (LinearLayout)findViewById(R.id.header);
        headerParent = (LinearLayout) findViewById(R.id.headerParent);
        //icon = (ImageView)findViewById(R.id.icon);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        ideaScrollView = (IdeaScrollView) findViewById(R.id.ideaScrollView);
        viewPager = (IdeaViewPager) findViewById(R.id.viewPager);
        layer = findViewById(R.id.layer);
        DaoSession daoSession = DaoMaster.newDevSession(this, UserDao.TABLENAME);
        UserDao userDao = daoSession.getUserDao();
        List<User> list = userDao.loadAll();
        userId = list.get(0).getUserId();
        sessionId = list.get(0).getSessionId();
        new ShoppingPresenter(new MyC()).request((int) userId, sessionId);

        Rect rectangle = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
        ideaScrollView.setViewPager(viewPager, getMeasureHeight(headerParent) - rectangle.top);
        //icon.setImageAlpha(0);
        radioGroup.setAlpha(0);
        radioGroup.check(radioGroup.getChildAt(0).getId());

        View one = findViewById(R.id.one);
        View two = findViewById(R.id.two);
        //View four = findViewById(R.id.four);
        View three = findViewById(R.id.three);
        ArrayList<Integer> araryDistance = new ArrayList<>();

        araryDistance.add(0);
        araryDistance.add(getMeasureHeight(one) - getMeasureHeight(headerParent));
        araryDistance.add(getMeasureHeight(one) + getMeasureHeight(two) - getMeasureHeight(headerParent));
        araryDistance.add(getMeasureHeight(one) + getMeasureHeight(two) + getMeasureHeight(three) - getMeasureHeight(headerParent));

        ideaScrollView.setArrayDistance(araryDistance);

        ideaScrollView.setOnScrollChangedColorListener(new IdeaScrollView.OnScrollChangedColorListener() {
            @Override
            public void onChanged(float percentage) {

                int color = getAlphaColor(percentage > 0.9f ? 1.0f : percentage);
                //header.setBackgroundDrawable(new ColorDrawable(color));
                radioGroup.setBackgroundDrawable(new ColorDrawable(color));
                //icon.setImageAlpha((int) ((percentage>0.9f?1.0f:percentage)*255));
                radioGroup.setAlpha((percentage > 0.9f ? 1.0f : percentage) * 255);

                setRadioButtonTextColor(percentage);

            }

            @Override
            public void onChangedFirstColor(float percentage) {

            }

            @Override
            public void onChangedSecondColor(float percentage) {

            }
        });

        ideaScrollView.setOnSelectedIndicateChangedListener(new IdeaScrollView.OnSelectedIndicateChangedListener() {
            @Override
            public void onSelectedChanged(int position) {
                isNeedScrollTo = false;
                radioGroup.check(radioGroup.getChildAt(position).getId());
                isNeedScrollTo = true;
            }
        });

        radioGroup.setOnCheckedChangeListener(radioGroupListener);
        DetailsFragment detailsFragment = new DetailsFragment();
        PLFragment plFragment = new PLFragment();
        XQFragment xqFragment = new XQFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.x_fragment1, detailsFragment).show(detailsFragment);
        transaction.add(R.id.x_fragment2, xqFragment).show(xqFragment);
        transaction.add(R.id.x_fragment3, plFragment).show(plFragment);
        transaction.commit();
        //new DetailsPresenter().request();
    }

    public void setRadioButtonTextColor(float percentage) {
        if (Math.abs(percentage - currentPercentage) >= 0.1f) {
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                radioButton.setTextColor(radioButton.isChecked() ? getRadioCheckedAlphaColor(percentage) : getRadioAlphaColor(percentage));
            }
            this.currentPercentage = percentage;
        }
    }

    public int getMeasureHeight(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        return view.getMeasuredHeight();
    }

    public int getAlphaColor(float f) {
        return Color.argb((int) (f * 255), 0x09, 0xc1, 0xf4);
    }

    public int getLayerAlphaColor(float f) {
        return Color.argb((int) (f * 255), 0x09, 0xc1, 0xf4);
    }

    public int getRadioCheckedAlphaColor(float f) {
        return Color.argb((int) (f * 255), 0x44, 0x44, 0x44);
    }

    public int getRadioAlphaColor(float f) {
        return Color.argb((int) (f * 255), 0xFF, 0xFF, 0xFF);

    }

    @OnClick({R.id.xq_back, R.id.xq_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.xq_back:
                finish();
                break;
            case R.id.xq_add:
                Intent intent = getIntent();
                String id = intent.getStringExtra("id");
                //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
                XQBean xqBean = new XQBean();
                xqBean.setCommodityId(Integer.parseInt(id));
                xqBean.setCount(1);
                list2.add(xqBean);
                Gson gson = new Gson();
                String s = gson.toJson(list2);
                new AddToCar(new MyCall()).request((int) userId, sessionId, s);
                break;
        }
    }

    @OnClick(R.id.xq_buy)
    public void onViewClicked() {
        Intent intent = new Intent(this,JSActivity.class);
        startActivity(intent);
    }

    class MyCall implements BaseCall<Result> {

        @Override
        public void loadSuccess(Result data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(XQActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void loadError(ApiException throwable) {

        }
    }

    class MyC implements BaseCall<Result<List<ShoppingBean>>> {

        @Override
        public void loadSuccess(Result<List<ShoppingBean>> data) {
            List<ShoppingBean> result = data.getResult();
            for (int i = 0; i < result.size(); i++) {
                shoppingBean = result.get(i);
                int commodityId = shoppingBean.getCommodityId();
                int count = shoppingBean.getCount();
                XQBean xqBean = new XQBean();
                xqBean.setCount(count);
                xqBean.setCommodityId(commodityId);
                list2.add(xqBean);
            }
        }

        @Override
        public void loadError(ApiException throwable) {

        }
    }
}
