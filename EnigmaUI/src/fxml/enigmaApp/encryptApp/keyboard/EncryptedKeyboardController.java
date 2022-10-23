package fxml.enigmaApp.encryptApp.keyboard;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class EncryptedKeyboardController {
    private MainKeyboardController mainKeyboardController;
    @FXML private FlowPane keyboardEncryptedFlowPane;

    public void setMainController(MainKeyboardController mainKeyboardController) {
        this.mainKeyboardController = mainKeyboardController;
    }

    public void deleteAllKeyboardButtons() {
        keyboardEncryptedFlowPane.getChildren().clear();
    }

    public void setKeyboardEncryptedFlowPaneDisable(boolean isDisable) {
        for (Node currBtn : keyboardEncryptedFlowPane.getChildren()) {
            currBtn.setDisable(isDisable);
        }
    }

    public void concatToEncryptedText(String letter) {
        mainKeyboardController.concatToEncryptedText(letter);
    }

    public void setLettersOnEncryptedKeyboard() {
        int numberOfLettersInKeyboard = mainKeyboardController.getEncryptAppController().getEnigmaAppController().getEngine().getRepository().getKeyboard().getNumberOfCharsInKeyboard();
        char[] keyboard = mainKeyboardController.getEncryptAppController().getEnigmaAppController().getEngine().getRepository().getKeyboard().getKeyboardChars();

        for (int i = 1; i <= numberOfLettersInKeyboard; i++) {
            String letterStr = String.valueOf(keyboard[i]);
            Button letter = new Button(letterStr);
            letter.setDisable(true);
            letter.setId(letterStr);
            keyboardEncryptedFlowPane.getChildren().add(letter);
        }

    }

    public void presentEncodedChar(String letterEncrypted) {
        concatToEncryptedText(letterEncrypted);

        final Color startColor = Color.web("#f11d1d");
        final Color endColor = Color.web("#ddf3f0");
        final ObjectProperty<Color> color = new SimpleObjectProperty<>(startColor);
        final StringBinding cssColorSpec = Bindings.createStringBinding(() -> String.format("-fx-body-color: rgb(%d, %d, %d);",
                (int) (256 * color.get().getRed()),
                (int) (256 * color.get().getGreen()),
                (int) (256 * color.get().getBlue())), color);

        for (Node currBtn : keyboardEncryptedFlowPane.getChildren()) {
            if (currBtn.getId().equals(letterEncrypted)) {
                currBtn.styleProperty().bind(cssColorSpec);

                final Timeline timeline = new Timeline(
                        new KeyFrame(Duration.seconds(2), new KeyValue(color, endColor)));
                timeline.play();

            }
        }
    }

}
