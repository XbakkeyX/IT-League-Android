package com.mycompany.itleague.database;

/**
 * Created by Сергей on 04.07.2015.
 */
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.mycompany.itleague.model.TableMainData;

//Notice how we specified the name of the table below
@Table(name = "TeamTable")
public class TeamsTable extends Model {

    // Notice how we specified the name of our column here
    @Column(name = "leagueName")
    public String leagueName;

    // Notice how we specified the name of our column here
    @Column(name = "teamMainData")
    public TableMainData teamMainData;

    public TeamsTable() {
        // Notice how super() has been called to perform default initialization
        // of our Model subclass
        super();
    }

    public TeamsTable(String leagueName, TableMainData teamMainData) {
        super();

        this.leagueName = leagueName;
        this.teamMainData = teamMainData;

    }
}