package com.mycompany.itleague.fragments;

import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.mycompany.itleague.R;
import com.mycompany.itleague.adapters.TableDataAdapter;
import com.mycompany.itleague.adapters.ViolationsDataAdapter;
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

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by Сергей on 25.06.2015.
 */
@EFragment(R.layout.table)
public class TableFragment extends Fragment {


    private TableDataAdapter adapter;

    public ArrayList<String> leagueName = new ArrayList<String>();

    ArrayList<TableRowsData> tableRowsDataArrayList = new ArrayList<TableRowsData>();

    public ArrayList<String> getLeagueName() {
        return leagueName;
    }

    public ArrayList<TableRowsData> getTableRows() {
        return this.tableRowsDataArrayList;
    }

    @Bean
    /*package*/
            MainApiClientProvider apiTableClientProvider;

    @ViewById
    /*package*/
            StickyListHeadersListView listTableView;

    @Background
    void updateTable() {
        TableLeaguesResponse tableLeaguesResponse = this.apiTableClientProvider.getMainApiClient()
                .getTableData();
        for (TableLeaguesData tableLeaguesData : tableLeaguesResponse) {
            tableRowsDataArrayList.addAll(tableLeaguesData.getLeagues());
        }
        String tmp = "";
        for (int i = 0; i < tableRowsDataArrayList.size(); i++) {
            if (tmp != tableRowsDataArrayList.get(i).getLeagueName()) {
                tmp = tableRowsDataArrayList.get(i).getLeagueName();
                leagueName.add(tmp);
            }

        }

        adapter = new TableDataAdapter(getActivity(), tableRowsDataArrayList);
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


