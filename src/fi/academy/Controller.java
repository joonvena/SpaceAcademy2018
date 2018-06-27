package fi.academy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
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
        String input = commandInput.getText();
        commandParser(input);
        Area thisArea = areaList.get(currentArea);
        conditions.put("GruisAlive", true);
        conditions.put("GruisAngry", true);
        for(int i = 0; i < (thisArea.getEncountersinRoom().size()); i++) {
            if(Game.encounterCheck(thisArea.getEncountersinRoom().get(i))) {
                Text text5 = new Text(Game.encounterHappens(thisArea.getEncountersinRoom().get(i)));
                output.getChildren().addAll(new Text(input+"\n\n"), text5);
            };
        }
        String borderAreas = "";
        String allItems = "";
        for (int i = 0; i < thisArea.getBorderingAreas().size(); i++) {
            borderAreas += "  " + fetchAreaName(thisArea.getBorderingAreas().get(i));
        }

        for (int i = 0; i < thisArea.getItemList().size(); i++) {
            allItems += "  " + thisArea.getItemList().get(i);
        }

        Text text1 = new Text("\nYou are in "+thisArea.getAreaName()+"\n");
        Text text2 = new Text(thisArea.getDescription()+"\n");
        Text text3 = new Text("Adjacent areas: "+borderAreas+"\n");
        Text text4 = new Text("Items in room: "+allItems+"\n");
        System.out.println(encounters);
        output.getChildren().addAll(new Text(input+"\n\n"), text1, text2, text3, text4);

        System.out.println(commandInput.getText());
        commandInput.clear();
        updateGUI();
    }

    public void updateGUI() {
        // Update all GUI elements according to current game state!
        inventory.getChildren().clear();
        for (String item : Game.inventory) {
            inventory.getChildren().addAll(new Text(item+"\n"));
        }
    }
}