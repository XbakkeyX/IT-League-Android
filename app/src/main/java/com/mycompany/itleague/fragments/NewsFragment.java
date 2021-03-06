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
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.activeandroid.util.SQLiteUtils;
import com.mycompany.itleague.R;
import com.mycompany.itleague.adapters.NewsDataAdapter;
import com.mycompany.itleague.database.NewsTable;
import com.mycompany.itleague.manager.MainApiClientProvider;
import com.mycompany.itleague.model.NewsModel;

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

    private NewsModel mainData = new NewsModel();

    private NewsDataAdapter adapter;

    private int pageNumber = 0;

    private int newsPerPage = 15;

    boolean loadingMore = true;

    private int newsCount = 0;

    private int amountOfAllNews;

    private ArrayList<NewsModel> downloadedNews = new ArrayList<>();

    private ArrayList<NewsModel> newsFromDataBase = new ArrayList<>();

    private LayoutInflater inflater;

    private  View footerView;

    private TextView footer;

    @Bean
    /*package*/
            MainApiClientProvider apiNewsClientProvider;

    @ViewById
    /*package*/
            ListView listNewsView;



    @Background
        public void run() {
            loadingMore = true;
            downloadedNews.addAll(apiNewsClientProvider.getMainApiClient()
                    .getListNews(pageNumber, newsPerPage).getMainNewsData());
            setNewsInfo();
        }

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
            newsFromDataBase = new ArrayList<>();
            List<NewsTable> allNews = select.all().from(NewsTable.class).execute();
            for (NewsTable newsTable : allNews) {
                NewsModel oneNews = new NewsModel();

                oneNews.setId(newsTable.newsId);
                oneNews.setCreatedAt(newsTable.newsCreatedAt);
                oneNews.setUpdatedAt(newsTable.newsUpdatedAt);
                oneNews.setTitle(newsTable.newsTitle);
                oneNews.setSubtitle(newsTable.newsSubtitle);
                oneNews.setAuthor(newsTable.newsAuthor);
                oneNews.setCommentsCount(newsTable.newsCommentsCount);
                oneNews.setBody(newsTable.newsBody);
                newsFromDataBase.add(oneNews);
            }
            downloadedNews = newsFromDataBase;
        } else {
            SQLiteUtils.execSql("DELETE FROM NewsTable");
            downloadedNews = this.apiNewsClientProvider.getMainApiClient()
                    .getListNews(pageNumber, newsPerPage).getMainNewsData();
            amountOfAllNews = this.apiNewsClientProvider.getMainApiClient()
                    .getListNews(pageNumber, newsPerPage).getNewsCount();
            ActiveAndroid.beginTransaction();
            try {
                for (NewsModel newsModel : downloadedNews) {
                    NewsTable newsDataBase = new NewsTable();
                    newsDataBase.newsId = newsModel.getId();
                    newsDataBase.newsCreatedAt = newsModel.getCreatedAt();
                    newsDataBase.newsUpdatedAt = newsModel.getUpdatedAt();
                    newsDataBase.newsTitle = newsModel.getTitle();
                    newsDataBase.newsSubtitle = newsModel.getSubtitle();
                    newsDataBase.newsAuthor = newsModel.getAuthor();
                    newsDataBase.newsCommentsCount = newsModel.getCommentsCount();
                    newsDataBase.newsBody = newsModel.getBody();
                    newsDataBase.save();
                }
                ActiveAndroid.setTransactionSuccessful();
            } finally {
                ActiveAndroid.endTransaction();
            }
        }
        this.setNewsInfo();
    }

    @UiThread
    void setNewsInfo() {
        String footerText = isNetworkAvailable()? "Загружаем больше новостей" : "Отсутствует подключение к интернету :(";
        footer.setText(footerText);
        adapter = new NewsDataAdapter(getActivity(), downloadedNews);
        adapter.notifyDataSetChanged();
        loadingMore = false;
        listNewsView.setAdapter(adapter);
        int sizeOfNews = downloadedNews.size();
        listNewsView.setSelection(sizeOfNews - 15);
        newsCount += newsPerPage;
    }

    @AfterViews
    void newsDefault() {
        if (isNetworkAvailable()) {
            listNewsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View v, int position,
                        long arg3) {
                    mainData = (NewsModel) adapter.getItemAtPosition(position);
                    long tmp = mainData.getId();
                    Bundle bundle = new Bundle();
                    bundle.putLong("message", tmp);
                    NewsDetailsFragment_ fragmentNewsInfo = new NewsDetailsFragment_();
                    fragmentNewsInfo.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainer, fragmentNewsInfo, "CanBeReturned");
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }
        inflater = LayoutInflater.from(getActivity());
        footerView  = inflater.inflate(R.layout.news_footer, null, false);
        footer = (TextView) footerView.findViewById(R.id.textViewFooter);
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
                    if (amountOfAllNews != newsCount && isNetworkAvailable()) {
                        pageNumber++;
                        run();
                    }
                }
            }
        });
        this.updateNews();
    }
}
