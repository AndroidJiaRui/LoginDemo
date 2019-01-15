package com.example.presenter;

import com.example.bean.CircleBean;
import com.example.bean.Result;
import com.example.core.BaseCall;
import com.example.core.ILogin;
import com.example.utils.HttpUtils;

import java.util.List;

import io.reactivex.Observable;

public class DetailsPresenter extends BasePresenter {
    public DetailsPresenter(BaseCall baseCall) {
        super(baseCall);
    }

    @Override
    protected Observable observable(Object... args) {
        ILogin iLogin = HttpUtils.getHttpUtils().create(ILogin.class);
        return iLogin.details((int)args[0],(String) args[1],(String) args[2]);
    }
}
