package com.mycompany.itleague.fragments;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.mycompany.itleague.R;
import com.mycompany.itleague.adapters.NewsDataAdapter;
import com.mycompany.itleague.manager.MainApiClientProvider;
import com.mycompany.itleague.model.NewsMainData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * Created by Сергей on 26.06.2015.
 */
@EFragment(R.layout.news)
public class NewsFragment extends Fragment {

    private NewsDataAdapter adapter;

    private int pageNumber = 0;

    private int newsPerPage = 15;

    boolean loadingMore = true;

    private int newsCount = 0;

    private int amountOfAllNews;

    ArrayList<NewsMainData> downloadedNews = new ArrayList<NewsMainData>();

    @Bean
    /*package*/
            MainApiClientProvider apiNewsClientProvider;

    @ViewById
    /*package*/
            ListView listNewsView;

    private Runnable loadMoreListItems = new Runnable() {
        @Override
        public void run() {
            loadingMore = true;
            downloadedNews.addAll(apiNewsClientProvider.getMainApiClient()
                    .getListNews(pageNumber, newsPerPage).getMainNewsData());
            //Done! now continue on the UI thread
            adapter = new NewsDataAdapter(getActivity(), downloadedNews);
            setNewsInfo();
        }
    };

    @Background
    void updateNews() {
        pageNumber = 1;
        downloadedNews = this.apiNewsClientProvider.getMainApiClient()
                .getListNews(pageNumber, newsPerPage).getMainNewsData();
        amountOfAllNews = this.apiNewsClientProvider.getMainApiClient()
                .getListNews(pageNumber, newsPerPage).getNewsCount();
        adapter = new NewsDataAdapter(getActivity(), downloadedNews);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View footerView = inflater.inflate(R.layout.news_footer, null, false);
        listNewsView.addFooterView(footerView);
        listNewsView.setOnScrollListener(new AbsListView.OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                    int visibleItemCount, int totalItemCount) {

                int lastInScreen = firstVisibleItem + visibleItemCount;
                //is the bottom item visible & not loading more already ? Load more !
                if ((lastInScreen == totalItemCount) && !(loadingMore)) {
                    if (amountOfAllNews != newsCount) {
                        Thread thread = new Thread(null, loadMoreListItems);
                        pageNumber++;
                        thread.start();
                    }
                }
            }

        });
        this.setNewsInfo();
    }

    @UiThread
    void setNewsInfo() {
        loadingMore = false;
        listNewsView.setAdapter(adapter);
        listNewsView.post(new Runnable() {
            @Override
            public void run() {
                int sizeOfNews = downloadedNews.size();
                listNewsView.setSelection(sizeOfNews - 15);
            }
        });
        newsCount += newsPerPage;
    }

    @AfterViews
    void newsDefault() {
        this.updateNews();
    }

}
