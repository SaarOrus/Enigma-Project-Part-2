package engine;

import Utilies.LogicUtilities;
import components.Rotor;
import dtoHandling.CodeConfiguration;
import dtoHandling.ConvertDeliveryObjToMachine;
import dtoHandling.MachineCode;
import dtoHandling.RepositoryInformationDto;
import enigmaMachine.Machine;
import historyAndStatistic.EncodeHistory;
import historyAndStatistic.EncodingsOfCodeConfiguration;
import historyAndStatistic.HistoryAndStatistic;
import repository.RepositoryOfComponents;
import xmlFilesHandling.CheckXMLFile;
import xmlFilesHandling.ReadXmlFile;
import xmlFilesHandling.convertedCte.ConvertCteToComponents;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class Engine implements Executable{
    private String pathOfXml;
    //history and statistic
    private EncodeHistory currEncodeHistory;
    private EncodingsOfCodeConfiguration currEncodingsOfCodeConfiguration;
    private final HistoryAndStatistic historyAndStatistic;
    private int encodingCounter;
    //code configuration
    private CodeConfiguration currentCode;
    private CodeConfiguration initializeCode;
    //repository and current machine
    private RepositoryOfComponents repository;
    private Machine machine;
    //dto
    private RepositoryInformationDto repoDto;
    private CodeConfiguration codeConfigurationDto;
    //encoding
    private MachineCode machineCode;
    private long totalTimeOfEncodingMessage;

    //ctor
    public Engine() {
        this.currEncodingsOfCodeConfiguration=new EncodingsOfCodeConfiguration();
        this.machineCode = new MachineCode();
        this.encodingCounter = 0;
        this.historyAndStatistic = new HistoryAndStatistic();
        this.pathOfXml = null;
        this.totalTimeOfEncodingMessage=0;
    }
    //set  functions
    public void setPathOfXml(String pathOfXml) {
        this.pathOfXml = pathOfXml;
    }
    public void setMachine(Machine machine) {
        this.machine = machine;
    }
    public void setMachineCode(MachineCode machineCode) {
        this.machineCode = machineCode;
    }
    public MachineCode getMachineCode() {
        return machineCode;
    }
    public void setTotalTimeOfEncodingMessage() {
        this.totalTimeOfEncodingMessage = 0;
    }
    //get functions
    public Machine getMachine() {
        return machine;
    }
    public RepositoryOfComponents getRepository() {
            return repository;
    }
    public CodeConfiguration getCurrentCode() {
        return currentCode;
    }
    public CodeConfiguration getInitializeCode() {
        return initializeCode;
    }
    public int getEncodingCounter() {
        return encodingCounter;
    }
    public HistoryAndStatistic getHistoryAndStatistic() {
        return historyAndStatistic;
    }
    public RepositoryInformationDto getRepoDto() {
        return repoDto;
    }

    //encode Message
    @Override
    public String encodeMessage(String stringToEncode, boolean isNeedToUpdate){
        String encodedString= encodeStringInEnigma(stringToEncode,isNeedToUpdate);
        updateCodeConfiguration();
        return encodedString;
    }
    public String encodeStringInEnigma(String stringToEncode, boolean isNeedToUpdate) {
        long startTime = System.nanoTime();
        String encodedString=machine.encodeString(stringToEncode);
        long endTime = System.nanoTime();
        this.totalTimeOfEncodingMessage += (endTime - startTime);

        if(isNeedToUpdate)
             updateHistoryAndStatistics(stringToEncode, encodedString);

        return encodedString;
    }
    public void updateHistoryAndStatistics(String stringToEncode, String encodedString){
        currEncodeHistory = new EncodeHistory(stringToEncode, encodedString, totalTimeOfEncodingMessage);
        currEncodingsOfCodeConfiguration.getEncodeHistoryList().add(currEncodeHistory);
        encodingCounter++;
        this.totalTimeOfEncodingMessage=0;
    }
    public void updateCodeConfiguration() {
        Rotor[] rotors = machine.getRotorsComponents().getRotors();
        int[] notchPlaceInRotor = new int[rotors.length - 1];
        char[] charAppearInWindows = new char[rotors.length - 1];

        for (int i = 1; i < rotors.length; i++) {
            notchPlaceInRotor[i - 1] = rotors[i].getIndexOfNotch();
            charAppearInWindows[i - 1] = rotors[i].getCurrentCharInWindow();
        }

        notchPlaceInRotor=LogicUtilities.reverseArrayInt(notchPlaceInRotor,0);
        charAppearInWindows=LogicUtilities.reverseArrayChar(charAppearInWindows,0);

        this.currentCode.setNotchLocationInRotor(notchPlaceInRotor);
        this.currentCode.setInitialPositionsOfRotors(charAppearInWindows);
    }
    // Create machine from choice of components
    public void createMachines(){
        this.machine =new Machine(convertedChoiceToMachine());
    }
    public Machine convertedChoiceToMachine() {
        ConvertDeliveryObjToMachine convertDeliveryObjToMachine = new ConvertDeliveryObjToMachine(machineCode, repository);
        return convertDeliveryObjToMachine.getEnigmaMachine();
    }

    //code configuration
    @Override
    public void resetCodeToInitializeCode() {
        createMachines();
        initialCodeConfigurationByMachine();
    }
    public void initialCodeConfigurationByMachine() {
        Rotor[] rotors = machine.getRotorsComponents().getRotors();
        int[] notchPlaceInRotor = new int[rotors.length - 1];
        for (int i = 1; i < rotors.length; i++) {
            notchPlaceInRotor[i - 1] = rotors[i].getIndexOfNotch();
        }

        this.initializeCode = new CodeConfiguration(machineCode, notchPlaceInRotor);
        this.currentCode = new CodeConfiguration(new MachineCode(machineCode), notchPlaceInRotor);
    }

    // initial random codes
    @Override
    public void initialCodeAutomatically(){
        createCodeAutomatically();
        initialCodeConfigurationByMachine();
        currEncodingsOfCodeConfiguration = new EncodingsOfCodeConfiguration();
        initialHistoryAndStatistic();
    }
    public void initialHistoryAndStatistic(){
        currEncodingsOfCodeConfiguration.setCodeConfiguration(new StringBuilder(currentCode.toString()));
        historyAndStatistic.getMachineHistoryAndStatisticList().add(currEncodingsOfCodeConfiguration);
    }
    public void createCodeAutomatically() {
        int numberOfRotors= repoDto.getNumbersOfRotorsInUse();
        randomRotorsConfiguration(numberOfRotors);
        randomReflector();
        randomPlugsConfiguration();
        createMachines();
    }
    public void randomPlugsConfiguration(){
        int numberOfPlugsInUse= LogicUtilities.getRandomNumber(0,repository.getKeyboard().getNumberOfCharsInKeyboard()/2);
        HashMap<Character,Character> plugsInUse=new HashMap<>();
        char firstCharPlug,secondCharPlug;

        for (int i = 0; i < numberOfPlugsInUse; i++) {
            do {
                int randomFirstPlug = LogicUtilities.getRandomNumber(1, repository.getKeyboard().getNumberOfCharsInKeyboard());
                int randomSecondPlug;

                do {
                    randomSecondPlug = LogicUtilities.getRandomNumber(1, repository.getKeyboard().getNumberOfCharsInKeyboard());
                } while (randomFirstPlug == randomSecondPlug);

                firstCharPlug = repository.getKeyboard().getKeyboardChars()[randomFirstPlug];
                secondCharPlug = repository.getKeyboard().getKeyboardChars()[randomSecondPlug];

            }while(!isExistDuplicatesInRandomPlugs(firstCharPlug,secondCharPlug,plugsInUse));

            plugsInUse.put(firstCharPlug,secondCharPlug);
        }
        machineCode.setPlugsInUse(plugsInUse);
    }
    public void randomReflector(){
        int numberOfReflection= LogicUtilities.getRandomNumber(1,repository.getNumberOfReflectors()-1);
        String reflectorGreekNumber= LogicUtilities.convertNumberToReflectorId(numberOfReflection);

        machineCode.setReflectorGreekNumber(reflectorGreekNumber);
    }
    public boolean isExistDuplicatesInRandomPlugs(char firstCharPlug,char secondCharPlug,HashMap<Character,Character> plugsInUse){
        boolean isValid=true;

        for(Map.Entry<Character, Character> entry : plugsInUse.entrySet()) {
            if(entry.getValue().equals(firstCharPlug) || entry.getKey().equals(firstCharPlug) || entry.getValue().equals(secondCharPlug)|| entry.getKey().equals(secondCharPlug)) {
                isValid = false;
                break;
            }
        }

        return isValid;
    }
    public void randomRotorsConfiguration(int numberOfRotors){
        int []  rotorsID= new int[numberOfRotors];
        char []  initialRotorsPosition= new char[numberOfRotors];
        boolean [] chosenIdOfRotors=new boolean[repository.getNumberOfRotors()];

        for (int i = 0; i < numberOfRotors; i++) {
            int randomRotorNumber;

            do {
                randomRotorNumber = LogicUtilities.getRandomNumber(1,repository.getNumberOfRotors()-1);
            } while (chosenIdOfRotors[randomRotorNumber]);

            chosenIdOfRotors[randomRotorNumber]=true;
            rotorsID[i] =randomRotorNumber;

            int indexOfCharInKeyboard = LogicUtilities.getRandomNumber(1,repository.getKeyboard().getNumberOfCharsInKeyboard());
            initialRotorsPosition[i] = repository.getKeyboard().getKeyboardChars()[indexOfCharInKeyboard];
        }

        machineCode.setRotorsNumbers(rotorsID);
        machineCode.setInitialPositionsOfRotors(initialRotorsPosition);
    }

    // initial code manually
    @Override
    public void initialCodeManually(){
        createMachines();
        initialCodeConfigurationByMachine();
        this.currEncodingsOfCodeConfiguration = new EncodingsOfCodeConfiguration();
        currEncodingsOfCodeConfiguration.setCodeConfiguration(new StringBuilder(currentCode.toString()));
        historyAndStatistic.getMachineHistoryAndStatisticList().add(currEncodingsOfCodeConfiguration);
    }

    // Create repository machine
    public void createRepository() throws JAXBException, FileNotFoundException {
        createRepositoryFromXML();
        repoDto=new RepositoryInformationDto(repository.getKeyboard(),repository.getNumberOfRotors()-1,repository.getNumberOfReflectors()-1,repository.getNumberOfRotorsInUse());
        historyAndStatistic.resetHistoryAndStatistic();
        encodingCounter=0;
    }
    public void createRepositoryFromXML() throws JAXBException, FileNotFoundException {
        ReadXmlFile readXml= new ReadXmlFile(pathOfXml);
        CheckXMLFile checkXMLFile= new CheckXMLFile(readXml.getCteEnigma());
        ConvertCteToComponents convertedCte= new ConvertCteToComponents(readXml.getCteEnigma());
        this.repository =convertedCte.getRepository();
    }

}
