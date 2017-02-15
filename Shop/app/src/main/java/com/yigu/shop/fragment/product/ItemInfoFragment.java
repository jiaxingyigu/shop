package com.yigu.shop.fragment.product;


import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yigu.shop.R;
import com.yigu.shop.activity.addr.SelAddrActivity;
import com.yigu.shop.adapter.ShopPagerAdapter;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.base.RequestCode;
import com.yigu.shop.commom.api.ItemApi;
import com.yigu.shop.commom.result.MapiAddrResult;
import com.yigu.shop.commom.result.MapiAttrResult;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.result.MapiShopResult;
import com.yigu.shop.commom.result.ProvinceModel;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.util.StringUtil;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.util.AnimationUtil;
import com.yigu.shop.util.ControllerUtil;
import com.yigu.shop.view.ItemShopLayout;
import com.yigu.shop.view.SelSizeLayout;
import com.yigu.shop.widget.SelSizePopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ItemInfoFragment extends BaseFrag {

    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.guide_dot)
    LinearLayout guideDot;
    @Bind(R.id.info_tv)
    TextView infoTv;
    @Bind(R.id.addr_tv)
    TextView addrTv;
    @Bind(R.id.itemShopLayout)
    ItemShopLayout itemShopLayout;
    @Bind(R.id.goods_desc)
    TextView goodsDesc;
    @Bind(R.id.shop_price)
    TextView shopPrice;
    @Bind(R.id.market_price)
    TextView marketPrice;
    @Bind(R.id.selSizeLayout)
    SelSizeLayout selSizeLayout;
    @Bind(R.id.bg_view)
    View bg_view;

    List<View> sliderViewList;

    MapiItemResult itemResult;

    private String market_price="";
    private String goods_price="";
    private String goods_number="";
    private String goods_attr="";
    private String goods_attr_id="";

    private boolean isNeedAttr = true;

    public boolean isNeedAttr() {
        return isNeedAttr;
    }

    Gson gson = new Gson();

    public ItemInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_item_info, container, false);
        ButterKnife.bind(this, view);
        initView();
        initListener();
        load();
        return view;
    }

    private void initView() {
        try {
            if (null != itemResult) {
//                goodsDesc.setText(TextUtils.isEmpty(itemResult.getGoods_name()) ? "" : itemResult.getGoods_name());
//                marketPrice.setText(TextUtils.isEmpty(itemResult.getMarket_price()) ? "原价：0" : "原价：" + itemResult.getMarket_price());
//                shopPrice.setText(TextUtils.isEmpty(itemResult.getShop_price()) ? "0" : itemResult.getShop_price());
//                marketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBasicInfo(MapiItemResult itemResult) {
        this.itemResult = itemResult;
    }

    private void initListener(){
        selSizeLayout.setOnHideListener(new SelSizeLayout.OnHideListener() {
            @Override
            public void hide() {

                infoTv.setText(selSizeLayout.getGoods_attr());
                shopPrice.setText(selSizeLayout.getGoods_price());
                hideAlbum();
            }
        });


    }

    public void load() {
        if(null!=itemResult){
            ItemApi.GoodDetail(getActivity(), itemResult.getGoods_id(),new RequestCallback<JSONObject>() {
                @Override
                public void success(JSONObject success) {

                    String goods_name = success.getJSONObject("data").getString("goods_name");
                    String market_price = success.getJSONObject("data").getString("market_price");
                    String shop_price = success.getJSONObject("data").getString("shop_price");

                    goodsDesc.setText(TextUtils.isEmpty(goods_name) ? "" : goods_name);
                    marketPrice.setText(TextUtils.isEmpty(market_price) ? "原价：￥0" : "原价：" + market_price);
                    shopPrice.setText(TextUtils.isEmpty(shop_price) ? "￥0" : shop_price);
                    marketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

                    List<MapiResourceResult> gallery = JSONArray.parseArray(success.getJSONObject("data").getJSONArray("pictures").toJSONString(),MapiResourceResult.class);
                    if(null!=gallery)
                        initViewPager(gallery);
                    MapiShopResult shopResult = JSONArray.parseObject(success.getJSONObject("data").getJSONObject("seller_info").toJSONString(),MapiShopResult.class);
                    if(null!=shopResult&& !StringUtil.isEmpty(shopResult.getId()))
                        itemShopLayout.load(shopResult);
                    else
                        itemShopLayout.setVisibility(View.GONE);

                    List<MapiAttrResult> attrs = gson.fromJson(success.getJSONObject("data").getJSONArray("specification").toJSONString(), new TypeToken<List<MapiAttrResult>>(){}.getType());
                    if(null!=attrs&&!attrs.isEmpty()) {
                        isNeedAttr = true;

                    }else{
                        isNeedAttr = false;
                    }

                    selSizeLayout.load(attrs, itemResult);

                    int collect = success.getJSONObject("data").getInteger("collected");
                    if(null!=collectListener)
                        collectListener.getCollcetStatus(collect);


                }
            }, new RequestExceptionCallback() {
                @Override
                public void error(Integer code, String message) {
                    MainToast.showShortToast(message);
                }
            });
        }

    }

    private void initViewPager( List<MapiResourceResult> gallery){
        if(null!=gallery&&gallery.size()>0){
            sliderViewList = new ArrayList<>();
            for (int i = 0; i < gallery.size(); i++) {
                SimpleDraweeView view = (SimpleDraweeView) LayoutInflater.from(getActivity()).inflate(R.layout.layout_draweeview,null);
                //创建将要下载的图片的URI
                Uri imageUri = Uri.parse(gallery.get(i).getUrl());
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                        .setResizeOptions(new ResizeOptions(DPUtil.dip2px(375), DPUtil.dip2px(375)))
                        .build();
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(view.getController())
                        .setControllerListener(new BaseControllerListener<ImageInfo>())
                        .build();
                view.setController(controller);



                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                sliderViewList.add(view);
            }
            ShopPagerAdapter sliderAdapter = new ShopPagerAdapter(sliderViewList);
            viewpager.setAdapter(sliderAdapter);
            guideDot.removeAllViews();
            for (int i = 0; i < sliderViewList.size(); i++) {
                ImageView imageView = new ImageView(getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DPUtil.dip2px(7), DPUtil.dip2px(7));
                params.setMargins(DPUtil.dip2px(4), 0, DPUtil.dip2px(4), DPUtil.dip2px(6));
                imageView.setLayoutParams(params);
                imageView.setBackgroundResource(R.drawable.selector_item_dot);
                guideDot.addView(imageView);
            }
            guideDot.getChildAt(0).setSelected(true);
            viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }


                @Override
                public void onPageSelected(int position) {
                    for (int i = 0; i < sliderViewList.size(); i++) {
                        if (position == i)
                            guideDot.getChildAt(i).setSelected(true);
                        else
                            guideDot.getChildAt(i).setSelected(false);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }

            });
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case RequestCode.sel_addr:
                    if (null != data)
                        addrTv.setText(data.getStringExtra("addr"));
                    break;
            }
        }
    }

    @OnClick({R.id.info_rl, R.id.addr_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.info_rl:
                if (!userSP.checkLogin()) {
                    ControllerUtil.go2Login();
                } else {
                    album();
                }
                break;
            case R.id.addr_rl:
                if (!userSP.checkLogin()) {
                    ControllerUtil.go2Login();
                } else {
                    Intent intent = new Intent(getActivity(), SelAddrActivity.class);
                    startActivityForResult(intent, RequestCode.sel_addr);
                }
                break;
        }
    }

    private void album() {
        if (selSizeLayout.getVisibility() == View.GONE) {
            popAlbum();
        } else {
            hideAlbum();
        }
    }

    /** 弹出 */
    private void popAlbum() {
        selSizeLayout.setVisibility(View.VISIBLE);
        bg_view.setVisibility(View.VISIBLE);
        new AnimationUtil(getActivity(), R.anim.translate_up_current)
                .setLinearInterpolator().startAnimation(selSizeLayout);
    }

    /** 隐藏 */
    private void hideAlbum() {
        new AnimationUtil(getActivity(), R.anim.translate_down)
                .setLinearInterpolator().startAnimation(selSizeLayout);
        selSizeLayout.setVisibility(View.GONE);
        bg_view.setVisibility(View.GONE);
    }

    public String getMarket_price() {
        market_price = selSizeLayout.getMarket_price();
        return market_price;
    }

    public String getGoods_price() {
        goods_price = selSizeLayout.getGoods_price();
        return goods_price;
    }

    public String getGoods_number() {
        goods_number = selSizeLayout.getGoods_number();
        return goods_number;
    }

    public String getGoods_attr_id() {
        goods_attr_id = selSizeLayout.getGoods_attr_id();
        return goods_attr_id;
    }

    public String getGoods_attr() {
        goods_attr = selSizeLayout.getGoods_attr();
        return goods_attr;
    }

    CollectListener collectListener;

    public interface CollectListener{
        void getCollcetStatus(int status);
    }

    public void setCollectListener(CollectListener collectListener){
        this.collectListener = collectListener;
    }

}
