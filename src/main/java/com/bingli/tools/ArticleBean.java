package com.bingli.tools;

public class ArticleBean implements Comparable<ArticleBean> {

    private String datetime;
    private String title;
    private String content_url;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent_url() {
        return content_url;
    }

    public void setContent_url(String content_url) {
        this.content_url = content_url;
    }

    @Override
    public int compareTo(ArticleBean o) {
        return Integer.parseInt(o.getDatetime()) - Integer.parseInt(this.getDatetime());
    }

    @Override
    public String toString() {
        return "ArticleBean [datetime=" + datetime + ", title=" + title + ", content_url=" + content_url + "]";
    }

    
}
