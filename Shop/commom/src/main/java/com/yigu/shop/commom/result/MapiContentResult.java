package com.yigu.shop.commom.result;

/**
 * Created by brain on 2017/3/16.
 */
public class MapiContentResult extends MapiBaseResult{

    private String infor;
    private String type;
    private String content;
    private String data;
    private String title;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getInfor() {
        return infor;
    }

    public void setInfor(String infor) {
        this.infor = infor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
