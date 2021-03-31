package com.cs356.mobile.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Data {
    private List<Event> confirmedEvents = new ArrayList<>();
    private List<Event> inProgressEvents = new ArrayList<>();

    /////////////////DEFAULT DATA/////////////////
    private List<String> defaultConfirmedEventInvitees = new ArrayList<String>(Arrays.asList("Ryan Bryson", "Andrew Bowden"));
    private List<String> defaultInProgressEventInvitees = new ArrayList<String>(Arrays.asList("Johnny Test"));
    private Event defaultConfirmedEvent = new Event("Making a Planning App", "Everyday", "Forever", "Android Studio", defaultConfirmedEventInvitees, false);
    private Event defaultInProgressEvent = new Event("Ski at Sundance Resort", "Monday March 8th, 2021", "6:30pm - 9:00pm", "8841", defaultInProgressEventInvitees, true);
    //////////////////////////////////////////////

    public Data() {
        confirmedEvents.clear();
        inProgressEvents.clear();
        confirmedEvents.add(defaultConfirmedEvent);
        inProgressEvents.add(defaultInProgressEvent);
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

    //TODO: Make it possible to confirm an inprogress event and vice versa
}
