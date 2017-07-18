package com.yigu.shop.commom.api;

/**
 * Created by brain on 2016/6/14.
 */
public class BasicApi {
    //http://115.159.118.182/djd/app/app.php?
    public static String BASIC_URL = "http://shop.autek.top/app/app.php?";
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
    public static String getAddresses = "act=/address/list";
    /**获取品牌列表*/
    public static String getArea = "act=/region/getallregionlist";//
    /**新增地址*/
    public static String addAddress = "act=/address/add";
    /**商品详情*/
    public static String GoodDetail = "act=/goods/getdetail";
    /**注册 获取短信验证码*/
    public static String getPhoneCode = "act=/user/getphonecode";
    /**注册*/
    public static String register = "act=/user/signup";
    /**购物车 新增商品*/
    public static String addCart = "act=/cart/addcart";
    /**商城详情web*/
    public static String goods_web = "act=/goods/getdesc&goods_id=";
    /**购物车列表*/
    public static String getCartGoods = "act=/cart/Getcartlist";
    /**购物车 修改数量*/
    public static String setCartGoodsNum = "/Cart/setCartGoodsNum";
    /**购物车 删除购物车记录*/
    public static String deleteCart = "/Cart/deleteCart";
    /**订单 新增订单*/
    public static String addOrder = "/Order/add";
    /**地址 修改地址*/
    public static String modifyAddress = "act=/address/edit";
    /**地址 删除地址*/
    public static String delAddresses = "act=/address/del";
    /**忘记密码*/
    public static String passwordreset = "act=/user/passwordreset";
    /**收藏商品*/
    public static String collect = "act=/goods/collect";
    /**取消收藏*/
    public static String delcollect = "act=/goods/delcollect";
    /**搜索列表*/
    public static String search = "act=/main/search";
    /**商品价格计算*/
    public static String price = "act=/goods/price";
    /**购物车指定商品数量变化*/
    public static String updatecart = "act=/cart/updatecart";
    /**批量删除购物车商品*/
    public static String droplist = "act=/cart/droplist";
    /**设置默认地址*/
    public static String setdefault = "act=/address/setdefault";
    /**收藏列表*/
    public static String getcollectlist = "act=/goods/getcollectlist";
    /**获取地址详情*/
    public static String addressdetail = "act=/address/info";
    /**获取商品评论*/
    public static String getcomment = "act=/goods/getcomment";
    /**获取店铺列表*/
    public static String sellerlist = "act=/main/sellerlist";
    /**获取订单确认页数据*/
    public static String checkorder = "act=/order/checkorder";
    /**提交订单*/
    public static String orderdown = "act=/order/down";
    /**分类页*/
    public static String categorylist = "act=/main/categorylist";
    /**分类页子类*/
    public static String categorylistbyid = "act=/main/categorylistbyid";
    /**购物帮助*/
    public static String help = "act=/region/help";
    /**详细帮助信息*/
    public static String article = "act=/region/Helpdetail&article_id=";
    /**订单列表*/
    public static String getorderlist = "act=/order/getorderlist";
    /**取消订单*/
    public static String ordercancel = "act=/order/cancel";
    /**订单详情*/
    public static String orderdetail = "act=/order/orderdetail";
    /**确认收货*/
    public static String setshipping = "act=/order/setshipping";
    /**我的订单-付款*/
    public static String orderpay = "act=/order/pay";
    /**我的信息*/
    public static String getinfo = "act=/user/getinfo";



    /**社区案例列表*/
    public static String topiclist = "http://www.autek.top/mobcent/app/web/index.php?r=forum/topiclist";
    /**交流案例列表*/
    public static String postlist = "http://www.autek.top/mobcent/app/web/index.php?r=forum/postlist";
    /**评论*/
    public static String topicadmin = "http://www.autek.top/mobcent/app/web/index.php?r=forum/topicadmin";
    /**帖子搜索*/
    public static String forumsearch = "http://www.autek.top/mobcent/app/web/index.php?r=forum/search";
    /**文章搜索*/
    public static String portalsearch = "http://www.autek.top/mobcent/app/web/index.php?r=portal/search";
    /**文章详情*/
    public static String portalnewsview = "http://www.autek.top/mobcent/app/web/index.php?r=portal/newsview";
    /**用户搜索*/
    public static String searchuser = "http://www.autek.top/mobcent/app/web/index.php?r=user/searchuser";
    /**获取社区token*/
    public static String userlogin = "http://www.autek.top/mobcent/app/web/index.php?r=user/login";
    /**关注*/
    public static String useradmin = "http://www.autek.top/mobcent/app/web/index.php?r=user/useradmin";
    /**我的关注*/
    public static String userlist = "http://www.autek.top/mobcent/app/web/index.php?r=user/userlist";
    /**我的发表*/
    public static String usertopiclist = "http://www.autek.top/mobcent/app/web/index.php?r=user/topiclist";
    /**个人资料*/
    public static String userinfo = "http://www.autek.top/mobcent/app/web/index.php?r=user/userinfo";
    /**招聘*/
    public static String zhaopin = "http://www.autek.top/mobcent/app/web/index.php?r=app/zhaopin";

}
