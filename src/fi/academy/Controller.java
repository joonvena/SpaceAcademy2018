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
    private ScrollPane Scroll;

    @FXML
    protected void parseCommand(ActionEvent event) {
        output.heightProperty().addListener(observable -> Scroll.setVvalue(1D));
        Scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        /*Scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);*/
        output.getChildren();

        // Get command from text box, parse it
        String input = commandInput.getText();
        commandParser(input);
        output.getChildren().addAll((new Text("> "+input+"\n\n")));
        if (errorMessage == true) {
            Text text5 = new Text(Game.encounterHappens("fumble"));
            text5.setStyle("-fx-font-style: italic");
            output.getChildren().addAll(text5, new Text ("\n\n"));
        }

        // If G.R.U.E. has been awakened, decrease the timer
        if (flaglist.contains("shitHitTheFan")) {
            hurt--;
            System.out.println(hurt);
            if (hurt<=0) {
                hurt = 0;
                System.out.println("The monster appears!");
            }
            // decrease timer THEN if 0, monster appears!
        }

        // If previous command triggered an item event, activate the event
        if (!lastItemUsed.equals(" ")) {
            Text t = new Text(Game.encounterHappens(lastItemUsed)+"\n\n");
            t.setStyle("-fx-font-style: italic");
            output.getChildren().addAll(t);
            lastItemUsed = " ";
        }

        // If current area has encounters and their conditions are true, activate the encounters
        Area thisArea = areaList.get(currentArea);
        if(!thisArea.getEncountersinRoom().contains(".")) {
            for (int i = 0; i < (thisArea.getEncountersinRoom().size()); i++) {
                if (Game.encounterCheck(thisArea.getEncountersinRoom().get(i))) {
                    Text text5 = new Text(Game.encounterHappens(thisArea.getEncountersinRoom().get(i)));
                    text5.setStyle("-fx-font-style: italic");
                    output.getChildren().addAll(text5, new Text ("\n\n"));
                }
            }
        }

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
        Text text5 = new Text(thisArea.getAreaName()+"\n");
        Text text2 = new Text(thisArea.getDescription()+"\n");
        Text text3 = new Text("Adjacent areas: "+borderAreas+"\n");
        Text text4 = new Text("Items in room: "+allItems+"\n");

        text1.setFill(Color.WHITE);
        text1.setStyle("-fx-font-weight: bold; -fx-font-style: italic;");
        text5.setFill(Color.RED);
        text5.setStyle("-fx-font-weight: bold;");
        text2.setFill(Color.WHITE);
        text3.setFill(Color.WHITE);
        text4.setFill(Color.WHITE);
        text4.setStyle("-fx-font-weight: bold");


        output.getChildren().addAll(text1, text5, text2, text3, text4, new Text("\n\n"));
        errorMessage = false;
        commandInput.clear();
        updateGUI();
    }

    public void updateGUI() {
        // Update all GUI elements according to current game state!
        inventory.getChildren().clear();
        System.out.println("This is test");
        System.out.println(Game.inventory);
        for (String item : Game.inventory) {
            inventory.getChildren().addAll(new Text(item+"\n"));
        }
        // This is ONLY for debugging purposes! Not intended for players to see!
        for (String flag : Game.flaglist) {
            inventory.getChildren().addAll(new Text("Flag: "+flag+"\n"));
        }
    }
}