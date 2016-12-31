package com.yigu.shop.commom.api;

import android.app.Activity;

import com.alibaba.fastjson.JSONObject;
import com.yigu.shop.commom.result.MapiUserResult;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.commom.util.MapiUtil;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by brain on 2016/6/16.
 */
public class UserApi extends BasicApi{

    public static void login(Activity activity, String username, String password, final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("name",username);
        params.put("password",password);
        MapiUtil.getInstance().call(activity,login,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                MapiUserResult result = JSONObject.parseObject(json.getJSONObject("data").toJSONString(),MapiUserResult.class);
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

    /**
     * 注册 获取短信验证码
     * @param activity
     * @param phone
     * @param callback
     * @param exceptionCallback
     */
    public static void getPhoneCode(Activity activity,String phone,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("phone",phone);
        MapiUtil.getInstance().call(activity,getPhoneCode,params,new MapiUtil.MapiSuccessResponse(){
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
     * 注册
     * @param activity
     * @param phone
     * @param code
     * @param password
     * @param mark
     * @param callback
     * @param exceptionCallback
     */
    public static void register(Activity activity,String phone,String code,String password,String mark,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("phone",phone);
        params.put("code",code);
        params.put("password",password);
        params.put("mark",mark);
        MapiUtil.getInstance().call(activity,register,params,new MapiUtil.MapiSuccessResponse(){
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
     * 忘记密码
     * @param activity
     * @param phone
     * @param code
     * @param password
     * @param mark
     * @param callback
     * @param exceptionCallback
     */
    public static void passwordreset(Activity activity,String phone,String code,String password,String mark,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("phone",phone);
        params.put("code",code);
        params.put("password",password);
        params.put("mark",mark);
        DebugLog.i(params.toString());
        MapiUtil.getInstance().call(activity,passwordreset,params,new MapiUtil.MapiSuccessResponse(){
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
