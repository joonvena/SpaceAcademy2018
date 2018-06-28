package fi.academy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.image.Image;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static fi.academy.Game.*;

public class Controller {
	
	// Custom colors for elements
    Color locationColor = Color.web("#81CFE0");
    Color itemColor = Color.web("#59ABE3");
    Color encounterColor = Color.web("#3498DB");
	
    @FXML
    private TextField commandInput;
    @FXML
    private Button action;
    @FXML
    private TextFlow output;
    @FXML
    private Canvas canvas;
    @FXML
    private TextFlow inventory;
    @FXML
    private ScrollPane Scroll;
	@FXML
    private ScrollPane inventoryScroll;
    @FXML
    private javafx.scene.image.ImageView ImageResolve;
    @FXML
    private javafx.scene.image.ImageView eventPicture;

    @FXML
    protected void parseCommand(ActionEvent event) {
        output.heightProperty().addListener(observable -> Scroll.setVvalue(1D));
        Scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		inventoryScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		
        // Get command from text box, parse it
        String input = commandInput.getText();
        commandParser(input);
        Text iText = new Text("> "+input+"\n\n");
        iText.setFill(Color.WHITE);
        output.getChildren().addAll(iText);
        if (errorMessage == true) {
            errorMessage = false;
            Text text = new Text(Game.encounterHappens("fumble"));
            text.setStyle("-fx-font-style: italic");
            output.getChildren().addAll(text, new Text ("\n\n"));
        }
        Area thisArea = areaList.get(currentArea);
        itemEvent();
        monsterEvent();
        areaEvent(thisArea);
        displayArea(thisArea);
        commandInput.clear();
        updateGUI();
    }
    public void itemEvent () {
        // If previous command triggered an item event, activate the event
        if (!lastItemUsed.equals(" ")) {
            Text text = null;
            if (Game.encounterCheck(lastItemUsed)) {
                text = new Text(Game.encounterHappens(lastItemUsed));
            } else {
                text = new Text("Nothing happens. (Either something is missing or this is not the way the item is meant to be used.)\n\n"); // Not sure if this works?!?
            }

            String reaction = "";
            if (lastHurt==1) reaction = " The monster flinched but doesn't go away.";
            if (lastHurt>1 && lastHurt <5) reaction = " You hurt the monster, and it escaped to the ventilation system, at least for a while.";
            if (lastHurt>=5) reaction = " You dealt some serious damage to the monster! It scurries off through the ventilation system.";

            text.setStyle("-fx-font-style: italic");
            text.setFill(Color.WHITE);
            output.getChildren().addAll(text, new Text(reaction),new Text("\n\n"));
            lastItemUsed = " ";
            lastHurt = 0;
        }
    }

    public void monsterEvent () {
        // If G.R.U.E. has been awakened, decrease the timer
        if (flaglist.contains("shitHitTheFan")) {
            hurt--;
            System.out.println(hurt);
            Text text = null;
            switch (hurt) {
                case -1: text = new Text (encounterHappens("monsterKills")+"\n\n");
                    break;
                case 0: text = new Text (encounterHappens("monsterAppears")+"\n\n");
                    break;
                case 1: text = new Text (encounterHappens("monsterOverhead")+"\n\n");
                    break;
                case 2: text = new Text (encounterHappens("monsterNear")+"\n\n");
                    break;
                case 3: text = new Text (encounterHappens("monsterFar")+"\n\n");
                    break;
            }
            if (hurt <= 3 && hurt > -2) {
                text.setStyle("-fx-font-style: italic");
                text.setFill(Color.WHITE);
                output.getChildren().addAll(text);
            }
        }
    }

    public void areaEvent (Area thisArea) {
        // If current area has encounters and their conditions are true, activate the encounters
        if(!thisArea.getEncountersinRoom().contains(".")) {
            for (int i = 0; i < (thisArea.getEncountersinRoom().size()); i++) {
                if (Game.encounterCheck(thisArea.getEncountersinRoom().get(i))) {
                    Text text = new Text(Game.encounterHappens(thisArea.getEncountersinRoom().get(i)));
                    text.setStyle("-fx-font-style: italic");
                    text.setFill(Color.WHITE);
                    output.getChildren().addAll(text, new Text ("\n\n"));
                }
            }
        }
    }

    public void displayArea(Area thisArea) {
        String borderAreas = "";
        String allItems = "";
        for (int i = 0; i < thisArea.getBorderingAreas().size(); i++) {
            borderAreas += fetchAreaName(thisArea.getBorderingAreas().get(i));
            if (i < thisArea.getBorderingAreas().size()-1) borderAreas += ", ";
        }

        for (int i = 0; i < thisArea.getItemList().size(); i++) {
            allItems += thisArea.getItemList().get(i);
            if (i < thisArea.getItemList().size()-1) allItems += ", ";
        }

        Text text1 = new Text("\nYou are in: ");
        Text text5 = new Text(thisArea.getAreaName() + "\n\n");
        Text text2 = new Text(thisArea.getDescription() + "\n\n");
        Text text3 = new Text("Adjacent areas: ");
        Text areaStyling = new Text(borderAreas + "\n\n");
        Text text4 = new Text("Items in room: ");
        Text itemsStyling = new Text(allItems + "\n");


        // Updating the location on map
        if (thisArea.getAreaName().equals("Cryogenics")) {
            ImageResolve.setImage(new Image("/fi/academy/mapimages/map_startingpoint.jpg"));
        } else if (thisArea.getAreaName().equals("Hydroponics")) {
            ImageResolve.setImage(new Image("/fi/academy/mapimages/map_hydro.jpg"));
        } else if (thisArea.getAreaName().equals("Bio Lab")) {
            ImageResolve.setImage(new Image("/fi/academy/mapimages/map_biolab.jpg"));
        } else if (thisArea.getAreaName().equals("Med Bay")) {
            ImageResolve.setImage(new Image("/fi/academy/mapimages/map_medical.jpg"));
        } else if (thisArea.getAreaName().equals("Access Hub")) {
            ImageResolve.setImage(new Image("/fi/academy/mapimages/map_access.jpg"));
        } else if (thisArea.getAreaName().equals("Rec Room")) {
            ImageResolve.setImage(new Image("/fi/academy/mapimages/map_rec.jpg"));
        } else if (thisArea.getAreaName().equals("Restrooms")) {
            ImageResolve.setImage(new Image("/fi/academy/mapimages/map_restroom.jpg"));
        } else if (thisArea.getAreaName().equals("Gym")) {
            ImageResolve.setImage(new Image("/fi/academy/mapimages/map_gym.jpg"));
        } else if (thisArea.getAreaName().equals("Crew Quarters")) {
            ImageResolve.setImage(new Image("/fi/academy/mapimages/map_crew.jpg"));
        } else if (thisArea.getAreaName().equals("Robotics")) {
            ImageResolve.setImage(new Image("/fi/academy/mapimages/map_robotic.jpg"));
        } else if (thisArea.getAreaName().equals("Server Room")) {
            ImageResolve.setImage(new Image("/fi/academy/mapimages/map_server.jpg"));
        } else if (thisArea.getAreaName().equals("Reactor")) {
            ImageResolve.setImage(new Image("/fi/academy/mapimages/map_reactor.jpg"));
        } else if (thisArea.getAreaName().equals("Engine Room")) {
            ImageResolve.setImage(new Image("/fi/academy/mapimages/map_engine.jpg"));
        } else if (thisArea.getAreaName().equals("Comms")) {
            ImageResolve.setImage(new Image("/fi/academy/mapimages/map_comms.jpg"));
        } else if (thisArea.getAreaName().equals("Astrogation")) {
            ImageResolve.setImage(new Image("/fi/academy/mapimages/map_astro.jpg"));
        } else if (thisArea.getAreaName().equals("Storage")) {
            ImageResolve.setImage(new Image("/fi/academy/mapimages/map_storage.jpg"));
        } else if (thisArea.getAreaName().equals("Airlock")) {
            ImageResolve.setImage(new Image("/fi/academy/mapimages/map_airlock.jpg"));
        } else if (thisArea.getAreaName().equals("Escape Pod")) {
            ImageResolve.setImage(new Image("/fi/academy/mapimages/map_pod.jpg"));
        }


        text1.setFill(Color.WHITE);
        text1.setStyle("-fx-font-weight: bold; -fx-font-style: italic;");
        text5.setFill(locationColor);
        text5.setStyle("-fx-font-weight: bold;");
        text2.setFill(Color.WHITE);
        text2.setStyle("-fx-font-style: italic; -fx-font-size: 16px;");
        text3.setFill(Color.WHITE);
        text3.setStyle("-fx-font-weight: bold;");
        areaStyling.setFill(Color.WHITE);
        text4.setFill(Color.WHITE);
        text4.setStyle("-fx-font-weight: bold");
        itemsStyling.setFill(itemColor);

        //output.getChildren().addAll(text1, text2, text3, text4, new Text("\n\n"));
        output.getChildren().addAll(text1, text5, text2, text3, areaStyling, text4, itemsStyling, new Text("\n\n"));
    }

    public void updateGUI() {
        // Update all GUI elements according to current game state!
        inventory.getChildren().clear();
        System.out.println("This is test");
        System.out.println(Game.inventory);
		inventory.heightProperty().addListener(observable -> inventoryScroll.setVvalue(1D));
        for (String item : Game.inventory) {
            inventory.getChildren().addAll(new Text(item+"\n"));
        }
        // This is ONLY for debugging purposes! Not intended for players to see!
        for (String flag : Game.flaglist) {
            inventory.getChildren().addAll(new Text("Flag: "+flag+"\n"));
        }
    }
}