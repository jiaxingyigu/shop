package com.yigu.shop.fragment.community;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yigu.shop.R;
import com.yigu.shop.adapter.community.CommunityAdapter;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.commom.api.ItemApi;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.result.MapiShopResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.util.ControllerUtil;
import com.yigu.shop.widget.DividerListItemDecoration;
import com.yigu.updatelibrary.UpdateFunGo;
import com.yigu.updatelibrary.config.DownloadKey;
import com.yigu.updatelibrary.config.UpdateKey;
import com.yigu.updatelibrary.utils.GetAppInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link BaseFrag} subclass.
 */
public class ComIndexFragment extends BaseFrag {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.tv_right_two)
    TextView tvRightTwo;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    CommunityAdapter mAdapter;
    List<IndexData> mList = new ArrayList<>();

    private final static String SCROLL = "SCROLL";
    private final static String TOOL = "TOOL";
    private final static String ITEM = "ITEM";

    public ComIndexFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_com_index, container, false);
        ButterKnife.bind(this, view);
        initView();
        load();
        return view;
    }

    private void initView() {

        back.setImageResource(R.mipmap.logo);
        ivRight.setImageResource(R.mipmap.search);
        tvRightTwo.setText("签到");
        tvRightTwo.setVisibility(View.VISIBLE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.addItemDecoration(new DividerListItemDecoration(getActivity(), OrientationHelper.HORIZONTAL, DPUtil.dip2px(10), getResources().getColor(R.color.divider_line)));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new CommunityAdapter(getActivity(), mList);
        recyclerView.setAdapter(mAdapter);
    }

    public void load() {
        showLoading();
        ItemApi.indexUrl(getActivity(), new RequestCallback<JSONObject>() {
            @Override
            public void success(JSONObject success) {
                hideLoading();
                if (success.isEmpty())
                    return;
                try {

                    List<MapiResourceResult> resourceResults = JSONArray.parseArray(success.getJSONObject("data").getJSONArray("banner").toJSONString(), MapiResourceResult.class);
                    MapiResourceResult mapiResourceResult = JSONObject.parseObject(success.getJSONObject("data").getJSONObject("version_info").toJSONString(),MapiResourceResult.class);
                    if(null!=mapiResourceResult&&!TextUtils.isEmpty(mapiResourceResult.getUrl()))
                        checkVersion(mapiResourceResult);
                    mList.clear();
                    mList.add(new IndexData(0, SCROLL, resourceResults));
                    mList.add(new IndexData(1, TOOL, new ArrayList<MapiResourceResult>()));
                    mList.add(new IndexData(2, ITEM, new ArrayList<MapiShopResult>()));
                    Collections.sort(mList);
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                hideLoading();
                MainToast.showShortToast(message);
            }
        });


    }

    /**
     * 检查版本，若不是最新版本则显示弹框
     *
     * @param result
     */
    private void checkVersion(MapiResourceResult result) {
        if (!GetAppInfo.getAppVersionCode(getActivity()).equals(result.getVersion())) {
            DownloadKey.version = result.getVersion();
            DownloadKey.changeLog = result.getRemark();
            DownloadKey.apkUrl = result.getUrl();
            //如果你想通过Dialog来进行下载，可以如下设置
            UpdateKey.DialogOrNotification= UpdateKey.WITH_DIALOG;
            DownloadKey.ToShowDownloadView = DownloadKey.showUpdateView;
            UpdateFunGo.init(getActivity());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        UpdateFunGo.onResume(getActivity());//现在只能弹框下载
    }

    @Override
    public void onStop() {
        super.onStop();
        UpdateFunGo.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.back, R.id.tv_right_two, R.id.iv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.tv_right_two:
                // TODO: 2017/3/29
                MainToast.showShortToast("正在开发...");
                break;
            case R.id.iv_right:
                ControllerUtil.go2ComSearch();
                break;
        }
    }
}
