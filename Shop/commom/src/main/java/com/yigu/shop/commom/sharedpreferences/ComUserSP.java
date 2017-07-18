package com.yigu.shop.commom.sharedpreferences;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.yigu.shop.commom.result.MapiMunityUserResult;
import com.yigu.shop.commom.result.MapiUserResult;

/**
 * Created by brain on 2017/3/24.
 */
public class ComUserSP extends AbstractSP{

    private final static String KEY_SP_USER = "com.user";
    private final static String KEY_SP_latitude = "com.latitude";
    private final static String KEY_SP_longitude = "com.longitude";
    private final static String KEY_SP_location = "com.location";
    public ComUserSP(Context context) {
        super(context);
    }

    public void saveUserBean(MapiMunityUserResult userbean) {
        sharedPreferences.edit().putString(KEY_SP_USER, JSONObject.toJSONString(userbean)).commit();
    }

    public MapiMunityUserResult getUserBean() {
        String userJsonStr = sharedPreferences.getString(KEY_SP_USER, null);
        if (TextUtils.isEmpty(userJsonStr)) {
            return null;
        }
        return JSONObject.parseObject(userJsonStr, MapiMunityUserResult.class);
    }

    public void clearUserData() {
        sharedPreferences.edit().remove(KEY_SP_USER).commit();
    }

    public void setLatitude(String latitude){
        sharedPreferences.edit().putString(KEY_SP_latitude,latitude).commit();
    }

    public void setLongitude(String longitude){
        sharedPreferences.edit().putString(KEY_SP_longitude,longitude).commit();
    }

    public String getLatitude(){
        String latitude = sharedPreferences.getString(KEY_SP_latitude, "30.738361358642578");
        return latitude;
    }

    public String getLongitude(){
        String longitude = sharedPreferences.getString(KEY_SP_longitude, "120.73665618896484");
        return longitude;
    }

    public void setLocation(String location){
        sharedPreferences.edit().putString(KEY_SP_location,location).commit();
    }

    public String getLocation(){
        String location = sharedPreferences.getString(KEY_SP_location, "浙江省嘉兴市南湖区");
        return location;
    }

}
