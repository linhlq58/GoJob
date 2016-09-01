package com.freshvegetable.gojob.models;

/**
 * Created by duyti on 8/29/2016.
 */
public class Message {

    public static final int TYPE_MESSAGE = 0;
    public static final int TYPE_MESSAGE_OTHER = 1;

    private int mType;
    private String mMessage;
    private String mUsername;
    private String mTime;
    private String mAvatarURL;

    public String getmAvatarURL() {
        return mAvatarURL;
    }

    public void setmAvatarURL(String mAvatarURL) {
        this.mAvatarURL = mAvatarURL;
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }
}

