package com.cache.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Article implements Serializable {
    private Long id;//主键
    private String articleTitle;//文章标题
    private LocalDateTime articleCreateDate;//创建日期
    private String articleContent;//文章内容
    private Integer isTop;//置顶字段
    private String addName;//添加者

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public LocalDateTime getArticleCreateDate() {
        return articleCreateDate;
    }

    public void setArticleCreateDate(LocalDateTime articleCreateDate) {
        this.articleCreateDate = articleCreateDate;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public String getAddName() {
        return addName;
    }

    public void setAddName(String addName) {
        this.addName = addName;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", articleTitle='" + articleTitle + '\'' +
                ", articleCreateDate=" + articleCreateDate +
                ", articleContent='" + articleContent + '\'' +
                ", isTop=" + isTop +
                ", addName='" + addName + '\'' +
                '}';
    }
}
