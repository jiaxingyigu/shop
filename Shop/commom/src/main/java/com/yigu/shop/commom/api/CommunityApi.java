package com.yigu.shop.commom.api;

import android.app.Activity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yigu.shop.commom.result.MapiContentResult;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.result.MapiJobResult;
import com.yigu.shop.commom.result.MapiMunityResult;
import com.yigu.shop.commom.result.MapiMunityUserResult;
import com.yigu.shop.commom.result.MapiUserResult;
import com.yigu.shop.commom.util.Constants;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.commom.util.MapiComUtil;
import com.yigu.shop.commom.util.MapiUtil;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback2;
import com.yigu.shop.commom.util.RequestPageCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by brain on 2017/3/15.
 */
public class CommunityApi extends BasicApi{

    /**
     * 案例列表
     * @param activity
     * @param filterId
     * @param boardId
     * @param sortby
     * @param isImageList
     * @param page
     * @param pageSize
     * @param callback
     * @param exceptionCallback
     */
    public static void topiclist(Activity activity, String filterId, String boardId, String sortby,String isImageList, String page,String pageSize, final RequestPageCallback callback, final RequestExceptionCallback2 exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("filterId",filterId);
        params.put("boardId",boardId);
        params.put("filterId",filterId);
        params.put("sortby",sortby);
        params.put("isImageList",isImageList);
        params.put("page",page);
        params.put("pageSize",pageSize);
        params.put("filterType","typeid");
        MapiComUtil.getInstance().call(activity,topiclist,params,new MapiComUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                Gson gson = new Gson();
                List<MapiMunityResult> result = gson.fromJson(json.getJSONArray("list").toJSONString(), new TypeToken<List<MapiMunityResult>>(){}.getType());//JSONArray.parseArray(json.getJSONObject("data").getJSONArray("goods_list").toJSONString(),MapiItemResult.class);
                Integer count = json.getInteger("total_num");
                DebugLog.i("count===>"+count);
                if(null!=count){
                    callback.success(count,result);
                }
            }
        },new MapiComUtil.MapiFailResponse(){
            @Override
            public void fail(String code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 案例详情
     * @param activity
     * @param topicId
     * @param boardId
     * @param callback
     * @param exceptionCallback
     */
    public static void postlist(Activity activity, String topicId, String boardId, final RequestCallback callback, final RequestExceptionCallback2 exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("topicId",topicId);
        params.put("boardId",boardId);
        MapiComUtil.getInstance().call(activity,postlist,params,new MapiComUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiComUtil.MapiFailResponse(){
            @Override
            public void fail(String code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 评论
     * @param activity
     * @param json
     * @param callback
     * @param exceptionCallback
     */
    public static void topicadmin(Activity activity,String json, final RequestCallback callback, final RequestExceptionCallback2 exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("json",json);
        params.put("act","reply");
        DebugLog.i(params.toString());
        MapiComUtil.getInstance().call(activity,topicadmin,params,new MapiComUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiComUtil.MapiFailResponse(){
            @Override
            public void fail(String code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });

    }

    /**
     * 社区搜索-帖子
     * @param activity
     * @param keyword
     * @param page
     * @param pageSize
     * @param callback
     * @param exceptionCallback
     */
    public static void forumsearch(Activity activity,String keyword,String page,String pageSize,final RequestPageCallback callback, final RequestExceptionCallback2 exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("keyword",keyword);
        params.put("page",page);
        params.put("pageSize",pageSize);

        MapiComUtil.getInstance().call(activity,forumsearch,params,new MapiComUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                Gson gson = new Gson();
                List<MapiMunityResult> result = gson.fromJson(json.getJSONArray("list").toJSONString(), new TypeToken<List<MapiMunityResult>>(){}.getType());//JSONArray.parseArray(json.getJSONObject("data").getJSONArray("goods_list").toJSONString(),MapiItemResult.class);
                Integer count = json.getInteger("total_num");
                DebugLog.i("count===>"+count);
                if(null!=count){
                    callback.success(count,result);
                }
            }
        },new MapiComUtil.MapiFailResponse(){
            @Override
            public void fail(String code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });

    }

    /**
     * 社区搜索-文章
     * @param activity
     * @param keyword
     * @param page
     * @param pageSize
     * @param callback
     * @param exceptionCallback
     */
    public static void portalsearch(Activity activity,String keyword,String page,String pageSize,final RequestPageCallback callback, final RequestExceptionCallback2 exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("keyword",keyword);
        params.put("page",page);
        params.put("pageSize",pageSize);

        MapiComUtil.getInstance().call(activity,portalsearch,params,new MapiComUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                Gson gson = new Gson();
                List<MapiMunityResult> result = gson.fromJson(json.getJSONArray("list").toJSONString(), new TypeToken<List<MapiMunityResult>>(){}.getType());//JSONArray.parseArray(json.getJSONObject("data").getJSONArray("goods_list").toJSONString(),MapiItemResult.class);
                Integer count = json.getInteger("total_num");
                DebugLog.i("count===>"+count);
                if(null!=count){
                    callback.success(count,result);
                }
            }
        },new MapiComUtil.MapiFailResponse(){
            @Override
            public void fail(String code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 文章详情
     * @param activity
     * @param json
     * @param callback
     * @param exceptionCallback
     */
    public static void portalnewsview(Activity activity, String json, final RequestCallback callback, final RequestExceptionCallback2 exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("json",json);
        MapiComUtil.getInstance().call(activity,portalnewsview,params,new MapiComUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiComUtil.MapiFailResponse(){
            @Override
            public void fail(String code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 社区搜索-用户
     * @param activity
     * @param keyword
     * @param page
     * @param pageSize
     * @param callback
     * @param exceptionCallback
     */
    public static void searchuser(Activity activity,String keyword,String page,String pageSize,final RequestPageCallback callback, final RequestExceptionCallback2 exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("keyword",keyword);
        params.put("page",page);
        params.put("pageSize",pageSize);

        MapiComUtil.getInstance().call(activity,searchuser,params,new MapiComUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                Gson gson = new Gson();
                List<MapiMunityUserResult> result = gson.fromJson(json.getJSONObject("body").getJSONArray("list").toJSONString(), new TypeToken<List<MapiMunityUserResult>>(){}.getType());//JSONArray.parseArray(json.getJSONObject("data").getJSONArray("goods_list").toJSONString(),MapiItemResult.class);
                Integer count = json.getInteger("total_num");
                DebugLog.i("count===>"+count);
                if(null!=count){
                    callback.success(count,result);
                }
            }
        },new MapiComUtil.MapiFailResponse(){
            @Override
            public void fail(String code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 社区登录
     * @param activity
     * @param username
     * @param password
     * @param callback
     * @param exceptionCallback
     */
    public static void userlogin(Activity activity,String username,String password,final RequestCallback callback, final RequestExceptionCallback2 exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("username",username);
        params.put("password",password);
        params.put("type","login");
        MapiComUtil.getInstance().call(activity,userlogin,params,new MapiComUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                Gson gson = new Gson();
                MapiMunityUserResult result = gson.fromJson(json.toJSONString(), new TypeToken<MapiMunityUserResult>(){}.getType());
                callback.success(result);
            }
        },new MapiComUtil.MapiFailResponse(){
            @Override
            public void fail(String code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     *
     * @param activity
     * @param uid
     * @param callback
     * @param exceptionCallback
     */
    public static void useradmin(Activity activity,String uid,String type,final RequestCallback callback, final RequestExceptionCallback2 exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("uid",uid);
        params.put("type",type);
        MapiComUtil.getInstance().call(activity,useradmin,params,new MapiComUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiComUtil.MapiFailResponse(){
            @Override
            public void fail(String code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 我的关注
     * @param activity
     * @param page
     * @param pageSize
     * @param callback
     * @param exceptionCallback
     */
    public static void userlist(Activity activity,String page,String pageSize,final RequestPageCallback callback, final RequestExceptionCallback2 exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("page",page);
        params.put("pageSize",pageSize);
        params.put("type","follow");

        MapiComUtil.getInstance().call(activity,userlist,params,new MapiComUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                Gson gson = new Gson();
                List<MapiMunityUserResult> result = gson.fromJson(json.getJSONArray("list").toJSONString(), new TypeToken<List<MapiMunityUserResult>>(){}.getType());//JSONArray.parseArray(json.getJSONObject("data").getJSONArray("goods_list").toJSONString(),MapiItemResult.class);
                Integer count = json.getInteger("total_num");
                DebugLog.i("count===>"+count);
                if(null!=count){
                    callback.success(count,result);
                }
            }
        },new MapiComUtil.MapiFailResponse(){
            @Override
            public void fail(String code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }


    /**
     * 我的发表
     * @param activity
     * @param type
     * @param isImageList
     * @param page
     * @param pageSize
     * @param callback
     * @param exceptionCallback
     */
    public static void usertopiclist(Activity activity,String uid,String type,String isImageList, String page,String pageSize,final RequestPageCallback callback, final RequestExceptionCallback2 exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("uid",uid);
        params.put("type",type);
        params.put("isImageList",isImageList);
        params.put("page",page);
        params.put("pageSize",pageSize);

        MapiComUtil.getInstance().call(activity,usertopiclist,params,new MapiComUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                Gson gson = new Gson();
                List<MapiMunityResult> result = gson.fromJson(json.getJSONArray("list").toJSONString(), new TypeToken<List<MapiMunityResult>>(){}.getType());//JSONArray.parseArray(json.getJSONObject("data").getJSONArray("goods_list").toJSONString(),MapiItemResult.class);
                Integer count = json.getInteger("total_num");
                DebugLog.i("count===>"+count);
                if(null!=count){
                    callback.success(count,result);
                }
            }
        },new MapiComUtil.MapiFailResponse(){
            @Override
            public void fail(String code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });

    }

    /**
     * 获取用户资料
     * @param activity
     * @param userId
     * @param callback
     * @param exceptionCallback
     */
    public static void userinfo(Activity activity,String userId,final RequestCallback callback, final RequestExceptionCallback2 exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("userId",userId);
        MapiComUtil.getInstance().call(activity,userinfo,params,new MapiComUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                Gson gson = new Gson();
                MapiMunityUserResult result = gson.fromJson(json.toJSONString(), new TypeToken<MapiMunityUserResult>(){}.getType());
                List<MapiContentResult> profileList = gson.fromJson(json.getJSONObject("body").getJSONArray("profileList").toJSONString(), new TypeToken<List<MapiContentResult>>(){}.getType());
                if(null!=profileList)
                    result.setProfileList(profileList);
                List<MapiContentResult> creditList = gson.fromJson(json.getJSONObject("body").getJSONArray("creditList").toJSONString(), new TypeToken<List<MapiContentResult>>(){}.getType());
                if(null!=creditList)
                    result.setCreditList(creditList);
                callback.success(result);
            }
        },new MapiComUtil.MapiFailResponse(){
            @Override
            public void fail(String code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 招聘列表
     * @param activity
     * @param page
     * @param callback
     * @param exceptionCallback
     */
    public static void zhaopin(Activity activity,String search,String page,final RequestPageCallback callback, final RequestExceptionCallback2 exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("type","list");
        params.put("search",search);
        params.put("page",page);

        MapiComUtil.getInstance().call(activity,zhaopin,params,new MapiComUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                Gson gson = new Gson();
                List<MapiJobResult> result = gson.fromJson(json.getJSONArray("list").toJSONString(), new TypeToken<List<MapiJobResult>>(){}.getType());//JSONArray.parseArray(json.getJSONObject("data").getJSONArray("goods_list").toJSONString(),MapiItemResult.class);
                Integer count = json.getJSONObject("page_info").getInteger("count");
                DebugLog.i("count===>"+count);
                if(null!=count){
                    callback.success(count,result);
                }
            }
        },new MapiComUtil.MapiFailResponse(){
            @Override
            public void fail(String code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 招聘详情
     * @param activity
     * @param id
     * @param callback
     * @param exceptionCallback
     */
    public static void zhaopinView(Activity activity,String id,final RequestCallback callback, final RequestExceptionCallback2 exceptionCallback){

        Map<String,String> params = new HashMap<>();
        params.put("type","view");
        params.put("id",id);
        MapiComUtil.getInstance().call(activity,zhaopin,params,new MapiComUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                Gson gson = new Gson();
                MapiJobResult result = gson.fromJson(json.getJSONObject("info").toJSONString(), new TypeToken<MapiJobResult>(){}.getType());
                callback.success(result);
            }
        },new MapiComUtil.MapiFailResponse(){
            @Override
            public void fail(String code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 发送简历
     * @param activity
     * @param lid
     *            招聘启示ID
     * @param callback
     * @param exceptionCallback
     */
    public static void zhaopinSend(Activity activity,String lid,final RequestCallback callback, final RequestExceptionCallback2 exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("type","send");
        params.put("lid",lid);
        MapiComUtil.getInstance().call(activity,zhaopin,params,new MapiComUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiComUtil.MapiFailResponse(){
            @Override
            public void fail(String code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 简历基础信息
     * @param activity
     * @param callback
     * @param exceptionCallback
     */
    public static void zhaopinBase(Activity activity,final RequestCallback callback, final RequestExceptionCallback2 exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("type","base");
        MapiComUtil.getInstance().call(activity,zhaopin,params,new MapiComUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiComUtil.MapiFailResponse(){
            @Override
            public void fail(String code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }


    /**
     * 新增/修改简历
     * @param activity
     * @param name
     * @param sex
     * @param ymd
     * @param xueli
     * @param work_nx
     * @param region
     * @param region1
     * @param tel
     * @param email
     * @param my_introduction
     * @param pos
     * @param pos1
     * @param work_jl
     * @param callback
     * @param exceptionCallback
     */
    public static void zhaopinAddsume(Activity activity,String name,String sex,String ymd,String xueli,String work_nx,String region,
                                      String region1,String tel,String email,String my_introduction,String pos,String pos1,
                                      String work_jl,final RequestCallback callback, final RequestExceptionCallback2 exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("type","addsume");
        params.put("name",name);
        params.put("sex",sex);
        params.put("ymd",ymd);
        params.put("xueli",xueli);
        params.put("work_nx",work_nx);
        params.put("region",region);
        params.put("region1",region1);
        params.put("tel",tel);
        params.put("email",email);
        params.put("my_introduction",my_introduction);
        params.put("pos",pos);
        params.put("pos1",pos1);
        params.put("work_jl",work_jl);


        MapiComUtil.getInstance().call(activity,zhaopin,params,new MapiComUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiComUtil.MapiFailResponse(){
            @Override
            public void fail(String code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 好多大咖
     * @param activity
     * @param page
     * @param callback
     * @param exceptionCallback
     */
    public static void zhaopinUser(Activity activity,String page,final RequestPageCallback callback, final RequestExceptionCallback2 exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("type","user");
        params.put("page",page);

        MapiComUtil.getInstance().call(activity,zhaopin,params,new MapiComUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                Gson gson = new Gson();
                List<MapiMunityUserResult> result = gson.fromJson(json.getJSONArray("list").toJSONString(), new TypeToken<List<MapiMunityUserResult>>(){}.getType());//JSONArray.parseArray(json.getJSONObject("data").getJSONArray("goods_list").toJSONString(),MapiItemResult.class);
                Integer count = json.getJSONObject("page_info").getInteger("count");
                DebugLog.i("count===>"+count);
                if(null!=count){
                    callback.success(count,result);
                }
            }
        },new MapiComUtil.MapiFailResponse(){
            @Override
            public void fail(String code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 发表
     * @param activity
     * @param json
     * @param callback
     * @param exceptionCallback
     */
    public static void topicadminnew(Activity activity,String json, final RequestCallback callback, final RequestExceptionCallback2 exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("json",json);
        params.put("act","new");
        DebugLog.i(params.toString());
        MapiComUtil.getInstance().call(activity,topicadmin,params,new MapiComUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiComUtil.MapiFailResponse(){
            @Override
            public void fail(String code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });

    }

    /**
     * 招聘编辑
     * @param activity
     * @param uid
     * @param callback
     * @param exceptionCallback
     */
    public static void topicadminedit(Activity activity,String uid, final RequestCallback callback, final RequestExceptionCallback2 exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("uid",uid);
        params.put("type","edit");
        DebugLog.i(params.toString());
        MapiComUtil.getInstance().call(activity,zhaopin,params,new MapiComUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiComUtil.MapiFailResponse(){
            @Override
            public void fail(String code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });

    }

    /**
     * 求职列表
     * @param activity
     * @param page
     * @param callback
     * @param exceptionCallback
     */
    public static void zhaopinsentlist(Activity activity,String page,final RequestPageCallback callback, final RequestExceptionCallback2 exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("type","sentlist");

        MapiComUtil.getInstance().call(activity,zhaopin,params,new MapiComUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                Gson gson = new Gson();
                List<MapiJobResult> result = gson.fromJson(json.getJSONArray("list").toJSONString(), new TypeToken<List<MapiJobResult>>(){}.getType());//JSONArray.parseArray(json.getJSONObject("data").getJSONArray("goods_list").toJSONString(),MapiItemResult.class);
                Integer count = json.getJSONObject("page_info").getInteger("count");
                DebugLog.i("count===>"+count);
                if(null!=count){
                    callback.success(count,result);
                }
            }
        },new MapiComUtil.MapiFailResponse(){
            @Override
            public void fail(String code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

}
