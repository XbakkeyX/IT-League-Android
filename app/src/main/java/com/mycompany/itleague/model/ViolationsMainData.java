package com.mycompany.itleague.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Сергей on 20.06.2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ViolationsMainData {
    @JsonProperty("team_name")
    private String teamName;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("second_name")
    private String secondName;

    @JsonProperty("stats_name")
    private String statsName;

    @JsonProperty("tour_name")
    private String tourName;

    public String  getTeamName () {
    return this.teamName;
    }

    public String  getFirstName () {
        return this.firstName;
    }

    public String  getSecondName () {
        return this.secondName;
    }

    public String  getStatsName () {
        return this.statsName;
    }

    public String  getTourName () {
        return this.tourName;
    }
}
