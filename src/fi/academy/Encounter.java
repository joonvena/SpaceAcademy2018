package fi.academy;

import java.util.ArrayList;

public class Encounter {
    private String eventname;
    private String description;
    private boolean hashappened;


    private ArrayList<String> encounterConditions;

    public Encounter(ArrayList<String> encounterConditions, String eventname, String description) {
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

    public void setHashappened(boolean value) {
        hashappened = value;
    }

    public ArrayList<String> getConditions() {
        return encounterConditions;
    }

}
