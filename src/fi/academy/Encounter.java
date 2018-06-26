package fi.academy;

import java.util.List;

public class Encounter {
    private String eventname;
    private boolean hashappened;
    private List<String> whathappens;


    public Encounter(String eventname, boolean hashappened, List<String> whathappens) {
        this.eventname = eventname;
        this.hashappened = hashappened;
        this.whathappens = whathappens;
    }

    public String getEventname() {
        return eventname;
    }

    public boolean Hashappened() {
        return hashappened;
    }

    public List<String> getWhathappens() {
        return whathappens;
    }
}
