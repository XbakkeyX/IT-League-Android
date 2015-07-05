package com.mycompany.itleague.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.mycompany.itleague.R;
import com.mycompany.itleague.adapters.NewsDataAdapter;
import com.mycompany.itleague.database.NewsTable;
import com.mycompany.itleague.database.TeamsTable;
import com.mycompany.itleague.internet.InternetConnection;
import com.mycompany.itleague.manager.MainApiClientProvider;
import com.mycompany.itleague.model.NewsMainData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Сергей on 26.06.2015.
 */
@EFragment(R.layout.news)
public class NewsFragment extends Fragment {

    NewsMainData mainData = new NewsMainData();
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

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    @Background
    void updateNews() {
        pageNumber = 1;
        if(!(isNetworkAvailable())) {
            Select select = new Select();
            List<NewsTable> allNews = select.all().from(NewsTable.class).execute();
            for (int i = 0; i < allNews.size(); i++) {
                NewsMainData oneNews = new NewsMainData();
                oneNews.setId(allNews.get(i).newsId);
                oneNews.setCreatedAt(allNews.get(i).newsCreatedAt);
                oneNews.setUpdatedAt(allNews.get(i).newsUpdatedAt);
                oneNews.setTitle(allNews.get(i).newsTitle);
                oneNews.setSubtitle(allNews.get(i).newsSubtitle);
                oneNews.setAuthor(allNews.get(i).newsAuthor);
                oneNews.setCommentsCount(allNews.get(i).newsCommentsCount);
                oneNews.setBody(allNews.get(i).newsBody);
                downloadedNews.add(oneNews);
            }

        }
        else {
            downloadedNews = this.apiNewsClientProvider.getMainApiClient()
                    .getListNews(pageNumber, newsPerPage).getMainNewsData();
            amountOfAllNews = this.apiNewsClientProvider.getMainApiClient()
                    .getListNews(pageNumber, newsPerPage).getNewsCount();

            ActiveAndroid.beginTransaction();
            try {
                for (int i = 0; i < downloadedNews.size(); i++) {
                    NewsTable db = new NewsTable();
                    db.newsId = downloadedNews.get(i).getId();
                    db.newsCreatedAt = downloadedNews.get(i).getCreatedAt();
                    db.newsUpdatedAt = downloadedNews.get(i).getUpdatedAt();
                    db.newsTitle = downloadedNews.get(i).getTitle();
                    db.newsSubtitle = downloadedNews.get(i).getSubtitle();
                    db.newsAuthor = downloadedNews.get(i).getAuthor();
                    db.newsCommentsCount = downloadedNews.get(i).getCommentsCount();
                    db.newsBody = downloadedNews.get(i).getBody();
                    db.save();
                }
                ActiveAndroid.setTransactionSuccessful();
            } finally {
                ActiveAndroid.endTransaction();
            }
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
        }
        adapter = new NewsDataAdapter(getActivity(), downloadedNews);
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
        listNewsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                    long arg3) {
                HeaderViewListAdapter ca = (HeaderViewListAdapter)adapter.getAdapter();
                NewsMainData value = (NewsMainData) ca.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putLong("message", value.getId());
                NewsInfoFragment fragobj = new NewsInfoFragment();
                fragobj.setArguments(bundle);
            }
        });
        this.updateNews();
    }
}
