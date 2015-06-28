package com.mycompany.itleague.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Сергей on 22.06.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsMainData {
    @JsonProperty("id")
    private long id;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("title")
    private String title;

    @JsonProperty("subtitle")
    private String subtitle;

    @JsonProperty ("author")
    private String author;

    @JsonProperty("comments_count")
    private long commentsCount;

    @JsonProperty("body")
    private String body;


    public long getId() {return this.id;}

    public String getCreatedAt() { return this.createdAt; }

    public String getUpdatedAt() { return this.updatedAt; }

    public String getTitle() { return this.title; }

    public String getSubtitle() { return this.subtitle; }

    public String getAuthor() { return this.author; }

    public long getCommentsCount () { return this.commentsCount; }

    public String getBody() { return  this.body; }
}
