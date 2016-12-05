package com.yigu.shop.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TopPopWindow extends PopupWindow implements PopupWindow.OnDismissListener{

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private LayoutInflater inflater = null;
    private View contentView = null;
    private int mWidth = 0;
    private Context mContext;
    List<MapiResourceResult> list = new ArrayList<>();
    private PopAdapter mAdapter;
    Activity activity;
    private int pos = -1;
    public TopPopWindow(Activity context, int width, List<MapiResourceResult> list, int style) {
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        activity = context;
        mWidth = width;
        this.list = list;
        initTopLayout(style);//R.style.PopupWindowAnimation
    }

    private void initTopLayout(int animaStyle) {
        contentView = inflater.inflate(R.layout.pop_top, null);
        this.setContentView(contentView);
        ButterKnife.bind(this, contentView);
        if (mWidth == 0)
            this.setWidth(LayoutParams.MATCH_PARENT);
        else
            // 设置弹出窗体的宽
            this.setWidth(mWidth);
        // 设置弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置弹出窗体可点击
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
        recyclerView.addItemDecoration(new DividerListItemDecoration(mContext,LinearLayoutManager.HORIZONTAL, DPUtil.dip2px(1),mContext.getResources().getColor(R.color.divider_line)));//分割线
        mAdapter = new PopAdapter(mContext);
        recyclerView.setAdapter(mAdapter);
    }

    private RecyOnItemClickListener mOnPopItemClickListener;

    /**
     * 设置item的点击监听事件
     */
    public void setOnPopItemClickListener(RecyOnItemClickListener l) {
        mOnPopItemClickListener = l;
    }

    @Override
    public void onDismiss() {
        DebugLog.i("pop==onDismiss");
        if (null != mOnPopItemClickListener)
            mOnPopItemClickListener.onItemClick(null,0);
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {

        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent,parent.getLayoutParams().width / 2, 0);
        } else {
            this.dismiss();
        }
    }

    class PopAdapter extends RecyclerView.Adapter<PopAdapter.ViewHolder>{
        private LayoutInflater inflater;
        public PopAdapter(Context context) {
            inflater = inflater.from(context);
        }

        @Override
        public int getItemCount() {
            return list==null?0:list.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(inflater.inflate(R.layout.item_pop_top,parent,false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.text.setText(list.get(position).getTitle());
            holder.text.setTag(position);
            holder.text.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(pos==(Integer)view.getTag())
                        pos = -1;
                    else
                        pos = (Integer)view.getTag();
                    if (null != mOnPopItemClickListener)
                        mOnPopItemClickListener.onItemClick(view, pos);
                    notifyDataSetChanged();
                    dismiss();
                }
            });
            if(pos==position){
                holder.text.setTextColor(Color.parseColor("#ffffff"));
                holder.text.setBackgroundColor(Color.parseColor("#cc333333"));
            }else{
                holder.text.setTextColor(Color.parseColor("#333333"));
                holder.text.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            @Bind(R.id.text)
            TextView text;
            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }
        }

    }

}
