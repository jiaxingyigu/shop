package com.yigu.shop.activity.addr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.adapter.ItemTwoAdapter;
import com.yigu.shop.adapter.addr.ManageAddrAdapter;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.base.RequestCode;
import com.yigu.shop.commom.api.ItemApi;
import com.yigu.shop.commom.result.MapiAddrResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.util.ControllerUtil;
import com.yigu.shop.widget.DividerListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManageAddrActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    ManageAddrAdapter mAdapter;
    private List<MapiAddrResult> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_addr);
        ButterKnife.bind(this);
        initView();
        load();
    }

    private void initView() {

        mList = new ArrayList<>();

        back.setImageResource(R.mipmap.back);
        center.setText("管理收货地址");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ManageAddrAdapter(this,mList);
        recyclerView.setAdapter(mAdapter);
    }

    @OnClick({R.id.back, R.id.add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.add:
                Intent intent = new Intent(this,AddAddrActivity.class);
                startActivityForResult(intent, RequestCode.add_addr);
                break;
        }
    }

    private void load(){
        ItemApi.getAddresses(this, userSP.getUserBean().getUser_id(), new RequestCallback<List<MapiAddrResult>>() {
            @Override
            public void success(List<MapiAddrResult> success) {
                if(success.isEmpty())
                    return;
                mList.clear();
                mList.addAll(success);
                mAdapter.notifyDataSetChanged();
            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                MainToast.showShortToast(message);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case RequestCode.add_addr:
                    load();
                    break;
            }
        }
    }
}
