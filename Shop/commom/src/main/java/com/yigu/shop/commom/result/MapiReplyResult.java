package com.yigu.shop.commom.result;

import java.util.List;

/**
 * Created by brain on 2017/3/17.
 */
public class MapiReplyResult extends MapiBaseResult{

    private String infor;
    private int type;
    private String reply_name;
    private String userTitle;
    private String icon;
    private String posts_date;
    private String position;
    private String quote_content;
    private List<MapiReplyResult> reply_content;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getInfor() {
        return infor;
    }

    public void setInfor(String infor) {
        this.infor = infor;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosts_date() {
        return posts_date;
    }

    public void setPosts_date(String posts_date) {
        this.posts_date = posts_date;
    }

    public String getQuote_content() {
        return quote_content;
    }

    public void setQuote_content(String quote_content) {
        this.quote_content = quote_content;
    }

    public List<MapiReplyResult> getReply_content() {
        return reply_content;
    }

    public void setReply_content(List<MapiReplyResult> reply_content) {
        this.reply_content = reply_content;
    }

    public String getReply_name() {
        return reply_name;
    }

    public void setReply_name(String reply_name) {
        this.reply_name = reply_name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }
}
