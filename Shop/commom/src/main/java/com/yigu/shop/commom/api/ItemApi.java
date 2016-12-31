package com.yigu.shop.commom.api;

import android.app.Activity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yigu.shop.commom.result.MapiAddrResult;
import com.yigu.shop.commom.result.MapiCartResult;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.result.MapiShopResult;
import com.yigu.shop.commom.result.MapiSortResult;
import com.yigu.shop.commom.result.MapiUserResult;
import com.yigu.shop.commom.result.ProvinceModel;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.commom.util.MapiUtil;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.util.RequestPageCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by brain on 2016/9/26.
 */
public class ItemApi extends BasicApi{

    public static void getcat(Activity activity, String page, String size, final RequestPageCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("page",page);
        params.put("size",size);
        MapiUtil.getInstance().call(activity,getcat,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                List<MapiSortResult> result = JSONArray.parseArray(json.getJSONArray("data").toJSONString(),MapiSortResult.class);
                String count = json.getString("count");
                if(null!=count){
                    callback.success(Integer.parseInt(count),result);
                }

            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 分类 获取品牌列表
     * @param activity
     * @param page
     * @param size
     * @param callback
     * @param exceptionCallback
     */
    public static void getBrands(Activity activity, String page, String size, final RequestPageCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("page",page);
        params.put("size",size);
        MapiUtil.getInstance().call(activity,getBrands,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                List<MapiSortResult> result = JSONArray.parseArray(json.getJSONArray("data").toJSONString(),MapiSortResult.class);
                String count = json.getString("count");
                if(null!=count){
                    callback.success(Integer.parseInt(count),result);
                }

            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 发现 获取店铺列表
     * @param activity
     * @param page
     * @param size
     * @param city 县或者区 例如南湖区
     * @param callback
     * @param exceptionCallback
     */
    public static void getshops(Activity activity, String page, String size,String city, final RequestPageCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("page",page);
        params.put("size",size);
        params.put("city",city);
        MapiUtil.getInstance().call(activity,getshops,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                List<MapiShopResult> result = JSONArray.parseArray(json.getJSONArray("data").toJSONString(),MapiShopResult.class);
                String count = json.getString("count");
                if(null!=count){
                    callback.success(Integer.parseInt(count),result);
                }

            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * @param activity
     * @param page
     * @param size
     * @param seller_id
     *          店铺id
     * @param type
     *          默认all全部 new最新 hot最热 best精品
     * @param callback
     * @param exceptionCallback
     */
    public static void shopDetail(Activity activity, String page, String size,String seller_id, String type,final RequestPageCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("page",page);
        params.put("size",size);
        params.put("seller_id",seller_id);
        params.put("type",type);
        MapiUtil.getInstance().call(activity,shopDetail,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                List<MapiItemResult> result = JSONArray.parseArray(json.getJSONArray("data").toJSONString(),MapiItemResult.class);
                String count = json.getString("count");
                if(null!=count){
                    callback.success(Integer.parseInt(count),result);
                }

            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 获取商品列表
     * @param activity
     * @param cat_id
     * @param type
     * @param page
     * @param callback
     * @param exceptionCallback
     */
    public static void getGoods(Activity activity,String cat_id,String seller_id,String brand,String type, String page, final RequestPageCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("page",page);
        params.put("c_id",cat_id);
        params.put("seller_id",seller_id);
        params.put("brand",brand);
        params.put("type",type);
        MapiUtil.getInstance().call(activity,getGoods,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                Gson gson = new Gson();
                List<MapiItemResult> result = gson.fromJson(json.getJSONObject("data").getJSONArray("goods_list").toJSONString(), new TypeToken<List<MapiItemResult>>(){}.getType());//JSONArray.parseArray(json.getJSONObject("data").getJSONArray("goods_list").toJSONString(),MapiItemResult.class);
                String count = json.getJSONObject("data").getJSONObject("page_info").getString("page_count");
                DebugLog.i("count===>"+count);
                if(null!=count){
                    callback.success(Integer.parseInt(count),result);
                }

            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 首页接口
     * @param activity
     * @param callback
     * @param exceptionCallback
     */
    public static void indexUrl(Activity activity, final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        MapiUtil.getInstance().call(activity,indexUrl,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);

            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 获取地址列表
     * @param activity
     * @param user_id
     * @param callback
     * @param exceptionCallback
     */
    public static void getAddresses(Activity activity, String user_id,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("user_id",user_id);
        MapiUtil.getInstance().call(activity,getAddresses,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                List<MapiAddrResult> result = JSONArray.parseArray(json.getJSONArray("data").toJSONString(),MapiAddrResult.class);
                callback.success(result);
            }
        },new MapiUtil.MapiFailResponse(){

            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 获取省市区列表
     * @param activity
     * @param callback
     * @param exceptionCallback
     */
    public static void getArea(Activity activity,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        MapiUtil.getInstance().call(activity,getArea,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 新增地址
     * @param activity
     * @param user_id
     * @param consignee
     * @param tel
     * @param province
     * @param city
     * @param district
     * @param address
     * @param callback
     * @param exceptionCallback
     */
    public static void addAddress(Activity activity,String user_id,String consignee,String tel,String province,String city,String district,
                                  String address,final RequestCallback callback,final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("consignee",consignee);
        params.put("tel",tel);
        params.put("province",province);
        params.put("city",city);
        params.put("district",district);
        params.put("address",address);
        MapiUtil.getInstance().call(activity,addAddress,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });

    }

    /**
     * 商品详情
     * @param activity
     * @param goods_id
     * @param seller_id
     * @param callback
     * @param exceptionCallback
     */
    public static void GoodDetail(Activity activity,String goods_id,String seller_id,final RequestCallback callback,final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("goods_id",goods_id);
        params.put("seller_id",seller_id);
        MapiUtil.getInstance().call(activity,GoodDetail,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 购物车 新增商品
     * @param activity
     * @param user_id
     * @param goods_id
     * @param goods_sn
     * @param goods_name
     * @param market_price
     * @param goods_price
     * @param goods_number
     * @param goods_attr
     * @param is_real
     * @param extension_code
     * @param goods_attr_id
     * @param callback
     * @param exceptionCallback
     */
    public static void addCart(Activity activity,String user_id,String goods_id,String goods_sn,String goods_name,String market_price,String goods_price,
                               String goods_number,String goods_attr,String is_real,String extension_code,String goods_attr_id,final RequestCallback callback,final RequestExceptionCallback exceptionCallback){

        Map<String,String> params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("goods_id",goods_id);
        params.put("goods_sn",goods_sn);
        params.put("goods_name",goods_name);
        params.put("market_price",market_price);
        params.put("goods_price",goods_price);
        params.put("goods_number",goods_number);
        params.put("goods_attr",goods_attr);
        params.put("is_real",is_real);
        params.put("extension_code",extension_code);
        params.put("goods_attr_id",goods_attr_id);


        MapiUtil.getInstance().call(activity,addCart,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });

    }

    /**
     * 购物车列表
     * @param activity
     * @param user_id
     * @param callback
     * @param exceptionCallback
     */
    public static void getCartGoods(Activity activity,String user_id,final RequestPageCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("user_id",user_id);
        MapiUtil.getInstance().call(activity,getCartGoods,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                Gson gson = new Gson();
                List<MapiCartResult> result = gson.fromJson(json.getJSONArray("data").toJSONString(), new TypeToken<List<MapiCartResult>>(){}.getType());
                callback.success(0,result);
            }
        },new MapiUtil.MapiFailResponse(){

            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 购物车 修改数量
     * @param activity
     * @param rec_id
     * @param num
     * @param callback
     * @param exceptionCallback
     */
    public static void setCartGoodsNum(Activity activity,String rec_id,String num,final RequestCallback callback,final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("rec_id",rec_id);
        params.put("num",num);
        MapiUtil.getInstance().call(activity,setCartGoodsNum,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 购物车 删除购物车记录
     * @param activity
     * @param rec_ids
     * @param callback
     * @param exceptionCallback
     */
    public static void deleteCart(Activity activity,String rec_ids,final RequestCallback callback,final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("rec_ids",rec_ids);
        MapiUtil.getInstance().call(activity,deleteCart,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 订单 新增订单
     * @param activity
     * @param user_id
     * @param consignee
     * @param province
     * @param city
     * @param district
     * @param address
     * @param tel
     * @param pay_id
     * @param pay_name
     * @param goods_amount
     * @param pay_fee
     * @param callback
     * @param exceptionCallback
     */
    public static void addOrder(Activity activity,String user_id,String consignee,String province,String city,String district,
                                String address,String tel,String pay_id,String pay_name,String goods_amount,
                                String pay_fee,final RequestCallback callback,final RequestExceptionCallback exceptionCallback){

        Map<String,String> params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("consignee",consignee);
        params.put("province",province);
        params.put("city",city);
        params.put("district",district);
        params.put("address",address);
        params.put("tel",tel);
        params.put("pay_id",pay_id);
        params.put("pay_name",pay_name);
        params.put("goods_amount",goods_amount);
        params.put("pay_fee",pay_fee);

        MapiUtil.getInstance().call(activity,addOrder,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });

    }


    /**
     * 修改地址
     * @param activity
     * @param address_id
     * @param consignee
     * @param tel
     * @param province
     * @param city
     * @param district
     * @param address
     * @param callback
     * @param exceptionCallback
     */
    public static void modifyAddress(Activity activity,String address_id,String consignee,String tel,String province,String city,String district,
                                  String address,final RequestCallback callback,final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("address_id",address_id);
        params.put("consignee",consignee);
        params.put("tel",tel);
        params.put("province",province);
        params.put("city",city);
        params.put("district",district);
        params.put("address",address);
        MapiUtil.getInstance().call(activity,modifyAddress,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });

    }

    /**
     *地址 删除地址
     * @param activity
     * @param address_id
     * @param callback
     * @param exceptionCallback
     */
    public static void delAddresses(Activity activity,String address_id,final RequestCallback callback,final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("address_id",address_id);
        MapiUtil.getInstance().call(activity,delAddresses,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

}
