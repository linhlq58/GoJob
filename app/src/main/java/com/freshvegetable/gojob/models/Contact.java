package com.freshvegetable.gojob.models;

/**
 * Created by duyti on 8/21/2016.
 */
public class Contact {

    private String id;
    private String username, displayName, avatarURL, created;

    public Contact() {
    }

    public Contact(String id, String username, String displayName, String avatarURL, String created) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.avatarURL = avatarURL;
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public String getCreated() {
        return created;
    }
}
