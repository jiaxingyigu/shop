package com.yigu.shop.commom.api;

/**
 * Created by brain on 2016/6/14.
 */
public class BasicApi {
    //http://115.159.118.182/shop/index.php/ShopApi
    public static String BASIC_URL = "http://115.159.118.182/djd/app/app.php?";
    /**分类-分类*/
    public static String getcat = "/Category/getcat";
    /**分类-品牌*/
    public static String getBrands = "/Brand/getBrands";
    /**发现-身边的店铺*/
    public static String getshops = "/Shop/getshops";
    /***/
    public static String shopDetail  = "/Shop/shopDetail";
    /**登录*/
    public static String login = "act=/user/signin";
    /**获取商品列表*/
    public static String getGoods = "act=/main/list";
    /**首页接口*/
    public static String indexUrl = "act=/main/index";
    /**获取地址列表*/
    public static String getAddresses = "/Address/getAddresses";
    /**获取品牌列表*/
    public static String getArea = "/Address/getArea";
    /**新增地址*/
    public static String addAddress = "/Address/add";
    /**商品详情*/
    public static String GoodDetail = "/Goods/GoodDetail";
    /**注册 获取短信验证码*/
    public static String getPhoneCode = "act=/user/getphonecode";
    /**注册*/
    public static String register = "act=/user/signup";
    /**购物车 新增商品*/
    public static String addCart = "/Cart/add";
    /**商城详情web*/
    public static String goods_web = "/Goods/goods_web/goods_id/";
    /**购物车列表*/
    public static String getCartGoods = "/Cart/getCartGoods";
    /**购物车 修改数量*/
    public static String setCartGoodsNum = "/Cart/setCartGoodsNum";
    /**购物车 删除购物车记录*/
    public static String deleteCart = "/Cart/deleteCart";
    /**订单 新增订单*/
    public static String addOrder = "/Order/add";
    /**地址 修改地址*/
    public static String modifyAddress = "/Address/modify";
    /**地址 删除地址*/
    public static String delAddresses = "/Address/delAddresses";
    /**忘记密码*/
    public static String passwordreset = "act=/user/passwordreset";
}
