package fi.academy;

import java.util.ArrayList;

public class Area {
    private String areaName;
    private String description;
    private ArrayList<Integer> borderingAreas;
    private ArrayList<String> itemList;
    private ArrayList<String> encountersinRoom;

    public Area(String areaName, String description, ArrayList<Integer> borderingAreas, ArrayList<String> itemList, ArrayList<String> encountersinRoom) {
        this.areaName = areaName;
        this.description = description;
        this.borderingAreas = borderingAreas;
        this.itemList = itemList;
        this.encountersinRoom = encountersinRoom;
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

    @Override
    public String toString() {
        String borderAreas = "";
        for (int i = 0; i < borderingAreas.size(); i++) {
            borderAreas += "  " + Game.fetchAreaName(i);
        }

        return " Areaname: " + areaName +
                ", description='" + description + '\'' +
                ", borderingAreas=" + borderAreas +
                '}' + "items in the room= " + itemList + "Events in the room" + encountersinRoom;
    }

    public ArrayList<String> getItemList() {
        return itemList;
    }
}
