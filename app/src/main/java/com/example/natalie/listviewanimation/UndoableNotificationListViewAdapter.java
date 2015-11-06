package com.example.natalie.listviewanimation;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.UndoAdapter;

import java.util.ArrayList;

/* notes: If there is no need for undo, we can remove the UndoAdapter and its required implementations*/
public class UndoableNotificationListViewAdapter extends ArrayAdapter<NotificationEntry> implements UndoAdapter {
    Context mContext;
    ArrayList<NotificationEntry> notifications;
    private static LayoutInflater mInflater = null;

    public UndoableNotificationListViewAdapter(Context context, ArrayList<NotificationEntry> notifications) {
        this.mContext = context;
        this.notifications = notifications;

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

    /* notes: These two methods are the required implementations for UndoAdapter */

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
