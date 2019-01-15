package com.example.presenter;

import com.example.bean.Result;
import com.example.bean.SearchGoods;
import com.example.core.BaseCall;
import com.example.core.ILogin;
import com.example.utils.HttpUtils;

import java.util.List;

import io.reactivex.Observable;

public class TopPresenter extends BasePresenter {
    public TopPresenter(BaseCall baseCall) {
        super(baseCall);
    }

    @Override
    protected Observable observable(Object... args) {
        ILogin iLogin = HttpUtils.getHttpUtils().create(ILogin.class);
        return iLogin.top();
    }
}
