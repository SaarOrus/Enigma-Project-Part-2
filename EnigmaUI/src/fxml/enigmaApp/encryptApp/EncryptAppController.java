package fxml.enigmaApp.encryptApp;

import fxml.enigmaApp.encryptApp.keyboard.MainKeyboardController;
import fxml.enigmaApp.encryptApp.leftEncrypt.LeftEncryptController;
import fxml.enigmaApp.encryptApp.rightStatistics.RightStatisticsEncryptController;
import fxml.enigmaApp.encryptApp.topCurrentCode.TopCurrentCodeController;
import fxml.enigmaApp.mainApp.EnigmaAppController;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class EncryptAppController {
    private EnigmaAppController enigmaAppController;
    @FXML private ScrollPane topCurrentCodeComponent;
    @FXML private TopCurrentCodeController topCurrentCodeComponentController;
    @FXML private ScrollPane leftEncryptComponent;
    @FXML private LeftEncryptController leftEncryptComponentController;
    @FXML private ScrollPane rightStatisticsComponent;
    @FXML private RightStatisticsEncryptController rightStatisticsComponentController;
    @FXML private GridPane mainKeyboardComponent;
    @FXML private MainKeyboardController mainKeyboardComponentController;

    @FXML
    public void initialize() {
        if (topCurrentCodeComponentController != null && leftEncryptComponentController != null && rightStatisticsComponentController != null && mainKeyboardComponentController != null) {
            topCurrentCodeComponentController.setMainController(this);
            leftEncryptComponentController.setMainController(this);
            rightStatisticsComponentController.setMainController(this);
            mainKeyboardComponentController.setMainController(this);
        }
    }

    public void deleteAllKeyboardButtons() {
        mainKeyboardComponentController.deleteAllKeyboardButtons();
    }

    public void concatToMessageToEncryptText(String letter) {
        leftEncryptComponentController.concatToMessageToEncryptText(letter);
    }

    public void concatToEncryptedText(String letter) {
        leftEncryptComponentController.concatToEncryptedText(letter);
    }

    public void setMainController(EnigmaAppController enigmaAppController) {
        this.enigmaAppController = enigmaAppController;
    }

    public EnigmaAppController getEnigmaAppController() {
        return enigmaAppController;
    }

    public void setDisableChoiceToEncrypt(boolean isDisable) {
        leftEncryptComponentController.setDisableChoiceToEncrypt(isDisable);
    }

    public void setStatisticsAndHistoryLabel(String statisticsAndHistoryLabelText) {
        rightStatisticsComponentController.setStatisticsAndHistoryLabel(statisticsAndHistoryLabelText);
    }

    public void resetStatisticsAndHistoryLabel() {
        rightStatisticsComponentController.resetStatisticsAndHistoryLabel();
    }

    //set current code in machine screen
    public void setCurrentMachineCodeLabel(String currentMachineCode) {
        enigmaAppController.setCurrentMachineCodeLabelMachineApp(currentMachineCode);
    }

    //set current code in encrypt screen
    public void setCurrentCodeLabelEncryptApp(String currentMachineCode) {
        topCurrentCodeComponentController.setCurrentCodeLabel(currentMachineCode);
    }

    public void setMessagesNumbersLabel(int messagesNumbersLabel) {
        enigmaAppController.setMessagesNumbersLabel(messagesNumbersLabel);
    }

    public LeftEncryptController getLeftEncryptComponentController() {
        return leftEncryptComponentController;
    }

    public void setLettersOnKeyboard() {
        mainKeyboardComponentController.setLettersOnKeyboard();
    }

    public void clearTextEncrypt() {
        leftEncryptComponentController.clearTextEncrypt();
    }

    public void disableResetCodeButton(boolean isDisable) {
        topCurrentCodeComponentController.disableResetCodeButton(isDisable);
    }

    public void setKeyboardFlowPaneDisable(boolean isDisable) {
        mainKeyboardComponentController.setKeyboardDisable(isDisable);
    }

    public void setCurrentCodeLabelDmScreen(String currentCodeLabel) {
        enigmaAppController.setCurrentCodeLabelDmScreen(currentCodeLabel);
    }
}
