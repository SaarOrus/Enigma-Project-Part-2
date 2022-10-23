package fxml.enigmaApp.encryptApp.rightStatistics;

import fxml.enigmaApp.encryptApp.EncryptAppController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RightStatisticsEncryptController {
    private EncryptAppController encryptAppController;
    @FXML private TextArea statisticsAndHistoryText;

    public void setMainController(EncryptAppController encryptAppController){
        this.encryptAppController=encryptAppController;
    }

    public void setStatisticsAndHistoryLabel(String statisticsAndHistoryLabelText) {
        this.statisticsAndHistoryText.setText(statisticsAndHistoryLabelText);
    }

    public void resetStatisticsAndHistoryLabel() {
        this.statisticsAndHistoryText.setText("There is no history and statistic to this machine.");
    }
}
