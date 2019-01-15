package com.example.presenter;


import com.example.core.BaseCall;
import com.example.core.ILogin;
import com.example.utils.HttpUtils;

import io.reactivex.Observable;

public class PayPresenter extends BasePresenter {

    public PayPresenter(BaseCall baseCall) {
        super(baseCall);
    }

    @Override
    protected Observable observable(Object... args) {
        ILogin iLoad = HttpUtils.getHttpUtils().create(ILogin.class);

        return iLoad.pay((long)args[0],(String)args[1],(String) args[2],(int) args[3]);
    }
}
