package com.mycompany.itleague.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by Сергей on 22.06.2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class TableLeaguesData {

    @JsonProperty("leagues")
    private List<TableRowsData> leagues;

    @JsonProperty("stage")
    private String stage;

    public List<TableRowsData> getLeagues() {
        return this.leagues;
    }

    public String getStage() {
        return this.stage;
    }
}
