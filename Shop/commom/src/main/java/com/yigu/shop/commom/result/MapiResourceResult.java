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
    private String name;
    private String src;
    private String img_id;
    private String img_url;

    public String getImg_id() {
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public MapiResourceResult(){

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
