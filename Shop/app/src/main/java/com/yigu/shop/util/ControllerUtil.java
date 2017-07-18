package com.yigu.shop.util;

import android.content.Intent;

import com.yigu.shop.activity.CommunityActivity;
import com.yigu.shop.activity.ForgetActivity;
import com.yigu.shop.activity.GuideActivity;
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
import com.yigu.shop.activity.community.ComChangeActivity;
import com.yigu.shop.activity.community.ComDetailActivity;
import com.yigu.shop.activity.community.ComPersonInfoActivity;
import com.yigu.shop.activity.community.ComSearchActivity;
import com.yigu.shop.activity.community.ComSearchTwoActivity;
import com.yigu.shop.activity.community.FollowListActivity;
import com.yigu.shop.activity.community.MunityListActivity;
import com.yigu.shop.activity.community.TopicListActivity;
import com.yigu.shop.activity.community.job.ComJobDetailActivity;
import com.yigu.shop.activity.community.job.ComJobEditActivity;
import com.yigu.shop.activity.community.job.ComJobHisActivity;
import com.yigu.shop.activity.community.job.ComJobListActivity;
import com.yigu.shop.activity.community.master.MasterListActivity;
import com.yigu.shop.activity.community.service.ServiceDetailActivity;
import com.yigu.shop.activity.community.service.ServiceListActivity;
import com.yigu.shop.activity.order.HisOrderDetailActivity;
import com.yigu.shop.activity.order.MyOrderActivity;
import com.yigu.shop.activity.order.OderDetailActivity;
import com.yigu.shop.activity.products.ProductListActivity;
import com.yigu.shop.activity.purcase.PurcaseActivity;
import com.yigu.shop.activity.purcase.PurcaseListActivity;
import com.yigu.shop.activity.search.PortalDetailActivity;
import com.yigu.shop.activity.shops.ShopDetailActivity;
import com.yigu.shop.activity.webview.WebviewControlActivity;
import com.yigu.shop.commom.application.AppContext;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.result.MapiMunityResult;
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
        Intent intent = new Intent(AppContext.getInstance(), ComSearchTwoActivity.class);
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

    /**
     * 案例详情
     */
    public static void go2ComDetail(String topicId,String boardId) {
        Intent intent = new Intent(AppContext.getInstance(), ComDetailActivity.class);
        intent.putExtra("topicId",topicId);
        intent.putExtra("boardId",boardId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 文章详情
     */
    public static void go2PortalDetail(String aid) {
        Intent intent = new Intent(AppContext.getInstance(), PortalDetailActivity.class);
        intent.putExtra("aid",aid);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 我的关注
     */
    public static void go2FollowList() {
        Intent intent = new Intent(AppContext.getInstance(), FollowListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 我的发表
     */
    public static void go2TopicList() {
        Intent intent = new Intent(AppContext.getInstance(), TopicListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 社区个人信息
     */
    public static void go2ComPersonInfo(String uid,boolean isEdit) {
        Intent intent = new Intent(AppContext.getInstance(), ComPersonInfoActivity.class);
        intent.putExtra("uid",uid);
        intent.putExtra("isEdit",isEdit);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 案列-列表
     */
    public static void go2MunityList(String title,String boardId) {
        Intent intent = new Intent(AppContext.getInstance(),  MunityListActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("boardId",boardId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 求职招聘
     */
    public static void go2ComJobList() {
        Intent intent = new Intent(AppContext.getInstance(),  ComJobListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 求职招聘-详情
     */
    public static void go2ComJobDetail(String id) {
        Intent intent = new Intent(AppContext.getInstance(),  ComJobDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("id",id);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 求职招聘-添加
     */
    public static void go2ComJobEdit() {
        Intent intent = new Intent(AppContext.getInstance(),  ComJobEditActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 大咖列表
     */
    public static void go2MasterList() {
        Intent intent = new Intent(AppContext.getInstance(),  MasterListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 交流切磋
     */
    public static void go2ComChange() {
        Intent intent = new Intent(AppContext.getInstance(),  ComChangeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 服务列表
     */
    public static void go2ServiceList() {
        Intent intent = new Intent(AppContext.getInstance(),  ServiceListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 点评详情
     */
    public static void go2ServiceDetail(String topicId, String boardId, MapiMunityResult mapiMunityResult) {
        Intent intent = new Intent(AppContext.getInstance(), ServiceDetailActivity.class);
        intent.putExtra("topicId",topicId);
        intent.putExtra("boardId",boardId);
        intent.putExtra("item",mapiMunityResult);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 引导页
     */
    public static void go2Guide() {
        Intent intent = new Intent(AppContext.getInstance(), GuideActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 求职列表
     */
    public static void go2ComJobHis() {
        Intent intent = new Intent(AppContext.getInstance(), ComJobHisActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

}
