package com.example.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.User;
import com.example.logindemo.AddressActivity;
import com.example.logindemo.CircleActivity;
import com.example.logindemo.FootActivity;
import com.example.logindemo.MyActivity;
import com.example.logindemo.R;
import com.example.logindemo.WalletActivity;
import com.example.logindemo.greendao.DaoMaster;
import com.example.logindemo.greendao.DaoSession;
import com.example.logindemo.greendao.UserDao;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Fragment5 extends Fragment {

    @BindView(R.id.me_info)
    RelativeLayout meInfo;
    @BindView(R.id.me_circle)
    RelativeLayout meCircle;
    @BindView(R.id.me_foot)
    RelativeLayout meFoot;
    @BindView(R.id.me_wallet)
    RelativeLayout meWallet;
    @BindView(R.id.me_address)
    RelativeLayout meAddress;
    Unbinder unbinder;
    @BindView(R.id.me_bg)
    SimpleDraweeView meBg;
    @BindView(R.id.me_nickname)
    TextView meNickname;
    @BindView(R.id.line1)
    LinearLayout line1;
    @BindView(R.id.me_tou)
    SimpleDraweeView meTou;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment5, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DaoSession daoSession = DaoMaster.newDevSession(getContext(), UserDao.TABLENAME);
        UserDao userDao = daoSession.getUserDao();
        List<User> list = userDao.loadAll();
        String headPic = list.get(0).getHeadPic();
        meTou.setImageURI(Uri.parse(headPic));
        meNickname.setText(list.get(0).getNickName());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.me_info, R.id.me_circle, R.id.me_foot, R.id.me_wallet, R.id.me_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.me_info:
                Intent intent = new Intent(getContext(), MyActivity.class);
                startActivity(intent);
                break;
            case R.id.me_circle:
                Intent intent4 = new Intent(getContext(),CircleActivity.class);
                startActivity(intent4);
                break;
            case R.id.me_foot:
                Intent intent3 = new Intent(getContext(), FootActivity.class);
                startActivity(intent3);
                break;
            case R.id.me_wallet:
                Intent intent1 = new Intent(getContext(),WalletActivity.class);
                startActivity(intent1);
                break;
            case R.id.me_address:
                Intent intent2 = new Intent(getContext(), AddressActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
