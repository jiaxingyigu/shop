package com.yigu.shop.activity.community;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yigu.shop.R;
import com.yigu.shop.adapter.community.ComDetailAdapter;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.api.CommunityApi;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiReplyResult;
import com.yigu.shop.commom.result.MapiTopicResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback2;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.widget.BestSwipeRefreshLayout;
import com.yigu.shop.widget.DividerListItemDecoration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ComDetailActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipRefreshLayout)
    BestSwipeRefreshLayout swipRefreshLayout;
    @Bind(R.id.reply_et)
    EditText replyEt;


    ComDetailAdapter mAdapter;

    private List<IndexData> mList;

    String topicId = "";
    String boardId = "";

    AudioManager mAudioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_detail);
        ButterKnife.bind(this);
        if (null != getIntent()) {
            topicId = getIntent().getStringExtra("topicId");
            boardId = getIntent().getStringExtra("boardId");
        }

    }

    private void initView() {
        back.setImageResource(R.mipmap.back);

        mList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.addItemDecoration(new DividerListItemDecoration(this, OrientationHelper.HORIZONTAL, DPUtil.dip2px(8), getResources().getColor(R.color.divider_line)));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ComDetailAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

    }

    private void initListener() {
        swipRefreshLayout.setBestRefreshListener(new BestSwipeRefreshLayout.BestRefreshListener() {
            @Override
            public void onBestRefresh() {
                refreshData();
            }
        });
    }

    private void load() {
        showLoading();
        CommunityApi.postlist(this, topicId, boardId, new RequestCallback<JSONObject>() {
            @Override
            public void success(JSONObject success) {
                swipRefreshLayout.setRefreshing(false);
                hideLoading();
                try {
                    String tilte = success.getString("forumName");
                    center.setText(TextUtils.isEmpty(tilte) ? "" : tilte);
                    Gson gson = new Gson();
                    MapiTopicResult result = gson.fromJson(success.getJSONObject("topic").toJSONString(), new TypeToken<MapiTopicResult>() {
                    }.getType());
                    List<MapiReplyResult> list = gson.fromJson(success.getJSONArray("list").toJSONString(), new TypeToken<List<MapiReplyResult>>() {
                    }.getType());
                    mList.add(new IndexData(0, "HEAD", result));
                    mList.add(new IndexData(1, "TOPIC", result));
                    mList.add(new IndexData(2, "ITEM", list));
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new RequestExceptionCallback2() {
            @Override
            public void error(String code, String message) {
                swipRefreshLayout.setRefreshing(false);
                hideLoading();
                MainToast.showShortToast(message);
            }
        });
    }

    public void refreshData() {
        if (null != mList) {
            mList.clear();
            mAdapter.notifyDataSetChanged();
            load();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!TextUtils.isEmpty(topicId)) {
            initView();
            initListener();
            load();
        }
    }

    boolean isPause = false;

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
        requestAudioFocus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAudioManager.abandonAudioFocus(audioFocusChangeListener);
    }

    private void requestAudioFocus() {
        int result = mAudioManager.requestAudioFocus(audioFocusChangeListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            DebugLog.i("audio focus been granted");
        }
    }

    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            DebugLog.i("focusChange: " + focusChange);
            if (isPause && focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                requestAudioFocus();
            }
        }
    };

    @OnClick({R.id.back, R.id.send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.send:
                if(TextUtils.isEmpty(replyEt.getText().toString())){
                    MainToast.showShortToast("请输入您的评论");
                    return;
                }
                showLoading();
                JSONObject jsonObject = new JSONObject();
                JSONObject bodyObject = new JSONObject();
                JSONObject jsonsObject = new JSONObject();
                jsonsObject.put("fid",boardId);
                jsonsObject.put("tid",topicId);
                jsonsObject.put("location",comUserSP.getLocation());
                jsonsObject.put("aid","");
                jsonsObject.put("longitude",comUserSP.getLongitude());
                jsonsObject.put("latitude",comUserSP.getLatitude());
                jsonsObject.put("isHidden",0);
                jsonsObject.put("isAnonymous",0);
                jsonsObject.put("isOnlyAuthor",0);
                jsonsObject.put("isShowPostion",0);
                jsonsObject.put("replyId",0);
                jsonsObject.put("isQuote",0);

                JSONArray jsonArray = new JSONArray();
                JSONObject contentObject = new JSONObject();
                contentObject.put("type",0);
                contentObject.put("infor",replyEt.getText().toString());
                jsonArray.add(contentObject);
                try {
                    jsonsObject.put("content",URLEncoder.encode(jsonArray.toJSONString(),"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                bodyObject.put("json",jsonsObject);
                jsonObject.put("body",bodyObject);

//                StringBuffer stringBuffer = new StringBuffer();
//                stringBuffer.append("{\"body\":{\"json\":{\"fid\":");
//                stringBuffer.append(boardId);
//                stringBuffer.append(",\"tid\":");
//                stringBuffer.append(topicId);
//                stringBuffer.append(",\"location\":\"\",\"aid\":\"\",\"content\":[{\"type\":0,\"infor\":\"");
//                stringBuffer.append(replyEt.getText().toString());
//                stringBuffer.append("\"}],\"longitude\":\"120.73665618896484\",\"latitude\":\"30.738361358642578\",\"isHidden\":0,\"isAnonymous\":0,\"isOnlyAuthor\":0,\"isShowPostion\":0,\"replyId\":0,\"isQuote\":0}}}");
                DebugLog.i(jsonObject.toString());
                CommunityApi.topicadmin(this,jsonObject.toJSONString(), new RequestCallback() {
                    @Override
                    public void success(Object success) {
                        hideLoading();
                        replyEt.setText("");
                        MainToast.showShortToast("评论成功");
                        refreshData();
                    }
                }, new RequestExceptionCallback2() {
                    @Override
                    public void error(String code, String message) {
                        hideLoading();
                        MainToast.showShortToast(message);
                    }
                });
                break;
        }
    }



}
