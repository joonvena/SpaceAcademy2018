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
        Scanner reader = new Scanner(System.in);
        ArrayList<Integer> borderings = new ArrayList<>();
        borderings.add(0);
        borderings.add(1);
        Area startingPlace = new Area("Cryo chamber", "Place where you start", borderings);
        Area startingPlace2 = new Area("Locker room", "Place where you put clothes on", borderings);
        areaList.add(startingPlace);
        areaList.add(startingPlace2);


        //game loop
        while(true) {
            System.out.println("You are in" + areaList.get(currentArea));
            String input = reader.nextLine();
            commandParser(input);
        }

    }


    public void commandParser(String input) {
//        input = input.toLowerCase();
        String [] command = input.split(" ", 2);
        switch(command[0]) {
            case "goto": {
                int nextArea = fetchArea(command[1]);
                if (nextArea != -1) {
                    currentArea = nextArea;
                } else {
                    System.out.println("Can't go there!");
                }
                break;
            }
        }
    }

    public int fetchArea(String input) {
        for (int i = 0; i < areaList.size(); i++) {
            if (input.equalsIgnoreCase(areaList.get(i).getAreaName())) {
                return i;
            }
        }
        return -1;
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
                    areaList.add(new Area(areaName, description, borderingAreas));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Error: " + e.getMessage());
            }
        }
    }




