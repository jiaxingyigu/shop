package com.yigu.shop.activity.purcase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yigu.shop.R;
import com.yigu.shop.adapter.purcase.PurcaseAdapter;
import com.yigu.shop.adapter.purcase.PurcaseListAdapter;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.api.ItemApi;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiCartResult;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.util.RequestPageCallback;
import com.yigu.shop.commom.util.StringUtil;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.shopinterface.AdapterSelListener;
import com.yigu.shop.util.ControllerUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PurcaseListActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
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
    @Bind(R.id.delete)
    TextView delete;

    PurcaseListAdapter mAdapter;
    List<IndexData> mList = new ArrayList<>();
    List<MapiCartResult> data = new ArrayList<>();
    boolean isAll;
    private ArrayList<MapiItemResult> delList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purcase_list);
        ButterKnife.bind(this);
        initView();
        initListener();
        load();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mList.clear();
        data.clear();
        mAdapter.notifyDataSetChanged();
        load();
    }

    private void initView(){

        center.setText("购物车");
        back.setImageResource(R.mipmap.back);
        tvRight.setText("编辑");
        delList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new PurcaseListAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);

    }


    private void initListener() {
        mAdapter.setOnAdapterSelListener(new AdapterSelListener() {
            @Override
            public void isAll() {
                notifyAllStatus();
            }

            @Override
            public void notifyParentStatus(int position) {
                MapiCartResult ware = (MapiCartResult) mList.get(position).getData();
                ware.setSel(!ware.isSel());
                for (MapiItemResult item : ware.getList()) {
                    item.setSel(ware.isSel());
                }
                mAdapter.notifyDataSetChanged();
                notifyAllStatus();
            }

            @Override
            public void notifyChildStatus(int position) {
                MapiItemResult item = (MapiItemResult) mList.get(position).getData();
                item.setSel(!item.isSel());
                mAdapter.notifyDataSetChanged();
                notifyAllStatus();
            }

            @Override
            public void notifyChildNum(int position, int num) {
                showLoading();
                MapiItemResult item = (MapiItemResult) mList.get(position).getData();
                ItemApi.updatecart(PurcaseListActivity.this, item.getRec_id(), num + "", new RequestCallback<JSONObject>() {
                    @Override
                    public void success(JSONObject success) {
                        hideLoading();
                        String goods_amount = success.getJSONObject("data").getString("goods_amount");
                        String goods_count = success.getJSONObject("data").getString("goods_count");
                        account.setText(goods_amount);
                        statement.setText(String.format("结算（%s）", goods_count));
                    }
                }, new RequestExceptionCallback() {
                    @Override
                    public void error(Integer code, String message) {
                        hideLoading();
                        MainToast.showShortToast(message);
                    }
                });
            }
        });
    }

    private void notifyAllStatus() {
        DebugLog.i("notifyAllStatus");
        delList.clear();
        isAll = true;
        for (IndexData indexData : mList) {
            if(indexData.getType().equals("head")){
                MapiCartResult mapiCartResult = (MapiCartResult) indexData.getData();
                if (!mapiCartResult.isSel()) {
                    DebugLog.i("notifyAllStatus==>head");
                    isAll = false;
                }
            }else if(indexData.getType().equals("item")){
                MapiItemResult mapiItemResult = (MapiItemResult) indexData.getData();
                if (!mapiItemResult.isSel()) {
                    DebugLog.i("notifyAllStatus==>item");
                    isAll = false;

                }else{
                    delList.add(mapiItemResult);
                }
            }
        }

        if (isAll)
            all.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.circle_red_sel, 0, 0, 0);
        else
            all.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.circle_white, 0, 0, 0);

    }
    private void load(){
        showLoading();
        ItemApi.getCartGoods(this,new RequestCallback<JSONObject>() {
            @Override
            public void success(JSONObject success) {
                hideLoading();
                Gson gson = new Gson();
                List<MapiCartResult> result = gson.fromJson(success.getJSONObject("data").getJSONArray("goods_list").toJSONString(), new TypeToken<List<MapiCartResult>>(){}.getType());
                String goods_amount = success.getJSONObject("data").getJSONObject("total").getString("goods_amount");
                String goods_count = success.getJSONObject("data").getJSONObject("total").getString("goods_count");
                account.setText(goods_amount);
                statement.setText(String.format("结算（%s）", goods_count));
                if(result.isEmpty())
                    return;
                data.clear();
                data.addAll(result);
                refreshList(data);
            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                hideLoading();
                MainToast.showShortToast(message);
            }
        });
    }

    private void refreshList( List<MapiCartResult> data){
        mList.clear();
        int num;
        if(data==null||data.size()==0){
            num = 0;
        }else{
            num = 1;
            mList.add(new IndexData(num++,"divider", new Object()));
            for (MapiCartResult ware : data) {
                mList.add(new IndexData(num++,"head",ware));
                for (int i=0;i<ware.getList().size();i++) {
                    if(i == ware.getList().size()-1){
                        ware.getList().get(i).setLast(true);
                    }else
                        ware.getList().get(i).setLast(false);
                    mList.add(new IndexData(num++,"item", ware.getList().get(i)));

                }
                mList.add(new IndexData(num++,"divider", new Object()));
            }
        }

        mAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.back, R.id.tv_right, R.id.all, R.id.statement,R.id.delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_right:
                if("编辑".equals(tvRight.getText())){
                    tvRight.setText("完成");
                    statement.setVisibility(View.GONE);
                    delete.setVisibility(View.VISIBLE);
                    all.setVisibility(View.VISIBLE);
                    mAdapter.setDel(true);
                    mAdapter.notifyDataSetChanged();
                }else if("完成".equals(tvRight.getText())){
                    tvRight.setText("编辑");
                    statement.setVisibility(View.VISIBLE);
                    delete.setVisibility(View.GONE);
                    all.setVisibility(View.INVISIBLE);
                    mAdapter.setDel(false);
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.all:
                for (IndexData indexData : mList) {
                    if(indexData.getType().equals("head")){
                        MapiCartResult mapiCartResult = (MapiCartResult) indexData.getData();
                        mapiCartResult.setSel(!isAll);

                    }else if(indexData.getType().equals("item")){
                        MapiItemResult mapiItemResult = (MapiItemResult) indexData.getData();
                        mapiItemResult.setSel(!isAll);

                    }
                }
                isAll = !isAll;
                mAdapter.notifyDataSetChanged();
//                notifyAllStatus();
                break;
            case R.id.statement:
                if (mList!=null) {
                    ArrayList<MapiItemResult> items = new ArrayList<>();
                    for(IndexData indexData : mList){
                        if(indexData.getType().equals("item")){
                            MapiItemResult itemResult = (MapiItemResult) indexData.getData();
                            items.add(itemResult);
                        }
                    }
                    if(items.size()>0){
                        ControllerUtil.go2OderDetail();
                    }else{
                        MainToast.showShortToast("您的购物车还是空的，快去购物吧");
                    }
                }
                break;
            case R.id.delete:
                String rec_ids = "";
                for(MapiItemResult itemResult:delList){
                    if(TextUtils.isEmpty(rec_ids))
                        rec_ids += itemResult.getRec_id();
                    else
                        rec_ids +=","+ itemResult.getRec_id();
                }
                DebugLog.i("rec_ids==>"+rec_ids);
                if(TextUtils.isEmpty(rec_ids)){
                    MainToast.showShortToast("请选删除的商品");
                    return;
                }else{
                    showLoading();
                    ItemApi.droplist(this, rec_ids, new RequestCallback<JSONObject>() {
                        @Override
                        public void success(JSONObject success) {
                            hideLoading();

                            for(MapiCartResult mapiCartResult : data){
                                Iterator<MapiItemResult> it =mapiCartResult.getList().iterator();
                                while(it.hasNext()){
                                    MapiItemResult x = it.next();
                                    if(delList.contains(x)){
                                        DebugLog.i("delList.contains(x)=>"+x.getRec_id());
                                        it.remove();
                                    }
                                }
                            }

                            Iterator<MapiCartResult> it =data.iterator();
                            while(it.hasNext()){
                                MapiCartResult mapiCartResult = it.next();
                                if(null==mapiCartResult.getList()||mapiCartResult.getList().size()==0){
                                    it.remove();
                                }
                            }

                            refreshList(data);



                            delList.clear();
                            mAdapter.notifyDataSetChanged();

                            String goods_amount = success.getJSONObject("data").getString("goods_amount");
                            String goods_count = success.getJSONObject("data").getString("goods_count");
                            account.setText(goods_amount);
                            statement.setText(String.format("结算（%s）", goods_count));
                            isAll = false;
                            all.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.circle_white, 0, 0, 0);
                        }
                    }, new RequestExceptionCallback() {
                        @Override
                        public void error(Integer code, String message) {
                            hideLoading();
                            MainToast.showShortToast(message);
                        }
                    });
                }

                break;
        }
    }
}
