package com.mycompany.itleague.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Сергей on 20.06.2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ViolationModel {

    @JsonProperty("team_name")
    private String teamName;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("stats_name")
    private String statsName;

    @JsonProperty("tour_name")
    private String tourName;

    public String getTeamName() {
        return this.teamName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getStatsName() {
        return this.statsName;
    }

    public String getTourName() {
        return this.tourName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setStatsName(String statsName) {
        this.statsName = statsName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }



}
