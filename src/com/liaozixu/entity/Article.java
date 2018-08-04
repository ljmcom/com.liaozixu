package com.liaozixu.entity;

import com.liaozixu.util.UrlUtils;

import java.util.Date;

public class Article {
    private int id;
//    类型 1=正常 2=草稿 3=私人
    private int type;
    private String title;
    private String description;
//    编辑器渲染出来的html
    private String contentText;
//    编辑器原参数
    private String contentRaw;
    private String ip;
    private Date postTime;
    private String keywords;
    private String alias;
    private int categoryID;
    private boolean original;
//    拓展参数
    private int categoryType;
    private String categoryTitle;
    private String categoryAlias;
    private String categoryKeywords;
    private String categoryDescription;

    public String getCategoryAlias() {
        return categoryAlias;
    }

    public String getCategoryKeywords() {
        return categoryKeywords;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public int getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public void setCategoryAlias(String categoryAlias) {
        this.categoryAlias = categoryAlias;
    }

    public void setCategoryKeywords(String categoryKeywords) {
        this.categoryKeywords = categoryKeywords;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getContentRaw() {
        return contentRaw;
    }

    public void setContentRaw(String contentRaw) {
        this.contentRaw = contentRaw;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public boolean isOriginal() {
        return original;
    }

    public void setOriginal(boolean original) {
        this.original = original;
    }

    public String getUrl(){
        return UrlUtils.articleDetail(categoryAlias,alias);
    }

    public String getCategoryUrl(){
        return UrlUtils.categoryList(categoryAlias);
    }

    public String toString() {
        return "Article{" +
                "id=" + id +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", contentText='" + contentText + '\'' +
                ", contentRaw='" + contentRaw + '\'' +
                ", ip='" + ip + '\'' +
                ", postTime=" + postTime +
                ", keywords='" + keywords + '\'' +
                ", alias='" + alias + '\'' +
                ", categoryID=" + categoryID +
                ", original=" + original +
                ", categoryType=" + categoryType +
                ", categoryTitle='" + categoryTitle + '\'' +
                ", categoryAlias='" + categoryAlias + '\'' +
                ", categoryKeywords='" + categoryKeywords + '\'' +
                ", categoryDescription='" + categoryDescription + '\'' +
                ", categoryUrl='" + getCategoryUrl() + '\'' +
                ", getUrl='" + getUrl() + '\'' +
                '}';
    }
}
