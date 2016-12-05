package com.yigu.shop.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.fragment.community.ComIndexFragment;
import com.yigu.shop.fragment.community.ComMunityFragment;
import com.yigu.shop.fragment.community.MunityHostFragment;
import com.yigu.shop.fragment.index.FindShopFragment;
import com.yigu.shop.fragment.index.IndextFragment;
import com.yigu.shop.fragment.index.MyShopFragment;
import com.yigu.shop.fragment.index.SortTwoFragment;
import com.yigu.shop.util.ControllerUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommunityActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.radio_home)
    RadioButton radioHome;
    @Bind(R.id.radio_community)
    RadioButton radioCommunity;
    @Bind(R.id.radio_person)
    RadioButton radioPerson;
    @Bind(R.id.radio_shop)
    RadioButton radioShop;
    @Bind(R.id.radio_tell)
    RadioButton radioTell;
    @Bind(R.id.bottom_layout)
    RadioGroup bottomLayout;
    @Bind(R.id.tv_right_two)
    TextView tvRightTwo;
    private BaseFrag[] fragments;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        back.setImageResource(R.mipmap.logo);
        ivRight.setImageResource(R.mipmap.search);
        tvRightTwo.setText("签到");
        tvRightTwo.setVisibility(View.VISIBLE);
        fragments = new BaseFrag[2];
        fragments[0] = new ComIndexFragment();
        fragments[1] = new ComMunityFragment();
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


    @OnClick({R.id.tv_right_two, R.id.radio_home,R.id.radio_community,R.id.radio_shop, R.id.radio_tell, R.id.radio_person,R.id.iv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right_two:
                break;
            case R.id.radio_home:
                index = 0;
                selectTab();
                fragments[index].load();
                break;
            case R.id.radio_community:
                index = 1;
                selectTab();
                fragments[index].load();
                break;
            case R.id.radio_shop:
                finish();
                break;
            case R.id.radio_tell:
                break;
            case R.id.radio_person:
                break;
            case R.id.iv_right:
                ControllerUtil.go2ComSearch();
                break;

        }
    }
}
