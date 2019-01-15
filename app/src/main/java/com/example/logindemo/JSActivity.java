package com.example.logindemo;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.MyTJAdapter;
import com.example.adapter.PopuAddressAdapter;
import com.example.bean.AddressBean;
import com.example.bean.OrderInfo;
import com.example.bean.Result;
import com.example.bean.ShoppingBean;
import com.example.bean.User;
import com.example.bean.UserInfo;
import com.example.core.BaseCall;
import com.example.exception.ApiException;
import com.example.logindemo.greendao.DaoMaster;
import com.example.logindemo.greendao.DaoSession;
import com.example.logindemo.greendao.ShoppingBeanDao;
import com.example.logindemo.greendao.UserDao;
import com.example.presenter.AddressPreseter;
import com.example.presenter.CreateOrederPresenter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JSActivity extends AppCompatActivity {

    @BindView(R.id.ti_image)
    ImageView tiImage;
    @BindView(R.id.ti_recy)
    RecyclerView tiRecy;
    @BindView(R.id.shopping_num)
    TextView shoppingNum;
    @BindView(R.id.shopping_sum)
    TextView shoppingSum;
    @BindView(R.id.address_text_name)
    TextView addressTextName;
    @BindView(R.id.address_text_phone)
    TextView addressTextPhone;
    @BindView(R.id.address_text_address)
    TextView addressTextAddress;
    @BindView(R.id.tijiao)
    Button tijiao;
    private MyTJAdapter myTJAdapter;
    private View view;
    private RecyclerView recyclerView;
    private PopuAddressAdapter popuAddressAdapter;
    private PopupWindow popupWindow;
    private List<ShoppingBean> list;
    private long userId;
    private String sessionId;
    private double sum;
    private int id2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js);
        ButterKnife.bind(this);
        DaoSession daoSession = DaoMaster.newDevSession(this, ShoppingBeanDao.TABLENAME);
        ShoppingBeanDao shoppingBeanDao = daoSession.getShoppingBeanDao();
        list = shoppingBeanDao.loadAll();
        getSum();
        myTJAdapter = new MyTJAdapter();
        myTJAdapter.setCountListener(new MyTJAdapter.CountListener() {
            @Override
            public void getCount(int count) {
                getSum();
            }
        });
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        myTJAdapter.addList(list);
        tiRecy.setLayoutManager(manager);
        tiRecy.setAdapter(myTJAdapter);
        DaoSession daoSession1 = DaoMaster.newDevSession(this, UserDao.TABLENAME);
        UserDao userDao = daoSession1.getUserDao();
        List<User> list1 = userDao.loadAll();
        userId = list1.get(0).getUserId();
        sessionId = list1.get(0).getSessionId();
        view = View.inflate(this, R.layout.popu_address_layout, null);
        new AddressPreseter(new My()).request((int) userId, sessionId);
        recyclerView = view.findViewById(R.id.address_recycle_show);
        popuAddressAdapter = new PopuAddressAdapter();
        StaggeredGridLayoutManager manager1 = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager1);
        recyclerView.setAdapter(popuAddressAdapter);
        popuAddressAdapter.setOnCheckListener(new PopuAddressAdapter.CheckListener() {
            @Override
            public void check(AddressBean address) {
                String address1 = address.getAddress();
                String phone = address.getPhone();
                String realName = address.getRealName();
                id2 = address.getId();
                addressTextName.setText(realName);
                addressTextPhone.setText(phone);
                addressTextAddress.setText(address1);
                popupWindow.dismiss();
            }
        });
    }

    @OnClick(R.id.ti_image)
    public void onViewClicked() {
        popupWindow = new PopupWindow(view, 800, 200,
                true);
//        int height = getWindowManager().getDefaultDisplay().getHeight();
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x000000));
        popupWindow.showAsDropDown(tiImage);

    }

    @OnClick(R.id.tijiao)
    public void onViewClicked(View view) {
        List<OrderInfo> list2 = new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setAmount(list.get(i).getCount());
            orderInfo.setCommodityId(list.get(i).getCommodityId());
            list2.add(orderInfo);
        }
        Gson gson = new Gson();
        String s = gson.toJson(list2);

        new CreateOrederPresenter(new MyJs()).request((int)userId,sessionId,s,sum+"",id2+"");
    }
    class MyJs implements BaseCall<Result>{

        @Override
        public void loadSuccess(Result data) {
            if(data.getStatus().equals("0000")){
                Toast.makeText(JSActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void loadError(ApiException throwable) {

        }
    }
    class My implements BaseCall<Result<List<AddressBean>>> {

        @Override
        public void loadSuccess(Result<List<AddressBean>> data) {
            if (data.getStatus().equals("0000")) {
                List<AddressBean> result = data.getResult();
                popuAddressAdapter.addList(result);
                popuAddressAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void loadError(ApiException throwable) {

        }
    }

    public void getSum() {
        sum = 0.0;
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i).getCount() * list.get(i).getPrice();
            count += list.get(i).getCount();
        }
        shoppingSum.setText(sum + "");
        shoppingNum.setText(count + "");
    }
}
