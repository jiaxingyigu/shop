package com.yigu.shop.commom.api;

import android.app.Activity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yigu.shop.commom.result.MapiAddrResult;
import com.yigu.shop.commom.result.MapiCartResult;
import com.yigu.shop.commom.result.MapiHelpResult;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.result.MapiOrderItem;
import com.yigu.shop.commom.result.MapiOrderResult;
import com.yigu.shop.commom.result.MapiShopResult;
import com.yigu.shop.commom.result.MapiSortChildResult;
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
                Integer count = json.getJSONObject("data").getJSONObject("page_info").getInteger("page_count");
                DebugLog.i("count===>"+count);
                if(null!=count){
                    callback.success(count,result);
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
     * @param callback
     * @param exceptionCallback
     */
    public static void getAddresses(Activity activity,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
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
     * @param consignee
     * @param tel
     * @param province
     * @param city
     * @param district
     * @param address
     * @param callback
     * @param exceptionCallback
     */
    public static void addAddress(Activity activity,String consignee,String tel,String province,String city,String district,
                                  String address,String defalut,final RequestCallback callback,final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("name",consignee);
        params.put("mobile",tel);
        params.put("country","1");//中国id
        params.put("province",province);
        params.put("city",city);
        params.put("district",district);
        params.put("address",address);
        params.put("defalut",defalut);
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
     * @param callback
     * @param exceptionCallback
     */
    public static void GoodDetail(Activity activity,String goods_id,final RequestCallback callback,final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("goods_id",goods_id);
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
     * @param goods_id
     * @param goods_attr_id
     * @param callback
     * @param exceptionCallback
     */
    public static void addCart(Activity activity,String goods_id,String goods_number,String goods_attr_id,final RequestCallback callback,final RequestExceptionCallback exceptionCallback){

        Map<String,String> params = new HashMap<>();
        params.put("goods_id",goods_id);
        params.put("number",goods_number);
        params.put("spec",goods_attr_id);
        DebugLog.i(params.toString());
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
     * @param callback
     * @param exceptionCallback
     */
    public static void getCartGoods(Activity activity,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        MapiUtil.getInstance().call(activity,getCartGoods,params,new MapiUtil.MapiSuccessResponse(){
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
                                  String address,String defalut,final RequestCallback callback,final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("address_id",address_id);
        params.put("name",consignee);
        params.put("mobile",tel);
        params.put("country","1");//中国id
        params.put("province",province);
        params.put("city",city);
        params.put("district",district);
        params.put("address",address);
        params.put("defalut",defalut);
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

    /**
     * 收藏商品
     * @param activity
     * @param goods_id
     * @param callback
     * @param exceptionCallback
     */
    public static void collect(Activity activity,String goods_id,final RequestCallback callback,final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("goods_id",goods_id);
        MapiUtil.getInstance().call(activity,collect,params,new MapiUtil.MapiSuccessResponse(){
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
     * 取消收藏商品
     * @param activity
     * @param goods_id
     * @param callback
     * @param exceptionCallback
     */
    public static void delcollect(Activity activity,String goods_id,final RequestCallback callback,final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("goods_id",goods_id);
        MapiUtil.getInstance().call(activity,delcollect,params,new MapiUtil.MapiSuccessResponse(){
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
     * 搜索列表
     * @param activity
     * @param keywords
     * @param page
     * @param callback
     * @param exceptionCallback
     */
    public static void search(Activity activity,String keywords,String page,String sort,String order,final RequestPageCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("keywords",keywords);
        params.put("page",page);
        params.put("sort",sort);
        params.put("order",order);
        MapiUtil.getInstance().call(activity,search,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                Gson gson = new Gson();
                List<MapiItemResult> result = gson.fromJson(json.getJSONObject("data").getJSONArray("goods_list").toJSONString(), new TypeToken<List<MapiItemResult>>(){}.getType());//JSONArray.parseArray(json.getJSONObject("data").getJSONArray("goods_list").toJSONString(),MapiItemResult.class);
                Integer count = json.getJSONObject("data").getJSONObject("page_info").getInteger("page_count");
                DebugLog.i("count===>"+count);
                if(null!=count){
                    callback.success(count,result);
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
     * 商品价格计算
     * @param activity
     * @param goods_id
     * @param attr_id
     * @param number
     * @param callback
     * @param exceptionCallback
     */
    public static void price(Activity activity,String goods_id,String attr_id,String number,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("goods_id",goods_id);
        params.put("attr_id",attr_id);
        params.put("number",number);
        MapiUtil.getInstance().call(activity,price,params,new MapiUtil.MapiSuccessResponse(){
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
     * 购物车指定商品数量变化
     * @param activity
     * @param rec_id
     * @param new_number
     * @param callback
     * @param exceptionCallback
     */
    public static void updatecart(Activity activity,String rec_id,String new_number,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("rec_id",rec_id);
        params.put("new_number",new_number);
        MapiUtil.getInstance().call(activity,updatecart,params,new MapiUtil.MapiSuccessResponse(){
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
     * 批量删除购物车商品
     * @param activity
     * @param drop_rec
     * @param callback
     * @param exceptionCallback
     */
    public static void droplist(Activity activity,String drop_rec,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("drop_rec",drop_rec);
        MapiUtil.getInstance().call(activity,droplist,params,new MapiUtil.MapiSuccessResponse(){
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
     * 设置默认地址
     * @param activity
     * @param address_id
     * @param callback
     * @param exceptionCallback
     */
    public static void setdefault(Activity activity,String address_id,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("address_id",address_id);
        MapiUtil.getInstance().call(activity,setdefault,params,new MapiUtil.MapiSuccessResponse(){
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
     * 收藏列表
     * @param activity
     * @param page
     * @param callback
     * @param exceptionCallback
     */
    public static void getcollectlist(Activity activity,String page,final RequestPageCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("page",page);
        MapiUtil.getInstance().call(activity,getcollectlist,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                Gson gson = new Gson();
                List<MapiItemResult> result = gson.fromJson(json.getJSONObject("data").getJSONArray("list").toJSONString(), new TypeToken<List<MapiItemResult>>(){}.getType());//JSONArray.parseArray(json.getJSONObject("data").getJSONArray("goods_list").toJSONString(),MapiItemResult.class);
                Integer count = json.getJSONObject("data").getJSONObject("page_info").getInteger("page_count");
                DebugLog.i("count===>"+count);
                if(null!=count){
                    callback.success(count,result);
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
     * 获取地址详情
     * @param activity
     * @param address_id
     * @param callback
     * @param exceptionCallback
     */
    public static void addressdetail(Activity activity,String address_id,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("address_id",address_id);
        MapiUtil.getInstance().call(activity,addressdetail,params,new MapiUtil.MapiSuccessResponse(){
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
     *获取商品评论
     * @param activity
     * @param goods_id
     * @param page
     * @param callback
     * @param exceptionCallback
     */
    public static void getcomment(Activity activity,String goods_id,String page,final RequestPageCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("goods_id",goods_id);
        params.put("page",page);
        MapiUtil.getInstance().call(activity,getcomment,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                Gson gson = new Gson();
                List<MapiOrderResult> result = gson.fromJson(json.getJSONObject("data").getJSONArray("list").toJSONString(), new TypeToken<List<MapiOrderResult>>(){}.getType());//JSONArray.parseArray(json.getJSONObject("data").getJSONArray("goods_list").toJSONString(),MapiItemResult.class);
                Integer count = json.getJSONObject("data").getJSONObject("page_info").getInteger("page_count");
                DebugLog.i("count===>"+count);
                if(null!=count){
                    callback.success(count,result);
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
     * 获取店铺列表
     * @param activity
     * @param page
     * @param callback
     * @param exceptionCallback
     */
    public static void sellerlist(Activity activity,String page,final RequestPageCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("page",page);
        MapiUtil.getInstance().call(activity,sellerlist,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                Gson gson = new Gson();
                List<MapiShopResult> result = gson.fromJson(json.getJSONObject("data").getJSONArray("list").toJSONString(), new TypeToken<List<MapiShopResult>>(){}.getType());//JSONArray.parseArray(json.getJSONObject("data").getJSONArray("goods_list").toJSONString(),MapiItemResult.class);
                Integer count = json.getJSONObject("data").getJSONObject("page_info").getInteger("page_count");
                DebugLog.i("count===>"+count);
                if(null!=count){
                    callback.success(count,result);
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
     * 获取订单确认页数据
     * @param activity
     * @param callback
     * @param exceptionCallback
     */
    public static void checkorder(Activity activity,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        MapiUtil.getInstance().call(activity,checkorder,params,new MapiUtil.MapiSuccessResponse(){
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
     * 提交订单
     * @param activity
     * @param shipping_id
     * @param address_id
     * @param pay_name
     * @param callback
     * @param exceptionCallback
     */
    public static void orderdown(Activity activity,String shipping_id,String address_id,String pay_name,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("shipping_id",shipping_id);
        params.put("address_id",address_id);
        params.put("pay_name",pay_name);
        DebugLog.i(params.toString());
        MapiUtil.getInstance().call(activity,orderdown,params,new MapiUtil.MapiSuccessResponse(){
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
     * 分类页
     * @param activity
     * @param callback
     * @param exceptionCallback
     */
    public static void categorylist(Activity activity,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        MapiUtil.getInstance().call(activity,categorylist,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                Gson gson = new Gson();
                List<MapiSortResult> result = gson.fromJson(json.getJSONArray("data").toJSONString(), new TypeToken<List<MapiSortResult>>(){}.getType());//JSONArray.parseArray(json.getJSONObject("data").getJSONArray("goods_list").toJSONString(),MapiItemResult.class);
                if(null!=result){
                    callback.success(result);
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
     * 分类页子类
     * @param activity
     * @param c_id
     * @param callback
     * @param exceptionCallback
     */
    public static void categorylistbyid(Activity activity,String c_id ,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("c_id",c_id);
        MapiUtil.getInstance().call(activity,categorylistbyid,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                Gson gson = new Gson();
                List<MapiSortChildResult> result = gson.fromJson(json.getJSONArray("data").toJSONString(), new TypeToken<List<MapiSortChildResult>>(){}.getType());//JSONArray.parseArray(json.getJSONObject("data").getJSONArray("goods_list").toJSONString(),MapiItemResult.class);
                if(null!=result){
                    callback.success(result);
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
     * 购物帮助
     * @param activity
     * @param callback
     * @param exceptionCallback
     */
    public static void help(Activity activity,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        MapiUtil.getInstance().call(activity,help,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                Gson gson = new Gson();
                List<MapiHelpResult> result = gson.fromJson(json.getJSONArray("data").toJSONString(), new TypeToken<List<MapiHelpResult>>(){}.getType());//JSONArray.parseArray(json.getJSONObject("data").getJSONArray("goods_list").toJSONString(),MapiItemResult.class);
                if(null!=result){
                    callback.success(result);
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
     * 订单列表
     * @param activity
     * @param type
     * @param page
     * @param callback
     * @param exceptionCallback
     */
    public static void getorderlist(Activity activity,String type,String page,final RequestPageCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("type",type);
        params.put("page",page);
        MapiUtil.getInstance().call(activity,getorderlist,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                Gson gson = new Gson();
                List<MapiOrderResult> result = gson.fromJson(json.getJSONObject("data").getJSONArray("list").toJSONString(), new TypeToken<List<MapiOrderResult>>(){}.getType());//JSONArray.parseArray(json.getJSONObject("data").getJSONArray("goods_list").toJSONString(),MapiItemResult.class);
                Integer count = json.getJSONObject("data").getJSONObject("page_info").getInteger("page_count");
                DebugLog.i("count===>"+count);
                if(null!=count){
                    callback.success(count,result);
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
     * 取消订单
     * @param activity
     * @param order_id
     * @param callback
     * @param exceptionCallback
     */
    public static void ordercancel(Activity activity,String order_id,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("order_id",order_id);
        MapiUtil.getInstance().call(activity,ordercancel,params,new MapiUtil.MapiSuccessResponse(){
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
     *订单详情
     * @param activity
     * @param order_id
     * @param callback
     * @param exceptionCallback
     */
    public static void orderdetail(Activity activity,String order_id,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("order_id",order_id);
        MapiUtil.getInstance().call(activity,orderdetail,params,new MapiUtil.MapiSuccessResponse(){
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
     * 确认收货
     * @param activity
     * @param order_id
     * @param callback
     * @param exceptionCallback
     */
    public static void setshipping(Activity activity,String order_id,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("order_id",order_id);
        MapiUtil.getInstance().call(activity,setshipping,params,new MapiUtil.MapiSuccessResponse(){
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
     * 我的订单-付款
     * @param activity
     * @param order_id
     * @param pay_name
     * @param callback
     * @param exceptionCallback
     */
    public static void orderpay(Activity activity,String order_id,String pay_name,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("order_id",order_id);
        params.put("pay_name",pay_name);
        MapiUtil.getInstance().call(activity,orderpay,params,new MapiUtil.MapiSuccessResponse(){
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
     * 我的信息
     * @param activity
     * @param callback
     * @param exceptionCallback
     */
    public static void getinfo(Activity activity,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        MapiUtil.getInstance().call(activity,getinfo,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                Gson gson = new Gson();
                MapiUserResult result = gson.fromJson(json.getJSONObject("data").getJSONObject("user").toJSONString(), new TypeToken<MapiUserResult>(){}.getType());
//                MapiUserResult result = JSONObject.parseObject(json.getJSONObject("data").getJSONObject("user").toJSONString(),MapiUserResult.class);
                String token = json.getJSONObject("data").getString("token");
                String uid = json.getJSONObject("data").getString("uid");
                result.setToken(token);
                result.setUid(uid);
                callback.success(result);
            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

}
