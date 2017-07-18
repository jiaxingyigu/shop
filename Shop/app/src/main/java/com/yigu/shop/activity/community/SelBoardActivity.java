package com.yigu.shop.activity.community;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelBoardActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.shebei)
    RadioButton shebei;
    @Bind(R.id.dushushe)
    RadioButton dushushe;
    @Bind(R.id.dipan)
    RadioButton dipan;
    @Bind(R.id.shipin)
    RadioButton shipin;
    @Bind(R.id.chitai)
    RadioButton chitai;
    @Bind(R.id.doudong)
    RadioButton doudong;
    @Bind(R.id.paopian)
    RadioButton paopian;
    @Bind(R.id.qita)
    RadioButton qita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_board);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back);
        center.setText("选择模块");

    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }

    @OnClick({R.id.shebei, R.id.dushushe, R.id.dipan, R.id.shipin, R.id.chitai, R.id.doudong, R.id.paopian, R.id.qita})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.shebei:
                intent.putExtra("boardID","2");
                intent.putExtra("boardName","设备培训");
                break;
            case R.id.dushushe:
                intent.putExtra("boardID","41");
                intent.putExtra("boardName","读书社");
                break;
            case R.id.dipan:
                intent.putExtra("boardID","37");
                intent.putExtra("boardName","底盘培训");
                break;
            case R.id.shipin:
                intent.putExtra("boardID","42");
                intent.putExtra("boardName","视频培训");
                break;
            case R.id.chitai:
                intent.putExtra("boardID","48");
                intent.putExtra("boardName","吃胎案例");
                break;
            case R.id.doudong:
                intent.putExtra("boardID","49");
                intent.putExtra("boardName","抖动案例");
                break;
            case R.id.paopian:
                intent.putExtra("boardID","47");
                intent.putExtra("boardName","跑偏案例");
                break;
            case R.id.qita:
                intent.putExtra("boardID","50");
                intent.putExtra("boardName","其他案例");
                break;
        }
        setResult(RESULT_OK,intent);
        finish();
    }
}
