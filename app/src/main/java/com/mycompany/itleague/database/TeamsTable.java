package com.mycompany.itleague.database;

/**
 * Created by Сергей on 04.07.2015.
 */

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.mycompany.itleague.model.TableMainData;

@Table(name = "TeamTable")
public class TeamsTable extends Model {

    @Column(name = "leagueName")
    public String leagueName;

    @Column(name = "teamMainData")
    public TableMainData teamMainData;

    public TeamsTable() {
        super();
    }

    public TeamsTable(String leagueName, TableMainData teamMainData) {
        super();

        this.leagueName = leagueName;
        this.teamMainData = teamMainData;

    }
}