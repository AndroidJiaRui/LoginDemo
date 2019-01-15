package com.example.presenter;

import com.example.bean.Result;
import com.example.bean.SearchGoods;
import com.example.core.BaseCall;
import com.example.exception.CustomException;
import com.example.exception.ResponseTransformer;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public abstract class BasePresenter {
    private BaseCall baseCall;
    private  boolean running;
    public BasePresenter(BaseCall baseCall){
        this.baseCall = baseCall;
    }
    protected abstract Observable observable(Object...args);
    public boolean isRunning(){
        return running;
    }
    public void request(Object...args){
        if(running){
            return;
        }
        running = true;
        observable(args)
                .compose(ResponseTransformer.handleResult())
                .compose(new ObservableTransformer() {
                    @Override
                    public ObservableSource apply(Observable upstream) {
                        return upstream.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribe(new Consumer<Result>() {

                    @Override
                    public void accept(Result result) throws Exception {
                        running = false;
                        baseCall.loadSuccess(result);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        running = false;
                        baseCall.loadError(CustomException.handleException(throwable));

                    }
                });
    }
}
