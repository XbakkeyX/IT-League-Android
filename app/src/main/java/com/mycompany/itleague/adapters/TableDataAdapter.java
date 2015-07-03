package com.mycompany.itleague.adapters;

import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
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

    private TableObject user;

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        user = getItem(position);
        if (convertView == null) {
            holder = new HeaderViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.table_header, parent, false);
            holder.league = (TextView) convertView.findViewById(R.id.textHeaderTable);
            holder.goals = (TextView) convertView.findViewById(R.id.goals);
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
        String first = "<font color='#00A42E'>+</font>" + "/";
        String next = "<font color='#EE0000'>-</font>";
        holder.goals.setText(Html.fromHtml(first + next));
        holder.league.setText(headerText);
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

        private TextView teamPlace;

        private TextView teamGoals;

        private TextView teamWins;

        private TextView teamDraws;

        private TextView teamLoses;

        private TextView teamGames;

        private TextView teamScores;
    }

    private static class HeaderViewHolder {

        private TextView league;

        private TextView goals;
    }

    public TableDataAdapter(Context context, ArrayList<TableObject> users) {
        super(context, R.layout.table_view, users);
        String league = "";
        for (int i = 0; i < users.size(); i++) {
            if (league != users.get(i).getLeagueName()) {
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
            viewHolder.teamPlace = (TextView) convertView.findViewById(R.id.textPlace);
            viewHolder.teamName = (TextView) convertView.findViewById(R.id.textTeamName);
            viewHolder.teamGames = (TextView) convertView.findViewById(R.id.textGames);
            viewHolder.teamWins = (TextView) convertView.findViewById(R.id.textWins);
            viewHolder.teamLoses = (TextView) convertView.findViewById(R.id.textLoses);
            viewHolder.teamDraws = (TextView) convertView.findViewById(R.id.textDraws);
            viewHolder.teamScores = (TextView) convertView.findViewById(R.id.textScores);
            viewHolder.teamGoals = (TextView) convertView.findViewById(R.id.textGoals);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.teamPlace.setText(user.getTableMainDatas().getPosition());
        viewHolder.teamName.setText(user.getTableMainDatas().getTeam());
        viewHolder.teamGames.setText(user.getTableMainDatas().getGames());
        viewHolder.teamWins.setText(user.getTableMainDatas().getWins());
        viewHolder.teamLoses.setText(user.getTableMainDatas().getLoses());
        viewHolder.teamDraws.setText(user.getTableMainDatas().getDraws());
        viewHolder.teamScores.setText(user.getTableMainDatas().getScores());
        String first = user.getTableMainDatas().getGoalsFor();
        String next = user.getTableMainDatas().getGoalsAgainst();
        viewHolder.teamGoals.setText(first + "/" + next, TextView.BufferType.SPANNABLE);
        Spannable s = (Spannable) viewHolder.teamGoals.getText();
        s.setSpan(new ForegroundColorSpan(0xFFFF0000), first.length() + 1,
                first.length() + 1 + next.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(new ForegroundColorSpan(0xFF00AF5A), 0, first.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return convertView;
    }
}
