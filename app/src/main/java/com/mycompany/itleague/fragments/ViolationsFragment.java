
package com.mycompany.itleague.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.mycompany.itleague.R;
import com.mycompany.itleague.adapters.ViolationsDataAdapter;
import com.mycompany.itleague.manager.MainApiClientProvider;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;


/**
 * Created by Сергей on 23.06.2015.
 */


@EFragment(R.layout.violtations)
public class ViolationsFragment extends Fragment {

    private ViolationsDataAdapter adapter;

    @Bean
    /*package*/
    MainApiClientProvider apiClientProvider;

    @ViewById
    /*package*/
    ListView listViolations;

    @Background
    void updateViolations() {
        for(int i = 0; i<this.apiClientProvider.getNewsApiClient().getViolationInfo().getMainViolationsData().size(); i++ ) {
            adapter = new ViolationsDataAdapter(getActivity(), this.apiClientProvider.getNewsApiClient().getViolationInfo().getMainViolationsData().get(i));
            this.setViolationInfo();
        }
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


