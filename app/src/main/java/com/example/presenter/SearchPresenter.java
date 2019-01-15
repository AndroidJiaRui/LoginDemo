package com.example.presenter;

import com.example.bean.Result;
import com.example.bean.SearchGoods;
import com.example.core.BaseCall;
import com.example.core.ILogin;
import com.example.utils.HttpUtils;

import java.util.List;

import io.reactivex.Observable;

public class SearchPresenter extends BasePresenter {
    public SearchPresenter(BaseCall baseCall) {
        super(baseCall);
    }

    @Override
    protected Observable<Result<List<SearchGoods>>> observable(Object... args) {
        ILogin iLogin = HttpUtils.getHttpUtils().create(ILogin.class);
        return iLogin.search((String) args[0],(int)args[1],(int)args[2]);
    }
}
