package com.example.natalie.listviewanimation;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.UndoAdapter;

import java.util.ArrayList;

public class NotificationAreaListViewAdapter extends ArrayAdapter<NotificationEntry> {
    Context mContext;
    private static LayoutInflater mInflater = null;

    public NotificationAreaListViewAdapter(Context context, ArrayList<NotificationEntry> notifications) {
        this.mContext = context;

        /* notes: ArrayAdapters have an 'add' method which allows 'getItem' to be called.
        * So there's no need to keep a instance variable of the data :) */
        for (NotificationEntry n: notifications) {
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

        TextView headerTextView = (TextView) rowView.findViewById(R.id.notification_header);
        TextView bodyTextView = (TextView) rowView.findViewById(R.id.notification_text);
        headerTextView.setTypeface(null, Typeface.BOLD);

        NotificationEntry entry = getItem(position);
        headerTextView.setText(entry.getTitle());
        bodyTextView.setText(entry.getBody());

        return rowView;
    }
}
