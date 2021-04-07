package com.cs356.mobile.model;

public class Notification {
    private String notificationType;
    private String notificationTitle;
    private String notificationBody;
    private Event associatedEvent;

    public Notification(String notificationType, String notificationTitle, String notificationBody, Event associatedEvent) {
        this.notificationType = notificationType;
        this.notificationTitle = notificationTitle;
        this.notificationBody = notificationBody;
        this.associatedEvent = associatedEvent;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationBody() {
        return notificationBody;
    }

    public void setNotificationBody(String notificationBody) {
        this.notificationBody = notificationBody;
    }

    public Event getAssociatedEvent() {
        return associatedEvent;
    }

    public void setAssociatedEvent(Event associatedEvent) {
        this.associatedEvent = associatedEvent;
    }
}
