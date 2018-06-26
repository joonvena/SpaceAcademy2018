package fi.academy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

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
        String input = commandInput.getText();
        commandParser(input);
        System.out.println(currentArea);
        Area thisArea = areaList.get(currentArea);
        String borderAreas = "";
        String allItems = "";

        for (int i = 0; i < thisArea.getBorderingAreas().size(); i++) {
            borderAreas += "  " + fetchAreaName(i);
        }

        for (int i = 0; i < thisArea.getItemList().size(); i++) {
            allItems += "  " + thisArea.getItemList().get(i);
        }

        Text text1 = new Text("\nYou are in "+thisArea.getAreaName()+"\n");
        Text text2 = new Text(thisArea.getDescription()+"\n");
        Text text3 = new Text("Adjacent areas: "+borderAreas+"\n");
        Text text4 = new Text("Items in room: "+allItems+"\n");
        output.getChildren().addAll(new Text(input+"\n\n"), text1, text2, text3, text4);

        System.out.println(commandInput.getText());

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