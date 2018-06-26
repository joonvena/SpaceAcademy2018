package fi.academy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Game {
    static int currentArea = 0;
    static List<Area> areaList = new ArrayList<>();

    public void start() {
        fileReader();
    }

    public static void commandParser(String input) {
        String[] command = input.split(" ", 2);
        switch (command[0]) {
            case "goto": {
                int nextArea = fetchAreaID(command[1]);
                if (nextArea != -1) {
                    currentArea = nextArea;
                } else {
                    System.out.println("Can't go there!");
                }
                break;
            }
        }
    }

    public static int fetchAreaID(String input) {
        for (int i = 0; i < areaList.size(); i++) {
            if (input.equalsIgnoreCase(areaList.get(i).getAreaName())) {
                return i;
            }
        }
        return -1;
    }

    private void eventCheck(int currentArea) {
        
    }


    public static String fetchAreaName(int index) {
        return areaList.get(index).getAreaName();
    }

    public void fileReader() {
        //ArrayList<Area> addedAreas = new ArrayList<>();
        try (Scanner areaReader = new Scanner(new File("./assets/areas.dat"))) {
            while (areaReader.hasNextLine()) {
                String areaName = areaReader.nextLine();
                String description = areaReader.nextLine();
                String indexList = areaReader.nextLine();
                ArrayList<Integer> borderingAreas = new ArrayList<>();
                String[] borderAreas = indexList.split(" ");
                for (int i = 0; i < borderAreas.length; i++) {
                    borderingAreas.add(Integer.parseInt(borderAreas[i]));
                }
                String items = areaReader.nextLine();
                ArrayList<String> itemsinRoom = new ArrayList<>();
                String[] possibleItems = items.split(",");
                for (int i = 0; i < possibleItems.length; i++) {
                    itemsinRoom.add(possibleItems[i]);
                }
                ArrayList<String> encountersintheRoom = new ArrayList<>();
                String possibleEncounters = areaReader.nextLine();
                String[] encounters = possibleEncounters.split(",");
                for (int i = 0; i < encounters.length; i++) {
                    encountersintheRoom.add(encounters[i]);
                }
                areaList.add(new Area(areaName, description, borderingAreas, itemsinRoom, encountersintheRoom));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
    }
}





/* possible Encounter-list for future use
    public ArrayList<Encounter> encounterReader() {
        try (Scanner encounterReader = new Scanner(new File("./assets/encounters.dat"))) {
            while (encounterReader.hasNextLine()) {
                ArrayList<Encounter> encountersInRoom = new ArrayList<>();
                String encounterName = encounterReader.nextLine();
                boolean hashappened = false;
                String encounterList = encounterReader.nextLine();
                ArrayList<String> whatwillhappen = new ArrayList<>();
                String[] encounters = encounterList.split(" ");
                for (int i = 0; i < encounters.length; i++) {
                    whatwillhappen.add(encounters[i]);
                }
                String items = encounterReader.nextLine();
                ArrayList<String> itemsinRoom = new ArrayList<>();
                String[] possibleItems = items.split(",");
                for (int i = 0; i < possibleItems.length; i++) {
                    itemsinRoom.add(possibleItems[i]);
                }
                encountersInRoom.add(new Encounter(encounterName, hashappened, whatwillhappen));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
        ArrayList<Encounter> encounters = new ArrayList<>();
        return encounters;

    }
}
*/



