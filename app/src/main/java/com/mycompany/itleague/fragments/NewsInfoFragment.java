package com.mycompany.itleague.fragments;

import android.support.v4.app.Fragment;
import android.view.View;
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
@EFragment(R.layout.news_info)
public class NewsInfoFragment extends Fragment {

    @Bean
    /*package*/
            MainApiClientProvider apiNewsClientProvider;

   private NewsMainData newsInfo = new NewsMainData();

    @ViewById
    /*package*/
            TextView textNewsInfo;

    @ViewById
    /*package*/
            ListView listNewsView;

    @Background
    void updateNews() {
        listNewsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                    long arg3) {
                NewsMainData value = (NewsMainData) adapter.getItemAtPosition(position);
                newsInfo = apiNewsClientProvider.getMainApiClient().getNewsInfo(value.getId());
            }
        });
        this.setNewsInfo();
    }


    @UiThread
    void setNewsInfo() {
        //Here must be the code which setting the news info in the WebView depending on which id we
        //got into the ClickListener method
    }

    @AfterViews
    void newsDefault() {
        this.updateNews();
    }
}
