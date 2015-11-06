package com.example.natalie.listviewanimation;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhaarman.listviewanimations.ArrayAdapter;

import java.util.ArrayList;

public class NotificationListViewAdapter extends ArrayAdapter<NotificationEntry> {
    Context mContext;
    private static LayoutInflater mInflater = null;

    public NotificationListViewAdapter(Context context, ArrayList<NotificationEntry> notifications) {
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

        CardView notificationCardView = (CardView) rowView.findViewById(R.id.notification_card);
        TextView headerTextView = (TextView) rowView.findViewById(R.id.notification_header);
        TextView bodyTextView = (TextView) rowView.findViewById(R.id.notification_text);
        ImageView iconImageView = (ImageView) rowView.findViewById(R.id.notification_icon);
        headerTextView.setTypeface(null, Typeface.BOLD);

        NotificationEntry entry = getItem(position);
        notificationCardView.setCardBackgroundColor(
                mContext.getResources().getColor(entry.getCardColorResFromType()));
        headerTextView.setText(entry.getTitle());
        bodyTextView.setText(entry.getBody());
        iconImageView.setImageResource(entry.getIconFromType());

        return rowView;
    }
}
