package fi.academy;

import java.util.ArrayList;

public class Encounter {
    private String eventname;
    private String description;
    private boolean hashappened;
    private String events;

    private ArrayList<String> encounterConditions;

    public Encounter(ArrayList<String> encounterConditions, String eventName, String description, String events) {
        this.eventname = eventName;
        this.description = description;
        this.encounterConditions = encounterConditions;
        this.hashappened = false;
        this.events = events;
    }

    public String getEvents() {
        return events;
    }

    public String getEventname() {
        return eventname;
    }

    public String getDescription() {
        return description;
    }

    public boolean getHasHappened() {
        return hashappened;
    }

    public void setHasHappened(boolean value) {
        hashappened = value;
    }

    public ArrayList<String> getConditions() {
        return encounterConditions;
    }

}
