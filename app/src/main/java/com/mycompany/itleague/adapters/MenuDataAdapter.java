package com.mycompany.itleague.adapters;

import com.mycompany.itleague.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Сергей on 02.07.2015.
 */
public class MenuDataAdapter extends ArrayAdapter<String> {

    private static class ViewHolder {

        private ImageView image;

        private TextView menu;
    }

    public MenuDataAdapter(Context context, ArrayList<String> users) {
        super(context, R.layout.menu_view, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String user = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.menu_view, parent, false);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.imageMenu);
            viewHolder.menu = (TextView) convertView.findViewById(R.id.textMenu);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        switch (position) {
            case 0:
                viewHolder.image.setImageResource(R.drawable.icon_scedule);
                break;
            case 1:
                viewHolder.image.setImageResource(R.drawable.icon_news);
                break;
            case 2:
                viewHolder.image.setImageResource(R.drawable.icon_fouls);
                break;
            case 3:
                viewHolder.image.setImageResource(R.drawable.table_icon);
                break;
        }
        viewHolder.menu.setText(user);
        return convertView;
    }
}
