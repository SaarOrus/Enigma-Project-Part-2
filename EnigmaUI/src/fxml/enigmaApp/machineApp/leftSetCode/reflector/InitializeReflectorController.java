package fxml.enigmaApp.machineApp.leftSetCode.reflector;

import fxml.enigmaApp.machineApp.leftSetCode.LeftSetCodeController;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;

public class InitializeReflectorController {
    private LeftSetCodeController leftSetCodeController;
    @FXML private ChoiceBox<String> reflectors_options_choiceBox;

    public void setMainController(LeftSetCodeController leftSetCodeController) {
        this.leftSetCodeController = leftSetCodeController;
    }

    @FXML
    void reflectors_options_pressed(MouseEvent event) {
        if (leftSetCodeController.getMachineAppController().getEnigmaAppController().isNeedToInitializeReflectorsChoiceBox()) {
            reflectors_options_choiceBox.getItems().clear();
            for (String currReflector : leftSetCodeController.getMachineAppController().getEnigmaAppController().getInputExplanation().getReflectorsOptions()) {
                reflectors_options_choiceBox.getItems().add(currReflector);
            }
            leftSetCodeController.getMachineAppController().getEnigmaAppController().setNeedToInitializeReflectorsChoiceBox(false);
        }
    }

    public ChoiceBox<String> getReflectors_options_choiceBox() {
        return reflectors_options_choiceBox;
    }

    public void resetChoiceOfReflector() {
        reflectors_options_choiceBox.getSelectionModel().clearSelection();
    }
}
