package com.liaozixu.entity;

import com.liaozixu.util.UrlUtils;

public class Category {
    private int id;
    private String title;
    private String alias;
    private String keywords;
    private String description;
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl(){
        return UrlUtils.categoryList(alias);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", alias='" + alias + '\'' +
                ", keywords='" + keywords + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                '}';
    }
}
