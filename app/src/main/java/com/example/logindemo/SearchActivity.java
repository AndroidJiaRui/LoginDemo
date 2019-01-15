package com.example.logindemo;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.BottonAdapter;
import com.example.adapter.MySearchAdapter;
import com.example.adapter.TopAdapter;
import com.example.bean.BottomBean;
import com.example.bean.Result;
import com.example.bean.SearchGoods;
import com.example.bean.TopBean;
import com.example.core.BaseCall;
import com.example.exception.ApiException;
import com.example.presenter.BasePresenter;
import com.example.presenter.BottomPresenter;
import com.example.presenter.SearchPresenter;
import com.example.presenter.ShowPresenter;
import com.example.presenter.TopPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.search_text)
    TextView searchText;
    @BindView(R.id.search_recy)
    RecyclerView searchRecy;
    @BindView(R.id.search_a)
    LinearLayout searchA;
    @BindView(R.id.search_b)
    LinearLayout searchB;
    @BindView(R.id.s1)
    ImageView s1;
    private MySearchAdapter mySearchAdapter;
    private View view1;
    private TopAdapter topAdapter;
    private PopupWindow popupWindow;
    private BottomPresenter bottomPresenter;
    private BottonAdapter bottonAdapter;
    String id = "1001002";
    private ShowPresenter showPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        mySearchAdapter = new MySearchAdapter();
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        searchRecy.setLayoutManager(manager);
        searchRecy.setAdapter(mySearchAdapter);
        view1 = View.inflate(SearchActivity.this, R.layout.item_pop, null);
        TopPresenter topPresenter = new TopPresenter(new Top());
        RecyclerView topRecycler = view1.findViewById(R.id.top_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        topRecycler.setLayoutManager(linearLayoutManager);
        topAdapter = new TopAdapter();
        topRecycler.setAdapter(topAdapter);
        topRecycler.setBackgroundColor(0x88000000);
        topPresenter.request();
        bottomPresenter = new BottomPresenter(new Botton());
        RecyclerView bottonRecycler = view1.findViewById(R.id.botton_recycler);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        bottonRecycler.setLayoutManager(linearLayoutManager1);
        bottonAdapter = new BottonAdapter();
        bottonRecycler.setAdapter(bottonAdapter);
        bottonRecycler.setBackgroundColor(0x77000000);

        bottomPresenter.request(id);
        showPresenter = new ShowPresenter(new MyCall());
        topAdapter.setOnClick(new TopAdapter.onClick() {
            @Override
            public void click(String id) {
                bottomPresenter.request(id);
            }
        });
        bottonAdapter.setOnClick(new BottonAdapter.onClick() {
            @Override
            public void chick(String id) {
                popupWindow.dismiss();
                showPresenter.request(id, "1", "20");
            }
        });
    }

    @OnClick({R.id.s1, R.id.search_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.s1:
                popupWindow = new PopupWindow(view1, 800, 200,
                        true);
//        int height = getWindowManager().getDefaultDisplay().getHeight();
                popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(0x000000));
                popupWindow.showAsDropDown(s1);
                break;
            case R.id.search_text:
                String s = searchEdit.getText().toString();
                new SearchPresenter(new MyCall()).request(s, 1, 10);
                break;
        }
    }

   class Botton implements BaseCall<Result<List<BottomBean>>> {

        @Override
        public void loadSuccess(Result<List<BottomBean>> data) {
            List<BottomBean> result = data.getResult();
            bottonAdapter.remove();
            bottonAdapter.addItem(result);
            bottonAdapter.notifyDataSetChanged();
        }

        @Override
        public void loadError(ApiException throwable) {

        }
    }

    class MyCall implements BaseCall<Result<List<SearchGoods>>> {

        @Override
        public void loadSuccess(Result<List<SearchGoods>> data) {
            if (data.getStatus().equals("0000")) {
                if (data.getResult().size() != 0) {
                    searchA.setVisibility(View.VISIBLE);
                    searchB.setVisibility(View.GONE);
                    mySearchAdapter.clear();
                    mySearchAdapter.addList(data.getResult());
                    mySearchAdapter.notifyDataSetChanged();
                } else {
                    searchA.setVisibility(View.GONE);
                    searchB.setVisibility(View.VISIBLE);
                    mySearchAdapter.clear();
                }
            }
        }

        @Override
        public void loadError(ApiException throwable) {

        }
    }
    class Top implements BaseCall<Result<List<TopBean>>> {


        @Override
        public void loadSuccess(Result<List<TopBean>> data) {
            List<TopBean> result = data.getResult();

            topAdapter.addItem(result);
            topAdapter.notifyDataSetChanged();
        }

        @Override
        public void loadError(ApiException throwable) {

        }
    }


}
