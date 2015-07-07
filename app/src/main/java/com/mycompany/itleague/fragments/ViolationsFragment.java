
package com.mycompany.itleague.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.mycompany.itleague.R;
import com.mycompany.itleague.adapters.ViolationsDataAdapter;
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

    @Bean
    /*package*/
            MainApiClientProvider apiViolationsClientProvider;

    @ViewById(R.id.listViolations)
    /*package*/
            StickyListHeadersListView listViolations;

    //InternetConnection connection = new InternetConnection(this.getActivity());

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    @Background
    void updateViolations() {

        ArrayList<ViolationsMainData> violationsMainDataArrayList
                = new ArrayList<ViolationsMainData>();
        if(!(isNetworkAvailable())){
            Select select = new Select();
            List<ViolationTable> players = select.all().from(ViolationTable.class).execute();
            for(int i = 0; i < players.size(); i++) {
                ViolationsMainData player = new ViolationsMainData();
                player.setFirstName(players.get(i).playerFirstName);
                player.setLastName(players.get(i).playerSecondName);
                player.setTeamName(players.get(i).playerTeam);
                player.setStatsName(players.get(i).playerCard);
                player.setTourName(players.get(i).playerTour);
                violationsMainDataArrayList.add(player);
            }
        }
        else {
            ArrayList<ArrayList<ViolationsMainData>> violationsArrayList
                    = apiViolationsClientProvider
                    .getMainApiClient().getViolationInfo().getMainViolationsData();
            for (int i = 0; i < violationsArrayList.size(); i++) {
                for (int j = 0; j < violationsArrayList.get(i).size(); j++) {
                    violationsMainDataArrayList.add(violationsArrayList.get(i).get(j));
                }
            }
            ActiveAndroid.beginTransaction();
            try {
                for (int i = 0; i < violationsMainDataArrayList.size(); i++) {
                    ViolationTable db = new ViolationTable();
                    db.playerFirstName = violationsMainDataArrayList.get(i).getFirstName();
                    db.playerSecondName = violationsMainDataArrayList.get(i).getLastName();
                    db.playerTeam = violationsMainDataArrayList.get(i).getTeamName();
                    db.playerCard = violationsMainDataArrayList.get(i).getStatsName();
                    db.playerTour = violationsMainDataArrayList.get(i).getTourName();
                    db.save();
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


