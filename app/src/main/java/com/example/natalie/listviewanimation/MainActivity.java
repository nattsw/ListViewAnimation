package com.example.natalie.listviewanimation;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
    private ArrayAdapter<String> mAdapter;
    private AnimationAdapter mAnimAdapter;
    private DynamicListView mNotificationList;
    private ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeData();
        initializeAdapter();
        initializeFab();
    }

    private void initializeData() {
        data = new ArrayList<>();
        data.add("A");
        data.add("B");
        data.add("C");
        data.add("D");
    }

    private void initializeAdapter() {
        /*Choose which animation you want*/

//        setSwingFromRightAdapter();
        setUndoSwipeAdapter();
//        setSwipeDismissAdapter();
    }

    private void initializeFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializeAdapter();
            }
        });
    }

    private void initializeList(boolean undo) {
        mNotificationList = (DynamicListView) findViewById(R.id.notification_list);
        if (undo) {
            mAdapter = new MessageCentreListViewAdapter(this, data);
        } else {
            mAdapter = new NotificationAreaListViewAdapter(this, data);
        }
    }

    private void setUndoSwipeAdapter() {
        initializeList(true);

        SimpleSwipeUndoAdapter simpleSwipeUndoAdapter =
                new SimpleSwipeUndoAdapter(mAdapter, this, new MyOnDismissCallback());
        mAnimAdapter = new SwingRightInAnimationAdapter(simpleSwipeUndoAdapter);
        mAnimAdapter.setAbsListView(mNotificationList);
        mNotificationList.setAdapter(mAnimAdapter);
        mNotificationList.enableSimpleSwipeUndo();
    }

    private void setSwipeDismissAdapter() {
        initializeList(false);
        SwipeDismissAdapter swipeDismissAdapter =
                new SwipeDismissAdapter(mAdapter, new MyOnDismissCallback());
        mAnimAdapter = new SwingRightInAnimationAdapter(swipeDismissAdapter);
        mAnimAdapter.setAbsListView(mNotificationList);
        mNotificationList.setAdapter(mAnimAdapter);
        mNotificationList.enableSwipeToDismiss(new MyOnDismissCallback());
    }

    private void setSwingFromRightAdapter() {
        initializeList(false);
        mAnimAdapter = new SwingRightInAnimationAdapter(mAdapter);
        mAnimAdapter.setAbsListView(mNotificationList);
        mNotificationList.setAdapter(mAnimAdapter);
    }

    private class MyOnDismissCallback implements OnDismissCallback {
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
