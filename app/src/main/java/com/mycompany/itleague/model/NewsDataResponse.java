package com.mycompany.itleague.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Сергей on 22.06.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsDataResponse {

    @JsonProperty("news_count")
    private int newsCount;

    @JsonProperty("news")
    private ArrayList<NewsModel> mainNewsData;

    public int getNewsCount() {
        return this.newsCount;
    }

    public ArrayList<NewsModel> getMainNewsData() {
        return this.mainNewsData;
    }
}

