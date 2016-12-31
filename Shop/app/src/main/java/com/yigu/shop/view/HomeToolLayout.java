package com.yigu.shop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yigu.shop.R;
import com.yigu.shop.commom.result.MapiResourceResult;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brain on 2016/8/31.
 */
public class HomeToolLayout extends RelativeLayout {
    @Bind(R.id.host)
    ImageView host;
    @Bind(R.id.news)
    ImageView news;
    @Bind(R.id.fine)
    ImageView fine;
    private Context mContext;
    private View view;

    private int selPostion = -1;

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

    @OnClick({R.id.host, R.id.news, R.id.fine})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.host:
                if(selPostion==0) {
                    selPostion = -1;
                    host.setImageResource(R.mipmap.host);
                }else {
                    selPostion = 0;
                    host.setImageResource(R.mipmap.host_sel);
                    news.setImageResource(R.mipmap.news);
                    fine.setImageResource(R.mipmap.fine);
                }
                setListener(selPostion);
                break;
            case R.id.news:
                if(selPostion==1) {
                    selPostion = -1;
                    news.setImageResource(R.mipmap.news);
                }else {
                    selPostion = 1;
                    news.setImageResource(R.mipmap.news_sel);
                    host.setImageResource(R.mipmap.host);
                    fine.setImageResource(R.mipmap.fine);
                }
                setListener(selPostion);
                break;
            case R.id.fine:
                if(selPostion==2) {
                    selPostion = -1;
                    fine.setImageResource(R.mipmap.fine);
                }else {
                    selPostion = 2;
                    fine.setImageResource(R.mipmap.fine_sel);
                    host.setImageResource(R.mipmap.host);
                    news.setImageResource(R.mipmap.news);
                }
                setListener(selPostion);
                break;
        }
    }

    private void setStatus(){
        if(selPostion==0){

        }
    }

    public interface TypeListener{
        void getType(String type);
    }

    private TypeListener typeListener;

    public void setTypeListener(TypeListener typeListener){
        this.typeListener = typeListener;
    }

    private void setListener(int selPostion){
        String type = "";
        if(selPostion<0)
            type = "";
        else if(selPostion==0)
            type = "host";
        else if(selPostion==1)
            type = "news";
        else if(selPostion==2)
            type = "fine";
        if(null!=typeListener)
            typeListener.getType(type);
    }

}
