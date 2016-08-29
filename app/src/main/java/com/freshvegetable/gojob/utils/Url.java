package com.freshvegetable.gojob.utils;

/**
 * Created by NamVp on 02/08/2016.
 */
public class Url {

    /**
     * public static final String BASE_URL = "http://localhost:3000";
     */
    //public static final String BASE_URL = "http://192.168.100.19:3000"; // company
//    public static final String BASE_URL = "http://192.168.43.215:3000"; // Linh's Láp tốp
//    public static final String BASE_URL = "http://192.168.43.254:3000"; // laptop
    public static final String BASE_URL = "http://gojob.herokuapp.com";

    /**
     * Auth API url;
     */
    public static final String SIGN_IN_API_URL = "/api/auth/signin";
    public static final String SIGN_UP_API_URL = "/api/auth/signup";
    public static final String SIGN_OUT_API_URl = "/api/auth/signout";

    /**
     * User API
     */
    public static final String PROFILE_API_URL = "/api/users/me";
    public static final String FIND_USER_URL = "/api/users/find";
    public static final String GET_USER_DETAIL_URL = "/api/users/details/"; //+ userID;
    public static final String CHANGE_PROFILE_PICTURE_URL = "/api/users/picture";

    public static final String GET_CHAT_LIST_HISTORY_URL = "/api/users/messageHistory";
    public static final String MASSAGE_HISTORY_URL = "api/users/massage/";

    /**
     * Post API
     */
    public static final String POST_API_URL = "/api/posts";
    public static final String GET_POST_BY_CATEGORY_URL = "/api/posts/categories/";
    public static final String GET_POST_DETAIL_URL = "/api/posts/"; // + postID;
    public static final String POST_COMMENT_URL = "/comment";
    public static final String SEARCH_POST_BY_TAG_URL = "/api/posts/search/"; // + tag;
}
