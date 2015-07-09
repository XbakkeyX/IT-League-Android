package com.mycompany.itleague.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.util.SQLiteUtils;
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

    private ArrayList<TableObject> tableObjectsFromDataBase = new ArrayList<TableObject>();

    public ArrayList<TableRowsData> getTableRows() {
        return this.tableRowsDataArrayList;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
            tableObjectsFromDataBase = new ArrayList<TableObject>();
            List<TeamsTable> teams = select.all().from(TeamsTable.class).execute();
            for (TeamsTable teamsTable : teams) {
                TableObject team = new TableObject();
                TableMainData mainData = new TableMainData();
                team.setLeagueName(teamsTable.leagueName);
                mainData.setPosition(teamsTable.teamPosition);
                mainData.setTeam(teamsTable.teamName);
                mainData.setGames(teamsTable.teamGames);
                mainData.setWins(teamsTable.teamWins);
                mainData.setDraws(teamsTable.teamDraws);
                mainData.setLoses(teamsTable.teamLoses);
                mainData.setGoalsFor(teamsTable.teamGoalsFor);
                mainData.setGoalsAgainst(teamsTable.teamGoalsAgainst);
                mainData.setGoalsDiff(teamsTable.teamGoalsDiff);
                mainData.setScores(teamsTable.teamScores);
                team.setTableMainDatas(mainData);
                tableObjectsFromDataBase.add(team);
            }
            tableObjects = tableObjectsFromDataBase;
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
            SQLiteUtils.execSql("DELETE FROM TeamTable");
            ActiveAndroid.beginTransaction();
            try {
                for (TableObject tableObject : tableObjects) {
                    TeamsTable teamsDataBase = new TeamsTable();
                    TableMainData mainData = tableObject.getTableMainDatas();
                    teamsDataBase.leagueName = tableObject.getLeagueName();
                    teamsDataBase.teamPosition = mainData.getPosition();
                    teamsDataBase.teamPositionChange = mainData.getPositionChange();
                    teamsDataBase.teamName = mainData.getTeam();
                    teamsDataBase.teamGames = mainData.getGames();
                    teamsDataBase.teamDraws = mainData.getDraws();
                    teamsDataBase.teamWins = mainData.getWins();
                    teamsDataBase.teamLoses = mainData.getLoses();
                    teamsDataBase.teamGoalsFor = mainData.getGoalsFor();
                    teamsDataBase.teamGoalsAgainst = mainData.getGoalsAgainst();
                    teamsDataBase.teamGoalsDiff = mainData.getGoalsDiff();
                    teamsDataBase.teamScores = mainData.getScores();
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


