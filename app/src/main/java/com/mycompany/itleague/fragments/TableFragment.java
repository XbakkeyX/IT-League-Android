package com.mycompany.itleague.fragments;

import android.support.v4.app.Fragment;

import com.mycompany.itleague.R;
import com.mycompany.itleague.adapters.TableObject;
import com.mycompany.itleague.adapters.TableDataAdapter;
import com.mycompany.itleague.manager.MainApiClientProvider;
import com.mycompany.itleague.model.TableLeaguesData;
import com.mycompany.itleague.model.TableLeaguesResponse;
import com.mycompany.itleague.model.TableMainData;
import com.mycompany.itleague.model.TableRowsData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

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


    @Bean
    /*package*/
            MainApiClientProvider apiTableClientProvider;

    @ViewById(R.id.listTableView)
    /*package*/
            StickyListHeadersListView listTableView;

    @Background
    void updateTable() {
        TableLeaguesResponse tableLeaguesResponse = this.apiTableClientProvider.getMainApiClient()
                .getTableData();
        for (TableLeaguesData tableLeaguesData : tableLeaguesResponse) {
            tableRowsDataArrayList.addAll(tableLeaguesData.getLeagues());
        }

        for (int i = 0; i < tableRowsDataArrayList.size(); i++) {
            for (int j = 0; j < tableRowsDataArrayList.get(i).getRowList().size(); j++) {
                TableObject tableObject = new TableObject();
                tableObject.setLeagueName(tableRowsDataArrayList.get(i).getLeagueName());
                tableObject.setTableMainDatas(tableRowsDataArrayList.get(i).getRowList().get(j));
                tableObjects.add(tableObject);
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


