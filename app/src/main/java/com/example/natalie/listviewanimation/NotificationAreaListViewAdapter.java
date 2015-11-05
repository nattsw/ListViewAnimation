package com.example.natalie.listviewanimation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nhaarman.listviewanimations.appearance.SingleAnimationAdapter;

import java.util.ArrayList;

public class NotificationAreaListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> notifications;
    private static LayoutInflater inflater = null;

    public NotificationAreaListViewAdapter(Context context, ArrayList<String> notifications) {
        this.context = context;
        this.notifications = notifications;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return notifications.size();
    }

    @Override
    public Object getItem(int position) {
        return notifications.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = mInflater.inflate(R.layout.notification_layout, null);

        TextView textView = (TextView) rowView.findViewById(R.id.notification_text);

        textView.setText(notifications.get(position));

        return rowView;
    }
}
