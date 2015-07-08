package com.mycompany.itleague.model;


/**
 * Created by Сергей on 30.06.2015.
 */
public class TableObject {

    private String leagueName = new String();

    private TableMainData tableMainDatas = new TableMainData();

    public void setLeagueName(String res) {
        this.leagueName = res;
    }

    public void setTableMainDatas(TableMainData res) {
        this.tableMainDatas = res;
    }

    public String getLeagueName() {
        return this.leagueName;
    }

    public TableMainData getTableMainDatas() {
        return this.tableMainDatas;
    }

    public TableObject(String leagueName, TableMainData tableMainData) {
        this.leagueName = leagueName;
        this.tableMainDatas = tableMainData;
    }

    public TableObject() {
        this.leagueName = "";
        this.tableMainDatas = new TableMainData();
    }

}
