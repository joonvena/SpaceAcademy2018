package fi.academy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static fi.academy.Game.*;

public class Controller {
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
    protected void parseCommand(ActionEvent event) {
        output.getChildren().clear();

        // Get command from text box, parse it
        String input = commandInput.getText();
        commandParser(input);
        output.getChildren().addAll((new Text("> "+input+"\n\n")));
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
            Text t = new Text(Game.encounterHappens(lastItemUsed)+"\n\n");
            t.setStyle("-fx-font-style: italic");
            output.getChildren().addAll(t);
            lastItemUsed = " ";
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
            text.setStyle("-fx-font-style: italic");
            output.getChildren().addAll(text);
        }
    }

    public void areaEvent (Area thisArea) {
        // If current area has encounters and their conditions are true, activate the encounters
        if(!thisArea.getEncountersinRoom().contains(".")) {
            for (int i = 0; i < (thisArea.getEncountersinRoom().size()); i++) {
                if (Game.encounterCheck(thisArea.getEncountersinRoom().get(i))) {
                    Text text5 = new Text(Game.encounterHappens(thisArea.getEncountersinRoom().get(i)));
                    text5.setStyle("-fx-font-style: italic");
                    output.getChildren().addAll(text5, new Text ("\n\n"));
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

        Text text1 = new Text("You are in "+thisArea.getAreaName()+".\n\n");
        text1.setStyle("-fx-font-weight: bold");
        Text text2 = new Text(thisArea.getDescription()+"\n\n");
        Text text3 = new Text("Adjacent areas: "+borderAreas+"\n");
        Text text4 = new Text("Items in room: "+allItems+"\n");
        output.getChildren().addAll(text1, text2, text3, text4, new Text("\n\n"));
    }

    public void updateGUI() {
        // Update all GUI elements according to current game state!
        inventory.getChildren().clear();
        for (String item : Game.inventory) {
            inventory.getChildren().addAll(new Text(item+"\n"));
        }
        // This is ONLY for debugging purposes! Not intended for players to see!
        for (String flag : Game.flaglist) {
            inventory.getChildren().addAll(new Text("Flag: "+flag+"\n"));
        }
    }
}