package com.yigu.shop.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.result.MapiValueResult;
import com.yigu.shop.commom.util.DPUtil;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/12/8.
 */
public class SelSizeViewLayout extends RelativeLayout {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.flowLayout)
    FlowLayout flowLayout;
    private Context mContext;
    private View rootView;

    List<MapiValueResult> list = new ArrayList<>();
    int selPos = -1;

   /* SelSizeItemAdapter mAdapter;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;*/

    public SelSizeViewLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public SelSizeViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public SelSizeViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        rootView = LayoutInflater.from(mContext).inflate(R.layout.layout_sel_size_view, this);
        ButterKnife.bind(this, rootView);

      /*  GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new SelSizeItemAdapter(mContext,list);
        recyclerView.setAdapter(mAdapter);*/

    }


    public void load(final String titleStr,List<MapiValueResult> attrs) {
        if (list.isEmpty()) {
            list.addAll(attrs);
           /* if(null!=mAdapter)
                mAdapter.notifyDataSetChanged();*/

            for (int i=0;i<list.size();i++) {

                final MapiValueResult resourceResult = list.get(i);

                final TextView textView = new TextView(mContext);
                FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(
                        FlowLayout.LayoutParams.WRAP_CONTENT, DPUtil.dip2px(26));
                params.setMargins(10, 10, 10, 10);
                textView.setLayoutParams(params);
                textView.setPadding(DPUtil.dip2px(15), DPUtil.dip2px(4), DPUtil.dip2px(15), DPUtil.dip2px(4));
                textView.setText(resourceResult.getLabel());
                textView.setGravity(Gravity.CENTER);
                textView.setMaxLines(1);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                if (resourceResult.isSel()) {
                    textView.setTextColor(mContext.getResources().getColor(R.color.light_yellow));
                    textView.setBackgroundResource(R.drawable.rect_stroke_yellow__width_1_round_4);
                } else {
                    textView.setTextColor(mContext.getResources().getColor(R.color.shop_black));
                    textView.setBackgroundResource(R.drawable.rect_stroke_gray__width_1_round_4);
                }
                textView.setTag(i);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(selPos>=0){
                            if(selPos==(Integer) view.getTag()){
                                textView.setTextColor(mContext.getResources().getColor(R.color.shop_black));
                                textView.setBackgroundResource(R.drawable.rect_stroke_gray__width_1_round_4);
                                selPos = -1;
                                if(null!=selSizeInterface)
                                    selSizeInterface.sizeDetail(rootView,"","0","");
                            }else{
                                TextView oldView = (TextView) flowLayout.getChildAt(selPos);
                                oldView.setTextColor(mContext.getResources().getColor(R.color.shop_black));
                                oldView.setBackgroundResource(R.drawable.rect_stroke_gray__width_1_round_4);
                                textView.setTextColor(mContext.getResources().getColor(R.color.light_yellow));
                                textView.setBackgroundResource(R.drawable.rect_stroke_yellow__width_1_round_4);
                                selPos = (Integer) view.getTag();
                                if(null!=selSizeInterface)
                                    selSizeInterface.sizeDetail(rootView,titleStr+":"+resourceResult.getLabel(),TextUtils.isEmpty(resourceResult.getPrice())?"":resourceResult.getPrice(),resourceResult.getId());
                            }
                        }else{
                            textView.setTextColor(mContext.getResources().getColor(R.color.light_yellow));
                            textView.setBackgroundResource(R.drawable.rect_stroke_yellow__width_1_round_4);
                            selPos = (Integer) view.getTag();
                            if(null!=selSizeInterface)
                                selSizeInterface.sizeDetail(rootView,titleStr+":"+resourceResult.getLabel(),TextUtils.isEmpty(resourceResult.getPrice())?"":resourceResult.getPrice(),resourceResult.getId());
                        }

                    }
                });
                flowLayout.addView(textView);
            }

        }

    }

    public void setTitleTV(String titleStr){
        title.setText(titleStr);
    }

    private SelSizeInterface selSizeInterface;

    public interface SelSizeInterface{
         void sizeDetail(View view,String goods_attr,String price,String attr_id);
    }

    public void setSelSizeListener(SelSizeInterface selSizeInterface){
        this.selSizeInterface = selSizeInterface;
    }


}
