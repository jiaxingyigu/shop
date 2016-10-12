package com.yigu.shop.commom.api;

import android.app.Activity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yigu.shop.commom.result.MapiShopResult;
import com.yigu.shop.commom.result.MapiSortResult;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.commom.util.MapiUtil;
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

}
