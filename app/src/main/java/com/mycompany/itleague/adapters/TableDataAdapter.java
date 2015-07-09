package com.mycompany.itleague.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mycompany.itleague.R;
import com.mycompany.itleague.model.TableObject;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Сергей on 25.06.2015.
 */


public class TableDataAdapter extends ArrayAdapter<TableObject> implements
        StickyListHeadersAdapter {

    private ArrayList<String> leagueName = new ArrayList<String>();

    private TableObject team;

    private static class ViewHolder {

        private TextView teamNameTextView;

        private TextView teamPlaceTextView;

        private TextView teamGoalsTextView;

        private TextView teamWinsTextView;

        private TextView teamDrawsTextView;

        private TextView teamLosesTextView;

        private TextView teamGamesTextView;

        private TextView teamScoresTextView;
    }

    private static class HeaderViewHolder {

        private TextView leagueTextView;

        private TextView goalsTextView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        team = getItem(position);
        if (convertView == null) {
            holder = new HeaderViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.table_header, parent, false);
            holder.leagueTextView = (TextView) convertView.findViewById(R.id.textHeaderTable);
            holder.goalsTextView = (TextView) convertView.findViewById(R.id.goals);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        String headerText = new String();
        for (String league : leagueName) {
            if (team.getLeagueName() == league) {
                headerText = league;
            }
        }
        String first = "<font color='#00A42E'>+</font>" + "/";
        String next = "<font color='#EE0000'>-</font>";
        holder.goalsTextView.setText(Html.fromHtml(first + next));
        holder.leagueTextView.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        team = getItem(position);
        long headerId = 0;
        for (int i = 0; i < leagueName.size(); i++) {
            if ((team.getLeagueName().equals(leagueName.get(i)))) {
                headerId = i;
            }
        }
        return headerId;
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
        team = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.table_view, parent, false);
            Typeface fontOfTable = Typeface
                    .createFromAsset(getContext().getAssets(), "fonts/HelveticaNeue.ttf");
            viewHolder.teamPlaceTextView = (TextView) convertView.findViewById(R.id.textPlace);
            viewHolder.teamNameTextView = (TextView) convertView.findViewById(R.id.textTeamName);
            viewHolder.teamGamesTextView = (TextView) convertView.findViewById(R.id.textGames);
            viewHolder.teamWinsTextView = (TextView) convertView.findViewById(R.id.textWins);
            viewHolder.teamLosesTextView = (TextView) convertView.findViewById(R.id.textLoses);
            viewHolder.teamDrawsTextView = (TextView) convertView.findViewById(R.id.textDraws);
            viewHolder.teamScoresTextView = (TextView) convertView.findViewById(R.id.textScores);
            viewHolder.teamGoalsTextView = (TextView) convertView.findViewById(R.id.textGoals);
            viewHolder.teamPlaceTextView.setTypeface(fontOfTable);
            viewHolder.teamNameTextView.setTypeface(fontOfTable);
            viewHolder.teamGamesTextView.setTypeface(fontOfTable);
            viewHolder.teamWinsTextView.setTypeface(fontOfTable);
            viewHolder.teamLosesTextView.setTypeface(fontOfTable);
            viewHolder.teamDrawsTextView.setTypeface(fontOfTable);
            viewHolder.teamScoresTextView.setTypeface(fontOfTable);
            viewHolder.teamGoalsTextView.setTypeface(fontOfTable);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.teamPlaceTextView.setText(team.getTableMainDatas().getPosition());
        viewHolder.teamNameTextView.setText(team.getTableMainDatas().getTeam());
        viewHolder.teamGamesTextView.setText(team.getTableMainDatas().getGames());
        viewHolder.teamWinsTextView.setText(team.getTableMainDatas().getWins());
        viewHolder.teamLosesTextView.setText(team.getTableMainDatas().getLoses());
        viewHolder.teamDrawsTextView.setText(team.getTableMainDatas().getDraws());
        viewHolder.teamScoresTextView.setText(team.getTableMainDatas().getScores());
        String first = team.getTableMainDatas().getGoalsFor();
        String next = team.getTableMainDatas().getGoalsAgainst();
        viewHolder.teamGoalsTextView.setText(first + "/" + next, TextView.BufferType.SPANNABLE);
        Spannable s = (Spannable) viewHolder.teamGoalsTextView.getText();
        s.setSpan(new ForegroundColorSpan(0xFFFF0000), first.length() + 1,
                first.length() + 1 + next.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(new ForegroundColorSpan(0xFF00AF5A), 0, first.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return convertView;
    }
}
