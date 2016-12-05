package com.yigu.shop.commom.api;

/**
 * Created by brain on 2016/6/14.
 */
public class BasicApi {
    public static String BASIC_URL = "http://115.159.118.182/shop/index.php/ShopApi";
    /**分类-分类*/
    public static String getcat = "/Category/getcat";
    /**分类-品牌*/
    public static String getBrands = "/Brand/getBrands";
    /**发现-身边的店铺*/
    public static String getshops = "/Shop/getshops";
    /***/
    public static String shopDetail  = "/Shop/shopDetail";
    /**登录*/
    public static String login = "/Login/login";
    /**获取商品列表*/
    public static String getGoods = "/Goods/getGoods";
    /**首页接口*/
    public static String indexUrl = "/Index/index";
    /**获取地址列表*/
    public static String getAddresses = "/Address/getAddresses";
    /**获取品牌列表*/
    public static String getArea = "/Address/getArea";
    /**新增地址*/
    public static String addAddress = "/Address/add";
    /**商品详情*/
    public static String GoodDetail = "/Goods/GoodDetail";
    /**注册 获取短信验证码*/
    public static String getPhoneCode = "/Login/getPhoneCode";
    /**注册*/
    public static String register = "/Login/register";
    /**购物车 新增商品*/
    public static String addCart = "/Cart/add";
}
