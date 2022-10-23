package fxml.enigmaApp.encryptApp.topCurrentCode;

import fxml.enigmaApp.encryptApp.EncryptAppController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class TopCurrentCodeController {
    private EncryptAppController encryptAppController;
    @FXML private Label currentCodeTitleLabel;
    @FXML private Button ResetCodeBtn;
    @FXML private TextArea currentCodeText;

    public void setMainController(EncryptAppController encryptAppController){
        this.encryptAppController=encryptAppController;
    }

    public void setCurrentCodeLabel(String currentMachineCode) {
        currentCodeText.setText(currentMachineCode);
    }

    @FXML
    void resetCodeBtnClicked(MouseEvent event) {
        encryptAppController.getEnigmaAppController().getEngine().resetCodeToInitializeCode();
        setCurrentCodeLabel(encryptAppController.getEnigmaAppController().getEngine().getInitializeCode().toString());
        setCurrentMachineCodeLabel(encryptAppController.getEnigmaAppController().getEngine().getInitializeCode().toString());
        clearTextEncrypt();
    }

    //set current code in machine screen
    public void setCurrentMachineCodeLabel(String currentMachineCode) {
        encryptAppController.setCurrentMachineCodeLabel(currentMachineCode);
    }

    public void clearTextEncrypt(){
        encryptAppController.clearTextEncrypt();
    }

    public void disableResetCodeButton(boolean isDisable){
        ResetCodeBtn.setDisable(isDisable);
    }


}
