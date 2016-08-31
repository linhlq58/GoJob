package com.freshvegetable.gojob.models;


import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by NamVp on 16/08/2016.
 *
 */
public class Post {
    private User.UserHolder user;
    private Long createTime;
    private String title;
    private String content;
    private String imgUrl;

    private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm  dd/MM/yyyy", Locale.ENGLISH);

    public Post(User.UserHolder user, @Nullable Long createTime, @Nullable String title, String content, @Nullable String imgUrl) {
        this.user = user;
        this.createTime = createTime;
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
    }

    public User.UserHolder getUser() {
        return user;
    }

    public void setUser(User.UserHolder user) {
        this.user = user;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
