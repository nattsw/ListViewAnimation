package com.example.natalie.listviewanimation;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.TypedValue;
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
    private ArrayList<NotificationEntry> mNotificationEntries;
    DynamicListView mNotificationList;
    DynamicListView mMessageCentreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeListViews();
        initializeData();

        /* Notes:
        Set the different types of adapters with Animations for your data and ListView:
          - Swipe to dismiss: setSwipeDismissAdapter
          - Swipe to undo, then dismiss: setUndoSwipeAdapter
          - Swings from the right, no swipe ability: setSwingFromRightAdapter
        Your ListView in the layout should be a DynamicListView
        (use com.nhaarman.listviewanimations.itemmanipulation.DynamicListView)
          - Your DynamicListView _cannot_ be in a RelativeLayout
            (animations won't work, somehow)
           */

        setSwipeDismissAdapter(mNotificationEntries, mNotificationList);
        setUndoSwipeAdapter(mNotificationEntries, mMessageCentreList);

        /* Notes:
        For this to work, you need to define the height and bottom (or top, if added)
        of each notification in values/dimens.xml:
                 notification_height
                 notification_bottom_margin
                 notification_top_margin
        * */
        setNumberOfVisibleNotifications(mNotificationList, 2);
        setNumberOfVisibleNotifications(mMessageCentreList, 4);
    }

    private void initializeListViews() {
        mNotificationList = (DynamicListView) findViewById(R.id.notification_list);
        mMessageCentreList = (DynamicListView) findViewById(R.id.message_list);
    }

    private void initializeData() {
        mNotificationEntries = new ArrayList<>();
        mNotificationEntries.add(new NotificationEntry(NotificationType.GENERAL, "Maybe migrate? Maybe not? If you want to, use this.", "Learn moar", "Learn moar", null));
        mNotificationEntries.add(new NotificationEntry(NotificationType.GENERAL, "If you want to increase your battery life, buy another battery.", "Enter!", "Learn moar", null));
        mNotificationEntries.add(new NotificationEntry(NotificationType.GENERAL, "You should download this.", "Learn moar", "Learn moar", null));
        mNotificationEntries.add(new NotificationEntry(NotificationType.GENERAL, "Concierge is developed by a bunch of awesome people", "Learn moar", "Learn moar", null));
        mNotificationEntries.add(new NotificationEntry(NotificationType.GENERAL, "Testing out different length messages. Hopefully everything will fit, we don't know.", "Learn moar", "Learn moar", null));
    }

    private int getNotificationHeight() {
        return getHeightFromRes(R.dimen.notification_height)
                + getHeightFromRes(R.dimen.notification_bottom_margin);
    }

    private int getHeightFromRes(int res) {
        return (int) (getResources().getDimension(res) / getResources().getDisplayMetrics().density);
    }

    private void setNumberOfVisibleNotifications(DynamicListView listView, int numberOfNotificationsVisible) {
        int dpNotificationHeight = getNotificationHeight();
        int dpListViewHeight = (numberOfNotificationsVisible * dpNotificationHeight) - 1;
        setHeightOfListView(listView, getPixelHeightFromDpValue(dpListViewHeight));
    }

    private int getPixelHeightFromDpValue(int dp) {
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
        return height;
    }

    private void setHeightOfListView(DynamicListView listView, int value) {
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = value;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private void setUndoSwipeAdapter(ArrayList<NotificationEntry> data, DynamicListView list) {
        ArrayAdapter<NotificationEntry> adapter = new UndoableNotificationListViewAdapter(this, data);
        SimpleSwipeUndoAdapter simpleSwipeUndoAdapter =
                new SimpleSwipeUndoAdapter(adapter, this, new MyOnDismissCallback(adapter));
        AnimationAdapter animAdapter = new SwingRightInAnimationAdapter(simpleSwipeUndoAdapter);
        animAdapter.setAbsListView(list);
        list.setAdapter(animAdapter);
        list.enableSimpleSwipeUndo();
    }

    private void setSwipeDismissAdapter(ArrayList<NotificationEntry> data, DynamicListView list) {
        ArrayAdapter<NotificationEntry> adapter = new NotificationListViewAdapter(this, data);

        SwipeDismissAdapter swipeDismissAdapter =
                new SwipeDismissAdapter(adapter, new MyOnDismissCallback(adapter));
        AnimationAdapter animAdapter = new SwingRightInAnimationAdapter(swipeDismissAdapter);
        animAdapter.setAbsListView(list);

        list.setAdapter(animAdapter);
        list.enableSwipeToDismiss(new MyOnDismissCallback(adapter));
    }

    private void setSwingFromRightAdapter(ArrayList<NotificationEntry> data, DynamicListView list) {
        ArrayAdapter<NotificationEntry> adapter = new NotificationListViewAdapter(this, data);

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
