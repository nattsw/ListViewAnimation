package com.example.natalie.listviewanimation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingRightInAnimationAdapter;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private BaseAdapter mAdapter;
    private AnimationAdapter mAnimAdapter;
    private ListView mNotificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeList();
    }

    private void initializeList() {
        mNotificationList = (ListView) findViewById(R.id.notification_list);
        ArrayList<String> a = new ArrayList<>();
        a.add("A");
        a.add("B");
        mAdapter = new NotificationAreaListViewAdapter(this, a);
        setRightAdapter();
    }

    private void setRightAdapter() {
        mAnimAdapter = new SwingRightInAnimationAdapter(mAdapter);
        mAnimAdapter.setAbsListView(mNotificationList);
        mNotificationList.setAdapter(mAnimAdapter);
    }
}
