package fxml.enigmaApp.bruteForceApp.topBruteForce;

import fxml.enigmaApp.bruteForceApp.BruteForceAppController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class TopCurrentCodeBruteForceController {
    private BruteForceAppController bruteForceAppController;
    @FXML private TextArea currentCodeTextDmScreen;
    @FXML private Button ResetCodeBtn;

    public void setMainController(BruteForceAppController bruteForceAppController){
        this.bruteForceAppController=bruteForceAppController;
    }


    public void setCurrentCodeLabelDmScreen(String currentCodeLabel) {
        currentCodeTextDmScreen.setText(currentCodeLabel);
    }

    @FXML
    void ResetCodeBtnClick(MouseEvent event) {
        bruteForceAppController.getEnigmaAppController().getEngine().resetCodeToInitializeCode();
        currentCodeTextDmScreen.setText(bruteForceAppController.getEnigmaAppController().getEngine().getInitializeCode().toString());
        setCurrentCodeLabelEncryptApp(bruteForceAppController.getEnigmaAppController().getEngine().getInitializeCode().toString());
        setCurrentMachineCodeLabelMachineApp(bruteForceAppController.getEnigmaAppController().getEngine().getInitializeCode().toString());
    }

    public void setCurrentCodeLabelEncryptApp(String currentMachineCode) {
        bruteForceAppController.setCurrentCodeLabelEncryptApp(currentMachineCode);
    }

    //set current code in machine screen
    public void setCurrentMachineCodeLabelMachineApp(String currentMachineCode) {
        bruteForceAppController.setCurrentMachineCodeLabelMachineApp(currentMachineCode);
    }
    public void setResetCodeBtnClickAble(){
        ResetCodeBtn.setDisable(false);
    }
}
