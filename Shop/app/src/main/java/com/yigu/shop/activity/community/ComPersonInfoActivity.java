package com.yigu.shop.activity.community;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.yigu.shop.R;
import com.yigu.shop.adapter.community.ComPersonAdapter;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.api.CommunityApi;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiContentResult;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.result.MapiMunityResult;
import com.yigu.shop.commom.result.MapiMunityUserResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback2;
import com.yigu.shop.commom.util.RequestPageCallback;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;
import com.yigu.shop.shopinterface.TabSelListener;
import com.yigu.shop.util.ControllerUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ComPersonInfoActivity extends BaseActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.sticky_header_view)
    LinearLayout stickyHeaderView;

    private final static String PERSON_BG = "PERSON_BG";
    private final static String PERSON_TAB = "PERSON_TAB";
    private final static String ITEM_PUBLISH = "ITEM_PUBLISH";
    private final static String ITEM_INFO = "ITEM_INFO";
    private final static String DIVIDER = "DIVIDER";

    List<IndexData> mList;
    private List<String> list_title = new ArrayList<>();
    ComPersonAdapter mAdapter;

    int maxDist;
    int totalChange = 0;
    int layHeaderHeight = 0;

    String type = "info";

    MapiMunityUserResult mapiMunityUserResult;

    List<MapiContentResult> infoList;
    List<MapiMunityResult> publishList;

    String uid = "";
    boolean isEdit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_person_info);
        ButterKnife.bind(this);
        if(null!=getIntent()){
            uid = getIntent().getStringExtra("uid");
            isEdit = getIntent().getBooleanExtra("isEdit",true);
        }
        if(!TextUtils.isEmpty(uid)){
            initView();
            initListener();
            load();
            loadTopic();
        }

    }

    private void initView() {

        //200dp是普通header的高度
        maxDist = DPUtil.dip2px(220);//上面图片的高度

        mList = new ArrayList<>();
        publishList = new ArrayList<>();
        infoList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
//        recyclerView.addItemDecoration(new DividerListItemDecoration(this,OrientationHelper.HORIZONTAL, DPUtil.dip2px(0.5f),getResources().getColor(R.color.divider_line)));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ComPersonAdapter(this,mList);
        mAdapter.setEdit(isEdit);
        recyclerView.setAdapter(mAdapter);


        list_title.add("资料");
        list_title.add("发表");

        tablayout.setTabMode(TabLayout.MODE_FIXED);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.addTab(tablayout.newTab().setText(list_title.get(0)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(1)));

        stickyHeaderView.post(new Runnable() {
            @Override
            public void run() {
                stickyHeaderView.setVisibility(View.VISIBLE);
                stickyHeaderView.setTranslationY(maxDist);
            }
        });

    }

    private void load() {
        showLoading();
        CommunityApi.userinfo(this, uid, new RequestCallback<MapiMunityUserResult>() {
            @Override
            public void success(MapiMunityUserResult success) {
                hideLoading();
                if (null != success) {
                    mapiMunityUserResult = success;
                    notifyType(type);
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

    private void loadTopic(){
        showLoading();
        CommunityApi.usertopiclist(this,uid, "topic", "1", "1", "20", new RequestPageCallback<List<MapiMunityResult>>() {
            @Override
            public void success(Integer count, List<MapiMunityResult> success) {
                hideLoading();
                if (success.isEmpty())
                    return;
                publishList.clear();
                publishList.addAll(success);

            }
        }, new RequestExceptionCallback2() {
            @Override
            public void error(String code, String message) {
                hideLoading();
                MainToast.showShortToast(message);
            }
        });
    }

    private void initListener() {
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                /*((MapiShopResult) mList.get(1).getData()).setType(tab.getPosition());
                ((MapiShopResult) mList.get(1).getData()).setSel(isSel);
                mAdapter.notifyDataSetChanged();
                isSel = true;
                */
                DebugLog.i("tablayout=>"+tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        type = "info";
                        break;
                    case 1:
                        type = "publish";
                        break;
                }

                notifyType(type);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mAdapter.setTabSelListener(new TabSelListener() {
            @Override
            public void tabSel(int position) {
//                isSel = false;
                tablayout.getTabAt(position).select();
            }
        });

        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ControllerUtil.go2ProductDetail((MapiItemResult) mList.get(position).getData());
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if ((newState == RecyclerView.SCROLL_STATE_IDLE) && manager.findLastVisibleItemPosition() > 0 && (manager.findLastVisibleItemPosition() == (manager.getItemCount() - 1))) {
//                    loadNext();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalChange += dy;
                int tranY = Math.max(0, maxDist - totalChange);
                //移动距离超过maxDist，就定在0处  ,barHeight是appbar的高度，必须加上
                stickyHeaderView.setVisibility(View.VISIBLE);
                stickyHeaderView.setTranslationY(tranY);


            }
        });

        mAdapter.setBgDeelListener(new ComPersonAdapter.BGDeelListener() {
            @Override
            public void deelListener() {
                // TODO: 2017/3/29
                MainToast.showShortToast("正在开发...");
            }

            @Override
            public void backListener() {
                finish();
            }
        });

    }

    private void notifyType(String type){
        infoList.clear();
        mList.clear();
        totalChange = 0;
        recyclerView.getLayoutManager().scrollToPosition(0);
        mAdapter.notifyDataSetChanged();
        if(type.equals("publish")){
            if(null!=mapiMunityUserResult){

                mList.add(new IndexData(0, PERSON_BG, mapiMunityUserResult));
                mList.add(new IndexData(1, PERSON_TAB, mapiMunityUserResult));

                int num = mList.size();
                if (publishList == null || publishList.size() == 0) {
                    num = 0;
                } else {
                    for (MapiMunityResult ware : publishList) {

                        mList.add(new IndexData(num++, ITEM_PUBLISH, ware));
                        mList.add(new IndexData(num++, DIVIDER, new Object()));

                    }
                }

                mAdapter.notifyDataSetChanged();

            }

        }else if(type.equals("info")){
            mList.add(new IndexData(0, PERSON_BG, mapiMunityUserResult));
            mList.add(new IndexData(1, PERSON_TAB, mapiMunityUserResult));

            List<MapiContentResult> creditList = mapiMunityUserResult.getCreditList();
            List<MapiContentResult> profileList = mapiMunityUserResult.getProfileList();
            if(null!=creditList&&creditList.size()>0){
                infoList.addAll(creditList);
            }

            if(null!=profileList&&profileList.size()>0){
                infoList.addAll(profileList);
            }

            int num = mList.size();
            DebugLog.i(infoList.size() + "==>infoList.size()");
            if (infoList == null || infoList.size() == 0) {
                num = 0;
            } else {
                for (MapiContentResult ware : infoList) {
                    mList.add(new IndexData(num++, ITEM_INFO, ware));
                    mList.add(new IndexData(num++, DIVIDER, new Object()));
                }
            }

            mAdapter.notifyDataSetChanged();
        }
    }
}
