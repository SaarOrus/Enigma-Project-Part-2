package fxml.enigmaApp.machineApp.leftSetCode;

import dtoHandling.MachineCode;
import dtoHandling.RepositoryInformationDto;
import fxml.enigmaApp.machineApp.MachineAppController;
import fxml.enigmaApp.machineApp.leftSetCode.plugs.InitializePlugsController;
import fxml.enigmaApp.machineApp.leftSetCode.reflector.InitializeReflectorController;
import fxml.enigmaApp.machineApp.leftSetCode.rotors.InitializeRotorsController;
import fxml.enigmaApp.machineApp.leftSetCode.setButtons.SetCodeButtonsController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import newUI.CheckInputCode;
import newUI.SetInitializeCodeDtoByUserInput;

public class LeftSetCodeController {
    private MachineAppController machineAppController;
    private RepositoryInformationDto repoDto;
    private MachineCode codeDto;
    private String errorMsg;
    private boolean isMachineInitialized = false;
    @FXML private BorderPane initializePlugsComponent;
    @FXML private InitializePlugsController initializePlugsComponentController;
    @FXML private GridPane setCodeButtonsComponent;
    @FXML private SetCodeButtonsController setCodeButtonsComponentController;
    @FXML private GridPane initializeRotorsComponent;
    @FXML private InitializeRotorsController initializeRotorsComponentController;
    @FXML private GridPane initializeReflectorComponent;
    @FXML private InitializeReflectorController initializeReflectorComponentController;



    @FXML
    public void initialize() {
        if (setCodeButtonsComponentController != null && initializePlugsComponentController != null && initializeRotorsComponentController != null && initializeReflectorComponentController != null) {
            initializePlugsComponentController.setMainController(this);
            initializeRotorsComponentController.setMainController(this);
            setCodeButtonsComponentController.setMainController(this);
            initializeReflectorComponentController.setMainController(this);
        }
    }

    public void setMainController(MachineAppController machineAppController) {
        this.machineAppController = machineAppController;
    }

    public void resetPlugs() {
        initializePlugsComponentController.resetPlugs();
    }

    public void resetChoiceOfReflector() {
        initializeReflectorComponentController.resetChoiceOfReflector();
    }

    public boolean getIsMachineInitialized() {
        return isMachineInitialized;
    }

    public MachineAppController getMachineAppController() {
        return machineAppController;
    }

    public void createButtonsForEachLabel() {
        initializePlugsComponentController.createButtonsForEachLabel();
    }

    public void setDisableResetBtn(Boolean isDisable) {
        initializePlugsComponentController.setDisableResetBtn(isDisable);
    }

    public void setCodesButtonsAvailable() {
        setCodeButtonsComponentController.setCodesButtonsAvailable();
    }


    public void setCode() {
        repoDto = machineAppController.getEnigmaAppController().getEngine().getRepoDto();
        this.errorMsg = CheckInputCode.checkCodeInputFromUser(machineAppController.getEnigmaAppController().getEngine().getRepository().getNumberOfRotorsInUse(), initializeRotorsComponentController.getCountUserAddRotors(), initializeReflectorComponentController.getReflectors_options_choiceBox().getValue(), initializePlugsComponentController.getChosenPlugsComponentController().getPlugsLabel().getText());
        if (errorMsg != null) {
            Alert errorAlert = new Alert(Alert.AlertType.WARNING);
            errorAlert.setHeaderText("Wrong input");
            errorAlert.setContentText(errorMsg);
            errorAlert.showAndWait();
            isMachineInitialized = false;

        } else {
            SetInitializeCodeDtoByUserInput buildDtoFromInput = new SetInitializeCodeDtoByUserInput(initializeRotorsComponentController.getRotorsIdArr(), initializeRotorsComponentController.getInitialPositionArr(), initializeReflectorComponentController.getReflectors_options_choiceBox().getValue(), initializePlugsComponentController.getChosenPlugsComponentController().getPlugsLabel().getText());
            codeDto = buildDtoFromInput.getMachineCodeDto();
            machineAppController.getEnigmaAppController().getEngine().setMachineCode(codeDto);
            machineAppController.getEnigmaAppController().getEngine().resetCodeToInitializeCode();
            machineAppController.getEnigmaAppController().getEngine().initialHistoryAndStatistic();
            setCurrentMachineCodeLabel(machineAppController.getEnigmaAppController().getEngine().getInitializeCode().toString());
            setInitializeMachineCodeLabel(machineAppController.getEnigmaAppController().getEngine().getInitializeCode().toString());
            setCurrentCodeLabel(machineAppController.getEnigmaAppController().getEngine().getInitializeCode().toString());
            setCurrentCodeLabelDmScreen(machineAppController.getEnigmaAppController().getEngine().getInitializeCode().toString());

            machineInitializeMessage();
            setDisableChoiceToEncrypt(false);
            isMachineInitialized = true;
            setEncodeBtnAble();


        }
    }


    public void resetInputOnScreenAfterSet() {
        initializePlugsComponentController.resetPlugs();
        initializeReflectorComponentController.resetChoiceOfReflector();
        initializeRotorsComponentController.resetRotorsCode();
    }

    public void disableResetButton(boolean isDisable) {
        initializePlugsComponentController.disableResetPlugsButton(isDisable);
        initializeRotorsComponentController.disableResetRotorsButton(isDisable);
    }

    public void setIsMachineInitialized(boolean isMachineInitialized) {
        this.isMachineInitialized = isMachineInitialized;
    }

    public void setCurrentCodeLabel(String currentMachineCode) {
        machineAppController.setCurrentCodeLabelEncryptApp(currentMachineCode);
    }

    public void initializeRotors() {
        initializeRotorsComponentController.initializeRotors();
    }

    public void setDisableChoiceToEncrypt(boolean isDisable) {
        machineAppController.setDisableChoiceToEncrypt(isDisable);
    }

    public void machineInitializeMessage() {
        Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setHeaderText("Machine initialize successfully ! :)");
        errorAlert.showAndWait();
    }

    public void setCurrentMachineCodeLabel(String currentMachineCode) {
        machineAppController.setCurrentMachineCodeLabelMachineApp(currentMachineCode);
    }

    public void setInitializeMachineCodeLabel(String initializeMachineCode) {
        machineAppController.setInitializeMachineCodeLabel(initializeMachineCode);
    }

    public void setEncodeBtnAble() {
        machineAppController.setEncodeBtnAble();
    }

    public void disableResetCodeButton(boolean isDisable) {
        machineAppController.disableResetCodeButton(isDisable);
    }

    public void deleteAllPlugsButtons() {
        initializePlugsComponentController.deleteAllPlugsButtons();
    }

    public void setCurrentCodeLabelDmScreen(String currentCodeLabel) {
        machineAppController.setCurrentCodeLabelDmScreen(currentCodeLabel);
    }
    public void setResetCodeBtnClickAble(){
        machineAppController.setResetCodeBtnClickAble();
    }
}