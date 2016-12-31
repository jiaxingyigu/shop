package com.yigu.shop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.result.SearchHistory;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.util.FileUtil;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.util.ControllerUtil;

import org.apmem.tools.layouts.FlowLayout;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/7.
 */
public class SearchActivity extends BaseActivity {

    private static final String SEARCH_HOT = "search_hot";
    @Bind(R.id.search_et)
    EditText searchEt;
    @Bind(R.id.search)
    TextView search;
    @Bind(R.id.header)
    LinearLayout header;
    @Bind(R.id.recent_search)
    FlowLayout recentSearch;
    @Bind(R.id.hot_search)
    FlowLayout hotSearch;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.clear_history)
    TextView clearHistory;

    private DbManager db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig().setDbName("shop").setDbDir(new File(FileUtil.getFolderPath(this,FileUtil.TYPE_DB)));
        db = x.getDb(daoConfig);
        searchEt.requestFocus();
        getRecentSearchData();
        initListener();
        loadData();
    }


    private void initListener() {
        //点击软键盘上的回车键触发
        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_ENDCALL)
                    search();
                return false;
            }
        });
    }

    private void loadData() {

    }

    private void getRecentSearchData() {
        try {
            List<SearchHistory> historyList = db.selector(SearchHistory.class).orderBy("createDate", true).limit(10).findAll();
            recentSearch.removeAllViews();
            if (historyList == null||historyList.size()==0) {
                clearHistory.setVisibility(View.GONE);
                return;
            }else{
                clearHistory.setVisibility(View.VISIBLE);
            }
            showRecentSearch(historyList);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }


    /**
     * 最近搜索
     *
     * @param list
     */
    private void showRecentSearch(List<SearchHistory> list) {
        for (final SearchHistory resourceResult : list) {
            final TextView textView = new TextView(this);
            FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(
                    FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);
            textView.setLayoutParams(params);
            textView.setPadding(DPUtil.dip2px(8), DPUtil.dip2px(4), DPUtil.dip2px(8), DPUtil.dip2px(4));
            textView.setText(resourceResult.getKeyword());
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(getResources().getColor(R.color.shop_black));
            textView.setBackgroundResource(R.drawable.rectangle_solid_white_strike_divider_line_5);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textView.setTextColor(getResources().getColor(R.color.gray_middle));
                    String keyWord = textView.getText().toString();
                    insertKeyword(keyWord);
                    ControllerUtil.go2SearchList(keyWord);
                    SearchActivity.this.finish();//进入列表页
                }
            });
            recentSearch.addView(textView);
        }
    }

    /**
     * 显示热门搜索
     *
     * @param list
     */
    private void showHotResearch(List<MapiResourceResult> list) {
        for (final MapiResourceResult resourceResult : list) {
            final TextView textView = new TextView(this);
            FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(
                    FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);
            textView.setLayoutParams(params);
            textView.setPadding(DPUtil.dip2px(8), DPUtil.dip2px(4), DPUtil.dip2px(8), DPUtil.dip2px(4));
            textView.setText(resourceResult.getTitle());
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(getResources().getColor(R.color.shop_black));
            textView.setBackgroundResource(R.drawable.rectangle_solid_white_strike_divider_line_5);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textView.setTextColor(getResources().getColor(R.color.gray_middle));
                    ControllerUtil.go2SearchList(resourceResult.getTitle());
                    SearchActivity.this.finish();//进入列表页

                }
            });
            hotSearch.addView(textView);
        }
    }

    /**
     * 将搜索内容保存到本地
     *
     * @param keyWord
     */
    private void insertKeyword(String keyWord) {
        try {
            SearchHistory history = db.selector(SearchHistory.class).where("keyword", "==", keyWord).findFirst();
            if (history != null) {
                history.setCreateDate(new Date());
                db.update(history);
            } else {
                history = new SearchHistory();
                history.setKeyword(keyWord);
                history.setCreateDate(new Date());
                db.save(history);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.search)
    void search() {
        String keyWord = searchEt.getText().toString().trim();
        if (TextUtils.isEmpty(keyWord)) {
            MainToast.showShortToast("请输入搜索词");
            return;
        }
        insertKeyword(keyWord);
        ControllerUtil.go2SearchList(keyWord);
    }

    @OnClick(R.id.back)
    void  back(){
        finish();
    }

    @OnClick(R.id.clear_history)
    void clearHistory(){
        try {
            db.delete(SearchHistory.class);
            getRecentSearchData();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
