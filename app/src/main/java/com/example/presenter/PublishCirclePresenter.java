package com.example.presenter;

import com.example.core.BaseCall;
import com.example.core.ILogin;
import com.example.presenter.BasePresenter;
import com.example.utils.HttpUtils;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PublishCirclePresenter extends BasePresenter {


    public PublishCirclePresenter(BaseCall baseCall) {
        super(baseCall);
    }

    @Override
    protected Observable observable(Object... args) {
        ILogin iLogin = HttpUtils.getHttpUtils().create(ILogin.class);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("content", (String) args[3]);
        builder.addFormDataPart("commodityId", "1");
        List<Object> list = (List<Object>) args[4];
        if (list.size() > 1) {
            for (int i = 1; i < list.size(); i++) {
                File file = new File((String) list.get(i));
                builder.addFormDataPart("image", file.getName(),
                        RequestBody.create(MediaType.parse("multipart/octet-stream"),
                                file));
            }
        }
        return iLogin.releaseCircle((long) args[0],
                (String) args[1], builder.build());
    }
}
