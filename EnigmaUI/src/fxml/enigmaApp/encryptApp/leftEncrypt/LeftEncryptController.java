package fxml.enigmaApp.encryptApp.leftEncrypt;

import fxml.enigmaApp.encryptApp.EncryptAppController;
import fxml.enigmaApp.encryptApp.leftEncrypt.leftEncryptSerial.LeftEncryptSerialController;
import fxml.enigmaApp.encryptApp.leftEncrypt.leftEncryptSingle.LeftEncryptSingleController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class LeftEncryptController {
    private EncryptAppController encryptAppController;
    @FXML private ScrollPane leftEncryptSerialComponent;
    @FXML private LeftEncryptSerialController leftEncryptSerialComponentController;
    @FXML private ScrollPane leftEncryptSingleComponent;
    @FXML private LeftEncryptSingleController leftEncryptSingleComponentController;
    @FXML private RadioButton letterByLetterMessageBtn;
    @FXML private RadioButton continuousMessageBtn;
    @FXML private ToggleButton offAnimationBtn;
    @FXML private ToggleButton onAnimationBtn;
    @FXML void offAnimationClick(MouseEvent event) {
        setAbleOnBtn();
    }
    @FXML void onAnimationClick(MouseEvent event) {
        setAbleOffBtn();
    }

    public void initialize(){
        if(leftEncryptSerialComponentController!=null && leftEncryptSingleComponentController!=null){
            leftEncryptSerialComponentController.setMainController(this);
            leftEncryptSingleComponentController.setMainController(this);
        }
    }

    public void setMainController(EncryptAppController encryptAppController){
        this.encryptAppController=encryptAppController;
    }

    public void concatToMessageToEncryptText(String letter){
        leftEncryptSingleComponentController.concatToMessageToEncryptText(letter);
    }

    public void concatToEncryptedText(String letter){
        leftEncryptSingleComponentController.concatToEncryptedText(letter);
    }

    public void setDisableChoiceToEncrypt(boolean isDisable){
        leftEncryptSerialComponentController.setIsDisableAllComponents(true);
        leftEncryptSingleComponentController.setIsDisableAllComponents(true);
        letterByLetterMessageBtn.setSelected(false);
        continuousMessageBtn.setSelected(false);
        letterByLetterMessageBtn.setDisable(isDisable);
        continuousMessageBtn.setDisable(isDisable);
    }

    public EncryptAppController getEncryptAppController() {
        return encryptAppController;
    }

    @FXML
    void encryptCharByCharClicked(MouseEvent event) {
        handleEncryptCharByCharClicked();
    }

    private void handleEncryptCharByCharClicked() {
        leftEncryptSingleComponentController.setIsDisableAllComponents(false);
        leftEncryptSerialComponentController.setIsDisableAllComponents(true);
        leftEncryptSerialComponentController.clearText();
        encryptAppController.getEnigmaAppController().getEngine().setTotalTimeOfEncodingMessage();
        continuousMessageBtn.setSelected(false);
        setKeyboardDisable(false);
    }

    @FXML
    void encryptContinuousClicked(MouseEvent event) {
        handleEncryptContinuousClicked();
    }

    private void handleEncryptContinuousClicked() {
        leftEncryptSerialComponentController.setIsDisableAllComponents(false);
        leftEncryptSingleComponentController.setIsDisableAllComponents(true);
        clearTextEncrypt();
        encryptAppController.getEnigmaAppController().getEngine().setTotalTimeOfEncodingMessage();
        letterByLetterMessageBtn.setSelected(false);
        setKeyboardDisable(true);
    }

    public void clearTextEncrypt(){
        leftEncryptSingleComponentController.clearText();
        leftEncryptSerialComponentController.clearText();
    }

    public void setKeyboardDisable(boolean isDisable){
        encryptAppController.setKeyboardFlowPaneDisable(isDisable);
    }

    public void setStatisticsAndHistoryLabel(String statisticsAndHistoryLabelText) {
        encryptAppController.setStatisticsAndHistoryLabel(statisticsAndHistoryLabelText);
    }
    //set current code in machine screen
    public void setCurrentMachineCodeLabel(String currentMachineCode) {
        encryptAppController.setCurrentMachineCodeLabel(currentMachineCode);
    }
    //set current code in encrypt screen
    public void setCurrentCodeLabel(String currentMachineCode) {
        encryptAppController.setCurrentCodeLabelEncryptApp(currentMachineCode);
    }
    public void setMessagesNumbersLabel(int messagesNumbersLabel) {
        encryptAppController.setMessagesNumbersLabel(messagesNumbersLabel);
    }
    public void setAbleOnBtn(){
        offAnimationBtn.setDisable(true);
        onAnimationBtn.setDisable(false);
    }
    public void setAbleOffBtn(){
        offAnimationBtn.setDisable(false);
        onAnimationBtn.setDisable(true);
    }
    public boolean isAnimationOn(){
        if(onAnimationBtn.isDisable())
            return  true;
        else
            return false;
    }
    public void setCurrentCodeLabelDmScreen(String currentCodeLabel) {
        encryptAppController.setCurrentCodeLabelDmScreen(currentCodeLabel);
    }
}
