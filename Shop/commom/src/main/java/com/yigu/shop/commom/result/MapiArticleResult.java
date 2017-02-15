package com.yigu.shop.commom.result;

/**
 * Created by brain on 2017/1/16.
 */
public class MapiArticleResult extends MapiBaseResult{

    private String title;
    private String short_title;

    public String getShort_title() {
        return short_title;
    }

    public void setShort_title(String short_title) {
        this.short_title = short_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
