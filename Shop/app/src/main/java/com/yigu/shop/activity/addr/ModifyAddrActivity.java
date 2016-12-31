package com.yigu.shop.activity.addr;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.yigu.shop.R;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.api.ItemApi;
import com.yigu.shop.commom.result.MapiAddrResult;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.widget.CityDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyAddrActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.area_tv)
    TextView areaTv;
    @Bind(R.id.consignee_tv)
    EditText consigneeTv;
    @Bind(R.id.mobile_tv)
    EditText mobileTv;
    @Bind(R.id.address_tv)
    EditText addressTv;

    private CityDialog cityDiolog = null;
    private String province = "";//省
    private String city = "";//市
    private String county = "";//区

    private String provinceID = "";//省
    private String cityID = "";//市
    private String countyID = "";//区

    MapiAddrResult mapiAddrResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_addr);
        ButterKnife.bind(this);
        if(null!=getIntent()){
            mapiAddrResult = (MapiAddrResult) getIntent().getSerializableExtra("item");
        }
        if(null!=mapiAddrResult){
            initView();
            if (TextUtils.isEmpty(userSP.getAddr()))
                load();
        }

    }

    private void initView() {
        back.setImageResource(R.mipmap.back);
        center.setText("修改地址");
        tvRight.setText("确认");

        consigneeTv.setText(mapiAddrResult.getConsignee());
        mobileTv.setText(mapiAddrResult.getTel());

        province = mapiAddrResult.getProvince_name();
        city = mapiAddrResult.getCity_name();
        county = mapiAddrResult.getDistrict_name();

        provinceID = mapiAddrResult.getProvince();
        cityID = mapiAddrResult.getCity();
        countyID = mapiAddrResult.getDistrict();

        areaTv.setText(province + "-" + city + "-" + county);
        addressTv.setText(mapiAddrResult.getAddress());

    }

    private void load() {
        ItemApi.getArea(this, new RequestCallback<JSONObject>() {
            @Override
            public void success(JSONObject success) {
                userSP.setAddr(success.toJSONString());
            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                MainToast.showShortToast(message);
            }
        });
    }


    @OnClick({R.id.back, R.id.tv_right, R.id.area_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_right:
                addAddr();
                break;
            case R.id.area_rl:
                if (!TextUtils.isEmpty(userSP.getAddr()))
                    getAddress(userSP.getAddr());
                else
                    MainToast.showShortToast("暂无地区信息");
                break;
        }
    }

    private void getAddress(String json) {
        setWindowBackgroundBlure(true);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        if (null == cityDiolog) {
            cityDiolog = new CityDialog(this, json, new CityDialog.PriorityListener() {

                @Override
                public void refreshPriorityUI(String proviceName, String cityName,
                                              String districtName) {

                }

                @Override
                public void refreshPriorityUI(String proviceName, String cityName, String districtName, String proviceId, String cityId, String districtId) {
                    province = proviceName;
                    city = cityName;
                    county = districtName;
                    areaTv.setText(province + "-" + city + "-" + county);
                    provinceID = proviceId;
                    cityID = cityId;
                    countyID = districtId;
                }
            }, width, height, "请选择省市区", province, city, county);//选择日期
        }

        Window window = cityDiolog.getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
        //		window.setWindowAnimations(R.style.AnimationPreview3); // 添加动画
        cityDiolog.setCancelable(true);
        cityDiolog.setCanceledOnTouchOutside(true);
        cityDiolog.show();
        cityDiolog.setOnDismissListener(new Dialog.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                setWindowBackgroundBlure(false);
            }
        });
    }

    // 设置弹出popupwindow后屏幕的背景透明度
    private void setWindowBackgroundBlure(boolean blure) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = blure ? 0.8f : 1;
        getWindow().setAttributes(lp);
    }

    private void addAddr() {
        String consignee = consigneeTv.getText().toString();
        String tel = mobileTv.getText().toString();
        String address = addressTv.getText().toString();
        if(TextUtils.isEmpty(consignee)) {
            MainToast.showShortToast("请输入收货人名称");
            return;
        }

        if(TextUtils.isEmpty(tel)){
            MainToast.showShortToast("请输入联系电话");
            return;
        }

        if(TextUtils.isEmpty(provinceID)||TextUtils.isEmpty(cityID)||TextUtils.isEmpty(countyID)){
            MainToast.showShortToast("请选择所在地区");
            return;
        }

        if(TextUtils.isEmpty(address)){
            MainToast.showShortToast("请输入详细地址");
            return;
        }

        DebugLog.i("provinceID=>"+provinceID+",cityID=>"+cityID+",countyID=>"+countyID);

        ItemApi.modifyAddress(this,mapiAddrResult.getAddress_id(), consignee, tel, provinceID, cityID, countyID, address, new RequestCallback() {
            @Override
            public void success(Object success) {
                setResult(RESULT_OK);
                finish();
            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                MainToast.showShortToast(message);
            }
        });


    }

}
