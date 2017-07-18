package com.yigu.shop.commom.util;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yigu.shop.commom.api.BasicApi;
import com.yigu.shop.commom.application.AppContext;
import com.yigu.shop.commom.sharedpreferences.ComUserSP;
import com.yigu.shop.commom.sharedpreferences.UserSP;

import java.util.Map;
import java.util.Set;

/**
 * Created by brain on 2017/3/15.
 */
public class MapiComUtil {

    private volatile static MapiComUtil mapiUtil;
    private Map<String, String> head = null;
    private RequestQueue requestQueue;
    protected volatile static ComUserSP comUserSP;
    public static  MapiComUtil getInstance() {
        if (mapiUtil == null) {
            synchronized (MapiUtil.class) {
                mapiUtil = new MapiComUtil();
                comUserSP = new ComUserSP(AppContext.getInstance());
            }
        }
        return mapiUtil;
    }


    private MapiComUtil() {
        requestQueue = Volley.newRequestQueue(AppContext.getInstance());
    }

    /**
     * 网络通信volley
     *
     * @param act      类名，广播
     * @param url      接口地址
     * @param params   传递的参数
     * @param response 成功返回数据的接口
     * @param fail     返回异常的接口
     */
    public void call(final Activity act, final String url, final Map<String,String> params,
                     final MapiSuccessResponse response, final MapiFailResponse fail) {
        if (params != null)
//            DebugLog.i("params=" + params.toString());
            DebugLog.i("url=" + url);
        if(null!=comUserSP.getUserBean()){
//            params.put(Constants.Token, TextUtils.isEmpty(userSP.getUserBean().getToken())?"":userSP.getUserBean().getToken());
//            params.put(Constants.Uid, TextUtils.isEmpty(userSP.getUserBean().getUid())?"":userSP.getUserBean().getUid());

            params.put(Constants.Munity_Token, TextUtils.isEmpty(comUserSP.getUserBean().getToken())?"":comUserSP.getUserBean().getToken());
            params.put(Constants.Munity_Secret, TextUtils.isEmpty(comUserSP.getUserBean().getSecret())?"":comUserSP.getUserBean().getSecret());

            if(TextUtils.isEmpty(params.get(Constants.Munity_uid))){
                params.put(Constants.Munity_uid, TextUtils.isEmpty(comUserSP.getUserBean().getUid())?"":comUserSP.getUserBean().getUid());
            }
//            DebugLog.i("token=>"+comUserSP.getUserBean().getToken());
//            DebugLog.i("userSP.getUserBean().getUid()=>"+comUserSP.getUserBean().getUid());
        }
        DebugLog.i("params=" + params.toString());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    public void onResponse(String s) {
                        DebugLog.i("mapi response" + s);
                        JSONObject jsonObject = JSONObject.parseObject(s);
                        DebugLog.i("jsonObject==>"+(jsonObject==null));
                        JSONObject headJSON = jsonObject.getJSONObject("head");

                        if (null!=headJSON&&("1".equals(jsonObject.getString("rs"))||"00000000".equals(headJSON.getString("errCode")))) {//
                            response.success(jsonObject);
                        }
                        String code = headJSON.getString("errCode");

                        if (!"1".equals(jsonObject.getString("rs"))&&null!=code&&code.equals("00100001")) {
                            DebugLog.i("跳登录页面");
                            //打开登录UI
                            if (act == null) {
                                return;
                            }
                            Intent intent = new Intent();
                            intent.setAction("com.yigu.shop.login");
                            act.sendBroadcast(intent);

                            return;
                        }

                        if (fail != null &&!"1".equals(jsonObject.getString("rs"))&& !code.equals("00000000")) {
                            fail.fail(code, headJSON.getString("errInfo"));//参数不满足条件
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                DebugLog.e("volleyError=" + volleyError);
                if (volleyError != null) {
                    if (volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError) {
                        fail.fail("9999", "oops！网络异常请重新连接");
                    } else {
                        fail.fail("9999", volleyError.getMessage());
                    }
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> heads = initHead();
//
//                return heads;
//            }
        };
        requestQueue.add(stringRequest);
//        requestQueue.start();//初始化的时候就已经调用
    }

    public interface MapiSuccessResponse {

        void success(JSONObject json);

    }

    public interface MapiFailResponse {

        void fail(String code, String failMessage);

    }

}
