Custom ListViewAnimation
=========

### Required dependencies

	dependencies {
	    compile 'com.android.support:design:22.2.+'
	    compile 'com.android.support:cardview-v7:22.2.+'
	    
	    compile 'com.nineoldandroids:library:2.4.0'
	    compile 'com.nhaarman.listviewanimations:lib-core:3.1.0@aar'
	    compile 'com.nhaarman.listviewanimations:lib-manipulation:3.1.0@aar'
	}
- `listviewanimations` library uses `nineoldandroids` for animations
- `listviewanimations` provides sweeeeet animation adapters you can put your existing adapters into.

### How to use

1. Populate your data source using an ArrayList

	`ArrayList<NotificationEntry> notificationEntries = ...;`

1. Use a DynamicListView in your layout. 
	
        <com.nhaarman.listviewanimations.itemmanipulation.DynamicListView
            android:id="@+id/notification_list"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginBottom="20dp"
            android:layout_marginTop="20dp"
            android:clipToPadding="false"
            android:divider="@android:color/transparent"
            android:fitsSystemWindows="true" />

	__NOTE__ that adding your `DynamicListView` in a `RelativeLayout` will cause some animations to stop working.

1. Set an animation adapter of your choice using your data source from (1) and `DynamicListView` from (2). There are 3 defined in `MainActivity`
	1. `setUndoSwipeAdapter`: allows you to undo the swipe to delete
	1. `setSwipeDismissAdapter`: allows you to swipe to delete
	1. `setSwingFromRightAdapter`: notifications slide in from the right
	
1. There's the option to set the number of visible notifications at any time. Just use `setNumberOfVisibleNotifications` specifying the `DynamicListView` and the number you want visible.
	1. This method uses these values from the dimens.xml
	
    		notification_height
    		notification_bottom_margin
    		notification_top_margin
    		
1. If you want to edit the layout of...
	1. the notification items, it is in `notification_layout`
	1. the undo row (what appears when you swipe a undo-able notification), it is in `undo_row`
	1. Both layouts share some values
		1. color/notification_card
		1. dimens/notification_height
		1. dimens/notification_bottom_margin
		1. dimens/notification_top_margin
 
   		
### Add-ons (Required)
1. The `NotificationEntry` class has been edited to include more defined values based on the `NotificationType`

	1. `getCardColorResFromType`
	1. `getIconFromType` 
	
1. The `NotificationType` now has a `SIGN_IN` value.
1. Several resources were added to match the mockups, they are required for these notifications to work
	1. drawable/ic_info_outline
	1. drawable/ic_warning
	1. mipmap/ic_device_bulb  