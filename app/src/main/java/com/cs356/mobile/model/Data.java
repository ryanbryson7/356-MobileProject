package com.cs356.mobile.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Data {
    private List<Event> confirmedEvents = new ArrayList<>();
    private List<Event> inProgressEvents = new ArrayList<>();
    private Event selectedEvent;
    private static Data instance;

    /////////////////DEFAULT DATA/////////////////
    private List<String> defaultConfirmedEventInvitees = new ArrayList<String>(Arrays.asList("Ryan Bryson", "Andrew Bowden"));
    private List<String> defaultInProgressEventInvitees = new ArrayList<String>(Arrays.asList("Johnny Test"));
    private Event defaultConfirmedEvent = new Event("Making a Planning App", 4, 20, 21, "Forever", "Android Studio", defaultConfirmedEventInvitees, false);
    private Event defaultInProgressEvent = new Event("Ski at Sundance Resort", 3, 31, 2021, "6:30pm - 9:00pm", "Provo, Utah", defaultInProgressEventInvitees, true);
    //////////////////////////////////////////////

    private Data() {
        selectedEvent = new Event();
        confirmedEvents.clear();
        inProgressEvents.clear();
        confirmedEvents.add(defaultConfirmedEvent);
        inProgressEvents.add(defaultInProgressEvent);
    }

    public static Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    public void addInProgressEvent(Event event) {
        inProgressEvents.add(event);
    }

    public void addConfirmedEvent(Event event) {
        confirmedEvents.add(event);
    }

    public List<Event> getConfirmedEvents() {
        return confirmedEvents;
    }

    public List<Event> getInProgressEvents() {
        return inProgressEvents;
    }

    public void confirmEvent(Event eventToConfirm) {
        for (int i = 0; i < inProgressEvents.size(); ++i) {
            if (eventToConfirm.equals(inProgressEvents.get(i))) {
                confirmedEvents.add(eventToConfirm);
                inProgressEvents.remove(i);
                return;
            }
        }

        // Couldn't find event to confirm (This shouldn't ever happen)
        System.out.println("ERROR: Couldn't find inProgress Event to confirm");
        return;
    }

    public void unconfirmEvent(Event eventToUnconfirm) {
        for (int i = 0; i < confirmedEvents.size(); ++i) {
            if (eventToUnconfirm.equals(confirmedEvents.get(i))) {
                inProgressEvents.add(eventToUnconfirm);
                confirmedEvents.remove(i);
                return;
            }
        }

        // Couldn't find event to unconfirm (This shouldn't ever happen)
        System.out.println("ERROR: Couldn't find confirmed Event to unconfirm");
        return;
    }

    public Event findEventByDate(int month, int day, int year) {
        for (Event event : confirmedEvents) {
            if (event.getDay() == day && event.getMonth() == month && event.getYear() == year) {
                return event;
            }
        }
        for (Event event : inProgressEvents) {
            if (event.getDay() == day && event.getMonth() == month && event.getYear() == year) {
                return event;
            }
        }
        return null;
    }

    public Event getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }
}
