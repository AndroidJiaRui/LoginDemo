package com.example.presenter;

import com.example.core.BaseCall;
import com.example.core.ILogin;
import com.example.utils.HttpUtils;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class MyPresenter extends BasePresenter {


    public MyPresenter(BaseCall baseCall) {
        super(baseCall);
    }

    @Override
    protected Observable observable(Object... args) {
        ILogin iLogin = HttpUtils.getHttpUtils().create(ILogin.class);
        return iLogin.login((String)args[0],(String)args[1]);
    }
}
