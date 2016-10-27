package com.yigu.shop.fragment.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yigu.shop.R;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.util.ControllerUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brain on 2016/10/8.
 */
public class MyShopFragment extends BaseFrag {
    @Bind(R.id.rl_addr)
    RelativeLayout rlAddr;
    @Bind(R.id.ll_order)
    LinearLayout llOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_shop, container, false);
        ButterKnife.bind(this, view);
        initView();
        load();
        return view;
    }

    private void initView() {

    }

    public void load() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ll_order, R.id.rl_addr})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_order:
                ControllerUtil.go2MyOrder();
                break;
            case R.id.rl_addr:
                ControllerUtil.go2ManageAddr();
                break;
        }
    }
}
