package com.example.logindemo;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import com.example.adapter.SimImageAdapter;
import com.example.bean.Result;
import com.example.core.BaseCall;
import com.example.exception.ApiException;
import com.example.fragment.Fragment2;
import com.example.presenter.PublishCirclePresenter;
import com.example.utils.StringUtils;
import com.example.utils.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;


public class CircleActivity extends WDActivity implements BaseCall {
    @BindView(R.id.bo_text)
    EditText mText;
    PublishCirclePresenter presenter;

    @BindView(R.id.bo_image_list)
    RecyclerView mImageList;

    SimImageAdapter mImageAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle;
    }

    @Override
    protected void initView() {
        mImageAdapter = new SimImageAdapter();
        mImageAdapter.setSign(1);
        mImageAdapter.add(R.drawable.mask_01);
        mImageList.setLayoutManager(new GridLayoutManager(this, 3));
        mImageList.setAdapter(mImageAdapter);
        presenter = new PublishCirclePresenter(this);
    }
    @OnClick(R.id.back)
    public void back(){
        finish();
    }

    @OnClick(R.id.send)
    public void publish(){
        presenter.request(LOGIN_USER.getUserId(),
                LOGIN_USER.getSessionId(),
                1,mText.getText().toString(),mImageAdapter.getList());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {//resultcode是setResult里面设置的code值
            String filePath = getFilePath(null,requestCode,data);
            if (!StringUtils.isEmpty(filePath)) {
                mImageAdapter.add(filePath);
                mImageAdapter.notifyDataSetChanged();
//                Bitmap bitmap = UIUtils.decodeFile(new File(filePath),200);
//                mImage.setImageBitmap(bitmap);
            }
        }

    }
    @Override
    protected void destoryData() {

    }


    @Override
    public void loadSuccess(Object data) {
        Result result = (Result) data;
        if (result.getStatus().equals("0000")){
            Fragment2.addCircle = true;
            finish();
        }else{
            UIUtils.showToastSafe(result.getStatus()+"  "+result.getMessage());
        }
    }

    @Override
    public void loadError(ApiException throwable) {

    }
}
