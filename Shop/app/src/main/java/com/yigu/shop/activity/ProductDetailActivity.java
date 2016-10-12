package com.yigu.shop.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yigu.shop.R;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.fragment.product.ItemDiscussFragment;
import com.yigu.shop.fragment.product.ItemInfoFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDetailActivity extends BaseActivity {
    @Bind(R.id.tabShop)
    TabLayout tabShop;
    @Bind(R.id.content)
    FrameLayout content;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.iv_right_two)
    ImageView ivRightTwo;
    private BaseFrag[] fragments;
    private int index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back);
        ivRight.setImageResource(R.mipmap.search);
        ivRightTwo.setVisibility(View.VISIBLE);
        ivRightTwo.setImageResource(R.mipmap.purcase);
        tabShop.setTabMode(TabLayout.MODE_FIXED);
        tabShop.addTab(tabShop.newTab().setText("商品"));
        tabShop.addTab(tabShop.newTab().setText("详情"));
        tabShop.addTab(tabShop.newTab().setText("评价"));
        tabShop.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        index = 0;
                        selectTab();
                        fragments[index].load();
                        break;
                    case 1:
                        index = 1;
                        selectTab();
                        fragments[index].load();
                        break;
                    case 2:
                        index = 2;
                        selectTab();
                        fragments[index].load();
                        break;
                }

                MainToast.showShortToast(tab.getPosition() + "");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        fragments = new BaseFrag[3];
        fragments[0] = new ItemInfoFragment();
        fragments[1] = new ItemInfoFragment();
        fragments[2] = new ItemDiscussFragment();
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

    @OnClick({R.id.back, R.id.iv_right, R.id.iv_right_two, R.id.shop, R.id.collect, R.id.add, R.id.buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_right:

                break;
            case R.id.iv_right_two:

                break;
            case R.id.shop:
                break;
            case R.id.collect:
                break;
            case R.id.add:
                break;
            case R.id.buy:
                break;
        }
    }

}
