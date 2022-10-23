package fxml.enigmaApp.machineApp.leftSetCode.plugs;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ChosenPlugsController {
    private InitializePlugsController initializePlugsController;
    @FXML private Label plugsLabel;

    public void setMainController(InitializePlugsController initializePlugsController){
        this.initializePlugsController=initializePlugsController;
    }

    public void presentChosenPlugs(String letter){
        plugsLabel.setText(plugsLabel.getText()+letter);
    }

    public void resetPlugLabel(){
       plugsLabel.setText("");
    }

    public Label getPlugsLabel() {
        return plugsLabel;
    }
}
