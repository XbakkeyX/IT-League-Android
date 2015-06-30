
package com.mycompany.itleague.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.mycompany.itleague.R;
import com.mycompany.itleague.adapters.ViolationsDataAdapter;
import com.mycompany.itleague.manager.MainApiClientProvider;
import com.mycompany.itleague.model.ViolationsConnectionData;
import com.mycompany.itleague.model.ViolationsMainData;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
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

    public ArrayList<String> tours = new ArrayList<String>();

    public ArrayList<String> getTours() {
        return tours;
    }

    @Bean
    /*package*/
            MainApiClientProvider apiViolationsClientProvider;

    @ViewById(R.id.listViolations)
    /*package*/
            StickyListHeadersListView listViolations;

    @Background
    void updateViolations() {

        ViolationsConnectionData violationsConnectionData = this.apiViolationsClientProvider
                .getMainApiClient().getViolationInfo().getMainViolationsData();
        List<ViolationsMainData> violationsMainDataArrayList = new ArrayList<>();
        for (ViolationsMainData violationsMainData : violationsConnectionData) {
            violationsMainDataArrayList.addAll(violationsConnectionData.getViolationsMainData());
        }
        String tmp = "";
        for (int i = 0; i < violationsMainDataArrayList.size(); i++) {
            if (tmp != violationsMainDataArrayList.get(i).getTeamName()) {
                tmp = violationsMainDataArrayList.get(i).getTeamName();
            }
            tours.add(tmp);
        }
        adapter = new ViolationsDataAdapter(getActivity(), violationsMainDataArrayList);
        this.setViolationInfo();
    }

    @UiThread
    void setViolationInfo() {
        listViolations.setAdapter(adapter);
    }

    @AfterViews
    void VioltationsrDefault() {
        this.updateViolations();
    }

}


