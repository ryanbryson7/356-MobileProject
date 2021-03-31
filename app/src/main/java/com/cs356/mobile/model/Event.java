package com.cs356.mobile.model;

import java.util.List;

public class Event {
    private String title;
    private String date;
    private String time;
    private String location;
    private List<String> invitees;
    private boolean isInProgress;

    public Event() {
        title = "DEFAULT TITLE";
        date = "DEFAULT DATE";
        time = "DEFAULT TIME";
        location = "DEFAULT LOCATION";
        isInProgress = true;
    }

    public Event(String title, String date, String time, String location, List<String> invitees, boolean isInProgress) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.location = location;
        this.invitees = invitees;
        this.isInProgress = isInProgress;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getInvitees() {
        return invitees;
    }
    public void addInvitee(String newInvitee) {
        invitees.add(newInvitee);
    }
    public void setInvitees(List<String> invitees) {
        this.invitees = invitees;
    }

    public boolean isInProgress() {
        return isInProgress;
    }
    public void setInProgress(boolean inProgress) {
        isInProgress = inProgress;
    }

    // I'm thinking this will be useful/necessary for displaying
    // the event in the ExpandableView on the home page
    @Override
    public String toString() {
        return title + "\n"
                + "\t- Date: " + date + "\n"
                + "\t- Time: " + time + "\n";
    }

    // Useful for confirming/uncofirming events
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof Event) {
            Event compareEvent = (Event) o;
            return (this.title.equals(compareEvent.getTitle()))  &&
                    (this.date.equals(compareEvent.getDate())) &&
                    (this.time.equals(compareEvent.getTime())) &&
                    (this.location.equals(compareEvent.getLocation())) &&
                    (this.invitees == compareEvent.getInvitees());
        }
        else {
            return false;
        }
    }
}
