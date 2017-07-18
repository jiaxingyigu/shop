package com.yigu.shop.fragment.community;


import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.yigu.shop.R;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.util.ControllerUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link BaseFrag} subclass.
 */
public class ComPersonFragment extends BaseFrag {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.image)
    SimpleDraweeView image;
    @Bind(R.id.userName)
    TextView userName;
    @Bind(R.id.score)
    TextView score;
    @Bind(R.id.userTitle)
    TextView userTitle;


    public ComPersonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_com_person, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        back.setImageResource(R.mipmap.logo);
        center.setText("我的");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (userSP.checkLogin()) {
            load();
        }
    }

    public void load() {
        //创建将要下载的图片的URI
        Uri imageUri = Uri.parse(comUserSP.getUserBean().getAvatar());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                .setResizeOptions(new ResizeOptions(DPUtil.dip2px(75), DPUtil.dip2px(75)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(image.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        image.setController(controller);

        userName.setText(TextUtils.isEmpty(comUserSP.getUserBean().getUserName()) ? "" : comUserSP.getUserBean().getUserName());
        score.setText("积分：" + (TextUtils.isEmpty(comUserSP.getUserBean().getScore()) ? "" : comUserSP.getUserBean().getScore()));
        userTitle.setText(TextUtils.isEmpty(comUserSP.getUserBean().getUserTitle()) ? "" : comUserSP.getUserBean().getUserTitle());
    }

    @OnClick({R.id.guanzhu, R.id.fabiao, R.id.review_ll, R.id.job_ll, R.id.person_ll,R.id.rl_setting,})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.guanzhu:
                ControllerUtil.go2FollowList();
                break;
            case R.id.fabiao:
                ControllerUtil.go2TopicList();
                break;
            case R.id.review_ll:
                break;
            case R.id.job_ll:
                ControllerUtil.go2ComJobHis();
                break;
            case R.id.person_ll:
                ControllerUtil.go2ComPersonInfo(comUserSP.getUserBean().getUid(), true);
                break;
            case R.id.rl_setting:
                if (!userSP.checkLogin()) {
                    ControllerUtil.go2Login();
                } else {
                    ControllerUtil.go2Setting();
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
