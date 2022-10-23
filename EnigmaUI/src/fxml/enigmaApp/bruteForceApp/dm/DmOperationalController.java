package fxml.enigmaApp.bruteForceApp.dm;

import bruteForce.dm.CandidateToDecode;
import bruteForce.dm.DifficultyLevels;
import fxml.UIAdapter;
import fxml.enigmaApp.bruteForceApp.BruteForceAppController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DmOperationalController {
    private BruteForceAppController bruteForceAppController;
    @FXML private ComboBox<String> levelComboBox;
    @FXML private ComboBox<Integer> amountOfAgentComboBox;
    @FXML private Spinner<Integer> missionSizeSpinner;
    @FXML private Label numberOfMissionLabel;
    @FXML private ToggleButton pauseBtn;
    @FXML private ToggleButton resumeBtn;
    @FXML private ToggleButton startBtn;
    @FXML private ToggleButton stopBtn;


    @FXML
    public void initialize(){
        setSizeOfMission();
    }

    public void setNumberOfMissionLabel(int numberOfMissionLabel) {
        this.numberOfMissionLabel.setText(String.valueOf(numberOfMissionLabel));
    }

    public void setInformationForDM(){
        String stringToDecode=bruteForceAppController.getDictionaryComponentController().getEncodeMessage();
        bruteForceAppController.getBruteForce().getDecryptionManager().setDmByUser(amountOfAgentComboBox.getValue(),DifficultyLevels.valueOf(levelComboBox.getValue()),Integer.parseInt(missionSizeSpinner.getEditor().getText())
                ,stringToDecode);
    }

    public void setProgress(double progress) {
        bruteForceAppController.setProgress(progress);
    }


    public void setOptionsBruteForce(){
        setLevelOption();
        setAmountOfAgent();
    }

    public void setSizeOfMission(){
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 2147483647,10);
        missionSizeSpinner.setValueFactory(valueFactory);
    }
    public void setAmountOfAgent(){
        int numberOfAgent=bruteForceAppController.getBruteForce().getNumberOfAgents();
        amountOfAgentComboBox.getItems().clear();
        List<Integer> rangeOfAgent = IntStream.range(2, numberOfAgent+1).boxed().collect(Collectors.toList());
        amountOfAgentComboBox.getItems().addAll(rangeOfAgent);
    }

    public void setLevelOption(){
        levelComboBox.getItems().clear();
        List<String> levelString= Arrays.asList("EASY","MEDIUM","HARD", "IMPOSSIBLE");
        levelComboBox.getItems().addAll(levelString);
    }


    public void setMainController(BruteForceAppController bruteForceAppController) {
        this.bruteForceAppController = bruteForceAppController;
    }

    @FXML void pauseProgressBtnClick(MouseEvent event) {
        pauseBtn.setDisable(true);
        resumeBtn.setDisable(false);

        bruteForceAppController.getBruteForce().getDecryptionManager().setPause(true);
    }

    public void setProgressPercent(long progressPercent){
        bruteForceAppController.setProgressPercent(progressPercent);
    }

    @FXML void resumeProgressBtnClick(MouseEvent event) {
        pauseBtn.setDisable(false);
        resumeBtn.setDisable(true);

        bruteForceAppController.getBruteForce().getDecryptionManager().resume();
        bruteForceAppController.getBruteForce().getDecryptionManager().setPause(false);
    }

    public void setStartBtnAble(){
        startBtn.setDisable(false);
    }

    public boolean isInformationFromUserValid(){
        Alert errorAlert = new Alert(Alert.AlertType.WARNING);

        if (missionSizeSpinner.getValue() < 1) {
            errorAlert.setContentText("Mission size is less then 1 -not valid");
            errorAlert.setHeaderText("Mission size is invalid");
            errorAlert.showAndWait();
            return false;
        } else if (amountOfAgentComboBox.getSelectionModel().isEmpty()) {
            errorAlert.setContentText("Amount of agents must be selected");
            errorAlert.setHeaderText("Unselected amount of agents");
            errorAlert.showAndWait();
            return false;
        } else if (levelComboBox.getSelectionModel().isEmpty()) {
            errorAlert.setContentText("level must be selected");
            errorAlert.setHeaderText("Unselected level");
            errorAlert.showAndWait();
            return false;
        }
        return true;
    }

    @FXML
    void startProgressBtnClick(MouseEvent event) {
        setProgress(0.0);
        setProgressPercent(1);
        bruteForceAppController.getBruteForce().getDecryptionManager().resetTime();
        setTotalTimeForTask(bruteForceAppController.getBruteForce().getDecryptionManager().getTimeOfExecutionAllTasks());
        setAverageTimeForTask(bruteForceAppController.getBruteForce().getDecryptionManager().getAvgTimeForAllTasks());
        bruteForceAppController.getBruteForce().getDecryptionManager().setFinish(false);
        UIAdapter uiAdapter = createUIAdapter();

        if (isInformationFromUserValid()) {
            startBtn.setDisable(true);
            stopBtn.setDisable(false);
            pauseBtn.setDisable(false);

            bruteForceAppController.setInformationForDM();
            bruteForceAppController.getBruteForce().getDecryptionManager().updateTotalNumberOfTasksToPerformed();
            setNumberOfMissionLabel(bruteForceAppController.getBruteForce().getDecryptionManager().getTotalNumberOfTasksToPerformed());

            new Thread(() -> {
                    try {
                        bruteForceAppController.getBruteForce().getDecryptionManager().executeByDifficultyLevel();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
            }).start();

            Thread listenerThread = null;
            listenerThread = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(1000);

                        if (!bruteForceAppController.getBruteForce().getDecryptionManager().isPause()) {
                            if (!bruteForceAppController.getBruteForce().getDecryptionManager().isFinish()) {
                                if (!bruteForceAppController.getBruteForce().getDecryptionManager().isCandidatesEmpty()) {
                                    CandidateToDecode candidateToDecode = bruteForceAppController.getBruteForce().getDecryptionManager().getTotalCandidateStringsToDecode().pollFromQueue();
                                    uiAdapter.addNewCandidate(candidateToDecode);
                                    setProgress(bruteForceAppController.getBruteForce().getDecryptionManager().getProgressAllLevels());
                                    setProgressPercent(bruteForceAppController.getBruteForce().getDecryptionManager().getProgressPercent());
                                    setTotalTimeForTask(bruteForceAppController.getBruteForce().getDecryptionManager().getTimeOfExecutionAllTasks());
                                    bruteForceAppController.getBruteForce().getDecryptionManager().calculateAverageTimeToTask();
                                    setAverageTimeForTask(bruteForceAppController.getBruteForce().getDecryptionManager().getAvgTimeForAllTasks());
                                } else {
                                    setTotalTimeForTask(bruteForceAppController.getBruteForce().getDecryptionManager().getTimeOfExecutionAllTasks());
                                    bruteForceAppController.getBruteForce().getDecryptionManager().calculateAverageTimeToTask();
                                    setAverageTimeForTask(bruteForceAppController.getBruteForce().getDecryptionManager().getAvgTimeForAllTasks());
                                    setProgressPercent(bruteForceAppController.getBruteForce().getDecryptionManager().getProgressPercent());
                                    setProgress(bruteForceAppController.getBruteForce().getDecryptionManager().getProgressAllLevels());
                                }
                            } else {
                                bruteForceAppController.getBruteForce().getDecryptionManager().stopDecoding();
                                return;
                            }
                        } else {;
                            bruteForceAppController.getBruteForce().getDecryptionManager().setIsPause(true);
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            });
            listenerThread.start();

        }
    }

    public void setAverageTimeForTask(double averageTime) {
        bruteForceAppController.setAverageTimeForTask(averageTime);
    }

    private UIAdapter createUIAdapter() {
        return new UIAdapter(
                CandidateToDecode -> {
                    bruteForceAppController.getDmOutputComponentController().concatDecodeText(CandidateToDecode.toString() + "\n");
                }
        );
    }

    public void clearDecodeText(){
        bruteForceAppController.clearDecodeText();
    }

    public void setTotalTimeForTask(double totalTime){
        bruteForceAppController.setTotalTimeForTask(totalTime);
    }

    @FXML
    void stopProgressBtnClick(MouseEvent event) {
        clearDecodeText();
        setProgress(0.0);
        setProgressPercent(1);
        startBtn.setDisable(true);
        stopBtn.setDisable(true);
        pauseBtn.setDisable(true);
        resumeBtn.setDisable(true);

        bruteForceAppController.getBruteForce().getDecryptionManager().stopDecoding();
        bruteForceAppController.getBruteForce().getDecryptionManager().setFinish(true);

        setTotalTimeForTask(bruteForceAppController.getBruteForce().getDecryptionManager().getTimeOfExecutionAllTasks());
        bruteForceAppController.getBruteForce().getDecryptionManager().calculateAverageTimeToTask();
        setAverageTimeForTask(bruteForceAppController.getBruteForce().getDecryptionManager().getAvgTimeForAllTasks());

        setProgress(bruteForceAppController.getBruteForce().getDecryptionManager().getProgressAllLevels());
        setProgressPercent(bruteForceAppController.getBruteForce().getDecryptionManager().getProgressPercent());

    }

}
