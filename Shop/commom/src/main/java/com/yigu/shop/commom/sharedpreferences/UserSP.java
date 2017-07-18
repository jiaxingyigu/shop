package com.yigu.shop.commom.sharedpreferences;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.yigu.shop.commom.result.MapiUserResult;

/**
 * Created by brain on 2016/6/14.
 */
public class UserSP extends AbstractSP {

    private final static String KEY_SP_USER = "jgj.user";
    private final static String KEY_SP_USER_GUIDE = "user_guide";
    private final static String KEY_SP_Resources = "jgj.resources";
    private final static String KEY_SP_Alias = "user_Alias";
    private final static String KEY_SP_Addr = "user_addr";
    private final static String KEY_SP_UserName = "user_name";
    private final static String KEY_SP_UserPsd = "user_psd";
    private final static String KEY_SP_munity_token = "munity_token";
    private final static String KEY_SP_munity_secret = "munity_secret";

    public UserSP(Context context) {
        super(context);
    }

    public void setMunityToken(String munity_token){
        sharedPreferences.edit().putString(KEY_SP_munity_token, munity_token).commit();
    }

    public void setMunitySecret(String munity_secret){
        sharedPreferences.edit().putString(KEY_SP_munity_secret, munity_secret).commit();
    }

    public String getMunityToken(){
        return sharedPreferences.getString(KEY_SP_munity_token,"");
    }

    public String getMunitySecret(){
        return sharedPreferences.getString(KEY_SP_munity_secret,"");
    }

    public void saveUserBean(MapiUserResult userbean) {
        sharedPreferences.edit().putString(KEY_SP_USER, JSONObject.toJSONString(userbean)).commit();
    }

    public void setUserName(String userName){
        sharedPreferences.edit().putString(KEY_SP_UserName, userName).commit();
    }

    public void setUserPsd(String psd){
        sharedPreferences.edit().putString(KEY_SP_UserPsd, psd).commit();
    }

    public String getUserName(){
        return sharedPreferences.getString(KEY_SP_UserName,"");
    }

    public String getUserPsd(){
        return sharedPreferences.getString(KEY_SP_UserPsd,"");
    }

    public MapiUserResult getUserBean() {
        String userJsonStr = sharedPreferences.getString(KEY_SP_USER, null);
        if (TextUtils.isEmpty(userJsonStr)) {
            return null;
        }
        return JSONObject.parseObject(userJsonStr, MapiUserResult.class);
    }

    public void saveResource(String json){
        sharedPreferences.edit().putString(KEY_SP_Resources, json).commit();
    }

    public String getResource() {
        String resourceJsonStr = sharedPreferences.getString(KEY_SP_Resources, null);
        if (TextUtils.isEmpty(resourceJsonStr)) {
            return null;
        }
        return resourceJsonStr;
    }

    public void setAlias(boolean isAlias){
        sharedPreferences.edit().putBoolean(KEY_SP_Alias, isAlias).commit();
    }

    public boolean getAlias(){
        boolean isAlias = sharedPreferences.getBoolean(KEY_SP_Alias,false);
        return isAlias;
    }

    public void setAddr(String json){
        sharedPreferences.edit().putString(KEY_SP_Addr, json).commit();

    }

    public String getAddr(){
        return sharedPreferences.getString(KEY_SP_Addr,"");
    }

    public boolean checkLogin() {
        return getUserBean() != null && !TextUtils.isEmpty(getUserBean().getId());
    }

    public void clearUserData() {
        sharedPreferences.edit().remove(KEY_SP_USER).commit();
        sharedPreferences.edit().remove(KEY_SP_Alias).commit();
    }

    /**
     * 保存版本
     *
     * @param value
     */
    public void saveUserGuide(String value) {
        sharedPreferences.edit().putString(KEY_SP_USER_GUIDE, value).commit();
    }

    /**
     * 获取版本
     *
     * @return
     */
    public String getUserGuide() {
        String code = sharedPreferences.getString(KEY_SP_USER_GUIDE, null);
        if (TextUtils.isEmpty(code)) {
            return null;
        }
        return code;
    }

}
