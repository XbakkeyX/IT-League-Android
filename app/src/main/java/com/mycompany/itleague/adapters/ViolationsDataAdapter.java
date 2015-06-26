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
import com.mycompany.itleague.model.ViolationsDataResponse;
import com.mycompany.itleague.model.ViolationsMainData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Сергей on 25.06.2015.
 */


public class ViolationsDataAdapter extends ArrayAdapter<ViolationsMainData> {
    // View lookup cache
    private static class ViewHolder {
        TextView name;
    }

    public ViolationsDataAdapter(Context context, List<ViolationsMainData> users) {
        super(context, R.layout.violations_view, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViolationsMainData user = getItem(position);
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
        String teamName = new String(user.getTeamName());
        Spannable wordtoSpan = new SpannableString(teamName);
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 0, teamName.length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.name.setText(user.getFirstName() + " " + user.getSecondName() + " ( " + teamName + ") ");

        return convertView;
    }
}
