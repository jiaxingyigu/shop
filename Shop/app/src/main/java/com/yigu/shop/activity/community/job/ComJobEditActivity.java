package com.yigu.shop.activity.community.job;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yigu.shop.R;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.api.CommunityApi;
import com.yigu.shop.commom.result.MapiJobInfoResult;
import com.yigu.shop.commom.util.DateUtil;
import com.yigu.shop.commom.util.MapSortUtil;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback2;
import com.yigu.shop.commom.widget.MainToast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ComJobEditActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.birth)
    TextView birth;
    @Bind(R.id.name)
    EditText nameEt;
    @Bind(R.id.xueli)
    TextView xueli;
    @Bind(R.id.work_nx)
    TextView work_nx;
    @Bind(R.id.pos)
    TextView pos;
    @Bind(R.id.addr)
    TextView addr;
    @Bind(R.id.maleRadio)
    RadioButton maleRadio;
    @Bind(R.id.femaleRadio)
    RadioButton femaleRadio;
    @Bind(R.id.genderGroup)
    RadioGroup genderGroup;
    @Bind(R.id.tel)
    EditText tel;
    @Bind(R.id.email)
    EditText email;
    @Bind(R.id.introduction)
    EditText introduction;
    @Bind(R.id.salary)
    EditText salary;
    @Bind(R.id.work_jl)
    EditText workJl;

    TimePickerView pvTime;
    OptionsPickerView xueliOptions;
    OptionsPickerView workOptions;
    OptionsPickerView positionOptions;
    OptionsPickerView regionOptions;

    Map<String, String> xueliMaps;
    Map<String, String> workMaps;

    ArrayList<MapiJobInfoResult> xueliOptions1Items = new ArrayList<>();

    ArrayList<MapiJobInfoResult> workOptions1Items = new ArrayList<>();

    ArrayList<MapiJobInfoResult> posOptions1Items = new ArrayList<>();
    ArrayList<ArrayList<MapiJobInfoResult>> posOptions2Items = new ArrayList<>();

    ArrayList<MapiJobInfoResult> regionOptions1Items = new ArrayList<>();
    ArrayList<ArrayList<MapiJobInfoResult>> regionOptions2Items = new ArrayList<>();

    String sex = "";
    String xueliID = "";
    String work_nx_ID = "";
    String regionID = "";
    String region1ID = "";
    String posID = "";
    String pos1ID = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_job_edit);
        ButterKnife.bind(this);
        initView();
        initListener();
        load();
    }

    private void initView() {

        center.setText("我的简历");
        back.setImageResource(R.mipmap.back);
        tvRight.setText("确认");

        //时间选择器
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
//        Calendar calendar = Calendar.getInstance();
//        pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));//要在setTime 之前才有效果哦
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);


    }

    private void initListener() {
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                birth.setText(DateUtil.getInstance().date2YMD_H(date));
            }
        });

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == maleRadio.getId())
                    sex = "男";
                else if (checkedId == femaleRadio.getId())
                    sex = "女";
            }
        });

    }

    private void load() {
        showLoading();
        CommunityApi.zhaopinBase(this, new RequestCallback<JSONObject>() {
            @Override
            public void success(JSONObject success) {
                hideLoading();
                if (null != success) {
                    Gson gson = new Gson();

                    xueliMaps = gson.fromJson(success.getJSONObject("base_info").getJSONObject("xueli").toJSONString(), new TypeToken<Map<String, String>>() {
                    }.getType());
                    workMaps = gson.fromJson(success.getJSONObject("base_info").getJSONObject("work_nx").toJSONString(), new TypeToken<Map<String, String>>() {
                    }.getType());

                    List<MapiJobInfoResult> positionList = gson.fromJson(success.getJSONObject("base_info").getJSONArray("position").toJSONString(), new TypeToken<List<MapiJobInfoResult>>() {
                    }.getType());

                    List<MapiJobInfoResult> provinceList = gson.fromJson(success.getJSONObject("region_list").getJSONArray("p").toJSONString(), new TypeToken<List<MapiJobInfoResult>>() {
                    }.getType());
                    Map<String, List<MapiJobInfoResult>> cityMaps = gson.fromJson(success.getJSONObject("region_list").getJSONObject("c").toJSONString(), new TypeToken<Map<String, List<MapiJobInfoResult>>>() {
                    }.getType());

                    if (null != xueliMaps) {
                        initXueli(xueliMaps);
                    }

                    if (null != workMaps) {
                        initWork(workMaps);
                    }

                    if (null != positionList) {
                        initPosition(positionList);
                    }

                    if (null != provinceList && null != cityMaps) {
                        initCity(provinceList, cityMaps);
                    }

                    getEditInfo();

                }
            }
        }, new RequestExceptionCallback2() {
            @Override
            public void error(String code, String message) {
                hideLoading();

            }
        });

    }

    private void getEditInfo(){
        showLoading();
        CommunityApi.topicadminedit(ComJobEditActivity.this, comUserSP.getUserBean().getUid(), new RequestCallback() {
            @Override
            public void success(Object success) {
                hideLoading();

            }
        }, new RequestExceptionCallback2() {
            @Override
            public void error(String code, String message) {
                hideLoading();

            }
        });
    }

    private void initXueli(Map<String, String> maps) {
        //选项选择器
        xueliOptions = new OptionsPickerView(this);
        for (Map.Entry<String, String> entry : maps.entrySet()) {
            //Map.entry<Integer,String> 映射项（键-值对）  有几个方法：用上面的名字entry
            //entry.getKey() ;entry.getValue(); entry.setValue();
            //map.entrySet()  返回此映射中包含的映射关系的 Set视图。
//            System.out.println("key= " + entry.getKey() + " and value= "
//                    + entry.getValue());
            MapiJobInfoResult mapiJobInfoResult = new MapiJobInfoResult();
            mapiJobInfoResult.setName(TextUtils.isEmpty(entry.getValue()) ? "" : entry.getValue());
            mapiJobInfoResult.setId(TextUtils.isEmpty(entry.getKey()) ? "" : entry.getKey());
            //选项1
            xueliOptions1Items.add(mapiJobInfoResult);
        }

        //三级联动效果
        xueliOptions.setPicker(xueliOptions1Items);
        //设置选择的三级单位
//        pwOptions.setLabels("省", "市", "区");
//        pvOptions.setTitle("选择城市");
        xueliOptions.setCyclic(false);
        //设置默认选中的三级项目
        //监听确定选择按钮
        xueliOptions.setSelectOptions(1);
        xueliOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                xueli.setText(xueliOptions1Items.get(options1).getPickerViewText());
                xueliID = xueliOptions1Items.get(options1).getId();
            }
        });

    }

    private void initWork(Map<String, String> maps) {
        //选项选择器
        workOptions = new OptionsPickerView(this);
        for (Map.Entry<String, String> entry : maps.entrySet()) {
            //Map.entry<Integer,String> 映射项（键-值对）  有几个方法：用上面的名字entry
            //entry.getKey() ;entry.getValue(); entry.setValue();
            //map.entrySet()  返回此映射中包含的映射关系的 Set视图。
//            System.out.println("key= " + entry.getKey() + " and value= "
//                    + entry.getValue());
            MapiJobInfoResult mapiJobInfoResult = new MapiJobInfoResult();
            mapiJobInfoResult.setName(TextUtils.isEmpty(entry.getValue()) ? "" : entry.getValue());
            mapiJobInfoResult.setId(TextUtils.isEmpty(entry.getKey()) ? "" : entry.getKey());
            //选项1
            workOptions1Items.add(mapiJobInfoResult);
        }

        //三级联动效果
        workOptions.setPicker(workOptions1Items);
        //设置选择的三级单位
//        pwOptions.setLabels("省", "市", "区");
//        pvOptions.setTitle("选择城市");
        workOptions.setCyclic(false);
        //设置默认选中的三级项目
        //监听确定选择按钮
        workOptions.setSelectOptions(1);
        workOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                work_nx.setText(workOptions1Items.get(options1).getPickerViewText());
                work_nx_ID = workOptions1Items.get(options1).getId();
            }
        });

    }

    private void initPosition(List<MapiJobInfoResult> positions) {
        //选项选择器
        positionOptions = new OptionsPickerView(this);
        for (MapiJobInfoResult mapiJobInfoResult : positions) {
            //选项1
            posOptions1Items.add(mapiJobInfoResult);

            if (null != mapiJobInfoResult.getList()) {
                for (MapiJobInfoResult item : mapiJobInfoResult.getList()) {
                    //选项2
                    ArrayList<MapiJobInfoResult> options2Items_01 = new ArrayList<>();
                    options2Items_01.add(item);
                    posOptions2Items.add(options2Items_01);
                }
            }
        }

        //三级联动效果
        positionOptions.setPicker(posOptions1Items, posOptions2Items, true);
        //设置选择的三级单位
//        pwOptions.setLabels("省", "市", "区");
//        pvOptions.setTitle("选择城市");
        positionOptions.setCyclic(false);
        //设置默认选中的三级项目
        //监听确定选择按钮
        positionOptions.setSelectOptions(1, 1);
        positionOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                pos.setText(posOptions1Items.get(options1).getPickerViewText() + "-" + posOptions2Items.get(options1).get(option2).getPickerViewText());
                posID = posOptions1Items.get(options1).getId();
                pos1ID = posOptions2Items.get(options1).get(option2).getId();
            }
        });

    }

    private void initCity(List<MapiJobInfoResult> provinceList, Map<String, List<MapiJobInfoResult>> cityMaps) {
        //选项选择器
        regionOptions = new OptionsPickerView(this);
        for (MapiJobInfoResult mapiJobInfoResult : provinceList) {
            //选项1
            regionOptions1Items.add(mapiJobInfoResult);
        }

        Map<String, List<MapiJobInfoResult>> maps = MapSortUtil.getInstance().sortMapByKey(cityMaps);
        for (Map.Entry<String, List<MapiJobInfoResult>> entry : maps.entrySet()) {
            List<MapiJobInfoResult> list = entry.getValue();
            //选项2
            ArrayList<MapiJobInfoResult> options2Items_01 = new ArrayList<>();
            if (null != list) {
                for (MapiJobInfoResult item : list) {

                    options2Items_01.add(item);

                }
            }

            regionOptions2Items.add(options2Items_01);

        }

        //三级联动效果
        regionOptions.setPicker(regionOptions1Items, regionOptions2Items, true);
        //设置选择的三级单位
//        pwOptions.setLabels("省", "市", "区");
//        pvOptions.setTitle("选择城市");
        regionOptions.setCyclic(false);
        //设置默认选中的三级项目
        //监听确定选择按钮
        regionOptions.setSelectOptions(0, 0);
        regionOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                addr.setText(regionOptions1Items.get(options1).getPickerViewText() + "-" + regionOptions2Items.get(options1).get(option2).getPickerViewText());
                regionID = regionOptions1Items.get(options1).getId();
                region1ID = regionOptions2Items.get(options1).get(option2).getId();
            }
        });

    }

    @OnClick({R.id.back, R.id.tv_right, R.id.birth_ll, R.id.xueli_ll, R.id.work_nx_ll, R.id.pos_ll, R.id.addr_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_right:
                editJob();
                break;
            case R.id.birth_ll:
                if (null != pvTime)
                    pvTime.show();
                break;
            case R.id.xueli_ll:
                if (null != xueliOptions)
                    xueliOptions.show();
                break;
            case R.id.work_nx_ll:
                if (null != workOptions)
                    workOptions.show();
                break;
            case R.id.pos_ll:
                if (null != positionOptions)
                    positionOptions.show();
                break;
            case R.id.addr_ll:
                if (null != regionOptions)
                    regionOptions.show();
                break;
        }
    }

    private void editJob() {
        if (TextUtils.isEmpty(nameEt.getText().toString())) {
            MainToast.showShortToast("请输入姓名");
            return;
        }

        if (TextUtils.isEmpty(sex)) {
            MainToast.showShortToast("请选择性别");
            return;
        }

        if (TextUtils.isEmpty(birth.getText().toString())) {
            MainToast.showShortToast("请选择出生日期");
            return;
        }

        if (TextUtils.isEmpty(xueliID)) {
            MainToast.showShortToast("请选择最高学历");
            return;
        }

        if (TextUtils.isEmpty(work_nx_ID)) {
            MainToast.showShortToast("请选择工作年限");
            return;
        }

        if (TextUtils.isEmpty(addr.getText().toString())) {
            MainToast.showShortToast("请选择现居住地");
            return;
        }

        if (TextUtils.isEmpty(tel.getText().toString())) {
            MainToast.showShortToast("请输入联系电话");
            return;
        }

        if (TextUtils.isEmpty(email.getText().toString())) {
            MainToast.showShortToast("请输入联系邮箱");
            return;
        }

        if (TextUtils.isEmpty(introduction.getText().toString())) {
            MainToast.showShortToast("请输入自我描述");
            return;
        }

        if (TextUtils.isEmpty(pos.getText().toString())) {
            MainToast.showShortToast("请输入意向职位");
            return;
        }

        if (TextUtils.isEmpty(salary.getText().toString())) {
            MainToast.showShortToast("请输入期望月薪");
            return;
        }

        if (TextUtils.isEmpty(workJl.getText().toString())) {
            MainToast.showShortToast("请输入工作经历");
            return;
        }
        showLoading();
        CommunityApi.zhaopinAddsume(this, nameEt.getText().toString(), sex, birth.getText().toString(), xueliID, work_nx_ID, regionID, region1ID, tel.getText().toString(),
                email.getText().toString(), introduction.getText().toString(), posID, pos1ID, workJl.getText().toString(), new RequestCallback() {
                    @Override
                    public void success(Object success) {
                        hideLoading();
                        MainToast.showShortToast("操作成功");
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
