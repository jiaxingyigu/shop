package com.yigu.shop.activity.community;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.fragment.community.ComChangeFragment;
import com.yigu.shop.fragment.community.ComIndexFragment;
import com.yigu.shop.fragment.community.ComMunityFragment;
import com.yigu.shop.fragment.search.ForumSearchFragment;
import com.yigu.shop.fragment.search.PortalSearchFragment;
import com.yigu.shop.fragment.search.UserSearchFragment;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;
import com.yigu.shop.widget.TopPopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ComSearchTwoActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.tablayout)
    TabLayout tablayout;

    private List<String> list_title = new ArrayList<>();
    private BaseFrag[] fragments;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_search_two);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back);

        list_title.add("帖子");
        list_title.add("文章");
        list_title.add("用户");

        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.addTab(tablayout.newTab().setText(list_title.get(0)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(1)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(2)));

        fragments = new BaseFrag[3];
        fragments[0] = new ForumSearchFragment();
        fragments[1] = new PortalSearchFragment();
        fragments[2] = new UserSearchFragment();
        selectTab();

    }

    private void initListener() {
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        index = 0;
                        selectTab();
//                        fragments[index].load();
                        break;

                    case 1:
                        index = 1;
                        selectTab();
//                        fragments[index].load();
                        break;
                    case 2:
                        index = 2;
                        selectTab();
//                        fragments[index].load();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void selectTab() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (BaseFrag frag : fragments) {
            if (!frag.isAdded())
                transaction.add(R.id.content, frag);
            transaction.hide(frag);
        }
        transaction.show(fragments[index]);
        transaction.commitAllowingStateLoss();
    }

    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
