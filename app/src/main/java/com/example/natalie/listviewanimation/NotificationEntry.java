package com.example.natalie.listviewanimation;

import android.content.Intent;

import static com.example.natalie.listviewanimation.NotificationType.GENERAL;

public class NotificationEntry {

    private NotificationType type;
    private String title;
    private String body;
    private String action;
    private long timeStamp;
    private boolean isRead;
    private Intent intent;

    public NotificationEntry(NotificationType type, String title, String body, String action, Intent intent) {
        this.type = type;
        this.title = title;
        this.body = body;
        this.action = action;
        this.timeStamp = System.currentTimeMillis();
        this.isRead = false;
        this.intent = intent;
    }

    public NotificationType getType() {
        return type;
    }

    public int getIconFromType() {
        int returnRes;
        switch (type) {
            case SIGN_IN:
                returnRes = R.drawable.ic_warning_24dp;
                break;
            case GENERAL:
                returnRes = R.drawable.ic_info_outline_24dp;
                break;
            default:
                returnRes= R.drawable.ic_info_outline_24dp;
                break;
        }
        return returnRes;
    }

    public int getCardColorResFromType() {
        int returnRes;
        switch (type) {
            case SIGN_IN:
                returnRes = R.color.warning_notification_card;
                break;
            case GENERAL:
                returnRes = R.color.notification_card;
                break;
            default:
                returnRes = R.color.notification_card;
                break;
        }
        return returnRes;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getAction() { return action; }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setRead() {
        isRead = true;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public Intent getIntent() {
        return intent;
    }

    @Override
    public String toString() {
        return "NotificationEntry{" +
                "type='" + getType() + "'" +
                ", title='" + getTitle() + '\'' +
                ", body='" + getBody() + '\'' +
                ", action='" + getAction() + '\'' +
                ", timeStamp=" + getTimeStamp()+
                ", isRead=" + getIsRead() +
                '}';
    }
}
