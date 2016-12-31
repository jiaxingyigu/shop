package com.yigu.shop.fragment.index;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.shop.R;
import com.yigu.shop.base.BaseFrag;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends BaseFrag {

    public FindFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_find, container, false);
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

    @OnClick({R.id.search_iv, R.id.purcase_iv, R.id.person_iv, R.id.msg, R.id.service, R.id.browse, R.id.help, R.id.join})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_iv:
                break;
            case R.id.purcase_iv:
                break;
            case R.id.person_iv:
                break;
            case R.id.msg:
                break;
            case R.id.service:
                break;
            case R.id.browse:
                break;
            case R.id.help:
                break;
            case R.id.join:
                break;
        }
    }
}
