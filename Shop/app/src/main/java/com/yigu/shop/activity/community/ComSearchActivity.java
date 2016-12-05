package com.yigu.shop.activity.community;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;
import com.yigu.shop.widget.TopPopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ComSearchActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.type_tv)
    TextView typeTv;
    @Bind(R.id.content_et)
    EditText contentEt;
    @Bind(R.id.tv_right_two)
    TextView tvRightTwo;

    TopPopWindow topPopWindow;
    List<MapiResourceResult> sList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_search);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back);
        tvRightTwo.setText("搜索");
        tvRightTwo.setVisibility(View.VISIBLE);

        sList.add(new MapiResourceResult(0,"帖子"));
        sList.add(new MapiResourceResult(1,"文章"));
        sList.add(new MapiResourceResult(2,"用户"));
        topPopWindow = new TopPopWindow(this, DPUtil.dip2px(85), sList, R.style.PopupWindowAnimation);

    }

    private void initListener() {
        topPopWindow.setOnPopItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if(null!=view){
                    if(postion>=0) {
                        typeTv.setText(sList.get(postion).getTitle());
                    }else{
                        typeTv.setText("全部");
                    }
                }
            }
        });
    }

    @OnClick({R.id.back, R.id.type_tv, R.id.tv_right_two})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.type_tv:
                topPopWindow.showPopupWindow(view);
                break;
            case R.id.tv_right_two:
                break;
        }
    }
}
