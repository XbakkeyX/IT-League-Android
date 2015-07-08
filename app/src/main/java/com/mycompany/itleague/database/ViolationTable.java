package com.mycompany.itleague.database;

/**
 * Created by Сергей on 04.07.2015.
 */

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Violation")
public class ViolationTable extends Model {

    @Column(name = "playerFirstName")
    public String playerFirstName;

    @Column(name = "playerSecondName")
    public String playerSecondName;

    @Column(name = "playerTeam")
    public String playerTeam;

    @Column(name = "playerCard")
    public String playerCard;

    @Column(name = "playerTour")
    public String playerTour;


    public ViolationTable() {
        super();
    }

    public ViolationTable(String playerFirstName, String personSecondName, String playerTeam,
            String playerTour, String playerCard) {
        super();

        this.playerFirstName = playerFirstName;
        this.playerFirstName = personSecondName;
        this.playerTeam = playerTeam;
        this.playerCard = playerCard;
        this.playerTour = playerTour;
    }
}