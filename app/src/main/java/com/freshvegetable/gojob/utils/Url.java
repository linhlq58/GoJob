package com.freshvegetable.gojob.utils;

/**
 * Created by NamVp on 02/08/2016.
 */
public interface Url {
    //    public static final String BASE_URL = "http://localhost:3000";
    public static final String BASE_URL = "http://192.168.100.19:3000";

    //    Account APIs
    public static final String SIGN_IN_API_URL = "/api/auth/signin";
    public static final String SIGN_UP_API_URL = "/api/auth/signup";
    public static final String SIGN_OUT_API_URl = "/api/auth/signout";

    //    Post APIs
    public static final String POST_API_URL = "/api/posts";
    public static final String GET_POST_BY_CATEGORY_URL = "/api/posts/categories/:categoryId";
    public static final String GET_POST_DETAIL_URL = "/api/posts/:postId";
    public static final String GET_ALL_CATEGORY_URL = "/api/categories";
}
