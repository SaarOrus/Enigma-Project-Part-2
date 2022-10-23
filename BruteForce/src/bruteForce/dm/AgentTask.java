package bruteForce.dm;

import bruteForce.DictionaryDecipher;
import components.Keyboard;
import dtoHandling.ConvertDeliveryObjToMachine;
import dtoHandling.MachineCode;
import enigmaMachine.Machine;
import repository.RepositoryOfComponents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AgentTask implements Runnable {
    private List<MachineCode> sectionOfCodesToTest; // list of all codes which the task will check to find candidate strings
    private CandidatesQueue candidateStringsToDecode; //product of task
    private final String stringToDecode;
    private final int taskSize;
    private String endPos;
    private int[] rotorsNumbers; //constant for all codes
    private final String reflectorGreekNumber; //constant for all codes
    private final Keyboard keyboard;
    private final int amountOfRotors;
    private Machine machine;
    private final DictionaryDecipher dictionary;
    private final RepositoryOfComponents repo;
    private Long timeToProcessTask;

    //ctor
    public AgentTask(int taskSize, int[] rotorsNumbers, String reflectorGreekNumber, String stringToDecode, DictionaryDecipher dictionary, String startPos, RepositoryOfComponents repo, CandidatesQueue totalCandidateStringsToDecode){
        this.taskSize=taskSize;
        this.repo=repo;
        this.rotorsNumbers=rotorsNumbers;
        this.reflectorGreekNumber=reflectorGreekNumber;
        this.keyboard=repo.getKeyboard();
        this.amountOfRotors=repo.getNumberOfRotorsInUse();
        this.stringToDecode=stringToDecode;
        this.dictionary=dictionary;
        this.sectionOfCodesToTest=new ArrayList<>();
        this.candidateStringsToDecode= totalCandidateStringsToDecode;
        this.timeToProcessTask=0L;

        buildAllCandidatesToEncodeByTaskSize(startPos);
    }

    @Override
    public void run() {
        if (!DecryptionManager.checkIfWait()) {
            decodeByAllCodes();
        } else {
            synchronized (DecryptionManager.pauseLock){
                if (DecryptionManager.checkIfWait()) {
                    try {
                        DecryptionManager.pauseLock.wait();
                    } catch (InterruptedException ignore) {
                        //throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public String getEndPos() {
        return endPos;
    }

    public void decodeByAllCodes(){
        Long startTime = System.currentTimeMillis();
        for(MachineCode currMachineCode: sectionOfCodesToTest){
            String decodedString=decodeStringByCurrentCode(currMachineCode);
            if(dictionary.checkIfStringIsExist(decodedString)){
                String agentName = Thread.currentThread().getName().substring(Thread.currentThread().getName().length()-1);
                CandidateToDecode candidateToDecode=new CandidateToDecode(currMachineCode,agentName,decodedString);
                candidateStringsToDecode.addToQueue(candidateToDecode);
            }
        }
        Long endTime = System.currentTimeMillis();
        this.timeToProcessTask = (endTime - startTime);
        DecryptionManager.addToTotalTime(timeToProcessTask);
    }

    public String decodeStringByCurrentCode(MachineCode currMachineCode){
        ConvertDeliveryObjToMachine convertToMachine=new ConvertDeliveryObjToMachine(currMachineCode,repo);
        machine= new Machine(convertToMachine.getEnigmaMachine());

        return machine.encodeString(stringToDecode);
    }

    public String getNextCode(String startPos) {
        char[] keyboardChars=keyboard.getKeyboardChars();
        int numberOfChars=keyboard.getNumberOfCharsInKeyboard();
        String lasPos=getLastInitialPosition();
        int i = startPos.length() - 1;
        boolean needToUpdateNextCol = true;

        while (needToUpdateNextCol) {
            if (startPos.charAt(i) != keyboardChars[numberOfChars]) {
                char nextCharInAbc = keyboardChars[keyboard.getIndexOfChar(startPos.charAt(i))+1];
                startPos = startPos.substring(0, i) + nextCharInAbc + startPos.substring(i + 1);
                needToUpdateNextCol = false;
            } else {
                if(startPos.equals(lasPos))
                    return startPos;
                startPos = startPos.substring(0, i) + keyboardChars[1] + startPos.substring(i + 1);
                i = i - 1;

            }
        }
        return startPos;
    }

    public void buildAllCandidatesToEncodeByTaskSize(String startPos) {
        boolean isLastPos=false;
        int counter=taskSize;
        String resCode=startPos;

        do{
            createMachineCodeAndAddToList(resCode);
            resCode=getNextCode(resCode);

            if(getLastInitialPosition().equals(resCode)){
                createMachineCodeAndAddToList(resCode);
                isLastPos=true;
            }
            counter--;

        }while (counter>0 && !isLastPos);

        endPos=resCode;
    }

    public void createMachineCodeAndAddToList(String resCode){
        MachineCode machineCode=new MachineCode();
        machineCode.setInitialPositionsOfRotors(convertStringPositionToArr(new StringBuilder(resCode)));
        machineCode.setRotorsNumbers(rotorsNumbers);
        machineCode.setReflectorGreekNumber(reflectorGreekNumber);
        HashMap<Character,Character> map=new HashMap<>();
        machineCode.setPlugsInUse(map);

        sectionOfCodesToTest.add(machineCode);
    }

    public String getLastInitialPosition(){
        String lastLetterInTheLanguage = String.valueOf(keyboard.getKeyboardChars()[keyboard.getNumberOfCharsInKeyboard()]);

        return String.join("", Collections.nCopies(amountOfRotors, lastLetterInTheLanguage));
    }

    public char[] convertStringPositionToArr(StringBuilder initialPositions) {
        char[] initialPositionsArr = new char[initialPositions.length()];

        for (int i = 0; i < initialPositions.length(); i++) {
            initialPositionsArr[i] = initialPositions.charAt(i);
        }

        return initialPositionsArr;
    }

}
