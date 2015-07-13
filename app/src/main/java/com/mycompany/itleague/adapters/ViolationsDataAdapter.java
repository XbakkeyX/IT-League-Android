package com.mycompany.itleague.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mycompany.itleague.R;
import com.mycompany.itleague.model.ViolationModel;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Сергей on 25.06.2015.
 */


public class ViolationsDataAdapter extends ArrayAdapter<ViolationModel> implements
        StickyListHeadersAdapter {

    public ArrayList<String> unicTours = new ArrayList<>();

    private ViolationModel player;


    private static class ViewHolder {

        private TextView nameTextView;

        private ImageView cardImageView;
    }

    private static class HeaderViewHolder {

        private TextView tourTextView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        player = getItem(position);
        if (convertView == null) {
            holder = new HeaderViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.violations_header, parent, false);
            holder.tourTextView = (TextView) convertView.findViewById(R.id.textHeaderViolations);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        String headerText = "";
        for (String tour : unicTours) {
            if ((player.getTourName().equals(tour))) {
                headerText = tour;
            }
        }
        holder.tourTextView.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        player = getItem(position);
        long headerId = 0;
        for (int i = 0; i < unicTours.size(); i++) {
            if (player.getTourName().equals(unicTours.get(i))) {
                headerId = i;
            }
        }
        return headerId;
    }


    public ViolationsDataAdapter(Context context, ArrayList<ViolationModel> users) {
        super(context, R.layout.violations_view, users);
        String tour = "";
        for (ViolationModel violationModel : users) {
            if (!(tour.equals(violationModel.getTourName()))) {
                tour = violationModel.getTourName();
                unicTours.add(tour);
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        player = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.violations_view, parent, false);
            Typeface fontOfName = Typeface
                    .createFromAsset(getContext().getAssets(), "fonts/HelveticaNeue.ttf");
            viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.textViolationsView);
            viewHolder.cardImageView = (ImageView) convertView.findViewById(R.id.imageCard);
            viewHolder.nameTextView.setTypeface(fontOfName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String teamName = player.getTeamName();
        if ((player.getStatsName().equals("yellow_card"))) {
            viewHolder.cardImageView.setImageResource(R.drawable.yellowcard);
        } else if ((player.getStatsName().equals("red_card"))) {
            viewHolder.cardImageView.setImageResource(R.drawable.redcard);
        }

        Spannable wordtoSpan = new SpannableString(teamName);
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.GREEN), 0, teamName.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        viewHolder.nameTextView.setText(
                player.getLastName() + " " + player.getFirstName() + " (" + teamName + ") ");

        return convertView;
    }
}
