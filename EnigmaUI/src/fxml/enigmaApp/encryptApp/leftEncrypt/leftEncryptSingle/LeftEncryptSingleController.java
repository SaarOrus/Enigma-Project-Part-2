package fxml.enigmaApp.encryptApp.leftEncrypt.leftEncryptSingle;

import components.Keyboard;
import fxml.enigmaApp.encryptApp.leftEncrypt.LeftEncryptController;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import newUI.CheckEncrypt;

public class LeftEncryptSingleController {
    private LeftEncryptController leftEncryptController;
    private  String processedMessage;
    private String messageToProcess;
    private boolean illegalChar;
    @FXML private Button doneBtn;
    @FXML private Label enterMessageLabel;
    @FXML private TextField messageToEncodeTxt;
    @FXML private Label doneEncodeLabel;
    @FXML private Label encodedMessageLabel;
    @FXML private ImageView processImg;

    public void setMainController(LeftEncryptController leftEncryptController){
        this.leftEncryptController=leftEncryptController;
    }

    public void setIsDisableAllComponents(boolean isDisable){
        enterMessageLabel.setDisable(isDisable);
        messageToEncodeTxt.setDisable(isDisable);
        doneEncodeLabel.setDisable(isDisable);
        encodedMessageLabel.setDisable(isDisable);
        doneBtn.setDisable(isDisable);
    }


    @FXML
    void processKeyPressed(KeyEvent event) {
        if(event.getCode() != KeyCode.BACK_SPACE && event.getCode() != KeyCode.DELETE && event.getCode() != KeyCode.ENTER){
            messageToProcess = messageToEncodeTxt.getText(messageToEncodeTxt.getText().length() - 1, messageToEncodeTxt.getText().length());
            if (CheckEncrypt.checkIfStringToEncodeIsTheLanguage(messageToProcess, leftEncryptController.getEncryptAppController().getEnigmaAppController().getEngine().getRepository().getKeyboard())) {
                doneEncodeLabel.setText("The encrypted \nmessage is: ");
                processedMessage = leftEncryptController.getEncryptAppController().getEnigmaAppController().getEngine().encodeMessage(messageToProcess.toUpperCase(), false);
                if (encodedMessageLabel.getText().isEmpty())
                    encodedMessageLabel.setText(processedMessage);
                else
                    encodedMessageLabel.setText(encodedMessageLabel.getText() + processedMessage);

                setCurrentMachineCodeLabel(leftEncryptController.getEncryptAppController().getEnigmaAppController().getEngine().getCurrentCode().toString());
                setCurrentCodeLabel(leftEncryptController.getEncryptAppController().getEnigmaAppController().getEngine().getCurrentCode().toString());
                setCurrentCodeLabelDmScreen(leftEncryptController.getEncryptAppController().getEnigmaAppController().getEngine().getCurrentCode().toString());
            } else {
                String withoutLastLetter = messageToEncodeTxt.getText().replace(messageToProcess, "");
                messageToEncodeTxt.setText(withoutLastLetter);
            }
        }
        else {
            event.consume();
            if(event.getCode() != KeyCode.ENTER)
            encodedMessageLabel.setText(encodedMessageLabel.getText().substring(0, encodedMessageLabel.getText().length() - 1));
        }
    }

    public void setCurrentCodeLabelDmScreen(String currentCodeLabel) {
        leftEncryptController.setCurrentCodeLabelDmScreen(currentCodeLabel);
    }

    public String processKey(String letterToEncode){
        return leftEncryptController.getEncryptAppController().getEnigmaAppController().getEngine().encodeMessage(letterToEncode.toUpperCase(),false);
    }

    @FXML
    void doneBtnClicked(MouseEvent event) {
        Keyboard currKeyboard=leftEncryptController.getEncryptAppController().getEnigmaAppController().getEngine().getRepository().getKeyboard();
        if (CheckEncrypt.checkIfStringToEncodeIsTheLanguage(messageToEncodeTxt.getText(),currKeyboard)) {
            leftEncryptController.getEncryptAppController().getEnigmaAppController().getEngine().updateHistoryAndStatistics(messageToEncodeTxt.getText().toUpperCase(), encodedMessageLabel.getText().toUpperCase());
            setStatisticsAndHistoryLabel(leftEncryptController.getEncryptAppController().getEnigmaAppController().getEngine().getHistoryAndStatistic().toString());
            setCurrentMachineCodeLabel(leftEncryptController.getEncryptAppController().getEnigmaAppController().getEngine().getCurrentCode().toString());
            setCurrentCodeLabel(leftEncryptController.getEncryptAppController().getEnigmaAppController().getEngine().getCurrentCode().toString());
            setMessagesNumbersLabel(leftEncryptController.getEncryptAppController().getEnigmaAppController().getEngine().getEncodingCounter());

            checkAndPlayAnimation();
            clearText();
        }
        else{
            messageToProcess="";
            messageToEncodeTxt.setText("");
        }

    }

    public void checkAndPlayAnimation(){
        if (leftEncryptController.isAnimationOn()) {
            rotateProcessImg();
        }
    }

    public void clearText(){
        messageToEncodeTxt.setText("");
        doneEncodeLabel.setText("");
        encodedMessageLabel.setText("");
    }
    //set current code in machine screen
    public void setCurrentMachineCodeLabel(String currentMachineCode) {
        leftEncryptController.setCurrentMachineCodeLabel(currentMachineCode);
    }

    //set current code in encrypt screen
    public void setCurrentCodeLabel(String currentMachineCode) {
        leftEncryptController.setCurrentCodeLabel(currentMachineCode);
    }
    public void setMessagesNumbersLabel(int messagesNumbersLabel) {
        leftEncryptController.setMessagesNumbersLabel(messagesNumbersLabel);
    }

    public void setStatisticsAndHistoryLabel(String statisticsAndHistoryLabelText) {
        leftEncryptController.setStatisticsAndHistoryLabel(statisticsAndHistoryLabelText);
    }

    public void concatToMessageToEncryptText(String letter){
        messageToEncodeTxt.setText(messageToEncodeTxt.getText()+letter);
    }

    public void concatToEncryptedText(String letter){
        encodedMessageLabel.setText(encodedMessageLabel.getText()+letter);
    }
    public void rotateProcessImg()
    {
        RotateTransition rt = new RotateTransition(Duration.millis(1500),processImg);
        rt.setByAngle(180);
        rt.setCycleCount(1);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    }



}
