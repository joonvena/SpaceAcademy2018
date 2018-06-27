package fi.academy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Game {
    static int currentArea = 0;
    static int points = 0;
    static int hurt = 0; // Indicates how many rounds the monster waits until its next attack!
    static String lastItemUsed = " ";
    static List<Area> areaList = new ArrayList<>();
//    static HashMap<String, Boolean> conditions = new HashMap<>();
    static HashMap<String, Encounter> encounters = new HashMap<>();
    static List<String> inventory = new ArrayList<>();
    static List<String> flaglist = new ArrayList<>();
    static boolean errorMessage = false;

    public void start() {
        fileReader();
        encounterReader();
    }

    public static void commandParser(String input) {
        try {
            String[] command = input.split(" ", 2);
            switch (command[0]) {
                case "goto": {
                    int nextArea = fetchAreaID(command[1]);
                    if (nextArea != -1 && areaList.get(currentArea).getBorderingAreas().contains(nextArea)) {
                        currentArea = nextArea;
                    } else {
                        System.out.println("Can't go there!");
                    }
                    break;
                }
                case "take": {
                    Encounter encounter = encounters.get(command[1]);
                    if(!encounter.getEvents().contains("heavy")) {
                        getItem(command[1]);
                    } else {
                        lastItemUsed = "tooHeavy";
                    }
                    break;
                }
                case "drop": {
                    loseItem(command[1]);
                    break;
                }
                case "use": {
                    String computerName = command[1].substring(0, (command[1].indexOf("computer") + 8));
                    if (command[1].contains("computer") && areaList.get(currentArea).getItemList().contains(computerName)) {
                        if (command[1].length() > computerName.length()) {
                            String answer = command[1].substring(command[1].indexOf("computer") + 9);
                            System.out.println(answer);
                            Encounter encounter = encounters.get(computerName);
                            String[] events = encounter.getEvents().split(",");
                            for (int i = 0; i < events.length; i++) {
                                if (events[i].equals("answer " + answer)) {
                                    lastItemUsed = computerName + "OK";
                                } else {
                                    lastItemUsed = "accessDenied";
                                }
                            }
                        } else {
                            lastItemUsed = computerName;
                        }
                    }
                    if (inventory.contains(command[1]) && (encounters.get(command[1]) != null)) {
                        lastItemUsed = command[1];
                        System.out.println("Something happens");
                    } else {
                        System.out.println("You don't have such an item!");
                    }
                    break;
                }
            }
        }
        catch (Exception e) {
            errorMessage = true;
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
        Encounter encounter = encounters.get(encounterName);
        if (!encounterName.equals(".")) {
            for (int i = 0; i < encounter.getConditions().size(); i++) {
                String checkCond = encounter.getConditions().get(i);
                System.out.println(checkCond);
                if(checkCond.equals(".") && (encounter.getHasHappened() == false)) {
                    return true;
                }
                if ((flaglist.contains(checkCond)) && (encounter.getHasHappened() == false)) {
                    return true;
                }
            }
        }
                return false;
            }

    static String encounterHappens(String encounterName) {
        Encounter encounter = encounters.get(encounterName);
        String[] commands = encounter.getEvents().split(",");
        for (String command: commands) {
            String[] comm = command.split(" ",2);
            switch (comm[0]) {
                case "goto": {
                    int nextArea = fetchAreaID(comm[1]);
                    if (nextArea != -1) {
                        currentArea = nextArea;
                    }
                    break;
                }
                case "get": {
                    inventory.add(comm[1]);
                    break;
                }
                case "lose": {
                    inventory.remove(comm[1]);
                    break;
                }
                case "disable": {
                    areaList.get(currentArea).getItemList().remove(comm[1]);
                    break;
                }
                case "win": {
                    // Win screen
                    break;
                }
                case "end": {
                    // Lose screen
                    System.out.println("You lose! You had " + points+".");
                }
                case "gain": {
                    points+=Integer.parseInt(comm[1]);
                }
                case "set": {
                    flaglist.add(comm[1]);
                    break;
                }
                case "clear": {
                    flaglist.remove(comm[1]);
                    break;
                }
                case "call": {
                    encounterHappens(comm[1]);
                    break;
                }

                case "damage": {
                    hurt += Integer.parseInt(comm[1]);
                }

                case "answer": {
                    break;
                }
            }
        }
        System.out.println("auto");
        encounter.setHasHappened(true);
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
                String events = encounterReader.nextLine();
                encounters.put(encounterName, new Encounter(conditions, encounterName, encounterDescription, events));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }


    }
}











