package com.mycompany.itleague.adapters;

import com.mycompany.itleague.fragments.TableFragment;
import com.mycompany.itleague.model.TableMainData;

import java.util.ArrayList;

/**
 * Created by Сергей on 30.06.2015.
 */
public class TableAdapter {
    //TableFragment tf = new TableFragment();
    private String leagueName = new String();

    private  TableMainData tableMainDatas = new TableMainData();

    public void setLeagueName (String res) { this.leagueName = res;}

    public void setTableMainDatas (TableMainData res) { this.tableMainDatas = res;}

    public String getLeagueName () { return this.leagueName; }

    public  TableMainData getTableMainDatas () { return this.tableMainDatas; }

}
