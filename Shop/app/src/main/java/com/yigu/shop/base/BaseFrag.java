package com.yigu.shop.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.shop.commom.application.AppContext;
import com.yigu.shop.commom.sharedpreferences.ComUserSP;
import com.yigu.shop.commom.sharedpreferences.UserSP;


/**
 * Created by brain on 2016/6/26.
 */
public class BaseFrag extends Fragment{
    protected UserSP userSP;
    protected ComUserSP comUserSP;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userSP = new UserSP(AppContext.getInstance());
        comUserSP = new ComUserSP(AppContext.getInstance());
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    public void showLoading() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showLoading();
        }
    }

    public void hideLoading() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideLoading();
        }
    }

    public void load() {

    }

}
