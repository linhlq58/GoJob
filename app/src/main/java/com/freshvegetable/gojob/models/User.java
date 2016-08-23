package com.freshvegetable.gojob.models;

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

        public UserHolder(User user) {
            this.id = user.id;
            this.displayName = user.displayName;
            this.username = user.userName;
            this.profileUrl = user.profileImageUrl;
        }

        public UserHolder(String id, String displayName, String username, String profileUrl) {
            this.id = id;
            this.displayName = displayName;
            this.username = username;
            this.profileUrl = profileUrl;
        }
    }
}
