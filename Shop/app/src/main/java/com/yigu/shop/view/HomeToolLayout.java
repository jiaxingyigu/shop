package com.yigu.shop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.yigu.shop.R;
import com.yigu.shop.commom.result.MapiResourceResult;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brain on 2016/8/31.
 */
public class HomeToolLayout extends RelativeLayout {
    private Context mContext;
    private View view;

    public HomeToolLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public HomeToolLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public HomeToolLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_home_tool, this);
        ButterKnife.bind(this, view);
    }

    public void load(List<MapiResourceResult> list) {

    }

    @OnClick({R.id.device, R.id.tool})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.device:
                break;
            case R.id.tool:
                break;
        }
    }
}
