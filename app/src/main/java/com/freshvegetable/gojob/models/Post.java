package com.freshvegetable.gojob.models;


import java.text.SimpleDateFormat;

/**
 * Created by NamVp on 16/08/2016.
 */
public class Post {
    private String user;
    private Long createTime;
    private String title;
    private String content;
    private int[] imgUrl;

    private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm  dd/MM/yyyy");

    public Post(String user, Long createTime, String title, String content, int[] imgUrl) {
        this.user = user;
        this.createTime = createTime;
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
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

    public int[] getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int[] imgUrl) {
        this.imgUrl = imgUrl;
    }
}
