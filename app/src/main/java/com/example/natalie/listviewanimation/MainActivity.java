package com.example.natalie.listviewanimation;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingRightInAnimationAdapter;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private static final String SAVEDINSTANCESTATE_ANIMATIONADAPTER = "savedinstancestate_animationadapter";
    private BaseAdapter mAdapter;
    private AnimationAdapter mAnimAdapter;
    private ListView mNotificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                setRightAdapter();
            }
        });

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
