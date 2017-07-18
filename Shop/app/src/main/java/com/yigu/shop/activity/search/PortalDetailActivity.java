package com.yigu.shop.activity.search;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PortalDetailActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipRefreshLayout)
    BestSwipeRefreshLayout swipRefreshLayout;

    private List<IndexData> mList;
    AudioManager mAudioManager;
    ComDetailAdapter mAdapter;

    private String aid = "";
    private String json = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal_detail);
        ButterKnife.bind(this);
        if(null!=getIntent()){
            aid = getIntent().getStringExtra("aid");
        }

        if(!TextUtils.isEmpty(aid)){
            json = " {aid:"+aid+",page:1}";
            initView();
            initListener();
            load();
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
        CommunityApi.portalnewsview(this,json, new RequestCallback<JSONObject>() {
            @Override
            public void success(JSONObject success) {
                swipRefreshLayout.setRefreshing(false);
                hideLoading();
                try {
                    String tilte = success.getJSONObject("body").getJSONObject("newsInfo").getString("catName");
                    center.setText(TextUtils.isEmpty(tilte) ? "" : tilte);
                    Gson gson = new Gson();
                    MapiTopicResult result = gson.fromJson(success.getJSONObject("body").getJSONObject("newsInfo").toJSONString(), new TypeToken<MapiTopicResult>() {
                    }.getType());

                    mList.add(new IndexData(0, "HEAD", result));
                    mList.add(new IndexData(1, "PORTAL", result));
                    mAdapter.notifyDataSetChanged();
                }catch (Exception e){
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

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
