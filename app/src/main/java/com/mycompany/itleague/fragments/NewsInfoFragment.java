package com.mycompany.itleague.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mycompany.itleague.R;
import com.mycompany.itleague.manager.MainApiClientProvider;
import com.mycompany.itleague.model.NewsMainData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Сергей on 26.06.2015.
 */
@EFragment
public class NewsInfoFragment extends Fragment {




    @Bean
    /*package*/
            MainApiClientProvider apiNewsClientProvider;

    private NewsMainData newsInfo = new NewsMainData();

    private long idOfNews = 0;

    StringBuilder sb;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        idOfNews = getArguments().getLong("message");
        return inflater.inflate(R.layout.news_info, container, false);
    }

    @ViewById
    /*package*/
            WebView webViewNewsInfo;

    @Background
    void updateNews() {
        sb = new StringBuilder();
        sb.append(
                "<HTML><HEAD><LINK href=\"styles.css\" type=\"text/css\" rel=\"stylesheet\"/> </HEAD><body><div style=\"margin:10p; padding-bottom:15px\">");
        sb.append(apiNewsClientProvider.getMainApiClient().getNewsInfo(idOfNews).getBody());
        sb.append("</div></body></HTML>");
        this.setNewsInfo();
    }

    @UiThread
    void setNewsInfo() {
        webViewNewsInfo.loadDataWithBaseURL("file:///android_asset/", sb.toString(), "text/html",
                "utf-8", null);
    }

    @AfterViews
    void newsDefault() {
        this.updateNews();
    }
}
