package com.yigu.shop.activity.community.job;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.api.CommunityApi;
import com.yigu.shop.commom.result.MapiJobResult;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback2;
import com.yigu.shop.commom.widget.MainToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ComJobDetailActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.pay)
    TextView pay;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.views)
    TextView views;
    @Bind(R.id.no)
    TextView no;
    @Bind(R.id.region)
    TextView region;
    @Bind(R.id.xueli)
    TextView xueli;
    @Bind(R.id.type)
    TextView type;
    @Bind(R.id.sex)
    TextView sex;
    @Bind(R.id.work_nx)
    TextView workNx;
    @Bind(R.id.renshu)
    TextView renshu;
    @Bind(R.id.desc)
    TextView desc;
    @Bind(R.id.send_ll)
    RelativeLayout sendLL;
    @Bind(R.id.company)
    TextView company;

    String id = "";
    boolean isShowBottom = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_job_detail);
        ButterKnife.bind(this);
        if(null!=getIntent()){
            id = getIntent().getStringExtra("id");
            isShowBottom = getIntent().getBooleanExtra("isShowBottom",true);
        }
        if(!TextUtils.isEmpty(id)){
            initView();
            load();
        }
    }

    private void initView() {
        center.setText("职位详情");
        back.setImageResource(R.mipmap.back);

        if(!isShowBottom)
            sendLL.setVisibility(View.GONE);
    }

    private void load(){
        showLoading();
        CommunityApi.zhaopinView(this, id, new RequestCallback<MapiJobResult>() {
            @Override
            public void success(MapiJobResult success) {
                hideLoading();
                if(null!=success){
                    company.setText(TextUtils.isEmpty(success.getGongsi())?"":success.getGongsi());
                    title.setText(TextUtils.isEmpty(success.getTitle())?"":success.getTitle());
                    pay.setText(TextUtils.isEmpty(success.getPay())?"":success.getPay());
                    time.setText(TextUtils.isEmpty(success.getTime())?"":success.getTime());
                    views.setText(TextUtils.isEmpty(success.getViews())?"0":success.getViews()+" 次浏览");
                    no.setText("信息编号 "+(TextUtils.isEmpty(success.getNo())?"":success.getNo()));
                    region.setText(TextUtils.isEmpty(success.getRegion())?"":success.getRegion());
                    xueli.setText(TextUtils.isEmpty(success.getXueli())?"":success.getXueli());
                    type.setText(TextUtils.isEmpty(success.getType())?"":success.getType());
                    sex.setText(TextUtils.isEmpty(success.getSex())?"":success.getSex());
                    workNx.setText(TextUtils.isEmpty(success.getWork_nx())?"":success.getWork_nx());
                    renshu.setText(TextUtils.isEmpty(success.getRenshu())?"":success.getRenshu());
                    desc.setText(TextUtils.isEmpty(success.getDesc())?"":success.getDesc());

                }
            }
        }, new RequestExceptionCallback2() {
            @Override
            public void error(String code, String message) {
                hideLoading();
                MainToast.showShortToast(message);
            }
        });
    }

    @OnClick({R.id.back, R.id.send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.send:
                send();
                break;
        }
    }

    private void send(){
        showLoading();
        CommunityApi.zhaopinSend(this, id, new RequestCallback() {
            @Override
            public void success(Object success) {
                hideLoading();
                MainToast.showShortToast("投递成功");
                finish();
            }
        }, new RequestExceptionCallback2() {
            @Override
            public void error(String code, String message) {
                hideLoading();
                MainToast.showShortToast(message);
            }
        });
    }

}
