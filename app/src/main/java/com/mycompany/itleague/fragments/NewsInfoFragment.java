package com.mycompany.itleague.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;


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

    StringBuilder htmlStringBuilder;

    private String stringByReplacingIframeTagWithATag(String string) {
        string = string.replace("&lt;", "<");
        string = string.replace("&gt;", ">");
        string = string.replace("&amp;", "&");
        string = string.replace("<iframe", "<a");
        string = string.replace("src=\"//coub", "href=\"http://coub");
        string = string.replace("src=\"http://youtube", "href=\"http://youtube");
        string = string.replace("src=\"//www.youtube", "href=\"//www.youtube");
        string = string.replace("src=\"//youtube", "href=\"//youtube");
        string = string.replace("src=\"http://coub", "href=\"http://coub");
        string = string.replace("</iframe", "Media</a");
        string = string.replace("\"//www.youtube", "\"http://www.youtube");
        return string;
    }

    @ViewById
    /*package*/
            WebView webViewNewsInfo;

    @Background
    void updateNews() {
        Bundle args = getArguments();
        if (args != null && args.containsKey("message")) {
            idOfNews = args.getLong("message");
        }
        String body = apiNewsClientProvider.getMainApiClient().getNewsInfo(idOfNews).getBody();
        byte[] valueDecoded = Base64.decodeBase64(
                apiNewsClientProvider.getMainApiClient().getNewsInfo(idOfNews).getBody()
                        .getBytes());
        System.out.println("Decoded value is " + new String(valueDecoded));
        body = new String(valueDecoded);

        htmlStringBuilder = new StringBuilder();
        htmlStringBuilder.append(
                "<HTML><HEAD><LINK href=\"css/styles.css\" type=\"text/css\" rel=\"stylesheet\"/> </HEAD><body><div style=\"margin:10px; padding-bottom:15px\">");

        body = stringByReplacingIframeTagWithATag(body);
        htmlStringBuilder.append(body);
        htmlStringBuilder.append("</div></body></HTML>");

        this.setNewsInfo();
    }

    @UiThread
    void setNewsInfo() {

        webViewNewsInfo.setWebChromeClient(new WebChromeClient());
        webViewNewsInfo.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null && url.startsWith("http://")) {
                    view.getContext().startActivity(
                            new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                } else {
                    return false;
                }
            }
        });
        webViewNewsInfo.getSettings().setSupportMultipleWindows(true);
        webViewNewsInfo.loadDataWithBaseURL("file:///android_asset/", htmlStringBuilder.toString(),
                "text/html",
                "utf-8", null);
    }

    @AfterViews
    void newsDefault() {
        this.updateNews();
    }
}
