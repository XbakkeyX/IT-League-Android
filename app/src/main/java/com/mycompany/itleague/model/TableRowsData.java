package com.mycompany.itleague.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by Сергей on 22.06.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TableRowsData {

    @JsonProperty("rows")
    private List<TableMainData> tableMainData;

    @JsonProperty("league_name")
    private String leagueName;

    public List<TableMainData> getRowList() {
        return this.tableMainData;
    }

    public String getLeagueName() {
        return this.leagueName;
    }
}