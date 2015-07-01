package com.mycompany.itleague.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mycompany.itleague.R;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Сергей on 25.06.2015.
 */


public class TableDataAdapter extends ArrayAdapter<TableObject> implements
        StickyListHeadersAdapter {

    private ArrayList<String> leagueName = new ArrayList<String>();

    TableObject user;

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        user = getItem(position);
        if (convertView == null) {
            holder = new HeaderViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.table_header, parent, false);
            holder.leagueName = (TextView) convertView.findViewById(R.id.textHeaderTable);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        String headerText = new String();
        for (int i = 0; i < leagueName.size(); i++) {
            if (user.getLeagueName() == leagueName.get(i)) {
                headerText = leagueName.get(i);
            }
        }
        holder.leagueName.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        user = getItem(position);
        long tmp = 0;
        for (int i = 0; i < leagueName.size(); i++) {
            if (user.getLeagueName() == leagueName.get(i)) {
                tmp = i;
            }
        }
        return tmp;
    }

    private static class ViewHolder {

        private TextView teamName;

        private TextView teamResults;
    }

    private static class HeaderViewHolder {

        private TextView leagueName;
    }

    public TableDataAdapter(Context context, ArrayList<TableObject> users) {
        super(context, R.layout.table_view, users);
        String league = "";
        for(int i = 0; i< users.size(); i++){
            if(league!= users.get(i).getLeagueName()){
                league = users.get(i).getLeagueName();
                leagueName.add(league);
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        user = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.table_view, parent, false);
            viewHolder.teamName = (TextView) convertView.findViewById(R.id.textTeamName);
            viewHolder.teamResults = (TextView) convertView.findViewById(R.id.textTeamResults);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.teamName.setText(user.getTableMainDatas().getTeam());
        viewHolder.teamResults.setText(
                user.getTableMainDatas().getGames() + " " + user.getTableMainDatas().getWins() + " " + user.getTableMainDatas().getDraws() + " " +
                        user.getTableMainDatas().getLoses());
        return convertView;
    }
}
