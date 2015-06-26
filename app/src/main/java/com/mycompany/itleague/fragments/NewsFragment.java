package com.mycompany.itleague.fragments;

import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.mycompany.itleague.R;
import com.mycompany.itleague.adapters.NewsDataApapter;
import com.mycompany.itleague.adapters.TableDataAdapter;
import com.mycompany.itleague.adapters.ViolationsDataAdapter;
import com.mycompany.itleague.manager.NewsApiClientProvider;
import com.mycompany.itleague.manager.TableApiClientProvider;
import com.mycompany.itleague.manager.ViolationsApiClientProvider;

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

    NewsDataApapter adapter;
    int pageNumber;
    int newsPerPage = 15;

    @Bean
    /*package*/
    NewsApiClientProvider apiNewsClientProvider;

    @ViewById
    /*package*/
    ListView listNewsView;

    @Background
    void updateNews() {
        pageNumber = 1;
        adapter = new NewsDataApapter(getActivity(), this.apiNewsClientProvider.getNewsApiClient().getListNews(pageNumber,newsPerPage).getMainNewsData());
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
