package com.yigu.shop.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.yigu.shop.R;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.fragment.index.FindFragment;
import com.yigu.shop.fragment.index.HomeFragment;
import com.yigu.shop.fragment.index.SortFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.radio_home)
    CheckBox radioHome;
    @Bind(R.id.radio_sort)
    CheckBox radioSort;
    @Bind(R.id.radio_street)
    CheckBox radioStreet;
    @Bind(R.id.radio_find)
    CheckBox radioFind;
    @Bind(R.id.radio_shop)
    CheckBox radioShop;
    private BaseFrag[] fragments;
    private CheckBox[] views;
    private int index = 0;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        fragments = new BaseFrag[5];
        fragments[0] = new HomeFragment();
        fragments[1] = new SortFragment();
        fragments[2] = new HomeFragment();
        fragments[3] = new HomeFragment();
        fragments[4] = new FindFragment();
        views = new CheckBox[5];
        views[0] = radioHome;
        views[1] = radioSort;
        views[2] = radioShop;
        views[3] = radioStreet;
        views[4] = radioFind;

        selectTab();
    }

    private void selectTab() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (BaseFrag frag : fragments) {
            if (!frag.isAdded())
                transaction.add(R.id.content, frag);
            transaction.hide(frag);
        }
        transaction.show(fragments[index]);
        transaction.commitAllowingStateLoss();
    }

    private void updateStatus(int index, boolean isChecked) {
        views[index].setChecked(isChecked);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出汽车商城", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @OnClick({R.id.radio_home, R.id.radio_sort, R.id.radio_shop, R.id.radio_street, R.id.radio_find})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radio_home:
                updateStatus(index, false);
                index = 0;
                updateStatus(index, true);
                selectTab();
                fragments[index].load();
                break;
            case R.id.radio_sort:
                updateStatus(index, false);
                index = 1;
                updateStatus(index, true);
                selectTab();
                fragments[index].load();
                break;
            case R.id.radio_shop:
                updateStatus(index, false);
                index = 2;
                selectTab();
                fragments[index].load();
                break;
            case R.id.radio_street:
                updateStatus(index, false);
                index = 3;
                updateStatus(index, true);
                selectTab();
                fragments[index].load();
                break;
            case R.id.radio_find:
                updateStatus(index, false);
                index = 4;
                updateStatus(index, true);
                selectTab();
                fragments[index].load();
                break;
        }
    }
}
