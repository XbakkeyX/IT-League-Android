package com.mycompany.itleague.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Сергей on 22.06.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TableMainData {

    @JsonProperty("position")
    private String position;

    @JsonProperty("position_change")
    private String position_change;

    @JsonProperty("team")
    private String team;

    @JsonProperty("games")
    private String games;

    @JsonProperty("wins")
    private String wins;

    @JsonProperty("draws")
    private String draws;

    @JsonProperty("loses")
    private String loses;

    @JsonProperty("goals_for")
    private String goals_for;

    @JsonProperty("goals_against")
    private String goals_against;

    @JsonProperty("goals_diff")
    private String goals_diff;

    @JsonProperty("scores")
    private String scores;

    public String  getPosition () {
        return this.position;
    }

    public String  getPositionChange () {
        return this.position_change;
    }

    public String  getTeam () {
        return this.team;
    }

    public String  getGames () {
        return this.games;
    }

    public String  getWins () {
        return this.wins;
    }

    public String  getDraws () {
        return this.draws;
    }

    public String  getLoses () {
        return this.loses;
    }

    public String  getGoalsFor () {
        return this.goals_for;
    }

    public String  getGoalsAgainst () {
        return this.goals_against;
    }

    public String  getGoalsDiff () {
        return this.goals_diff;
    }

    public String  getScores () {
        return this.scores;
    }

}
