package com.cs356.mobile.model;

import com.applandeo.materialcalendarview.EventDay;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Event {
    private String title;
    private int day;
    private int month;
    private int year;
    private String time;
    private String location;
    private List<String> invitees;
    private boolean isInProgress;
    private EventDay eventDay;

    public Event() {
        title = "DEFAULT TITLE";
        day = 1;
        month = 1;
        year = 2021;
        time = "DEFAULT TIME";
        location = "DEFAULT LOCATION";
        isInProgress = true;
    }

    public Event(String title, int month, int day, int year, String time, String location, List<String> invitees, boolean isInProgress) {
        this.title = title;
        this.month = month;
        this.day = day;
        this.year = year;
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

    public int getDay() {
        return day;
    }
    public void setDay(int day) {
        this.day = day;
    }
    public String getDateAsString() {
        return "" + month + "/" + day + "/" + year;
    }

    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
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

    // Alternatively, we could just hardcode friendList to come from the Data instance here
    public List<String> getUninvitedFriends(List<String> friendList) {
        List<String> uninvitedFriends = new ArrayList<String>();

        for (String friend : friendList) {
            if (!invitees.contains(friend)) {
                uninvitedFriends.add(friend);
            }
        }

        return uninvitedFriends;
    }

    public void inviteFriend(String person) {
        invitees.add(person);
    }

    // I'm thinking this will be useful/necessary for displaying
    // the event in the ExpandableView on the home page
    @Override
    public String toString() {
        return title + "\n"
                + "\t- Date: " + month +"/" + day + "/" + year + "\n"
                + "\t- Time: " + time + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        Event event = (Event) o;
        return getDay() == event.getDay() &&
                getMonth() == event.getMonth() &&
                getYear() == event.getYear() &&
                isInProgress() == event.isInProgress() &&
                Objects.equals(getTitle(), event.getTitle()) &&
                Objects.equals(getTime(), event.getTime()) &&
                Objects.equals(getLocation(), event.getLocation()) &&
                Objects.equals(getInvitees(), event.getInvitees());
    }

    public void setEventDay(EventDay eventDay) {
        this.eventDay = eventDay;
    }

    public EventDay getEventDay() {
        return eventDay;
    }

}
