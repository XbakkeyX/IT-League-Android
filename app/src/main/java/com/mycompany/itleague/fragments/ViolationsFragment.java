
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
import com.mycompany.itleague.database.ViolationTable;
import com.mycompany.itleague.manager.MainApiClientProvider;
import com.mycompany.itleague.model.ViolationModel;


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

    private ArrayList<ViolationModel> violationMainDatas
            = new ArrayList<>();

    private ArrayList<ViolationModel> violationFromDataBase
            = new ArrayList<>();


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
            violationFromDataBase = new ArrayList<>();
            List<ViolationTable> players = select.all().from(ViolationTable.class).execute();
            for (ViolationTable violationTable : players) {
                ViolationModel player = new ViolationModel();
                player.setFirstName(violationTable.playerFirstName);
                player.setLastName(violationTable.playerSecondName);
                player.setTeamName(violationTable.playerTeam);
                player.setStatsName(violationTable.playerCard);
                player.setTourName(violationTable.playerTour);
                violationFromDataBase.add(player);
            }
            violationMainDatas = violationFromDataBase;
        } else {
            ArrayList<ArrayList<ViolationModel>> violationsArrayList
                    = apiViolationsClientProvider
                    .getMainApiClient().getViolationInfo().getMainViolationsData();
            violationMainDatas = new ArrayList<>();
            for (ArrayList<ViolationModel> violationModels : violationsArrayList) {
                for (ViolationModel violationModel : violationModels) {
                    violationMainDatas.add(violationModel);
                }
            }
            SQLiteUtils.execSql("DELETE FROM Violation");
            ActiveAndroid.beginTransaction();
            try {
                for (ViolationModel violationModel : violationMainDatas) {
                    ViolationTable violationDataBase = new ViolationTable();
                    violationDataBase.playerFirstName = violationModel.getFirstName();
                    violationDataBase.playerSecondName = violationModel.getLastName();
                    violationDataBase.playerTeam = violationModel.getTeamName();
                    violationDataBase.playerCard = violationModel.getStatsName();
                    violationDataBase.playerTour = violationModel.getTourName();
                    violationDataBase.save();
                }
                ActiveAndroid.setTransactionSuccessful();
            } finally {
                ActiveAndroid.endTransaction();
            }
        }
        adapter = new ViolationsDataAdapter(getActivity(), violationMainDatas);
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


