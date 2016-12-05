package com.yigu.shop.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yigu.shop.R;
import com.yigu.shop.adapter.SelSizeAdapter;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.view.PurcaseSheetLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/11/25.
 */
public class SelSizePopWindow extends PopupWindow implements PopupWindow.OnDismissListener {

    @Bind(R.id.image)
    SimpleDraweeView image;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.purcaseSheetLayout)
    PurcaseSheetLayout purcaseSheetLayout;
    private Context mContext;
    private LayoutInflater inflater = null;
    List<MapiResourceResult> list = new ArrayList<>();
    private View contentView = null;

    SelSizeAdapter mAdapter;

    private int mWidth = 0;
    private int mHight = 0;

    public SelSizePopWindow(Activity context,List<MapiResourceResult> list, int style) {
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        this.list = list;
        initPopLayout(style);
    }

    private void initPopLayout(int animaStyle) {
        contentView = inflater.inflate(R.layout.pop_sel_size, null);
        this.setContentView(contentView);
        ButterKnife.bind(this, contentView);
        if (mWidth == 0)
            this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        else
            // 设置弹出窗体的宽
            this.setWidth(mWidth);

       /* if (mHight == 0)
            // 设置弹出窗体的高
            this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        else*/
            // 设置弹出窗体的高
            this.setHeight(DPUtil.dip2px(300));

        this.setFocusable(true);
        // 设置允许在外点击消失
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // 设置popupwindow显示和隐藏时的动画
        this.setAnimationStyle(animaStyle);
        setOnDismissListener(this);
        initView();
    }

    private void initView() {

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(manager);
        mAdapter = new SelSizeAdapter(mContext,list);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onDismiss() {

    }

    public void showPopupWindow(View parent,int gravity){
        if (!this.isShowing()) {
            this.showAtLocation(parent, gravity, 0, 0);
        }else{
            this.dismiss();
        }
    }

}
