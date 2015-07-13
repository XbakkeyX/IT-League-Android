package com.mycompany.itleague.model;


/**
 * Created by Сергей on 30.06.2015.
 */
public class TableObject {

    private String leagueName = new String();

    private TableModel mTableMainDatas = new TableModel();

    public void setLeagueName(String res) {
        this.leagueName = res;
    }

    public void setTableMainDatas(TableModel res) {
        this.mTableMainDatas = res;
    }

    public String getLeagueName() {
        return this.leagueName;
    }

    public TableModel getTableMainDatas() {
        return this.mTableMainDatas;
    }

    public TableObject() {
        this.leagueName = "";
        this.mTableMainDatas = new TableModel();
    }

}
