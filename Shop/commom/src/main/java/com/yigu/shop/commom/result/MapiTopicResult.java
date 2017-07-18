package com.yigu.shop.commom.result;

import java.util.List;

/**
 * Created by brain on 2017/3/16.
 */
public class MapiTopicResult extends MapiBaseResult{

    private String title;
    private String hits;
    private String icon;
    private String user_nick_name;
    private String userTitle;
    private String create_date;

    private String catName;
    private String viewNum;

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getViewNum() {
        return viewNum;
    }

    public void setViewNum(String viewNum) {
        this.viewNum = viewNum;
    }

    private List<MapiContentResult> content;

    public List<MapiContentResult> getContent() {
        return content;
    }

    public void setContent(List<MapiContentResult> content) {
        this.content = content;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getUser_nick_name() {
        return user_nick_name;
    }

    public void setUser_nick_name(String user_nick_name) {
        this.user_nick_name = user_nick_name;
    }

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
