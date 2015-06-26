package com.mycompany.itleague.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Сергей on 20.06.2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ViolationsMainData {
    @JsonProperty("team_name")
    private String team_name;

    @JsonProperty("first_name")
    private String first_name;

    @JsonProperty("second_name")
    private String second_name;

    @JsonProperty("stats_name")
    private String stats_name;

    @JsonProperty("tour_name")
    private String tour_name;

    public String  getTeamName () {
    return this.team_name;
    }

    public String  getFirstName () {
        return this.first_name;
    }

    public String  getSecondName () {
        return this.second_name;
    }

    public String  getStatsName () {
        return this.stats_name;
    }

    public String  getTourName () {
        return this.tour_name;
    }
}
