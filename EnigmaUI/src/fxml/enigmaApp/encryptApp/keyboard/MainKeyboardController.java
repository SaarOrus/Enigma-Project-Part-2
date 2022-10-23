package fxml.enigmaApp.encryptApp.keyboard;

import fxml.enigmaApp.encryptApp.EncryptAppController;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

public class MainKeyboardController {
    private EncryptAppController encryptAppController;
    @FXML private FlowPane keyboardComponent;
    @FXML private KeyboardController keyboardComponentController;
    @FXML private FlowPane encryptedKeyboardComponent;
    @FXML private EncryptedKeyboardController encryptedKeyboardComponentController;

    @FXML
    public void initialize(){
        if(keyboardComponentController!=null && encryptedKeyboardComponentController!=null){
            keyboardComponentController.setMainController(this);
            encryptedKeyboardComponentController.setMainController(this);
        }
    }

    public void concatToMessageToEncryptText(String letter){
        encryptAppController.concatToMessageToEncryptText(letter);
    }

    public void concatToEncryptedText(String letter){
        encryptAppController.concatToEncryptedText(letter);
    }

    public void setKeyboardDisable(boolean isDisable){
        keyboardComponentController.setKeyboardFlowPaneDisable(isDisable);
        encryptedKeyboardComponentController.setKeyboardEncryptedFlowPaneDisable(isDisable);
    }

    public EncryptedKeyboardController getEncryptedKeyboardComponentController() {
        return encryptedKeyboardComponentController;
    }

    public void setMainController(EncryptAppController encryptAppController){
        this.encryptAppController=encryptAppController;
    }

    public void deleteAllKeyboardButtons(){
        encryptedKeyboardComponentController.deleteAllKeyboardButtons();
        keyboardComponentController.deleteAllKeyboardButtons();
    }

    public void setLettersOnKeyboard() {
        keyboardComponentController.setLettersOnKeyboard();
        encryptedKeyboardComponentController.setLettersOnEncryptedKeyboard();
    }

    public EncryptAppController getEncryptAppController() {
        return encryptAppController;
    }
}
