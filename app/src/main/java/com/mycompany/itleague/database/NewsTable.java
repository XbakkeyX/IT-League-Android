package com.mycompany.itleague.database;

/**
 * Created by Сергей on 04.07.2015.
 */

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


@Table(name = "NewsTable")
public class NewsTable extends Model {


    @Column(name = "newsId")
    public long newsId;

    @Column(name = "createdAt")
    public String newsCreatedAt;

    @Column(name = "updatedAt")
    public String newsUpdatedAt;

    @Column(name = "title")
    public String newsTitle;

    @Column(name = "subtitle")
    public String newsSubtitle;

    @Column(name = "author")
    public String newsAuthor;

    @Column(name = "comments_count")
    public long newsCommentsCount;

    @Column(name = "body")
    public String newsBody;


    public NewsTable() {
        super();
    }

    public NewsTable(long newsId, String newsCreatedAt, String newsUpdatedAt, String newsTitle,
            String newsSubtitle,
            String newsAuthor, long newsCommentsCount, String newsBody) {
        super();
        this.newsId = newsId;
        this.newsCreatedAt = newsCreatedAt;
        this.newsUpdatedAt = newsUpdatedAt;
        this.newsTitle = newsTitle;
        this.newsSubtitle = newsSubtitle;
        this.newsAuthor = newsAuthor;
        this.newsCommentsCount = newsCommentsCount;
        this.newsBody = newsBody;
    }
}