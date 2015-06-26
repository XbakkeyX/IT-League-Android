package com.mycompany.itleague.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by Сергей on 22.06.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsDataResponse {

    @JsonProperty("news_count")
    private int new_count;

    @JsonProperty("news")
    private List<NewsMainData> mainNewsData;

    public int getNewCount () { return this.new_count; }

    public List<NewsMainData> getMainNewsData () { return this.mainNewsData; }
}

