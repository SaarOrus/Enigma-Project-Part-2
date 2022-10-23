package fxml.enigmaApp.encryptApp.leftEncrypt.leftEncryptSerial;

import components.Keyboard;
import fxml.enigmaApp.encryptApp.leftEncrypt.LeftEncryptController;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import newUI.CheckEncrypt;

public class LeftEncryptSerialController {
    private LeftEncryptController leftEncryptController;
    @FXML private Label enterMessageLabel;
    @FXML private TextField messageToEncodeTxt;
    @FXML private Button processBtn;
    @FXML private Label doneEncodeLabel;
    @FXML private Label encodedMessageLabel;
    @FXML private Button clearBtn;
    @FXML private ImageView processImg;

    public void setMainController(LeftEncryptController leftEncryptController) {
        this.leftEncryptController = leftEncryptController;
    }

    @FXML
    void btnClearClicked(MouseEvent event) {
        doneEncodeLabel.setText("");
        encodedMessageLabel.setText("");
        messageToEncodeTxt.setText("");
    }

    public void setIsDisableAllComponents(boolean isDisable){
        enterMessageLabel.setDisable(isDisable);
        messageToEncodeTxt.setDisable(isDisable);
        processBtn.setDisable(isDisable);
        doneEncodeLabel.setDisable(isDisable);
        encodedMessageLabel.setDisable(isDisable);
        clearBtn.setDisable(isDisable);
    }

    public void clearText(){
        messageToEncodeTxt.setText("");
        doneEncodeLabel.setText("");
        encodedMessageLabel.setText("");
    }

    @FXML
    void processBtnClicked(MouseEvent event) {
        String messageToProcess = messageToEncodeTxt.getText();
        Keyboard currKeyboard=leftEncryptController.getEncryptAppController().getEnigmaAppController().getEngine().getRepository().getKeyboard();
        if (CheckEncrypt.checkIfStringToEncodeIsTheLanguage(messageToProcess,currKeyboard)) {
            doneEncodeLabel.setText("The encrypted \nmessage is: ");
            String processedMessage = leftEncryptController.getEncryptAppController().getEnigmaAppController().getEngine().encodeMessage(messageToProcess.toUpperCase(), true);
            encodedMessageLabel.setText(processedMessage);
            setStatisticsAndHistoryLabel(leftEncryptController.getEncryptAppController().getEnigmaAppController().getEngine().getHistoryAndStatistic().toString());
            setCurrentMachineCodeLabel(leftEncryptController.getEncryptAppController().getEnigmaAppController().getEngine().getCurrentCode().toString());
            setCurrentCodeLabel(leftEncryptController.getEncryptAppController().getEnigmaAppController().getEngine().getCurrentCode().toString());
            setMessagesNumbersLabel(leftEncryptController.getEncryptAppController().getEnigmaAppController().getEngine().getEncodingCounter());
            setCurrentCodeLabelDmScreen(leftEncryptController.getEncryptAppController().getEnigmaAppController().getEngine().getCurrentCode().toString());

            checkAndPlayAnimation();
        }
    }

    public void checkAndPlayAnimation(){
        if (leftEncryptController.isAnimationOn()) {
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), evt -> encodedMessageLabel.setVisible(false)),
                    new KeyFrame(Duration.seconds(1), evt -> encodedMessageLabel.setVisible(true)));
            timeline.setCycleCount(2);
            timeline.play();

            rotateProcessImg();
        }
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
    public void rotateProcessImg()
    {
        RotateTransition rt = new RotateTransition(Duration.millis(1500),processImg);
        rt.setByAngle(180);
        rt.setCycleCount(1);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    }
    public void setCurrentCodeLabelDmScreen(String currentCodeLabel) {
        leftEncryptController.setCurrentCodeLabelDmScreen(currentCodeLabel);
    }
}
