package com.example.presenter;

import com.example.core.BaseCall;
import com.example.core.ILogin;
import com.example.utils.HttpUtils;

import io.reactivex.Observable;

public class ListPresenter extends BasePresenter {
    public ListPresenter(BaseCall baseCall) {
        super(baseCall);
    }

    @Override
    protected Observable observable(Object... args) {
        ILogin iLogin = HttpUtils.getHttpUtils().create(ILogin.class);
        return iLogin.AllOrder((int)args[0],(String)args[1],(String)args[2],(String)args[3],(String)args[4]);
    }
}
