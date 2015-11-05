package com.example.natalie.listviewanimation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.UndoAdapter;

import java.util.ArrayList;

public class NotificationAreaListViewAdapter extends ArrayAdapter<String> implements UndoAdapter {
    Context mContext;
    ArrayList<String> notifications;
    private static LayoutInflater mInflater = null;

    public NotificationAreaListViewAdapter(Context context, ArrayList<String> notifications) {
        this.mContext = context;

        this.notifications = notifications;
        for (String n: notifications) {
            add(n);
        }
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = mInflater.inflate(R.layout.notification_layout, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.notification_text);

        textView.setText(getItem(position));

        return rowView;
    }

    @NonNull
    @Override
    public View getUndoView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.undo_row, parent, false);
        }
        return view;
    }

    @NonNull
    @Override
    public View getUndoClickView(@NonNull View view) {
        return view.findViewById(R.id.undo_row_undobutton);
    }
}
