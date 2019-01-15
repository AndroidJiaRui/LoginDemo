package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.ShoppingTrolleyAdapter;
import com.example.bean.Result;
import com.example.bean.ShoppingBean;
import com.example.bean.User;
import com.example.core.BaseCall;
import com.example.exception.ApiException;
import com.example.logindemo.JSActivity;
import com.example.logindemo.R;
import com.example.logindemo.greendao.DaoMaster;
import com.example.logindemo.greendao.DaoSession;
import com.example.logindemo.greendao.ShoppingBeanDao;
import com.example.logindemo.greendao.UserDao;
import com.example.presenter.ShoppingPresenter;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Fragment3 extends Fragment implements ShoppingTrolleyAdapter.TotalPriceListener {
    private SwipeMenuRecyclerView recyclerView;
    private TextView sumss;
    private ShoppingTrolleyAdapter shoppingTrolleyAdapter;
    private int userId;
    private String sessionId;
    private ShoppingPresenter shoppingPresenter;
    private UserDao userDao;
    private List<ShoppingBean> result;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);
        ButterKnife.bind(this, view);

        DaoSession daoSession1 = DaoMaster.newDevSession(getContext(), UserDao.TABLENAME);
        userDao = daoSession1.getUserDao();
        final List<User> list2 = userDao.loadAll();
        userId = (int) list2.get(0).getUserId();
        sessionId = list2.get(0).getSessionId();

        //计算总价
        sumss = view.findViewById(R.id.sumss);

        recyclerView = view.findViewById(R.id.expandableListView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //创建适配器
        shoppingTrolleyAdapter = new ShoppingTrolleyAdapter(getContext());
        //设置适配器

        shoppingTrolleyAdapter.setTotalPriceListener(this);//设置总价回调接口
        //获取全选控件
        CheckBox checkboxs = view.findViewById(R.id.checkbox1);
        checkboxs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //调用adapter里面的全选/全不选方法
                shoppingTrolleyAdapter.checkAll(isChecked);
            }
        });

        recyclerView.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
                SwipeMenuItem swipeMenuItem = new SwipeMenuItem(getContext());
                swipeMenuItem.setBackgroundColor(0xff00000)
                        .setText("删除")
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)//设置高，这里使用match_parent，就是与item的高相同
                        .setWidth(80);//设置宽
                rightMenu.addMenuItem(swipeMenuItem);//设置右边的侧滑
            }
        });
        recyclerView.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, int position) {
                int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。0是左，右是1，暂时没有用到
                //int adapterPosition = menuBridge.get; // RecyclerView的Item的position。
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                //Toast.makeText(getContext(), "删除" + position, Toast.LENGTH_SHORT).show();
                result.remove(position);
                ShoppingTrolleyAdapter shoppingTrolleyAdapter = new ShoppingTrolleyAdapter(getContext());
                shoppingTrolleyAdapter.addItem(result);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(shoppingTrolleyAdapter);

            }
        });
        recyclerView.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                //Toast.makeText(getContext(), "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(shoppingTrolleyAdapter);

        //调用P层方法
        shoppingPresenter = new ShoppingPresenter(new Shopping());
        shoppingPresenter.request(userId,sessionId);
        return view;
    }

    //接口回调
    @Override
    public void totalPrice(double totalPrice) {
        sumss.setText("合计：￥" + totalPrice);
    }


    @Override
    public void onResume() {
        super.onResume();
        shoppingPresenter.request(userId, sessionId);
    }


    private class Shopping implements BaseCall<Result<List<ShoppingBean>>> {


        @Override
        public void loadSuccess(Result<List<ShoppingBean>> data) {
            result = data.getResult();
            shoppingTrolleyAdapter.remove();
            shoppingTrolleyAdapter.addItem(result);
            //刷新适配器
            shoppingTrolleyAdapter.notifyDataSetChanged();
        }

        @Override
        public void loadError(ApiException throwable) {

        }
    }
    @OnClick(R.id.jiesuan)
    public void jiesuan() {
        List<ShoppingBean> shoppingBeans = shoppingTrolleyAdapter.close();
        DaoSession daoSession = DaoMaster.newDevSession(getActivity(), ShoppingBeanDao.TABLENAME);
        ShoppingBeanDao shoppingBeanDao = daoSession.getShoppingBeanDao();
        shoppingBeanDao.deleteAll();
        for (int i = 0; i < shoppingBeans.size(); i++) {
            ShoppingBean shoppingBean = shoppingBeans.get(i);
            shoppingBean.setId(i);
            shoppingBeanDao.insertOrReplace(shoppingBean);
        }
        Intent intent = new Intent(getActivity(), JSActivity.class);
        startActivity(intent);
    }
}
