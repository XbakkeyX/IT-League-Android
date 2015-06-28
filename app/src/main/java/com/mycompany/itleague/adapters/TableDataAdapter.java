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
import com.mycompany.itleague.model.TableLeaguesData;
import com.mycompany.itleague.model.TableMainData;
import com.mycompany.itleague.model.ViolationsDataResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Сергей on 25.06.2015.
 */


public class TableDataAdapter extends ArrayAdapter<TableMainData> {

    private static class ViewHolder {
        private TextView teamName;
        private TextView teamResults;
    }

    public TableDataAdapter(Context context, List<TableMainData> users) {
        super(context, R.layout.table_view, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TableMainData user = getItem(position);
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

        viewHolder.teamName.setText(user.getTeam());
        viewHolder.teamResults.setText(user.getGames() + " " + user.getWins() + " " + user.getDraws() + " " + user.getLoses());
        return convertView;
    }
}
