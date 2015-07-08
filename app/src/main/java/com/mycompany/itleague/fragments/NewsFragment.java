package com.mycompany.itleague.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.mycompany.itleague.R;
import com.mycompany.itleague.adapters.NewsDataAdapter;
import com.mycompany.itleague.database.NewsTable;
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

    private NewsMainData mainData = new NewsMainData();

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

    @ViewById
    /*package*/
            RelativeLayout relLay;

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
                = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    @Background
    void updateNews() {
        pageNumber = 1;
        if (!(isNetworkAvailable())) {
            Select select = new Select();
            List<NewsTable> allNews = select.all().from(NewsTable.class).execute();
            for (NewsTable newsTable : allNews) {
                NewsMainData oneNews = new NewsMainData();
                oneNews.setId(newsTable.newsId);
                oneNews.setCreatedAt(newsTable.newsCreatedAt);
                oneNews.setUpdatedAt(newsTable.newsUpdatedAt);
                oneNews.setTitle(newsTable.newsTitle);
                oneNews.setSubtitle(newsTable.newsSubtitle);
                oneNews.setAuthor(newsTable.newsAuthor);
                oneNews.setCommentsCount(newsTable.newsCommentsCount);
                oneNews.setBody(newsTable.newsBody);
                downloadedNews.add(oneNews);
            }

        } else {
            downloadedNews = this.apiNewsClientProvider.getMainApiClient()
                    .getListNews(pageNumber, newsPerPage).getMainNewsData();
            amountOfAllNews = this.apiNewsClientProvider.getMainApiClient()
                    .getListNews(pageNumber, newsPerPage).getNewsCount();

            ActiveAndroid.beginTransaction();
            try {
                for (NewsMainData newsMainData : downloadedNews) {
                    NewsTable newsDataBase = new NewsTable();
                    newsDataBase.newsId = newsMainData.getId();
                    newsDataBase.newsCreatedAt = newsMainData.getCreatedAt();
                    newsDataBase.newsUpdatedAt = newsMainData.getUpdatedAt();
                    newsDataBase.newsTitle = newsMainData.getTitle();
                    newsDataBase.newsSubtitle = newsMainData.getSubtitle();
                    newsDataBase.newsAuthor = newsMainData.getAuthor();
                    newsDataBase.newsCommentsCount = newsMainData.getCommentsCount();
                    newsDataBase.newsBody = newsMainData.getBody();
                    newsDataBase.save();
                }
                ActiveAndroid.setTransactionSuccessful();
            } finally {
                ActiveAndroid.endTransaction();
            }
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
                mainData = (NewsMainData) adapter.getItemAtPosition(position);
                long tmp = mainData.getId();
                Bundle bundle = new Bundle();
                bundle.putLong("message", tmp);
                NewsInfoFragment_ fragmentNewsInfo = new NewsInfoFragment_();
                fragmentNewsInfo.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, fragmentNewsInfo, "CanBeReturned");
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View footerView = inflater.inflate(R.layout.news_footer, null, false);
        listNewsView.addFooterView(footerView);
        listNewsView.setOnScrollListener(new AbsListView.OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    listNewsView.invalidateViews();
                }
            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                    int visibleItemCount, int totalItemCount) {

                int lastInScreen = firstVisibleItem + visibleItemCount;
                if ((lastInScreen == totalItemCount) && !(loadingMore) && lastInScreen != 0
                        && totalItemCount != 0) {
                    if (amountOfAllNews != newsCount) {
                        Thread thread = new Thread(null, loadMoreListItems);
                        pageNumber++;
                        thread.start();
                    }
                }
            }
        });
        this.updateNews();
    }
}
