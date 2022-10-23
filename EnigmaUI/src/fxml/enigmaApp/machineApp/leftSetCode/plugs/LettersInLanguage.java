package fxml.enigmaApp.machineApp.leftSetCode.plugs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Circle;

public class LettersInLanguage {
    private InitializePlugsController initializePlugsController;
    private boolean isLettersCreated =false;
    private int numberOfLetters;
    @FXML private FlowPane lettersFlowPane;

    public void setMainController(InitializePlugsController initializePlugsController){
        this.initializePlugsController=initializePlugsController;
    }


    public void createButtonsForEachLabel(){
        this.numberOfLetters=initializePlugsController.getLeftSetCodeController().getMachineAppController().getEnigmaAppController().getEngine().getRepository().getKeyboard().getNumberOfCharsInKeyboard();
        char[] keyboard=initializePlugsController.getLeftSetCodeController().getMachineAppController().getEnigmaAppController().getEngine().getRepository().getKeyboard().getKeyboardChars();

        for (int i = 1; i <=numberOfLetters; i++) {
            String letterStr=String.valueOf(keyboard[i]);
            Button letter= new Button(letterStr);
            letter.setShape(new Circle(1.5));
            letter.setId(letterStr);
            letter.setOnMouseClicked(event -> presentChosenPlugs(letterStr,letter));
            lettersFlowPane.getChildren().add(letter);
        }
    }

    public void deleteAllPlugsButtons(){
        lettersFlowPane.getChildren().clear();
    }

    public void presentChosenPlugs(String letterStr, Button letterBtn){
        letterBtn.setDisable(true);
        initializePlugsController.presentChosenPlugs(letterStr);
    }

    public FlowPane getLettersFlowPane() {
        return lettersFlowPane;
    }
}
