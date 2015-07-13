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
    private List<TableModel> mTableModel;

    @JsonProperty("league_name")
    private String leagueName;

    public List<TableModel> getRowList() {
        return this.mTableModel;
    }

    public String getLeagueName() {
        return this.leagueName;
    }
}