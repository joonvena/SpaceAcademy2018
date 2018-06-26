package fi.academy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Game {
    static int currentArea = 0;
    static List<Area> areaList = new ArrayList<>();
    static HashMap<String, Boolean> conditions = new HashMap<>();
    static HashMap<String, Encounter> encounters = new HashMap<>();
    static List<String> inventory = new ArrayList<>(); 

    public void start() {
        fileReader();
        encounterReader();
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
            case "take": {
                getItem(command[1]);
                break;
            }
            case "drop": {
                loseItem(command[1]);
                break;
            }
        }
    }

    public static void getItem (String input) {
        List itemsInRoom = areaList.get(currentArea).getItemList();
        System.out.println(itemsInRoom);
        if (itemsInRoom.contains(input)) {
            System.out.println("Is here!");
            itemsInRoom.remove(input);
            inventory.add(input);
        } else {
            System.out.println(input+" is not in this area!");
        }
    }

    public static void loseItem (String input) {
        List itemsInRoom = areaList.get(currentArea).getItemList();
        System.out.println(itemsInRoom);
        if (inventory.contains(input)) {
            System.out.println("You have!");
            inventory.remove(input);
            itemsInRoom.add(input);
        } else {
            System.out.println(input+" is not in your pocket!");
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

    static boolean encounterCheck(String encounterName) {
        System.out.println(encounterName);
        Encounter encounter = encounters.get(encounterName);
        for (int i = 0; i < encounter.getConditions().size(); i++) {
            String checkCond = encounter.getConditions().get(i);
            System.out.println(checkCond);
            if (conditions.get(checkCond) && (encounter.gethashappened() == false)) {
                        return true;
                    }
                }
                return false;
            }

    static String encounterHappens(String encounterName) {
        Encounter encounter = encounters.get(encounterName);
        return encounter.getDescription();

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


    public void encounterReader() {
        try (Scanner encounterReader = new Scanner(new File("./assets/encounters.dat"))) {
            while (encounterReader.hasNextLine()) {
                ArrayList<Encounter> encountersInRoom = new ArrayList<>();
                String encounterName = encounterReader.nextLine();
                String encounterDescription = encounterReader.nextLine();
                ArrayList<String> conditions = new ArrayList<>();
                String conditionList = encounterReader.nextLine();
                String[] condition = conditionList.split(",");
                for (int i = 0; i < condition.length; i++) {
                    conditions.add(condition[i]);
                }
                encounters.put(encounterName, new Encounter(conditions, encounterName, encounterDescription));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }


    }
}











