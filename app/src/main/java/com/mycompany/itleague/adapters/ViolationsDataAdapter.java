package com.mycompany.itleague.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mycompany.itleague.R;
import com.mycompany.itleague.model.ViolationsMainData;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Сергей on 25.06.2015.
 */


public class ViolationsDataAdapter extends ArrayAdapter<ViolationsMainData> implements
        StickyListHeadersAdapter {

    public ArrayList<String> tours = new ArrayList<String>();

    ViolationsMainData user;

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        user = getItem(position);
        if (convertView == null) {
            holder = new HeaderViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.violations_header, parent, false);
            holder.tour = (TextView) convertView.findViewById(R.id.textHeaderViolations);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        String headerText = "";

        for (int i = 0; i < tours.size(); i++) {
            if (user.getTourName() == tours.get(i)) {
                headerText = tours.get(i);
                holder.tour.setText(headerText);
            }
        }
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        user = getItem(position);
        long tmp = 0;
        for (int i = 0; i < tours.size(); i++) {
            if (user.getTourName().equals(tours.get(i))) {
                tmp = i;
            }
        }
        return tmp;
    }


    private static class ViewHolder {

        private TextView name;
    }

    private static class HeaderViewHolder {

        private TextView tour;
    }

    public ViolationsDataAdapter(Context context, ArrayList<ViolationsMainData> users) {
        super(context, R.layout.violations_view, users);
        String tour = "";
        for(int i = 0; i< users.size(); i++)
            if(!(tour.equals(users.get(i).getTourName()))) {
                tour = users.get(i).getTourName();
                tours.add(tour);
            }


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        user = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.violations_view, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.textViolationsView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String teamName = user.getTeamName();
        Spannable wordtoSpan = new SpannableString(teamName);
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 0, teamName.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.name.setText(
                user.getFirstName() + " " + user.getLastName() + " ( " + teamName + ") ");

        return convertView;
    }
}
