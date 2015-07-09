
package com.mycompany.itleague.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.activeandroid.util.SQLiteUtils;
import com.mycompany.itleague.R;
import com.mycompany.itleague.adapters.ViolationsDataAdapter;
import com.mycompany.itleague.database.TeamsTable;
import com.mycompany.itleague.database.ViolationTable;
import com.mycompany.itleague.manager.MainApiClientProvider;
import com.mycompany.itleague.model.ViolationsMainData;


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
 * Created by Сергей on 23.06.2015.
 */

@EFragment(R.layout.violtations)
public class ViolationsFragment extends Fragment {

    private ViolationsDataAdapter adapter;

    ArrayList<ViolationsMainData> violationsMainDataArrayList
            = new ArrayList<ViolationsMainData>();

    ArrayList<ViolationsMainData> violationsMainDataFromDataBase
            = new ArrayList<ViolationsMainData>();


    @Bean
    /*package*/
            MainApiClientProvider apiViolationsClientProvider;

    @ViewById(R.id.listViolations)
    /*package*/
            StickyListHeadersListView listViolations;


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    @Background
    void updateViolations() {
        if (!(isNetworkAvailable())) {
            Select select = new Select();
            violationsMainDataFromDataBase = new ArrayList<ViolationsMainData>();
            List<ViolationTable> players = select.all().from(ViolationTable.class).execute();
            for (ViolationTable violationTable : players) {
                ViolationsMainData player = new ViolationsMainData();
                player.setFirstName(violationTable.playerFirstName);
                player.setLastName(violationTable.playerSecondName);
                player.setTeamName(violationTable.playerTeam);
                player.setStatsName(violationTable.playerCard);
                player.setTourName(violationTable.playerTour);
                violationsMainDataFromDataBase.add(player);
            }
            violationsMainDataArrayList = violationsMainDataFromDataBase;
        } else {
            ArrayList<ArrayList<ViolationsMainData>> violationsArrayList
                    = apiViolationsClientProvider
                    .getMainApiClient().getViolationInfo().getMainViolationsData();
            for (ArrayList<ViolationsMainData> violationsMainDatas : violationsArrayList) {
                for (ViolationsMainData violationsMainData : violationsMainDatas) {
                    violationsMainDataArrayList.add(violationsMainData);
                }
            }
            SQLiteUtils.execSql("DELETE FROM Violation");
            ActiveAndroid.beginTransaction();
            try {
                for (ViolationsMainData violationsMainData : violationsMainDataArrayList) {
                    ViolationTable violationDataBase = new ViolationTable();
                    violationDataBase.playerFirstName = violationsMainData.getFirstName();
                    violationDataBase.playerSecondName = violationsMainData.getLastName();
                    violationDataBase.playerTeam = violationsMainData.getTeamName();
                    violationDataBase.playerCard = violationsMainData.getStatsName();
                    violationDataBase.playerTour = violationsMainData.getTourName();
                    violationDataBase.save();
                }
                ActiveAndroid.setTransactionSuccessful();
            } finally {
                ActiveAndroid.endTransaction();
            }
        }
        adapter = new ViolationsDataAdapter(getActivity(), violationsMainDataArrayList);
        this.setViolationInfo();
    }

    @UiThread
    void setViolationInfo() {
        listViolations.setAdapter(adapter);
    }

    @AfterViews
    void ViolationsDefault() {
        this.updateViolations();
    }

}


