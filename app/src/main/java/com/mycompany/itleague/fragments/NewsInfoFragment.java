package com.mycompany.itleague.fragments;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.mycompany.itleague.R;
import com.mycompany.itleague.manager.MainApiClientProvider;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Сергей on 26.06.2015.
 */
@EFragment(R.layout.news_info)
public class NewsInfoFragment extends Fragment {
    //TODO: Understand how to send the id of the chosen news;
    @Bean
    /*package*/
    MainApiClientProvider apiNewsClientProvider;

    @ViewById
    /*package*/
    TextView textNewsInfo;

    @Background
    void updateNews() {
        this.setNewsInfo();
    }


    @UiThread
    void setNewsInfo() {

    }

    @AfterViews
    void newsDefault() {
        this.updateNews();
    }
}
