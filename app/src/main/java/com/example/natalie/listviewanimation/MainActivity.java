package com.example.natalie.listviewanimation;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingRightInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SimpleSwipeUndoAdapter;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private ArrayList<NotificationEntry> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeData();

        DynamicListView notificationList = (DynamicListView) findViewById(R.id.notification_list);
        DynamicListView messageCentreList = (DynamicListView) findViewById(R.id.message_list);

        setSwipeDismissAdapter(mData, notificationList);
        setUndoSwipeAdapter(mData, messageCentreList);

        setNumberOfNotificationsVisible(notificationList, 2);
        setNumberOfNotificationsVisible(messageCentreList, 4);
    }

    private void initializeData() {
        mData = new ArrayList<>();
        mData.add(new NotificationEntry(NotificationType.GENERAL, "Maybe migrate? Maybe not? If you want to, use this.", "Learn moar", "Learn moar", null));
        mData.add(new NotificationEntry(NotificationType.GENERAL, "If you want to increase your battery life, buy another battery.", "Enter!", "Learn moar", null));
        mData.add(new NotificationEntry(NotificationType.GENERAL, "You should download this.", "Learn moar", "Learn moar", null));
        mData.add(new NotificationEntry(NotificationType.GENERAL, "Concierge is developed by a bunch of awesome people", "Learn moar", "Learn moar", null));
        mData.add(new NotificationEntry(NotificationType.GENERAL, "Testing out different length messages. Hopefully everything will fit, we don't know.", "Learn moar", "Learn moar", null));
    }

    private void setNumberOfNotificationsVisible(DynamicListView listView, int numberOfNotificationsVisible) {
        int dpOfOneNotification = 95;
        int dpHeight = numberOfNotificationsVisible * dpOfOneNotification;
        setHeightOfListView(listView, getHeightFromDP(dpHeight));
    }

    private int getHeightFromDP(int dp) {
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
        return height;
    }

    private void setHeightOfListView(DynamicListView listView, int value) {
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = value;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private ArrayAdapter<NotificationEntry> initializeList(ArrayList<NotificationEntry> data, boolean undo) {
        ArrayAdapter<NotificationEntry> adapter;
        if (undo) {
            adapter = new MessageCentreListViewAdapter(this, data);
        } else {
            adapter = new NotificationAreaListViewAdapter(this, data);
        }
        return adapter;
    }

    private void setUndoSwipeAdapter(ArrayList<NotificationEntry> data, DynamicListView list) {
        ArrayAdapter<NotificationEntry> adapter = initializeList(data, true);
        SimpleSwipeUndoAdapter simpleSwipeUndoAdapter =
                new SimpleSwipeUndoAdapter(adapter, this, new MyOnDismissCallback(adapter));
        AnimationAdapter animAdapter = new SwingRightInAnimationAdapter(simpleSwipeUndoAdapter);
        animAdapter.setAbsListView(list);
        list.setAdapter(animAdapter);
        list.enableSimpleSwipeUndo();
    }

    private void setSwipeDismissAdapter(ArrayList<NotificationEntry> data, DynamicListView list) {
        ArrayAdapter<NotificationEntry> adapter = initializeList(data, false);

        SwipeDismissAdapter swipeDismissAdapter =
                new SwipeDismissAdapter(adapter, new MyOnDismissCallback(adapter));
        AnimationAdapter animAdapter = new SwingRightInAnimationAdapter(swipeDismissAdapter);
        animAdapter.setAbsListView(list);
        list.setAdapter(animAdapter);
        list.enableSwipeToDismiss(new MyOnDismissCallback(adapter));
    }

    private void setSwingFromRightAdapter(ArrayList<NotificationEntry> data, DynamicListView list) {
        ArrayAdapter<NotificationEntry> adapter = initializeList(data, false);

        AnimationAdapter animAdapter = new SwingRightInAnimationAdapter(adapter);
        animAdapter.setAbsListView(list);
        list.setAdapter(animAdapter);
    }

    private class MyOnDismissCallback implements OnDismissCallback {
        ArrayAdapter<NotificationEntry> mAdapter;
        public MyOnDismissCallback(ArrayAdapter<NotificationEntry> adapter) {
            mAdapter = adapter;
        }
        @Override
        public void onDismiss(@NonNull final ViewGroup listView,
                              @NonNull final int[] reverseSortedPositions) {
            for (int position : reverseSortedPositions) {
                mAdapter.remove(position);
            }
            Toast.makeText(MainActivity.this,"Removed",Toast.LENGTH_LONG).show();
        }
    }
}
