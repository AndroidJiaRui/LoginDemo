package com.example.presenter;

import com.example.core.BaseCall;
import com.example.core.ILogin;
import com.example.utils.HttpUtils;

import io.reactivex.Observable;

public class AddressPreseter extends BasePresenter {
    public AddressPreseter(BaseCall baseCall) {
        super(baseCall);
    }

    @Override
    protected Observable observable(Object... args) {
        ILogin iLogin = HttpUtils.getHttpUtils().create(ILogin.class);
        return iLogin.addresslist((int)args[0],(String)args[1]);
    }
}
