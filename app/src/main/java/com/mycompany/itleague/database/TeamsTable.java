package com.mycompany.itleague.database;

/**
 * Created by Сергей on 04.07.2015.
 */

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "TeamTable")
public class TeamsTable extends Model {

    @Column(name = "leagueName")
    public String leagueName;

    @Column(name = "position")
    public String teamPosition;

    @Column(name = "positionChange")
    public String teamPositionChange;

    @Column(name = "teamName")
    public String teamName;

    @Column(name = "teamGames")
    public String teamGames;

    @Column(name = "teamWins")
    public String teamWins;

    @Column(name = "teamDraws")
    public String teamDraws;

    @Column(name = "teamLoses")
    public String teamLoses;

    @Column(name = "teamGoalsFor")
    public String teamGoalsFor;

    @Column(name = "teamGoalsAgainst")
    public String teamGoalsAgainst;

    @Column(name = "teamGoalsDiff")
    public String teamGoalsDiff;

    @Column(name = "teamScores")
    public String teamScores;

    public TeamsTable() {
        super();
    }

    public TeamsTable(String leagueName, String teamPosition, String teamPositionChange,
            String teamName, String teamGames, String teamWins, String teamDraws,
            String teamLoses, String teamGoalsFor, String teamGoalsAgainst, String teamGoalsDiff,
            String teamScores) {
        super();

        this.leagueName = leagueName;
        this.teamPosition = teamPosition;
        this.teamPositionChange = teamPositionChange;
        this.teamName = teamName;
        this.teamGames = teamGames;
        this.teamWins = teamWins;
        this.teamDraws = teamDraws;
        this.teamLoses = teamLoses;
        this.teamGoalsFor = teamGoalsFor;
        this.teamGoalsAgainst = teamGoalsAgainst;
        this.teamGoalsDiff = teamGoalsDiff;
        this.teamScores = teamScores;
    }
}