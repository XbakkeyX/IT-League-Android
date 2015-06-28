package com.mycompany.itleague.fragments;

import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.mycompany.itleague.R;
import com.mycompany.itleague.adapters.TableDataAdapter;
import com.mycompany.itleague.adapters.ViolationsDataAdapter;
import com.mycompany.itleague.manager.MainApiClientProvider;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Сергей on 25.06.2015.
 */
@EFragment (R.layout.table)
public class TableFragment extends Fragment {

    private TableDataAdapter adapter;

    @Bean
    /*package*/
    MainApiClientProvider apiTableClientProvider;

    @ViewById
    /*package*/
    ListView listTableView;

        @Background
    void updateTable() {
        for (int i = 0; i < this.apiTableClientProvider.getNewsApiClient().getTableData().getTableLeaguesData().size(); i++) {
            for (int j = 0; j < this.apiTableClientProvider.getNewsApiClient().getTableData().getTableLeaguesData().get(i).getLeagues().size(); j++) {
                for (int y = 0; y < this.apiTableClientProvider.getNewsApiClient().getTableData().getTableLeaguesData().get(i).getLeagues().get(j).getRowList().size(); y++) {
                    adapter = new TableDataAdapter(getActivity(), this.apiTableClientProvider.getNewsApiClient().getTableData().getTableLeaguesData().get(i).getLeagues().get(j).getRowList());
                    this.setTableInfo();

                }
            }
        }
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

