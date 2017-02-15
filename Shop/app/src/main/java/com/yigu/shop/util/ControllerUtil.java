package com.yigu.shop.util;

import android.content.Intent;

import com.yigu.shop.activity.CommunityActivity;
import com.yigu.shop.activity.ForgetActivity;
import com.yigu.shop.activity.HelpActivity;
import com.yigu.shop.activity.ItemListActivity;
import com.yigu.shop.activity.LoginActivity;
import com.yigu.shop.activity.MainActivity;
import com.yigu.shop.activity.ProductDetailActivity;
import com.yigu.shop.activity.RegisterActivity;
import com.yigu.shop.activity.SearchActivity;
import com.yigu.shop.activity.SearchListActivity;
import com.yigu.shop.activity.SelPayActivity;
import com.yigu.shop.activity.SettingActivity;
import com.yigu.shop.activity.addr.AddAddrActivity;
import com.yigu.shop.activity.addr.ManageAddrActivity;
import com.yigu.shop.activity.addr.SelAddrActivity;
import com.yigu.shop.activity.collect.CollectListActivity;
import com.yigu.shop.activity.community.ComSearchActivity;
import com.yigu.shop.activity.order.HisOrderDetailActivity;
import com.yigu.shop.activity.order.MyOrderActivity;
import com.yigu.shop.activity.order.OderDetailActivity;
import com.yigu.shop.activity.products.ProductListActivity;
import com.yigu.shop.activity.purcase.PurcaseActivity;
import com.yigu.shop.activity.purcase.PurcaseListActivity;
import com.yigu.shop.activity.shops.ShopDetailActivity;
import com.yigu.shop.activity.webview.WebviewControlActivity;
import com.yigu.shop.commom.application.AppContext;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.result.MapiOrderResult;
import com.yigu.shop.commom.result.MapiShopResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brain on 2016/6/22.
 */
public class ControllerUtil {

    /**
     * 由主页进入购物车
     */
    public static void go2Purcase() {
        Intent intent = new Intent(AppContext.getInstance(), PurcaseActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 由主页进入购物车
     */
    public static void go2PurcaseList() {
        Intent intent = new Intent(AppContext.getInstance(), PurcaseListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 商品详情
     */
    public static void go2ProductDetail(MapiItemResult itemResult) {
        Intent intent = new Intent(AppContext.getInstance(), ProductDetailActivity.class);
        intent.putExtra("item",itemResult);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 店铺详情
     */
    public static void go2ShopDetail(MapiShopResult result){
        Intent intent = new Intent(AppContext.getInstance(), ShopDetailActivity.class);
        intent.putExtra("item",result);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 地址管理
     */
    public static void go2ManageAddr() {
        Intent intent = new Intent(AppContext.getInstance(),ManageAddrActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 添加地址
     */
    public static void go2AddAddr() {
        Intent intent = new Intent(AppContext.getInstance(),AddAddrActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 我的订单
     */
    public static void go2MyOrder(String from,int fragIndex) {
        Intent intent = new Intent(AppContext.getInstance(),MyOrderActivity.class);
        intent.putExtra("from",from);
        intent.putExtra("fragIndex",fragIndex);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 首页进入社区
     */
    public static void go2Community() {
        Intent intent = new Intent(AppContext.getInstance(), CommunityActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }
    /**
    ** 注册
    */
    public static void go2Register() {
        Intent intent = new Intent(AppContext.getInstance(), RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     ** 忘记密码
     */
    public static void go2Forget() {
        Intent intent = new Intent(AppContext.getInstance(), ForgetActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 由登录页进入首页
     */
    public static void go2Main() {
        Intent intent = new Intent(AppContext.getInstance(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 进入登录
     */
    public static void go2Login(){
        Intent intent = new Intent(AppContext.getInstance(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 社区-搜索
     */
    public static void go2ComSearch() {
        Intent intent = new Intent(AppContext.getInstance(), ComSearchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 选择收货地址
     */
    public static void go2SelAddr() {
        Intent intent = new Intent(AppContext.getInstance(), SelAddrActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     *确认订单
     */
    public static void go2OderDetail() {
        Intent intent = new Intent(AppContext.getInstance(),OderDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 设置
     */
    public static void go2Setting() {
        Intent intent = new Intent(AppContext.getInstance(),SettingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 收藏列表
     */
    public static void go2CollectList() {
        Intent intent = new Intent(AppContext.getInstance(),CollectListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 搜索
     */
    public static void go2Search() {
        Intent intent = new Intent(AppContext.getInstance(), SearchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 搜索列表
     */
    public static void go2SearchList(String search) {
        Intent intent = new Intent(AppContext.getInstance(),SearchListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("search",search);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 列表页-activity
     */
    public static void go2ItemList(String c_id,String name) {
        Intent intent = new Intent(AppContext.getInstance(), ItemListActivity.class);
        intent.putExtra("c_id",c_id);
        intent.putExtra("name",name);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 帮助
     */
    public static void go2Help() {
        Intent intent = new Intent(AppContext.getInstance(), HelpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * h5页面
     */
    public static void go2WebView(String url, String title,String shareTitle, boolean isShare) {
        Intent intent = new Intent(AppContext.getInstance(), WebviewControlActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("isShare", isShare);
        intent.putExtra("shareTitle", shareTitle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 选择支付方式
     */
    public static void go2SelPay(MapiOrderResult delapiOrderResult) {
        Intent intent = new Intent(AppContext.getInstance(), SelPayActivity.class);
        intent.putExtra("item",delapiOrderResult);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 我的订单详情
     */
    public static void go2HisOrderDetail(MapiOrderResult itemResult) {
        Intent intent = new Intent(AppContext.getInstance(), HisOrderDetailActivity.class);
        intent.putExtra("item",itemResult);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

}
