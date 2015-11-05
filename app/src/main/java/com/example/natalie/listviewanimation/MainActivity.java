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
    private ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeData();
        setSwipeDismissAdapter((DynamicListView) findViewById(R.id.notification_list));
        setUndoSwipeAdapter((DynamicListView) findViewById(R.id.message_list));
        initializeFab();
    }

    private void initializeData() {
        data = new ArrayList<>();
        data.add("A");
        data.add("B");
        data.add("C");
        data.add("D");
    }

    private void initializeFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSwipeDismissAdapter((DynamicListView) findViewById(R.id.notification_list));
                setUndoSwipeAdapter((DynamicListView) findViewById(R.id.message_list));
            }
        });
    }

    private ArrayAdapter<String> initializeList(DynamicListView list, boolean undo) {
        ArrayAdapter<String> adapter;
        if (undo) {
            adapter = new MessageCentreListViewAdapter(this, data);
        } else {
            adapter = new NotificationAreaListViewAdapter(this, data);
        }
        return adapter;
    }

    private void setUndoSwipeAdapter(DynamicListView list) {
        ArrayAdapter<String> adapter = initializeList(list, true);
        SimpleSwipeUndoAdapter simpleSwipeUndoAdapter =
                new SimpleSwipeUndoAdapter(adapter, this, new MyOnDismissCallback(adapter));
        AnimationAdapter animAdapter = new SwingRightInAnimationAdapter(simpleSwipeUndoAdapter);
        animAdapter.setAbsListView(list);
        list.setAdapter(animAdapter);
        list.enableSimpleSwipeUndo();
    }

    private void setSwipeDismissAdapter(DynamicListView list) {
        ArrayAdapter<String> adapter = initializeList(list, false);

        SwipeDismissAdapter swipeDismissAdapter =
                new SwipeDismissAdapter(adapter, new MyOnDismissCallback(adapter));
        AnimationAdapter animAdapter = new SwingRightInAnimationAdapter(swipeDismissAdapter);
        animAdapter.setAbsListView(list);
        list.setAdapter(animAdapter);
        list.enableSwipeToDismiss(new MyOnDismissCallback(adapter));
    }

    private void setSwingFromRightAdapter(DynamicListView list) {
        ArrayAdapter<String> adapter = initializeList(list, false);

        AnimationAdapter animAdapter = new SwingRightInAnimationAdapter(adapter);
        animAdapter.setAbsListView(list);
        list.setAdapter(animAdapter);
    }

    private class MyOnDismissCallback implements OnDismissCallback {
        ArrayAdapter<String> mAdapter;
        public MyOnDismissCallback(ArrayAdapter<String> adapter) {
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
