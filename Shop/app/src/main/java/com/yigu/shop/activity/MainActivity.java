package com.yigu.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.yigu.shop.R;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.base.RequestCode;
import com.yigu.shop.fragment.find.UnCollectionFragment;
import com.yigu.shop.fragment.index.FindShopFragment;
import com.yigu.shop.fragment.index.IndextFragment;
import com.yigu.shop.fragment.index.MyShopFragment;
import com.yigu.shop.fragment.index.SortTwoFragment;
import com.yigu.shop.fragment.shop.ShopListFragment;
import com.yigu.shop.fragment.sort.SortItemsFragment;
import com.yigu.shop.fragment.sort.SortItemsTwoFragment;
import com.yigu.shop.util.ControllerUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.iv_right_two)
    ImageView ivRightTwo;
    @Bind(R.id.radio_home)
    RadioButton radioHome;
    @Bind(R.id.radio_find)
    RadioButton radioFind;
    @Bind(R.id.radio_community)
    RadioButton radioCommunity;
    @Bind(R.id.radio_sort)
    RadioButton radioSort;
    @Bind(R.id.radio_person)
    RadioButton radioPerson;
    private BaseFrag[] fragments;
    private int index = 0;


    private RadioButton[] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (!userSP.checkLogin()) {
//            ControllerUtil.go2Login();
//            finish();
//        } else {
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);
            initView();
//        }

    }

    private void initView() {
        back.setImageResource(R.mipmap.logo);
        ivRight.setImageResource(R.mipmap.search);
        ivRightTwo.setVisibility(View.VISIBLE);
        ivRightTwo.setImageResource(R.mipmap.purcase);
        fragments = new BaseFrag[5];
        fragments[0] = new IndextFragment();
        fragments[1] = new ShopListFragment();
        fragments[2] = new IndextFragment();
        fragments[3] = new SortItemsTwoFragment();
        fragments[4] = new MyShopFragment();

        buttons = new RadioButton[5];
        buttons[0] = radioHome;
        buttons[1] = radioFind;
        buttons[2] = radioCommunity;
        buttons[3] = radioSort;
        buttons[4] = radioPerson;

        selectTab();



    }

    @Override
    protected void onResume() {
        super.onResume();
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        int type = intent.getIntExtra("type",0);
//        if(type== RequestCode.login_exit){
//            ControllerUtil.go2Login();
//            finish();
//        }
    }

    @OnClick({R.id.radio_home,R.id.radio_find, R.id.radio_community, R.id.radio_sort, R.id.radio_person,R.id.iv_right_two
    ,R.id.iv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radio_home:
                index = 0;
                selectTab();
//                fragments[index].load();
                break;
            case R.id.radio_find:
                index = 1;
                selectTab();
//                fragments[index].load();
                break;
            case R.id.radio_community:
                finish();
                break;
            case R.id.radio_sort:
                index = 3;
                selectTab();
//                fragments[index].load();
                break;
            case R.id.radio_person:
                if (!userSP.checkLogin()) {
                    buttons[index].setChecked(true);
                    ControllerUtil.go2Login();
                } else {
                    index = 4;
                    selectTab();
                    fragments[index].load();
                }

                break;
            case R.id.iv_right_two:
                if (!userSP.checkLogin()) {
                    ControllerUtil.go2Login();
                } else {
                     ControllerUtil.go2PurcaseList();
                }
                break;
            case R.id.iv_right:
                ControllerUtil.go2Search();
                break;
        }
    }
}
