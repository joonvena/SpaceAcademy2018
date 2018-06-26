package fi.academy;

import java.util.ArrayList;

public class Encounter {
    private String eventname;
    private String description;
    private boolean hashappened;
    private ArrayList<String> encounterConditions;

    public Encounter(String eventname, String description, ArrayList<String> encounterConditions) {
        this.eventname = eventname;
        this.description = description;
        this.encounterConditions = encounterConditions;
        this.hashappened = false;
    }


    public String getEventname() {
        return eventname;
    }

    public String getDescription() {
    return description;
    }

    public boolean gethashappened() {
        return hashappened;
    }

    public void setHashappened() {
        hashappened = false;
    }

    public ArrayList<String> getConditions() {
        return encounterConditions;
    }
}


