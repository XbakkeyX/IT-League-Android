
package com.mycompany.itleague.fragments;

import android.support.v4.app.Fragment;

import com.mycompany.itleague.R;
import com.mycompany.itleague.adapters.ViolationsDataAdapter;
import com.mycompany.itleague.manager.MainApiClientProvider;
import com.mycompany.itleague.model.ViolationsMainData;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

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

    @Background
    void updateViolations() {

        ArrayList<ViolationsMainData> violationsMainDataArrayList
                = new ArrayList<ViolationsMainData>();
        ArrayList<ArrayList<ViolationsMainData>> violationsArrayList = apiViolationsClientProvider
                .getMainApiClient().getViolationInfo().getMainViolationsData();
        for (int i = 0; i < violationsArrayList.size(); i++) {
            for (int j = 0; j < violationsArrayList.get(i).size(); j++) {
                violationsMainDataArrayList.add(violationsArrayList.get(i).get(j));
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


