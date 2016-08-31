package com.freshvegetable.gojob.models;

import android.support.annotation.Nullable;

/**
 * Created by Nam on 8/12/2016.
 */
public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String displayName;
    private String userName;
    private String password;
    private String salt;
    private String profileImageUrl;
    private String provider;
    private String providetData;
    private String additionalProviderData;
    private String role;
    private String update;
    private String create;
    private String resetPasswordToken;
    private String resetPasswordExpires;
    private String email;

    public static class UserHolder{
        public String id;
        public String displayName;
        public String username;
        public String profileUrl;

        public UserHolder(String id, String displayName, @Nullable String username, @Nullable String profileUrl) {
            this.id = id;
            this.displayName = displayName;
            this.username = username;
            this.profileUrl = profileUrl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getProfileUrl() {
            return profileUrl;
        }

        public void setProfileUrl(String profileUrl) {
            this.profileUrl = profileUrl;
        }
    }
}
