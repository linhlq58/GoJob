package com.freshvegetable.gojob.models;

/**
 * Created by NamVp on 16/08/2016.
 */
public class Category {
    private String _id;
    private int categoryImage;
    private String categoryTitle;
    private int postCount;

    public Category(String id, int categoryImage, String categoryTitle, int postCount) {
        this._id = id;
        this.categoryImage = categoryImage;
        this.categoryTitle = categoryTitle;
        this.postCount = postCount;
    }

//    public Category(int categoryImage, String categoryTitle, int postCount) {
//        this.categoryImage = categoryImage;
//        this.categoryTitle = categoryTitle;
//        this.postCount = postCount;
//    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(int categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }
}
