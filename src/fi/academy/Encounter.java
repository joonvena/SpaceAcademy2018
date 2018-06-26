package fi.academy;

import java.util.ArrayList;

public class Encounter {
    private String eventname;
    private String description;
    private boolean hashappened;
    private ArrayList<String> conditions;

    public Encounter(String eventname, String description, ArrayList<String> conditions) {
        this.eventname = eventname;
        this.description = description;
        this.conditions = conditions;
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
}


