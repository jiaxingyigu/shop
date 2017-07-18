package com.yigu.shop.fragment.community;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yigu.shop.R;
import com.yigu.shop.activity.community.SelBoardActivity;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.base.RequestCode;
import com.yigu.shop.commom.api.CommunityApi;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback2;
import com.yigu.shop.commom.widget.MainToast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComPublishFragment extends BaseFrag {


    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.board_tv)
    TextView boardTv;
    @Bind(R.id.title_edit)
    EditText titleEdit;
    @Bind(R.id.info_edit)
    EditText infoEdit;

    String boardID = "";

    public ComPublishFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_com_publish, container, false);
        ButterKnife.bind(this, view);
        initView();
        load();
        return view;
    }

    private void initView() {
        center.setText("发表");
        tvRight.setText("确认");
    }

    public void load() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.back, R.id.tv_right,R.id.board_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.tv_right:

                if(TextUtils.isEmpty(boardID)){
                    MainToast.showShortToast("请选择模块");
                    return;
                }

                if(TextUtils.isEmpty(titleEdit.getText().toString())){
                    MainToast.showShortToast("请输入标题");
                    return;
                }

                if(TextUtils.isEmpty(infoEdit.getText().toString())){
                    MainToast.showShortToast("请输入发表的内容");
                    return;
                }

                showLoading();
                JSONObject jsonObject = new JSONObject();
                JSONObject bodyObject = new JSONObject();
                JSONObject jsonsObject = new JSONObject();
                jsonsObject.put("fid",boardID);
                jsonsObject.put("ti_id",0);
                jsonsObject.put("title",titleEdit.getText().toString());
                jsonsObject.put("location",comUserSP.getLocation());
                jsonsObject.put("aid","");
                jsonsObject.put("longitude",comUserSP.getLongitude());
                jsonsObject.put("latitude",comUserSP.getLatitude());
                jsonsObject.put("isHidden",0);
                jsonsObject.put("isAnonymous",0);
                jsonsObject.put("isOnlyAuthor",0);
                jsonsObject.put("isShowPostion",1);
                jsonsObject.put("typeId",0);
                jsonsObject.put("sortId",1);
                JSONArray jsonArray = new JSONArray();
                JSONObject contentObject = new JSONObject();
                contentObject.put("type",0);
                contentObject.put("infor",infoEdit.getText().toString());
                jsonArray.add(contentObject);
                try {
                    jsonsObject.put("content", URLEncoder.encode(jsonArray.toJSONString(),"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                bodyObject.put("json",jsonsObject);
                jsonObject.put("body",bodyObject);

                DebugLog.i(jsonObject.toString());
                CommunityApi.topicadminnew(getActivity(),jsonObject.toJSONString(), new RequestCallback() {
                    @Override
                    public void success(Object success) {
                        hideLoading();
                        boardID = "";
                        boardTv.setText("");
                        titleEdit.setText("");
                        infoEdit.setText("");
                        MainToast.showShortToast("发表成功");
                    }
                }, new RequestExceptionCallback2() {
                    @Override
                    public void error(String code, String message) {
                        hideLoading();
                        MainToast.showShortToast(message);
                    }
                });

                break;
            case R.id.board_ll:
                startActivityForResult(new Intent(getActivity(),SelBoardActivity.class), RequestCode.sel_board);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==getActivity().RESULT_OK){
            if(requestCode==RequestCode.sel_board){
                boardID = data.getStringExtra("boardID");
                DebugLog.i(boardID);
                String boardName = data.getStringExtra("boardName");
                boardTv.setText(boardName);
            }
        }
    }
}
