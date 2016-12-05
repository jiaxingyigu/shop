package com.yigu.shop.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yigu.shop.R;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.commom.api.ItemApi;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback;
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

    MapiItemResult itemResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
        itemResult = (MapiItemResult) getIntent().getSerializableExtra("item");
        if(null!=itemResult)
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
        ((ItemInfoFragment)fragments[0]).setBasicInfo(itemResult);
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
                if(null!=itemResult){
                    String user_id = userSP.getUserBean().getUser_id();
                    String goods_id = itemResult.getGoods_id();
                    String goods_sn = itemResult.getGoods_sn();
                    String goods_name = itemResult.getGoods_name();
                    String is_real = itemResult.getIs_real();
                    String extension_code = itemResult.getExtension_code();
                    String market_price = TextUtils.isEmpty(((ItemInfoFragment)fragments[0]).getMarket_price())?"0":((ItemInfoFragment)fragments[0]).getMarket_price();
                    String goods_price = TextUtils.isEmpty(((ItemInfoFragment)fragments[0]).getGoods_price())?"0":((ItemInfoFragment)fragments[0]).getGoods_price();
                    String goods_number =  TextUtils.isEmpty(((ItemInfoFragment)fragments[0]).getGoods_number())?"0":((ItemInfoFragment)fragments[0]).getGoods_number();

                    if("0".equals(goods_number)){
                        MainToast.showShortToast("请选择数量");
                       return;
                    }

                    DebugLog.i("goods_number===>"+goods_number);

                    String goods_attr = ((ItemInfoFragment)fragments[0]).getGoods_attr();
                    String goods_attr_id = ((ItemInfoFragment)fragments[0]).getGoods_attr_id();

                    ItemApi.addCart(this, user_id, goods_id, goods_sn, goods_name, market_price, goods_price, goods_number
                            , goods_attr, is_real, extension_code, goods_attr_id, new RequestCallback() {
                                @Override
                                public void success(Object success) {
                                    MainToast.showShortToast("添加成功");
                                }
                            }, new RequestExceptionCallback() {
                                @Override
                                public void error(Integer code, String message) {
                                    MainToast.showShortToast(message);
                                }
                            });
                }

                break;
            case R.id.buy:
                break;
        }
    }


}
