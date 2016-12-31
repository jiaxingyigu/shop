package com.yigu.shop.activity.purcase;

import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.adapter.purcase.PurcaseAdapter;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.api.ItemApi;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiCartResult;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.util.RequestPageCallback;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.shopinterface.AdapterSelListener;
import com.yigu.shop.util.ControllerUtil;
import com.yigu.shop.widget.DividerListItemDecoration;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PurcaseActivity extends BaseActivity {

    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.all)
    TextView all;
    @Bind(R.id.account)
    TextView account;
    @Bind(R.id.statement)
    TextView statement;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.count_ll)
    LinearLayout countLl;
    @Bind(R.id.back)
    ImageView back;
    private List<MapiCartResult> mList = new ArrayList<>();
    PurcaseAdapter mAdapter;
    private Integer pageIndex = 0;
    private Integer pageSize = 10;
    private Integer ISNEXT = 0;
    private boolean isall = false;
    int count = 0;

    private ArrayList<MapiItemResult> delList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purcase);
        ButterKnife.bind(this);
        initView();
        initListener();
        load();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back);
        center.setText("购物车");
        tvRight.setText("编辑");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
//        recyclerView.addItemDecoration(new DividerListItemDecoration(this, OrientationHelper.HORIZONTAL, DPUtil.dip2px(10), Color.parseColor("#e5e5e5")));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new PurcaseAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        mAdapter.setOnAdapterSelListener(new AdapterSelListener() {
            @Override
            public void isAll() {
                notifyData();
            }
        });
    }

    public void notifyData(){
        count = 0;
        delList.clear();
        double accountNum = 0;
        boolean isAll = true;
        for (MapiCartResult result : mAdapter.getmList()) {
            if (!result.isSel()) {
                isAll = false;
            }
            for (MapiItemResult item : result.getCart_goods()) {
                if (!item.isSel()) {
                    isAll = false;
                } else {

                    Double priceff = Double.parseDouble(TextUtils.isEmpty(item.getGoods_price())?"0":item.getGoods_price());
                    accountNum += priceff;
                    String numStr = TextUtils.isEmpty(item.getGoods_number())?"0":item.getGoods_number();
                    count += Integer.parseInt(numStr);
                    item.setShop_name(result.getShop_name());
                    delList.add(item);
                }
            }
        }
        isall = isAll;
        if (isall)
            all.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.circle_red_sel, 0, 0, 0);
        else
            all.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.circle_white, 0, 0, 0);
        if ("编辑".equals(tvRight.getText().toString())){
            statement.setText(String.format("结算（%d）", count));
            DecimalFormat df = new DecimalFormat("#0.00");
            account.setText(df.format(accountNum));

        }else{
            statement.setText("删除");
            DecimalFormat df = new DecimalFormat("#0.00");
            account.setText(df.format(accountNum));
        }
    }

    public void load() {
        ItemApi.getCartGoods(this, userSP.getUserBean().getUser_id(), new RequestPageCallback<List<MapiCartResult>>() {
            @Override
            public void success(Integer count, List<MapiCartResult> success) {
                if(success.isEmpty())
                    return;
                mList.addAll(success);
                mAdapter.notifyDataSetChanged();
            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                MainToast.showShortToast(message);
            }
        });

    }


    @OnClick({R.id.back, R.id.tv_right, R.id.all, R.id.statement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_right:
                if ("编辑".equals(tvRight.getText().toString())) {
                    tvRight.setText("完成");
                    countLl.setVisibility(View.INVISIBLE);
                    statement.setText("删除");
                } else if ("完成".equals(tvRight.getText().toString())) {
                    tvRight.setText("编辑");
                    countLl.setVisibility(View.VISIBLE);
                    statement.setText(String.format("结算（%d）", count));
                }
                break;
            case R.id.all:
                count = 0;
                double accountNum = 0;
                delList.clear();

                isall = !isall;
                if (isall)
                    all.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.circle_red_sel, 0, 0, 0);
                else
                    all.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.circle_white, 0, 0, 0);
                for (MapiCartResult item : mAdapter.getmList()) {
                    item.setSel(isall);
                    for (MapiItemResult result : item.getCart_goods()) {
                        result.setSel(isall);
                        String numStr = TextUtils.isEmpty(result.getGoods_number())?"0":result.getGoods_number();
                        count += Integer.parseInt(numStr);
                        Double priceff = Double.parseDouble(TextUtils.isEmpty(result.getGoods_price())?"0":result.getGoods_price());
                        accountNum += priceff;
                        if(isall)
                            delList.add(result);
                    }
                }
                mAdapter.notifyDataSetChanged();
                if ("编辑".equals(tvRight.getText().toString())){

                    if (isall) {
                        statement.setText(String.format("结算（%d）", count));
                        DecimalFormat df = new DecimalFormat("#0.00");
                        account.setText(df.format(accountNum));
                    } else {
                        count = 0;
                        accountNum = 0;
                        DecimalFormat df = new DecimalFormat("#0.00");
                        account.setText(df.format(accountNum));
                        statement.setText(String.format("结算（%d）", count));
                    }
                }else{
                    if (isall) {
                        DecimalFormat df = new DecimalFormat("#0.00");
                        account.setText(df.format(accountNum));
                    } else {
                        accountNum = 0;
                        DecimalFormat df = new DecimalFormat("#0.00");
                        account.setText(df.format(accountNum));
                    }
                }

                break;
            case R.id.statement:
                if ("编辑".equals(tvRight.getText().toString())) {
                    if (count > 0) {
                        ControllerUtil.go2OderDetail(delList);
                    }else{
                        MainToast.showShortToast("请选择需要结算的商品");
                    }
                }else if("完成".equals(tvRight.getText().toString())){
                    if(delList.size()>0){
                        String rec_ids = "";
                        for(MapiItemResult itemResult:delList){
                            if(TextUtils.isEmpty(rec_ids))
                                rec_ids += itemResult.getRec_id();
                            else
                                rec_ids +=","+ itemResult.getRec_id();
                        }
                        DebugLog.i("rec_ids==>"+rec_ids);
                        ItemApi.deleteCart(this, rec_ids, new RequestCallback() {
                            @Override
                            public void success(Object success) {

                                try{

                                    for(MapiCartResult mapiCartResult : mAdapter.getmList()){
                                        Iterator<MapiItemResult> it =mapiCartResult.getCart_goods().iterator();
                                        while(it.hasNext()){
                                            MapiItemResult x = it.next();
                                                if(delList.contains(x)){
                                                    DebugLog.i("delList.contains(x)=>"+x.getRec_id());
                                                    it.remove();
                                                }
                                        }
                                    }



                                    Iterator<IndexData> it =mAdapter.getList().iterator();
                                    while(it.hasNext()){
                                        IndexData indexData = it.next();
                                        if(indexData.getType().equals("item")){
                                            MapiItemResult mapiItemResult = (MapiItemResult) indexData.getData();
                                            if(delList.contains(mapiItemResult)){
                                                DebugLog.i("delList.contains(x)=>"+mapiItemResult.getRec_id());
                                                it.remove();
                                            }
                                        }else if(indexData.getType().equals("head")){
                                            MapiCartResult mapiCartResult = (MapiCartResult) indexData.getData();
                                            if(null==mapiCartResult.getCart_goods()||mapiCartResult.getCart_goods().size()==0){
                                                it.remove();
                                            }
                                        }

                                    }

                                    delList.clear();
                                    mAdapter.notifyDataSetChanged();
                                    notifyData();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }, new RequestExceptionCallback() {
                            @Override
                            public void error(Integer code, String message) {
                                MainToast.showShortToast(message);
                            }
                        });
                    }else{
                        MainToast.showShortToast("请选择需要删除的商品");
                    }
                }

                break;
        }
    }
}
