package com.yigu.shop.commom.result;

import java.util.List;

/**
 * Created by brain on 2017/3/23.
 */
public class MapiMunityUserResult extends MapiBaseResult{

    private String credits;
    private String dateline;
    private String icon;
    private String isFollow;
    private String location;
    private String userTitle;
    private String uid;
    private String score;
    private String userName;
    private String avatar;
    private String token;
    private String secret;
    private List<MapiContentResult> creditList;
    private List<MapiContentResult> profileList;
    private String follow_num;
    private String friend_num;
    private String reply_posts_num;
    private String sign;
    private String posts;
    private String threads;
    private String username;

    public String getPosts() {
        return posts;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }

    public String getThreads() {
        return threads;
    }

    public void setThreads(String threads) {
        this.threads = threads;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getFollow_num() {
        return follow_num;
    }

    public void setFollow_num(String follow_num) {
        this.follow_num = follow_num;
    }

    public String getFriend_num() {
        return friend_num;
    }

    public void setFriend_num(String friend_num) {
        this.friend_num = friend_num;
    }

    public String getReply_posts_num() {
        return reply_posts_num;
    }

    public void setReply_posts_num(String reply_posts_num) {
        this.reply_posts_num = reply_posts_num;
    }

    public List<MapiContentResult> getProfileList() {
        return profileList;
    }

    public void setProfileList(List<MapiContentResult> profileList) {
        this.profileList = profileList;
    }

    public List<MapiContentResult> getCreditList() {
        return creditList;
    }

    public void setCreditList(List<MapiContentResult> creditList) {
        this.creditList = creditList;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(String isFollow) {
        this.isFollow = isFollow;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }
}
