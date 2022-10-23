package fxml.enigmaApp.bruteForceApp;

import bruteForce.BruteForce;
import engine.Engine;
import fxml.enigmaApp.bruteForceApp.dictionary.DictionaryController;
import fxml.enigmaApp.bruteForceApp.dm.DmOperationalController;
import fxml.enigmaApp.bruteForceApp.dm.DmOutputController;
import fxml.enigmaApp.bruteForceApp.topBruteForce.TopCurrentCodeBruteForceController;
import fxml.enigmaApp.mainApp.EnigmaAppController;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public class BruteForceAppController {
    private EnigmaAppController enigmaAppController;
    private BruteForce bruteForce;
    @FXML private ScrollPane topCurrentCodeBruteForceComponent;
    @FXML private TopCurrentCodeBruteForceController topCurrentCodeBruteForceComponentController;
    @FXML private AnchorPane dictionaryComponent;
    @FXML private DictionaryController dictionaryComponentController;
    @FXML private AnchorPane dmOperationalComponent;
    @FXML private DmOperationalController dmOperationalComponentController;
    @FXML private AnchorPane dmOutputComponent;
    @FXML private DmOutputController dmOutputComponentController;

    @FXML
    public void initialize() {
        if(topCurrentCodeBruteForceComponentController!=null && dictionaryComponentController!=null&&dmOperationalComponentController!=null && dmOutputComponentController!=null) {
            topCurrentCodeBruteForceComponentController.setMainController(this);
            dictionaryComponentController.setMainController(this);
            dmOperationalComponentController.setMainController(this);
            dmOutputComponentController.setMainController(this);
        }
    }

    public void createBruteForce(Engine engine,String pathOfXml){
        try {
            this.bruteForce=new BruteForce(engine,pathOfXml);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public DmOutputController getDmOutputComponentController() {
        return dmOutputComponentController;
    }

    public void clearDecodeText(){
       dmOutputComponentController.clearDecodeText();
    }

    public BruteForce getBruteForce() {
        return bruteForce;
    }

    public void setInformationForDM(){
        dmOperationalComponentController.setInformationForDM();
    }

    public void setOptionsBruteForce(){
        dmOperationalComponentController.setOptionsBruteForce();
    }

    public void setMainController(EnigmaAppController enigmaAppController){
        this.enigmaAppController=enigmaAppController;
    }
    public void setStartBtnAble(){
        dmOperationalComponentController.setStartBtnAble();
    }

    public DictionaryController getDictionaryComponentController() {
        return dictionaryComponentController;
    }

    public EnigmaAppController getEnigmaAppController() {
        return enigmaAppController;
    }

    public void presentDictionary(){
        dictionaryComponentController.presetDictionary();
    }
    public void setEncodeBtnAble(){
        dictionaryComponentController.setEncodeBtnAble();
    }
    //set current code in encrypt screen
    public void setCurrentCodeLabelEncryptApp(String currentMachineCode) {
        enigmaAppController.setCurrentCodeLabelEncryptApp(currentMachineCode);
    }
    //set current code in machine screen
    public void setCurrentMachineCodeLabelMachineApp(String currentMachineCode) {
        enigmaAppController.setCurrentMachineCodeLabelMachineApp(currentMachineCode);
    }
    public void setCurrentCodeLabelDmScreen(String currentCodeLabel) {
        topCurrentCodeBruteForceComponentController.setCurrentCodeLabelDmScreen(currentCodeLabel);
    }

    public void setResetCodeBtnClickAble(){

        topCurrentCodeBruteForceComponentController.setResetCodeBtnClickAble();
    }
    //set current code in machine screen
    public void setAverageTimeForTask(double averageTime) {
        dmOutputComponentController.setAverageTimeForTask(averageTime);
    }

    public void setProgress(double progress) {
        dmOutputComponentController.setProgress(progress);
    }
    public void setProgressPercent(long progressPercent){
        dmOutputComponentController.setProgressPercent(progressPercent);

    }
    public void setTotalTimeForTask(double totalTime){
        dmOutputComponentController.setTotalTimeForTask(totalTime);
    }
}
