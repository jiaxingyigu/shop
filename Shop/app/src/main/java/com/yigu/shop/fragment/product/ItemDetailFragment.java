package com.yigu.shop.fragment.product;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.yigu.shop.R;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.util.webview.WebViewUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemDetailFragment extends BaseFrag {


    @Bind(R.id.webview)
    WebView webview;

    String linkUrl = "";

    public ItemDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_item_detail, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        WebSettings webSetting = webview.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setBuiltInZoomControls(false);
        webview.addJavascriptInterface(new WebViewUtil(getActivity(), webview), "app");
    }

    @Override
    public void onResume() {
        super.onResume();
        load();
    }

    public void load(){
        DebugLog.i(linkUrl);
        if(!TextUtils.isEmpty(linkUrl))
            webview.loadUrl(linkUrl, WebViewUtil.getWebviewHeader());//加载网页webview.loadUrl(linkUrl, WebViewUtil.getWebviewHeader());//加载网页
    }

    public void loadWeb(String linkUrl) {
        this.linkUrl = linkUrl;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
