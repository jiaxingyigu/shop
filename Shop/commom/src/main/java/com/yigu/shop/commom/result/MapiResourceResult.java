package com.yigu.shop.commom.result;

import java.io.Serializable;

/**
 * Created by brain on 2016/8/30.
 */
public class MapiResourceResult implements Serializable{
    private String title;
    private String url;
    private boolean isChecked;
    private int id;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MapiResourceResult(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public MapiResourceResult(int id, String title,String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

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
