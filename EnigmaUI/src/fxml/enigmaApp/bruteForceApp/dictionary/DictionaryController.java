package fxml.enigmaApp.bruteForceApp.dictionary;

import fxml.enigmaApp.bruteForceApp.BruteForceAppController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import test.CheckLogic;

import java.util.List;

public class DictionaryController {
    private BruteForceAppController bruteForceAppController;
    private List<String> dictionaryWordList;
    private String encodeMessage;
    @FXML private TextField searchBar;
    @FXML private ListView<Button> listViewOfWords;
    @FXML private TextArea messageToEncode;
    @FXML private Label isMessageValid;
    @FXML private Button encodeBtn;

    @FXML
    void searchOnKeyPressed(KeyEvent event) {
        searchWord();
    }

    @FXML
    void encodeClicked(MouseEvent event) {
        CheckLogic checkLogic = new CheckLogic();
        String messageToEncodeAfterArrange = bruteForceAppController.getBruteForce().getDictionaryDecipher().deleteInvalidCharactersFromEncodeString((messageToEncode.getText().toUpperCase()));
        boolean messageIsValidKeyboard = checkLogic.checkIfCharsIsInTheLanguage(messageToEncodeAfterArrange, bruteForceAppController.getEnigmaAppController().getEngine().getRepository().getKeyboard());
        boolean messageIsValidDictionary = bruteForceAppController.getBruteForce().getDictionaryDecipher().checkIfStringIsExist(messageToEncodeAfterArrange);

        if (!messageIsValidDictionary)
            isMessageValid.setText("Message is not valid - You chose a word that is not in the dictionary");
        else if (!messageIsValidKeyboard) {
            isMessageValid.setText("Message is not valid - You chose a letter that is not in the keyboard");
        }
        else{
            encodeMessage = bruteForceAppController.getEnigmaAppController().getEngine().encodeMessage(messageToEncodeAfterArrange, false);
            isMessageValid.setText("Message to encode:\n" + messageToEncode.getText() + "\n Encode message is:\n" + encodeMessage);
            setStartBtnAble();
            setCurrentCodeLabelEncryptApp(bruteForceAppController.getEnigmaAppController().getEngine().getCurrentCode().toString());
            setCurrentMachineCodeLabelMachineApp(bruteForceAppController.getEnigmaAppController().getEngine().getCurrentCode().toString());
            setCurrentCodeLabelDmScreen(bruteForceAppController.getEnigmaAppController().getEngine().getCurrentCode().toString());
        }
        messageToEncode.setText("");

    }

    public void setStartBtnAble(){
        bruteForceAppController.setStartBtnAble();
    }
    public String getEncodeMessage() {
        return encodeMessage;
    }

    public void setMainController(BruteForceAppController bruteForceAppController) {
        this.bruteForceAppController = bruteForceAppController;
    }

    public void presetDictionary() {
        searchBar.setDisable(false);
        this.dictionaryWordList = bruteForceAppController.getBruteForce().getDictionaryDecipher().getDictionaryWordList();
        AddWordsToListView(dictionaryWordList);
    }

    public void addWordToEncodeString(String word) {
        messageToEncode.setText(messageToEncode.getText()+word+" ");
    }

    public void searchWord() {
        String wordToSearch = searchBar.getText();
        List<String> listOfFoundWords = bruteForceAppController.getBruteForce().getDictionaryDecipher().getWordsFromDictionaryBySearch(wordToSearch);
        listViewOfWords.getItems().clear();
        AddWordsToListView(listOfFoundWords);

    }

    public void AddWordsToListView(List<String> listToAdd) {
        listViewOfWords.getItems().clear();
        for (String word : listToAdd) {
            Button newWordBtn = new Button(word);
            newWordBtn.setOnMouseClicked(event -> addWordToEncodeString(word));
            listViewOfWords.getItems().add(newWordBtn);
        }
    }
    public void setEncodeBtnAble(){
        encodeBtn.setDisable(false);
    }
    public void setCurrentCodeLabelEncryptApp(String currentMachineCode) {
        bruteForceAppController.setCurrentCodeLabelEncryptApp(currentMachineCode);
    }
    //set current code in machine screen
    public void setCurrentMachineCodeLabelMachineApp(String currentMachineCode) {
        bruteForceAppController.setCurrentMachineCodeLabelMachineApp(currentMachineCode);
    }
    public void setCurrentCodeLabelDmScreen(String currentCodeLabel) {
        bruteForceAppController.setCurrentCodeLabelDmScreen(currentCodeLabel);
    }
}
