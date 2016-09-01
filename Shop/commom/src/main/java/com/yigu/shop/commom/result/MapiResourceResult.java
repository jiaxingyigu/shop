package com.yigu.shop.commom.result;

import java.io.Serializable;

/**
 * Created by brain on 2016/8/30.
 */
public class MapiResourceResult extends MapiBaseResult{
    private String title;
    private String url;
    private boolean isChecked;

    public MapiResourceResult(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
