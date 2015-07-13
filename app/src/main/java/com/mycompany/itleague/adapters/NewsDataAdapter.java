package com.mycompany.itleague.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mycompany.itleague.R;
import com.mycompany.itleague.model.NewsModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Сергей on 26.06.2015.
 */
public class NewsDataAdapter extends ArrayAdapter<NewsModel> {

    private static class ViewHolder {

        private TextView titleTextView;

        private TextView previewTextView;

        private TextView authorTextView;

        private TextView dateTextView;
    }

    public NewsDataAdapter(Context context, List<NewsModel> users) {
        super(context, R.layout.news_view, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsModel newsData = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.news_view, parent, false);
            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.textTitle);
            Typeface fontOfTitle = Typeface
                    .createFromAsset(getContext().getAssets(), "fonts/HelveticaNeue.ttf");
            viewHolder.titleTextView.setTypeface(fontOfTitle);

            viewHolder.previewTextView = (TextView) convertView.findViewById(R.id.textPreview);
            Typeface fontOfPreview = Typeface
                    .createFromAsset(getContext().getAssets(), "fonts/HelveticaNeueCyr-Thin.otf");
            viewHolder.previewTextView.setTypeface(fontOfPreview);
            viewHolder.authorTextView = (TextView) convertView.findViewById(R.id.textNameAuthor);
            convertView.setTag(viewHolder);
            Typeface fontOfAuthor = Typeface
                    .createFromAsset(getContext().getAssets(), "fonts/HelveticaNeue.ttf");
            viewHolder.authorTextView.setTypeface(fontOfAuthor);
            viewHolder.dateTextView = (TextView) convertView.findViewById(R.id.textViewDate);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        java.util.Date date = null;
        try {
            date = form.parse(newsData.getCreatedAt());
        } catch (ParseException e) {

            e.printStackTrace();
        }
        SimpleDateFormat postFormater = new SimpleDateFormat("dd MMMM yyyy");
        String newDateStr = postFormater.format(date);
        viewHolder.dateTextView.setText(newDateStr);
        viewHolder.authorTextView.setText(newsData.getAuthor());
        viewHolder.titleTextView.setText(newsData.getTitle());
        String subtitle = newsData.getSubtitle();
        viewHolder.previewTextView.setText(subtitle.replaceAll("\\s+", " "));
        return convertView;
    }
}
