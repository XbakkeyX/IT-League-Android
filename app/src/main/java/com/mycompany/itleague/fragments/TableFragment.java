package com.mycompany.itleague.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.mycompany.itleague.R;
import com.mycompany.itleague.database.TeamsTable;
import com.mycompany.itleague.model.TableMainData;
import com.mycompany.itleague.model.TableObject;
import com.mycompany.itleague.adapters.TableDataAdapter;
import com.mycompany.itleague.manager.MainApiClientProvider;
import com.mycompany.itleague.model.TableLeaguesData;
import com.mycompany.itleague.model.TableLeaguesResponse;
import com.mycompany.itleague.model.TableRowsData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by Сергей on 25.06.2015.
 */
@EFragment(R.layout.table)
public class TableFragment extends Fragment {

    private TableDataAdapter adapter;

    private ArrayList<TableRowsData> tableRowsDataArrayList = new ArrayList<TableRowsData>();

    private ArrayList<TableObject> tableObjects = new ArrayList<TableObject>();

    public ArrayList<TableRowsData> getTableRows() {
        return this.tableRowsDataArrayList;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    @Bean
    /*package*/
            MainApiClientProvider apiTableClientProvider;

    @ViewById(R.id.listTableView)
    /*package*/
            StickyListHeadersListView listTableView;

    @Background
    void updateTable() {
        if (!(isNetworkAvailable())) {
            Select select = new Select();
            List<TeamsTable> teams = select.all().from(TeamsTable.class).execute();
            for (TeamsTable teamsTable : teams) {
                TableObject team = new TableObject();
                team.setLeagueName(teamsTable.leagueName);
                team.setTableMainDatas(teamsTable.teamMainData);
                tableObjects.add(team);
            }
        } else {
            TableLeaguesResponse tableLeaguesResponse = this.apiTableClientProvider
                    .getMainApiClient()
                    .getTableData();
            for (TableLeaguesData tableLeaguesData : tableLeaguesResponse) {
                tableRowsDataArrayList.addAll(tableLeaguesData.getLeagues());
            }

            for (TableRowsData rowsData : tableRowsDataArrayList) {
                for (TableMainData mainData : rowsData.getRowList()) {
                    TableObject tableObject = new TableObject();
                    tableObject.setLeagueName(rowsData.getLeagueName());
                    tableObject.setTableMainDatas(mainData);
                    tableObjects.add(tableObject);
                }
            }
            ActiveAndroid.beginTransaction();
            try {
                for (TableObject tableObject : tableObjects) {
                    TeamsTable teamsDataBase = new TeamsTable();
                    teamsDataBase.leagueName = tableObject.getLeagueName();
                    teamsDataBase.teamMainData = tableObject.getTableMainDatas();
                    teamsDataBase.save();
                }
                ActiveAndroid.setTransactionSuccessful();
            } finally {
                ActiveAndroid.endTransaction();
            }

        }
        adapter = new TableDataAdapter(getActivity(), tableObjects);
        this.setTableInfo();
    }


    @UiThread
    void setTableInfo() {
        listTableView.setAdapter(adapter);
    }

    @AfterViews
    void TableDefault() {
        this.updateTable();
    }
}


