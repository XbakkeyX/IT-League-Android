package com.mycompany.itleague.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mycompany.itleague.R;
import com.mycompany.itleague.model.NewsMainData;

import java.util.List;

/**
 * Created by Сергей on 26.06.2015.
 */
public class NewsDataAdapter extends ArrayAdapter<NewsMainData> {

    private static class ViewHolder {

        private TextView titleTextView;

        private TextView previewTextView;

        private TextView authorTextView;
    }

    public NewsDataAdapter(Context context, List<NewsMainData> users) {
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
            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.textTitle);
            viewHolder.previewTextView = (TextView) convertView.findViewById(R.id.textPreview);
            viewHolder.authorTextView = (TextView) convertView.findViewById(R.id.textNameAuthor);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.authorTextView.setText(user.getAuthor());
        viewHolder.titleTextView.setText(user.getTitle());
        viewHolder.previewTextView.setText(user.getSubtitle());
        return convertView;
    }
}
