package com.mycompany.itleague.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mycompany.itleague.R;
import com.mycompany.itleague.model.NewsMainData;
import com.mycompany.itleague.model.TableMainData;

import java.util.List;

/**
 * Created by Сергей on 26.06.2015.
 */
public class NewsDataApapter extends ArrayAdapter<NewsMainData> {

    private static class ViewHolder {
        //TODO: Find out how the views must look;
        private TextView title;
        private TextView preview;
    }

    public NewsDataApapter(Context context, List<NewsMainData> users) {
        super(context, R.layout.news_view, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsMainData user = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.news_view, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.textTitle);
            viewHolder.preview = (TextView) convertView.findViewById(R.id.textPreview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(user.getTitle());
        viewHolder.preview.setText(user.getSubtitle());
        return convertView;
    }
}
