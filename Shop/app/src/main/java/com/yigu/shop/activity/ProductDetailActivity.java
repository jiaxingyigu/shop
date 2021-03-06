package com.yigu.shop.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.commom.api.BasicApi;
import com.yigu.shop.commom.api.ItemApi;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.fragment.product.ItemDetailFragment;
import com.yigu.shop.fragment.product.ItemDiscussFragment;
import com.yigu.shop.fragment.product.ItemInfoFragment;
import com.yigu.shop.util.ControllerUtil;

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
    @Bind(R.id.collect_tv)
    TextView collectTv;
    private BaseFrag[] fragments;
    private int index = 0;

    MapiItemResult itemResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
        itemResult = (MapiItemResult) getIntent().getSerializableExtra("item");
        if (null != itemResult)
            initView();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back);
//        ivRight.setImageResource(R.mipmap.search);
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
        ((ItemInfoFragment) fragments[0]).setBasicInfo(itemResult);
        ((ItemInfoFragment) fragments[0]).setCollectListener(new ItemInfoFragment.CollectListener() {
            @Override
            public void getCollcetStatus(int status) {
                itemResult.setCollected(status);
                if (itemResult.getCollected() == 0) {
                    collectTv.setTextColor(Color.parseColor("#333333"));
                    collectTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.collection_gray,0,0);
                }else{
                    collectTv.setTextColor(Color.parseColor("#e60013"));
                    collectTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.collection_pre,0,0);
                }
            }
        });
        fragments[1] = new ItemDetailFragment();
        ((ItemDetailFragment) fragments[1]).loadWeb(BasicApi.BASIC_URL + BasicApi.goods_web + itemResult.getGoods_id());
        fragments[2] = new ItemDiscussFragment();
        ((ItemDiscussFragment) fragments[2]).setBasicInfo(itemResult);
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
                if (!userSP.checkLogin()) {
                    ControllerUtil.go2Login();
                } else {
                    ControllerUtil.go2PurcaseList();
                }
                break;
            case R.id.shop:
                if (!userSP.checkLogin()) {
                    ControllerUtil.go2Login();
                }
                break;
            case R.id.collect:
                if (!userSP.checkLogin()) {
                    ControllerUtil.go2Login();
                }else{
                    showLoading();
                    if(itemResult.getCollected()==0){
                        ItemApi.collect(this, itemResult.getGoods_id(), new RequestCallback() {
                            @Override
                            public void success(Object success) {
                                hideLoading();
                                itemResult.setCollected(1);
                                collectTv.setTextColor(Color.parseColor("#e60013"));
                                collectTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.collection_pre,0,0);
                            }
                        }, new RequestExceptionCallback() {
                            @Override
                            public void error(Integer code, String message) {
                                hideLoading();
                                MainToast.showShortToast(message);
                            }
                        });
                    }else {
                        ItemApi.delcollect(this, itemResult.getGoods_id(), new RequestCallback() {
                            @Override
                            public void success(Object success) {
                                hideLoading();
                                itemResult.setCollected(0);
                                collectTv.setTextColor(Color.parseColor("#333333"));
                                collectTv.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.collection_gray,0,0);
                            }
                        }, new RequestExceptionCallback() {
                            @Override
                            public void error(Integer code, String message) {
                                hideLoading();
                                MainToast.showShortToast(message);
                            }
                        });
                    }

                }
                break;
            case R.id.add:
                if (!userSP.checkLogin()) {
                    ControllerUtil.go2Login();
                } else {
                    if (null != itemResult) {
                        String goods_id = itemResult.getGoods_id();

                        String goods_number = TextUtils.isEmpty(((ItemInfoFragment) fragments[0]).getGoods_number()) ? "0" : ((ItemInfoFragment) fragments[0]).getGoods_number();

                        if ("0".equals(goods_number)) {
                            MainToast.showShortToast("请选择数量");
                            return;
                        }
                        DebugLog.i("goods_number===>" + goods_number);
                        String goods_attr_id = ((ItemInfoFragment) fragments[0]).getGoods_attr_id();
                        if(((ItemInfoFragment) fragments[0]).isNeedAttr()){
                            if(TextUtils.isEmpty(goods_attr_id)){
                                MainToast.showShortToast("请选择规格");
                                return;
                            }
                        }


                        showLoading();
                        ItemApi.addCart(this,goods_id,goods_number,goods_attr_id, new RequestCallback() {
                                    @Override
                                    public void success(Object success) {
                                        hideLoading();
                                        MainToast.showShortToast("添加成功");
                                    }
                                }, new RequestExceptionCallback() {
                                    @Override
                                    public void error(Integer code, String message) {
                                        hideLoading();
                                        MainToast.showShortToast(message);
                                    }
                                });
                    }
                }

                break;
            case R.id.buy:
                if (!userSP.checkLogin()) {
                    ControllerUtil.go2Login();
                } else {

                }
                break;
        }
    }


}
