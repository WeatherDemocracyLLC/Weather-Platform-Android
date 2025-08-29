package com.webmobrilweatherapp.model;

public class NotificationModel {

    private int notification_image;
    private  String notification_txt;

    public NotificationModel(int notification_image, String notification_txt)
    {
        this.notification_image = notification_image;
        this.notification_txt = notification_txt;
    }

    public int getNotification_image()
    {
        return notification_image;
    }
    public void setNotification_image(int notification_image)
    {
        this.notification_image = notification_image;
    }

    public String getNotification_txt() {
        return notification_txt;
    }

    public void setNotification_txt(String notification_txt) {
        this.notification_txt = notification_txt;
    }
}