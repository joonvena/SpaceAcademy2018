package fi.academy;

import java.util.ArrayList;

public class Area {
    private String areaName;
    private String description;
    private ArrayList<Integer> borderingAreas;

    public Area(String areaName, String description, ArrayList<Integer> borderingAreas) {
        this.areaName = areaName;
        this.description = description;
        this.borderingAreas = borderingAreas;
    }

    public String getAreaName() {
        return areaName;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Integer> getBorderingAreas() {
        return borderingAreas;
    }

}
