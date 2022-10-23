package fxml.enigmaApp.encryptApp.keyboard;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

public class KeyboardController {
    private MainKeyboardController mainKeyboardController;
    private String letterSelectedId;
    @FXML private FlowPane keyboardFlowPane;

    public void setMainController(MainKeyboardController mainKeyboardController){
        this.mainKeyboardController=mainKeyboardController;
    }

    public void deleteAllKeyboardButtons(){
        keyboardFlowPane.getChildren().clear();
    }

    public void concatToMessageToEncryptText(String letter){
        mainKeyboardController.concatToMessageToEncryptText(letter);
    }


    public void setLettersOnKeyboard(){
        int numberOfLettersInKeyboard = mainKeyboardController.getEncryptAppController().getEnigmaAppController().getEngine().getRepository().getKeyboard().getNumberOfCharsInKeyboard();
        char[] keyboard = mainKeyboardController.getEncryptAppController().getEnigmaAppController().getEngine().getRepository().getKeyboard().getKeyboardChars();

        for (int i = 1; i <= numberOfLettersInKeyboard; i++) {
            String letterStr=String.valueOf(keyboard[i]);
            Button letter= new Button(letterStr);
            letter.setId(letterStr);
            letter.setDisable(true);
            letter.setOnMouseClicked(event -> selectedLetterToEncrypt(letterStr));
            keyboardFlowPane.getChildren().add(letter);
        }
    }

    public void setKeyboardFlowPaneDisable(boolean isDisable){
        for(Node currBtn: keyboardFlowPane.getChildren()) {
            currBtn.setDisable(isDisable);
        }
    }

    public void selectedLetterToEncrypt(String letterStr){
        concatToMessageToEncryptText(letterStr);
        String encryptedLetter=String.valueOf(mainKeyboardController.getEncryptAppController().getLeftEncryptComponentController().getEncryptAppController().getEnigmaAppController().getEngine().getMachine().encodeChar(letterStr.charAt(0)));
        resetIdBack();

        for(Node currBtn: keyboardFlowPane.getChildren()) {
            if (currBtn.getId().equals(letterStr))
                currBtn.setId("chosen");
        }

        mainKeyboardController.getEncryptedKeyboardComponentController().presentEncodedChar(encryptedLetter);
        this.letterSelectedId=letterStr;
    }

    public void resetIdBack(){
        for(Node currBtn: keyboardFlowPane.getChildren()) {
            if (currBtn.getId().equals("chosen"))
                currBtn.setId(letterSelectedId);
        }
    }

}

