package com.mycompany.itleague.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.webkit.WebView;


import com.mycompany.itleague.R;
import com.mycompany.itleague.manager.MainApiClientProvider;
import com.mycompany.itleague.model.NewsMainData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.codec.binary.Base64;

/**
 * Created by Сергей on 26.06.2015.
 */
@EFragment(R.layout.news_info)
public class NewsInfoFragment extends Fragment {




    @Bean
    /*package*/
            MainApiClientProvider apiNewsClientProvider;

    private NewsMainData newsInfo = new NewsMainData();

    private long idOfNews = 0;

    StringBuilder sb;


/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        idOfNews = getArguments().getLong("message");
        return inflater.inflate(R.layout.news_info, container, false);
    }
*/

    @ViewById
    /*package*/
            WebView webViewNewsInfo;

    @Background
    void updateNews() {
        Bundle args = getArguments();
        if (args  != null && args.containsKey("message")){
            idOfNews = args.getLong("message");
        }
        String body = apiNewsClientProvider.getMainApiClient().getNewsInfo(idOfNews).getBody();
        byte[] valueDecoded= Base64.decodeBase64(apiNewsClientProvider.getMainApiClient().getNewsInfo(idOfNews).getBody().getBytes());
        System.out.println("Decoded value is " + new String(valueDecoded));
        body = new String(valueDecoded);

        sb = new StringBuilder();
        sb.append(
                "<HTML><HEAD><LINK href=\"css/styles.css\" type=\"text/css\" rel=\"stylesheet\"/> </HEAD><body><div style=\"margin:10px; padding-bottom\">");
        sb.append(body);
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
