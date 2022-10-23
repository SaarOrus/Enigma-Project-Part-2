package fxml.enigmaApp.machineApp.leftSetCode.plugs;

import fxml.enigmaApp.machineApp.leftSetCode.LeftSetCodeController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

public class InitializePlugsController {
    private LeftSetCodeController leftSetCodeController;
    @FXML private FlowPane lettersInLanguagesComponent;
    @FXML private LettersInLanguage lettersInLanguagesComponentController;
    @FXML private FlowPane chosenPlugsComponent;
    @FXML private ChosenPlugsController chosenPlugsComponentController;
    @FXML private Button resetBtn;

    @FXML
    public void initialize(){
        if(lettersInLanguagesComponentController!=null && chosenPlugsComponentController!=null){
            lettersInLanguagesComponentController.setMainController(this);
            chosenPlugsComponentController.setMainController(this);
        }
    }

    @FXML
    void resetBtnClicked(MouseEvent event) {
        resetPlugs();
    }

    public void setDisableResetBtn(Boolean isDisable){
        resetBtn.setDisable(isDisable);
    }

    public void resetPlugs(){
        for(Node currBtn: lettersInLanguagesComponentController.getLettersFlowPane().getChildren()){
            currBtn.setDisable(false);
        }
        resetPlugLabel();
    }

    public void deleteAllPlugsButtons(){
       lettersInLanguagesComponentController.deleteAllPlugsButtons();
    }

    public void resetPlugLabel(){
        chosenPlugsComponentController.resetPlugLabel();
    }

    public ChosenPlugsController getChosenPlugsComponentController() {
        return chosenPlugsComponentController;
    }

    public void setMainController(LeftSetCodeController leftSetCodeController){
        this.leftSetCodeController=leftSetCodeController;
    }

    public LeftSetCodeController getLeftSetCodeController() {
        return leftSetCodeController;
    }

    public void createButtonsForEachLabel(){
        lettersInLanguagesComponentController.createButtonsForEachLabel();
    }

    public void presentChosenPlugs(String letter){
        chosenPlugsComponentController.presentChosenPlugs(letter);
    }

    public void disableResetPlugsButton(boolean isDisable){
        resetBtn.setDisable(isDisable);
    }
}
