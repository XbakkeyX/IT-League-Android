package com.mycompany.itleague.fragments;

import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.mycompany.itleague.R;
import com.mycompany.itleague.adapters.NewsDataAdapter;
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
@EFragment(R.layout.news)
public class NewsFragment extends Fragment {

    private NewsDataAdapter adapter;
    private int pageNumber;
    private int newsPerPage = 15;

    @Bean
    /*package*/
    MainApiClientProvider apiNewsClientProvider;

    @ViewById
    /*package*/
    ListView listNewsView;

    @Background
    void updateNews() {
        pageNumber = 1;
        adapter = new NewsDataAdapter(getActivity(), this.apiNewsClientProvider.getMainApiClient().getListNews(pageNumber,newsPerPage).getMainNewsData());
        this.setNewsInfo();
    }


    @UiThread
    void setNewsInfo() {
        listNewsView.setAdapter(adapter);
    }

    @AfterViews
    void newsDefault() {
        this.updateNews();
    }

}
