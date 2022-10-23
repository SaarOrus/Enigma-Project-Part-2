package fxml.enigmaApp.bruteForceApp.dm;

import fxml.enigmaApp.bruteForceApp.BruteForceAppController;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DmOutputController {
    private BruteForceAppController bruteForceAppController;
    @FXML private TextArea decode;
    @FXML private ProgressBar progress;
    @FXML private TextField missionStatusText;
    @FXML private TextField averageTimeForTask;
    @FXML private TextField totalTimeForTask;


    public void setMainController(BruteForceAppController bruteForceAppController) {
        this.bruteForceAppController = bruteForceAppController;
    }

    public void concatDecodeText(String decodeText){
        decode.setText(decode.getText() + decodeText);
    }

    public void clearDecodeText(){
        decode.clear();
    }

    public void setAverageTimeForTask(double averageTime) {
        String formattedString = String.format("%.02f", averageTime);
        this.averageTimeForTask.setText(String.valueOf(formattedString)+ "ms");
    }

    public void setProgress(double progress) {
        this.progress.setProgress(progress);
    }

    public void setProgressPercent(long progressPercent){
            this.missionStatusText.setText(progressPercent +" %");
    }

    public void setTotalTimeForTask(double totalTime){
        String formattedString = String.format("%.02f", totalTime);
        this.totalTimeForTask.setText(String.valueOf(formattedString) + "ms");
    }
}
