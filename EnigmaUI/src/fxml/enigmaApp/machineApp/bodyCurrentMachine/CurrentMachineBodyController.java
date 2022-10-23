package fxml.enigmaApp.machineApp.bodyCurrentMachine;


import fxml.enigmaApp.machineApp.MachineAppController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class CurrentMachineBodyController {
    private MachineAppController machineAppController;

    @FXML private TextArea initializeCodeText;

    @FXML private TextArea currentMachineCodeText;

    public void setMainController(MachineAppController machineAppController) {
        this.machineAppController = machineAppController;
    }

    public void setCurrentMachineCodeLabelMachineApp(String currentMachineCode) {
        currentMachineCodeText.setText(currentMachineCode);
    }

    public void setInitializeMachineCodeLabel(String initializeMachineCode) {
        initializeCodeText.setText(initializeMachineCode);
    }
}
